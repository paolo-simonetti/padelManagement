package it.solving.padelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.solving.padelmanagement.model.Court;

public interface CourtRepository extends JpaRepository<Court, Long> {

}
