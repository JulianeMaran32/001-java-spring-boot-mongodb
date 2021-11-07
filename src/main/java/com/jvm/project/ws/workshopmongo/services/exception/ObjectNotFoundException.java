package com.jvm.project.ws.workshopmongo.services.exception;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	// sobrepor 
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
}