package it.solving.padelmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solving.padelmanagement.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {

	@Query("from Match m left join fetch m.otherPlayers p left join fetch m.creator cr left join fetch m.slots s "
			+ "left join fetch m.court ct where m.id=?1")
	public Optional<Match> findByIdWithCompleteInfo(Long id);
	
}
