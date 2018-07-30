package com.alicegabbana.cahierenligne.services.action;

public class ActionException extends Exception {

	private static final long serialVersionUID = -8980886744007398396L;
	
	public static int NOT_FOUND = 404;
	public static int CONFLICT = 409;
	private int code;
	private String message;
	
	ActionException ( int code, String message ) {
		this.code = code;
		this.message = message;
	}

	public ActionException(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
}