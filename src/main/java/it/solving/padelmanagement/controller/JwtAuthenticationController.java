package it.solving.padelmanagement.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.solving.padelmanagement.config.JwtTokenUtil;
import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.dto.message.JwtRequestMessageDTO;
import it.solving.padelmanagement.dto.message.JwtResponseMessageDTO;
import it.solving.padelmanagement.dto.message.insert.UserInsertMessageDTO;
import it.solving.padelmanagement.service.JwtUserDetailsService;
import it.solving.padelmanagement.service.UserService;
import it.solving.padelmanagement.validator.SignUpValidator;

@RestController
@RequestMapping("authorization")
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService jwtInMemoryUserDetailsService;
	
	@Autowired
	private SignUpValidator signUpValidator;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("login")
	public ResponseEntity<JwtResponseMessageDTO> generateLoginToken(@RequestBody JwtRequestMessageDTO inputMessage) {
		this.authenticate(inputMessage.getUsername(),inputMessage.getPassword());
		final UserDetails userDetails=jwtInMemoryUserDetailsService.loadUserByUsername(inputMessage.getUsername());
		final String token=jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.status(HttpStatus.OK).body(new JwtResponseMessageDTO(token));
	}
	
	@PostMapping(path="signup",consumes="multipart/form-data;charset=UTF-8")
	public ResponseEntity<ResultDTO> insert(@Valid @ModelAttribute UserInsertMessageDTO userInsertMessageDTO, BindingResult bindingResult) throws IOException {
		signUpValidator.validate(userInsertMessageDTO,bindingResult);
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO("User already exists"));
		}
		userService.insert(userInsertMessageDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("New user successfully inserted")); 
	}
	
	
	private void authenticate(String username, String password) throws BadCredentialsException {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
	}
}
