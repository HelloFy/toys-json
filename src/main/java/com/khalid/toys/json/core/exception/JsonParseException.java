package com.khalid.toys.json.core.exception;


/**
 * Json异常基础类
 * @author 费玥
 * @since 2016.9.20
 * */
public abstract class JsonParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JsonParseException(String msg){
		super(msg);
	}
	
	public JsonParseException(String msg , Throwable cause){
		super(msg,cause);
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof JsonParseException)) {
			return false;
		}
		JsonParseException otherE = (JsonParseException) other;
		return (getMessage().equals(otherE.getMessage()) &&
				getCause() != null && getCause().equals( otherE.getCause()));
	}

	@Override
	public int hashCode() {
		return getMessage().hashCode();
	}


}
