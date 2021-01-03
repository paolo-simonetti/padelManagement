package it.solving.padelmanagement.controller.superadmin;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.solving.padelmanagement.dto.NewClubProposalDTO;
import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.dto.message.insert.ClubInsertMessageDTO;
import it.solving.padelmanagement.dto.message.insert.UserInsertMessageDTO;
import it.solving.padelmanagement.exception.EmailException;
import it.solving.padelmanagement.model.NewClubProposal;
import it.solving.padelmanagement.model.ProposalStatus;
import it.solving.padelmanagement.repository.NewClubProposalRepository;
import it.solving.padelmanagement.service.AdminService;
import it.solving.padelmanagement.service.EmailService;
import it.solving.padelmanagement.service.NewClubProposalService;
import it.solving.padelmanagement.util.MyUtil;

@RestController
@RequestMapping("superadmin")
public class SuperAdminController {

	@Autowired
	private NewClubProposalService newClubProposalService;
	
	@Autowired
	private NewClubProposalRepository newClubProposalRepository;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MyUtil myUtil;
	
	@GetMapping("findAllPendingNewClubProposals")
	public ResponseEntity<Set<NewClubProposalDTO>> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(newClubProposalService.findAll()
			.stream().filter(proposalDTO->proposalDTO.getProposalStatus().equals("pending"))
				.collect(Collectors.toSet()));
	}
	
	@GetMapping("approveNewClubProposal")
	public ResponseEntity<ResultDTO> approveNewClubProposal(@RequestParam Long newClubProposalId) throws EmailException {
		NewClubProposal newClubProposal=newClubProposalService.findByIdWithCreatorTheirProPicAndLogo(newClubProposalId);
		if (newClubProposal!=null && newClubProposal.getProposalStatus()==ProposalStatus.PENDING) {
			newClubProposal.setProposalStatus(ProposalStatus.APPROVED);
			UserInsertMessageDTO admin=myUtil.getAdminFromNewClubProposal(newClubProposal);
			ClubInsertMessageDTO club=myUtil.getClubFromNewClubProposal(newClubProposal);
			adminService.insert(admin,club, newClubProposal.getCreator().getId());
			emailService.sendApprovedNewClubProposalNotification(newClubProposal.getCreator().getMailAddress());
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResultDTO("An email was sent to the applicant to notify the approval"));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO("Invalid requested proposal"));
		}
		
	}
	
	@GetMapping("rejectNewClubProposal")
	public ResponseEntity<ResultDTO> rejectNewClubProposal(@RequestParam Long newClubProposalId) throws EmailException {
		NewClubProposal newClubProposal=newClubProposalService.findByIdWithCreatorTheirProPicAndLogo(newClubProposalId);
		if(newClubProposal!=null && newClubProposal.getProposalStatus()==ProposalStatus.PENDING) {
			newClubProposal.setProposalStatus(ProposalStatus.REJECTED);
			newClubProposalRepository.save(newClubProposal);
			emailService.sendRejectedNewClubProposalNotification(newClubProposal.getCreator().getMailAddress());
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResultDTO("An email was sent to the applicant to notify the rejection"));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO("Invalid requested proposal"));
		}
	}
	
}
