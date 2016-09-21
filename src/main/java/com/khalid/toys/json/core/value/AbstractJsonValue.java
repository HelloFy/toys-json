package com.khalid.toys.json.core.value;

import com.khalid.toys.json.core.type.JsonType;


/**
 * Json解析值基类 
 * @author 费玥
 * @since 2016.09.21
 * */
public abstract class AbstractJsonValue<T> implements Value<T> {

	protected JsonType jsonType;
	
	private T value;
	
	public void setJsonType(JsonType jsonType){
		this.jsonType = jsonType;
	} 
	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	public JsonType getJsonType(){
		return jsonType;
	}
	
	@Override 
	public String toString(){
		return "value:"+value;	
	}
	
}
