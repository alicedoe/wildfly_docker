package com.alicegabbana.cahierenligne.services.user;

public class UserException extends Exception {

	private static final long serialVersionUID = -2568845463818544064L;
	
	public static int CONFLICT = 409;
	public static int NOT_FOUND = 404;
	public static int BAD_REQUEST = 400;
	public static int UNAUTHORIZED = 401;
	public static int INTERNAL_SERVER_ERROR = 500;
	private int code;
	private String message;

	public UserException(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public int getCode() {
		return this.code;
	}
}
