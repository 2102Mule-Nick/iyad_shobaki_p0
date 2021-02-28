package com.revature.service;

import java.io.IOException;
import java.util.List;

import com.revature.pojo.BarberUser;

public interface BarberAuthSerivce {
	
	/*
	 * Sends a user object to the data access layer to check if the user exist
	 * Returns true if exist and false if not.
	 */
	public boolean isUserExist(BarberUser user);
	
	/*
	 * Sends a user object to the data access layer to check if the user exist
	 * Returns true if exist and false if not.
	 */
	public BarberUser authenticateUser(BarberUser user);
	
	/*
	 * Sends a user object to the data access layer to check if the user exist
	 * and the user is an administrator
	 * Returns the user object if the user exists and null if not.
	 */
	public BarberUser authenticateAdmin(BarberUser user);
	
	/*
	 * Send a user object to the data access layer
	 * and returns back the same object
	 */
	public BarberUser registerUser(BarberUser user);
	
	/* 
	 * Get a list of appointments from the data access layer
	 */
	public List<BarberUser> getAllUsers() throws IOException;
}
