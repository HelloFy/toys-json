package com.khalid.toys.json.core.parse;

import com.khalid.toys.json.core.exception.ParseExpectValueException;
import com.khalid.toys.json.core.exception.ParseInvalidValueException;
import com.khalid.toys.json.core.exception.ParseRootNotSingularException;
import com.khalid.toys.json.core.value.AbstractJsonValue;
import com.khalid.toys.json.core.value.BooleanValue;
import com.khalid.toys.json.core.value.NullValue;
import com.khalid.toys.json.core.value.NumberValue;
import com.khalid.toys.json.core.value.StringValue;

public class SimpleValueParser<T extends AbstractJsonValue<?>> implements ValueParse<T> {
	
	private boolean checkIndexIfOut(int index,char[] array){
		return (index < array.length ) ? false : true;
	}
	
	private JsonContext validateForLiteral(JsonContext jsonContext,char[] literal) throws ParseExpectValueException{
		int index = jsonContext.getIndex();
		for(int i=0;i<literal.length;i++){
			if(jsonContext.getJsonCharValueAtIndex(index+i) != literal[i]){
				throw new ParseExpectValueException("解析失败，期望值与实际值不符");
			}
		}
		jsonContext.setIndex(index+literal.length-1);
		return jsonContext;
	}
	
	private boolean isNumber1To9(char ch){
		return ('1'<=ch && ch<='9')? true : false;
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
		char[] literal = new char[]{'n','u','l','l'};
		try{
			validateForLiteral(jsonContext,literal);
		}
		catch(ParseExpectValueException e){
			throw new ParseExpectValueException("解析NULL失败,NULL值应为null",e);
		}
		value.setValue("null");
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
		char[] literal = new char[]{'t','r','u','e'};
		try{
			validateForLiteral(jsonContext,literal);
		}
		catch(ParseExpectValueException e){
			throw new ParseExpectValueException("解析Boolean.TRUE失败,TRUE值应为true",e);
		}
		value.setValue(true);
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
		char[] literal = new char[]{'f','a','l','s','e'};
		try{
			validateForLiteral(jsonContext,literal);
		}
		catch(ParseExpectValueException e){
			throw new ParseExpectValueException("解析Boolean.FALSE失败,FALSE值应为false",e);
		}
		value.setValue(false);
		return value;
	} 
	
	
	public NumberValue parseNumber(NumberValue value , JsonContext jsonContext) throws ParseInvalidValueException{
		int index = jsonContext.getIndex();
		final char[] array = jsonContext.getJsonCharArray();
		StringBuilder numberStr = new StringBuilder();
		/*
		 * 是否是负数
		  */
		if(jsonContext.getJsonCharValueAtIndex(index) == '-'){
			numberStr.append(jsonContext.getJsonCharValueAtIndex(index));
			if(!checkIndexIfOut(++index, array))
				jsonContext.setIndex(index);
		}
		/*
		 * 整数以及0
		 * */
		if(jsonContext.getJsonCharValueAtIndex(index) == '0'){
			numberStr.append(jsonContext.getJsonCharValueAtIndex(index));
			if(!checkIndexIfOut(++index, array)){
				jsonContext.setIndex(index);
			}
		}
		else{
			if(!isNumber1To9(jsonContext.getJsonCharValueAtIndex(index))){
				throw new ParseInvalidValueException("数字不合法");
			}
			while(Character.isDigit(jsonContext.getJsonCharValueAtIndex(index))){
				numberStr.append(jsonContext.getJsonCharValueAtIndex(index));
				if(!checkIndexIfOut(index+1, array)){
					jsonContext.setIndex(++index);
				}
				else break;
			}
		}
		/*
		 * 小数
		 * */
		if(jsonContext.getJsonCharValueAtIndex(index) == '.'){
			if(checkIndexIfOut(index+1, array)){
				throw new ParseInvalidValueException("数字不合法");
			}
			numberStr.append(jsonContext.getJsonCharValueAtIndex(index));
			jsonContext.setIndex(++index);
			if(!Character.isDigit(jsonContext.getJsonCharValueAtIndex(index))){
				throw new ParseInvalidValueException("数字不合法");
			}
			while(Character.isDigit(jsonContext.getJsonCharValueAtIndex(index))){
				numberStr.append(jsonContext.getJsonCharValueAtIndex(index));
				if(!checkIndexIfOut(index+1, array)){
					jsonContext.setIndex(++index);
				}
				else break;
			}
		}
		
		/*
		 * 科学计数法
		 * */
		if(jsonContext.getJsonCharValueAtIndex(index) == 'e' ||jsonContext.getJsonCharValueAtIndex(index) == 'E'){
			if(checkIndexIfOut(index+1, array)){
				throw new ParseInvalidValueException("数字不合法");
			}
			numberStr.append(jsonContext.getJsonCharValueAtIndex(index));
			jsonContext.setIndex(++index);
			if(jsonContext.getJsonCharValueAtIndex(index) == '+' || jsonContext.getJsonCharValueAtIndex(index) == '-'){
				numberStr.append(jsonContext.getJsonCharValueAtIndex(index));
				if(checkIndexIfOut(index+1, array)){
					throw new ParseInvalidValueException("数字不合法");
				}
				jsonContext.setIndex(++index);
			}
			if(!Character.isDigit(jsonContext.getJsonCharValueAtIndex(index))){
				throw new ParseInvalidValueException("数字不合法");
			}
			while(Character.isDigit(jsonContext.getJsonCharValueAtIndex(index))){
				numberStr.append(jsonContext.getJsonCharValueAtIndex(index));
				if(!checkIndexIfOut(index+1, array)){
					jsonContext.setIndex(++index);
				}
				else break;
			}
			
		}
		try{
			value.setValue(Double.valueOf(numberStr.toString()));
		}
		catch(Exception e){
			throw new ParseInvalidValueException("数字越界", e);
		}
		
		return value;
	
	}
	
	public StringValue parseString(StringValue value , JsonContext jsonContext){
		StringBuilder stringValue = new StringBuilder();
		
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
			case '"':
				return (T) parseString(new StringValue(),jsonContext);
			default :
				return (T) parseNumber(new NumberValue(), jsonContext);
		}
	}


	public Object parse(String jsonStr) throws ParseRootNotSingularException, ParseExpectValueException, ParseInvalidValueException {
		// TODO Auto-generated method stub
		if(jsonStr == null || jsonStr.isEmpty()){
			throw new ParseRootNotSingularException("Json串空");
		}
		JsonContext jsonContext = new JsonContext(jsonStr);
		parseWhiteSpace(jsonContext);
		AbstractJsonValue<?> v = parseValue(jsonContext);
		if(jsonContext.getIndex() < (jsonContext.getJsonCharArray().length-1)){
			jsonContext.setIndex(jsonContext.getIndex()+1);
			parseWhiteSpace(jsonContext);
			char curChar = jsonContext.getJsonCharValueAtIndex(jsonContext.getIndex());
			if(curChar != ' ' && curChar != '\t' && curChar != '\n' && curChar != '\r')
				throw new ParseRootNotSingularException("Json格式错误！");
		}
		return v;
	}

	
}
