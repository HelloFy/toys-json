package com.khalid.toys.json.core.type;

/**
 * Json数据类型枚举
 * @author 费玥
 * @since 2016 09 21
 * */
public enum JsonType{
	
	TYPE_NULL("null"),TYPE_FALSE("false"),TYPE_TRUE("true"),TYPE_NUMBER("number"),
	TYPE_STRING("string"),TYPE_ARRAY("array"),TYPE_OBJECT("object");
	
	private String typeName;
	
	JsonType(String typeName){
		this.typeName=typeName;
	}
	
	public String getTypeName(){
		return typeName;
	}
	
}
