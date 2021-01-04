package it.solving.padelmanagement.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solving.padelmanagement.model.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
	
	@Query("from Notice n left join fetch n.club c left join fetch c.admin a where n.id=?1")
	public Optional<Notice> findByIdWithClub(Long id);
	
	@Query("from Notice n left join fetch n.club c left join fetch c.players p where p.id=?1")
	public Set<Notice> findAllByClubPlayerId(Long id);
}
