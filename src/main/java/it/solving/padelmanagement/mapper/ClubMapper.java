package it.solving.padelmanagement.mapper;

import java.io.IOException;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.solving.padelmanagement.dto.ClubDTO;
import it.solving.padelmanagement.dto.message.insert.ClubInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.ClubUpdateMessageDTO;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.Image;

@Component
public class ClubMapper extends AbstractMapper<Club, ClubDTO, ClubInsertMessageDTO, ClubUpdateMessageDTO> {

	@Override
	public ClubDTO convertEntityToDTO(Club entity) {
		if(entity==null) {
			return null;			
		}
		
		ClubDTO dto=new ClubDTO();
		
		if(entity.getId()!=null) {
			dto.setId(entity.getId().toString());
		}
		
		if(StringUtils.isNotBlank(entity.getName())) {
			dto.setName(entity.getName());
		}
		
		if(StringUtils.isNotBlank(entity.getCity())) {
			dto.setCity(entity.getCity());
		}
		
		if(entity.getLogo()!=null) {
			dto.setLogo(entity.getLogo().getImage());
			dto.setLogoName(entity.getLogo().getName());
		}
		
		if(entity.getCourts()!=null && entity.getCourts().size()>0) {
			dto.setCourtsIds(entity.getCourts().stream().map(court->court.getId().toString()).collect(Collectors.toSet()));
		}
		
		if(entity.getAdmin()!=null) {
			dto.setAdminId(entity.getAdmin().getId().toString());
		}
		
		if(entity.getNotices()!=null && entity.getNotices().size()>0) {
			dto.setNoticesIds(entity.getNotices().stream().map(notice->notice.getId().toString()).collect(Collectors.toSet()));
		}
		
		if(entity.getJoinProposals()!=null && entity.getJoinProposals().size()>0) {
			dto.setJoinProposalsIds(entity.getJoinProposals().stream().map(proposal->proposal.getId().toString()).collect(Collectors.toSet()));
		}
		
		if(entity.getPlayers()!=null && entity.getPlayers().size()>0) {
			dto.setPlayersIds(entity.getPlayers().stream().map(player->player.getId().toString()).collect(Collectors.toSet()));
		}
		
		return dto;
	}

	@Override
	public Club convertDTOToEntity(ClubDTO dto) {
		if(dto==null) {
			return null;			
		}
		
		Club entity=new Club();
		
		if(StringUtils.isNotBlank(dto.getId())) {
			entity.setId(Long.parseLong(dto.getId()));
		} 
		
		if(StringUtils.isNotBlank(dto.getName())) {
			entity.setName(dto.getName());
		}
		
		if(StringUtils.isNotBlank(dto.getCity())) {
			entity.setCity(dto.getCity());
		}
		
		Image logo=new Image();
		
		if(dto.getLogo()!=null) {
			try {
				logo.setImage(dto.getLogo());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(StringUtils.isNotBlank(dto.getLogoName())) {
			logo.setName(dto.getLogoName());
		}
		
		entity.setLogo(logo);
				
		return entity;
	}

	@Override
	public Club convertInsertMessageDTOToEntity(ClubInsertMessageDTO insertMessageDTO) {
		if(insertMessageDTO==null) {
			return null;			
		}
		
		Club entity = new Club();
		
		if(StringUtils.isNotBlank(insertMessageDTO.getName())) {
			entity.setName(insertMessageDTO.getName());
		}
		
		if(StringUtils.isNotBlank(insertMessageDTO.getCity())) {
			entity.setCity(insertMessageDTO.getCity());
		}
		
		Image logo=new Image();
		
		if(insertMessageDTO.getLogo()!=null) {
			try {
				logo.setImage(insertMessageDTO.getLogo());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(StringUtils.isNotBlank(insertMessageDTO.getLogoName())) {
			logo.setName(insertMessageDTO.getLogoName());
		}
		
		entity.setLogo(logo);
		
		return entity;
	}

	@Override
	public Club convertUpdateMessageDTOToEntity(ClubUpdateMessageDTO updateMessageDTO) {
		if(updateMessageDTO==null) {
			return null;			
		}
		
		Club entity=new Club();
		
		if(StringUtils.isNotBlank(updateMessageDTO.getId())) {
			entity.setId(Long.parseLong(updateMessageDTO.getId()));
		} 
		
		if(StringUtils.isNotBlank(updateMessageDTO.getName())) {
			entity.setName(updateMessageDTO.getName());
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getCity())) {
			entity.setCity(updateMessageDTO.getCity());
		}
		
		Image logo=new Image();
		
		if(updateMessageDTO.getLogo()!=null) {
			try {
				logo.setImage(updateMessageDTO.getLogo());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getLogoName())) {
			logo.setName(updateMessageDTO.getLogoName());
		}
		
		entity.setLogo(logo);
				
		return entity;
	}
	
}
