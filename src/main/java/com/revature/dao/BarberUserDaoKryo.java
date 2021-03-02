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
import com.revature.pojo.BarberUser;

public class BarberUserDaoKryo implements BarberUserDao {

	private Kryo kryo = new Kryo();
	private Logger log = Logger.getRootLogger();
	private static final String FOLDER_NAME = "users\\";
	private static final String FILE_EXTENSION = ".dat";
	private static final String CLASS_NAME = "BarberUserDaoKryo";

	public BarberUserDaoKryo() {
		super();
		kryo.register(BarberUser.class);
	}

	/*
	 * End point to Insert a new user record Parameters: BarberUser object
	 * 
	 */

	@Override
	public boolean createUser(BarberUser user) {

		// Check if user is null
		if (user == null) {
			return false;
		}

		// Check and create a directory if it is not exists
		File usersDir = new File("users\\");
		if (!usersDir.exists()) {
			usersDir.mkdir();
		}

		// Check and create a file if its not exists.
		// I don't think I need the following code, because FileOutputStream will create
		// a file if not exists When using writeObject method. But just in case
		// --------------------------
		String fileName = FOLDER_NAME + user.getUsername() + FILE_EXTENSION;
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.error(CLASS_NAME + ".createUser() -> " + "Could not create a file with name= " + fileName);
				return false;
			}
		}
		// --------------------------

		log.info(CLASS_NAME + ".createUser() -> " + "An Attempt to create user with username= " + user.getUsername());

		// Add user record to the directory by adding a file
		try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
			Output output = new Output(outputStream);
			kryo.writeObject(output, user);
			output.close();
			log.info(CLASS_NAME + ".createUser() -> " + "User with username= " + user.getUsername()
					+ ", was created successfully!");
			return true;
		} catch (FileNotFoundException e) {
			log.error(CLASS_NAME + ".createUser() -> " + "File= " + fileName + ", was not found!\n" + e.getMessage());
			return false;
		} catch (IOException e) {
			log.error(CLASS_NAME + ".createUser() -> " + "Something went wrong creating a file with name= " + fileName
					+ "\n" + e.getMessage());
			return false;
		}

	}

	/*
	 * End point get a user record by username Parameters: username returns
	 * BarberUser object or null
	 * 
	 */

	@Override
	public BarberUser getUserByUsername(String username) {

		log.info(CLASS_NAME + ".getUserByUsername() -> " + "An Attempt to get a user with username= " + username);

		String fileName = FOLDER_NAME + username + FILE_EXTENSION;

		// -- important
		// Check if a file is not exists. (thats means the user is not exists)
		// because FileInputStream will not create
		// a file if its not exists when using readObject method.
		File file = new File(fileName);
		if (!file.exists()) {
			log.info(CLASS_NAME + ".getUserByUsername() -> " + "User with username= " + username + " is not exist");
			return null;
		}

		// Create a user object using FileInputStream and return it.
		try (FileInputStream inputStream = new FileInputStream(fileName)) {
			Input input = new Input(inputStream);
			BarberUser user = kryo.readObject(input, BarberUser.class);
			input.close();
			log.info(CLASS_NAME + ".getUserByUsername() -> " + "User with username= " + username
					+ ", was returned successfully!");
			return user;

		} catch (FileNotFoundException e) {

			// We can return null here if we don't want to check if the file exists at the
			// beginning
			log.error(CLASS_NAME + ".getUserByUsername() -> " + "File " + fileName + ", was not found!\n"
					+ e.getMessage());
		} catch (IOException e) {
			log.error(CLASS_NAME + ".getUserByUsername() -> " + "Something went wrong opening a file with name= "
					+ fileName + "\n" + e.getMessage());
		}

		return null;
	}

	/*
	 * End point get all users records returns a list of BarberUser
	 * 
	 */
	@Override
	public List<BarberUser> getAllUsers() throws IOException {

		log.info(CLASS_NAME + ".getAllUsers() -> " + "An Attempt to get all users");

		List<BarberUser> users = new ArrayList<>();

		// Get all files inside the directory
		String userDir = System.getProperty("user.dir");
		userDir = userDir + "/" + FOLDER_NAME;

		List<File> files = Files.list(Paths.get(userDir)).filter(Files::isRegularFile).map(Path::toFile)
				.collect(Collectors.toList());
		
		// Loop through all files to added to the list of users
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
				BarberUser user = kryo.readObject(input, BarberUser.class);
				input.close();
				users.add(user);

			} catch (FileNotFoundException e) {
				log.error(CLASS_NAME + ".getAllUsers() -> " + "File= " + fileName + ", was not found!\n"
						+ e.getMessage());
			} catch (IOException e) {
				log.error(CLASS_NAME + ".getAllUsers() -> " + "Something went wrong opening a file with name= "
						+ fileName + "\n" + e.getMessage());
			}
		}
		log.info(CLASS_NAME + ".getAllUsers() -> " + "A list of users returned successfully!");
		return users;
	}

	@Override
	public void updateUser(BarberUser user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeUser(BarberUser user) {
		// TODO Auto-generated method stub

	}

}
