package com.khalid.toys.json.core.parse;

import com.khalid.toys.json.core.exception.JsonParseValueException;
import com.khalid.toys.json.core.value.AbstractJsonValue;

public interface ValueParse<T extends AbstractJsonValue<?>> {
	
	public T parseValue(JsonContext jsonContext) throws JsonParseValueException;
} 
