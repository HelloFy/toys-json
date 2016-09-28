package com.khalid.toys.json.core.test.parse.value;

import org.junit.Assert;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.khalid.toys.json.core.exception.JsonParseException;
import com.khalid.toys.json.core.exception.ParseExpectValueException;
import com.khalid.toys.json.core.exception.ParseInvalidValueException;
import com.khalid.toys.json.core.exception.ParseNumberTooHugeExcpetion;
import com.khalid.toys.json.core.exception.ParseRootNotSingularException;
import com.khalid.toys.json.core.parse.SimpleValueParser;
import com.khalid.toys.json.core.value.BooleanValue;
import com.khalid.toys.json.core.value.NullValue;
import com.khalid.toys.json.core.value.NumberValue;
import com.khalid.toys.json.core.value.StringValue;

public class ParseValueTest 
{
	@Test
    public void testParseNullValue(){
		try {
			Assert.assertEquals("value:null",new SimpleValueParser<NullValue>().parse("").toString());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void testParseBooleanValue(){
    	try {
			Assert.assertEquals("value:true",new SimpleValueParser<BooleanValue>().parse(" true ").toString());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Assert.assertEquals("value:false",new SimpleValueParser<BooleanValue>().parse(" false ").toString());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void testParseNumber(){
    	try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("1.0000000000000002"));
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("4.9406564584124654e-324"));
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("-4.9406564584124654e-324"));
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("2.2250738585072009e-308"));
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("-2.2250738585072009e-308"));
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("2.2250738585072014e-308"));
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("-2.2250738585072014e-308"));
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("1.7976931348623157e+308"));
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("-1.7976931348623157e+308"));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    @Test
    public void parseString(){
		try {
			System.out.println(new SimpleValueParser<StringValue>().parse("\"232\n\"32\""));
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<StringValue>().parse("\"232\n32\"\""));
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<StringValue>().parse("\"232\tt\"\"\"\"\"22"));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }
    
    @Test
    public void testToys(){
    	String s = "12\2E11";
    	char c1= '\7';
    	System.out.println((int)'\"'<0x20);
    	System.out.println((char)0x09);
    	System.out.println((int)c1);
    	char c = s.charAt(2);
    	String jsonStr ="{\"value\":\"12\2E11\",\"js\":true}";
    	JSONObject jsonObject = new JSONObject();
    	Object object = jsonObject.parse(jsonStr);
    	TestParseObject object2 = (TestParseObject) jsonObject.parseObject(jsonStr,TestParseObject.class);
//    	double value = Double.valueOf(s);
    	if(c==92){
    		return;
    	}
    	System.out.println((char)92);
    }
    
    
}
