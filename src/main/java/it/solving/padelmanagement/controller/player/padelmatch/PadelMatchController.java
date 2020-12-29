package it.solving.padelmanagement.controller.player.padelmatch;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.solving.padelmanagement.dto.CourtDTO;
import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.dto.message.createpadelmatch.InputVerifyAvailabilityMessageDTO;
import it.solving.padelmanagement.exception.VerifyAvailabilityException;
import it.solving.padelmanagement.service.CourtService;
import it.solving.padelmanagement.validator.VerifyAvailabilityValidator;

//TODO: in tutto questo package, devo controllare che il player stia agendo sulle proprie robe
@RestController
@RequestMapping("player/padelmatch")
public class PadelMatchController {
	
	@Autowired
	private VerifyAvailabilityValidator verifyAvailabilityValidator;
	
	@Autowired
	private CourtService courtService;
	
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
		
		return ResponseEntity.status(HttpStatus.OK).body(courts) ;
	}
	
	
}
