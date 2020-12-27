package it.solving.padelmanagement.service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.NewClubProposalDTO;
import it.solving.padelmanagement.dto.message.insert.NewClubProposalInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.NewClubProposalUpdateMessageDTO;
import it.solving.padelmanagement.mapper.NewClubProposalMapper;
import it.solving.padelmanagement.model.NewClubProposal;
import it.solving.padelmanagement.model.User;
import it.solving.padelmanagement.repository.NewClubProposalRepository;
import it.solving.padelmanagement.repository.UserRepository;

@Service
public class NewClubProposalService {

	@Autowired
	private NewClubProposalRepository newClubProposalRepository;
	
	@Autowired
	private NewClubProposalMapper newClubProposalMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	public void insert (NewClubProposalInsertMessageDTO newClubProposalInsertMessageDTO) {
		NewClubProposal newClubProposal=newClubProposalMapper
				.convertInsertMessageDTOToEntity(newClubProposalInsertMessageDTO);
		User creator=userRepository.findById(Long.parseLong(newClubProposalInsertMessageDTO.
				getCreatorId())).orElse(null);
		
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
		if (newClubProposalRepository.findByIdWithCreator(id).isPresent()) {
			NewClubProposal newClubProposal=newClubProposalRepository.findByIdWithCreator(id).get();
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
	
	public NewClubProposal findByIdWithCreator(Long id) {
		if (newClubProposalRepository.findByIdWithCreator(id).isPresent()) {
			return newClubProposalRepository.findByIdWithCreator(id).get();
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public Set<NewClubProposalDTO> findAll() {
		return newClubProposalMapper.convertEntityToDTO(newClubProposalRepository.findAll()
			.stream().collect(Collectors.toSet()));
	}
	
}
