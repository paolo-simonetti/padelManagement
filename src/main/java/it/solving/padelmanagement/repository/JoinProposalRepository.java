package it.solving.padelmanagement.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import it.solving.padelmanagement.model.JoinProposal;
import it.solving.padelmanagement.model.User;

public interface JoinProposalRepository extends JpaRepository<JoinProposal, Long> {
	
	public Set<JoinProposal> findAllByApplicant(User applicant);
	
}
