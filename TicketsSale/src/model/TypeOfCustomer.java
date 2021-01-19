package model;

public class TypeOfCustomer {
	private TypesOfCustomers name;
	private double discount;
	private int requiredPoints;
	
	public TypeOfCustomer(TypesOfCustomers name, double discount, int requiredPoints) {
		super();
		this.name = name;
		this.discount = discount;
		this.requiredPoints = requiredPoints;
	}

	public TypesOfCustomers getName() {
		return name;
	}

	public void setName(TypesOfCustomers name) {
		this.name = name;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getRequiredPoints() {
		return requiredPoints;
	}

	public void setRequiredPoints(int requiredPoints) {
		this.requiredPoints = requiredPoints;
	}
}
