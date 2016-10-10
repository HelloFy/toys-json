package com.khalid.toys.json.core.value;

import com.khalid.toys.json.core.type.JSONType;

public final class NullValue extends StringValue {

	public NullValue(){
		super.setJsonType(JSONType.TYPE_NULL);
	}
	
}
