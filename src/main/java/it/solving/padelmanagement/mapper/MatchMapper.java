package it.solving.padelmanagement.mapper;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.solving.padelmanagement.dto.MatchDTO;
import it.solving.padelmanagement.dto.message.insert.MatchInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.MatchUpdateMessageDTO;
import it.solving.padelmanagement.model.Match;

@Component
public class MatchMapper extends AbstractMapper<Match, MatchDTO, MatchInsertMessageDTO, MatchUpdateMessageDTO> {

	@Override
	public MatchDTO convertEntityToDTO(Match entity) {
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
		
		if(entity.getPayed()!=null) {
			dto.setPayed(entity.getPayed().toString());
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
	public Match convertDTOToEntity(MatchDTO dto) {
		if(dto==null) {
			return null;			
		}
		
		Match entity=new Match();
		
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
	public Match convertInsertMessageDTOToEntity(MatchInsertMessageDTO insertMessageDTO) {
		if(insertMessageDTO==null) {
			return null;			
		}
		
		Match entity=new Match();
		
		if(StringUtils.isNotBlank(insertMessageDTO.getDate())) {
			entity.setDate(LocalDate.parse(insertMessageDTO.getDate()));
		}
		
		if(StringUtils.isNotBlank(insertMessageDTO.getPayed())) {
			entity.setPayed(Boolean.parseBoolean(insertMessageDTO.getPayed()));
		}
		
		if(StringUtils.isNotBlank(insertMessageDTO.getMissingPlayers())) {
			entity.setMissingPlayers(Integer.parseInt(insertMessageDTO.getMissingPlayers()));
		}
		
		return entity;
	}

	@Override
	public Match convertUpdateMessageDTOToEntity(MatchUpdateMessageDTO updateMessageDTO) {
		
		if(updateMessageDTO==null) {
			return null;
		}
		
		Match entity=new Match();
		
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

}
