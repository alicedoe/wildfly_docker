package com.alicegabbana.cahierenligne.services.town;

import org.apache.log4j.spi.ErrorCode;

public class TownException extends Exception implements ErrorCode {

	private static final long serialVersionUID = 4081578677497564895L;
	
	public int DUPLICATE = 409;
	public int NOT_FOUND = 404;
	private int code;
	private String message;	

	public TownException(int code) {
		this.code = code;
	}
	
	public TownException(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode () {
		return this.code;
	}
	
	public String getMessage () {
		return this.message;
	}

}
