package it.solving.padelmanagement.validator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.solving.padelmanagement.dto.message.createpadelmatch.InputValidateAndInsertInputMessageDTO;
import it.solving.padelmanagement.exception.VerifyAvailabilityException;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.Court;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.model.Slot;
import it.solving.padelmanagement.repository.CourtRepository;
import it.solving.padelmanagement.repository.PlayerRepository;
import it.solving.padelmanagement.securitymodel.PlayerPrincipal;
import it.solving.padelmanagement.service.PlayerService;
import it.solving.padelmanagement.util.MyUtil;

@Component
public class MatchInsertValidator implements Validator {

	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private CourtRepository courtRepository;
	
	@Autowired
	private VerifyAvailabilityValidator verifyAvailabilityValidator;
	
	@Autowired
	private MyUtil myUtil;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return InputValidateAndInsertInputMessageDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		InputValidateAndInsertInputMessageDTO inputMessage=(InputValidateAndInsertInputMessageDTO) target;

		// Ripeto i controlli effettuati in verifica disponibilità (esistenza player e validità slots)
		verifyAvailabilityValidator.validate(inputMessage,errors);
		
		// Controllo che il campo esista
		Court court=courtRepository.findByIdWithClub(Long.parseLong(inputMessage.getCourtId())).orElseThrow(
			NoSuchElementException::new);
		// Controllo che il campo sia nel circolo a cui è iscritto il player
		Club courtsClub=MyUtil.initializeAndUnproxy(court.getClub());
		Player player=((PlayerPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPlayer();		
		Club playersClub=MyUtil.initializeAndUnproxy(playerRepository.findByIdWithClub(player.getId())).get().getClub();
		if (courtsClub.getId()!=playersClub.getId()) {
			errors.rejectValue("courtId","courtInAnotherClub","The selected court is"
					+ " not in the club the player has joined in");
		}
		
		// Controllo che il campo non sia dismesso  
		if (!court.mayBeReserved()) {
			errors.rejectValue("courtId","courtCannotBeReserved", "The selected court cannot be reserved by an admin decision");
		}
		
		/* Controllo che il creatore non abbia altre partite previste per gli stessi slot della partita che 
		 * sta provando a inserire */
		player=playerRepository.findByIdWithAllMatches(player.getId()).get();
		Set<Slot> slotsInWhichThePlayerIsBusy=new HashSet<>();
		LocalDate dateOfTheMatch=LocalDate.parse(inputMessage.getDate());
		for (PadelMatch m:player.getMatches().stream().filter(
			match->match.getDate().compareTo(dateOfTheMatch)==0).collect(Collectors.toSet())) {
			// la natura di hashset previene la possibilità di duplicati in slotsInWhichThePlayerIsBusy 
			slotsInWhichThePlayerIsBusy.addAll(m.getSlots());
		}
		Set<Slot> slotsOfTheMatch=new HashSet<>();
		try {
			slotsOfTheMatch=myUtil.convertInputVerifyAvailabilityMessageDTOToSlots(inputMessage);
		} catch (VerifyAvailabilityException e) {
			errors.rejectValue("durationHour","invalidSlots",e.getMessage());
		}
		for (Slot s:slotsOfTheMatch) {
			if(slotsInWhichThePlayerIsBusy.contains(s)) {
				errors.rejectValue("durationHour","ubiquityGift","The player is "
						+ "already busy with another match in those hours");
				break;
			}
		}
		
		// Controllo che il player abbia pagato tutte le partite che ha creato
		if(!playerService.thePlayerHasPayedForEveryMatch(player.getId())) {
			errors.rejectValue("playerId","expectedPayment","The player created some matches for which he has not payed yet");
		}
		
		// Controllo che il campo sia libero nell'orario richiesto dal giocatore
		if (courtRepository.findByIdWithMatchesAndTheirSlotsByDate(
				dateOfTheMatch, court.getId()).isPresent()) {
			// Se sono qui dentro, vuol dire che nel campo verranno giocate delle partite quel giorno
			Court courtWithSlotsInWhichItsBusy=courtRepository.findByIdWithMatchesAndTheirSlotsByDate(
					dateOfTheMatch, court.getId()).get();
				Set<Slot> slotsInWhiChTheCourtIsBusy=new HashSet<>();
				if (courtWithSlotsInWhichItsBusy.getMatches()!=null && courtWithSlotsInWhichItsBusy.getMatches().size()>0) {
					courtWithSlotsInWhichItsBusy.getMatches().stream().forEach(m->slotsInWhiChTheCourtIsBusy.addAll(m.getSlots()));
				}
				for (Slot s:slotsOfTheMatch) {
					if(slotsInWhiChTheCourtIsBusy.contains(s)) {
						errors.rejectValue("courtId","courtAlreadyTaken","The court is "
								+ "already reserved to another match for at least part of the time the player needs");
						break;
					}
				}
			
		}
		
	}

}
