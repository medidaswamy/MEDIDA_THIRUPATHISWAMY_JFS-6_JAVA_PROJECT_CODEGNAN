package com.codegnan.customException;



public class InsufficientMachineBalanceException extends Exception {
	public InsufficientMachineBalanceException(String errorMsg) {
		super(errorMsg);
	}

}
