package it.solving.padelmanagement.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.solving.padelmanagement.dto.SlotDTO;
import it.solving.padelmanagement.dto.message.update.SlotUpdateMessageDTO;
import it.solving.padelmanagement.model.Slot;

@Component
public class SlotMapper {

	public SlotDTO convertEntityToDTO (Slot entity) {
		if (entity==null) {
			return null;
		}
		
		SlotDTO dto=new SlotDTO();
		
		if(entity.getId()!=null) {
			dto.setId(entity.getId().toString());
		}
		
		if(entity.getHour()!=null) {
			dto.setHour(entity.getHour().toString());
		}
		
		if(entity.getMinute()!=null) {
			dto.setMinute(entity.getMinute().toString());
		}
		
		if(entity.getMatches()!=null && entity.getMatches().size()>0) {
			dto.setMatchesIds(entity.getMatches().stream().map(match->
				match.getId().toString()).collect(Collectors.toSet()));
		}
		
		return dto;
	}
	
	public Set<SlotDTO> convertEntityToDTO (Set<Slot> entities) {
		return entities.stream().map(this::convertEntityToDTO).collect(Collectors.toSet());
	}
	
	public Slot convertDTOToEntity(SlotDTO dto) {
		if(dto==null) {
			return null;
		}
		
		if(StringUtils.isNotBlank(dto.getId())) {
			return Slot.convertIdToSlot(Integer.parseInt(dto.getId()));
		} else if(StringUtils.isNotBlank(dto.getHour())&&StringUtils.isNotBlank(dto.getMinute())) {
			return Slot.convertHourAndMinuteToSlot(Integer.parseInt(dto.getHour()),Integer.parseInt(dto.getMinute()));
		} else {
			return null;
		}
		
	}

	public Set<Slot> convertDTOToEntity (Set<SlotDTO> dtos) {
		return dtos.stream().map(this::convertDTOToEntity).collect(Collectors.toSet());
	}

	
	public Slot convertUpdateMessageDTOToEntity(SlotUpdateMessageDTO updateMessageDTO) {
		if(updateMessageDTO==null) {
			return null;
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getId())) {
			return Slot.convertIdToSlot(Integer.parseInt(updateMessageDTO.getId()));
		} else if(StringUtils.isNotBlank(updateMessageDTO.getHour()) && 
				StringUtils.isNotBlank(updateMessageDTO.getMinute())) {
			return Slot.convertHourAndMinuteToSlot(Integer.parseInt(updateMessageDTO.getHour()),
					Integer.parseInt(updateMessageDTO.getMinute()));
		} else {
			return null;
		}
	}
	
}
