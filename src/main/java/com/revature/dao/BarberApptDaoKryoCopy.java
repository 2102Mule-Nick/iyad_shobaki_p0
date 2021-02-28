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


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.revature.pojo.Appointment;

public class BarberApptDaoKryoCopy implements BarberApptDao {

	private Kryo kryo = new Kryo();
	private static final String FOLDER_NAME = "appointmentsTest\\";
	private static final String FILE_EXTENSION = ".dat";

	public BarberApptDaoKryoCopy() {
		super();
		kryo.register(Appointment.class);
	}

	/*
	 * End point to Insert a new appointment record Parameters: Appointment object
	 * 
	 */

	@Override
	public boolean createAppointment(Appointment appointment) {

		if(appointment == null) {
			return false;
		}
		File usersDir = new File("appointmentsTest\\");
		if (!usersDir.exists()) {
			usersDir.mkdir();
		}
		String fileName = FOLDER_NAME + appointment.getUsername() + appointment.getAppointmentDate()
				+ appointment.getAppointmentTime() + FILE_EXTENSION;
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				return false;
			}
		}
		try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
			Output output = new Output(outputStream);
			kryo.writeObject(output, appointment);
			output.close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		
	}

	/*
	 * End point to get all appointments records returns a list of appointments
	 * 
	 */

	@Override
	public List<Appointment> getAllAppoinment() throws IOException {

		List<Appointment> appointments = new ArrayList<>();

		String userDir = System.getProperty("user.dir");
		userDir = userDir + "/" + FOLDER_NAME;
		
		List<File> files = Files.list(Paths.get(userDir)).filter(Files::isRegularFile).map(Path::toFile)
				.collect(Collectors.toList());

		for (int i = 0; i < files.size(); i++) {
			String filePath = files.get(i).toString();
			Path path = Paths.get(filePath);
			Path fileName = path.getFileName();
			try (FileInputStream inputStream = new FileInputStream(FOLDER_NAME + fileName.toString())) {
				Input input = new Input(inputStream);
				Appointment appointment = kryo.readObject(input, Appointment.class);
				input.close();
				appointments.add(appointment);

			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
		}

		return appointments;
	}
	
	/*
	 * End point to delete all appointments records (In testing Folder only)
	 * 
	 */
	public void deleteAllAppointments() {
		File usersDir = new File("appointmentsTest\\");
		for(File subFile : usersDir.listFiles()) {
			subFile.delete();
		}
	}

}

