package com.revature.service;

import java.io.IOException;
import java.util.List;

import com.revature.pojo.Appointment;
import com.revature.pojo.BarberUser;

public interface BarberApptService {

	/*
	 * Sends an Appointment object to the data access layer to create an appointment
	 * Returns back the same object or null.
	 */
	public Appointment createNewAppointment(Appointment appointment);
	
	/* 
	 * Connects with the data access layer, gets and returns a list of appointments
	 */
	public List<Appointment> getAllAppointments() throws IOException;
	
	/*
	 * Connects with the data access layer, gets and returns a list of appointments for a specific user
	 */
	public List<Appointment> getAllUserAppointments(String username) throws IOException;
	
}
