package it.solving.padelmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.solving.padelmanagement.model.Role;
import it.solving.padelmanagement.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByRole(Role role);

}
