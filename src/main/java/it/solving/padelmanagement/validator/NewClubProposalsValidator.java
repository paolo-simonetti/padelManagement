package it.solving.padelmanagement.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solving.padelmanagement.dto.message.insert.NewClubProposalInsertMessageDTO;
import it.solving.padelmanagement.model.User;
import it.solving.padelmanagement.repository.UserRepository;
import it.solving.padelmanagement.securitymodel.UserPrincipal;
import it.solving.padelmanagement.service.UserService;

@Component
public class NewClubProposalsValidator implements Validator {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return NewClubProposalInsertMessageDTO.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		NewClubProposalInsertMessageDTO newClubProposalInsertMessageDTO=(NewClubProposalInsertMessageDTO) target;
		User user=((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();	
		// Controllo che lo user non abbia altre proposte in sospeso
		if(userService.hasAnotherPendingProposal(user.getId())) {
			errors.rejectValue("creatorId","anotherPendingProposal", "The user has another pending proposal");
		}
			
	}

}
