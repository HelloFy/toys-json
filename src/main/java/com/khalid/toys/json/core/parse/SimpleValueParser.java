package com.khalid.toys.json.core.parse;

import java.util.ArrayList;
import java.util.List;

import com.khalid.toys.json.core.exception.JsonParseValueException;
import com.khalid.toys.json.core.exception.ParseExpectValueException;
import com.khalid.toys.json.core.exception.ParseInvalidValueException;
import com.khalid.toys.json.core.exception.ParseNumberTooHugeExcpetion;
import com.khalid.toys.json.core.exception.ParseRootNotSingularException;
import com.khalid.toys.json.core.exception.ParseStringInvalidEscapeException;
import com.khalid.toys.json.core.value.AbstractJsonValue;
import com.khalid.toys.json.core.value.ArrayValue;
import com.khalid.toys.json.core.value.BooleanValue;
import com.khalid.toys.json.core.value.NullValue;
import com.khalid.toys.json.core.value.NumberValue;
import com.khalid.toys.json.core.value.ObjectValue;
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
	
	
	public NumberValue parseNumber(NumberValue value , JsonContext jsonContext) throws ParseInvalidValueException, ParseNumberTooHugeExcpetion{
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
		value.setValue(Double.valueOf(numberStr.toString()));
		if(Double.isInfinite(value.getValue())){
			throw new ParseNumberTooHugeExcpetion("数字越界");
		}
		
		return value;
	
	}
	
	public StringValue parseString(StringValue value , JsonContext jsonContext) throws ParseRootNotSingularException, ParseExpectValueException, ParseStringInvalidEscapeException{
		StringBuilder stringValue = new StringBuilder();
		int index = jsonContext.getIndex();
		char[] array = jsonContext.getJsonCharArray();
		char curChar = jsonContext.getJsonCharValueAtIndex(index);
		if(curChar != '\"'){
			throw new ParseExpectValueException("解析字符串失败，字符串应以\"开头");
		}
		if(checkIndexIfOut(index+1, array)){
			throw new ParseRootNotSingularException("解析字符串失败，格式错误");
		}
		jsonContext.setIndex(++index);
		while(true){
			curChar = jsonContext.getJsonCharValueAtIndex(index);
			if(curChar == '\"'){
				if(checkIndexIfOut(index+1, array)){
					value.setValue(stringValue.toString());
					return value;
				}
				stringValue.append(curChar);
			}
			if((int)curChar < 0x20){
				switch(curChar){
					case '\\': 
					case '/':  
					case '\b':
					case '\f':
					case '\n': 
					case '\r': 
					case '\t': break;
					default:
						throw new ParseStringInvalidEscapeException("解析String失败，转义字符非法");
				}
			}
			stringValue.append(curChar);
			if(checkIndexIfOut(index+1, array)){
				break;
			}
			jsonContext.setIndex(++index);		
		}
		if(curChar!='\n'){
			throw new ParseExpectValueException("解析String失败,必须以\"结尾");
		}
		value.setValue(stringValue.toString());
		return value;
	}
	
	public ArrayValue parseArray(ArrayValue value,JsonContext jsonContext) throws JsonParseValueException{
		List<? super AbstractJsonValue<?>> list =new ArrayList<>();
		int index = jsonContext.getIndex();
		char[] array = jsonContext.getJsonCharArray();
		if(checkIndexIfOut(index+1, array)){
			throw new ParseExpectValueException("解析Array失败，应以]结尾。");
		}
		jsonContext.setIndex(++index);
		StringBuilder valueTmp = new StringBuilder(); 
		while(true){
			char curChar = jsonContext.getJsonCharValueAtIndex(index);
			switch (curChar) {
				case ',':
					if(checkIndexIfOut(index+1, array)){
						throw new ParseExpectValueException("解析Array出错,应以]结尾");
					}
					else if(jsonContext.getJsonCharValueAtIndex(index+1) == ']'){
						throw new ParseExpectValueException("解析Array出错, ,后必须存在值.");
					}
					else{
						list.add(parse(valueTmp.toString()));//解析之前缓存的值
						valueTmp.delete(0,valueTmp.length());
						}
					break;
				case ']':
					list.add(parse(valueTmp.toString()));//解析之前缓存的值
					value.setValue(list);
					return value;
				default:
					valueTmp.append(curChar);
					break;
					
			}
			if(checkIndexIfOut(index+1, array))
				throw new ParseExpectValueException("解析数组失败.");
			jsonContext.setIndex(++index);
		}
	}
	
	public ObjectValue parseObject(ObjectValue value , JsonContext jsonContext){
		return value;		
	}


	@SuppressWarnings("unchecked")
	@Override
	public T parseValue(JsonContext jsonContext) throws JsonParseValueException{
		// TODO Auto-generated method stub
		int index = jsonContext.getIndex();
		switch(jsonContext.getJsonCharValueAtIndex(index)){
			case 'n':
				return (T) parseNull(new NullValue(),jsonContext);
			case 't':
				return (T) parseTrue(new BooleanValue(),jsonContext);
			case 'f':
				return (T) parseFalse(new BooleanValue(),jsonContext);
			case '\"':
				return (T) parseString(new StringValue(),jsonContext);
			case '[':
				return (T) parseArray(new ArrayValue(),jsonContext);
			case '{':
				return (T) parseArray(new ArrayValue(),jsonContext);
			default :
				return (T) parseNumber(new NumberValue(), jsonContext);
		}
	}


	@SuppressWarnings("unchecked")
	public T parse(String jsonStr) throws JsonParseValueException {
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
		return (T) v;
	}

	
}
