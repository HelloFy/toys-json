package com.khalid.toys.json.core.value;

import com.khalid.toys.json.core.type.JSONType;

public final class NumberValue extends AbstractJsonValue<Double> {

	public NumberValue(){
		super.setJsonType(JSONType.TYPE_NUMBER);
	}
	
}
