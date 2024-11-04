package com.codegnan.cards;

import java.util.ArrayList;

import java.util.Collections;

import com.codegnan.Interfaces.I_ATM_Service;
import com.codegnan.customException.InsufficientBalanceException;
import com.codegnan.customException.InsufficientMachineBalanceException;
import com.codegnan.customException.InvalidAmountException;
import com.codegnan.customException.NotAOperatorException;

public class SBIDebitCare implements I_ATM_Service {
	
	String name;
	long debitCardNumber;
	double accountBalance;
	int pinNumber;
	ArrayList <String> statement;
	final String type = "user";
	int chances;
	// constractor
	public SBIDebitCare(long debitCardNumber,String name,  double accountBalance, int pinNumber) {
		chances=3;
		this.name = name;
		
		this.accountBalance = accountBalance;
		this.pinNumber = pinNumber;
		statement = new ArrayList<>();
	}

	@Override
	public String getUserType() throws NotAOperatorException {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public double withDrawAmount(double withAmount)
			throws InvalidAmountException, InsufficientBalanceException, InsufficientMachineBalanceException {
		// TODO Auto-generated method stub
		if(withAmount <= 0) {
			throw new InvalidAmountException("you can enter zero(0) amount to withdraw. please enter a valid amount");
		}else if (withAmount%10 != 0) {
			throw new InsufficientBalanceException("please withdraw multiples of 100");
		}
		 else if (withAmount < 500) {
	          	throw new InsufficientBalanceException("Please Withdraw More Than 500");

		 }
		else if (withAmount >accountBalance) {
			throw new InsufficientBalanceException ("you don't have sufficient funds to withdraw ... please check your balance");
		}else {
			accountBalance = accountBalance-withAmount;
			statement.add("Debited : "+withAmount);
			return withAmount;
		}
	
	}

	@Override
	public void depositAmount(double dptAmount) throws InvalidAmountException {
		// TODO Auto-generated method stub
		if (dptAmount <= 0 || dptAmount%10 != 0) {
			throw new InvalidAmountException("please deposit multiples of 100");
		}else {
			accountBalance = accountBalance+dptAmount;
			statement.add("Creadited : "+dptAmount);
		}
		
		
	}

	@Override
	public double checkAccountBalance() {
		// TODO Auto-generated method stub
		return accountBalance;
	}

	@Override
	public void changePinNumber(int pinNumber) {
		// TODO Auto-generated method stub
		this.pinNumber = pinNumber;
		
	}

	@Override
	public int getPinNumber() {
		// TODO Auto-generated method stub
		return pinNumber;
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void decreaseChances() {
		// TODO Auto-generated method stub
		--chances;
	}

	@Override
	public int getChances() {
		// TODO Auto-generated method stub
		return chances;
	}

	@Override
	public void resetPinChances() {
		// TODO Auto-generated method stub
		chances = 3;
		
	}

	@Override
	public void generateMiniStatement() {
		// TODO Auto-generated method stub
		int count = 5;
		if(statement.size()==0) {
			System.out.println("there are no transaction happend");
			return;
		}
		System.out.println("Last 5 Transaction ");
		Collections.reverse(statement);
		for(String tran : statement) {
			if(count ==0) {
				break;
			}
			System.out.println(tran);
			count--;
		}
		Collections.reverse(statement);
		
	}

}
