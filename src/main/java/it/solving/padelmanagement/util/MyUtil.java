package it.solving.padelmanagement.util;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.solving.padelmanagement.dto.message.createpadelmatch.InputValidateAndInsertInputMessageDTO;
import it.solving.padelmanagement.dto.message.createpadelmatch.InputVerifyAvailabilityMessageDTO;
import it.solving.padelmanagement.dto.message.insert.ClubInsertMessageDTO;
import it.solving.padelmanagement.dto.message.insert.PlayerInsertMessageDTO;
import it.solving.padelmanagement.dto.message.insert.UserInsertMessageDTO;
import it.solving.padelmanagement.exception.VerifyAvailabilityException;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.Court;
import it.solving.padelmanagement.model.JoinProposal;
import it.solving.padelmanagement.model.NewClubProposal;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.model.Slot;
import it.solving.padelmanagement.model.User;
import it.solving.padelmanagement.repository.SlotRepository;

@Component
public class MyUtil {

	@Autowired
	private SlotRepository slotRepository;
	
	public UserInsertMessageDTO getAdminFromNewClubProposal(NewClubProposal newClubProposal) {
		UserInsertMessageDTO result=new UserInsertMessageDTO();
		result.setName(newClubProposal.getCreator().getName());
		result.setSurname(newClubProposal.getCreator().getSurname());
		result.setDateOfBirth(newClubProposal.getCreator().getDateOfBirth().toString());
		result.setMailAddress(newClubProposal.getCreator().getMailAddress());
		result.setMobile(newClubProposal.getCreator().getMobile());
		result.setUsername(newClubProposal.getCreator().getUsername());
		result.setPassword(newClubProposal.getCreator().getPassword());
		return result;
	}
	
	public ClubInsertMessageDTO getClubFromNewClubProposal(NewClubProposal newClubProposal) {
		ClubInsertMessageDTO result=new ClubInsertMessageDTO();
		result.setName(newClubProposal.getName());
		result.setCity(newClubProposal.getCity());
		result.setLogo(newClubProposal.getLogo());
		return result;
	}
	
	public PlayerInsertMessageDTO buildPlayerInsertMessageDTOFromJoinProposal(JoinProposal joinProposal) {
		PlayerInsertMessageDTO insertMessageDTO=new PlayerInsertMessageDTO();
		User user = joinProposal.getApplicant();
		Club club=joinProposal.getClub();
		insertMessageDTO.setName(user.getName());
		insertMessageDTO.setSurname(user.getSurname());
		insertMessageDTO.setDateOfBirth(user.getDateOfBirth().toString());
		insertMessageDTO.setMailAddress(user.getMailAddress());
		insertMessageDTO.setUsername(user.getUsername());
		insertMessageDTO.setPassword(user.getPassword());
		insertMessageDTO.setMobile(user.getMobile());
		insertMessageDTO.setLevel(joinProposal.getUserLevel().toString());
		insertMessageDTO.setClubId(club.getId().toString());
		return insertMessageDTO;
	}
	
	public Set<Slot> convertInputVerifyAvailabilityMessageDTOToSlots(InputVerifyAvailabilityMessageDTO inputMessage) 
		throws VerifyAvailabilityException {
		Set<Slot> result =new HashSet<>();
		
		// verifico che l'orario di inizio sia valido
		Slot initialSlot=Slot.convertHourAndMinuteToSlot(Integer.parseInt(inputMessage.getHour()),
				Integer.parseInt(inputMessage.getMinute()));
		if(initialSlot==null) {
			throw new VerifyAvailabilityException("Invalid choice of match start");
		} else {
			result.add(initialSlot);
		} 
		
		// converto la durata chiesta dal player in numero di slot, tenendo presente che il primo slot è già contato
		// es. 2h---> 3 slots; 3h30 ---> 6 slot
		int numberOfSlots=2*Integer.parseInt(inputMessage.getDurationHour());
		if(Integer.parseInt(inputMessage.getDurationMinute())==0) {
			numberOfSlots--;
		}
		
		// aggiungo gli slot al risultato
		
		for (int i=1; i<=numberOfSlots; i++) {
			Slot slotToBeAdded=Slot.convertIdToSlot(initialSlot.getId()+i);
			if (slotToBeAdded==null) {
				throw new VerifyAvailabilityException("Invalid choice of match duration");
			} else {
				result.add(slotToBeAdded);
			}
		}
		return result;
	}
	
	public boolean isTheCourtOptimalForTheRequiredSlots(Court court, Set<Slot> requiredSlots) {
		// Trovo l'id minimo tra gli slots, che sicuramente non può essere maggiore dell'ultimo slot
		int min=29;
		// trovo anche l'id massimo, che sicuramente sarà superiore a 1
		int max=1;
		for (Integer i:requiredSlots.stream().map(slot->slot.getId()).collect(Collectors.toSet())) {
			if(i<=min) {
				min=i;
			}
			
			if(i>=max) {
				max=i;
			}
		}
		/* Se l'id minimo trovato è <= 3, allora l'ottimizzazione consiste nel controllare solo i tre slot successivi
		 * all'id massimo. */
		if (min<=3) {
			Set<Slot> slots=new HashSet<>();
			if (max>=27) {
				/* Caso patologico: il giocatore vuole prenotare per praticamente tutta la giornata
				 * --> il campo risultante dalla ricerca base va già benissimo */
				return true;
			} else {
				// Recupero i tre id successivi e verifico se ci sono partite nel campo
				for (int i=1; i<=3; i++) {
					slots.add(Slot.convertIdToSlot(max+i));
				}
				return court.areThereMatchesInTheSlots(slots);	
			}
		}
		
		/* Se l'id minimo trovato è > 3, allora l'ottimizzazione consiste nel controllare i tre slot precedenti
		 * all'id minimo, ed eventualmente anche i 3 successivi all'id massimo. */
		Set<Slot> slots=new HashSet<>();
		for (int i=1;i<=3;i++) {
			slots.add(Slot.convertIdToSlot(min-i));
		}
		
		if (max<27) {
			// in questo caso, devo controllare anche i tre slot successivi a quello finale 
			for (int i=1; i<=3; i++) {
				slots.add(Slot.convertIdToSlot(max+i));
			}
		}
		
		return court.areThereMatchesInTheSlots(slots);
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T initializeAndUnproxy(T entity) {
	    if (entity == null) {
	        throw new 
	           NullPointerException("Entity passed for initialization is null");
	    }

	    Hibernate.initialize(entity);
	    if (entity instanceof HibernateProxy) {
	        entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
	                .getImplementation();
	    }
	    return entity;
	}
	
}
