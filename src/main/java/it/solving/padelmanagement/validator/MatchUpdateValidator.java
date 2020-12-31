package it.solving.padelmanagement.validator;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solving.padelmanagement.dto.message.createpadelmatch.InputValidateAndUpdateInputMessageDTO;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.repository.MatchRepository;
import it.solving.padelmanagement.repository.PlayerRepository;
import it.solving.padelmanagement.util.MyUtil;

@Component
public class MatchUpdateValidator implements Validator {

	@Autowired
	private MatchInsertValidator matchInsertValidator;
	
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private MyUtil myUtil;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return InputValidateAndUpdateInputMessageDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		InputValidateAndUpdateInputMessageDTO inputMessage=(InputValidateAndUpdateInputMessageDTO) target;
		// Ripeto la validazione già fatta per l'insert
		matchInsertValidator.validate(inputMessage,errors);

		// verifico che il match esista
		PadelMatch match=matchRepository.findByIdWithCreatorAndOtherPlayersAndSlots(Long.parseLong(
				inputMessage.getMatchId())).orElseThrow(NoSuchElementException::new);		
		// verifico che il match sia stato creato dall'id del player indicato come creatore
		Long idMatchCreator=match.getCreator().getId();
		if (idMatchCreator!=Long.parseLong(inputMessage.getPlayerId())) {
			errors.rejectValue("matchId","invalidMatchChoice","The match was not created by this player!");
		}
		
		/* Impedisco la modifica nei seguenti casi:
		 * 1. la partita non è (più) una call-for-action;
		 * 2. manca meno di mezz'ora all'inizio della partita;
		 * 3. altri giocatori si sono uniti alla call-for-action e sono già impegnati nel nuovo orario
		 * 4. il creatore stesso è già impegnato in altre partite nel nuovo orario
		 */
		
		// 1 e 2
		if (match.getMissingPlayers()==0||myUtil.lessThanHalfAnHourLeft(match)) {
			errors.rejectValue("matchId", "matchCannotBeModifiedNow", "The match can't be modified now");
		}
		
		// 3
		Player creatorWithAllSlotsInWhichTheyAreBusy=playerRepository.findByIdWithAllMatchesAndTheirSlots(
				match.getCreator().getId()).get();
		if (match.getOtherPlayers()!=null && match.getOtherPlayers().size()>0) {
			Set<Player> otherPlayersWithAllSlotsInWhichTheyAreBusy=match.getOtherPlayers().stream().map(p->
			playerRepository.findByIdWithAllMatchesAndTheirSlots(p.getId()).get()).collect(Collectors.toSet());
			for (Player player:otherPlayersWithAllSlotsInWhichTheyAreBusy) {
				if (myUtil.thePlayerIsPlayingSomewhereElseAtThatTime(player,match)) {
					errors.rejectValue("durationHour","otherPlayersAlreadyBusy","One of the other players is already busy at that time!");
				}			
			}
			
		}
		
		// 4
		if (myUtil.thePlayerIsPlayingSomewhereElseAtThatTime(creatorWithAllSlotsInWhichTheyAreBusy,match)) {
			errors.rejectValue("durationHour","creatorAlreadyBusy","The creator is already busy at that time!");
		}
	}

}
