package com.khalid.toys.json.core.value;

import com.khalid.toys.json.core.type.JSONType;


/**
 * Json解析值基类 
 * @author 费玥
 * @since 2016.09.21
 * */
public abstract class AbstractJsonValue<T> implements Value<T> {

	protected JSONType jsonType;
	
	private T value;
	
	public void setJsonType(JSONType jsonType){
		this.jsonType = jsonType;
	} 
	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	public JSONType getJsonType(){
		return jsonType;
	}
	
	@Override 
	public String toString(){
		return jsonType+"Value:"+value;	
	}
	
}
