package it.solving.padelmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solving.padelmanagement.model.PadelMatch;

public interface MatchRepository extends JpaRepository<PadelMatch, Long> {

	@Query("from PadelMatch m left join fetch m.otherPlayers p left join fetch m.creator cr left join fetch m.slots s "
			+ "left join fetch m.court ct where m.id=?1")
	public Optional<PadelMatch> findByIdWithCompleteInfo(Long id);
	
}
