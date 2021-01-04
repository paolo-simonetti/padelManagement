package it.solving.padelmanagement.controller.admin.joinproposals;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.exception.EmailException;
import it.solving.padelmanagement.exception.ProposalStatusException;
import it.solving.padelmanagement.model.Admin;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.repository.ClubRepository;
import it.solving.padelmanagement.securitymodel.AdminPrincipal;
import it.solving.padelmanagement.service.JoinProposalService;

@RestController
@RequestMapping("admin/joinproposals")
public class JoinProposalController {
	
	@Autowired
	private JoinProposalService joinProposalService;
	
	@Autowired
	private ClubRepository clubRepository;
	
	@GetMapping("findAllPending")
	public ResponseEntity<Object> findAllPending(@RequestParam Long clubId) {
		// Recupero l'admin dal security context holder
		Admin admin=((AdminPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
			.getAdmin();
		// Recupero l'admin col club dal contesto di persistenza
		Club club = clubRepository.findByIdWithAdmin(clubId).orElseThrow(NoSuchElementException::new);
		// verifico che l'admin stia agendo dal proprio club
		if (club.getAdmin()!=admin) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO("This club cannot be acted upon by the user"));
		}
		return ResponseEntity.status(HttpStatus.OK).body(joinProposalService.findAllPendingWithApplicantByClub(clubId));
	}
	
	@GetMapping("approve")
	public ResponseEntity<ResultDTO> approveJoinProposal(@RequestParam Long id) throws ProposalStatusException, EmailException {
		// Recupero l'admin dal security context holder
		Admin admin=((AdminPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
			.getAdmin();
		// Recupero l'admin col club dal contesto di persistenza
		Club club = clubRepository.findByIdWithAdmin(id).orElseThrow(NoSuchElementException::new);
		// verifico che l'admin stia agendo dal proprio club
		if (club.getAdmin()!=admin) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO("This club cannot be acted upon by the user"));
		}
		joinProposalService.approve(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("An email was sent to the applicant to notify the approval"));
	}
	
	@GetMapping("reject")
	public ResponseEntity<ResultDTO> rejectJoinProposal(@RequestParam Long id) throws ProposalStatusException, EmailException {
		// Recupero l'admin dal security context holder
		Admin admin=((AdminPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
			.getAdmin();
		// Recupero l'admin col club dal contesto di persistenza
		Club club = clubRepository.findByIdWithAdmin(id).orElseThrow(NoSuchElementException::new);
		// verifico che l'admin stia agendo dal proprio club
		if (club.getAdmin()!=admin) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO("This club cannot be acted upon by the user"));
		}
		joinProposalService.reject(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("An email was sent to the applicant to notify the rejection"));
	}
	
}
