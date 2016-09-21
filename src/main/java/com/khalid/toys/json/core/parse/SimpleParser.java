package com.khalid.toys.json.core.parse;

import com.khalid.toys.json.core.exception.ParseExpectValueException;
import com.khalid.toys.json.core.exception.ParseRootNotSingularException;
import com.khalid.toys.json.core.value.AbstractJsonValue;
import com.khalid.toys.json.core.value.NullValue;

public class SimpleParser<T extends AbstractJsonValue<?>> implements Parse<T> {
	
	private boolean checkIndexIfOut(int index,char[] array){
		return (index < (array.length - 1)) ? false : true;
	}

	public JsonContext parseWhiteSpace(JsonContext jsonContext){
		int index = jsonContext.getIndex();
		char curChar = jsonContext.getJsonCharValueAtIndex(index);
		while(curChar == ' ' || curChar == '\t' || curChar == '\n' || curChar == '\r'){
			if(checkIndexIfOut(index,jsonContext.getJsonCharArray()))
				return jsonContext;
			jsonContext.setIndex(++index);
			curChar = jsonContext.getJsonCharValueAtIndex(index);
		}	
		return jsonContext;
	}
	

	public NullValue parseNull(NullValue value,JsonContext jsonContext) throws ParseExpectValueException{
		int index = jsonContext.getIndex();
		if(jsonContext.getJsonCharValueAtIndex(index) != 'n')
			throw new ParseExpectValueException("解析NULL失败,NULL值必须以字符n开始!");
		if(checkIndexIfOut(index+3, jsonContext.getJsonCharArray())){
			throw new ParseExpectValueException("解析NULL失败,NULL值长度不够");
		}
		if(jsonContext.getJsonCharValueAtIndex(index+1) != 'u' || jsonContext.getJsonCharValueAtIndex(index+2) != 'l'|| jsonContext.getJsonCharValueAtIndex(index+3) != 'l'){
			throw new ParseExpectValueException("解析NULL失败,NULL值必须是null形式!");
		}
		
		value.setValue("null");
		jsonContext.setIndex(index+3);
		return value;
	}


	@SuppressWarnings("unchecked")
	public T parseValue(JsonContext jsonContext) {
		// TODO Auto-generated method stub
		int index = jsonContext.getIndex();
		switch(jsonContext.getJsonCharValueAtIndex(index)){
			case 'n': try {
				return parseValue((T) new NullValue(),jsonContext);
				} 
				catch (ParseExpectValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private T parseValue(T value , JsonContext jsonContext) throws ParseExpectValueException{
		switch(value.getJsonType()){
			case TYPE_NULL: return (T) parseNull((NullValue) value,jsonContext);
		default:
			break;
			
		}
		return value;
	}


	public Object parse(String jsonStr) throws ParseRootNotSingularException {
		// TODO Auto-generated method stub
		JsonContext jsonContext = new JsonContext(jsonStr);
		parseWhiteSpace(jsonContext);
		AbstractJsonValue<?> v = parseValue(jsonContext);
		if(jsonContext.getIndex() < (jsonContext.getJsonCharArray().length-1)){
			jsonContext.setIndex(jsonContext.getIndex()+1);
			parseWhiteSpace(jsonContext);
			if(jsonContext.getIndex() != (jsonContext.getJsonCharArray().length-1))
				throw new ParseRootNotSingularException("Json格式错误！");
		}
		return v;
	}

	
}
