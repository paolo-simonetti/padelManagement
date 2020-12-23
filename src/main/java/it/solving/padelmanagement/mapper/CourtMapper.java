package it.solving.padelmanagement.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.solving.padelmanagement.dto.CourtDTO;
import it.solving.padelmanagement.dto.message.insert.CourtInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.CourtUpdateMessageDTO;
import it.solving.padelmanagement.model.Court;

@Component
public class CourtMapper extends AbstractMapper<Court, CourtDTO, CourtInsertMessageDTO, CourtUpdateMessageDTO> {

	@Override
	public CourtDTO convertEntityToDTO(Court entity) {
		if(entity==null) {
			return null;			
		}
		
		CourtDTO dto=new CourtDTO();
		
		if (entity.getId()!=null) {
			dto.setId(entity.getId().toString());
		}
		
		if (StringUtils.isNotBlank(entity.getName())) {
			dto.setName(entity.getName());
		}
		
		if (entity.getClub()!=null) {
			dto.setClubId(entity.getClub().getId().toString());
		}
		
		return dto;
	}

	@Override
	public Court convertDTOToEntity(CourtDTO dto) {
		if(dto==null) {
			return null;			
		}
		
		Court entity=new Court();
		
		if(StringUtils.isNotBlank(dto.getId())) {
			entity.setId(Long.parseLong(dto.getId()));
		}
		
		if(StringUtils.isNotBlank(dto.getName())) {
			entity.setName(dto.getName());
		}
		
		return entity;
	}

	@Override
	public Court convertInsertMessageDTOToEntity(CourtInsertMessageDTO insertMessageDTO) {
		if(insertMessageDTO==null) {
			return null;			
		}
		
		Court entity=new Court();
		
		if (StringUtils.isNotBlank(entity.getName())) {
			insertMessageDTO.setName(entity.getName());
		}
		
		if (entity.getClub()!=null) {
			insertMessageDTO.setClubId(entity.getClub().getId().toString());
		}
		
		return entity;
	}

	@Override
	public Court convertUpdateMessageDTOToEntity(CourtUpdateMessageDTO updateMessageDTO) {
		if(updateMessageDTO==null) {
			return null;			
		}
		
		Court entity=new Court();
		
		if(StringUtils.isNotBlank(updateMessageDTO.getId())) {
			entity.setId(Long.parseLong(updateMessageDTO.getId()));
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getName())) {
			entity.setName(updateMessageDTO.getName());
		}
		
		return entity;
	}

}
