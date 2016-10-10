package com.khalid.toys.json.core.value;

import java.util.List;

import com.khalid.toys.json.core.type.JSONType;

public class ArrayValue extends AbstractJsonValue<List<? super AbstractJsonValue<?>>>{
	
	public ArrayValue(){
		super.setJsonType(JSONType.TYPE_ARRAY);
	}

}