package it.solving.padelmanagement.service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.NoticeDTO;
import it.solving.padelmanagement.dto.PlayerDTO;
import it.solving.padelmanagement.dto.message.callforactions.InputCancelParticipationMessageDTO;
import it.solving.padelmanagement.dto.message.callforactions.InputJoinCallForActionMessageDTO;
import it.solving.padelmanagement.dto.message.insert.PlayerInsertMessageDTO;
import it.solving.padelmanagement.dto.message.member.InputUpdateMemberMessageDTO;
import it.solving.padelmanagement.dto.message.update.PlayerUpdateMessageDTO;
import it.solving.padelmanagement.exception.AbandonClubException;
import it.solving.padelmanagement.exception.EmailException;
import it.solving.padelmanagement.exception.NonAdmissibleActionOnMatchNow;
import it.solving.padelmanagement.mapper.NoticeMapper;
import it.solving.padelmanagement.mapper.PlayerMapper;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.repository.ClubRepository;
import it.solving.padelmanagement.repository.MatchRepository;
import it.solving.padelmanagement.repository.NoticeRepository;
import it.solving.padelmanagement.repository.PlayerRepository;
import it.solving.padelmanagement.securitymodel.PlayerPrincipal;

@Service
public class PlayerService {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private PlayerMapper playerMapper;
	
	@Autowired
	private ClubRepository clubRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	public PlayerDTO findById(Long id) {
		return playerMapper.convertEntityToDTO(playerRepository.findById(id).get());
	}
	
	public PlayerDTO findByIdWithClub(Long id) {
		return playerMapper.convertEntityToDTO(playerRepository.findByIdWithClub(id)
			.orElseThrow(NoSuchElementException::new));
	}
	
	public Set<PlayerDTO> findAll() {
		return playerMapper.convertEntityToDTO(playerRepository.findAll().stream().collect(Collectors.toSet()));
	}
	
	public void insert(PlayerInsertMessageDTO playerInsertMessageDTO, Long idUserToBeDeleted) {
		Player player = playerMapper.convertInsertMessageDTOToEntity(playerInsertMessageDTO);
		Club club = clubRepository.findById(Long.parseLong(playerInsertMessageDTO.getClubId()))
			.orElseThrow(NoSuchElementException::new);
		// Rimuovo il player dalla tabella User
		userService.delete(idUserToBeDeleted);
		// Assegno il player al club e salvo tutto nel contesto di persistenza
		player.setClub(club);
		club.addToPlayers(player);
		playerRepository.save(player);
		clubRepository.save(club);
	}
	
	public void update(PlayerUpdateMessageDTO playerUpdateMessageDTO) {
		if (!playerRepository.findById(Long.parseLong(playerUpdateMessageDTO.getId())).isPresent()) {
			throw new NoSuchElementException();
		}
		
		Player player = playerMapper.convertUpdateMessageDTOToEntity(playerUpdateMessageDTO);
		
		// Aggiorno i match creati
		if (playerUpdateMessageDTO.getMatchesIds()!=null && playerUpdateMessageDTO.getMatchesIds().size()>0) {
			Set<PadelMatch> matches=playerUpdateMessageDTO.getMatchesIds().stream().map(stringId->
			matchRepository.findById(Long.parseLong(stringId)).get()).collect(Collectors.toSet());
			player.setMatches(matches);
			matches.stream().forEach(match-> {
				match.setCreator(player);
				matchRepository.save(match);
			});
		}
		
		// Aggiorno i match a cui partecipa
		if(playerUpdateMessageDTO.getMatchesJoinedIds()!=null && 
				playerUpdateMessageDTO.getMatchesJoinedIds().size()>0) {
			Set<PadelMatch> matchesJoined=playerUpdateMessageDTO.getMatchesJoinedIds().stream().map(stringId->			
				matchRepository.findById(Long.parseLong(stringId)).get()).collect(Collectors.toSet());
			player.setMatchesJoined(matchesJoined);
			matchesJoined.stream().forEach(match-> {
				match.addToOtherPlayers(player);
				matchRepository.save(match);
			});
		}
		
		playerRepository.save(player);
		
	}
	
	public void updatePersonalData(InputUpdateMemberMessageDTO inputMessage) {
		Player newPlayer=playerMapper.convertInputUpdateMemberMessageDTOToPlayer(inputMessage);
		//Recupero il player dal Security context holder
		Player player=((PlayerPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPlayer();
		// Setto nel nuovo player l'id del player recuperato dal security context holder
		newPlayer.setId(player.getId());
		// Recupero le informazioni sul club e sulle partite
		Player oldPlayer=playerRepository.findByIdWithAllMatchesAndClub(newPlayer.getId()).get();
		// Trascrivo i match creati
		newPlayer.setMatches(oldPlayer.getMatches());
		// Trascrivo le call for actions a cui partecipa
		newPlayer.setMatchesJoined(oldPlayer.getMatchesJoined());
		// Trascrivo il club di appartenenza
		newPlayer.setClub(oldPlayer.getClub());
		playerRepository.save(newPlayer);
	}
	
	public void delete (Long id) {
		if (playerRepository.findById(id).isPresent()) {
			Player player = playerRepository.findByIdWithAllMatches(id).get();
			Set<PadelMatch> matches=player.getMatches();
			Set<PadelMatch> matchesJoined=player.getMatchesJoined();
			if (matches!=null && matches.size()>0) {
				player.setMatches(null);
				matches.stream().forEach(match-> {
					match.setCreator(null);
					matchRepository.save(match);
					playerRepository.save(player);
				});
			}
			
			if (matchesJoined!=null && matchesJoined.size()>0) {
				player.setMatchesJoined(null);
				matches.stream().forEach(match-> {
					match.removeFromOtherPlayers(player);
					matchRepository.save(match);
					playerRepository.save(player);
				});
			}
			
			playerRepository.delete(player);
		} else {
			throw new NoSuchElementException();
		}
	} 
	
	public String joinCallForAction(InputJoinCallForActionMessageDTO inputMessage) throws EmailException {
		PadelMatch match=matchRepository.findByIdWithOtherPlayersAndCreatorAndCourtAndClub(Long.parseLong(inputMessage.getMatchId())).get();
		Player player=playerRepository.findByIdWithMatchesJoined(Long.parseLong(inputMessage.getPlayerId())).get();
		player.addToMatchesJoined(match);
		match.addToOtherPlayers(player);
		match.decrementMissingPlayers();
		matchRepository.save(match);
		playerRepository.save(player);
		if (match.getMissingPlayers()==0) {
			emailService.sendMatchSummary(match.getCreator().getMailAddress(),match,match.getCourt().getClub());
			return "The call-for-action was successful: the goal of 4 players was reached!";
		} else {
			return "The player has successfully joined in the call-for-action";
		}
	}
	
	public void cancelParticipation(InputCancelParticipationMessageDTO inputMessage) throws NonAdmissibleActionOnMatchNow {
		Player player = playerRepository.findByIdWithMatchesJoined(Long.parseLong(inputMessage.getPlayerId())).get();
		PadelMatch match=matchRepository.findByIdWithOtherPlayers(Long.parseLong(inputMessage.getMatchId())).get();
		player.removeFromMatchesJoined(match);
		match.removeFromOtherPlayers(player);
		match.incrementMissingPlayers();
		playerRepository.save(player);
		matchRepository.save(match);
	}
	
	public boolean theMatchIsHeldInTheSameClub(Long idPlayer,Long idMatch) {
		Player player=playerRepository.findByIdWithClub(idPlayer).orElseThrow(NoSuchElementException::new);
		PadelMatch match=matchRepository.findByIdWithCourtAndClub(idMatch).orElseThrow(NoSuchElementException::new);
		return player.getClub().getId()==match.getCourt().getClub().getId();
	}
	
	public boolean thePlayerHasPayedForEveryMatch(Long idPlayer) {
		Player player=playerRepository.findByIdWithMatchesCreated(idPlayer).orElseThrow(NoSuchElementException::new);
		return player.getMatches().stream().noneMatch(match->!match.isPayed());
	}
	
	public void abandonClub() throws AbandonClubException {
		//Recupero il player dal Security context holder
		Player player=((PlayerPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPlayer();
				
		// controllo che il giocatore abbia pagato tutti i match creati, prima di svignarsela
		if (!thePlayerHasPayedForEveryMatch(player.getId())) {
			throw new AbandonClubException("The player has to pay for all the matches he created, before leaving the club!");
		}
		
		// Controllo che il giocatore non abbia call-for-actions né partite fissate per il futuro
		player = playerRepository.findByIdWithAllMatchesAndClub(player.getId()).orElseThrow(NoSuchElementException::new);
		if (player.getMatches()!=null && player.getMatches().size()>0 && player.getMatches().stream()
				.filter(match->match.getDate().compareTo(LocalDate.now())>=0).findFirst().orElse(null)==null) {
			throw new AbandonClubException("The player has created at least one match to play today or in the future");
		}
		if (player.getMatchesJoined()!=null && player.getMatchesJoined().size()>0 && player.getMatchesJoined().stream()
				.filter(match->match.getDate().compareTo(LocalDate.now())>=0).findFirst().orElse(null)==null) {
			throw new AbandonClubException("The player has created at least one call-for-action to play today or in the future");
		}
		
		// mi occupo delle operazioni di business
		player.setClub(null);
		playerRepository.save(player);
		playerRepository.delete(player);
	}
	
	public Set<NoticeDTO> getClubNotices() {
		//Recupero il player dal Security context holder
		Player player=((PlayerPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPlayer();				
		return noticeMapper.convertEntityToDTO(noticeRepository.findAllByClubPlayerId(player.getId()));
	}
	
}
