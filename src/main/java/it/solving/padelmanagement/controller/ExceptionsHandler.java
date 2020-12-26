package it.solving.padelmanagement.controller;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import it.solving.padelmanagement.dto.ResultDTO;
import it.solving.padelmanagement.exception.NonAdmissibleProposalException;


@ControllerAdvice(basePackages = "it.solvingteam.padelmanagement.controller")
public class ExceptionsHandler {

	@ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResultDTO> handleNoSuchElementException(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO("One of the requested elements is not present in the persistence context."));
    }

	@ExceptionHandler(NonAdmissibleProposalException.class)
	public ResponseEntity<ResultDTO> handleNonAdmissibleProposalException(NonAdmissibleProposalException e){
	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultDTO(e.getMessage()));
	}
	   
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResultDTO> handleException(Exception e){
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDTO(e.getMessage()));
	}
	   
	
}
