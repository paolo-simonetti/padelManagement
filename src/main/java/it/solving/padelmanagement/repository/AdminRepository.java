package it.solving.padelmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solving.padelmanagement.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{

	@Query("from Admin a where a.mailAddress=?1")
	public Optional<Admin> findByMailAddress(String address);
	
	@Query("from Admin a left join fetch a.proPicFile p where a.username=?1")
	public Optional<Admin> findByUsernameWithProPicFile(String username);
	
	@Query("from Admin a left join fetch a.club c where a.id=?1")
	public Optional<Admin> findByIdWithClub(Long id);
	
}
