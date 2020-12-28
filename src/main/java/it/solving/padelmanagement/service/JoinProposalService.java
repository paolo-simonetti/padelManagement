package it.solving.padelmanagement.service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.JoinProposalDTO;
import it.solving.padelmanagement.dto.message.insert.JoinProposalInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.JoinProposalUpdateMessageDTO;
import it.solving.padelmanagement.exception.NonAdmissibleProposalException;
import it.solving.padelmanagement.exception.ProposalStatusException;
import it.solving.padelmanagement.mapper.JoinProposalMapper;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.JoinProposal;
import it.solving.padelmanagement.model.ProposalStatus;
import it.solving.padelmanagement.model.User;
import it.solving.padelmanagement.repository.ClubRepository;
import it.solving.padelmanagement.repository.JoinProposalRepository;
import it.solving.padelmanagement.repository.UserRepository;
import it.solving.padelmanagement.util.MyUtil;

@Service
public class JoinProposalService {

	@Autowired
	private JoinProposalRepository joinProposalRepository;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private JoinProposalMapper joinProposalMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ClubRepository clubRepository;
	
	@Autowired
	private MyUtil myUtil;
	
	
	public void insert(JoinProposalInsertMessageDTO joinProposalInsertMessageDTO) throws NonAdmissibleProposalException {
		User user = userRepository.findById(Long.parseLong(joinProposalInsertMessageDTO.getApplicantId()))
			.orElseThrow(NoSuchElementException::new);
		Club club = clubRepository.findById(Long.parseLong(joinProposalInsertMessageDTO.getClubId()))
			.orElseThrow(NoSuchElementException::new);
		JoinProposal joinProposal=joinProposalMapper.convertInsertMessageDTOToEntity(joinProposalInsertMessageDTO);
		joinProposal.setApplicant(user);
		joinProposal.setProposalStatus(ProposalStatus.PENDING);
		user.addToJoinProposals(joinProposal);
		joinProposal.setClub(club);
		club.addToJoinProposals(joinProposal);
		joinProposalRepository.save(joinProposal);
		userRepository.save(user);
		clubRepository.save(club);	
	}
	
	public void update(JoinProposalUpdateMessageDTO joinProposalUpdateMessageDTO) throws NoSuchElementException {
		if(joinProposalRepository.findById(Long.parseLong(joinProposalUpdateMessageDTO.getId())).orElse(null)!=null) {
			JoinProposal joinProposal=joinProposalMapper.convertUpdateMessageDTOToEntity(joinProposalUpdateMessageDTO);
			User applicant = userRepository.findById(Long.parseLong(joinProposalUpdateMessageDTO.getApplicantId())).get();
			Club club = clubRepository.findById(Long.parseLong(joinProposalUpdateMessageDTO.getClubId())).get();
			joinProposal.setApplicant(applicant);
			applicant.addToJoinProposals(joinProposal);
			joinProposal.setClub(club);
			club.addToJoinProposals(joinProposal);
			joinProposalRepository.save(joinProposal);
			userRepository.save(applicant);
			clubRepository.save(club);
		} else {
			throw new NoSuchElementException();
		}		
	}
	
	public void delete(Long id) {
		if (joinProposalRepository.findById(id).orElse(null)!=null) {
			joinProposalRepository.delete(joinProposalRepository.findById(id).get());
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public JoinProposalDTO findById(Long id) {
		if (joinProposalRepository.findById(id).isPresent()) {
			return joinProposalMapper.convertEntityToDTO(joinProposalRepository.findById(id).get());			
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public Set<JoinProposalDTO> findAll() {
		return joinProposalMapper.convertEntityToDTO(joinProposalRepository.findAll().stream().collect(Collectors.toSet()));
	}
	
	public Set<JoinProposalDTO> findAllByApplicant(Long applicantId) {
		User applicant = userRepository.findById(applicantId).orElse(null);
		
		if (applicant!=null ) {
			return joinProposalMapper.convertEntityToDTO(joinProposalRepository.findAllByApplicant(applicant));
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public Set<JoinProposalDTO> findAllPendingWithApplicantByClub(Long clubId) {
		return joinProposalMapper.convertEntityToDTO(joinProposalRepository
			.findAllPendingWithApplicantByClub(clubId)
			.stream().filter(joinProposal->joinProposal.getProposalStatus()==ProposalStatus.PENDING)
			.collect(Collectors.toSet()));
	}
	
	public void approve(Long id) throws ProposalStatusException {
		JoinProposal joinProposal=joinProposalRepository.findByIdWithCompleteInfos(id)
			.orElseThrow(NoSuchElementException::new);
		if(joinProposal.getProposalStatus()==ProposalStatus.PENDING) {
			joinProposal.setProposalStatus(ProposalStatus.APPROVED);
			joinProposalRepository.save(joinProposal);
			playerService.insert(myUtil.buildPlayerInsertMessageDTOFromJoinProposal(joinProposal), 
				joinProposal.getApplicant().getId());	
		} else {
			throw new ProposalStatusException("The proposal had already been evaluated");
		}
	}
	
	public void reject(Long id) throws ProposalStatusException {
		JoinProposal joinProposal=joinProposalRepository.findByIdWithCompleteInfos(id)
				.orElseThrow(NoSuchElementException::new);
		if(joinProposal.getProposalStatus()==ProposalStatus.PENDING) {
			joinProposal.setProposalStatus(ProposalStatus.REJECTED);
			joinProposalRepository.save(joinProposal);
		} else {
			throw new ProposalStatusException("The proposal had already been evaluated");
		}
	}

}
