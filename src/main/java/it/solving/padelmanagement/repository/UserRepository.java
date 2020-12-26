package it.solving.padelmanagement.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import it.solving.padelmanagement.model.Role;
import it.solving.padelmanagement.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<Set<User>> findAllByRole(Role role);

}
