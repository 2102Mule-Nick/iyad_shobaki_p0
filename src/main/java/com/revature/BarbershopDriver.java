package com.revature;

import java.io.IOException;
import java.util.Scanner;

import com.revature.dao.BarberApptDao;
import com.revature.dao.BarberApptDaoKryo;
import com.revature.dao.BarberUserDao;
import com.revature.dao.BarberUserDaoKryo;
import com.revature.pojo.BarberUser;
import com.revature.service.BarberApptService;
import com.revature.service.BarberApptServiceImpl;
import com.revature.service.BarberAuthSerivce;
import com.revature.service.BarberAuthServiceImpl;
import com.revature.ui.BarberBookAppointmentMenu;
import com.revature.ui.BarberLoginMenu;
import com.revature.ui.BarberManagerReportMenu;
import com.revature.ui.BarberMenu;
import com.revature.ui.BarberRegistrationMenu;
import com.revature.ui.BarberWelcomeMenu;

public class BarbershopDriver {

	public static Scanner scanner = new Scanner(System.in);
	public static BarberUser user = new BarberUser();

	public static void main(String[] args) throws IOException {
				
		
		BarberUserDao barberUserDao = new BarberUserDaoKryo();
		BarberAuthSerivce barberAuthSerivce = new BarberAuthServiceImpl(barberUserDao);

		BarberApptDao barberApptDao = new BarberApptDaoKryo();
		BarberApptService barberApptService = new BarberApptServiceImpl(barberApptDao);
		BarberMenu barberBookAppMenu = new BarberBookAppointmentMenu(user, barberApptService);
		
		BarberMenu barberManagerRepoMenu = new BarberManagerReportMenu(user, barberApptService, barberAuthSerivce);
		BarberMenu barberLoginMenu = new BarberLoginMenu(user,barberAuthSerivce, barberBookAppMenu, barberManagerRepoMenu);
		BarberMenu barberRegistrationMenu = new BarberRegistrationMenu(user,barberAuthSerivce, barberLoginMenu);

		BarberMenu barberWelcomeMenu = new BarberWelcomeMenu(barberLoginMenu, barberRegistrationMenu);

		BarberMenu barberNextMenu = barberWelcomeMenu;

		do {
			
			barberNextMenu.displayOptions(scanner);

			barberNextMenu = barberNextMenu.advance();

		} while (barberNextMenu != null);

	}

}
