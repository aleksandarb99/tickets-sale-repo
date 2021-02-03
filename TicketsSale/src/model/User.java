package model;

import java.util.Date;

public abstract class User {
	private Date dateOfBirth;
	private Gender gender;
	private boolean isDeleted;
	private String lastName;
	private String name;
	private String password;
	private String username;

	public User() {
	}

	public User(String username, String password, String name, String lastName, Gender gender, Date dateOfBirth,
			boolean isDeleted) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.isDeleted = isDeleted;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public String getLastName() {
		return lastName;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
