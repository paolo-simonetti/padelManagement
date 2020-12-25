package it.solving.padelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.solving.padelmanagement.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
