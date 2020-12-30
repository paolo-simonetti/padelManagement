package it.solving.padelmanagement.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solving.padelmanagement.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

	@Query("from Player p left join fetch p.matches mc left join fetch p.matchesJoined mj where p.id=?1")
	public Optional<Player> findByIdWithAllMatches(Long id);
	
	@Query("from Player p join fetch p.club c where p.id=?1")
	public Optional<Player> findByIdWithClub(Long id);
	
	@Query("from Player p left join fetch p.matches mc left join fetch p.matchesJoined mj left join fetch "
			+ "mc.slots mcS left join fetch mj.slots mjS where p.id=?1")
	public Optional<Player> findByIdWithAllMatchesAndTheirSlots(Long id);
	
	@Query("from Player p left join fetch p.matchesJoined m where p.id=?1")
	public Optional<Player> findByIdWithMatchesJoined(Long id);
	
	@Query("from Player p left join fetch p.matches m where p.id=?1")
	public Optional<Player> findByIdWithMatchesCreated(Long id);
	
	public Set<Player> findAllByUsername(String username);
	
	public Set<Player> findAllByMailAddress(String mailAddress);
	
	@Query("from Player p left join fetch p.matches mc left join fetch p.matchesJoined mj left join fetch p.club c "
			+ "where p.id=?1")
	public Optional<Player> findByIdWithAllMatchesAndClub(Long id);
	
}
