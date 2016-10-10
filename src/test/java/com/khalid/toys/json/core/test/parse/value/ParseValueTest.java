package com.khalid.toys.json.core.test.parse.value;

import org.junit.Assert;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.khalid.toys.json.core.exception.JSONParseException;
import com.khalid.toys.json.core.parse.SimpleValueParser;
import com.khalid.toys.json.core.value.ArrayValue;
import com.khalid.toys.json.core.value.BooleanValue;
import com.khalid.toys.json.core.value.NullValue;
import com.khalid.toys.json.core.value.NumberValue;
import com.khalid.toys.json.core.value.StringValue;

public class ParseValueTest 
{
	@Test
    public void testParseNullValue(){
		try {
			Assert.assertEquals("value:null",new SimpleValueParser<NullValue>().parse("nul ").toString());
		} catch (JSONParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void testParseBooleanValue(){
    	try {
			Assert.assertEquals("value:true",new SimpleValueParser<BooleanValue>().parse(" true ").toString());
		} catch (JSONParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Assert.assertEquals("value:false",new SimpleValueParser<BooleanValue>().parse(" false ").toString());
		} catch (JSONParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void testParseNumber(){
    	try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("1.0000000000000002"));
		} catch (JSONParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("4.9406564584124654e-324"));
		} catch (JSONParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("-4.9406564584124654e-324"));
		} catch (JSONParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("2.2250738585072009e-308"));
		} catch (JSONParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("-2.2250738585072009e-308"));
		} catch (JSONParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("2.2250738585072014e-308"));
		} catch (JSONParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("-2.2250738585072014e-308"));
		} catch (JSONParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("1.7976931348623157e+308"));
		} catch (JSONParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("-1.7976931348623157e+308"));
		} catch (JSONParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    @Test
    public void parseString(){
		try {
			System.out.println(new SimpleValueParser<StringValue>().parse("\"232\\u52B2\n\" "));
		} catch (JSONParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<StringValue>().parse("\"232\n32\"\""));
		} catch (JSONParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			System.out.println(new SimpleValueParser<StringValue>().parse("\"232\tt\\\"\" "));
		} catch (JSONParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }
    
    @Test
    public void testToys(){
    	String s = "12\2E11";
    	char c1= '\7';
    	System.out.println((int)'\"');
    	System.out.println((int)':'<0x20);
    	System.out.println('\"'=='"');
    	System.out.println((char)0x09);
    	System.out.println((int)c1);
    	System.out.println(123<0x1FFF);
    	char c = s.charAt(2);
    	String s1="{\"array\":[  \"12,\\"+"\"31\"  ,  \"23\"  ,  1.1  ,  123  ]  }";

    	JSONObject jsonObject = new JSONObject();
    	jsonObject.parse(s1);
//    	Double.valueOf(s);
    	if(c==92){
    		return;
    	}
    	System.out.println((char)92);
    }
    
    @Test
    public void testParseArray(){
    	String s="   [  \"12\\"+"\"31\"  ,  \"23\"  ,  1.1  ,  123,true,false,null,[123,234,true]]   ";
    	System.out.println(s);
    	try {
			System.out.println(new SimpleValueParser<ArrayValue>().parse(s));
		} catch (JSONParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void testParseObject(){
    	String s = "{\"name\":\"费玥\",\"value\":123,\"name2\":[\"123123\",1.1,123]}";
    	try {
			System.out.println(new SimpleValueParser<>().parse(s));
		} catch (JSONParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    
}
