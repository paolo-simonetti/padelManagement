package it.solving.padelmanagement.validator;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solving.padelmanagement.dto.message.callforactions.InputUpdateMissingPlayersMessageDTO;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.repository.MatchRepository;
import it.solving.padelmanagement.repository.PlayerRepository;
import it.solving.padelmanagement.securitymodel.PlayerPrincipal;
import it.solving.padelmanagement.util.MyUtil;

@Component
public class InputUpdateMissingPlayersValidator implements Validator {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private MyUtil myUtil;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return InputUpdateMissingPlayersMessageDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		InputUpdateMissingPlayersMessageDTO inputMessage=(InputUpdateMissingPlayersMessageDTO) target;
		
		//Recupero il player dal Security context holder
		Player creator=((PlayerPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPlayer();
		
		// Controllo che il match esista
		PadelMatch match=matchRepository.findByIdWithCreatorAndOtherPlayersAndSlots(
				Long.parseLong(inputMessage.getMatchId())).orElseThrow(NoSuchElementException::new);
		
		// Controllo che il creatore sia effettivamente colui che ha creato il match
		if(match.getCreator().getId()!=creator.getId()) {
			errors.rejectValue("creatorId","wrongCreator","This player has not created the match!");
		}
		
		// Controllo che i giocatori totali non siano più di 4
		if(match.getOtherPlayers()!=null && match.getOtherPlayers().size()+
				Integer.parseInt(inputMessage.getMissingPlayers())>4) {
			errors.rejectValue("missingPlayers","tooManyPlayers","The total number of players cannot exceed 4");
		}
		
		// Controllo che la partita sia effettivamente una call-for-action e che manchi almeno mezz'ora alla partita
		if (match.getMissingPlayers()==0||myUtil.lessThanHalfAnHourLeft(match)) {
			errors.rejectValue("matchId","notAnUpdatableCallForAction","This match cannot be updated any longer");
		}
	}

}
