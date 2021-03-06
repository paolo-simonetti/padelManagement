package it.solving.padelmanagement.controller;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.exception.AbandonClubException;
import it.solving.padelmanagement.exception.EmailException;
import it.solving.padelmanagement.exception.ForbiddenOperationException;
import it.solving.padelmanagement.exception.MatchInsertException;
import it.solving.padelmanagement.exception.MatchUpdateException;
import it.solving.padelmanagement.exception.NonAdmissibleActionOnMatchNow;
import it.solving.padelmanagement.exception.NonAdmissibleProposalException;
import it.solving.padelmanagement.exception.ProposalStatusException;
import it.solving.padelmanagement.exception.VerifyAvailabilityException;
import it.solving.padelmanagement.exception.WrongCreatorException;


@ControllerAdvice(basePackages = "it.solving.padelmanagement.controller")
public class ExceptionsHandler {

	@ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResultDTO> handleNoSuchElementException(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO("One of the requested elements is not present in the persistence context."));
    }
	
	@ExceptionHandler(ForbiddenOperationException.class)
	public ResponseEntity<ResultDTO> handleMatchPaymentException(ForbiddenOperationException e){
	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO(e.getMessage()));
	}
	
	@ExceptionHandler(NonAdmissibleProposalException.class)
	public ResponseEntity<ResultDTO> handleNonAdmissibleProposalException(NonAdmissibleProposalException e){
	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO(e.getMessage()));
	}
	
	@ExceptionHandler(ProposalStatusException.class) 
	public ResponseEntity<ResultDTO> handleProposalStatusException (ProposalStatusException e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO(e.getMessage()));
	}
	
	@ExceptionHandler(VerifyAvailabilityException.class) 
	public ResponseEntity<ResultDTO> handleVerifyAvailabilityException (VerifyAvailabilityException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO(e.getMessage()));
	}
	
	@ExceptionHandler(MatchInsertException.class) 
	public ResponseEntity<ResultDTO> handleMatchInsertException (MatchInsertException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO(e.getMessage()));
	}
	
	@ExceptionHandler(MatchUpdateException.class) 
	public ResponseEntity<ResultDTO> handleMatchUpdateException (MatchUpdateException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO(e.getMessage()));
	}
	
	@ExceptionHandler(WrongCreatorException.class) 
	public ResponseEntity<ResultDTO> handleWrongCreatorException (WrongCreatorException e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO(e.getMessage()));
	}

	@ExceptionHandler(NonAdmissibleActionOnMatchNow.class) 
	public ResponseEntity<ResultDTO> handleNonAdmissibleActionOnMatchNow (NonAdmissibleActionOnMatchNow e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO(e.getMessage()));
	}
	
	@ExceptionHandler(AbandonClubException.class) 
	public ResponseEntity<ResultDTO> handleAbandonClubException (AbandonClubException e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO(e.getMessage()));
	}
	
	@ExceptionHandler(EmailException.class) 
	public ResponseEntity<ResultDTO> handleEmailException (EmailException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(e.getMessage()));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResultDTO> handleException(Exception e){
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDTO(e.getMessage()));
	}
	   
	
}
