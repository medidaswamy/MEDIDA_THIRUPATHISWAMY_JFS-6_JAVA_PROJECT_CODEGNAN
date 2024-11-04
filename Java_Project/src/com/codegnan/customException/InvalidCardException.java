package com.codegnan.customException;

public class InvalidCardException extends Exception{
	public  InvalidCardException(String errorMsg) {
		super(errorMsg);
	}

}
