package it.solving.padelmanagement.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.SlotDTO;
import it.solving.padelmanagement.mapper.SlotMapper;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.model.Slot;
import it.solving.padelmanagement.repository.MatchRepository;
import it.solving.padelmanagement.repository.SlotRepository;

@Service
public class SlotService {

	@Autowired
	private SlotRepository slotRepository;
	
	@Autowired
	private SlotMapper slotMapper;
	
	@Autowired
	private MatchRepository matchRepository;
	
	public void update(SlotDTO slotDTO) {
		if (slotRepository.findById(Integer.parseInt(slotDTO.getId())).isPresent()) {
			Slot slot =slotRepository.findById(Integer.parseInt(slotDTO.getId())).get();
			if (slotDTO.getMatchesIds()!=null && slotDTO.getMatchesIds().size()>0) {
				Set<PadelMatch> matches=slotDTO.getMatchesIds().stream().map(stringId->matchRepository
						.findById(Long.parseLong(stringId)).get()).collect(Collectors.toSet());
				matches.stream().forEach(match-> {
					slot.addToMatches(match);
					match.addToSlots(slot);
					matchRepository.save(match);
				});
			}
		}
		slotRepository.save(slotMapper.convertDTOToEntity(slotDTO));
	}
		
	public Slot findByIdWithMatches(Integer id) {
		return slotRepository.findByIdWithMatches(id).orElse(null);
	}
	
	public Set<SlotDTO> findAllWithMatches() {
		return slotMapper.convertEntityToDTO(slotRepository.findAllWithMatches().stream().collect(Collectors.toSet()));
	}
	
}
