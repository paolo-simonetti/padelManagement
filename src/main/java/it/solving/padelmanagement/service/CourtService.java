package it.solving.padelmanagement.service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.CourtDTO;
import it.solving.padelmanagement.dto.message.insert.CourtInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.CourtUpdateMessageDTO;
import it.solving.padelmanagement.mapper.CourtMapper;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.Court;
import it.solving.padelmanagement.repository.ClubRepository;
import it.solving.padelmanagement.repository.CourtRepository;

@Service
public class CourtService {

	@Autowired
	private CourtRepository courtRepository;
	
	@Autowired
	private CourtMapper courtMapper;
	
	@Autowired
	private ClubRepository clubRepository;
	
	public Court findById(Long id) {
		if (courtRepository.findById(id).isPresent()) {
			return courtRepository.findById(id).get();			
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public void update(CourtUpdateMessageDTO courtUpdateMessageDTO) {
		if(this.findById(Long.parseLong(courtUpdateMessageDTO.getId()))!=null) {
			courtRepository.save(courtMapper.convertUpdateMessageDTOToEntity(courtUpdateMessageDTO));
		}
	}
	
	public void insert(CourtInsertMessageDTO courtInsertMessageDTO) throws NoSuchElementException {
		// Recupero il club, lanciando un'eccezione se non esiste
		Club club=clubRepository.findById(Long.parseLong(courtInsertMessageDTO.getClubId()))
			.orElseThrow(NoSuchElementException::new);
		Court court=courtMapper.convertInsertMessageDTOToEntity(courtInsertMessageDTO);
		// Inserisco club e court nel contesto di persistenza
		courtRepository.save(court);
		clubRepository.save(club);
		// li collego
		court.setClub(club);
		club.addToCourts(court);
		courtRepository.save(court);
		clubRepository.save(club);
		
	} 
	
	public void delete(Long id) {
		if(courtRepository.findById(id).isPresent()) {
			courtRepository.delete(courtRepository.findById(id).get());
		} else {
			throw new NoSuchElementException();
		}
		
	}
	
	public Set<CourtDTO> findAll() {
		return courtMapper.convertEntityToDTO(courtRepository.findAll().stream().collect(Collectors.toSet()));
	}
	
	public Set<CourtDTO> findAllWithMatchesAndTheirSlotsByDate(LocalDate date) {
		return courtMapper.convertEntityToDTO(courtRepository.findAllWithMatchesAndTheirSlotsByDate(date).get());
	}
	
}
