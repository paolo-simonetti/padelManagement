package it.solving.padelmanagement.mapper;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.solving.padelmanagement.dto.NoticeDTO;
import it.solving.padelmanagement.dto.message.insert.NoticeInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.NoticeUpdateMessageDTO;
import it.solving.padelmanagement.model.Notice;

@Component
public class NoticeMapper extends AbstractMapper<Notice, NoticeDTO, NoticeInsertMessageDTO, NoticeUpdateMessageDTO> {

	@Override
	public NoticeDTO convertEntityToDTO(Notice entity) {
		if(entity==null) {
			return null;			
		}
		
		NoticeDTO dto=new NoticeDTO();
		
		if(entity.getId()!=null) {
			dto.setId(entity.getId().toString());
		}
		
		if(StringUtils.isNotBlank(entity.getMessage())) {
			dto.setMessage(entity.getMessage());
		}
		
		if(entity.getCreationDate()!=null) {
			dto.setCreationDate(entity.getCreationDate().toString());
		}
		
		if (entity.getClub()!=null) {
			dto.setClubId(entity.getClub().getId().toString());
		}
		
		return dto;
	}

	@Override
	public Notice convertDTOToEntity(NoticeDTO dto) {
		if(dto==null) {
			return null;			
		}
		
		Notice entity=new Notice();
		
		if(StringUtils.isNotBlank(dto.getId())) {
			entity.setId(Long.parseLong(dto.getId()));
		}
		
		if(StringUtils.isNotBlank(dto.getMessage())) {
			entity.setMessage(dto.getMessage());
		}
		
		if(StringUtils.isNotBlank(dto.getCreationDate())) {
			entity.setCreationDate(LocalDate.parse(dto.getCreationDate()));
		}
		
		return entity;
	}

	@Override
	public Notice convertInsertMessageDTOToEntity(NoticeInsertMessageDTO insertMessageDTO) {
		if(insertMessageDTO==null) {
			return null;			
		}
		
		Notice entity=new Notice();
		
		if(StringUtils.isNotBlank(insertMessageDTO.getMessage())) {
			entity.setMessage(insertMessageDTO.getMessage());
		}
		
		return entity;
	}

	@Override
	public Notice convertUpdateMessageDTOToEntity(NoticeUpdateMessageDTO updateMessageDTO) {
		if(updateMessageDTO==null) {
			return null;
		}
		
		Notice entity=new Notice();
		
		if(StringUtils.isNotBlank(updateMessageDTO.getId())) {
			entity.setId(Long.parseLong(updateMessageDTO.getId()));
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getMessage())) {
			entity.setMessage(updateMessageDTO.getMessage());
		}
		
		return entity;
	}

}
