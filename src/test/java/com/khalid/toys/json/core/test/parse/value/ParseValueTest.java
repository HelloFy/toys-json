package com.khalid.toys.json.core.test.parse.value;

import org.junit.Assert;
import org.junit.Test;

import com.khalid.toys.json.core.exception.ParseExpectValueException;
import com.khalid.toys.json.core.exception.ParseInvalidValueException;
import com.khalid.toys.json.core.exception.ParseRootNotSingularException;
import com.khalid.toys.json.core.parse.SimpleParser;
import com.khalid.toys.json.core.value.BooleanValue;
import com.khalid.toys.json.core.value.NullValue;

public class ParseValueTest 
{
	@Test
    public void testParseNullValue(){
    	try {
			Assert.assertEquals("value:null",new SimpleParser<NullValue>().parse("  null").toString());
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
    		Assert.assertEquals("value:true",new SimpleParser<BooleanValue>().parse(" true ").toString());
			Assert.assertEquals("value:false",new SimpleParser<BooleanValue>().parse(" false ").toString());
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
    
    
}
