package it.solving.padelmanagement.service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.MatchDTO;
import it.solving.padelmanagement.dto.message.callforactions.InputUpdateMissingPlayersMessageDTO;
import it.solving.padelmanagement.dto.message.createpadelmatch.InputValidateAndInsertInputMessageDTO;
import it.solving.padelmanagement.dto.message.createpadelmatch.InputValidateAndUpdateInputMessageDTO;
import it.solving.padelmanagement.exception.EmailException;
import it.solving.padelmanagement.exception.ForbiddenOperationException;
import it.solving.padelmanagement.exception.VerifyAvailabilityException;
import it.solving.padelmanagement.mapper.MatchMapper;
import it.solving.padelmanagement.model.Admin;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.Court;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.model.Slot;
import it.solving.padelmanagement.repository.CourtRepository;
import it.solving.padelmanagement.repository.MatchRepository;
import it.solving.padelmanagement.repository.PlayerRepository;
import it.solving.padelmanagement.repository.SlotRepository;
import it.solving.padelmanagement.securitymodel.AdminPrincipal;
import it.solving.padelmanagement.securitymodel.PlayerPrincipal;
import it.solving.padelmanagement.util.MyUtil;

@Service
public class MatchService {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired 
	private MatchMapper matchMapper;
	
	@Autowired
	private PlayerRepository playerRepository;
		
	@Autowired
	private CourtRepository courtRepository;
	
	@Autowired
	private SlotRepository slotRepository;
	
	@Autowired
	private MyUtil myUtil;
	
	public void insert(InputValidateAndInsertInputMessageDTO inputMessage) throws VerifyAvailabilityException, EmailException {
		PadelMatch match=matchMapper.convertInputValidateAndInsertInputMessageDTOToPadelMatch(inputMessage);
		Set<Slot> slots=myUtil.convertInputVerifyAvailabilityMessageDTOToSlots(inputMessage);
		//Recupero il player dal Security context holder
		Player creator=((PlayerPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPlayer();
	
		creator=playerRepository.findByIdWithAllMatches(creator.getId()).get();
		matchRepository.save(match);
		
		match.setCreator(creator);
		creator.addToMatches(match);
		playerRepository.save(creator);
		
		Court court=courtRepository.findByIdWithMatchesAndClub(Long.parseLong(inputMessage.getCourtId())).get();
		court.addToMatches(match);
		match.setCourt(court);
		match.setSlots(slots);	
		courtRepository.save(court);
		
		slots.stream().forEach(slot->{
			slot.addToMatches(match);
			slotRepository.save(slot);
		});
		
		matchRepository.save(match);
		
		if (match.getMissingPlayers()==0) {
			emailService.sendMatchSummary(creator.getMailAddress(),match,court.getClub());
		}
	}
	
	public void update (InputValidateAndUpdateInputMessageDTO inputMessage) throws VerifyAvailabilityException {
		//Recupero il player dal Security context holder
		Player creator=((PlayerPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPlayer();
				
		PadelMatch match=matchMapper.convertInputValidateAndUpdateInputMessageDTOToPadelMatch(inputMessage);
		// Nell'update non passo il numero di giocatori mancanti, quindi lo recupero dal db qui
		match.setMissingPlayers(matchRepository.findById(match.getId()).get().getMissingPlayers());
		// rispetto all'insert, devo trasferire l'informazione sullo stato di pagamento della partita
		match.setPayed(matchRepository.findById(match.getId()).get().isPayed());
		
		Set<Slot> slots=myUtil.convertInputVerifyAvailabilityMessageDTOToSlots(inputMessage);
		creator=playerRepository.findByIdWithAllMatches(creator.getId()).get();
		matchRepository.save(match);
		
		match.setCreator(creator);
		creator.addToMatches(match);
		playerRepository.save(creator);
		
		Court court=courtRepository.findByIdWithMatches(Long.parseLong(inputMessage.getCourtId())).get();
		court.addToMatches(match);
		match.setCourt(court);
		match.setSlots(slots);	
		courtRepository.save(court);
		
		slots.stream().forEach(slot->{
			slot.addToMatches(match);
			slotRepository.save(slot);
		});
		
		matchRepository.save(match);
		
	}
	
	public void updateMissingPlayers(InputUpdateMissingPlayersMessageDTO inputMessage) throws EmailException {
		PadelMatch match=matchRepository.findByIdWithCompleteInfo(Long.parseLong(inputMessage.getMatchId())).get();
		match.setMissingPlayers(Integer.parseInt(inputMessage.getMissingPlayers()));
		matchRepository.save(match);
		if (match.getMissingPlayers()==0) {
			emailService.sendMatchSummary(match.getCreator().getMailAddress(),match,match.getCourt().getClub());
		}
	}
	
	public void delete (Long id) {
		if (matchRepository.findByIdWithCompleteInfo(id).isPresent()) {
			PadelMatch match=matchRepository.findByIdWithCompleteInfo(id).get();
			
			// Elimino la partita dall'elenco di quelle a cui partecipano i giocatori
			if (match.getOtherPlayers()!=null && match.getOtherPlayers().size()>0) {
				match.getOtherPlayers().stream().forEach(player-> {
					player.removeFromMatchesJoined(match);
					match.removeFromOtherPlayers(player);
					playerRepository.save(player);
				});				
			}
			
			// Elimino la partita dall'elenco di quelle create dal creatore
			Player creator=match.getCreator();
			creator.removeFromMatches(match);
			match.setCreator(null);
			playerRepository.save(creator);
			
			// Libero gli slot
			Set<Slot> slots=match.getSlots();
			match.getSlots().clear();
			slots.stream().forEach(slot->{
				slot.removeFromMatches(match);
				slotRepository.save(slot);
			});
			
			// Libero il campo
			Court court=match.getCourt();
			court.removeFromMatches(match);
			match.setCourt(null);
			courtRepository.save(court);
			
			matchRepository.save(match);
			matchRepository.delete(match);
		}
	}
	
	public MatchDTO findById(Long id) {
		if (matchRepository.findById(id).isPresent()) {
			return matchMapper.convertEntityToDTO(matchRepository.findById(id).get());			
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public Set<MatchDTO> findAll() {
		return matchMapper.convertEntityToDTO(matchRepository.findAll().stream().collect(Collectors.toSet()));
	}
	
	public Set<MatchDTO> findAllByDate(LocalDate date) {
		//Recupero il player dal Security context holder
		Player player=((PlayerPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPlayer();
		// Recupero dal contesto di persistenza il club in cui gioca il player
		player=playerRepository.findByIdWithClub(player.getId()).get();
		Club club=player.getClub();
		if(matchRepository.findAllByDateAndClub(date,club)==null || matchRepository.findAllByDateAndClub(date,club).size()==0) {
			return null;
		}
		return matchMapper.convertEntityToDTO(matchRepository.findAllByDateAndClub(date,club).stream().collect(Collectors.toSet()));
	}
	
	public Set<MatchDTO> findAllOthersCallForActions(Long idPlayer) {
		if (!playerRepository.findById(idPlayer).isPresent()) {
			throw new NoSuchElementException();
		}
		// Recupero tutti i match creati da altri giocatori nello stesso club del richiedente
		Set<PadelMatch> matches=matchRepository.findAllOthersCallForActionsInTheSameClubAsThePlayersOne(idPlayer);
		
		// Li filtro per avere solo quelli a cui manchino giocatori, e il cui creatore abbia livello di gioco 
		// inferiore rispetto al richiedente
		matches=matches.stream().filter(match->match.getMissingPlayers()>0 && match.getCreator().getLevel() <=
				playerRepository.findById(idPlayer).get().getLevel()).collect(Collectors.toSet());
		
		return matchMapper.convertEntityToDTO(matches);
	}
	
	public void writeDownPayment(Long matchId) throws ForbiddenOperationException {
		// Recupero l'admin dal security context holder
		Admin admin=((AdminPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
			.getAdmin();
		// Recupero il match dal contesto di persistenza
		PadelMatch match=matchRepository.findByIdWithCourtClubAndAdmin(matchId).orElseThrow(NoSuchElementException::new);
		// verifico che il match si svolga nel club amministrato dall'admin
		if (match.getCourt().getClub().getAdmin()!=admin) {
			throw new ForbiddenOperationException("This court does not belong to the admin's club!");
		}
		
		if (!match.isPayed()) {
			match.setPayed(true);
			matchRepository.save(match);
		} else {
			throw new ForbiddenOperationException("The match was already payed!");
		}
		
	}
	
}
