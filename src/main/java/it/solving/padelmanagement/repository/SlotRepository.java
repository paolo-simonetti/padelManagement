package it.solving.padelmanagement.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solving.padelmanagement.model.Slot;



public interface SlotRepository extends JpaRepository<Slot, Integer> {
	
	@Query("from Slot s left join fetch s.matches m where s.id=?1")
	Optional<Slot> findByIdWithMatches(Integer id);
	
	@Query("from Slot s left join fetch s.matches m")
	Set<Slot> findAllWithMatches();
}
