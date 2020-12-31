package it.solving.padelmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solving.padelmanagement.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{

	@Query("from Admin a where a.mailAddress=?1")
	public Optional<Admin> findByMailAddress(String address);
	
}
