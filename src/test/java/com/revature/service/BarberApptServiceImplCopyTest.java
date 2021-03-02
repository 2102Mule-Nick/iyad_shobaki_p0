package com.revature.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.pojo.Appointment;

class BarberApptServiceImplCopyTest {

	public static BarberApptServiceImplCopy service;
	public static Appointment appointment1;
	public static Appointment appointment2;
	public static Appointment appointment3;

	@BeforeEach
	private void setUpService() {
		appointment1 = new Appointment("02-01-2021", "5PM", "Mike");
		appointment2 = new Appointment("02-02-2021", "6PM", "John");
		appointment3 = new Appointment("02-03-2021", "7PM", "Lee");
		service = new BarberApptServiceImplCopy();
	}

	@AfterEach
	private void tearDown() {
		service.deleteAllAppointments();
	}

	@Test
	void testCreateAppointment() {
		assertNotNull(service.createNewAppointment(appointment1));
	}

	@Test
	void testCreateAppointmentWithNullValue() {
		assertNull(service.createNewAppointment(null));
	}

	@Test
	void testGetAllAppointments() {
		service.createNewAppointment(appointment1);
		service.createNewAppointment(appointment2);
		service.createNewAppointment(appointment3);
		try {
			assertEquals(3, service.getAllAppointments().size());
		} catch (IOException e) {

		}

	}

	@Test
	void testGetAllUserAppointmentsForExisitingUser() {
		service.createNewAppointment(appointment1);
		service.createNewAppointment(appointment2);
		service.createNewAppointment(appointment3);
		try {
			assertEquals(1, service.getAllUserAppointments("Mike").size());
		} catch (IOException e) {

		}

	}

	@Test
	void testGetAllUserAppointmentsForNonExistingUser() {
		service.createNewAppointment(appointment1);
		service.createNewAppointment(appointment2);
		service.createNewAppointment(appointment3);
		try {
			assertEquals(0, service.getAllUserAppointments("Jack").size());
		} catch (IOException e) {

		}

	}


}
