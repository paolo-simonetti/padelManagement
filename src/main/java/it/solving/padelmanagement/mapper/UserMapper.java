package it.solving.padelmanagement.mapper;

import java.io.IOException;
import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.solving.padelmanagement.dto.UserDTO;
import it.solving.padelmanagement.dto.message.insert.UserInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.UserUpdateMessageDTO;
import it.solving.padelmanagement.model.Image;
import it.solving.padelmanagement.model.Role;
import it.solving.padelmanagement.model.User;
import it.solving.padelmanagement.util.MyUtil;

@Component
public class UserMapper extends AbstractMapper<User, UserDTO, UserInsertMessageDTO, UserUpdateMessageDTO> {

	@Override
	public UserDTO convertEntityToDTO(User entity) {
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
		
		if (StringUtils.isNotBlank(entity.getUsername())) {
			dto.setUsername(entity.getUsername());
		}
		
		if (StringUtils.isNotBlank(entity.getPassword())) {
			dto.setPassword(entity.getPassword());
		}
		
		if (StringUtils.isNotBlank(entity.getMailAddress())) {
			dto.setMailAddress(entity.getMailAddress());
		}
		
		if(StringUtils.isNotBlank(entity.getMobile())) {
			dto.setMobile(entity.getMobile());
		}
		
		Image proPic=MyUtil.initializeAndUnproxy(entity.getProPicFile());
		
		if(StringUtils.isNotBlank(proPic.getName())) {
			dto.setProPicName(entity.getProPicFile().getName());
		}
		
		if(proPic.getImage()!=null && !proPic.getImage().isEmpty()) {
			dto.setProPic(proPic.getImage());
		}
		
		if(entity.getRole()!=null) {
			dto.setRole(entity.getRole().getStringRole());
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
	public User convertDTOToEntity(UserDTO dto) {
		if(dto==null) {
			return null;			
		}
		
		User entity=new User();
		
		if (StringUtils.isNotBlank(dto.getId())) {
			entity.setId(Long.parseLong(dto.getId()));
		}
		
		if (StringUtils.isNotBlank(dto.getName())) {
			entity.setName(dto.getName());
		}
		
		if (StringUtils.isNotBlank(dto.getSurname())) {
			entity.setSurname(dto.getSurname());
		}

		if (StringUtils.isNotBlank(dto.getDateOfBirth())) {
			entity.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
		}
		
		if (StringUtils.isNotBlank(dto.getUsername())) {
			entity.setUsername(dto.getUsername());
		}
		
		if (StringUtils.isNotBlank(dto.getMailAddress())) {
			entity.setMailAddress(dto.getMailAddress());
		}
		
		if (StringUtils.isNotBlank(dto.getRole())) {
			entity.setRole(Role.conversionRole.get(dto.getRole()));
		}

		if (StringUtils.isNotBlank(dto.getMobile())) {
			entity.setMobile(dto.getMobile());
		}
		
		Image proPic=new Image();
		
		if (dto.getProPic()!=null) {
			try {
				proPic.setImage(dto.getProPic());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (StringUtils.isNotBlank(dto.getProPicName())) {
			proPic.setName(dto.getProPicName());
		}
		
		entity.setProPicFile(proPic);
		
		return entity;
	}

	@Override
	public User convertInsertMessageDTOToEntity(UserInsertMessageDTO insertMessageDTO) {
		if(insertMessageDTO==null) {
			return null;			
		}
		
		User entity=new User();
		
		if(StringUtils.isNotBlank(insertMessageDTO.getName())) {
			entity.setName(insertMessageDTO.getName());
		}
		
		if(StringUtils.isNotBlank(insertMessageDTO.getSurname())) {
			entity.setSurname(insertMessageDTO.getSurname());
		}
		
		if(StringUtils.isNotBlank(insertMessageDTO.getDateOfBirth())) {
			entity.setDateOfBirth(LocalDate.parse(insertMessageDTO.getDateOfBirth()));
		}
		
		if(StringUtils.isNotBlank(insertMessageDTO.getUsername())) {
			entity.setUsername(insertMessageDTO.getUsername());
		}
		
		if(StringUtils.isNotBlank(insertMessageDTO.getPassword())) {
			entity.setPassword(insertMessageDTO.getPassword());
		}
		
		if(StringUtils.isNotBlank(insertMessageDTO.getMailAddress())) {
			entity.setMailAddress(insertMessageDTO.getMailAddress());
		}
		
		if(StringUtils.isNotBlank(insertMessageDTO.getMobile())) {
			entity.setMobile(insertMessageDTO.getMobile());
		}
		
		entity.setRole(Role.ROLE_GUEST);

		Image proPic=new Image();
		
		if (insertMessageDTO.getProPic()!=null) {
			try {
				proPic.setImage(insertMessageDTO.getProPic());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (StringUtils.isNotBlank(insertMessageDTO.getProPicName())) {
			proPic.setName(insertMessageDTO.getProPicName());
		}
		
		entity.setProPicFile(proPic);

		return entity;
	}

	@Override
	public User convertUpdateMessageDTOToEntity(UserUpdateMessageDTO updateMessageDTO) {
		if(updateMessageDTO==null) {
			return null;			
		}
		
		User entity=new User();
		
		if(StringUtils.isNotBlank(updateMessageDTO.getId())) {
			entity.setId(Long.parseLong(updateMessageDTO.getId()));
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getName())) {
			entity.setName(updateMessageDTO.getName());
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getSurname())) {
			entity.setSurname(updateMessageDTO.getSurname());
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getDateOfBirth())) {
			entity.setDateOfBirth(LocalDate.parse(updateMessageDTO.getDateOfBirth()));
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getUsername())) {
			entity.setUsername(updateMessageDTO.getUsername());
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getPassword())) {
			entity.setPassword(updateMessageDTO.getPassword());
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getMailAddress())) {
			entity.setMailAddress(updateMessageDTO.getMailAddress());
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getMobile())) {
			entity.setMobile(updateMessageDTO.getMobile());
		}
		
		Image proPic=new Image();
		
		if (updateMessageDTO.getProPic()!=null) {
			try {
				proPic.setImage(updateMessageDTO.getProPic());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (StringUtils.isNotBlank(updateMessageDTO.getProPicName())) {
			proPic.setName(updateMessageDTO.getProPicName());
		}
		
		entity.setProPicFile(proPic);
		
		
		entity.setRole(Role.ROLE_GUEST);
		
		return entity;
	}

}
