package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.model.Users;
import com.revature.util.ConnectionUtil;

public class UsersRepojdbc implements UsersRepository{

	private static final Logger LOGGER = Logger.getLogger(UsersRepojdbc.class);
	
	@Override
	public Users getUsers(String user) {
		// TODO Auto-generated method stub
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
		LOGGER.trace("Looking for account by the name of: " + user);
		String sql = "SELECT * FROM USERS WHERE U_NAME = ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(++parameterIndex, user);
		ResultSet result = statement.executeQuery();
		
		if(result.next()) {
			
			Users Account = new Users (
					result.getLong("U_ID"),
					result.getString("U_NAME"),
					result.getString("U_PASSWORD"),
					result.getLong("U_BALANCE")
					);
			
			LOGGER.trace("My account is: " + Account.toString());
			return Account;
		}
		
		} catch (SQLException e){
			LOGGER.error("Account not found", e);
		}
		return null;
	}
	
	public List<Users> getAllUsers(){
		LOGGER.trace("Getting all users");
		try(Connection connection = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM USERS";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			
			List<Users> allUsers = new ArrayList<>();
			
			while(result.next()) {
				
				allUsers.add (new Users (
						result.getLong("U_ID"),
						result.getString("U_NAME"),
						result.getString("U_PASSWORD"),
						result.getLong("U_BALANCE")
						));
			}
			
			return allUsers;
			
		} catch (SQLException e) {
			LOGGER.error("Counld not get all accounts.");
		}
		
		return null;
	}
	
	public boolean createUsers(long iDNum, String userName, String passWord) {
		LOGGER.trace("Creating account: " + userName + ", " + passWord);
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			String sql = "INSERT INTO USERS VALUES (?,?,?,?)";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(++parameterIndex, iDNum);
			statement.setString(++parameterIndex, userName);
			statement.setString(++parameterIndex, passWord);
			statement.setLong(++parameterIndex, 0);
			
			if(statement.executeUpdate() > 0) {
				return true;
			}
			
		} catch (SQLException e) {
			LOGGER.error("Could not create account", e);
		}
		
		return false;
	}
	
	@Override
	public boolean setBalance(String user, long balance) {
		// TODO Auto-generated method stub
		try(Connection connection = ConnectionUtil.getConnection()){
			int parameterIndex = 0;
			LOGGER.trace("Looking for account by the name of: " + user);
			String sql = "UPDATE USERS SET U_BALANCE = ? WHERE U_NAME = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(++parameterIndex, balance);
			statement.setString(++parameterIndex, user);
			
			if(statement.executeUpdate() > 0) {
				return true;
			}
			
		} catch(SQLException e){
			return false;
		}
		
		return false;
	}
	
}
