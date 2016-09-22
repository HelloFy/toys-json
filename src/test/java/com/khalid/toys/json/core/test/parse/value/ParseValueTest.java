package com.khalid.toys.json.core.test.parse.value;

import org.junit.Assert;
import org.junit.Test;

import com.khalid.toys.json.core.exception.ParseExpectValueException;
import com.khalid.toys.json.core.exception.ParseInvalidValueException;
import com.khalid.toys.json.core.exception.ParseRootNotSingularException;
import com.khalid.toys.json.core.parse.SimpleValueParser;
import com.khalid.toys.json.core.value.BooleanValue;
import com.khalid.toys.json.core.value.NullValue;
import com.khalid.toys.json.core.value.NumberValue;

public class ParseValueTest 
{
	@Test
    public void testParseNullValue(){
    	try {
			Assert.assertEquals("value:null",new SimpleValueParser<NullValue>().parse("").toString());
		} catch (ParseRootNotSingularException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseExpectValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseInvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void testParseBooleanValue(){
    	try {
    		Assert.assertEquals("value:true",new SimpleValueParser<BooleanValue>().parse(" true ").toString());
			Assert.assertEquals("value:false",new SimpleValueParser<BooleanValue>().parse(" false ").toString());
		} catch (ParseRootNotSingularException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseExpectValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseInvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void testParseNumber(){
    	try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("1.0000000000000002"));
		} catch (ParseRootNotSingularException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseExpectValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseInvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("4.9406564584124654e-324"));
		} catch (ParseRootNotSingularException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseExpectValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseInvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("-4.9406564584124654e-324"));
		} catch (ParseRootNotSingularException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseExpectValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseInvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("2.2250738585072009e-308"));
		} catch (ParseRootNotSingularException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseExpectValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseInvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("-2.2250738585072009e-308"));
		} catch (ParseRootNotSingularException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseExpectValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseInvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("2.2250738585072014e-308"));
		} catch (ParseRootNotSingularException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseExpectValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseInvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("-2.2250738585072014e-308"));
		} catch (ParseRootNotSingularException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseExpectValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseInvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("1.7976931348623157e+308"));
		} catch (ParseRootNotSingularException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseExpectValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseInvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			System.out.println(new SimpleValueParser<NumberValue>().parse("-1.7976931348623157e+308"));
		} catch (ParseRootNotSingularException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseExpectValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseInvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void testToys(){
    	String s = "123E11";
    	char c = s.charAt(0);
    	double value = Double.valueOf(s);
    	System.out.println(value);
    }
    
    
}
