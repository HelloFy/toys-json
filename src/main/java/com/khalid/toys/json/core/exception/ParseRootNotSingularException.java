package com.khalid.toys.json.core.exception;

public class ParseRootNotSingularException extends JSONParseValueException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String errorCode = "parse not singular";

	public ParseRootNotSingularException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public ParseRootNotSingularException(String msg , Throwable cause){
		super(msg,cause);
	}

	public String getErrorCode() {
		// TODO Auto-generated method stub
		return errorCode;
	}

}
