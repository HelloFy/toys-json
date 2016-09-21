package com.khalid.toys.json.core.value;

import com.khalid.toys.json.core.type.JsonType;

public class StringValue extends AbstractJsonValue<String>{
	
	public StringValue(){
		super.setJsonType(JsonType.TYPE_STRING);
	}
}
