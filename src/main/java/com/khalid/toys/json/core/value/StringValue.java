package com.khalid.toys.json.core.value;

import com.khalid.toys.json.core.type.JSONType;

public class StringValue extends AbstractJsonValue<String>{
	
	public StringValue(){
		super.setJsonType(JSONType.TYPE_STRING);
	}
}
