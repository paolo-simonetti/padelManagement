package it.solving.padelmanagement.mapper;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.solving.padelmanagement.dto.MatchDTO;
import it.solving.padelmanagement.dto.message.createpadelmatch.InputValidateAndInsertInputMessageDTO;
import it.solving.padelmanagement.dto.message.createpadelmatch.InputValidateAndUpdateInputMessageDTO;
import it.solving.padelmanagement.dto.message.createpadelmatch.OutputMatchMailMessageDTO;
import it.solving.padelmanagement.dto.message.insert.MatchInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.MatchUpdateMessageDTO;
import it.solving.padelmanagement.exception.VerifyAvailabilityException;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.util.MyUtil;

@Component
public class MatchMapper extends AbstractMapper<PadelMatch, MatchDTO, MatchInsertMessageDTO, MatchUpdateMessageDTO> {
	
	@Autowired
	private MyUtil myUtil;
	
	@Override
	public MatchDTO convertEntityToDTO(PadelMatch entity) {
		if(entity==null) {
			return null;			
		}
		
		MatchDTO dto=new MatchDTO();
		
		if(entity.getId()!=null) {
			dto.setId(entity.getId().toString());
		}
		
		if(entity.getDate()!=null) {
			dto.setDate(entity.getDate().toString());
		}
		
		if(entity.isPayed()!=null) {
			dto.setPayed(entity.isPayed().toString());
		}
		
		if(entity.getMissingPlayers()!=null) {
			dto.setMissingPlayers(entity.getMissingPlayers().toString());
		}
		
		if(entity.getOtherPlayers()!=null && entity.getOtherPlayers().size()>0) {
			dto.setOtherPlayersIds(entity.getOtherPlayers().stream()
					.map(player->player.getId().toString()).collect(Collectors.toSet()));
		} 
		
		if(entity.getCreator()!=null) {
			dto.setCreatorId(entity.getCreator().getId().toString());
		}

		if(entity.getSlots()!=null && entity.getSlots().size()>0) {
			dto.setSlotsIds(entity.getSlots().stream()
					.map(slot->slot.getId().toString()).collect(Collectors.toSet()));
		} 

		if(entity.getCourt()!=null) {
			dto.setCourtId(entity.getCourt().getId().toString());
		}
		
		return dto;
	}

	@Override
	public PadelMatch convertDTOToEntity(MatchDTO dto) {
		if(dto==null) {
			return null;			
		}
		
		PadelMatch entity=new PadelMatch();
		
		if(StringUtils.isNotBlank(dto.getId())) {
			entity.setId(Long.parseLong(dto.getId()));
		}
		
		if(StringUtils.isNotBlank(dto.getDate())) {
			entity.setDate(LocalDate.parse(dto.getDate()));
		}
		
		if(StringUtils.isNotBlank(dto.getPayed())) {
			entity.setPayed(Boolean.parseBoolean(dto.getPayed()));
		}
		
		if(StringUtils.isNotBlank(dto.getMissingPlayers())) {
			entity.setMissingPlayers(Integer.parseInt(dto.getMissingPlayers()));
		}
		
		return entity;
	}

	@Override
	public PadelMatch convertInsertMessageDTOToEntity(MatchInsertMessageDTO insertMessageDTO) {
		if(insertMessageDTO==null) {
			return null;			
		}
		
		PadelMatch entity=new PadelMatch();
		
		if(StringUtils.isNotBlank(insertMessageDTO.getDate())) {
			entity.setDate(LocalDate.parse(insertMessageDTO.getDate()));
		}
		
		entity.setPayed(false);
		
		if(StringUtils.isNotBlank(insertMessageDTO.getMissingPlayers())) {
			entity.setMissingPlayers(Integer.parseInt(insertMessageDTO.getMissingPlayers()));
		}
		
		return entity;
	}

	@Override
	public PadelMatch convertUpdateMessageDTOToEntity(MatchUpdateMessageDTO updateMessageDTO) {
		
		if(updateMessageDTO==null) {
			return null;
		}
		
		PadelMatch entity=new PadelMatch();
		
		if(StringUtils.isNotBlank(updateMessageDTO.getId())) {
			entity.setId(Long.parseLong(updateMessageDTO.getId()));
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getDate())) {
			entity.setDate(LocalDate.parse(updateMessageDTO.getDate()));
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getPayed())) {
			entity.setPayed(Boolean.parseBoolean(updateMessageDTO.getPayed()));
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getMissingPlayers())) {
			entity.setMissingPlayers(Integer.parseInt(updateMessageDTO.getMissingPlayers()));
		}
		
		return entity;
	}

	public PadelMatch convertInputValidateAndInsertInputMessageDTOToPadelMatch(
			InputValidateAndInsertInputMessageDTO inputMessage) throws VerifyAvailabilityException {
		PadelMatch match=new PadelMatch();
		match.setDate(LocalDate.parse(inputMessage.getDate()));
		match.setPayed(false);
		match.setMissingPlayers(Integer.parseInt(inputMessage.getMissingPlayers()));
		return match;
	}
	
	public PadelMatch convertInputValidateAndUpdateInputMessageDTOToPadelMatch(
			InputValidateAndUpdateInputMessageDTO inputMessage) throws VerifyAvailabilityException {
		PadelMatch match=new PadelMatch();
		match.setId(Long.parseLong(inputMessage.getMatchId()));
		match.setDate(LocalDate.parse(inputMessage.getDate()));
		return match;
	}
	
	public OutputMatchMailMessageDTO convertEntityToOutputMatchMailMessageDTO(PadelMatch entity) {
		OutputMatchMailMessageDTO message = new OutputMatchMailMessageDTO();
		
		if(entity.getDate()!=null) {
			message.setDate(entity.getDate().toString());			
		}
		
		if(entity.isPayed()!=null) {
			message.setPayed(entity.isPayed().toString());
		}
		
		if(entity.getMissingPlayers()!=null) {
			message.setMissingPlayers(entity.getMissingPlayers().toString());
		}
		
		if(entity.getOtherPlayers()!=null && entity.getOtherPlayers().size()>0) {
			message.setOtherPlayersUsername(entity.getOtherPlayers().stream()
					.map(player->player.getUsername()).collect(Collectors.toSet()));
		} 
		
		if(entity.getCreator()!=null) {
			message.setUsernameCreator(entity.getCreator().getUsername());
		}
		
		if(entity.getCourt()!=null) {
			message.setCourtName(entity.getCourt().getName());
		}
		
		if (entity.getSlots()!=null && entity.getSlots().size()>0) {
			message.setHourStart(myUtil.getHourStartFromSlots(entity.getSlots()));
			message.setMinuteStart(myUtil.getMinuteStartFromSlots(entity.getSlots()));
			message.setHourEnd(myUtil.getHourEndFromSlots(entity.getSlots()));
			message.setMinuteEnd(myUtil.getMinuteEndFromSlots(entity.getSlots()));			
		}
		return message;
	}

}
