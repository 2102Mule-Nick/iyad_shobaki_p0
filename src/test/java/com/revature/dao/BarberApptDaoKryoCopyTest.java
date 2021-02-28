package com.revature.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.pojo.Appointment;

class BarberApptDaoKryoCopyTest {

	public static BarberApptDaoKryoCopy service;
	public static Appointment appointment1;
	public static Appointment appointment2;
	public static Appointment appointment3;

	@BeforeEach
	private void setUp() {
		service = new BarberApptDaoKryoCopy();
	}

	@AfterEach
	private void tearDown() {
		service.deleteAllAppointments();
	}

	@Test
	void testCreateAppointment() {
		appointment1 = new Appointment("02-01-2021", "5:00PM", "Mike");
		assertTrue(service.createAppointment(appointment1));
	}

	@Test
	void testCreateAppointmentWithNullValue() {
		assertFalse(service.createAppointment(null));
	}

	@Test
	void testGetAllAppoinment() {
		appointment1 = new Appointment("02-01-2021", "5PM", "Mike");
		appointment2 = new Appointment("02-02-2021", "6PM", "John");
		appointment3 = new Appointment("02-03-2021", "7PM", "Lee");
		service.createAppointment(appointment1);
		service.createAppointment(appointment2);
		service.createAppointment(appointment3);
		try {
			assertEquals(3, service.getAllAppoinment().size());
		} catch (IOException e) {

		}

	}

}
