package it.solving.padelmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.exception.EmailException;
import it.solving.padelmanagement.mapper.MatchMapper;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.model.Role;
import it.solving.padelmanagement.model.User;
import it.solving.padelmanagement.repository.AdminRepository;
import it.solving.padelmanagement.repository.PlayerRepository;
import it.solving.padelmanagement.repository.UserRepository;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
	private MatchMapper matchMapper;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void sendNotification(String from, String to,String subject, String text) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}
	
	public void sendApprovedJoinProposalNotification(String to, Club club) throws EmailException {
		if(playerRepository.findByMailAddress(to).isPresent()) {
			this.sendNotification(club.getAdmin().getMailAddress(),to,
					"Your join proposal was approved!", "Welcome to "+ club.getName()+", "+
				playerRepository.findByMailAddress(to).get().getName()+"!");
		} else {
			throw new EmailException("The user's mail address was not found in the persistence context!");
		}
	}
	
	public void sendRejectedJoinProposalNotification(String to, Club club) throws EmailException {
		if(userRepository.findByMailAddress(to).isPresent()) {
			this.sendNotification(club.getAdmin().getMailAddress(), to,"Your join proposal was rejected!", 
					"Sorry, "+userRepository.findByMailAddress(to).get().getName()+
					", you were denied to join in the club "+ club.getName()+"!");
		} else {
			throw new EmailException("The user's mail address was not found in the persistence context!");
		}
	}
	
	public void sendApprovedNewClubProposalNotification(String to) throws EmailException {
		User superAdmin=userRepository.findAllByRole(Role.ROLE_SUPER_ADMIN).stream().findFirst().orElse(null);
		if (superAdmin==null) {
			throw new EmailException("The SuperAdmin's mail address was not found in the persistence context!");
		}

		if(adminRepository.findByMailAddress(to).isPresent()) {
			this.sendNotification(superAdmin.getMailAddress(), to,
					"Your new club proposal was approved!", 
					"Congratulation, "+adminRepository.findByMailAddress(to).get().getName()+
					", your proposal for a new club was approved! ");
		} else {
			throw new EmailException("The user's mail address was not found in the persistence context!");
		}
	}
	
	public void sendRejectedNewClubProposalNotification(String to) throws EmailException {
		User superAdmin=userRepository.findAllByRole(Role.ROLE_SUPER_ADMIN).stream().findFirst().orElse(null);
		if (superAdmin==null) {
			throw new EmailException("The SuperAdmin's mail address was not found in the persistence context!");
		}
		
		if(userRepository.findByMailAddress(to).isPresent()) {
			
			this.sendNotification(superAdmin.getMailAddress(), to,
					"Your join proposal was rejected!", 
					"Sorry, "+userRepository.findByMailAddress(to).get().getName()+
					", your proposal for a new club has been rejected!");
		} else {
			throw new EmailException("The user's mail address was not found in the persistence context!");
		}
	}
	
	public void sendMatchSummary(String to, PadelMatch match, Club club) throws EmailException {
		if(userRepository.findByMailAddress(to).isPresent()) {
			this.sendNotification(club.getAdmin().getMailAddress(), to,"Match summary", 
					"Hello "+userRepository.findByMailAddress(to).get().getName()+
					", here is the recap of the match you are going to play at "+ club.getName()+":\n "+
					matchMapper.convertEntityToOutputMatchMailMessageDTO(match));
		} else {
			throw new EmailException("The user's mail address was not found in the persistence context!");
		}
	}
	
}
