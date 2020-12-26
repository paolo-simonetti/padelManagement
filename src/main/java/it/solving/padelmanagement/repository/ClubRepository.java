package it.solving.padelmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solving.padelmanagement.model.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
	
	@Query("from Club c left join fetch c.admin a where c.id=?1")
	Optional<Club> findByIdWithAdmin(Long id);
	
}
