package it.solving.padelmanagement.validator;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solving.padelmanagement.dto.message.callforactions.InputJoinCallForActionMessageDTO;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.repository.MatchRepository;
import it.solving.padelmanagement.repository.PlayerRepository;
import it.solving.padelmanagement.service.PlayerService;
import it.solving.padelmanagement.util.MyUtil;

@Component
public class InputJoinCallForActionValidator implements Validator {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private MyUtil myUtil;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return InputJoinCallForActionMessageDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		InputJoinCallForActionMessageDTO inputMessage=(InputJoinCallForActionMessageDTO) target;
		// Controllo che il player esista
		Player player = playerRepository.findByIdWithAllMatchesAndTheirSlots(Long.parseLong(
				inputMessage.getPlayerId())).orElseThrow(NoSuchElementException::new);
		
		// Controllo che il match esista
		PadelMatch match = matchRepository.findByIdWithCreatorAndOtherPlayersAndSlots(
				Long.parseLong(inputMessage.getMatchId())).orElseThrow(NoSuchElementException::new);
		// Controllo che il match non sia stato creato dal player
		if (player.getId()==match.getCreator().getId()) {
			errors.rejectValue("playerId","playerCreatedMatch","The player can't join in a call-for-action they created");
		}
		
		// Controllo che il player non si fosse già unito a questa call-for-action
		if (match.getOtherPlayers().stream().map(p->p.getId()).collect(Collectors.toSet()).contains(player.getId())) {
			errors.rejectValue("matchId","playerAlreadyJoinedIn", "The player has already joined in the match");
		}
		
		// Controllo che la partita sia ancora in stato di call-for-action 
		if (match.getMissingPlayers()==0 || myUtil.lessThanHalfAnHourLeft(match)) {
			errors.rejectValue("matchId","matchCannotBeJoinedInAnyLonger", "The match cannot be joined in any longer");
		}
		
		// Controllo che il player non sia già impegnato a quell'ora
		if (myUtil.thePlayerIsPlayingSomewhereElseAtThatTime(player,match)) {
			errors.rejectValue("playerId","playerIsBusy", "The player has another match on that date in that time!");
		}
		
		// Controllo che la partita si svolga nello stesso club a cui è iscritto il giocatore
		if (!playerService.theMatchIsHeldInTheSameClub(player.getId(),match.getId())) {
			errors.rejectValue("playerId","matchInAnotherClub", "The match is held in a different club from the one the player is member of!");
		}
		
		// Controllo che il player abbia pagato tutte le partite che ha creato
		if(!playerService.thePlayerHasPayedForEveryMatch(player.getId())) {
			errors.rejectValue("playerId","expectedPayment","The player created some matches for which he has not payed yet");
		}
	}

}
