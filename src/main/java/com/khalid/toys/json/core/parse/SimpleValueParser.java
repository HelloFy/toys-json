package com.khalid.toys.json.core.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.khalid.toys.json.core.exception.JSONParseException;
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
	
	private JSONContext validateForLiteral(JSONContext jsonContext,char[] literal) throws ParseExpectValueException{
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
	
	
	private char parseUnicode(JSONContext jsonContext) throws ParseExpectValueException{
		int index = jsonContext.getIndex();
		char[] array = jsonContext.getJsonCharArray();
		if(checkIndexIfOut(index+4, array)){
			throw new ParseExpectValueException("UniCode长度不足");
		}
		jsonContext.setIndex(++index);
		StringBuilder sb = new StringBuilder(4);
		for(int i = 0;i < 4; i++){
			sb.append(jsonContext.getJsonCharValueAtIndex(index+i));
		}
		jsonContext.setIndex(index+4);
		int nfe = Integer.parseInt(sb.toString(), 16);
		return (char)nfe;
	}
	

	public JSONContext parseWhiteSpace(JSONContext jsonContext){
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
	
	public NullValue parseNull(NullValue value,JSONContext jsonContext) throws ParseExpectValueException{
		int index = jsonContext.getIndex();
		char[] array = jsonContext.getJsonCharArray();
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
		index = jsonContext.getIndex();
		if(!checkIndexIfOut(index+1, array)){
			jsonContext.setIndex(++index);
			return value;
		}
		else{
			throw new ParseExpectValueException("JSON格式错误");
		}
	}
	
	public BooleanValue parseTrue(BooleanValue value ,JSONContext jsonContext) throws ParseExpectValueException{
		int index = jsonContext.getIndex();
		char[] array = jsonContext.getJsonCharArray();
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
		index = jsonContext.getIndex();
		if(!checkIndexIfOut(index+1, array)){
			jsonContext.setIndex(++index);
			return value;
		}
		else{
			throw new ParseExpectValueException("JSON格式错误");
		}
	}
	
	public BooleanValue parseFalse(BooleanValue value , JSONContext jsonContext) throws ParseExpectValueException{
		int index = jsonContext.getIndex();
		char[] array = jsonContext.getJsonCharArray();
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
		index = jsonContext.getIndex();
		if(!checkIndexIfOut(index+1, array)){
			jsonContext.setIndex(++index);
			return value;
		}
		else{
			throw new ParseExpectValueException("JSON格式错误");
		}
	} 
	
	
	public NumberValue parseNumber(NumberValue value , JSONContext jsonContext) throws ParseInvalidValueException, ParseNumberTooHugeExcpetion, ParseExpectValueException{
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
	
	public StringValue parseString(StringValue value , JSONContext jsonContext) throws ParseRootNotSingularException, ParseExpectValueException, ParseStringInvalidEscapeException{
		StringBuilder stringValue = new StringBuilder();
		int index = jsonContext.getIndex();
		char[] array = jsonContext.getJsonCharArray();
		char curChar = jsonContext.getJsonCharValueAtIndex(index);
		if(curChar != '"'){
			throw new ParseExpectValueException("解析字符串失败，字符串应以\"开头");
		}
		if(checkIndexIfOut(index+1, array)){
			throw new ParseRootNotSingularException("解析字符串失败，格式错误");
		}
		jsonContext.setIndex(++index);
		while(true){
			curChar = jsonContext.getJsonCharValueAtIndex(index);
			if(curChar == '"'){
				value.setValue(stringValue.toString());
				if(!checkIndexIfOut(index+1, array)){
					jsonContext.setIndex(++index);
					return value;
				}
				else{
					throw new ParseExpectValueException("JSON格式错误");
				}
			}
			else if(curChar == '\\'){
				if(checkIndexIfOut(index+1, array)){
					throw new ParseStringInvalidEscapeException("转义无效。");
				}
				jsonContext.setIndex(++index);
				curChar = jsonContext.getJsonCharValueAtIndex(index);
				switch(curChar){
					case '\\': stringValue.append('\\');break;
					case '/':  stringValue.append('/');break;
					case '\'': stringValue.append('\'');break;
					case '\"': stringValue.append('\"');break;
					case 'b': stringValue.append('\b');break;
					case 'f': stringValue.append('\f');break;
					case 'n': stringValue.append('\n');break;
					case 'r': stringValue.append('\r');break;
					case 'u': stringValue.append(parseUnicode(jsonContext));break;
					case 't': stringValue.append('\t');break;
					default:
						throw new ParseStringInvalidEscapeException("解析String失败，转义字符非法");
				}
			}
			else if((int)curChar < 0x20){
				switch(curChar){
					case '\\': 
					case '/':  
					case '\b':
					case '\f':
					case '\n': 
					case '\r': 
					case '\t': 
					case '(':
					case ')': break;
					default:
						throw new ParseStringInvalidEscapeException("解析String失败.字符非法");
				}
			}
			else{
				stringValue.append(curChar);
			}
			if(checkIndexIfOut(index+1, array)){
				throw new ParseExpectValueException("解析String失败,必须以\"结尾");
			}
			jsonContext.setIndex(++index);		
		}
	}
	
	public ArrayValue parseArray(ArrayValue value,JSONContext jsonContext) throws JSONParseException{
		List<? super AbstractJsonValue<?>> list =new ArrayList<>();
		char[] array = jsonContext.getJsonCharArray();
		int index = jsonContext.getIndex();
		if(checkIndexIfOut(index+1, array)){
			throw new ParseExpectValueException("解析Array失败，应以]结尾。");
		}
		jsonContext.setIndex(++index);
		parseWhiteSpace(jsonContext);
		while(true){
			index = jsonContext.getIndex();
			char ch = jsonContext.getJsonCharValueAtIndex(index);
			if(ch == ','){
				if(!checkIndexIfOut(index+1, array)){
					jsonContext.setIndex(++index);
					parseWhiteSpace(jsonContext);
				}
				else{
					throw new ParseExpectValueException("，后需要一个值");
				}
			}
			else if(ch == ']'){
				value.setValue(list);
				if(!checkIndexIfOut(index+1, array))
					jsonContext.setIndex(++index);
				else
					throw new ParseExpectValueException("JSON格式错误");
				return value;
			}
			list.add(parseValue(jsonContext));
			parseWhiteSpace(jsonContext);
		}
	}
	
	public ObjectValue parseObject(ObjectValue value , JSONContext jsonContext) throws JSONParseException{
		Map<String,? super AbstractJsonValue<?>> valueMap = new HashMap<>();
		int index = jsonContext.getIndex();
		char[] array = jsonContext.getJsonCharArray();
		if(checkIndexIfOut(index+1, array)){
			throw new ParseExpectValueException("解析Object失败，应以}结尾。");
		}
		jsonContext.setIndex(++index);
		parseWhiteSpace(jsonContext);
		StringValue nameTmp = new StringValue();
		while(true){
			index = jsonContext.getIndex();
			char curChar = jsonContext.getJsonCharValueAtIndex(index);
			switch (curChar) {
				case '"':
					parseString(nameTmp, jsonContext);
					parseWhiteSpace(jsonContext);
					break;
				case ',':
					if(checkIndexIfOut(index+1, array)){
						throw new ParseExpectValueException("解析Object失败，应以}结尾。");
					}
					jsonContext.setIndex(++index);
					jsonContext = parseWhiteSpace(jsonContext);
					break;
				case ':':
					if(checkIndexIfOut(index+1, array)){
						throw new ParseExpectValueException("解析Object失败，应以}结尾。");
					}
					jsonContext.setIndex(++index);
					jsonContext = parseWhiteSpace(jsonContext);
					if(valueMap.containsKey(nameTmp.toString())){
						throw new ParseExpectValueException("JSON Key:"+nameTmp+" 重复。");
					}
					else{
						valueMap.put(nameTmp.getValue(), parseValue(jsonContext));
						parseWhiteSpace(jsonContext);
						break;
					}
				case '}':
					value.setValue(valueMap);
					if(checkIndexIfOut(index+1, array))
						return value;
					else{
						jsonContext.setIndex(++index);
						return value;
					}
			}
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public T parseValue(JSONContext jsonContext) throws JSONParseException{
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
				return (T) parseObject(new ObjectValue(),jsonContext);
			default :
				return (T) parseNumber(new NumberValue(), jsonContext);
		}
	}


	@SuppressWarnings("unchecked")
	public T parse(String jsonStr) throws JSONParseException {
		// TODO Auto-generated method stub
		if(jsonStr == null || jsonStr.isEmpty()){
			throw new ParseRootNotSingularException("Json串空");
		}
		JSONContext jsonContext = new JSONContext(jsonStr);
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
