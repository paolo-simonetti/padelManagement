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
import it.solving.padelmanagement.mapper.JoinProposalMapper;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.JoinProposal;
import it.solving.padelmanagement.model.NewClubProposal;
import it.solving.padelmanagement.model.ProposalStatus;
import it.solving.padelmanagement.model.User;
import it.solving.padelmanagement.repository.ClubRepository;
import it.solving.padelmanagement.repository.JoinProposalRepository;
import it.solving.padelmanagement.repository.NewClubProposalRepository;
import it.solving.padelmanagement.repository.UserRepository;

@Service
public class JoinProposalService {

	@Autowired
	private JoinProposalRepository joinProposalRepository;
	
	@Autowired
	private JoinProposalMapper joinProposalMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ClubRepository clubRepository;
	
	@Autowired
	private NewClubProposalRepository newClubProposalRepository;	
	
	public void insert(JoinProposalInsertMessageDTO joinProposalInsertMessageDTO) throws NonAdmissibleProposalException {
		User user = userRepository.findById(Long.parseLong(joinProposalInsertMessageDTO.getApplicantId())).orElse(null);
		Club club = clubRepository.findById(Long.parseLong(joinProposalInsertMessageDTO.getClubId())).orElse(null);
		
		if (user!=null && club!=null) {
			// TODO: sposta questi controlli in un validator
			// controllo che lo user non abbia già altre proposte pending in qualche circolo
			Set<JoinProposal> previousJoinProposals=joinProposalRepository.findAllByApplicant(user);
			Set<NewClubProposal> previousNewClubProposals=newClubProposalRepository.findAllByCreator(user);
			if (previousJoinProposals.stream().filter(joinProposal->joinProposal.getProposalStatus()!=
					ProposalStatus.PENDING).findFirst().orElse(null)!=null) {
				throw new NonAdmissibleProposalException("The user already has a pending join proposal to some club!");
			}
			if (previousNewClubProposals.stream().filter(newClubProposal->newClubProposal.getProposalStatus()!=
					ProposalStatus.PENDING).findFirst().orElse(null)!=null) {
				throw new NonAdmissibleProposalException("The user already has a pending proposal for a new club!");
			}
			
			JoinProposal joinProposal=joinProposalMapper.convertInsertMessageDTOToEntity(joinProposalInsertMessageDTO);
			joinProposal.setApplicant(user);
			joinProposal.setProposalStatus(ProposalStatus.PENDING);
			user.addToJoinProposals(joinProposal);
			joinProposal.setClub(club);
			club.addToJoinProposals(joinProposal);
			joinProposalRepository.save(joinProposal);
			userRepository.save(user);
			clubRepository.save(club);
		} else {
			throw new NoSuchElementException();
		}
		
	}
	
	public Set<JoinProposalDTO> findAllByApplicant(Long applicantId) {
		User applicant = userRepository.findById(applicantId).orElse(null);
		
		if (applicant!=null ) {
			return joinProposalMapper.convertEntityToDTO(joinProposalRepository.findAllByApplicant(applicant));
		} else {
			throw new NoSuchElementException();
		}
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
	
}
