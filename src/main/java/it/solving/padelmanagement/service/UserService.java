package it.solving.padelmanagement.service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.UserDTO;
import it.solving.padelmanagement.dto.message.insert.UserInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.UserUpdateMessageDTO;
import it.solving.padelmanagement.mapper.UserMapper;
import it.solving.padelmanagement.model.JoinProposal;
import it.solving.padelmanagement.model.NewClubProposal;
import it.solving.padelmanagement.model.User;
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
	
	public void delete (Long id) {
		if (userRepository.findById(id).isPresent()) {
			userRepository.delete(userRepository.findById(id).get());
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public void insert (UserInsertMessageDTO userInsertMessageDTO) {
		userRepository.save(userMapper.convertInsertMessageDTOToEntity(userInsertMessageDTO));
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
	
}
