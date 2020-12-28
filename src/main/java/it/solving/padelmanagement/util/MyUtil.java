package it.solving.padelmanagement.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import it.solving.padelmanagement.dto.message.createpadelmatch.InputVerifyAvailabilityMessageDTO;
import it.solving.padelmanagement.dto.message.insert.ClubInsertMessageDTO;
import it.solving.padelmanagement.dto.message.insert.PlayerInsertMessageDTO;
import it.solving.padelmanagement.dto.message.insert.UserInsertMessageDTO;
import it.solving.padelmanagement.exception.VerifyAvailabilityException;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.JoinProposal;
import it.solving.padelmanagement.model.NewClubProposal;
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
}
