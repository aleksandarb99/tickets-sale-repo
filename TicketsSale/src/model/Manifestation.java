package model;

import java.awt.Image;
import java.util.Date;

public class Manifestation {
	private String name;
	private TypeOfManifestation type;
	private int numberOfSeats;
	private Date date;
	private double priceOfRegularTicket;
	private ManifestationState state;
	private Location location;
	private Image image;
	
	public Manifestation(String name, TypeOfManifestation type, int numberOfSeats, Date date,
			double priceOfRegularTicket, ManifestationState state, Location location, Image image) {
		super();
		this.name = name;
		this.type = type;
		this.numberOfSeats = numberOfSeats;
		this.date = date;
		this.priceOfRegularTicket = priceOfRegularTicket;
		this.state = state;
		this.location = location;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeOfManifestation getType() {
		return type;
	}

	public void setType(TypeOfManifestation type) {
		this.type = type;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getPriceOfRegularTicket() {
		return priceOfRegularTicket;
	}

	public void setPriceOfRegularTicket(double priceOfRegularTicket) {
		this.priceOfRegularTicket = priceOfRegularTicket;
	}

	public ManifestationState getState() {
		return state;
	}

	public void setState(ManifestationState state) {
		this.state = state;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
