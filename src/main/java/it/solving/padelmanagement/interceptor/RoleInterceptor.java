package it.solving.padelmanagement.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import it.solving.padelmanagement.config.JwtTokenUtil;
import it.solving.padelmanagement.model.Admin;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.model.Role;
import it.solving.padelmanagement.model.User;
import it.solving.padelmanagement.repository.AdminRepository;
import it.solving.padelmanagement.repository.PlayerRepository;
import it.solving.padelmanagement.repository.UserRepository;

@Component
public class RoleInterceptor implements HandlerInterceptor {
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public boolean preHandle (HttpServletRequest request, HttpServletResponse response, 
		Object handler) throws Exception {
		String relativePath=request.getRequestURI();
		if (relativePath.startsWith("/authorization")) {
			return true;
		}
		String username=jwtTokenUtil.getUsernameFromToken(request.getHeader("Authorization").substring(7));
		Admin admin=adminRepository.findByUsernameWithProPicFile(username).orElse(null);
		Player player=playerRepository.findByUsernameWithProPicFile(username).orElse(null);
		User user=userRepository.findByUsernameWithProPicFile(username).orElse(null);
		
		if (relativePath.startsWith("/admin")) {
			return admin!=null;
		}
		
		if (relativePath.startsWith("/player")) {
			return player!=null;
		}
		
		if (relativePath.startsWith("/guest")) {
			return (user!=null && user.getRole()==Role.ROLE_GUEST);
		}

		if (relativePath.startsWith("/superadmin")) {
			return (user!=null && user.getRole()==Role.ROLE_SUPER_ADMIN);
		}

		return true;
	}
	
}
