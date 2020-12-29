package it.solving.padelmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solving.padelmanagement.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

	@Query("from Player p left join fetch p.matches mc left join fetch p.matchesJoined mj where p.id=?1")
	public Optional<Player> findByIdWithAllMatches(Long id);
	
	@Query("from Player p join fetch p.club c where p.id=?1")
	public Optional<Player> findByIdWithClub(Long id);
	
}
