package it.solving.padelmanagement.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solving.padelmanagement.dto.message.createpadelmatch.InputVerifyAvailabilityMessageDTO;
import it.solving.padelmanagement.exception.VerifyAvailabilityException;
import it.solving.padelmanagement.model.Court;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.repository.PlayerRepository;
import it.solving.padelmanagement.securitymodel.PlayerPrincipal;
import it.solving.padelmanagement.util.MyUtil;

@Component
public class VerifyAvailabilityValidator implements Validator {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private MyUtil myUtil;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return InputVerifyAvailabilityMessageDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		InputVerifyAvailabilityMessageDTO inputMessage = (InputVerifyAvailabilityMessageDTO) target;
		
		//Recupero il player dal Security context holder
		Player player=((PlayerPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPlayer();		
		
		// Controlli di sintassi su inizio e durata della partita
		LocalDate date=null;
		try {
			date=LocalDate.parse(inputMessage.getDate());
		} catch(DateTimeParseException e) {
			errors.rejectValue("date", "invalidDate", "Invalid date!");
		}
		
		if(date!=null && date.compareTo(LocalDate.now())<0) {
			errors.rejectValue("date", "dateInThePast", "Courts cannot be reserved for a date in the past!");
		}
		
		if(Integer.parseInt(inputMessage.getMinute())!=0 && Integer.parseInt(inputMessage.getMinute())!=30) {
			errors.rejectValue("minute", "invalidMinute", "Minute can be only 0 or 30!");
		}
		
		if(Integer.parseInt(inputMessage.getDurationMinute())!=0 && Integer.parseInt(inputMessage.getDurationMinute())!=30) {
			errors.rejectValue("durationMinute", "invalidDurationMinute", "Duration minute can be only 0 or 30!");
		}
		
		// Controllo che la partita duri almeno un'ora e mezza e sia in orari validi
		try {
			if (myUtil.convertInputVerifyAvailabilityMessageDTOToSlots(inputMessage).size()<3) {
				errors.rejectValue("durationHour", "invalidDuration", "A match must be at least 1h30 long");
			}
		} catch (VerifyAvailabilityException e) {
			errors.rejectValue("durationHour", "invalidDurationOrStart", e.getMessage());
		}
		
		// Controllo che ci siano campi attivi nel club 
		Set<Court> activeCourts=playerRepository.findByIdWithClubAndItsActiveCourts(player.getId()).get()
				.getClub().getCourts();
		if(activeCourts==null || activeCourts.size()==0) {
			errors.rejectValue("date","noActiveCourts","There are no active courts in the club the player is member of");
		}
	}

}
