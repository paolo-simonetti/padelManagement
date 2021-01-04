package it.solving.padelmanagement.validator;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solving.padelmanagement.dto.message.createpadelmatch.InputDeleteMessageDTO;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.repository.MatchRepository;
import it.solving.padelmanagement.repository.PlayerRepository;
import it.solving.padelmanagement.securitymodel.PlayerPrincipal;
import it.solving.padelmanagement.util.MyUtil;

@Component
public class MatchDeleteValidator implements Validator {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private MyUtil myUtil;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return InputDeleteMessageDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		InputDeleteMessageDTO inputMessage = (InputDeleteMessageDTO) target;
		//Recupero il player dal Security context holder
		Player creator=((PlayerPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPlayer();
				
		// Controllo che il match esista
		PadelMatch match=matchRepository.findByIdWithCreatorAndSlots(Long.parseLong(inputMessage.getMatchId()))
			.orElseThrow(NoSuchElementException::new);
		
		// Controllo che manchi più di mezz'ora all'inizio e che la partita sia ancora aperta
		if (match.getMissingPlayers()==0||myUtil.lessThanHalfAnHourLeft(match)) {
			errors.rejectValue("matchId","matchCannotBeDeleted","Match can't be deleted now");
		}
		
		// Controllo che il creatore della partita coincida con quello indicato in input
		if (match.getCreator()!=creator) {
			errors.rejectValue("creatorId","forbiddenDeletion", "The match was not created by this user");
		}
	}

}
