package com.khalid.toys.json.core.exception;

public class JSONParseSyntaxException extends JSONParseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JSONParseSyntaxException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public JSONParseSyntaxException(String msg , Throwable cause){
		super(msg,cause);
		// TODO Auto-generated constructor stub
	}

	public JSONParseSyntaxException(String msg , int offset){
		this(msg+" at offset "+offset);
	}
	
	public JSONParseSyntaxException(String msg ,Throwable cause, int offset){
		this(msg+" at offset "+offset,cause);
	}
}
