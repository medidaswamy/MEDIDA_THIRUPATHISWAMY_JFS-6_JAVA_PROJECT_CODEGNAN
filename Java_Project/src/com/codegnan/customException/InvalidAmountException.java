package com.codegnan.customException;

public class InvalidAmountException extends Exception {
	public InvalidAmountException(String errorMsg) {
		super(errorMsg);
	}

}
