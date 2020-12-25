package it.solving.padelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.solving.padelmanagement.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{

}
