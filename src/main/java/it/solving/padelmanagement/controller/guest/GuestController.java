package it.solving.padelmanagement.controller.guest;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.solving.padelmanagement.dto.ClubDTO;
import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.dto.message.insert.JoinProposalInsertMessageDTO;
import it.solving.padelmanagement.dto.message.insert.NewClubProposalInsertMessageDTO;
import it.solving.padelmanagement.exception.NonAdmissibleProposalException;
import it.solving.padelmanagement.service.ClubService;
import it.solving.padelmanagement.service.JoinProposalService;
import it.solving.padelmanagement.service.NewClubProposalService;
import it.solving.padelmanagement.validator.JoinProposalsValidator;
import it.solving.padelmanagement.validator.NewClubProposalsValidator;

@RestController
@RequestMapping("guest")
public class GuestController {
	
	@Autowired
	private ClubService clubService;
	
	@Autowired
	private JoinProposalService joinProposalService;
	
	@Autowired
	private NewClubProposalService newClubProposalService;
	
	@Autowired
	private JoinProposalsValidator joinProposalsValidator;
	
	@Autowired
	private NewClubProposalsValidator newClubProposalsValidator;
	
	@GetMapping("findAllClubs")
	public ResponseEntity<Set<ClubDTO>> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(clubService.findAll());
	}
	
	@PostMapping("insertJoinProposal")
	public ResponseEntity<ResultDTO> insertJoinProposal(@Valid @RequestBody 
			JoinProposalInsertMessageDTO joinProposalInsertMessageDTO, BindingResult bindingResult) 
					throws NonAdmissibleProposalException {
		joinProposalsValidator.validate(joinProposalInsertMessageDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new NonAdmissibleProposalException(bindingResult.getAllErrors().stream()
				.map(obj->(FieldError) obj).map(fieldError->fieldError.getDefaultMessage()).reduce(
						(message1,message2)->message1+";\n "+message2).get());
		}
		
		joinProposalService.insert(joinProposalInsertMessageDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("The proposal will be evaluated by the Admin of the club"));
	}
	
	@PostMapping("insertNewClubProposal")
	public ResponseEntity<ResultDTO> insertNewClubProposal(@Valid @RequestBody 
			NewClubProposalInsertMessageDTO newClubProposalInsertMessageDTO, BindingResult bindingResult) 
					throws NonAdmissibleProposalException {
		newClubProposalsValidator.validate(newClubProposalInsertMessageDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO("Encountered errors in the creator field"));
		}
		
		newClubProposalService.insert(newClubProposalInsertMessageDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("The proposal will be evaluated by the superAdmin"));
	}

}
