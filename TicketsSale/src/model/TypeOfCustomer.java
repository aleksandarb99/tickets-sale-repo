package model;

public class TypeOfCustomer {
	private TypesOfCustomers name;
	private double discount;
	private int requiredPoints;
	
	public TypeOfCustomer(TypesOfCustomers name) {
		super();
		this.name = name;
		if(name.equals(TypesOfCustomers.BRONZE)) {
			this.discount = 0;
			this.requiredPoints = 3000;
		}
		else if(name.equals(TypesOfCustomers.SILVER)) {
			this.discount = 3;
			this.requiredPoints = 4000;
		} else {
			this.discount = 5;
		}
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

	@Override
	public String toString() {
		return "TypeOfCustomer [name=" + name + ", discount=" + discount + ", requiredPoints=" + requiredPoints + "]";
	}
	
	
}
