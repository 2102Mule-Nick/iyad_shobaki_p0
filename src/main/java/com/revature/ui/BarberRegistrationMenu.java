package com.revature.ui;

import java.util.Scanner;

import com.revature.pojo.BarberUser;
import com.revature.service.BarberAuthSerivce;

public class BarberRegistrationMenu implements BarberMenu {

	private BarberUser user;
	private BarberAuthSerivce barberAuthSerivce;
	private BarberMenu barberLoginMenu;
	private BarberMenu barberNextMenu;

	// Constructor
	public BarberRegistrationMenu(BarberUser user, BarberAuthSerivce barberAuthSerivce, BarberMenu barberLoginMenu) {
		super();
		this.user = user;
		this.barberAuthSerivce = barberAuthSerivce;
		this.barberLoginMenu = barberLoginMenu;
	}

	/*
	 * Direct the user to the next menu
	 */
	@Override
	public BarberMenu advance() {
		return barberNextMenu;
	}

	/*
	 * Display options to the user Get the user input (username and password) and
	 * try to register as new user Ask the user if he/she wants to proceed with the
	 * registration process or go back to the login menu
	 */
	@Override
	public void displayOptions(Scanner scanner) {

		System.out.println("Please Enter Username");
		/// String username = scanner.nextLine();
		this.user.setUsername(scanner.nextLine());
		System.out.println("Please Enter Password");
		// String password = scanner.nextLine();
		this.user.setPassword(scanner.nextLine());

		// this.user = new BarberUser(username, password);

		if (!barberAuthSerivce.isUserExist(this.user)) {
			System.out.println("reach here1");
			if (barberAuthSerivce.registerUser(this.user) != null) {
				System.out.println("reach here2");
				barberNextMenu = barberLoginMenu;
			} else {
				this.user.setUsername("");
				this.user.setUsername("");
				System.out.println(
						"Something went wrong! Please press Enter to try again or 'login' to go to the login menu");
				String answer = scanner.nextLine();

				if ("login".equals(answer)) {
					barberNextMenu = barberLoginMenu;
				} else {
					barberNextMenu = this;
				}
			}
		} else {
			this.user.setUsername("");
			this.user.setUsername("");
			System.out.println("Username taken, please press Enter to try again or 'login' to go to the login menu");
			String answer = scanner.nextLine();

			if ("login".equals(answer)) {
				barberNextMenu = barberLoginMenu;
			} else {
				barberNextMenu = this;
			}
		}

	}

	@Override
	public BarberMenu previousMenu() {
		return null;
	}

}
