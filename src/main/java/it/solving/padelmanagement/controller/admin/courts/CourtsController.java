package it.solving.padelmanagement.controller.admin.courts;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.solving.padelmanagement.dto.CourtDTO;
import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.dto.message.clubmanagement.ClubManagementMessageDTO;
import it.solving.padelmanagement.dto.message.insert.CourtInsertMessageDTO;
import it.solving.padelmanagement.dto.message.update.CourtUpdateMessageDTO;
import it.solving.padelmanagement.exception.CourtBeReservedException;
import it.solving.padelmanagement.exception.MatchPaymentException;
import it.solving.padelmanagement.service.CourtService;
import it.solving.padelmanagement.service.MatchService;


//TODO: in tutte questo package, devo controllare che l'admin stia agendo su roba riguardante il proprio circolo
@RestController
@RequestMapping("admin/courts")
public class CourtsController {

	@Autowired
	private CourtService courtService;
	
	@Autowired 
	private MatchService matchService;
		
	// qui ho scelto di far visualizzare solo i campi non dismessi
	@GetMapping("findAllMatchesByDate")
	public ResponseEntity<Set<CourtDTO>> findAllMatchesByDate(@RequestBody ClubManagementMessageDTO inputMessage) {
		return ResponseEntity.status(HttpStatus.OK).body(courtService.findAllWithMatchesAndTheirSlotsByDate(inputMessage));
	}
	
	// qui faccio visualizzare tutti i campi
	@GetMapping("findAllCourts")
	public ResponseEntity<Set<CourtDTO>> findAllCourts() {
		return ResponseEntity.status(HttpStatus.OK).body(courtService.findAll());
	}
	
	@PostMapping("insertCourt")
	public ResponseEntity<ResultDTO> insertCourt(@Valid @RequestBody CourtInsertMessageDTO courtInsertMessageDTO) {
		courtService.insert(courtInsertMessageDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("Court successfully created"));
	}
	
	@PutMapping("renameCourt")
	public ResponseEntity<ResultDTO> renameCourt(@Valid @RequestBody CourtUpdateMessageDTO courtUpdateMessageDTO) {
		courtService.update(courtUpdateMessageDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("Court successfully updated"));
	}
	
	@GetMapping("cannotBeReserved")
	public ResponseEntity<ResultDTO> cannotBeReserved(@RequestParam Long courtId) throws CourtBeReservedException {
		courtService.cannotBeReserved(courtId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("Court won't be able to be reserved any longer"));
	}
	
	@GetMapping("canBeReserved")
	public ResponseEntity<ResultDTO> canBeReserved(@RequestParam Long courtId) throws CourtBeReservedException {
		courtService.canBeReserved(courtId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("Court may be reserved since now"));
	}
	
	@GetMapping("writeDownPayment")
	public ResponseEntity<ResultDTO> writeDownPayment(@RequestParam Long matchId) throws MatchPaymentException {
		matchService.writeDownPayment(matchId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("Payment was successfully written down."));
	}
	
	
	
}
