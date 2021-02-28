package com.revature.dao;

import java.io.IOException;
import java.util.List;

import com.revature.pojo.BarberUser;

public interface BarberUserDao {
	
	/*
	 * End point to Insert a new user record
	 * Parameters: BarberUser object
	 * 
	 */
	public void createUser(BarberUser user);
	
	/*
	 * End point get a user record by username
	 * Parameters: username
	 * returns BarberUser object or null
	 * 
	 */
	public BarberUser getUserByUsername(String username);
	

	/*
	 * End point get all users records
	 * returns a list of BarberUser
	 * 
	 */
	public List<BarberUser> getAllUsers() throws IOException;
	
	public void updateUser(BarberUser user);
	
	public void removeUser(BarberUser user);
}
