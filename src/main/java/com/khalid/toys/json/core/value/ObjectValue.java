package com.khalid.toys.json.core.value;

import java.util.Map;

import com.khalid.toys.json.core.type.JsonType;

public class ObjectValue extends AbstractJsonValue<Map<String,? super AbstractJsonValue<?>>> {

	public ObjectValue(){
		super.setJsonType(JsonType.TYPE_OBJECT);
	}
}
