package com.codegnan.operations;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Scanner;

import com.codegnan.Interfaces.I_ATM_Service;
import com.codegnan.cards.AxisDebitCard;
import com.codegnan.cards.HDFCDebitCard;
import com.codegnan.cards.OperatorCard;
import com.codegnan.cards.SBIDebitCare;

import com.codegnan.customException.IncorrectPinLimitReachedException;
import com.codegnan.customException.InsufficientBalanceException;
import com.codegnan.customException.InsufficientMachineBalanceException;
import com.codegnan.customException.InvalidAmountException;
import com.codegnan.customException.InvalidCardException;
import com.codegnan.customException.InvalidPinException;
import com.codegnan.customException.NotAOperatorException;

public class ATMOperations {
	public static double ATM_MACHINE_BALANCE = 100000.0; // INITIAL ATM MACHINE BALANCE

	// list to keep track of all activities perfomed on the atm.
	public static ArrayList<String> ACTIVITY = new ArrayList<>();

	// database to hash map cardnumber to card object
	public static HashMap<Long, I_ATM_Service> dataBase = new HashMap<>();

	// if the atm machine is an off or on
	public static boolean MACHINE_ON = true;

	// reference to the current card in use
	public static I_ATM_Service card;

	// validate inserted card by checking againist the database
	public static I_ATM_Service validateCard(long cardNumber) throws InvalidCardException {
		if (dataBase.containsKey(cardNumber)) {
			return dataBase.get(cardNumber);
		} else {
			ACTIVITY.add("Accessed by : " + cardNumber + " is not compatiable");
			throw new InvalidCardException("this is not a valid card");
		}
	}

	// display activities performes on the atm
	public static void checkATMMachineActivities() {
		System.out.println("=======================================Activities Performed=================");
		for (String activity : ACTIVITY) {
			System.out.println("=========================================");
			System.out.println(activity);
		}
		System.out.println("==================================================");
	}

	// reset the number of pin attempts for a user
	public static void resetUserAttempts(I_ATM_Service OperatorCard) {
		I_ATM_Service card = null;
		long number;

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your card number");
		number = sc.nextLong();
		try {
			card = validateCard(number);
			card.resetPinChances(); // reset the pin attempts for the specified card
			ACTIVITY.add("Accessed by : " + OperatorCard.getUserName() + " to reset the number of chances to user");
		} catch (InvalidCardException ive) {

			System.out.println(ive.getMessage());
		}
	}

	// validate user Credientails including pin verification
	public static I_ATM_Service validateCredentials(long cardNumber, int pinNumber) 
			throws InvalidCardException, IncorrectPinLimitReachedException, InvalidPinException {
		if(dataBase.containsKey(cardNumber)) {
			card = dataBase.get(cardNumber);
		}else {
			throw new InvalidCardException("this car is not a valid card");
		}
		try {
			//operator a different pin validation process
			if(card.getUserType().equals("operator")) {
				if(card.getPinNumber() != pinNumber) {
					throw new InvalidPinException("dear operator.... Please enter correct pin number");
				}else {
					return card;
				}
			}
		}catch(NotAOperatorException noe){
			noe.printStackTrace();
			
		}
		
	
	// validate pin and handle incorrect pin attempts
	if(card.getChances()<=0)

	{
		throw new IncorrectPinLimitReachedException("you have reached wrong limit of pin number which is 3 attempts");
	}if(card.getPinNumber()!=pinNumber)
	{
		card.decreaseChances(); // decrease the number of remaining chances
		throw new InvalidPinException("You have entered a wrong pin number");
	}else
	{
		return card;
	}
	}

	// validate the amount for withdraw t ensure sufficient machine balance
	
	public static void validateAmount(double amount) throws InsufficientMachineBalanceException{
		if(amount > ATM_MACHINE_BALANCE) {
			throw new InsufficientMachineBalanceException("insufficient cash in the machine");
		}
	}

	// validate deposit amount to ensure it too meat machine requirement
	public static void validateDepositAmount(double amount) throws InsufficientMachineBalanceException, InvalidAmountException{
		if(amount %100 != 0) {
			throw new InvalidAmountException("please deposit amount multiples of 100 ");
		}
		if(amount + ATM_MACHINE_BALANCE > 200000.0d) {
			ACTIVITY.add("unable to deposit cash in the machine...");
			throw new InsufficientMachineBalanceException("you can't deposit the cash as the limit of machine is reached");
		}
	}
	
	// operator availabe in operator mode
	public static void operatorMode(I_ATM_Service card) {
		Scanner sc = new Scanner(System.in);
		double amount;
		boolean flag = true;
		while (flag) {
			System.out.println("Operator Mode: operator Name : "+card.getUserName());
			System.out.println("====================================");

			System.out.println("||   0.switch off the machine                                   ||");
			System.out.println("||   1.check the  ATM machine Balance                                ||");
			System.out.println("||   2.Deposit cash in the  machine                                   ||");
			System.out.println("||   3.reset the user pin attempts                                  ||");
			System.out.println("||   4.check activities performed on the  machine                                   ||");
			System.out.println("||   5.exit operator mode                                   ||");
			System.out.println("Please enter your choice");
			int option = sc.nextInt();
			switch(option) {
			case 0:
				MACHINE_ON = false;
				ACTIVITY.add("Accessed by : "+card.getUserName()+"Activity performed by switch of the ATM machine");
				flag = false;
				break;
				
			case 1:
				ACTIVITY.add("Accessed by : "+card.getUserName()+"Activity performent by : checkin Atm machine balance :");
				System.out.println("The balance of ATM machine is : "+ATM_MACHINE_BALANCE+" IS available");
				break;
				
			case 2:
				System.out.println("Enter the amount to deposit : ");
				amount = sc.nextDouble();
				try {
					validateDepositAmount(amount);
					ATM_MACHINE_BALANCE += amount;
					ACTIVITY.add("Accessed by : "+card.getUserName()+" Activity performed by : deposit cash into atm machine");
					System.out.println("===================================================");
					System.out.println("==============================cash added in the atm machine===========");
					System.out.println("============================================================");
					
				}catch(InvalidAmountException | InsufficientMachineBalanceException e) {
			
			       System.out.println(e.getMessage());
				}
				break;
			case 3:
				resetUserAttempts(card);
				System.out.println("===================================================");
				System.out.println("======================User attempts are reset===========");
				System.out.println("============================================================");
				ACTIVITY.add("Accessed by : "+card.getUserName()+" Activity performed by : resetting the pin attempts of user");
				break;
			case 4:
				checkATMMachineActivities();
				break;
			case 5:
				flag = false;
				break;
				default:
					System.out.println("you have entered a wrong option");
				
			} 
			
		}
	}
	public static void main(String[] args) throws NotAOperatorException {
		dataBase.put(2222222221L,new AxisDebitCard(2222222221L,"yashas",50000,22222));
		dataBase.put(3333333331L, new SBIDebitCare(3333333331L,"akshay",55000,33333));
		dataBase.put(4444444441L,new  HDFCDebitCard(4444444441L,"DAS",32000,44444));
		dataBase.put(1111111111L, new OperatorCard(1111111111L,1111,"Operator"));
		
		Scanner sc = new Scanner(System.in);
		long cardNumber = 0;
		double depositAmount = 0.0;
		double withdrawAmount = 0.0;
		int pin = 0;
		
		//main loop for Atm operator
		
		while(MACHINE_ON) {
			System.out.println("Please Enter  Debit Card Number");
			cardNumber = sc.nextLong();
			try {
				System.out.println("Enter PIn number");
				pin = sc.nextInt();
				card = validateCredentials(cardNumber, pin);
				if(card == null ) {
					System.out.println("card validation failure : ");
					continue;
				}
				ACTIVITY.add("Accessed by : "+card.getUserName()+"status : Access Approved");
				if (card.getUserType().equals("operator")) {
					operatorMode(card);
					continue;
				}
				while(true) {
					System.out.println("user Mode : "+card.getUserName());
					System.out.println("=====================================================");
					System.out.println("||        1.Withdraw Amount                             ||");
					System.out.println("||        2.Deposit Amount                                ||");
					System.out.println("||        3.check balance                                    ||");
					System.out.println("||        4.change pin                                         ||");
					System.out.println("||        5.mini statement                                  ||");
					System.out.println("========================================================");
					
					System.out.println("Enter your choice ");
					 int option = sc.nextInt();
					 try {
						 switch(option) {
						 case 1:
							 System.out.println("Please Enter the amount to Withdraw .............");
							 withdrawAmount = sc.nextDouble();
							 validateAmount(withdrawAmount);
							 card.withDrawAmount(withdrawAmount);
							 ATM_MACHINE_BALANCE = ATM_MACHINE_BALANCE - withdrawAmount;
							 ACTIVITY.add("Accessed by : "+card.getUserName()+" Activity : amount withdraw "+withdrawAmount+" from machine" );
							 break;
						 case 2:
							 System.out.println("Enter the amount to deposit");
							 depositAmount = sc.nextDouble();
							 validateDepositAmount(depositAmount);
							 ATM_MACHINE_BALANCE = ATM_MACHINE_BALANCE+depositAmount;
							 card.depositAmount(depositAmount);
							 ACTIVITY.add("Accessed by : "+card.getUserName()+" Activity : amount deposit "+depositAmount +" from machine");
							 break;
						 case 3:
							 System.out.println("your account balance is : "+card.checkAccountBalance());
							 ACTIVITY.add("Accessed by : "+card.getUserName()+" Activity : Checking the balance ");
							 break;
						 case 4:
							 System.out.println("Enter a new pin :");
							 pin = sc.nextInt();
							 card.changePinNumber(pin);
							 ACTIVITY.add("Accessed by : "+card.getUserName()+" Activity : changing the pin number ");
							 break;
						 case 5: 
							 ACTIVITY.add("Accessed by : "+card.getUserName()+" Activity : generating mini  statement");
							 card.generateMiniStatement();
							 break;
							 default:
								 System.out.println("you have entered a wrong option................");
							 break;
						 }
						 System.out.println("Do you want continue ?(Y/N): ");
						 String nextOption = sc.next();
						 if(nextOption.equalsIgnoreCase("N")) {
							 break;
						 }
						 
					 }catch(InvalidAmountException | InsufficientBalanceException | InsufficientMachineBalanceException  e) {
						    System.out.println( e.getMessage());
					 }
				}
						
			}catch(InvalidPinException | InvalidCardException | IncorrectPinLimitReachedException e) {
				ACTIVITY.add("Accessed by : "+card.getUserName()+"status : Access denied : ");
				e.printStackTrace();
			}
		}
		System.out.println("===========================================================");
		System.out.println("=====================Thanks for using Icci Atm machine=================");
		System.out.println("=======================Thanking you   visit again=================");
		System.out.println("========================================================");
		
		
	}

	

}
