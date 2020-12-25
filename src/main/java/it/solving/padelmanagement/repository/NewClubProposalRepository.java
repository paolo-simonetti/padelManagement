package it.solving.padelmanagement.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solving.padelmanagement.model.NewClubProposal;
import it.solving.padelmanagement.model.User;

public interface NewClubProposalRepository extends JpaRepository<NewClubProposal, Long> {
	
	public Set<NewClubProposal> findAllByCreator(User creator);
	
	@Query("from NewClubProposal n left join fetch n.creator c where n.id=?1")
	public Optional<NewClubProposal> findByIdWithCreator(Long id);
	
}
