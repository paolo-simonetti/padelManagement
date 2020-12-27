package it.solving.padelmanagement.mapper;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.solving.padelmanagement.dto.AdminDTO;
import it.solving.padelmanagement.dto.message.insert.UserInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.UserUpdateMessageDTO;
import it.solving.padelmanagement.model.Admin;
import it.solving.padelmanagement.model.User;

@Component
public class AdminMapper extends AbstractMapper<Admin, AdminDTO, UserInsertMessageDTO,UserUpdateMessageDTO> {

	@Override
	public AdminDTO convertEntityToDTO(Admin entity) {
		if(entity==null) {
			return null;			
		}
		
		AdminDTO dto=new AdminDTO();
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
		
		if(entity.getClub()!=null) {
			dto.setClubId(entity.getClub().getId().toString());
		}
		
		return dto;
	}

	@Override
	public Admin convertDTOToEntity(AdminDTO dto) {
		if(dto==null) {
			return null;			
		}
		
		Admin entity=new Admin();
		
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
		
		if (StringUtils.isNotBlank(dto.getMailAddress())) {
			entity.setMailAddress(dto.getMailAddress());
		}

		if (StringUtils.isNotBlank(dto.getMobile())) {
			entity.setMobile(dto.getMobile());
		}
		
		return entity;
	}

	@Override
	public Admin convertInsertMessageDTOToEntity(UserInsertMessageDTO insertMessageDTO) {
		if(insertMessageDTO==null) {
			return null;			
		}
		
		Admin entity=new Admin();
		
		if(StringUtils.isNotBlank(insertMessageDTO.getName())) {
			entity.setName(insertMessageDTO.getName());
		}
		
		if(StringUtils.isNotBlank(insertMessageDTO.getSurname())) {
			entity.setSurname(insertMessageDTO.getSurname());
		}
		
		if(StringUtils.isNotBlank(insertMessageDTO.getDateOfBirth())) {
			entity.setDateOfBirth(LocalDate.parse(insertMessageDTO.getDateOfBirth()));
		}
		
		if(StringUtils.isNotBlank(insertMessageDTO.getMailAddress())) {
			entity.setMailAddress(insertMessageDTO.getMailAddress());
		}
		
		if(StringUtils.isNotBlank(insertMessageDTO.getMobile())) {
			entity.setMobile(insertMessageDTO.getMobile());
		}
		
		return entity;
	}

	@Override
	public Admin convertUpdateMessageDTOToEntity(UserUpdateMessageDTO updateMessageDTO) {
		if(updateMessageDTO==null) {
			return null;			
		}
		
		Admin entity=new Admin();
		
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
		
		if(StringUtils.isNotBlank(updateMessageDTO.getMailAddress())) {
			entity.setMailAddress(updateMessageDTO.getMailAddress());
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getMobile())) {
			entity.setMobile(updateMessageDTO.getMobile());
		}
		
		return entity;
	}
	
}
