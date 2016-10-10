package com.khalid.toys.json.core.exception;

import com.khalid.toys.json.core.type.JSONType;

public class JSONParseValueException extends JSONParseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8809174753511239923L;

	public JSONParseValueException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public JSONParseValueException(String msg,Throwable cause){
		super(msg,cause);
	}
	
	public JSONParseValueException(String msg,int offset){
		this(msg+" at offset "+offset);
	}
	
	public JSONParseValueException(String msg,int offset,JSONType jsonType){
		this(msg+" at offset "+offset+" type with "+jsonType+" 解析失败");
	}
	
	public JSONParseValueException(String msg,Throwable cause,int offset,JSONType jsonType){
		this(msg+" at offset "+offset+" type with "+jsonType+" 解析失败",cause);
	}

}
