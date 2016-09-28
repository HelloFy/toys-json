package com.khalid.toys.json.core.exception;

public class ParseStringInvalidEscapeException extends JsonParseValueException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static String errorCode = "invalid escape";
	
	public ParseStringInvalidEscapeException(String msg){
		super(msg);
	}
	
	public ParseStringInvalidEscapeException(String msg, Throwable cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getErrorCode() {
		// TODO Auto-generated method stub
		return errorCode;
	}

}
