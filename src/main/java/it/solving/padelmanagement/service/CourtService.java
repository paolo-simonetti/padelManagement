package it.solving.padelmanagement.service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.CourtDTO;
import it.solving.padelmanagement.dto.message.clubmanagement.ClubManagementMessageDTO;
import it.solving.padelmanagement.dto.message.createpadelmatch.InputVerifyAvailabilityMessageDTO;
import it.solving.padelmanagement.dto.message.insert.CourtInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.CourtUpdateMessageDTO;
import it.solving.padelmanagement.exception.ForbiddenOperationException;
import it.solving.padelmanagement.exception.VerifyAvailabilityException;
import it.solving.padelmanagement.mapper.CourtMapper;
import it.solving.padelmanagement.model.Admin;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.Court;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.model.Slot;
import it.solving.padelmanagement.repository.AdminRepository;
import it.solving.padelmanagement.repository.ClubRepository;
import it.solving.padelmanagement.repository.CourtRepository;
import it.solving.padelmanagement.repository.MatchRepository;
import it.solving.padelmanagement.repository.PlayerRepository;
import it.solving.padelmanagement.securitymodel.AdminPrincipal;
import it.solving.padelmanagement.securitymodel.PlayerPrincipal;
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
	private AdminRepository adminRepository;
	
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
		// Recupero lo user dal security context holder
		Admin admin=((AdminPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAdmin();
		// dallo user ricavo il club, di cui metto l'id nell'input dell'istruzione successiva
		Club club=adminRepository.findByIdWithClub(admin.getId()).get().getClub();
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
	
	public void cannotBeReserved(Long courtId) throws ForbiddenOperationException {
		Court court=courtRepository.findByIdWithMatches(courtId).orElseThrow(NoSuchElementException::new);
		// Recupero l'admin dal security context holder
		Admin admin=((AdminPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
			.getAdmin();
		// Prendo dal contesto di persistenza tutti i campi del circolo amministrato dall'admin, 
		// e verifico che ci sia anche il campo in input
		if(!courtRepository.findAllByClub_Admin(admin).stream().map(c->c.getId()).collect(Collectors.toSet())
			.contains(courtId)) {
			throw new ForbiddenOperationException("This court does not belong to the admin's club!");
		}
		if (court.getMatches()!=null && court.getMatches().size()>0) {
			throw new ForbiddenOperationException("There are scheduled matches for that court!");
		} 
		
		if (court.mayBeReserved()) {
			court.setMayBeReserved(false);
			courtRepository.save(court);
		} else {
			throw new ForbiddenOperationException("The court couldn't already be reserved!");
		}
	}
	
	public void canBeReserved(Long courtId) throws ForbiddenOperationException {
		Court court=courtRepository.findById(courtId).orElseThrow(NoSuchElementException::new);
		// Recupero l'admin dal security context holder
		Admin admin=((AdminPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
			.getAdmin();
		// Prendo dal contesto di persistenza tutti i campi del circolo amministrato dall'admin, 
		// e verifico che ci sia anche il campo in input
		if(!courtRepository.findAllByClub_Admin(admin).stream().map(c->c.getId()).collect(Collectors.toSet())
			.contains(courtId)) {
			throw new ForbiddenOperationException("This court does not belong to the admin's club!");
		}
		if (!court.mayBeReserved()) {
			court.setMayBeReserved(true);
			courtRepository.save(court);
		} else {
			throw new ForbiddenOperationException("The court could already be reserved!");
		}
	}
	
	public void delete(Long id) {
		if(courtRepository.findById(id).isPresent()) {
			courtRepository.delete(courtRepository.findById(id).get());
		} else {
			throw new NoSuchElementException();
		}
		
	}
	
	public Set<CourtDTO> findAllByClub() {
		// Recupero lo user dal security context holder
		Admin admin=((AdminPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAdmin();
		// dallo user ricavo il club, di cui metto l'id nell'input dell'istruzione successiva
		admin=adminRepository.findByIdWithClub(admin.getId()).get();
		return courtMapper.convertEntityToDTO(courtRepository.findAllByClub(admin.getClub()).stream().collect(Collectors.toSet()));
	}
	
	public Set<CourtDTO> findAllWithMatchesAndTheirSlotsByDate(ClubManagementMessageDTO inputMessage) {
		// Recupero lo user dal security context holder
		Admin admin=((AdminPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAdmin();
		// dallo user ricavo il club, di cui metto l'id nell'input dell'istruzione successiva
		admin=adminRepository.findByIdWithClub(admin.getId()).get();
		
		return courtMapper.convertEntityToDTO(courtRepository.findAllWithMatchesAndTheirSlotsByDate(
				LocalDate.parse(inputMessage.getDate()), admin.getClub().getId())
				.stream().filter(court->court.mayBeReserved()).collect(Collectors.toSet()));
	}
	
	public Set<CourtDTO> verifyAvailability(InputVerifyAvailabilityMessageDTO inputMessage) 
		throws VerifyAvailabilityException {
		//Recupero il player dal Security context holder
		Player player=((PlayerPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
			.getPlayer();
				
		Set<Slot> requiredSlots=myUtil.convertInputVerifyAvailabilityMessageDTOToSlots(inputMessage);
		player=playerRepository.findByIdWithClub(player.getId())
			.orElseThrow(NoSuchElementException::new);
		Club club=MyUtil.initializeAndUnproxy(player.getClub());
		
		// Recupero i campi con le rispettive informazioni su partite e slot occupati
		Set<Court> courts=courtRepository.findAllWithMatchesAndTheirSlotsByDate(
				LocalDate.parse(inputMessage.getDate()), club.getId());
		if(courts==null ||courts.size()==0) {
			// Se sono qui, vuol dire che, in quella data, non ci sono campi occupati --> restituisco l'intera lista
			// dei campi presenti nel circolo
			return courtMapper.convertEntityToDTO(clubRepository.findByIdWithCourts(club.getId()).get().getCourts());
		}
				
		// filtro i campi, eliminando quelli dismessi dall'admin e quelli occupati nell'orario richiesto
		courts=courts.stream().filter(court->court.mayBeReserved()&&!court.areThereMatchesInTheSlots(requiredSlots))
			.collect(Collectors.toSet());
		
		if (courts==null||courts.size()==0) {
			// se sono qui, vuol dire che non ci sono campi disponibili
			return null;
		}
		
		// filtro i campi, eliminando quelli non ottimali
		Set<Court> optimalCourts=courts.stream().filter(court->
			myUtil.isTheCourtOptimalForTheRequiredSlots(court,requiredSlots)).collect(Collectors.toSet());
		
		if (optimalCourts==null||optimalCourts.size()==0) {
			// se sono qui, vuol dire che non ci sono campi ottimi, quindi ritorno l'elenco di quelli trovati prima
			return courtMapper.convertEntityToDTO(courts);
		}
		
		return courtMapper.convertEntityToDTO(optimalCourts);
	}
	
}
