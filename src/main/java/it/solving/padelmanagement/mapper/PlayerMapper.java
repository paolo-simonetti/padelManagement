package it.solving.padelmanagement.mapper;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.solving.padelmanagement.dto.PlayerDTO;
import it.solving.padelmanagement.dto.message.insert.PlayerInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.PlayerUpdateMessageDTO;
import it.solving.padelmanagement.model.Player;

@Component
public class PlayerMapper extends AbstractMapper<Player, PlayerDTO, PlayerInsertMessageDTO, PlayerUpdateMessageDTO> {

	@Override
	public PlayerDTO convertEntityToDTO(Player entity) {
		if(entity==null) {
			return null;			
		}
		
		PlayerDTO dto=new PlayerDTO();
		
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
		
		if (entity.getLevel()!=null) {
			dto.setLevel(entity.getLevel().toString());
		}
		
		if (entity.getClub()!=null) {
			dto.setClubId(entity.getClub().getId().toString());
		}
		
		if (entity.getMatches()!=null && entity.getMatches().size()>0) {
			dto.setMatchesIds(entity.getMatches().stream().map(match->
				match.getId().toString()).collect(Collectors.toSet()));
		}
		
		return dto;
	}

	@Override
	public Player convertDTOToEntity(PlayerDTO dto) {
		if(dto==null) {
			return null;
		}
		
		Player entity=new Player();
		
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
		
		if (dto.getProPicFile()!=null && dto.getProPicFile().size()>0) {
			entity.setProPicFile(dto.getProPicFile());
		}
		
		if (StringUtils.isNotBlank(dto.getLevel())) {
			entity.setLevel(Integer.parseInt(dto.getLevel()));
		}
		
		return entity;
	}

	@Override
	public Player convertInsertMessageDTOToEntity(PlayerInsertMessageDTO insertMessageDTO) {
		if(insertMessageDTO==null) {
			return null;			
		}
		
		Player entity=new Player();
		
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
		
		if(StringUtils.isNotBlank(insertMessageDTO.getLevel())) {
			entity.setLevel(Integer.parseInt(insertMessageDTO.getLevel()));
		}
		
		return entity;
	}

	@Override
	public Player convertUpdateMessageDTOToEntity(PlayerUpdateMessageDTO updateMessageDTO) {
		if(updateMessageDTO==null) {
			return null;			
		}
		
		Player entity=new Player();
		
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
		
		if(StringUtils.isNotBlank(updateMessageDTO.getLevel())) {
			entity.setLevel(Integer.parseInt(updateMessageDTO.getLevel()));
		}
		
		return entity;
	}

}
