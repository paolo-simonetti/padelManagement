package it.solving.padelmanagement.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.model.Slot;
import it.solving.padelmanagement.model.User;

@Component
public class MyUtil {

	
	public UserInsertMessageDTO getAdminFromNewClubProposal(NewClubProposal newClubProposal) {
		UserInsertMessageDTO result=new UserInsertMessageDTO();
		result.setName(newClubProposal.getCreator().getName());
		result.setSurname(newClubProposal.getCreator().getSurname());
		result.setDateOfBirth(newClubProposal.getCreator().getDateOfBirth().toString());
		result.setMailAddress(newClubProposal.getCreator().getMailAddress());
		result.setMobile(newClubProposal.getCreator().getMobile());
		result.setUsername(newClubProposal.getCreator().getUsername());
		result.setPassword(newClubProposal.getCreator().getPassword());
		result.setProPicName(newClubProposal.getLogo().getName());
		result.setProPic(newClubProposal.getLogo().getImage());
		return result;
	}
	
	public ClubInsertMessageDTO getClubFromNewClubProposal(NewClubProposal newClubProposal) {
		ClubInsertMessageDTO result=new ClubInsertMessageDTO();
		result.setName(newClubProposal.getName());
		result.setCity(newClubProposal.getCity());
		result.setLogo(newClubProposal.getLogo().getImage());
		result.setLogoName(newClubProposal.getLogo().getName());
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
		insertMessageDTO.setProPicName(user.getProPicFile().getName());
		insertMessageDTO.setProPic(user.getProPicFile().getImage());
		return insertMessageDTO;
	}
	
	public <T extends InputVerifyAvailabilityMessageDTO> Set<Slot> convertInputVerifyAvailabilityMessageDTOToSlots(T  inputMessage) 
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
		int min=28;
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
			if (max>=26) {
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
		
		if (max<26) {
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
	
	public boolean lessThanHalfAnHourLeft(PadelMatch match) {
		// Recupero la data della partita
		LocalDate date=match.getDate();
		
		// Trovo l'id minimo tra gli slots, che sicuramente non può essere maggiore dell'ultimo slot
		Integer min=28;
		for (Integer i:match.getSlots().stream().map(slot->slot.getId()).collect(Collectors.toSet())) {
			if(i<=min) {
				min=i;
			}
			
		}
		
		// Dallo slot minimo, ricavo ora e minuto
		Integer hourStart=Slot.convertIdToSlot(min).getHour();
		Integer minuteStart=Slot.convertIdToSlot(min).getMinute();
		
		// Procedo con il confronto di data, ora e minuto
		if (date.compareTo(LocalDate.now())>0) {
			// la partita è in una data futura
			return false;
		} else if (date.compareTo(LocalDate.now())<0) {
			// la partita è in una data passata
			return true;
		}
		
		// Se sono qui, la partita è oggi. Recupero ora e minuto attuali
		Integer hourNow=LocalDateTime.now().getHour();
		Integer minuteNow=LocalDateTime.now().getMinute();
		if (hourStart<hourNow) {
			// la partita è già iniziata
			return true;
		} else if (hourStart-hourNow>1) {
			// manca più di un'ora alla partita
			return false;
		} else if (hourStart==hourNow+1) {
			// ora potrebbero essere, per esempio, le 15.59 e la partita inizia alle 16.10 
			// --> in questo caso, ai 10 minuti delle 16.10 sommo i 60 minuti derivanti dall'ora di differenza
			return (minuteStart+60-minuteNow<=30);
		} else {
			// l'orario di inizio della partita coincide con l'ora attuale --> mi basta confrontare i minuti
			return (minuteStart-minuteNow<30);
		}
	}
	
	public String allErrorsToString(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return bindingResult.getAllErrors().stream()
					.map(obj->(FieldError) obj).map(fieldError->fieldError.getDefaultMessage()).reduce(
							(message1,message2)->message1+"; \n\r "+message2).get();
		} else {
			return "";
		}
	}
	
	public Boolean thePlayerIsPlayingSomewhereElseAtThatTime(Player player, PadelMatch match) {
		Set<Slot> matchSlots=match.getSlots();
		Set<Slot> slotsInWhichPlayerIsBusy=new HashSet<>();
		if (player.getMatches()!=null && player.getMatches().size()>0) {
			player.getMatches().stream().filter(m->m.getDate().compareTo(match.getDate())==0 && m.getId()!=match.getId())
			.forEach(m->slotsInWhichPlayerIsBusy.addAll(m.getSlots()));			
		}
		
		if (player.getMatchesJoined()!=null && player.getMatchesJoined().size()>0) {
			player.getMatchesJoined().stream().filter(m->m.getDate().compareTo(match.getDate())==0 && m.getId()!=match.getId())
				.forEach(m->slotsInWhichPlayerIsBusy.addAll(m.getSlots()));
			for (Slot s:matchSlots) {
				if (slotsInWhichPlayerIsBusy.contains(s)) {
					return true;
				}
			}
		}

		return false;
	}

	public String getHourStartFromSlots(Set<Slot> slots) {
		// Trovo l'id minimo tra gli slots, che sicuramente non può essere maggiore dell'ultimo slot
		Integer min=28;
		for (Integer i:slots.stream().map(slot->slot.getId()).collect(Collectors.toSet())) {
			if(i<=min) {
				min=i;
			}
					
		}
				
		// Dallo slot minimo, ricavo l'ora
		return Slot.convertIdToSlot(min).getHour().toString();
	}

	public String getMinuteStartFromSlots(Set<Slot> slots) {
		// Trovo l'id minimo tra gli slots, che sicuramente non può essere maggiore dell'ultimo slot
		Integer min=28;
		for (Integer i:slots.stream().map(slot->slot.getId()).collect(Collectors.toSet())) {
			if(i<=min) {
				min=i;
			}
					
		}
				
		// Dallo slot minimo, ricavo il minuto
		return Slot.convertIdToSlot(min).getMinute().toString();
	}

	public String getHourEndFromSlots(Set<Slot> slots) {
		// Trovo l'id massimo tra gli slots, che sicuramente non può essere minore del primo slot
		Integer max=1;
		for (Integer i:slots.stream().map(slot->slot.getId()).collect(Collectors.toSet())) {
			if(i>=max) {
				max=i;
			}
					
		}
				
		// Dallo slot massimo, ricavo l'ora
		return Slot.convertIdToSlot(max).getHour().toString();
	}

	public String getMinuteEndFromSlots(Set<Slot> slots) {
		// Trovo l'id massimo tra gli slots, che sicuramente non può essere minore del primo slot
		Integer max=1;
		for (Integer i:slots.stream().map(slot->slot.getId()).collect(Collectors.toSet())) {
			if(i>=max) {
				max=i;
			}
					
		}
				
		// Dallo slot massimo, ricavo il minuto
		return Slot.convertIdToSlot(max).getMinute().toString();
	}
	
}
