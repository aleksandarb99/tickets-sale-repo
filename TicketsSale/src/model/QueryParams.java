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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateUntil() {
		return dateUntil;
	}

	public void setDateUntil(String dateUntil) {
		this.dateUntil = dateUntil;
	}

	public String getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(String priceFrom) {
		this.priceFrom = priceFrom;
	}

	public String getPriceUntil() {
		return priceUntil;
	}

	public void setPriceUntil(String priceUntil) {
		this.priceUntil = priceUntil;
	}

	@Override
	public String toString() {
		return "QueryParams [name=" + name + ", location=" + location + ", dateFrom=" + dateFrom + ", dateUntil="
				+ dateUntil + ", priceFrom=" + priceFrom + ", priceUntil=" + priceUntil + "]";
	}
	

}
