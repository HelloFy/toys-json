package com.khalid.toys.json.core.value;

import com.khalid.toys.json.core.type.JsonType;

public final class NumberValue extends AbstractJsonValue<Number> {

	public NumberValue(){
		super.setJsonType(JsonType.TYPE_NUMBER);
	}
	
}
