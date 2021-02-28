package com.revature.ui;

import java.io.IOException;
import java.util.Scanner;


public interface BarberMenu {
	
	/*
	 * Direct the user to the next menu
	 */
	
	public BarberMenu advance();

	/*
	 * Display options to the user
	 */
	public void displayOptions(Scanner scanner) throws IOException;

	public BarberMenu previousMenu();

}
