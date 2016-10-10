package com.khalid.toys.json.core.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.khalid.toys.json.core.exception.JSONParseException;
import com.khalid.toys.json.core.exception.JSONParseSyntaxException;
import com.khalid.toys.json.core.exception.JSONParseValueException;
import com.khalid.toys.json.core.type.JSONType;
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
	
	private JSONContext validateForLiteral(JSONContext jsonContext,char[] literal) throws JSONParseException{
		int index = jsonContext.getIndex();
		for(int i=0;i<literal.length;i++){
			if(jsonContext.getJsonCharValueAtIndex(index+i) != literal[i]){
				throw new JSONParseValueException("解析失败，期望 "+literal[i]+" 实际"+jsonContext.getJsonCharValueAtIndex(index+i),index+i);
			}
		}
		jsonContext.setIndex(index+literal.length-1);
		return jsonContext;
	}
	
	private boolean isNumber1To9(char ch){
		return ('1'<=ch && ch<='9')? true : false;
	}
	
	
	private int parseUnicode(JSONContext jsonContext) throws JSONParseException{
		int index = jsonContext.getIndex();
		char[] array = jsonContext.getJsonCharArray();
		if(checkIndexIfOut(index+4, array)){
			throw new JSONParseValueException("Unicode长度不足",index,JSONType.TYPE_STRING);
		}
		jsonContext.setIndex(++index);
		StringBuilder sb = new StringBuilder(4);
		for(int i = 0;i < 4; i++){
			char ch = jsonContext.getJsonCharValueAtIndex(index+i);
			if      (ch >= '0' && ch <= '9')  sb.append(ch);
	        else if (ch >= 'A' && ch <= 'F')  sb.append(ch);
	        else if (ch >= 'a' && ch <= 'f')  sb.append(ch);
	        else throw new JSONParseValueException("Unicode 格式错误",index,JSONType.TYPE_STRING);
		}
		jsonContext.setIndex(index+4);
		return Integer.parseInt(sb.toString(), 16);
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
	
	public NullValue parseNull(NullValue value,JSONContext jsonContext) throws JSONParseException{
		int index = jsonContext.getIndex();
		char[] array = jsonContext.getJsonCharArray();
		if(checkIndexIfOut(index+3, jsonContext.getJsonCharArray())){
			throw new JSONParseValueException("解析NULL失败,NULL值长度不够",index,JSONType.TYPE_NULL);
		}
		char[] literal = new char[]{'n','u','l','l'};
		validateForLiteral(jsonContext,literal);
		value.setValue("null");
		index = jsonContext.getIndex();
		if(!checkIndexIfOut(index+1, array)){
			jsonContext.setIndex(++index);
			return value;
		}
		else{
			throw new JSONParseSyntaxException("JSON格式错误",index);
		}
	}
	
	public BooleanValue parseTrue(BooleanValue value ,JSONContext jsonContext) throws JSONParseException{
		int index = jsonContext.getIndex();
		char[] array = jsonContext.getJsonCharArray();
		if(checkIndexIfOut(index+3, jsonContext.getJsonCharArray())){
			throw new JSONParseValueException("解析Boolean.TRUE失败,TRUE值长度不够",index,JSONType.TYPE_BOOLEAN);
		}
		char[] literal = new char[]{'t','r','u','e'};
		validateForLiteral(jsonContext,literal);
		value.setValue(true);
		index = jsonContext.getIndex();
		if(!checkIndexIfOut(index+1, array)){
			jsonContext.setIndex(++index);
			return value;
		}
		else{
			throw new JSONParseSyntaxException("JSON格式错误",index);
		}
	}
	
	public BooleanValue parseFalse(BooleanValue value , JSONContext jsonContext) throws JSONParseException{
		int index = jsonContext.getIndex();
		char[] array = jsonContext.getJsonCharArray();
		if(checkIndexIfOut(index+4, jsonContext.getJsonCharArray())){
			throw new JSONParseValueException("解析Boolean.FALSE失败,FALSE值长度不够",index,JSONType.TYPE_BOOLEAN);
		}
		char[] literal = new char[]{'f','a','l','s','e'};
		validateForLiteral(jsonContext,literal);
		value.setValue(false);
		index = jsonContext.getIndex();
		if(!checkIndexIfOut(index+1, array)){
			jsonContext.setIndex(++index);
			return value;
		}
		else{
			throw new JSONParseSyntaxException("JSON格式错误",index);
		}
	} 
	
	
	public NumberValue parseNumber(NumberValue value , JSONContext jsonContext) throws JSONParseException{
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
				throw new JSONParseValueException("数字不合法",index,JSONType.TYPE_NUMBER);
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
				throw new JSONParseValueException("数字不合法",index,JSONType.TYPE_NUMBER);
			}
			numberStr.append(jsonContext.getJsonCharValueAtIndex(index));
			jsonContext.setIndex(++index);
			if(!Character.isDigit(jsonContext.getJsonCharValueAtIndex(index))){
				throw new JSONParseValueException("数字不合法",index,JSONType.TYPE_NUMBER);
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
				throw new JSONParseValueException("数字不合法",index,JSONType.TYPE_NUMBER);
			}
			numberStr.append(jsonContext.getJsonCharValueAtIndex(index));
			jsonContext.setIndex(++index);
			if(jsonContext.getJsonCharValueAtIndex(index) == '+' || jsonContext.getJsonCharValueAtIndex(index) == '-'){
				numberStr.append(jsonContext.getJsonCharValueAtIndex(index));
				if(checkIndexIfOut(index+1, array)){
					throw new JSONParseSyntaxException("JSON格式错误 需要一个终结符",index);
				}
				jsonContext.setIndex(++index);
			}
			if(!Character.isDigit(jsonContext.getJsonCharValueAtIndex(index))){
				throw new JSONParseValueException("数字不合法",index,JSONType.TYPE_NUMBER);
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
			throw new JSONParseValueException("数字越界",index,JSONType.TYPE_NUMBER);
		}
		return value;
	}
	
	public StringValue parseString(StringValue value , JSONContext jsonContext) throws JSONParseException{
		StringBuilder stringValue = new StringBuilder();
		int index = jsonContext.getIndex();
		char[] array = jsonContext.getJsonCharArray();
		char curChar = jsonContext.getJsonCharValueAtIndex(index);
		if(checkIndexIfOut(index+1, array)){
			throw new JSONParseSyntaxException("解析字符串失败，格式错误",index);
		}
		jsonContext.setIndex(++index);
		while(true){
			index=jsonContext.getIndex();
			curChar = jsonContext.getJsonCharValueAtIndex(index);
			if(curChar == '"'){
				value.setValue(stringValue.toString());
				if(!checkIndexIfOut(index+1, array)){
					jsonContext.setIndex(++index);
					return value;
				}
				else{
					throw new JSONParseSyntaxException("JSON格式错误",index);
				}
			}
			else if(curChar == '\\'){
				if(checkIndexIfOut(index+1, array)){
					throw new JSONParseValueException("转义无效",index,JSONType.TYPE_STRING);
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
					case 'u': 
						int nfe = parseUnicode(jsonContext);
						index = jsonContext.getIndex();
						if (nfe >= 0xD800 && nfe <= 0xDBFF){
							index = jsonContext.getIndex();
							curChar = jsonContext.getJsonCharValueAtIndex(index);
							if(curChar != '\\'){
								throw new JSONParseValueException("Unicode格式错误。",index,JSONType.TYPE_STRING);
							}
							if(checkIndexIfOut(index+1, array)){
								throw new JSONParseValueException("Unicode格式错误。",index,JSONType.TYPE_STRING);
							}
							else{
								index++;
							}
							curChar = jsonContext.getJsonCharValueAtIndex(index);
							if(curChar != 'u'){
								throw new JSONParseValueException("Unicode格式错误。",index,JSONType.TYPE_STRING);
							}
							int nfe2 = parseUnicode(jsonContext);
							if (nfe2 < 0xDC00 || nfe2 > 0xDFFF){
								throw new JSONParseValueException("Unicode格式错误。",index,JSONType.TYPE_STRING);
							}
					        nfe = (((nfe - 0xD800) << 10) | (nfe2 - 0xDC00)) + 0x10000;
						}
						stringValue.append((char)nfe);
						break;
					case 't': stringValue.append('\t');break;
					default:
						throw new JSONParseValueException("解析String失败，转义字符非法",index,JSONType.TYPE_STRING);
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
						throw new JSONParseValueException("解析String失败.字符非法",index,JSONType.TYPE_STRING);
				}
			}
			else{
				stringValue.append(curChar);
			}
			if(checkIndexIfOut(index+1, array)){
				throw new JSONParseSyntaxException("JSON格式错误",index);
			}
			jsonContext.setIndex(++index);		
		}
	}
	
	public ArrayValue parseArray(ArrayValue value,JSONContext jsonContext) throws JSONParseException{
		List<? super AbstractJsonValue<?>> list =new ArrayList<>();
		char[] array = jsonContext.getJsonCharArray();
		int index = jsonContext.getIndex();
		if(checkIndexIfOut(index+1, array)){
			throw new JSONParseSyntaxException("解析Array失败，应以]结尾",index);
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
					throw new JSONParseSyntaxException("，后需要一个值",index);
				}
			}
			else if(ch == ']'){
				value.setValue(list);
				if(!checkIndexIfOut(index+1, array))
					jsonContext.setIndex(++index);
				else
					throw new JSONParseSyntaxException("JSON格式错误",index);
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
			throw new JSONParseSyntaxException("解析Object失败，应以}结尾。",index);
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
						throw new JSONParseSyntaxException("JSON格式错误,缺少值",index);
					}
					jsonContext.setIndex(++index);
					jsonContext = parseWhiteSpace(jsonContext);
					break;
				case ':':
					if(checkIndexIfOut(index+1, array)){
						throw new JSONParseSyntaxException("JSON格式错误,缺少值",index);
					}
					jsonContext.setIndex(++index);
					jsonContext = parseWhiteSpace(jsonContext);
					if(valueMap.containsKey(nameTmp.toString())){
						throw new JSONParseValueException("JSON Key:"+nameTmp+" 重复。",index,JSONType.TYPE_OBJECT);
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
		if(StringUtils.isEmpty(jsonStr)){
			throw new JSONParseSyntaxException("JSON串空");
		}
		JSONContext jsonContext = new JSONContext(jsonStr);
		parseWhiteSpace(jsonContext);
		AbstractJsonValue<?> v = parseValue(jsonContext);
		if(jsonContext.getIndex() < (jsonContext.getJsonCharArray().length-1)){
			jsonContext.setIndex(jsonContext.getIndex()+1);
			parseWhiteSpace(jsonContext);
			char curChar = jsonContext.getJsonCharValueAtIndex(jsonContext.getIndex());
			if(curChar != ' ' && curChar != '\t' && curChar != '\n' && curChar != '\r')
				throw new JSONParseSyntaxException("JSON格式错误！");
		}
		return (T) v;
	}

	
}
