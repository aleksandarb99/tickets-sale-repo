package model;

import java.util.Date;

public class Manifestation {
	private String name;
	private TypeOfManifestation type;
	private int numberOfSeats;
	private Date date;
	private double priceOfRegularTicket;
	private ManifestationState state;
	private Location location;
	private String url;
	private boolean isDeleted;
	
	public Manifestation() {}
	
	public Manifestation(String name, TypeOfManifestation type, int numberOfSeats, Date date,
			double priceOfRegularTicket, ManifestationState state, Location location, String url, boolean isDeleted) {
		super();
		this.name = name;
		this.type = type;
		this.numberOfSeats = numberOfSeats;
		this.date = date;
		this.priceOfRegularTicket = priceOfRegularTicket;
		this.state = state;
		this.location = location;
		this.url = url;
		this.isDeleted = isDeleted;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
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
	
	public long getDateLong() {
		return date.getTime();
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
