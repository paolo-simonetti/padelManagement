package it.solving.padelmanagement.service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.PlayerDTO;
import it.solving.padelmanagement.dto.message.insert.PlayerInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.PlayerUpdateMessageDTO;
import it.solving.padelmanagement.mapper.PlayerMapper;
import it.solving.padelmanagement.model.Club;
import it.solving.padelmanagement.model.PadelMatch;
import it.solving.padelmanagement.model.Player;
import it.solving.padelmanagement.repository.ClubRepository;
import it.solving.padelmanagement.repository.MatchRepository;
import it.solving.padelmanagement.repository.PlayerRepository;

@Service
public class PlayerService {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private PlayerMapper playerMapper;
	
	@Autowired
	private ClubRepository clubRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MatchRepository matchRepository;
	
	public PlayerDTO findById(Long id) {
		return playerMapper.convertEntityToDTO(playerRepository.findById(id).get());
	}
	
	public Set<PlayerDTO> findAll() {
		return playerMapper.convertEntityToDTO(playerRepository.findAll().stream().collect(Collectors.toSet()));
	}
	
	public void insert(PlayerInsertMessageDTO playerInsertMessageDTO) {
		Player player = playerMapper.convertInsertMessageDTOToEntity(playerInsertMessageDTO);
		Club club = clubRepository.findById(Long.parseLong(playerInsertMessageDTO.getClubId())).orElse(null);
		if (club!=null) {
			// Rimuovo il player dalla tabella User
			userService.delete(player.getId());
			// Assegno il player al club e salvo tutto nel contesto di persistenza
			player.setClub(club);
			club.addToPlayers(player);
			playerRepository.save(player);
			clubRepository.save(club);
		} else {
			throw new NoSuchElementException();
		}
	}
	
	public void update(PlayerUpdateMessageDTO playerUpdateMessageDTO) {
		if (!playerRepository.findById(Long.parseLong(playerUpdateMessageDTO.getId())).isPresent()) {
			throw new NoSuchElementException();
		}
		
		Player player = playerMapper.convertUpdateMessageDTOToEntity(playerUpdateMessageDTO);
		
		// Aggiorno i match creati
		if (playerUpdateMessageDTO.getMatchesIds()!=null && playerUpdateMessageDTO.getMatchesIds().size()>0) {
			Set<PadelMatch> matches=playerUpdateMessageDTO.getMatchesIds().stream().map(stringId->
			matchRepository.findById(Long.parseLong(stringId)).get()).collect(Collectors.toSet());
			player.setMatches(matches);
			matches.stream().forEach(match-> {
				match.setCreator(player);
				matchRepository.save(match);
			});
		}
		
		// Aggiorno i match a cui partecipa
		if(playerUpdateMessageDTO.getMatchesJoinedIds()!=null && 
				playerUpdateMessageDTO.getMatchesJoinedIds().size()>0) {
			Set<PadelMatch> matchesJoined=playerUpdateMessageDTO.getMatchesJoinedIds().stream().map(stringId->			
				matchRepository.findById(Long.parseLong(stringId)).get()).collect(Collectors.toSet());
			player.setMatchesJoined(matchesJoined);
			matchesJoined.stream().forEach(match-> {
				match.addToOtherPlayers(player);
				matchRepository.save(match);
			});
		}
		
		playerRepository.save(player);
		
	}
	
	public void delete (Long id) {
		if (playerRepository.findById(id).isPresent()) {
			Player player = playerRepository.findByIdWithAllMatches(id).get();
			Set<PadelMatch> matches=player.getMatches();
			Set<PadelMatch> matchesJoined=player.getMatchesJoined();
			if (matches!=null && matches.size()>0) {
				player.setMatches(null);
				matches.stream().forEach(match-> {
					match.setCreator(null);
					matchRepository.save(match);
					playerRepository.save(player);
				});
			}
			
			if (matchesJoined!=null && matchesJoined.size()>0) {
				player.setMatchesJoined(null);
				matches.stream().forEach(match-> {
					match.removeFromOtherPlayers(player);
					matchRepository.save(match);
					playerRepository.save(player);
				});
			}
			
			playerRepository.delete(player);
		} else {
			throw new NoSuchElementException();
		}
	} 
	
}
