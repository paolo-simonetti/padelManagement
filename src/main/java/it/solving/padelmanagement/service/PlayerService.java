package it.solving.padelmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.solving.padelmanagement.dto.PlayerDTO;
import it.solving.padelmanagement.mapper.PlayerMapper;
import it.solving.padelmanagement.repository.PlayerRepository;

@Service
public class PlayerService {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private PlayerMapper playerMapper;
	
	public PlayerDTO findById(Long id) {
		return playerMapper.convertEntityToDTO(playerRepository.findById(id).get());
	}
	
}
