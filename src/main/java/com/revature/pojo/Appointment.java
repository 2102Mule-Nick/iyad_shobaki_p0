package com.revature.pojo;

public class Appointment {

	private String appointmentDate;
	private String appointmentTime;
	private String username;

	// Default constructor
	public Appointment() {
		super();
	}

	// Constructor
	public Appointment(String appointmentDate, String appointmentTime, String username) {
		super();
		setAppointmentDate(appointmentDate);
		setAppointmentTime(appointmentTime);
		setUsername(username);
	}

	// Setters and getters
	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	// Override methods
	@Override
	public String toString() {
		return "Appointment [username=" + getUsername() + ", Appointment Date=" + getAppointmentDate()
				+ ", Appointment Time=" + getAppointmentTime() + "]";
	}

}
