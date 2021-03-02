package com.revature.ui;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.revature.pojo.Appointment;
import com.revature.pojo.BarberUser;
import com.revature.service.BarberApptService;
import com.revature.service.BarberAuthSerivce;

public class BarberManagerReportMenu implements BarberMenu {

	private BarberUser user;
	private BarberApptService barberApptService;
	private BarberAuthSerivce barberAuthSerivce;

	// Constructor
	public BarberManagerReportMenu(BarberUser user, BarberApptService barberApptService,
			BarberAuthSerivce barberAuthSerivce) {
		super();
		this.user = user;
		this.barberApptService = barberApptService;
		this.barberAuthSerivce = barberAuthSerivce;
	}

	/*
	 * Direct the user to the next menu
	 */
	@Override
	public BarberMenu advance() {
		return null;
	}

	/*
	 * Display options to the user Ask the admin if he/she wants to see all
	 * appointments or/and all users
	 *
	 */
	@Override
	public void displayOptions(Scanner scanner) throws IOException {
		System.out.println("Welcome " + this.user.getUsername() + ", To The Manager Report Page");
		printMenu();
		List<Appointment> appointments = null;
		List<BarberUser> users = null;

		boolean quit = false;

		while (!quit) {
			int action = scanner.nextInt();
			scanner.nextLine();

			switch (action) {
			case 0:
				System.out.println("Thank you!");
				quit = true;
				break;
			case 1:
				appointments = barberApptService.getAllAppointments();
				System.out.println("All appointments: ");
				if (appointments.size() < 1) {
					System.out.println("There are no appoinments found");
				}
				for (Appointment appt : appointments) {
					System.out.println(appt);
				}
				break;
			case 2:
				users = barberAuthSerivce.getAllUsers();
				System.out.println("All users: ");
				for (BarberUser u : users) {
					System.out.println(u);
				}
				break;
			case 9:
				printMenu();
				break;

			}

		}

	}

	public static void printMenu() {
		System.out.println("Available actions:\npress");
		System.out.println(
				"0 - to quit\n" + "1 - See all appointments\n" + "2 - See all users\n" + "9 - Print available actions");

	}

	@Override
	public BarberMenu previousMenu() {
		return null;
	}

}
