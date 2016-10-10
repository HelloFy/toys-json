package com.khalid.toys.json.core.value;

import java.util.Map;

import com.khalid.toys.json.core.type.JSONType;

public class ObjectValue extends AbstractJsonValue<Map<String,? super AbstractJsonValue<?>>> {

	public ObjectValue(){
		super.setJsonType(JSONType.TYPE_OBJECT);
	}
}
