package com.revature.pojo;

public class BarberUser {
	
	private String username;
	
	private String password;
	
	private String role = "customer";
	
	// Default constructor
	public BarberUser() {
		super();
	}
	
    // Constructor
	public BarberUser(String username, String password) {
		super();
		setUsername(username);
		setPassword(password);
	}

    // Setters and getters
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	
	// Override methods
	@Override
	public String toString() {
		return "User [username=" + getUsername() + ", password=" + getPassword() + ", role=" + getRole() + "]";
	}

}
