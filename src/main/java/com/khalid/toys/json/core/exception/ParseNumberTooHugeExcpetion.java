package com.khalid.toys.json.core.exception;

public class ParseNumberTooHugeExcpetion extends JsonParseException implements ErrorCode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String errorCode = "number too huge";

	public ParseNumberTooHugeExcpetion(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public ParseNumberTooHugeExcpetion(String msg , Throwable cause) {
		// TODO Auto-generated constructor stub
		super(msg,cause);
	}

	@Override
	public String getErrorCode() {
		// TODO Auto-generated method stub
		return errorCode;
	}

}
