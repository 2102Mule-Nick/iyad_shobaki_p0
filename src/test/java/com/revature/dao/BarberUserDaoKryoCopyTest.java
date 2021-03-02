package com.revature.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.pojo.BarberUser;

class BarberUserDaoKryoCopyTest {

	public static BarberUserDaoKryoCopy service;
	public static BarberUser user1;
	public static BarberUser user2;
	public static BarberUser user3;

	@BeforeEach
	private void setUp() {
		user1 = new BarberUser("Mike", "1234");
		user2 = new BarberUser("Jane", "3456");
		user3 = new BarberUser("John", "5678");
		service = new BarberUserDaoKryoCopy();
	}

	@AfterEach
	private void tearDown() {
		service.deleteAllUsers();
	}

	@Test
	void testCreateUser() {
		assertTrue(service.createUser(user1));
	}

	@Test
	void testCreateUserWithNullValue() {
		assertFalse(service.createUser(null));
	}

	@Test
	void testGetUserByUsernameExist() {
		service.createUser(user1);
		assertNotNull(service.getUserByUsername("Mike"));
	}

	@Test
	void testGetUserByUsernameNotExist() {
		assertNull(service.getUserByUsername("Lee"));
	}

	@Test
	void testGetAllUsers() {
		service.createUser(user1);
		service.createUser(user2);
		service.createUser(user3);

		try {
			assertEquals(3, service.getAllUsers().size());
		} catch (IOException e) {

		}
	}

}
