package com.khalid.toys.json.core.value;

import com.khalid.toys.json.core.type.JsonType;

public final class NumberValue extends AbstractJsonValue<Double> {

	public NumberValue(){
		super.setJsonType(JsonType.TYPE_NUMBER);
	}
	
}
