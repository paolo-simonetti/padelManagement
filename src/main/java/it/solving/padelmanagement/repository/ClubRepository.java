package it.solving.padelmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solving.padelmanagement.model.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
	
	@Query("from Player p left join fetch Admin a where p.id=?1")
	Optional<Club> findByIdWithAdmin(Long id);
	
}
