package com.khalid.toys.json.core.value;

import com.khalid.toys.json.core.type.JsonType;

public final class NullValue extends StringValue {

	public NullValue(){
		super.setJsonType(JsonType.TYPE_NULL);
	}
	
}
