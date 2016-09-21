package com.khalid.toys.json.core.parse;

import com.khalid.toys.json.core.exception.JsonException;
import com.khalid.toys.json.core.value.AbstractJsonValue;

public interface Parse<T extends AbstractJsonValue> {
	
	public Object parse(String jsonStr) throws JsonException;

	public T parseValue(JsonContext jsonContext);
} 
