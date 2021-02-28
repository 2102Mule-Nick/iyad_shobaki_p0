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
		List<Appointment> appointments = barberApptDao.getAllAppoinment();
		return appointments;
	}

}
