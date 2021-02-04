package model;

public class QueryParams {

	private String name, location, dateFrom, dateUntil, priceFrom, priceUntil;

	public QueryParams() {

	}

	public QueryParams(String name, String location, String dateFrom, String dateUntil, String priceFrom,
			String priceUntil) {
		super();
		this.name = name;
		this.location = location;
		this.dateFrom = dateFrom;
		this.dateUntil = dateUntil;
		this.priceFrom = priceFrom;
		this.priceUntil = priceUntil;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public String getDateUntil() {
		return dateUntil;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public String getPriceFrom() {
		return priceFrom;
	}

	public String getPriceUntil() {
		return priceUntil;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public void setDateUntil(String dateUntil) {
		this.dateUntil = dateUntil;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPriceFrom(String priceFrom) {
		this.priceFrom = priceFrom;
	}

	public void setPriceUntil(String priceUntil) {
		this.priceUntil = priceUntil;
	}

}
