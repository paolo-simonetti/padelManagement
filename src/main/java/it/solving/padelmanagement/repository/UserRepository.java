package it.solving.padelmanagement.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.solving.padelmanagement.model.Role;
import it.solving.padelmanagement.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public Set<User> findAllByRole(Role role);
	
	@Query("from User u where u.username=?1")
	public Optional<User> findByUsername(String username);
	
	@Query("from User u where u.mailAddress=?1")
	public Optional<User> findByMailAddress(String address);
	
	@Query("from User u left join fetch u.joinProposals jp left join fetch u.newClubProposals ncp where u.id=?1")
	public Optional<User> findByIdWithProposals(Long id);
	
}
