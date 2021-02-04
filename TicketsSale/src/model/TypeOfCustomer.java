package model;

public class TypeOfCustomer {
	private double discount;
	private TypesOfCustomers name;
	private int requiredPoints;

	public TypeOfCustomer(TypesOfCustomers name) {
		super();
		this.name = name;
		if (name.equals(TypesOfCustomers.BRONZE)) {
			this.discount = 0;
			this.requiredPoints = 3000;
		} else if (name.equals(TypesOfCustomers.SILVER)) {
			this.discount = 3;
			this.requiredPoints = 4000;
		} else {
			this.discount = 5;
		}
	}

	public double getDiscount() {
		return discount;
	}

	public TypesOfCustomers getName() {
		return name;
	}

	public int getRequiredPoints() {
		return requiredPoints;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public void setName(TypesOfCustomers name) {
		this.name = name;
	}

	public void setRequiredPoints(int requiredPoints) {
		this.requiredPoints = requiredPoints;
	}

}
