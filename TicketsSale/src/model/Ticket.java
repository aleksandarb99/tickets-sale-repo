package model;

import java.util.Date;

public class Ticket {

	private Date date;
	private String id; // 10 characters
	private boolean isDeleted;
	private String nameLastName;
	private double price;
	private Manifestation reservedManifestation;
	private TicketState state;
	private TypeOfTicket type;

	public Ticket(String id, Manifestation reservedManifestation, Date date, double price, String nameLastName,
			TicketState state, TypeOfTicket type, boolean isDeleted) {
		super();
		this.id = id;
		this.reservedManifestation = reservedManifestation;
		this.date = date;
		this.price = price;
		this.nameLastName = nameLastName;
		this.state = state;
		this.type = type;
		this.isDeleted = isDeleted;
	}

	public Date getDate() {
		return date;
	}

	public String getId() {
		return id;
	}

	public String getNameLastName() {
		return nameLastName;
	}

	public double getPrice() {
		return price;
	}

	public Manifestation getReservedManifestation() {
		return reservedManifestation;
	}

	public TicketState getState() {
		return state;
	}

	public TypeOfTicket getType() {
		return type;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNameLastName(String nameLastName) {
		this.nameLastName = nameLastName;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setReservedManifestation(Manifestation reservedManifestation) {
		this.reservedManifestation = reservedManifestation;
	}

	public void setState(TicketState state) {
		this.state = state;
	}

	public void setType(TypeOfTicket type) {
		this.type = type;
	}

}
