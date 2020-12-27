package it.solving.padelmanagement.controller.admin;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.solving.padelmanagement.dto.CourtDTO;
import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.dto.message.insert.CourtInsertMessageDTO;
import it.solving.padelmanagement.service.CourtService;

@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private CourtService courtService;
	
		
	@GetMapping("findAllMatchesByDate")
	public ResponseEntity<Set<CourtDTO>> findAllMatchesByDate(@RequestBody LocalDate date) {
		return ResponseEntity.status(HttpStatus.OK).body(courtService.findAllWithMatchesAndTheirSlotsByDate(date));
	}
	
	@GetMapping("findAllCourts")
	public ResponseEntity<Set<CourtDTO>> findAllCourts() {
		return ResponseEntity.status(HttpStatus.OK).body(courtService.findAll());
	}
	
	@PostMapping("insertCourt")
	public ResponseEntity<ResultDTO> insertCourt(@Valid @RequestBody CourtInsertMessageDTO courtInsertMessageDTO) {
		courtService.insert(courtInsertMessageDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("Court successfully created"));
	}
	
}
