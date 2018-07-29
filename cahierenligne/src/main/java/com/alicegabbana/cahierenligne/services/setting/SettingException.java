package com.alicegabbana.cahierenligne.services.setting;

public class SettingException extends Exception {

	private static final long serialVersionUID = -1857846710950809686L;

	public static int INTERNAL_SERVER_ERROR = 500;
	public static int NOT_FOUND = 404;
	public static int BAD_REQUEST = 400;
	
	private String message;
	private int code;

	public SettingException(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public SettingException(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}
}
