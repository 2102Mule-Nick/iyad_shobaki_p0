package com.revature.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.pojo.BarberUser;

class BarberAuthServiceImplCopyTest{

	public static BarberAuthServiceImplCopy service;
	public static BarberUser user1;
	public static BarberUser user2;
	public static BarberUser user3;

	@BeforeEach
	private void setUp() {
		user1 = new BarberUser("Mike", "1234");
		user2 = new BarberUser("Jane", "3456");
		user3 = new BarberUser("John", "5678");
		service = new BarberAuthServiceImplCopy();
	}

	@AfterEach
	private void tearDown() {
		service.deleteAllUsers();
	}
	
	@Test
	public void testRegisterUser() {
		assertNotNull(service.registerUser(user1));
	}
	
	@Test
	public void testRegisterUserIfUserIsNull() {
		assertNull(service.registerUser(null));
	}
	@Test
	void testIsUserExistForExistingUser() {
		service.registerUser(user1);
		assertTrue(service.isUserExist(user1));
	}
	
	@Test
	void testIsUserExistForNotExistingUser() {	
		assertFalse(service.isUserExist(user1));
	}
	
	@Test
	void testGetAllUsers() {
		service.registerUser(user1);
		service.registerUser(user2);
		service.registerUser(user3);
		try {
			assertEquals(3, service.getAllUsers().size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void testAuthenticateUser() {
		service.registerUser(user1);
		assertNotNull(service.authenticateUser(new BarberUser("Mike", "1234")));
	}
	
	@Test
	void testAuthenticateUserInvalidUsername() {
		service.registerUser(user1);
		assertNull(service.authenticateUser(new BarberUser("Woo", "1234")));
	}
	
	@Test
	void testAuthenticateUserInvalidPassword() {
		service.registerUser(user1);
		assertNull(service.authenticateUser(new BarberUser("Mike", "1111")));
	}

}
