package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer extends User {
	private double collectedPoints;
	private boolean isBlocked;
	private List<Ticket> tickets;
	private TypeOfCustomer type;

	public Customer() {
	}

	public Customer(String username, String password, String name, String lastName, Gender gender, Date dateOfBirth,
			double collectedPoints, TypeOfCustomer type, boolean isBlocked, boolean isDeleted) {
		super(username, password, name, lastName, gender, dateOfBirth, isDeleted);
		this.tickets = new ArrayList<Ticket>();
		this.collectedPoints = collectedPoints;
		this.type = type;
		this.isBlocked = isBlocked;
	}

	public double getCollectedPoints() {
		return collectedPoints;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public TypeOfCustomer getType() {
		return type;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public void setCollectedPoints(double collectedPoints) {
		this.collectedPoints = collectedPoints;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public void setType(TypeOfCustomer type) {
		this.type = type;
	}
}
