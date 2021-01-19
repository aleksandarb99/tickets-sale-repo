package model;

public class Address {
	private String streetAndNumber;
	private String place;
	private String postNumber;
	
	public Address(String streetAndNumber, String place, String postNumber) {
		super();
		this.streetAndNumber = streetAndNumber;
		this.place = place;
		this.postNumber = postNumber;
	}

	public String getStreetAndNumber() {
		return streetAndNumber;
	}

	public void setStreetAndNumber(String streetAndNumber) {
		this.streetAndNumber = streetAndNumber;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPostNumber() {
		return postNumber;
	}

	public void setPostNumber(String postNumber) {
		this.postNumber = postNumber;
	}
}
