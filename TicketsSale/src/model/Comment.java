package model;

public class Comment {
	private Customer customer;
	private Manifestation manifestation;
	private String text;
	private int grade; //from 1 to 5
	
	public Comment(Customer customer, Manifestation manifestation, String text, int grade) {
		super();
		this.customer = customer;
		this.manifestation = manifestation;
		this.text = text;
		this.grade = grade;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Manifestation getManifestation() {
		return manifestation;
	}

	public void setManifestation(Manifestation manifestation) {
		this.manifestation = manifestation;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
}
