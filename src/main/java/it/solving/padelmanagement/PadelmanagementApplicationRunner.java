package it.solving.padelmanagement;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import it.solving.padelmanagement.model.Role;
import it.solving.padelmanagement.model.Slot;
import it.solving.padelmanagement.model.User;
import it.solving.padelmanagement.repository.SlotRepository;
import it.solving.padelmanagement.repository.UserRepository;
import it.solving.padelmanagement.service.UserService;

@Component
public class PadelmanagementApplicationRunner implements ApplicationRunner {

	@Autowired
	private SlotRepository slotRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (slotRepository.findAll()!=null && slotRepository.findAll().size()==0) {
			for (int i=1; i<29; i++) {
				slotRepository.save(Slot.convertIdToSlot(i));
			}
		}
		
		if (!userService.isSuperAdminPresent()) {
			User superAdmin=new User();
			superAdmin.setName("Aldo");
			superAdmin.setSurname("Giannuli");
			superAdmin.setDateOfBirth(LocalDate.of(1952,06,18));
			superAdmin.setMailAddress("mafiaMondiale@protonmail.com");
			superAdmin.setMobile("39102487629");
			superAdmin.setRole(Role.ROLE_SUPER_ADMIN);
			userRepository.save(superAdmin);
		}

	}

}
