package it.solving.padelmanagement.service;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.UserDTO;
import it.solving.padelmanagement.dto.message.insert.UserInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.UserUpdateMessageDTO;
import it.solving.padelmanagement.mapper.UserMapper;
import it.solving.padelmanagement.model.Image;
import it.solving.padelmanagement.model.JoinProposal;
import it.solving.padelmanagement.model.NewClubProposal;
import it.solving.padelmanagement.model.ProposalStatus;
import it.solving.padelmanagement.model.Role;
import it.solving.padelmanagement.model.User;
import it.solving.padelmanagement.repository.ImageRepository;
import it.solving.padelmanagement.repository.JoinProposalRepository;
import it.solving.padelmanagement.repository.NewClubProposalRepository;
import it.solving.padelmanagement.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private NewClubProposalRepository newClubProposalRepository;
	
	@Autowired
	private JoinProposalRepository joinProposalRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	public void delete (Long id) {
		if (userRepository.findById(id).isPresent()) {
			userRepository.delete(userRepository.findById(id).get());
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public void insert (UserInsertMessageDTO userInsertMessageDTO) throws IOException {
		Image image=new Image();
		image.setName(userInsertMessageDTO.getProPicName());
		image.setImage(userInsertMessageDTO.getProPic());
		
		imageRepository.save(image);
		User user=userMapper.convertInsertMessageDTOToEntity(userInsertMessageDTO);
		user.setProPicFile(image);
		userRepository.save(user);
	}
	
	public void update (UserUpdateMessageDTO userUpdateMessageDTO) {
		if (!userRepository.findById(Long.parseLong(userUpdateMessageDTO.getId())).isPresent()) {
			throw new NoSuchElementException();
		}
		
		// Se sono qui, lo user esiste
		User user=userMapper.convertUpdateMessageDTOToEntity(userUpdateMessageDTO);
		// TODO: scrivere un validator per proposte multiple
		if (userUpdateMessageDTO.getNewClubProposalsIds()!=null && 
				userUpdateMessageDTO.getNewClubProposalsIds().size()>0) {
			Set<NewClubProposal> newClubProposals=userUpdateMessageDTO.getNewClubProposalsIds().stream().map(stringId->
				newClubProposalRepository.findById(Long.parseLong(stringId)).get()).collect(Collectors.toSet());
			// Se qualche id non era valido, lancio un'eccezione
			if(newClubProposals.size()<userUpdateMessageDTO.getNewClubProposalsIds().size()) {
				throw new NoSuchElementException();
			}
			
			newClubProposals.stream().forEach(newClubProposal-> {
				newClubProposal.setCreator(user);
				user.addToNewClubProposals(newClubProposal);
				newClubProposalRepository.save(newClubProposal);
			});
		}
		
		if (userUpdateMessageDTO.getJoinProposalsIds()!=null && userUpdateMessageDTO.getJoinProposalsIds().size()>0) {
			Set<JoinProposal> joinProposals=userUpdateMessageDTO.getJoinProposalsIds().stream().map(stringId->
				joinProposalRepository.findById(Long.parseLong(stringId)).get()).collect(Collectors.toSet());
			// Se qualche id non era valido, lancio un'eccezione
			if(joinProposals.size()<userUpdateMessageDTO.getJoinProposalsIds().size()) {
				throw new NoSuchElementException();
			}

			joinProposals.stream().forEach(joinProposal->{
				joinProposal.setApplicant(user);
				user.addToJoinProposals(joinProposal);
				joinProposalRepository.save(joinProposal);
			});
		}
		
		userRepository.save(user);
		
	}
	
	public UserDTO findById(Long id) {
		if (userRepository.findById(id).isPresent()) {
			return userMapper.convertEntityToDTO(userRepository.findById(id).get());			
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public Set<UserDTO> findAll() {
		return userMapper.convertEntityToDTO(userRepository.findAll().stream().collect(Collectors.toSet()));
	}
	
	public boolean isSuperAdminPresent() {
		return userRepository.findAllByRole(Role.ROLE_SUPER_ADMIN).stream().findFirst().orElse(null)!=null;
	}
	
	public boolean hasAnotherPendingProposal(Long id) {
		User user = userRepository.findByIdWithProposals(id).orElseThrow(NoSuchElementException::new);
		AtomicBoolean newClubResult=new AtomicBoolean();
		AtomicBoolean joinResult=new AtomicBoolean();
		// Determino se lo user abbia un'altra proposta di istituzione di Circolo da valutare
		Set<NewClubProposal> newClubProposals=user.getNewClubProposals();
		Set<JoinProposal> joinProposals=user.getJoinProposals();
		if (newClubProposals!=null && newClubProposals.size()>0) {
			newClubProposals.stream().forEach(proposal-> {
				if(proposal.getProposalStatus()==ProposalStatus.PENDING) {
					newClubResult.getAndSet(true);
				}
			});
		}
			
		// Determino se lo user abbia un'altra Proposta di Adesione a un Circolo da valutare
		if (joinProposals!=null && joinProposals.size()>0) {
			joinProposals.stream().forEach(proposal-> {
				if(proposal.getProposalStatus()==ProposalStatus.PENDING) {
					joinResult.getAndSet(true);
				}
			});
		}

		return (newClubResult.get()||joinResult.get());			
	}
	
	public boolean userAlreadyExists(String username) {
		return (userRepository.findByUsername(username).isPresent());
	}
}
