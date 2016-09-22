package com.khalid.toys.json.core.parse;

import com.khalid.toys.json.core.exception.JsonParseException;
import com.khalid.toys.json.core.value.AbstractJsonValue;

public interface ValueParse<T extends AbstractJsonValue<?>> {
	
	public Object parse(String jsonStr) throws JsonParseException;

	public T parseValue(JsonContext jsonContext) throws JsonParseException;
} 
