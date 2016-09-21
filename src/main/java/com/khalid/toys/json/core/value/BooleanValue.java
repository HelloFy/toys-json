package com.khalid.toys.json.core.value;

import com.khalid.toys.json.core.type.JsonType;

public final class BooleanValue extends AbstractJsonValue<Boolean>{
	
	public BooleanValue(){
		super.setJsonType(JsonType.TYPE_BOOLEAN);
	}

}
