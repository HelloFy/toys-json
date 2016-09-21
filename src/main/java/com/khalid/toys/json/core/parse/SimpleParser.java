package com.khalid.toys.json.core.parse;

import com.khalid.toys.json.core.exception.ParseExpectValueException;
import com.khalid.toys.json.core.exception.ParseInvalidValueException;
import com.khalid.toys.json.core.exception.ParseRootNotSingularException;
import com.khalid.toys.json.core.value.AbstractJsonValue;
import com.khalid.toys.json.core.value.BooleanValue;
import com.khalid.toys.json.core.value.NullValue;

public class SimpleParser<T extends AbstractJsonValue<?>> implements Parse<T> {
	
	private boolean checkIndexIfOut(int index,char[] array){
		return (index < array.length ) ? false : true;
	}

	public JsonContext parseWhiteSpace(JsonContext jsonContext){
		int index = jsonContext.getIndex();
		char curChar = jsonContext.getJsonCharValueAtIndex(index);
		while(curChar == ' ' || curChar == '\t' || curChar == '\n' || curChar == '\r'){
			if(checkIndexIfOut(++index,jsonContext.getJsonCharArray()))
				return jsonContext;
			jsonContext.setIndex(index);
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
	
	public BooleanValue parseTrue(BooleanValue value ,JsonContext jsonContext) throws ParseExpectValueException{
		int index = jsonContext.getIndex();
		if(jsonContext.getJsonCharValueAtIndex(index) != 't'){
			throw new ParseExpectValueException("解析Boolean.TRUE失败,TRUE值必须以字符t开始!");
		}
		if(checkIndexIfOut(index+3, jsonContext.getJsonCharArray())){
			throw new ParseExpectValueException("解析Boolean.TRUE失败,TRUE值长度不够");
		}
		if(jsonContext.getJsonCharValueAtIndex(index+1) != 'r' || jsonContext.getJsonCharValueAtIndex(index+2) != 'u'|| jsonContext.getJsonCharValueAtIndex(index+3) != 'e'){
			throw new ParseExpectValueException("解析Boolean.TRUE失败,TRUE值必须是true形式!");
		}
		
		value.setValue(true);
		jsonContext.setIndex(index+3);
		return value;
	}
	
	public BooleanValue parseFalse(BooleanValue value , JsonContext jsonContext) throws ParseExpectValueException{
		int index = jsonContext.getIndex();
		if(jsonContext.getJsonCharValueAtIndex(index) != 'f'){
			throw new ParseExpectValueException("解析Boolean.FALSE失败,FALSE值必须以字符t开始!");
		}
		if(checkIndexIfOut(index+4, jsonContext.getJsonCharArray())){
			throw new ParseExpectValueException("解析Boolean.FALSE失败,FALSE值长度不够");
		}
		if(jsonContext.getJsonCharValueAtIndex(index+1) != 'a' || jsonContext.getJsonCharValueAtIndex(index+2) != 'l'
				|| jsonContext.getJsonCharValueAtIndex(index+3) != 's' || jsonContext.getJsonCharValueAtIndex(index+4) != 'e'){
			throw new ParseExpectValueException("解析Boolean.FALSE失败,FALSE值必须是false形式!");
		}
		
		value.setValue(false);
		jsonContext.setIndex(index+4);
		return value;
	} 


	@SuppressWarnings("unchecked")
	public T parseValue(JsonContext jsonContext) throws ParseExpectValueException, ParseInvalidValueException {
		// TODO Auto-generated method stub
		int index = jsonContext.getIndex();
		switch(jsonContext.getJsonCharValueAtIndex(index)){
			case 'n':
				return (T) parseNull(new NullValue(),jsonContext);
			case 't':
				return (T) parseTrue(new BooleanValue(),jsonContext);
			case 'f':
				return (T) parseFalse(new BooleanValue(),jsonContext);
			default :
				throw new ParseInvalidValueException("Json格式错误");
		}
	}


	public Object parse(String jsonStr) throws ParseRootNotSingularException, ParseExpectValueException, ParseInvalidValueException {
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
