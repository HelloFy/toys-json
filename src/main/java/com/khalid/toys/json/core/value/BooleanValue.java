package com.khalid.toys.json.core.value;

import com.khalid.toys.json.core.type.JSONType;

public final class BooleanValue extends AbstractJsonValue<Boolean>{
	
	public BooleanValue(){
		super.setJsonType(JSONType.TYPE_BOOLEAN);
	}

}
