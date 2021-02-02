package model;

import java.util.Date;

public class Administrator extends User {
	
	public Administrator() {}

	public Administrator(String username, String password, String name, String lastName, Gender gender,
			Date dateOfBirth, boolean isDeleted) {
		super(username, password, name, lastName, gender, dateOfBirth, isDeleted);
	}
}
