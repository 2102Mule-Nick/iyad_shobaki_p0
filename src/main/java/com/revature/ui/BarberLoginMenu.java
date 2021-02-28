package com.revature.ui;

import java.util.Scanner;

import com.revature.pojo.BarberUser;
import com.revature.service.BarberAuthSerivce;

public class BarberLoginMenu implements BarberMenu {

	private BarberUser user;
	private BarberAuthSerivce barberAuthSerivce;
	private BarberMenu barberBookAppointmentMenu;
	private BarberMenu barberManagerReportMenu;
	private BarberMenu barberNextMenu;

	// Constructor
	public BarberLoginMenu(BarberUser user, BarberAuthSerivce barberAuthSerivce, BarberMenu barberBookAppointmentMenu,
			BarberMenu barberManagerReportMenu) {
		super();
		this.user = user;
		this.barberAuthSerivce = barberAuthSerivce;
		this.barberBookAppointmentMenu = barberBookAppointmentMenu;
		this.barberManagerReportMenu = barberManagerReportMenu;
	}

	/*
	 * Direct the user to the next menu
	 */
	@Override
	public BarberMenu advance() {
		return barberNextMenu;
	}

	/*
	 * Display options to the user Ask the user if he/she wants login as an admin
	 * Get the user input (username and password) and try to login
	 */
	@Override
	public void displayOptions(Scanner scanner) {

		if (this.user.getUsername() == null || this.user.getUsername().isEmpty()) {

			System.out.println("Enter 'admin' to login as admin or press enter to continue.");
			String answer = scanner.nextLine();

			System.out.println("Please Enter Username");
			// String username = scanner.nextLine();
			this.user.setUsername(scanner.nextLine());
			System.out.println("Please Enter Password");
			// String password = scanner.nextLine();
			this.user.setPassword(scanner.nextLine());

			// this.user = new BarberUser(username, password);

			if ("admin".equals(answer)) {
				if (barberAuthSerivce.authenticateAdmin(this.user) != null) {
					barberNextMenu = barberManagerReportMenu;
				} else {
					System.out.println("Invalid username or password. Pleas try again!");
					this.user.setUsername("");
					this.user.setUsername("");
					barberNextMenu = this;
				}

			} else {

				if (barberAuthSerivce.authenticateUser(this.user) != null) {
					barberNextMenu = barberBookAppointmentMenu;
				} else {
					System.out.println("Invalid username or password. Pleas try again!");
					this.user.setUsername("");
					this.user.setUsername("");
					barberNextMenu = this;
				}
			}
		} else {
			barberAuthSerivce.authenticateUser(this.user);
			barberNextMenu = barberBookAppointmentMenu;
		}
	}

	@Override
	public BarberMenu previousMenu() {
		return null;
	}

}
