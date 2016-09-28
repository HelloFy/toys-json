package com.khalid.toys.json.core.value;

import java.util.List;

import com.khalid.toys.json.core.type.JsonType;

public class ArrayValue extends AbstractJsonValue<List<? super AbstractJsonValue<?>>>{
	
	public ArrayValue(){
		super.setJsonType(JsonType.TYPE_ARRAY);
	}

}
