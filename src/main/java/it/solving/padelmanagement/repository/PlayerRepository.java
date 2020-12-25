package it.solving.padelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.solving.padelmanagement.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
