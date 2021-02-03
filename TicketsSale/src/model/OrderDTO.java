package model;

public class OrderDTO {

	private int quantity;
	private String type;
	private double price;
	private String manifestation;
	
	public OrderDTO() {}
	
	public OrderDTO(int quantity, String type, double price, String manifestation) {
		super();
		this.quantity = quantity;
		this.type = type;
		this.price = price;
		this.manifestation = manifestation;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getManifestation() {
		return manifestation;
	}

	public void setManifestation(String manifestation) {
		this.manifestation = manifestation;
	}

	@Override
	public String toString() {
		return "OrderDTO [quantity=" + quantity + ", type=" + type + ", price=" + price + ", manifestation="
				+ manifestation + "]";
	}


	
	
	
}
