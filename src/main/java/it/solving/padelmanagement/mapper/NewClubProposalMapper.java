package it.solving.padelmanagement.mapper;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.solving.padelmanagement.dto.NewClubProposalDTO;
import it.solving.padelmanagement.dto.message.insert.NewClubProposalInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.NewClubProposalUpdateMessageDTO;
import it.solving.padelmanagement.model.Image;
import it.solving.padelmanagement.model.NewClubProposal;
import it.solving.padelmanagement.model.ProposalStatus;

@Component
public class NewClubProposalMapper extends
		AbstractMapper<NewClubProposal, NewClubProposalDTO, NewClubProposalInsertMessageDTO, NewClubProposalUpdateMessageDTO> {

	@Override
	public NewClubProposalDTO convertEntityToDTO(NewClubProposal entity) {
		if(entity==null) {
			return null;			
		}
		
		NewClubProposalDTO dto=new NewClubProposalDTO();
		
		if(entity.getId()!=null) {
			dto.setId(entity.getId().toString());
		}
		
		if(StringUtils.isNotBlank(entity.getName())) {
			dto.setName(entity.getName());
		}
		
		if(StringUtils.isNotBlank(entity.getCity())) {
			dto.setCity(entity.getCity());
		}
		
		if(entity.getProposalStatus()!=null) {
			dto.setProposalStatus(entity.getProposalStatus().getStringProposalStatus());
		}
		
		if(entity.getLogo()!=null) {
			dto.setLogo(entity.getLogo().getImage());
			dto.setLogoName(entity.getLogo().getName());
		}
		
		if(entity.getCreator()!=null) {
			dto.setCreatorId(entity.getCreator().getId().toString());
		}
		
		return dto;

	}

	@Override
	public NewClubProposal convertDTOToEntity(NewClubProposalDTO dto) {
		if(dto==null) {
			return null;			
		}
		
		NewClubProposal entity=new NewClubProposal();
		
		if(dto.getId()!=null) {
			entity.setId(Long.parseLong(dto.getId()));
		}
		
		if(StringUtils.isNotBlank(dto.getName())) {
			entity.setName(dto.getName());
		}
		
		if(StringUtils.isNotBlank(dto.getProposalStatus())) {
			entity.setProposalStatus(ProposalStatus.conversionProposalStatus.get(dto.getProposalStatus()));
		}
		
		if(dto.getCity()!=null) {
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
	public NewClubProposal convertInsertMessageDTOToEntity(NewClubProposalInsertMessageDTO insertMessageDTO) {
		if(insertMessageDTO==null) {
			return null;			
		}
		
		NewClubProposal entity=new NewClubProposal();
		
		if(StringUtils.isNotBlank(insertMessageDTO.getName())) {
			entity.setName(insertMessageDTO.getName());
		}
		
		
		entity.setProposalStatus(ProposalStatus.PENDING);
		
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
	public NewClubProposal convertUpdateMessageDTOToEntity(NewClubProposalUpdateMessageDTO updateMessageDTO) {
		if(updateMessageDTO==null) {
			return null;			
		}
		
		NewClubProposal entity=new NewClubProposal();
		
		if(updateMessageDTO.getId()!=null) {
			entity.setId(Long.parseLong(updateMessageDTO.getId()));
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getName())) {
			entity.setName(updateMessageDTO.getName());
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getProposalStatus())) {
			entity.setProposalStatus(ProposalStatus.conversionProposalStatus.get(updateMessageDTO.getProposalStatus()));
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
