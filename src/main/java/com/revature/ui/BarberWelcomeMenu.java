package com.revature.ui;

import java.util.Scanner;

public class BarberWelcomeMenu implements BarberMenu {

	private BarberMenu barberLoginMenu;
	private BarberMenu barberRegistrationMenu;
	private BarberMenu barberNextMenu;

	// Constructor
	public BarberWelcomeMenu(BarberMenu barberLoginMenu, BarberMenu barberLegistrationMenu) {
		super();
		this.barberLoginMenu = barberLoginMenu;
		this.barberRegistrationMenu = barberLegistrationMenu;
	}

	/*
	 * Direct the user to the next menu
	 */
	@Override
	public BarberMenu advance() {
		return barberNextMenu;
	}

	/*
	 * Display options to the user Ask the user if he/she wants to create an account
	 * or login as an admin or a customer
	 */
	@Override
	public void displayOptions(Scanner scanner) {

		System.out.println("------------ Welcome To The Barbershop Application ------------");
		System.out.println("Would you like to login or register?");
		String answer = scanner.nextLine();

		if ("login".equals(answer)) {
			barberNextMenu = barberLoginMenu;
		} else if ("register".equals(answer)) {
			barberNextMenu = barberRegistrationMenu;
		} else {
			System.out.println("invalid input");
			barberNextMenu = this;
		}

	}

	@Override
	public BarberMenu previousMenu() {
		return null;
	}

}
