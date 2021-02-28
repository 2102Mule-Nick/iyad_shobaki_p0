package com.revature.ui;

import java.util.Scanner;

import com.revature.pojo.Appointment;
import com.revature.pojo.BarberUser;
import com.revature.service.BarberApptService;

public class BarberBookAppointmentMenu implements BarberMenu {

	private BarberUser user;
	private BarberApptService barberApptService;
	private BarberMenu barberNextMenu;

	// Constructor
	public BarberBookAppointmentMenu(BarberUser user, BarberApptService barberApptService) {
		super();
		this.user = user;
		this.barberApptService = barberApptService;
	}

	/*
	 * Direct the user to the next menu
	 */
	@Override
	public BarberMenu advance() {
		return barberNextMenu;
	}

	/*
	 * Display options to the user 
	 * Ask the user if he/she wants to book an appointment
	 *
	 */
	@Override
	public void displayOptions(Scanner scanner) {
		System.out.println(" ------- Welcome " + this.user.getUsername() + ", To The Book Appointment Page  --------");
		printMenu();

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
				System.out.println("Please Enter A Date");
				String appointmentDate = scanner.nextLine();

				System.out.println("Please Enter A Time");
				String appointmentTime = scanner.nextLine();
				Appointment appointment = new Appointment(appointmentDate, appointmentTime, this.user.getUsername());

				if(barberApptService.createNewAppointment(appointment) != null) {
					
					System.out.println("An appointment has been scheduled for you on " + "Date: " + appointmentDate
							+ " at: " + appointmentTime);
				}else {
					System.out.println("Something went wrong! Please try again.");
				}

				break;
			case 9:
				printMenu();
				break;
			}
		}
	}

	/*
	 * Display the menu options
	 */
	public static void printMenu() {
		System.out.println("Available actions:\npress");
		System.out.println("0 - to quit\n" + "1 - Book an appointment\n" + "9 - Print available actions");

	}

	@Override
	public BarberMenu previousMenu() {
		return null;
	}

}
