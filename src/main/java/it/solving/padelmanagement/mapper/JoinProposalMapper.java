package it.solving.padelmanagement.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.solving.padelmanagement.dto.JoinProposalDTO;
import it.solving.padelmanagement.dto.message.insert.JoinProposalInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.JoinProposalUpdateMessageDTO;
import it.solving.padelmanagement.model.JoinProposal;
import it.solving.padelmanagement.model.ProposalStatus;

@Component
public class JoinProposalMapper extends
		AbstractMapper<JoinProposal, JoinProposalDTO, JoinProposalInsertMessageDTO, JoinProposalUpdateMessageDTO> {

	@Override
	public JoinProposalDTO convertEntityToDTO(JoinProposal entity) {
		if(entity==null) {
			return null;
		}
		
		JoinProposalDTO dto=new JoinProposalDTO();
		
		if(entity.getId()!=null) {
			dto.setId(entity.getId().toString());
		}
		
		if(entity.getUserLevel()!=null) {
			dto.setUserLevel(entity.getUserLevel().toString());
		}
		
		if(entity.getProposalStatus()!=null) {
			dto.setProposalStatus(entity.getProposalStatus().getStringProposalStatus());
		}
		
		if(entity.getApplicant()!=null) {
			dto.setApplicantId(entity.getApplicant().getId().toString());
		}
		
		if(entity.getClub()!=null) {
			dto.setClubId(entity.getClub().getId().toString());
		}
		
		return dto;
	}

	@Override
	public JoinProposal convertDTOToEntity(JoinProposalDTO dto) {
		if(dto==null) {
			return null;
		}
		
		JoinProposal entity=new JoinProposal();
		
		if(StringUtils.isNotBlank(dto.getId())) {
			entity.setId(Long.parseLong(dto.getId()));
		}
		
		if(StringUtils.isNotBlank(dto.getUserLevel())) {
			entity.setUserLevel(Integer.parseInt(dto.getUserLevel()));
		}
		
		if(StringUtils.isNotBlank(dto.getProposalStatus())) {
			entity.setProposalStatus(ProposalStatus.conversionProposalStatus.get(dto.getProposalStatus()));
		}
		
		return entity;
	}

	@Override
	public JoinProposal convertInsertMessageDTOToEntity(JoinProposalInsertMessageDTO insertMessageDTO) {
		if(insertMessageDTO==null) {
			return null;			
		}
		
		JoinProposal entity=new JoinProposal();
		
		if(StringUtils.isNotBlank(insertMessageDTO.getUserLevel())) {
			entity.setUserLevel(Integer.parseInt(insertMessageDTO.getUserLevel()));
		}
		
		if(StringUtils.isNotBlank(insertMessageDTO.getProposalStatus())) {
			entity.setProposalStatus(ProposalStatus.conversionProposalStatus.get(insertMessageDTO.getProposalStatus()));
		}
		
		return entity;
	}

	@Override
	public JoinProposal convertUpdateMessageDTOToEntity(JoinProposalUpdateMessageDTO updateMessageDTO) {
		if(updateMessageDTO==null) {
			return null;
		}
		
		JoinProposal entity=new JoinProposal();
		
		if(StringUtils.isNotBlank(updateMessageDTO.getId())) {
			entity.setId(Long.parseLong(updateMessageDTO.getId()));
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getUserLevel())) {
			entity.setUserLevel(Integer.parseInt(updateMessageDTO.getUserLevel()));
		}
		
		if(StringUtils.isNotBlank(updateMessageDTO.getProposalStatus())) {
			entity.setProposalStatus(ProposalStatus.conversionProposalStatus.get(updateMessageDTO.getProposalStatus()));
		}
		
		return entity;
	}

}
