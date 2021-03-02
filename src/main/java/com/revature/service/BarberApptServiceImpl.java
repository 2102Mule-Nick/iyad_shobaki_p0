package com.revature.service;

import java.io.IOException;
import java.util.List;

import com.revature.dao.BarberApptDao;
import com.revature.pojo.Appointment;

public class BarberApptServiceImpl implements BarberApptService {

	private BarberApptDao barberApptDao;

	// Constructor
	public BarberApptServiceImpl(BarberApptDao barberApptDao) {
		super();
		this.barberApptDao = barberApptDao;
	}

	/*
	 * Sends an Appointment object to the data access layer to create an appointment
	 * Returns back the same object or null.
	 */

	@Override
	public Appointment createNewAppointment(Appointment appointment) {

		if (barberApptDao.createAppointment(appointment)) {
			return appointment;
		}

		return null;
	}

	/*
	 * Connects with the data access layer, gets and returns a list of appointments
	 */
	@Override
	public List<Appointment> getAllAppointments() throws IOException {
		List<Appointment> appointments = barberApptDao.getAllAppointments();
		return appointments;
	}

	/*
	 * Connects with the data access layer, gets and returns a list of appointments
	 * for a specific user
	 */
	@Override
	public List<Appointment> getAllUserAppointments(String username) throws IOException {
		List<Appointment> appointments = barberApptDao.getAllUserAppointments(username);
		return appointments;
	}

}
