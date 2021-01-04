package it.solving.padelmanagement.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.PadelMatch;

public interface MatchRepository extends JpaRepository<PadelMatch, Long> {

	@Query("from PadelMatch m left join fetch m.otherPlayers p left join fetch m.creator cr left join fetch m.slots s "
			+ "left join fetch m.court ct left join fetch ct.club cl where m.id=?1")
	public Optional<PadelMatch> findByIdWithCompleteInfo(Long id);
	
	@Query("from PadelMatch m left join fetch m.court co left join fetch co.club cl where m.date=?1")
	public Set<PadelMatch> findAllByDateAndClub(LocalDate date, Club club);
	
	@Query("from PadelMatch m left join fetch m.creator c where m.id=?1")
	public Optional<PadelMatch> findByIdWithCreator(Long id);
	
	@Query("from PadelMatch m left join fetch m.creator c left join fetch m.slots s where m.id=?1")
	public Optional<PadelMatch> findByIdWithCreatorAndSlots(Long id);
	
	@Query("from PadelMatch m left join fetch m.creator c left join fetch m.otherPlayers o left join fetch m.slots s "
			+ "where m.id=?1")
	public Optional<PadelMatch> findByIdWithCreatorAndOtherPlayersAndSlots(Long id);
	
	@Query("from PadelMatch m left join fetch m.creator cr left join fetch cr.club clCr left join fetch m.court co "
			+ "left join fetch co.club clCo where cr.id!=?1 and clCr.id=clCo.id")
	public Set<PadelMatch> findAllOthersCallForActionsInTheSameClubAsThePlayersOne(Long id);
	
	@Query("from PadelMatch m left join fetch m.otherPlayers o where m.id=?1")
	public Optional<PadelMatch> findByIdWithOtherPlayers(Long id);
	
	@Query("from PadelMatch m left join fetch m.otherPlayers o left join fetch m.creator c left join fetch m.court co "
			+ "left join fetch co.club cl where m.id=?1")
	public Optional<PadelMatch> findByIdWithOtherPlayersAndCreatorAndCourtAndClub(Long id);
	
	@Query("from PadelMatch m left join fetch m.court co left join fetch co.club cl where m.id=?1")
	public Optional<PadelMatch> findByIdWithCourtAndClub(Long id);
	
	@Query("from PadelMatch m left join fetch m.court co left join fetch co.club cl left join fetch cl.admin a where m.id=?1")
	public Optional<PadelMatch> findByIdWithCourtClubAndAdmin(Long id);
}
