package com.khalid.toys.json.core.exception;

public abstract class JSONParseValueException extends JSONParseException implements ErrorCode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JSONParseValueException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public JSONParseValueException(String msg,Throwable cause){
		super(msg,cause);
	}

}
