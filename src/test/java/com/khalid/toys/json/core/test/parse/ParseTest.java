package com.khalid.toys.json.core.test.parse;

import org.junit.Test;

import com.khalid.toys.json.core.exception.ParseRootNotSingularException;
import com.khalid.toys.json.core.parse.SimpleParser;
import com.khalid.toys.json.core.value.NullValue;

public class ParseTest 
{
    @Test
    public void testParseNullValue(){
    	try {
			System.out.println(new SimpleParser<NullValue>().parse("  nul"));
		} catch (ParseRootNotSingularException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
