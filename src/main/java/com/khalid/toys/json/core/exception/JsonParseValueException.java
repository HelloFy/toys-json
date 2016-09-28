package com.khalid.toys.json.core.exception;

public abstract class JsonParseValueException extends JsonParseException implements ErrorCode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JsonParseValueException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public JsonParseValueException(String msg,Throwable cause){
		super(msg,cause);
	}

}
