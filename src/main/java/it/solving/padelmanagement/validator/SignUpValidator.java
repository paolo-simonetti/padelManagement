package it.solving.padelmanagement.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solving.padelmanagement.dto.message.insert.UserInsertMessageDTO;
import it.solving.padelmanagement.service.UserService;

@Component
public class SignUpValidator implements Validator {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UserInsertMessageDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserInsertMessageDTO user = (UserInsertMessageDTO) target;
		if (userService.userAlreadyExists(user.getUsername())) {
			errors.rejectValue("username", "usernameAlreadyInUse", "The username is already in use");
		}
		
		if (userService.userAlreadyExists(user.getMailAddress())) {
			errors.rejectValue("mailAddress", "mailAddressAlreadyUsed", "The mail address is already in use");
		}

	}

}
