package it.solving.padelmanagement.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solving.padelmanagement.dto.message.member.InputUpdateMemberMessageDTO;
import it.solving.padelmanagement.repository.PlayerRepository;

@Component
public class InputUpdateMemberValidator implements Validator {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return InputUpdateMemberMessageDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		InputUpdateMemberMessageDTO inputMessage=(InputUpdateMemberMessageDTO) target;
		
		// Controllo che la mail e lo username non siano già in uso
		if (playerRepository.findAllByMailAddress(inputMessage.getMailAddress())!=null && 
				playerRepository.findAllByMailAddress(inputMessage.getMailAddress()).size()>0) {
			errors.rejectValue("mailAddress","mailAddressAlreadyInUse","The mail address is already in use!");
		}
		
		if (playerRepository.findAllByUsername(inputMessage.getUsername())!=null && 
				playerRepository.findAllByUsername(inputMessage.getUsername()).size()>0) {
			errors.rejectValue("username","usernameAlreadyInUse","The username is already in use!");
		}
	}

}
