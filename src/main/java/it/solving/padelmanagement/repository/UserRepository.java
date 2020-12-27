package it.solving.padelmanagement.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solving.padelmanagement.model.Role;
import it.solving.padelmanagement.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<Set<User>> findAllByRole(Role role);
	
	@Query("from User u left join fetch u.joinProposals jp left join fetch u.newClubProposals ncp where u.id=?1")
	public Optional<User> findByIdWithProposals(Long id);

}
