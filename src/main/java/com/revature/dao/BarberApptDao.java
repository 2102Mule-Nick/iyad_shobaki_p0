package com.revature.dao;

import java.io.IOException;
import java.util.List;

import com.revature.pojo.Appointment;

public interface BarberApptDao {

	/*
	 * End point to Insert a new appointment record Parameters: Appointment object
	 * 
	 */
	public boolean createAppointment(Appointment appointment);

	/*
	 * End point to get all appointments records returns a list of appointments
	 * 
	 */
	public List<Appointment> getAllAppointments() throws IOException;

	public List<Appointment> getAllUserAppointments(String username) throws IOException;
}
