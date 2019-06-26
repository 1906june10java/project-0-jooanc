package com.revature.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.exception.InvalidNumberException;
import com.revature.model.Users;
import com.revature.repository.UsersRepojdbc;

public class ServiceUtil {
	
	private static final Logger LOGGER = Logger.getLogger(ServiceUtil.class);

	UsersRepojdbc userRepo = new UsersRepojdbc();
	
	public String [] getUserInfo(String userName) {

		try {
		Users localUser = userRepo.getUsers(userName);
		String [] localArray = localUser.toStrArray();
		return localArray;
		} catch (NullPointerException e){
			LOGGER.error(e);
			String [] localArray = {null, null, null, null};
			return localArray;
		}
		
	}
	
	public void pushBalance(String user, long balance){
		if (balance < 0) {
			try {
				throw new InvalidNumberException();
			} catch(InvalidNumberException e) {
				e.printStackTrace();
				LOGGER.error("Below zero balance. Transaction void.");
			}
		}
		else {
		userRepo.setBalance(user, balance);
		}
	}
	
	public boolean registerNewUser(String user, String password){
		
		List <Users> local = userRepo.getAllUsers();
		boolean newAccount = true;
	 
	 		for(Users existingAccount : local){
	 			if (user.equals(existingAccount.toStrArray()[1])){
	 				newAccount = false;
	 			}
	 			
	 		}
	 
	 		if(newAccount == true){
	 		userRepo.createUsers(local.size()+1, user, password);
	 		System.out.println("Account created for " + user);
	 		return true;
	 		}
	 		
	 		else {
	 		LOGGER.error("Username already in use.");	
	 		return false;
	 		}
	 }
	 
	
}