package it.solving.padelmanagement.service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.MatchDTO;
import it.solving.padelmanagement.dto.message.insert.MatchInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.MatchUpdateMessageDTO;
import it.solving.padelmanagement.mapper.MatchMapper;
import it.solving.padelmanagement.model.Court;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.model.Slot;
import it.solving.padelmanagement.repository.CourtRepository;
import it.solving.padelmanagement.repository.MatchRepository;
import it.solving.padelmanagement.repository.PlayerRepository;
import it.solving.padelmanagement.repository.SlotRepository;

@Service
public class MatchService {

	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired 
	private MatchMapper matchMapper;
	
	@Autowired
	private PlayerRepository playerRepository;
		
	@Autowired
	private CourtRepository courtRepository;
	
	@Autowired
	private SlotRepository slotRepository;
	
	public void insert(MatchInsertMessageDTO matchInsertMessageDTO) {
		PadelMatch match=matchMapper.convertInsertMessageDTOToEntity(matchInsertMessageDTO);
		Player creator=playerRepository.findById(Long.parseLong(matchInsertMessageDTO.getCreatorId())).get();
		Set<Slot> slots=matchInsertMessageDTO.getSlotsIds().stream().map(stringId->
			Slot.convertIdToSlot(Integer.parseInt(stringId))).collect(Collectors.toSet());
		Court court=courtRepository.findById(Long.parseLong(matchInsertMessageDTO.getCourtId())).get();
		if (slots!=null && slots.size()>=3 && creator!=null && court!=null) {
			match.setCourt(court);
			court.addToMatches(match);
			match.setCreator(creator);
			creator.addToMatches(match);
			match.setSlots(slots);
			slots.stream().forEach(slot-> {
				slot.addToMatches(match);
				slotRepository.save(slot);
			});
			
			courtRepository.save(court);
			matchRepository.save(match);
			playerRepository.save(creator);
		}
	}
	
	public void update(MatchUpdateMessageDTO matchUpdateMessageDTO) {
		PadelMatch match=matchMapper.convertUpdateMessageDTOToEntity(matchUpdateMessageDTO);
		if (matchUpdateMessageDTO.getOtherPlayersIds()!=null && matchUpdateMessageDTO.getOtherPlayersIds().size()>0) {
			Set<Player> otherPlayers=matchUpdateMessageDTO.getOtherPlayersIds().stream()
					.map(stringId->playerRepository.findById(Long.parseLong(stringId)).orElse(null))
					.collect(Collectors.toSet());
			// Controllo che tutti gli id immessi fossero validi
			if (otherPlayers==null || otherPlayers.size()<matchUpdateMessageDTO.getOtherPlayersIds().size()) {
				throw new NoSuchElementException();
			}
			
			match.setOtherPlayers(otherPlayers);
			otherPlayers.stream().forEach(player-> {
				player.addToMatchesJoined(match);
				playerRepository.save(player);
			});
		}
		
		// Aggiorno il creatore
		Player creator = playerRepository.findById(Long.parseLong(matchUpdateMessageDTO.getCreatorId())).orElse(null);
		if (creator!=null) {
			match.setCreator(creator);
			creator.addToMatches(match);
			playerRepository.save(creator);
		} else {
			throw new NoSuchElementException();
		}
		
		// Aggiorno gli slot
		Set<Slot> slots=matchUpdateMessageDTO.getSlotsIds().stream().map(stringId->
			Slot.convertIdToSlot(Integer.parseInt(stringId))).collect(Collectors.toSet());
		match.setSlots(slots);
		slots.stream().forEach(slot->{
			slot.addToMatches(match);
			slotRepository.save(slot);
		});
		
		// Aggiorno il campo
		Court court=courtRepository.findById(Long.parseLong(matchUpdateMessageDTO.getCourtId())).get();
		match.setCourt(court);
		court.addToMatches(match);
		courtRepository.save(court);
		
		// Aggiorno la partita nel contesto di persistenza
		matchRepository.save(match);
	}
	
	public void delete (Long id) {
		if (matchRepository.findByIdWithCompleteInfo(id).isPresent()) {
			PadelMatch match=matchRepository.findByIdWithCompleteInfo(id).get();
			
			// Elimino la partita dall'elenco di quelle a cui partecipano i giocatori
			if (match.getOtherPlayers()!=null && match.getOtherPlayers().size()>0) {
				match.getOtherPlayers().stream().forEach(player-> {
					player.removeFromMatchesJoined(match);
					match.removeFromOtherPlayers(player);
					playerRepository.save(player);
				});				
			}
			
			// Elimino la partita dall'elenco di quelle create dal creatore
			Player creator=match.getCreator();
			creator.removeFromMatches(match);
			match.setCreator(null);
			playerRepository.save(creator);
			
			// Libero gli slot
			Set<Slot> slots=match.getSlots();
			match.getSlots().clear();
			slots.stream().forEach(slot->{
				slot.removeFromMatches(match);
				slotRepository.save(slot);
			});
			
			// Libero il campo
			Court court=match.getCourt();
			court.removeFromMatches(match);
			match.setCourt(null);
			courtRepository.save(court);
			
			matchRepository.save(match);
		}
	}
	
	public MatchDTO findById(Long id) {
		if (matchRepository.findById(id).isPresent()) {
			return matchMapper.convertEntityToDTO(matchRepository.findById(id).get());			
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public Set<MatchDTO> findAll() {
		return matchMapper.convertEntityToDTO(matchRepository.findAll().stream().collect(Collectors.toSet()));
	}
	
}
