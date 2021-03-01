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
import com.revature.pojo.BarberUser;

public class BarberUserDaoKryoCopy extends BarberUserDaoKryo {

	private Kryo kryo = new Kryo();
	private static final String FOLDER_NAME = "usersTest\\";
	private static final String FILE_EXTENSION = ".dat";

	public BarberUserDaoKryoCopy() {
		super();
		kryo.register(BarberUser.class);
	}

	/*
	 * End point to Insert a new user record Parameters: BarberUser object
	 * 
	 */

	@Override
	public boolean createUser(BarberUser user) {

		if (user == null) {
			return false;
		}
		File usersDir = new File("usersTest\\");
		if (!usersDir.exists()) {
			usersDir.mkdir();
		}
		String fileName = FOLDER_NAME + user.getUsername() + FILE_EXTENSION;
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
			kryo.writeObject(output, user);
			output.close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
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

		String fileName = FOLDER_NAME + username + FILE_EXTENSION;
		File file = new File(fileName);
		if (!file.exists()) {
			return null;
		}
		try (FileInputStream inputStream = new FileInputStream(fileName)) {
			Input input = new Input(inputStream);
			BarberUser user = kryo.readObject(input, BarberUser.class);
			input.close();
			return user;

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		
		return null;
	}

	/*
	 * End point get all users records returns a list of BarberUser
	 * 
	 */
	@Override
	public List<BarberUser> getAllUsers() throws IOException {

		List<BarberUser> users = new ArrayList<>();

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
				BarberUser user = kryo.readObject(input, BarberUser.class);
				input.close();
				users.add(user);

			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
		}
		return users;
	}
	/*
	 * End point to delete all users records (In testing Folder only)
	 * 
	 */
	public void deleteAllUsers() {
		File usersDir = new File("usersTest\\");
		for(File subFile : usersDir.listFiles()) {
			subFile.delete();
		}
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
