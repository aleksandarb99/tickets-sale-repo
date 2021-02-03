package model;

public class OrderDTO {

	private String manifestation;
	private double price;
	private int quantity;
	private String type;

	public OrderDTO() {
	}

	public OrderDTO(int quantity, String type, double price, String manifestation) {
		super();
		this.quantity = quantity;
		this.type = type;
		this.price = price;
		this.manifestation = manifestation;
	}

	public String getManifestation() {
		return manifestation;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getType() {
		return type;
	}

	public void setManifestation(String manifestation) {
		this.manifestation = manifestation;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setType(String type) {
		this.type = type;
	}

}
