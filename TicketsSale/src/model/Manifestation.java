package model;

import java.util.Date;

public class Manifestation {
	private Date date;
	private boolean isDeleted;
	private Location location;
	private String name;
	private int numberOfSeats;
	private double priceOfRegularTicket;
	private ManifestationState state;
	private TypeOfManifestation type;
	private String url;

	public Manifestation() {
	}

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

	public Date getDate() {
		return date;
	}

	public long getDateLong() {
		return date.getTime();
	}

	public Location getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public double getPriceOfRegularTicket() {
		return priceOfRegularTicket;
	}

	public ManifestationState getState() {
		return state;
	}

	public TypeOfManifestation getType() {
		return type;
	}

	public String getUrl() {
		return url;
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

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public void setPriceOfRegularTicket(double priceOfRegularTicket) {
		this.priceOfRegularTicket = priceOfRegularTicket;
	}

	public void setState(ManifestationState state) {
		this.state = state;
	}

	public void setType(TypeOfManifestation type) {
		this.type = type;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
