package it.solving.padelmanagement.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solving.padelmanagement.model.Admin;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.Court;

public interface CourtRepository extends JpaRepository<Court, Long> {
	
	@Query("from Court c left join fetch c.matches m left join fetch m.slots s left join fetch c.club cl "
			+ "where m.date=?1 and cl.id=?2")
	public Set<Court> findAllWithMatchesAndTheirSlotsByDate(LocalDate date, Long clubId);

	@Query("from Court c left join fetch c.matches m left join fetch m.slots s "
			+ "where m.date=?1 and c.id=?2")
	public Optional<Court> findByIdWithMatchesAndTheirSlotsByDate(LocalDate date, Long courtId);
	
	@Query("from Court c left join fetch c.matches m left join fetch c.club cl where c.id=?1")
	public Optional<Court> findByIdWithCompleteInfos(Long id);
	
	@Query("from Court c left join fetch c.matches m where c.id=?1")
	public Optional<Court> findByIdWithMatches(Long id);
	
	@Query("from Court c left join fetch c.matches m left join fetch c.club cl where c.id=?1")
	public Optional<Court> findByIdWithMatchesAndClub(Long id);
	
	@Query("from Court c left join fetch c.club cl where c.id=?1")
	public Optional<Court> findByIdWithClub(Long id);

	public Set<Court> findAllByClub(Club club);
	
	public Set<Court> findAllByClub_Admin(Admin admin);
}
