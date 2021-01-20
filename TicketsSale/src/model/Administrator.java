package model;

import java.util.Date;

public class Administrator extends User {

	public Administrator(String username, String password, String name, String lastName, Gender gender,
			Date dateOfBirth) {
		super(username, password, name, lastName, gender, dateOfBirth);
	}
}
