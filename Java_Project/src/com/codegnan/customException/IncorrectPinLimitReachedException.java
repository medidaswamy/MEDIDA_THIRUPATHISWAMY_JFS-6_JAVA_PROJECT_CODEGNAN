package com.codegnan.customException;

public class IncorrectPinLimitReachedException extends Exception {
	public IncorrectPinLimitReachedException(String errorMsg) {
		super(errorMsg);
	}

}
