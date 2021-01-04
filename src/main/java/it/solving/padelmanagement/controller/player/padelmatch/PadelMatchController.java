package it.solving.padelmanagement.controller.player.padelmatch;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.solving.padelmanagement.dto.CourtDTO;
import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.dto.message.createpadelmatch.InputDeleteMessageDTO;
import it.solving.padelmanagement.dto.message.createpadelmatch.InputFindAllMatchesByDateMessageDTO;
import it.solving.padelmanagement.dto.message.createpadelmatch.InputValidateAndInsertInputMessageDTO;
import it.solving.padelmanagement.dto.message.createpadelmatch.InputValidateAndUpdateInputMessageDTO;
import it.solving.padelmanagement.dto.message.createpadelmatch.InputVerifyAvailabilityMessageDTO;
import it.solving.padelmanagement.exception.EmailException;
import it.solving.padelmanagement.exception.MatchInsertException;
import it.solving.padelmanagement.exception.MatchUpdateException;
import it.solving.padelmanagement.exception.VerifyAvailabilityException;
import it.solving.padelmanagement.exception.WrongCreatorException;
import it.solving.padelmanagement.service.CourtService;
import it.solving.padelmanagement.service.MatchService;
import it.solving.padelmanagement.validator.MatchDeleteValidator;
import it.solving.padelmanagement.validator.MatchInsertValidator;
import it.solving.padelmanagement.validator.MatchUpdateValidator;
import it.solving.padelmanagement.validator.VerifyAvailabilityValidator;

@RestController
@RequestMapping("player/padelmatch")
public class PadelMatchController {
	
	@Autowired
	private VerifyAvailabilityValidator verifyAvailabilityValidator;
	
	@Autowired
	private MatchInsertValidator matchInsertValidator;
	
	@Autowired
	private MatchUpdateValidator matchUpdateValidator;
	
	@Autowired
	private MatchDeleteValidator matchDeleteValidator;
	
	@Autowired
	private CourtService courtService;
	
	@Autowired
	private MatchService matchService;
	
	@PostMapping("verifyavailability")
	public ResponseEntity<Object> verifyAvailability(@Valid @RequestBody 
		InputVerifyAvailabilityMessageDTO inputMessage, BindingResult bindingResult) throws VerifyAvailabilityException {
		verifyAvailabilityValidator.validate(inputMessage,bindingResult);
		if (bindingResult.hasErrors()) {
			throw new VerifyAvailabilityException(bindingResult.getAllErrors().stream()
					.map(obj->(FieldError) obj).map(fieldError->fieldError.getDefaultMessage()).reduce(
							(message1,message2)->message1+"; \n\r "+message2).get());
		}
		Set<CourtDTO> courts=courtService.verifyAvailability(inputMessage);
		if (courts==null) {
			return ResponseEntity.status(HttpStatus.OK).body(
				new ResultDTO("There are no available courts for the required time and date :("));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(courts);
	}
	
	@GetMapping("findallbydate") 
	public ResponseEntity<Object> findAllMatchesByDate(@RequestBody InputFindAllMatchesByDateMessageDTO inputMessage) {
		if (matchService.findAllByDate(LocalDate.parse(inputMessage.getDate()))==null) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("No matches were found."));
		}
		return ResponseEntity.status(HttpStatus.OK).body(matchService.findAllByDate(LocalDate.parse(inputMessage.getDate())));
	}
	
	@PostMapping("insert")
	public ResponseEntity<ResultDTO> insertPadelMatch(@Valid @RequestBody InputValidateAndInsertInputMessageDTO
			inputMessage, BindingResult bindingResult) throws MatchInsertException, VerifyAvailabilityException, EmailException {
		matchInsertValidator.validate(inputMessage,bindingResult);
		if (bindingResult.hasErrors()) {
			throw new MatchInsertException(bindingResult.getAllErrors().stream()
					.map(obj->(FieldError) obj).map(fieldError->fieldError.getDefaultMessage()).reduce(
							(message1,message2)->message1+"; \n\r "+message2).get());
		}
		matchService.insert(inputMessage);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("Match successfully inserted"));
	}
	
	@PutMapping("update")
	public ResponseEntity<ResultDTO> updatePadelMatch(@Valid @RequestBody InputValidateAndUpdateInputMessageDTO
			inputMessage, BindingResult bindingResult) 
				throws MatchInsertException, VerifyAvailabilityException, WrongCreatorException, MatchUpdateException {
		matchUpdateValidator.validate(inputMessage,bindingResult);
		if (bindingResult.hasErrors()) {
			
			/* determino se tra gli errori c'è anche l'aver sbagliato creatore della partita o aver agito 
			 su una partita troppo tardi */ 
			boolean creatorIsRight=bindingResult.getAllErrors().stream()
				.map(obj->(FieldError) obj).map(fieldError->fieldError.getCode())
				.noneMatch(code->code.equals("invalidMatchChoice")||code.equals("matchCannotBeModifiedNow"));
			if(!creatorIsRight) {
				throw new WrongCreatorException("The player cannot act on this match!");
			}
			
			// negli altri casi, lancio un'eccezione diversa
			throw new MatchUpdateException(bindingResult.getAllErrors().stream()
					.map(obj->(FieldError) obj).map(fieldError->fieldError.getDefaultMessage()).reduce(
							(message1,message2)->message1+"; \n\r "+message2).get());
		}
		
		matchService.update(inputMessage);
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("Match successfully updated"));
	}
	
	@DeleteMapping("delete")
	public ResponseEntity<ResultDTO> deletePadelmatch(@Valid @RequestBody InputDeleteMessageDTO inputMessage,
			BindingResult bindingResult) {
		matchDeleteValidator.validate(inputMessage,bindingResult);
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO(
					bindingResult.getAllErrors().stream()
					.map(obj->(FieldError) obj).map(fieldError->fieldError.getDefaultMessage()).reduce(
							(message1,message2)->message1+"; \n\r "+message2).get()));
		}
		matchService.delete(Long.parseLong(inputMessage.getMatchId()));
		return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO("The match was successuflly deleted"));
		
	}
}
