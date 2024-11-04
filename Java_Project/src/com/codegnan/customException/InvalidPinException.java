package com.codegnan.customException;

public class InvalidPinException extends Exception {
	public InvalidPinException(String errorMsg) {
		super(errorMsg);
	}

}
