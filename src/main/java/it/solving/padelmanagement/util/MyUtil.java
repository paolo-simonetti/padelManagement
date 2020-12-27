package it.solving.padelmanagement.util;

import org.springframework.stereotype.Component;

import it.solving.padelmanagement.dto.message.insert.ClubInsertMessageDTO;
import it.solving.padelmanagement.dto.message.insert.UserInsertMessageDTO;
import it.solving.padelmanagement.model.NewClubProposal;

@Component
public class MyUtil {

	public UserInsertMessageDTO getAdminFromNewClubProposal(NewClubProposal newClubProposal) {
		UserInsertMessageDTO result=new UserInsertMessageDTO();
		result.setName(newClubProposal.getCreator().getName());
		result.setSurname(newClubProposal.getCreator().getSurname());
		result.setDateOfBirth(newClubProposal.getCreator().getDateOfBirth().toString());
		result.setMailAddress(newClubProposal.getCreator().getMailAddress());
		result.setMobile(newClubProposal.getCreator().getMobile());
		return result;
	}
	
	public ClubInsertMessageDTO getClubFromNewClubProposal(NewClubProposal newClubProposal) {
		ClubInsertMessageDTO result=new ClubInsertMessageDTO();
		result.setName(newClubProposal.getName());
		result.setCity(newClubProposal.getCity());
		result.setLogo(newClubProposal.getLogo());
		return result;
	}
	
}
