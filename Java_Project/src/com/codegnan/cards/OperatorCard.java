package com.codegnan.cards;


import com.codegnan.Interfaces.I_ATM_Service;

import com.codegnan.customException.InsufficientBalanceException;
import com.codegnan.customException.InsufficientMachineBalanceException;
import com.codegnan.customException.InvalidAmountException;
import com.codegnan.customException.NotAOperatorException;
//import com.codegnan.customException.NotAOperatorException;

public class OperatorCard implements I_ATM_Service {
	
	private int pinNumber;
	private long id;
	private String name;
	private final String type = "operator";
	// constractor

	

	public OperatorCard( long id,int pinNumber, String name) {
		
		
		id = id;
		pinNumber = pinNumber;
		this.name = name;
	}
	
	public String getUserType() throws NotAOperatorException {
		// TODO Auto-generated method stub
		return type;
	}

	public double withDrawAmount(double withAmount)
			throws InvalidAmountException, InsufficientBalanceException, InsufficientMachineBalanceException {
	
			return 0;
		}
	
	

	
	public void depositAmount(double dptAmount) throws InvalidAmountException {
	
		
	}

	
	public double checkAccountBalance() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void changePinNumber(int pinNumber) {
		
		
	}

	
	public int getPinNumber() {
		// TODO Auto-generated method stub
		return pinNumber;
	}

	public String getUserName() {
		// TODO Auto-generated method stub
		return name;
	}

	public void decreaseChances() {
		// TODO Auto-generated method stub
		
	}

	public int getChances() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void resetPinChances() {
		// TODO Auto-generated method stub
	
		
	}

	public void generateMiniStatement() {
	
}
}
