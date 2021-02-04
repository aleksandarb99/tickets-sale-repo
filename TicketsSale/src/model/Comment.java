package model;

public class Comment {
	private Customer customer;
	private int grade; // from 1 to 5
	private int id;
	private Manifestation manifestation;
	private CommentState state;
	private String text;

	public Comment() {
	}

	public Comment(int id, Customer customer, Manifestation manifestation, String text, int grade, CommentState state) {
		super();
		this.id = id;
		this.customer = customer;
		this.manifestation = manifestation;
		this.text = text;
		this.grade = grade;
		this.state = state;
	}

	public Customer getCustomer() {
		return customer;
	}

	public int getGrade() {
		return grade;
	}

	public int getId() {
		return id;
	}

	public Manifestation getManifestation() {
		return manifestation;
	}

	public CommentState getState() {
		return state;
	}

	public String getText() {
		return text;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setManifestation(Manifestation manifestation) {
		this.manifestation = manifestation;
	}

	public void setState(CommentState state) {
		this.state = state;
	}

	public void setText(String text) {
		this.text = text;
	}
}
