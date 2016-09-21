package com.khalid.toys.json.core.exception;

public class ParseExpectValueException extends JsonParseException implements ErrorCode{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static String errorCode="expect some value";

	public ParseExpectValueException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public ParseExpectValueException(String msg , Throwable cause){
		super(msg,cause);
	}

	public String getErrorCode() {
		// TODO Auto-generated method stub
		return errorCode;
	}

}
