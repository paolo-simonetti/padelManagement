package it.solving.padelmanagement.service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.CourtDTO;
import it.solving.padelmanagement.dto.message.clubmanagement.ClubManagementMessageDTO;
import it.solving.padelmanagement.dto.message.createpadelmatch.InputVerifyAvailabilityMessageDTO;
import it.solving.padelmanagement.dto.message.insert.CourtInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.CourtUpdateMessageDTO;
import it.solving.padelmanagement.exception.CourtBeReservedException;
import it.solving.padelmanagement.exception.VerifyAvailabilityException;
import it.solving.padelmanagement.mapper.CourtMapper;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.Court;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.model.Slot;
import it.solving.padelmanagement.repository.ClubRepository;
import it.solving.padelmanagement.repository.CourtRepository;
import it.solving.padelmanagement.repository.MatchRepository;
import it.solving.padelmanagement.repository.PlayerRepository;
import it.solving.padelmanagement.util.MyUtil;

@Service
public class CourtService {

	@Autowired
	private CourtRepository courtRepository;
	
	@Autowired
	private CourtMapper courtMapper;
	
	@Autowired
	private ClubRepository clubRepository;
	
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private MyUtil myUtil;
	
	public Court findById(Long id) {
		if (courtRepository.findById(id).isPresent()) {
			return courtRepository.findById(id).get();			
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public void update(CourtUpdateMessageDTO courtUpdateMessageDTO) {
		Court oldCourt=courtRepository.findByIdWithCompleteInfos(Long.parseLong(courtUpdateMessageDTO.getId()))
				.orElseThrow(NoSuchElementException::new);
		Court newCourt=courtMapper.convertUpdateMessageDTOToEntity(courtUpdateMessageDTO);
		
		// Il mapper non trasferisce le informazioni sul mayBeReserved, sul club e sulle partite
		Set<PadelMatch> matches=oldCourt.getMatches();
		Club club=oldCourt.getClub();
		club.removeFromCourts(oldCourt);
		club.addToCourts(newCourt);
		newCourt.setClub(club);
		clubRepository.save(club);

		matches.stream().forEach(match-> {
			match.setCourt(newCourt);
			matchRepository.save(match);
		});
		
		newCourt.setMatches(matches);
		newCourt.setMayBeReserved(oldCourt.mayBeReserved());
		courtRepository.save(newCourt);
	}
	
	public void insert(CourtInsertMessageDTO courtInsertMessageDTO) throws NoSuchElementException {
		// Recupero il club, lanciando un'eccezione se non esiste
		Club club=clubRepository.findById(Long.parseLong(courtInsertMessageDTO.getClubId()))
			.orElseThrow(NoSuchElementException::new);
		Court court=courtMapper.convertInsertMessageDTOToEntity(courtInsertMessageDTO);
		// Inserisco club e court nel contesto di persistenza
		courtRepository.save(court);
		clubRepository.save(club);
		// li collego
		court.setClub(club);
		club.addToCourts(court);
		courtRepository.save(court);
		clubRepository.save(club);
		
	} 
	
	public void cannotBeReserved(Long courtId) throws CourtBeReservedException {
		Court court=courtRepository.findByIdWithMatches(courtId).orElseThrow(NoSuchElementException::new);
		if (court.getMatches()!=null && court.getMatches().size()>0) {
			throw new CourtBeReservedException("Forbidden operation: there are scheduled matches for that court!");
		} 
		
		if (court.mayBeReserved()) {
			court.setMayBeReserved(false);
			courtRepository.save(court);
		} else {
			throw new CourtBeReservedException("Forbidden operation: the court couldn't already be reserved!");
		}
	}
	
	public void canBeReserved(Long courtId) throws CourtBeReservedException {
		Court court=courtRepository.findById(courtId).orElseThrow(NoSuchElementException::new); 
		if (!court.mayBeReserved()) {
			court.setMayBeReserved(true);
			courtRepository.save(court);
		} else {
			throw new CourtBeReservedException("Forbidden operation: the court could already be reserved!");
		}
	}
	
	public void delete(Long id) {
		if(courtRepository.findById(id).isPresent()) {
			courtRepository.delete(courtRepository.findById(id).get());
		} else {
			throw new NoSuchElementException();
		}
		
	}
	
	public Set<CourtDTO> findAll() {
		return courtMapper.convertEntityToDTO(courtRepository.findAll().stream().collect(Collectors.toSet()));
	}
	
	public Set<CourtDTO> findAllWithMatchesAndTheirSlotsByDate(ClubManagementMessageDTO inputMessage) {
		return courtMapper.convertEntityToDTO(courtRepository.findAllWithMatchesAndTheirSlotsByDate(
				LocalDate.parse(inputMessage.getDate()), Long.parseLong(inputMessage.getClubId())).get()
				.stream().filter(court->court.mayBeReserved()).collect(Collectors.toSet()));
	}
	
	public Set<CourtDTO> verifyAvailability(InputVerifyAvailabilityMessageDTO inputMessage) 
		throws VerifyAvailabilityException {
		Set<Slot> requiredSlots=myUtil.convertInputVerifyAvailabilityMessageDTOToSlots(inputMessage);
		Player player=playerRepository.findByIdWithClub(Long.parseLong(inputMessage.getPlayerId()))
			.orElseThrow(NoSuchElementException::new);
		Club club=player.getClub();
		ClubManagementMessageDTO clubManagementMessageDTO = 
			new ClubManagementMessageDTO(inputMessage.getDate(),club.getId().toString());
		Set<CourtDTO> result=this.findAllWithMatchesAndTheirSlotsByDate(clubManagementMessageDTO);
		// scrivere un metodo booleano in court che verifica che non ci siano sovrapposizioni tra match previsti nel court
	}
	
}
