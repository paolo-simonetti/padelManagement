package it.solving.padelmanagement.controller.guest;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.solving.padelmanagement.dto.ClubDTO;
import it.solving.padelmanagement.service.ClubService;

@RestController
@RequestMapping("guest")
public class GuestController {
	
	@Autowired
	private ClubService clubService;
	
	@GetMapping("findAll")
	public ResponseEntity<Set<ClubDTO>> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(clubService.findAll());
	}
}
