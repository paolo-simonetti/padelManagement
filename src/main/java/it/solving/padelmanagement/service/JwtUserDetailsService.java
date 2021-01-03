package it.solving.padelmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.model.Admin;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.model.User;
import it.solving.padelmanagement.repository.AdminRepository;
import it.solving.padelmanagement.repository.PlayerRepository;
import it.solving.padelmanagement.repository.UserRepository;
import it.solving.padelmanagement.securitymodel.AdminPrincipal;
import it.solving.padelmanagement.securitymodel.PlayerPrincipal;
import it.solving.padelmanagement.securitymodel.UserPrincipal;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private PlayerRepository playerRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByUsernameWithProPicFile(username).orElse(null);
		Player player=playerRepository.findByUsernameWithProPicFile(username).orElse(null);
		Admin admin=adminRepository.findByUsernameWithProPicFile(username).orElse(null);
	
		if(user!=null) {
			return new UserPrincipal(user);
		}
		
		if(player!=null) {
			return new PlayerPrincipal(player);
		}
		
		if(admin!=null) {
			return new AdminPrincipal(admin);
		}
		
		throw new UsernameNotFoundException("The user was not found!");
	}

}
