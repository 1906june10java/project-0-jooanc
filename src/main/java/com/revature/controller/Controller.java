package com.revature.controller;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.exception.InvalidMenuException;
import com.revature.service.ServiceUtil;

public class Controller {
	
	private static final Logger LOGGER = Logger.getLogger(Controller.class); 
	
public Controller(){
	
	//input
	Scanner input = new Scanner(System.in); 
	String input_holder = "0";
	
	//controller state
	Boolean loginState = false;
	String menuState = "0";
	
	//information stored on local machine about user.
	String [] userInfo = null;
	String username = "";
	String password = "";
	ServiceUtil services = new ServiceUtil();
	long balance = 0;
	
	while (true) {

		if (loginState == false) {
				System.out.println("Type the corresponding number to make your selection. \n Login (1) Register(2)");
				input_holder = input.nextLine();
				
			if(input_holder.equals("2")) {	
				System.out.println("Enter username.\n");
				input_holder = input.nextLine();
				username = input_holder;
				
				System.out.println("Enter password.\n");
				input_holder = input.nextLine();
				password = input_holder;
				
				services.registerNewUser(username, password);
				input_holder = "1";
			}
			
			if(input_holder.equals("1")) {
				System.out.println("Enter username: ");
				input_holder = input.nextLine();
				userInfo = services.getUserInfo(input_holder);
				
			if(input_holder.equals(userInfo[1])) {
				System.out.println("Enter password: ");
				input_holder = input.nextLine();
				
				if(input_holder.equals(userInfo[2])) {
					System.out.println("Login successful.");
					menuState = "0";
					balance = Long.valueOf(userInfo[3]);
					loginState = true;
				}
				else {System.out.println("Invalid Password\n");}
			}
			else {System.out.println("Invalid Username\n");}
		}
			
			else {System.out.println("Invalid input_holder\n");}
			
		}
		
		
		else if (loginState == true) {
			
				
				if(menuState.equals("0")) {
				System.out.println("\nType the corresponding number to make your selection.\n ----------------\n|Deposit(1)\n|Withdraw(2)\n|Check Balance(3)\n|Logout(4)\n ----------------\n");
				menuState = input.nextLine();
				}
				
				if(menuState.equals("1")) {

				System.out.printf("\nEnter deposit amount. Current balance: %d\n", balance);
				input_holder = input.nextLine();
				try {
				if(Integer.valueOf(input_holder) < 0) {
					System.out.println("Negative deposit detected. Voiding transaction.");
					System.out.println("\nType the corresponding number to make your selection.\n --------------\n Deposit(1)\n  Withdraw(2)\n  Check Balance(3)\n  Logout(4)\n --------------\n");
					menuState = input.nextLine();
				}
				else {
				balance += Integer.valueOf(input_holder);
				services.pushBalance(userInfo [1], balance);
				System.out.printf("Deposited $ %s, Balance updated to: $%d\n", input_holder, balance);
				System.out.println("\nType the corresponding number to make your selection.\n --------------\n Deposit(1)\n  Withdraw(2)\n  Check Balance(3)\n  Logout(4)\n --------------\n");
				menuState = input.nextLine();
				}
				}
				catch (NumberFormatException e) {
					LOGGER.error("Invalid characters used.");
				System.out.println("\nType the corresponding number to make your selection.\n ----------------\n|Deposit(1)\n|Withdraw(2)\n|Check Balance(3)\n|Logout(4)\n ----------------\n");

					menuState = input.nextLine();
				}
				
				}
				
				if(menuState.equals("2")) {

				System.out.printf("\nEnter withdrawl amount. Current balance: %d\n", balance);
				input_holder = input.nextLine();
				try {
					if(Integer.valueOf(input_holder) < 0) {
						System.out.println("Negative withdrawls not allowed.");
						System.out.println("\nType the corresponding number to make your selection.\n --------------\n Deposit(1)\n  Withdraw(2)\n  Check Balance(3)\n  Logout(4)\n --------------\n");
						menuState = input.nextLine();
					}
					else if (Integer.valueOf(input_holder) > balance) {
					System.out.println("You have not enough funds.");
					System.out.println("\nType the corresponding number to make your selection.\n --------------\n Deposit(1)\n  Withdraw(2)\n  Check Balance(3)\n  Logout(4)\n --------------\n");
					menuState = input.nextLine();
				}
				else {
				balance -= Integer.valueOf(input_holder);
				services.pushBalance(userInfo [1], balance);
				System.out.printf("Withdrew $ %s, Balance updated to: $%d\n", input_holder, balance);
				System.out.println("\nType the corresponding number to make your selection.\n --------------\n Deposit(1)\n  Withdraw(2)\n  Check Balance(3)\n  Logout(4)\n --------------\n");
				menuState = input.nextLine();
				}
				} catch (NumberFormatException e) {
					LOGGER.error("Invalid characters used.");
					System.out.println("\nType the corresponding number to make your selection.\n --------------\n Deposit(1)\n  Withdraw(2)\n  Check Balance(3)\n  Logout(4)\n --------------\n");
					menuState = input.nextLine();
				}
				}

				if(menuState.equals("3")) {
				System.out.printf("Your balance is: $%d\n", balance);
				System.out.println("\nType the corresponding number to make your selection.\n --------------\n Deposit(1)\n  Withdraw(2)\n  Check Balance(3)\n  Logout(4)\n --------------\n");
				menuState = input.nextLine();
				}
				
				if(menuState.equals("4")) {
					System.out.println("Transaction complete. Migrating to main menu.\n");
					loginState = false;
				}
				
				else if (!menuState.equals("0")&&!menuState.equals("1")&&!menuState.equals("2")&&!menuState.equals("3")&&!menuState.equals("4")) {
					try {
						throw new InvalidMenuException();
					} catch (InvalidMenuException e) {
						LOGGER.error("Illegal action. Returned to menu.");
						menuState = "0";
					}
					
				}
				
				else {}
				
		}
		
		
	}
		}
	
}