package com.revature.repository;

import com.revature.model.Users;

public interface UsersRepository {

	public Users getUsers(String user);
	
	public boolean setBalance(String userID, long balance);
	
}
