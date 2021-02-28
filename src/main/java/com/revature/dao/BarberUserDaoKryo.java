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
	 * End point to Insert a new user record
	 * Parameters: BarberUser object
	 * 
	 */
	
	@Override
	public void createUser(BarberUser user) {
		File usersDir = new File("users\\");
		if (!usersDir.exists()) {
			usersDir.mkdir();
		}
		String fileName = FOLDER_NAME + user.getUsername() + FILE_EXTENSION;
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.error(CLASS_NAME + ".createUser() -> " + "Could not create a file with name= " + fileName);
				// e.printStackTrace();
			}
		}
		// System.out.println("createUser() ->" + user);
		log.info(CLASS_NAME + ".createUser() -> " + "An Attempt to create user with username= " + user.getUsername());

		try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
			Output output = new Output(outputStream);
			kryo.writeObject(output, user);
			output.close();
			log.info(CLASS_NAME + ".createUser() -> " + "User with username= " + user.getUsername()
					+ ", was created successfully!");
		} catch (FileNotFoundException e) {
			log.error(CLASS_NAME + ".createUser() -> " + "File= " + fileName + ", was not found!\n" + e.getMessage());
			// System.out.println("File not found!");
		} catch (IOException e) {
			// e.printStackTrace();
			log.error(CLASS_NAME + ".createUser() -> " + "Something went wrong creating a file with name= " + fileName
					+ "\n" + e.getMessage());
			// System.out.println("IOException!");
		}

	}
	
	/*
	 * End point get a user record by username
	 * Parameters: username
	 * returns BarberUser object or null
	 * 
	 */

	@Override
	public BarberUser getUserByUsername(String username) {

		log.info(CLASS_NAME + ".getUserByUsername() -> " + "An Attempt to get a user with username= " + username);
		String fileName = FOLDER_NAME + username + FILE_EXTENSION;
		File file = new File(fileName);
		if (!file.exists()) {
			log.info(CLASS_NAME + ".getUserByUsername() -> " + "User with username= " + username
					+ " is not exist");
			return null;
		}
		try (FileInputStream inputStream = new FileInputStream(fileName)) {
			Input input = new Input(inputStream);
			BarberUser user = kryo.readObject(input, BarberUser.class);
			input.close();
			log.info(CLASS_NAME + ".getUserByUsername() -> " + "User with username= " + username
					+ ", was returned successfully!");
			return user;
			
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			log.error(CLASS_NAME + ".getUserByUsername() -> " + "File " + fileName + ", was not found!\n"
					+ e.getMessage());
			// System.out.println("File not found!");
		} catch (IOException e) {
			// e.printStackTrace();
			// System.out.println("IOException!");
			log.error(CLASS_NAME + ".getUserByUsername() -> " + "Something went wrong opening a file with name= "
					+ fileName + "\n" + e.getMessage());
		}

		return null;
	}

	/*
	 * End point get all users records
	 * returns a list of BarberUser
	 * 
	 */
	@Override
	public List<BarberUser> getAllUsers() throws IOException {
		
		log.info(CLASS_NAME + ".getAllUsers() -> " + "An Attempt to get all users");
		List<BarberUser> users = new ArrayList<>();

		String userDir = System.getProperty("user.dir");
		userDir = userDir + "/" + FOLDER_NAME;
		// System.out.println(userDir);
		List<File> files = Files.list(Paths.get(userDir)).filter(Files::isRegularFile).map(Path::toFile)
				.collect(Collectors.toList());

		// System.out.println(files.size());
		for (int i = 0; i < files.size(); i++) {
			String filePath = files.get(i).toString();
			Path path = Paths.get(filePath);
			Path fileName = path.getFileName();
			// System.out.println(fileName.toString());
			try (FileInputStream inputStream = new FileInputStream(FOLDER_NAME + fileName.toString())) {
				Input input = new Input(inputStream);
				BarberUser user = kryo.readObject(input, BarberUser.class);
				// System.out.println(user);
				input.close();
				users.add(user);

			} catch (FileNotFoundException e) {
				// e.printStackTrace();
				// System.out.println("File not found!");
				log.error(CLASS_NAME + ".getAllUsers() -> " + "File= " + fileName + ", was not found!\n"
						+ e.getMessage());
			} catch (IOException e) {
				// e.printStackTrace();
				// System.out.println("IOException!");
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
