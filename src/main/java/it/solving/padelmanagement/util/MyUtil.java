package it.solving.padelmanagement.util;

import org.springframework.stereotype.Component;

import it.solving.padelmanagement.dto.message.insert.ClubInsertMessageDTO;
import it.solving.padelmanagement.dto.message.insert.PlayerInsertMessageDTO;
import it.solving.padelmanagement.dto.message.insert.UserInsertMessageDTO;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.JoinProposal;
import it.solving.padelmanagement.model.NewClubProposal;
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
	
}
