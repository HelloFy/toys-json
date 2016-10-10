package com.khalid.toys.json.core.parse;

import com.khalid.toys.json.core.exception.JSONParseException;
import com.khalid.toys.json.core.exception.JSONParseValueException;
import com.khalid.toys.json.core.value.AbstractJsonValue;

public interface ValueParse<T extends AbstractJsonValue<?>> {
	
	public T parseValue(JSONContext jsonContext) throws JSONParseException;
} 
