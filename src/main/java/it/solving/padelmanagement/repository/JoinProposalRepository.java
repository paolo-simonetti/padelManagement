package it.solving.padelmanagement.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solving.padelmanagement.model.JoinProposal;
import it.solving.padelmanagement.model.User;

public interface JoinProposalRepository extends JpaRepository<JoinProposal, Long> {
	
	public Set<JoinProposal> findAllByApplicant(User applicant);
	
	@Query("from JoinProposal j left join fetch j.applicant a where j.id=?1")
	public Optional<JoinProposal> findByIdWithApplicant (Long id);
	
	@Query("from JoinProposal j left join fetch j.applicant a left join fetch a.proPicFile p left join fetch j.club c where j.id=?1")
	public Optional<JoinProposal> findByIdWithCompleteInfos (Long id);
	
	@Query("from JoinProposal j left join fetch j.applicant a left join fetch j.club c where c.id=?1")
	public Set<JoinProposal> findAllPendingWithApplicantByClub (Long clubId);
	
}
