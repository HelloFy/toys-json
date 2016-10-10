package com.khalid.toys.json.core.parse;

import org.junit.Assert;

public class JSONContext {

	private char[] jsonCharArray;
	
	private String jsonStr;
	
	private int index = 0;
	
	public JSONContext(String jsonStr){
		this.setJsonStr(jsonStr);
		setJsonCharArray(jsonStr.toCharArray());
	}

	public char[] getJsonCharArray() {
		return jsonCharArray;
	}

	private void setJsonCharArray(char[] jsonCharArray) {
		this.jsonCharArray = jsonCharArray;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
		setJsonCharArray(jsonStr.toCharArray());
	}
	
	public char getJsonCharValueAtIndex(int index){
		Assert.assertNotNull(jsonCharArray);
		Assert.assertTrue(index >= 0 && index < jsonCharArray.length);
		setIndex(index);
		return jsonCharArray[this.index];
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
