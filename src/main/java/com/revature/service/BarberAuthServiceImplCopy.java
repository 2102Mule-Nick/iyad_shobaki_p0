package com.revature.service;

import java.io.IOException;
import java.util.List;


import com.revature.dao.BarberUserDaoKryoCopy;
import com.revature.pojo.BarberUser;

public class BarberAuthServiceImplCopy implements BarberAuthSerivce{

	private BarberUserDaoKryoCopy barberUserDao = new BarberUserDaoKryoCopy();

//	// Constructor
//	public BarberAuthServiceImplCopy(BarberUserDao barberUserDao) {
//		super();
//		this.barberUserDao = barberUserDao;
//	}

	/*
	 * Sends a user object to the data access layer to check if the user exist
	 * Returns true if exist and false if not.
	 */
	@Override
	public boolean isUserExist(BarberUser user) {

		if (barberUserDao.getUserByUsername(user.getUsername()) != null) {
			return true;
		}
		return false;
	}

	/*
	 * Sends a user object to the data access layer to check if the user exist and
	 * the user is an administrator Returns the user object if the user exists and
	 * null if not.
	 */
	@Override
	public BarberUser authenticateAdmin(BarberUser user) {

		BarberUser existingUser = barberUserDao.getUserByUsername(user.getUsername());

		if (existingUser != null && existingUser.getPassword().equals(user.getPassword())
				&& existingUser.getRole().equals("admin")) {

			return existingUser;
		}

		return null;
	}

	/*
	 * Sends a user object to the data access layer to check if the user exist
	 * Returns the user object if the user exists and null if not.
	 */
	@Override
	public BarberUser authenticateUser(BarberUser user) {

		BarberUser existingUser = barberUserDao.getUserByUsername(user.getUsername());

		if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
			return existingUser;
		}

		return null;
	}

	/*
	 * Sends a user object to the data access layer to create a user Returns back
	 * the same object
	 */
	@Override
	public BarberUser registerUser(BarberUser user) {

		if (barberUserDao.createUser(user)) {

			return user;
		}
		return null;
	}

	/*
	 * Connects with the data access layer, gets and returns a list of users
	 */

	@Override
	public List<BarberUser> getAllUsers() throws IOException {
		List<BarberUser> users = barberUserDao.getAllUsers();
		return users;
	}
	
	public void deleteAllUsers() {
		barberUserDao.deleteAllUsers();
	}
}
