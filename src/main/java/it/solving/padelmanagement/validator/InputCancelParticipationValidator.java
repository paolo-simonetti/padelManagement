package it.solving.padelmanagement.validator;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solving.padelmanagement.dto.message.callforactions.InputCancelParticipationMessageDTO;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.repository.MatchRepository;
import it.solving.padelmanagement.repository.PlayerRepository;

@Component
public class InputCancelParticipationValidator implements Validator {

	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return InputCancelParticipationMessageDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		InputCancelParticipationMessageDTO inputMessage=(InputCancelParticipationMessageDTO) target;
		// Controllo che il player esista
		Player player=playerRepository.findById(Long.parseLong(inputMessage.getPlayerId()))
				.orElseThrow(NoSuchElementException::new);
		
		// Controllo che il match esista
		PadelMatch match=matchRepository.findByIdWithOtherPlayers(Long.parseLong(inputMessage.getMatchId()))
				.orElseThrow(NoSuchElementException::new);
		
		// Controllo che il match sia una call-for-action a cui il player ha effettivamente risposto
		if(!match.getOtherPlayers().stream().map(p->p.getId()).collect(Collectors.toSet()).contains(player.getId())) {
			errors.rejectValue("matchId","playerNeverJoinedMatch","This player had never joined in the call-for-action in the first moment!");
		}
	}

}
