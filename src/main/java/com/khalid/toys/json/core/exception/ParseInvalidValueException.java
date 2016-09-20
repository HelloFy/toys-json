package com.khalid.toys.json.core.exception;

public class ParseInvalidValueException extends JsonException implements ErrorCode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String errorCode="value is invalid";
	
	public ParseInvalidValueException(String msg){
		super(msg);
	}

	public ParseInvalidValueException(String msg, Throwable cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}

	public String getErrorCode() {
		// TODO Auto-generated method stub
		return errorCode;
	}

}
