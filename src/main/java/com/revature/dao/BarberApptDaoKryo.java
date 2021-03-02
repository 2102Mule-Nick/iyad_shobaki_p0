package com.revature.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.revature.pojo.Appointment;

public class BarberApptDaoKryo implements BarberApptDao {

	private Kryo kryo = new Kryo();
	private Logger log = Logger.getRootLogger();
	private static final String FOLDER_NAME = "appointments\\";
	private static final String FILE_EXTENSION = ".dat";
	private static final String CLASS_NAME = "BarberApptDaoKryo";

	public BarberApptDaoKryo() {
		super();
		kryo.register(Appointment.class);
	}

	/*
	 * End point to Insert a new appointment record Parameters: Appointment object
	 * 
	 */

	@Override
	public boolean createAppointment(Appointment appointment) {

		// Check if appointment is null
		if (appointment == null) {
			return false;
		}

		// Check and create a directory if it is not exists
		File usersDir = new File("appointments\\");
		if (!usersDir.exists()) {
			usersDir.mkdir();
		}

		String fileName = FOLDER_NAME + appointment.getUsername() + appointment.getAppointmentDate()
				+ appointment.getAppointmentTime() + FILE_EXTENSION;

		// Check and create a file if its not exists.
		// I don't think I need the following code, because FileOutputStream will create
		// a file if not exists when using writeObject method. But just in case
		// --------------------------
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.error(CLASS_NAME + ".createAppointment() -> " + "Could not create a file with name= " + fileName);
				return false;
			}
		}
		// --------------------------
		
		log.info(CLASS_NAME + ".createAppointment() -> " + "An Attempt to create an appoinement for username= "
				+ appointment.getUsername());

		// Add appointment record to the directory by adding a file
		try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
			Output output = new Output(outputStream);
			kryo.writeObject(output, appointment);
			output.close();
			log.info(CLASS_NAME + ".createAppointment() -> " + "Appointment for username= " + appointment.getUsername()
					+ ", was created successfully!");
			return true;
		} catch (FileNotFoundException e) {
			log.error(CLASS_NAME + ".createAppointment() -> " + "File= " + fileName + ", was not found!\n"
					+ e.getMessage());
			return false;
		} catch (IOException e) {
			log.error(CLASS_NAME + ".createAppointment() -> " + "Something went wrong creating a file with name= "
					+ fileName + "\n" + e.getMessage());
			return false;
		}
	}

	/*
	 * End point get all appointments records returns a list of appointments
	 * 
	 */

	@Override
	public List<Appointment> getAllAppointments() throws IOException {

		log.info(CLASS_NAME + ".getAllAppointments() -> " + "An Attempt to get all appointments");

		List<Appointment> appointments = new ArrayList<>();

		// Get all files inside the directory
		String userDir = System.getProperty("user.dir");
		userDir = userDir + "/" + FOLDER_NAME;

		List<File> files = Files.list(Paths.get(userDir)).filter(Files::isRegularFile).map(Path::toFile)
				.collect(Collectors.toList());

		// Loop through all files to added to the list of appointments
		for (int i = 0; i < files.size(); i++) {

			// Take just the file name from the full path
			String filePath = files.get(i).toString();
			Path path = Paths.get(filePath);
			Path fileName = path.getFileName();

			// Usually when we use readObject method we need to check if the file exists
			// first But we don't need it in this case because we get the files names after
			// searching the directory
			try (FileInputStream inputStream = new FileInputStream(FOLDER_NAME + fileName.toString())) {
				Input input = new Input(inputStream);
				Appointment appointment = kryo.readObject(input, Appointment.class);
				input.close();
				appointments.add(appointment);

			} catch (FileNotFoundException e) {
				log.error(CLASS_NAME + ".getAllAppointments() -> " + "File= " + fileName + ", was not found!\n"
						+ e.getMessage());
			} catch (IOException e) {
				log.error(CLASS_NAME + ".getAllAppointments() -> " + "Something went wrong opening a file with name= "
						+ fileName + "\n" + e.getMessage());
			}
		}

		log.info(CLASS_NAME + ".getAllAppoinment() -> " + "A list of appointments returned successfully!");
		return appointments;
	}

	/*
	 * End point get all appointments records for a specific user returns a list of
	 * appointments
	 * 
	 */

	@Override
	public List<Appointment> getAllUserAppointments(String username) throws IOException {

		log.info(CLASS_NAME + ".getAllUserAppointments() -> " + "An Attempt to get all user appointments");

		List<Appointment> appointments = new ArrayList<>();

		// Get all files inside the directory
		String userDir = System.getProperty("user.dir");
		userDir = userDir + "/" + FOLDER_NAME;

		List<File> files = Files.list(Paths.get(userDir)).filter(Files::isRegularFile).map(Path::toFile)
				.collect(Collectors.toList());

		// Loop through all files to added to the list of appointments if
		// appointment.getUsername() equal to username
		for (int i = 0; i < files.size(); i++) {

			// Take just the file name from the full path
			String filePath = files.get(i).toString();
			Path path = Paths.get(filePath);
			Path fileName = path.getFileName();

			// Usually When we use readObject method we need to check if the file exists
			// first But we don't need it in this case because we get the files names after
			// searching the directory
			try (FileInputStream inputStream = new FileInputStream(FOLDER_NAME + fileName.toString())) {
				Input input = new Input(inputStream);
				Appointment appointment = kryo.readObject(input, Appointment.class);
				input.close();

				if (appointment.getUsername().equalsIgnoreCase(username)) {
					System.out.println(appointment);
					appointments.add(appointment);
				}

			} catch (FileNotFoundException e) {
				log.error(CLASS_NAME + ".getAllUserAppointments() -> " + "File= " + fileName + ", was not found!\n"
						+ e.getMessage());
			} catch (IOException e) {
				log.error(CLASS_NAME + ".getAllUserAppointments() -> "
						+ "Something went wrong opening a file with name= " + fileName + "\n" + e.getMessage());
			}
		}

		log.info(CLASS_NAME + ".getAllUserAppoinment() -> " + "A list of appointments for username= " + username
				+ " returned successfully!");

		return appointments;
	}

}
