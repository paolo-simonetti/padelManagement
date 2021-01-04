package it.solving.padelmanagement.controller.admin.courts;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import it.solving.padelmanagement.exception.ForbiddenOperationException;
import it.solving.padelmanagement.service.CourtService;
import it.solving.padelmanagement.service.MatchService;
import it.solving.padelmanagement.validator.InputRenameCourtValidator;

@RestController
@RequestMapping("admin/courts")
public class CourtsController {
	
	@Autowired
	private InputRenameCourtValidator inputRenameCourtValidator;
	
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
		return ResponseEntity.status(HttpStatus.OK).body(courtService.findAllByClub());
	}
	
	@PostMapping("insertCourt")
	public ResponseEntity<ResultDTO> insertCourt(@Valid @RequestBody CourtInsertMessageDTO courtInsertMessageDTO) {
		courtService.insert(courtInsertMessageDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("Court successfully created"));
	}
	
	@PutMapping("renameCourt")
	public ResponseEntity<ResultDTO> renameCourt(@Valid @RequestBody CourtUpdateMessageDTO courtUpdateMessageDTO,
			BindingResult bindingResult) {
		inputRenameCourtValidator.validate(courtUpdateMessageDTO,bindingResult);
		if(bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO("This court does not belong to the admin's club!"));
		}
		courtService.update(courtUpdateMessageDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("Court successfully updated"));
	}
	
	@GetMapping("cannotBeReserved")
	public ResponseEntity<ResultDTO> cannotBeReserved(@RequestParam Long courtId) throws ForbiddenOperationException {
		courtService.cannotBeReserved(courtId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("Court won't be able to be reserved any longer"));
	}
	
	@GetMapping("canBeReserved")
	public ResponseEntity<ResultDTO> canBeReserved(@RequestParam Long courtId) throws ForbiddenOperationException {
		courtService.canBeReserved(courtId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("Court may be reserved since now"));
	}
	
	@GetMapping("writeDownPayment")
	public ResponseEntity<ResultDTO> writeDownPayment(@RequestParam Long matchId) throws ForbiddenOperationException {
		matchService.writeDownPayment(matchId);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("Payment was successfully written down."));
	}
	
	
	
}
