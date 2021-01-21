package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer extends User {
	private List<Ticket> tickets;
	private double collectedPoints;
	private TypeOfCustomer type;
	
	public Customer() {}
	
	public Customer(String username, String password, String name, String lastName, Gender gender, Date dateOfBirth,
			 double collectedPoints, TypeOfCustomer type) {
		super(username, password, name, lastName, gender, dateOfBirth);
		this.tickets = new ArrayList<Ticket>();
		this.collectedPoints = collectedPoints;
		this.type = type;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public double getCollectedPoints() {
		return collectedPoints;
	}

	public void setCollectedPoints(double collectedPoints) {
		this.collectedPoints = collectedPoints;
	}

	public TypeOfCustomer getType() {
		return type;
	}

	public void setType(TypeOfCustomer type) {
		this.type = type;
	}
}
