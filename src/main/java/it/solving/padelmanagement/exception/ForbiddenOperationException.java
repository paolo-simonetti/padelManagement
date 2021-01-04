package it.solving.padelmanagement.exception;

public class ForbiddenOperationException extends Exception {
	
	private static final long serialVersionUID = -1604264474985771913L;
	
	public ForbiddenOperationException(String message) {
		super(message);
	}
	
}
