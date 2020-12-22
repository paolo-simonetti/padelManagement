package it.solving.padelmanagement.mapper;

import org.apache.commons.lang3.StringUtils;

import it.solving.padelmanagement.dto.UserDTO;
import it.solving.padelmanagement.dto.message.insert.UserInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.UserUpdateMessageDTO;
import it.solving.padelmanagement.model.User;

public class UserMapper extends AbstractMapper<User, UserDTO, UserInsertMessageDTO, UserUpdateMessageDTO> {

	@Override
	public UserDTO convertEntityToDto(User entity) {
		if(entity==null) {
			return null;
		}
		
		UserDTO dto=new UserDTO();
		
		if (entity.getId()!=null) {
			dto.setId(entity.getId().toString());
		}
		
		if (StringUtils.isNotBlank(entity.getName())) {
			dto.setName(entity.getName());
		}
		
		if (StringUtils.isNotBlank(entity.getSurname())) {
			dto.setSurname(entity.getSurname());
		}

		if (entity.getDateOfBirth()!=null) {
			dto.setDateOfBirth(entity.getDateOfBirth().toString());
		}
		
		if (StringUtils.isNotBlank(entity.getMailAddress())) {
			dto.setMailAddress(entity.getMailAddress());
		}
		
		if(StringUtils.isNotBlank(entity.getMobile())) {
			dto.setMobile(entity.getMobile());
		}
		
		if(entity.getProPicFile()!=null && entity.getProPicFile().size()>0) {
			dto.setProPicFile(entity.getProPicFile());
		}
		
		if(entity.getNewClubProposals()!=null && entity.getNewClubProposals().size()>0) {
			entity.getNewClubProposals().stream().forEach(newClubProposal-> 
				dto.addToNewClubProposalsIds(newClubProposal.getId().toString()));
		}
		
		if(entity.getJoinProposals()!=null && entity.getJoinProposals().size()>0) {
			entity.getJoinProposals().stream().forEach(joinProposal-> 
				dto.addToJoinProposalsIds(joinProposal.getId().toString()));
		}
		
		return dto;
	}

	@Override
	public User convertDtoToEntity(UserDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User convertInsertMessageDTOToEntity(UserInsertMessageDTO insertMessageDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User convertUpdateMessageDTOToEntity(UserUpdateMessageDTO updateMessageDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
