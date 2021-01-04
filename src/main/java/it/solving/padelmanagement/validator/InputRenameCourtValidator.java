package it.solving.padelmanagement.validator;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solving.padelmanagement.dto.message.update.CourtUpdateMessageDTO;
import it.solving.padelmanagement.model.Admin;
import it.solving.padelmanagement.repository.CourtRepository;
import it.solving.padelmanagement.securitymodel.AdminPrincipal;

@Component
public class InputRenameCourtValidator implements Validator {
	
	@Autowired
	private CourtRepository courtRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return CourtUpdateMessageDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CourtUpdateMessageDTO courtUpdateMessageDTO=(CourtUpdateMessageDTO) target;
		// Recupero l'admin dal security context holder
		Admin admin=((AdminPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
			.getAdmin();
		// Prendo dal contesto di persistenza tutti i campi del circolo amministrato dall'admin, 
		// e verifico che ci sia anche il campo in input
		if(!courtRepository.findAllByClub_Admin(admin).stream().map(court->court.getId()).collect(Collectors.toSet())
			.contains(Long.parseLong(courtUpdateMessageDTO.getId()))) {
			errors.rejectValue("courtId","courtNotUnderAdminControl","This court does not belong to the admin's club!");
		}
		
	}

}
