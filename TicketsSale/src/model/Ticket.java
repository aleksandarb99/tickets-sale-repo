package model;

import java.util.Date;

public class Ticket {

	private String id; //10 characters
	private Manifestation reservedManifestation;
	private Date date;
	private double price;
	private String nameLastName;
	private TicketState state;
	private TypeOfTicket type;
	
	public Ticket(String id, Manifestation reservedManifestation, Date date, double price, String nameLastName,
			TicketState state, TypeOfTicket type) {
		super();
		this.id = id;
		this.reservedManifestation = reservedManifestation;
		this.date = date;
		this.price = price;
		this.nameLastName = nameLastName;
		this.state = state;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Manifestation getReservedManifestation() {
		return reservedManifestation;
	}

	public void setReservedManifestation(Manifestation reservedManifestation) {
		this.reservedManifestation = reservedManifestation;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getNameLastName() {
		return nameLastName;
	}

	public void setNameLastName(String nameLastName) {
		this.nameLastName = nameLastName;
	}

	public TicketState getState() {
		return state;
	}

	public void setState(TicketState state) {
		this.state = state;
	}

	public TypeOfTicket getType() {
		return type;
	}

	public void setType(TypeOfTicket type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", reservedManifestation=" + reservedManifestation + ", date=" + date + ", price="
				+ price + ", nameLastName=" + nameLastName + ", state=" + state + ", type=" + type + "]";
	}
	
	
}
