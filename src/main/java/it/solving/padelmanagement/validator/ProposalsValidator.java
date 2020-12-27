package it.solving.padelmanagement.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solving.padelmanagement.dto.message.insert.JoinProposalInsertMessageDTO;
import it.solving.padelmanagement.dto.message.insert.NewClubProposalInsertMessageDTO;
import it.solving.padelmanagement.model.JoinProposal;
import it.solving.padelmanagement.model.NewClubProposal;
import it.solving.padelmanagement.service.UserService;

@Component
public class ProposalsValidator<Proposal> implements Validator {
	
	private Proposal supportVariable;
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> aClass) {
		if (supportVariable instanceof JoinProposal) {
			return JoinProposalInsertMessageDTO.class.isAssignableFrom(aClass);
		} else if(supportVariable instanceof NewClubProposal) {
			return NewClubProposalInsertMessageDTO.class.isAssignableFrom(aClass);
		} else {
			return false;
		}
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (supportVariable instanceof JoinProposal) {
			JoinProposalInsertMessageDTO joinProposalInsertMessageDTO=(JoinProposalInsertMessageDTO) target;
			if(userService.hasAnotherPendingProposal(Long.parseLong(joinProposalInsertMessageDTO.getApplicantId()))) {
				errors.rejectValue("applicantId","anotherPendingProposal", "The user has another pending proposal");
			}
		} else if (supportVariable instanceof NewClubProposal) {
			NewClubProposalInsertMessageDTO newClubProposalInsertMessageDTO=(NewClubProposalInsertMessageDTO) target;
			if(userService.hasAnotherPendingProposal(Long.parseLong(newClubProposalInsertMessageDTO.getCreatorId()))) {
				errors.rejectValue("creatorId","anotherPendingProposal", "The user has another pending proposal");
			}
		}
	}

}
