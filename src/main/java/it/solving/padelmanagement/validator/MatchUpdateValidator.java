package it.solving.padelmanagement.validator;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solving.padelmanagement.dto.message.createpadelmatch.InputValidateAndUpdateInputMessageDTO;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.repository.MatchRepository;
import it.solving.padelmanagement.util.MyUtil;

@Component
public class MatchUpdateValidator implements Validator {

	@Autowired
	private MatchInsertValidator matchInsertValidator;
	
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private MyUtil myUtil;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return InputValidateAndUpdateInputMessageDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		InputValidateAndUpdateInputMessageDTO inputMessage=(InputValidateAndUpdateInputMessageDTO) target;
		// verifico che il match esista
		PadelMatch match=matchRepository.findByIdWithCreatorAndSlots(Long.parseLong(inputMessage.getMatchId()))
			.orElseThrow(NoSuchElementException::new);		
		// verifico che il match sia stato creato dall'id del player indicato come creatore
		Long idMatchCreator=match.getCreator().getId();
		if (idMatchCreator!=Long.parseLong(inputMessage.getInputValidateAndInsertInputMessageDTO()
			.getInputVerifyAvailabilityMessageDTO().getPlayerId())) {
			errors.rejectValue("matchId","invalidMatchChoice","The match was not created by this player!");
		}
		
		// Se la partita non è in stato di call-for-action o manca meno di mezz'ora al suo inizio, impedisco la modifica
		if (match.getMissingPlayers()==0||myUtil.lessThanHalfAnHourLeft(match)) {
			errors.rejectValue("matchId", "matchCannotBeModifiedNow", "The match can't be modified now");
		}
		
		matchInsertValidator.validate(inputMessage,errors);
	}

}
