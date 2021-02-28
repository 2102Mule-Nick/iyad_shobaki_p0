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
	 * End point to Insert a new appointment record
	 * Parameters: Appointment object
	 * 
	 */

	@Override
	public boolean createAppointment(Appointment appointment) {
		
		if(appointment == null) {
			return false;
		}
		File usersDir = new File("appointments\\");
		if(!usersDir.exists()) {
			usersDir.mkdir();
		}
		String fileName = FOLDER_NAME + appointment.getUsername() + appointment.getAppointmentDate()
		+ appointment.getAppointmentTime() + FILE_EXTENSION;
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.error(CLASS_NAME + ".createAppointment() -> " + "Could not create a file with name= " + fileName);
				return false;
			}
		}
		log.info(CLASS_NAME + ".createAppointment() -> " + "An Attempt to create an appoinement for username= " + appointment.getUsername());
		try(FileOutputStream outputStream = new FileOutputStream(fileName)) {
			Output output = new Output(outputStream);
			kryo.writeObject(output, appointment);
			output.close();
			log.info(CLASS_NAME + ".createAppointment() -> " + "Appointment for username= " + appointment.getUsername()
			+ ", was created successfully!");
			return true;
		} catch (FileNotFoundException e) {
			log.error(CLASS_NAME + ".createAppointment() -> " + "File= " + fileName + ", was not found!\n" + e.getMessage());
			return false;
		} catch (IOException e) {
			log.error(CLASS_NAME + ".createAppointment() -> " + "Something went wrong creating a file with name= " + fileName
					+ "\n" + e.getMessage());
			return false;
		}
	}


	/*
	 * End point get all appointments records
	 * returns a list of appointments
	 * 
	 */
	
	@Override
	public List<Appointment> getAllAppoinment() throws IOException {
		
		log.info(CLASS_NAME + ".getAllAppoinment() -> " + "An Attempt to get all appointments");
		List<Appointment> appointments = new ArrayList<>();
		
		String userDir = System.getProperty("user.dir");
		userDir = userDir +"/" +FOLDER_NAME;

		List<File> files = Files.list(Paths.get(userDir))
				.filter(Files::isRegularFile)
				.map(Path::toFile)
				.collect(Collectors.toList());
		
		for(int i = 0; i < files.size(); i++) {
			String filePath = files.get(i).toString();
			Path path = Paths.get(filePath);
			Path fileName = path.getFileName();
			try (FileInputStream inputStream = new FileInputStream(FOLDER_NAME + fileName.toString())) {
				Input input = new Input(inputStream);
				Appointment appointment = kryo.readObject(input, Appointment.class);
				input.close();
				appointments.add(appointment);
				
			} catch (FileNotFoundException e) {
				log.error(CLASS_NAME + ".getAllAppoinment() -> " + "File= " + fileName + ", was not found!\n"
						+ e.getMessage());
			} catch (IOException e) {
				log.error(CLASS_NAME + ".getAllAppoinment() -> " + "Something went wrong opening a file with name= "
						+ fileName + "\n" + e.getMessage());
			}
		}
	
		log.info(CLASS_NAME + ".getAllAppoinment() -> " + "A list of appointments returned successfully!");
		return appointments;
	}

}
