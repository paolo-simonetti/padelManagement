package it.solving.padelmanagement.controller.admin.joinproposals;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.solving.padelmanagement.dto.JoinProposalDTO;
import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.service.JoinProposalService;

//TODO: in tutte questo package, devo controllare che l'admin stia agendo su roba riguardante il proprio circolo
@RestController
@RequestMapping("admin/joinproposals")
public class JoinProposalController {
	
	@Autowired
	private JoinProposalService joinProposalService;
	
	@GetMapping("findAllPending")
	public ResponseEntity<Set<JoinProposalDTO>> findAllPending(@RequestParam Long clubId) {
		return ResponseEntity.status(HttpStatus.OK).body(joinProposalService.findAllPendingWithApplicantByClub(clubId));
	}
	
	@GetMapping("approve")
	public ResponseEntity<ResultDTO> approveJoinProposal(@RequestParam Long id) {
		joinProposalService.approve(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("An email was sent to the applicant to notify the approval"));
	}
	
	@GetMapping("reject")
	public ResponseEntity<ResultDTO> rejectJoinProposal(@RequestParam Long id) {
		joinProposalService.reject(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("An email was sent to the applicant to notify the rejection"));
	}
	
}
