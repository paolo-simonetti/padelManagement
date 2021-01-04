package it.solving.padelmanagement.service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.NewClubProposalDTO;
import it.solving.padelmanagement.dto.message.insert.ClubInsertMessageDTO;
import it.solving.padelmanagement.dto.message.insert.NewClubProposalInsertMessageDTO;
import it.solving.padelmanagement.dto.message.insert.UserInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.NewClubProposalUpdateMessageDTO;
import it.solving.padelmanagement.exception.EmailException;
import it.solving.padelmanagement.mapper.NewClubProposalMapper;
import it.solving.padelmanagement.model.NewClubProposal;
import it.solving.padelmanagement.model.ProposalStatus;
import it.solving.padelmanagement.model.User;
import it.solving.padelmanagement.repository.ImageRepository;
import it.solving.padelmanagement.repository.NewClubProposalRepository;
import it.solving.padelmanagement.repository.UserRepository;
import it.solving.padelmanagement.securitymodel.UserPrincipal;
import it.solving.padelmanagement.util.MyUtil;

@Service
public class NewClubProposalService {

	@Autowired
	private MyUtil myUtil;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private NewClubProposalRepository newClubProposalRepository;
	
	@Autowired
	private NewClubProposalMapper newClubProposalMapper;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void insert (NewClubProposalInsertMessageDTO newClubProposalInsertMessageDTO) {
		NewClubProposal newClubProposal=newClubProposalMapper
				.convertInsertMessageDTOToEntity(newClubProposalInsertMessageDTO);
		User user=((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		User creator=userRepository.findByIdWithProPicFileAndNewClubProposals(user.getId()).get();
		imageRepository.save(newClubProposal.getLogo());
		newClubProposalRepository.save(newClubProposal);
		userRepository.save(creator);

		newClubProposal.setCreator(creator);
		creator.addToNewClubProposals(newClubProposal);
		newClubProposalRepository.save(newClubProposal);
		userRepository.save(creator);
	}
	
	public void update (NewClubProposalUpdateMessageDTO newClubProposalUpdateMessageDTO) {
		if (newClubProposalRepository.findById(Long.parseLong(newClubProposalUpdateMessageDTO.getId())).isPresent()) {
			NewClubProposal newClubProposal=newClubProposalMapper
					.convertUpdateMessageDTOToEntity(newClubProposalUpdateMessageDTO);
			User creator=userRepository.findById(Long.parseLong(newClubProposalUpdateMessageDTO.
					getCreatorId())).orElse(null);
			
			newClubProposal.setCreator(creator);
			creator.addToNewClubProposals(newClubProposal);
			newClubProposalRepository.save(newClubProposal);
			userRepository.save(creator);
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public void delete (Long id) {
		if (newClubProposalRepository.findByIdWithCreatorTheirProPicAndLogo(id).isPresent()) {
			NewClubProposal newClubProposal=newClubProposalRepository.findByIdWithCreatorTheirProPicAndLogo(id).get();
			User creator=newClubProposal.getCreator();
			creator.removeFromNewClubProposals(newClubProposal);
			newClubProposal.setCreator(null);
			newClubProposalRepository.save(newClubProposal);
			userRepository.save(creator);
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public NewClubProposalDTO findById(Long id) {
		if (newClubProposalRepository.findById(id).isPresent()) {
			return newClubProposalMapper.convertEntityToDTO(newClubProposalRepository.findById(id).get());
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public NewClubProposal findByIdWithCreatorTheirProPicAndLogo(Long id) {
		if (newClubProposalRepository.findByIdWithCreatorTheirProPicAndLogo(id).isPresent()) {
			return newClubProposalRepository.findByIdWithCreatorTheirProPicAndLogo(id).get();
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public Set<NewClubProposalDTO> findAll() {
		return newClubProposalMapper.convertEntityToDTO(newClubProposalRepository.findAll()
			.stream().collect(Collectors.toSet()));
	}
	
	public boolean approveNewClubProposal(Long newClubProposalId) throws EmailException {
		NewClubProposal newClubProposal=newClubProposalRepository
			.findByIdWithCreatorTheirProPicAndLogo(newClubProposalId).orElseThrow(NoSuchElementException::new);
		if (newClubProposal.getProposalStatus()==ProposalStatus.PENDING) {
			newClubProposal.setProposalStatus(ProposalStatus.APPROVED);
			UserInsertMessageDTO admin=myUtil.getAdminFromNewClubProposal(newClubProposal);
			ClubInsertMessageDTO club=myUtil.getClubFromNewClubProposal(newClubProposal);
			adminService.insert(admin,club, newClubProposal.getCreator().getId());
			emailService.sendApprovedNewClubProposalNotification(newClubProposal.getCreator().getMailAddress());
			return true;
		} else {
			return false;
		}
	}
	
}
