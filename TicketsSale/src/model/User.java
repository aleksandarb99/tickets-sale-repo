package model;

import java.util.Date;

public abstract class User {
	private String username;
	private String password;
	private String name;
	private String lastName;
	private Gender gender;
	private Date dateOfBirth;
	private boolean isDeleted;
	
	public User() {}
	
	public User(String username, String password, String name, String lastName, Gender gender, Date dateOfBirth, boolean isDeleted) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.isDeleted = isDeleted;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", name=" + name + ", lastName=" + lastName
				+ ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", isDeleted=" + isDeleted + "]";
	}
	
	
	
}
