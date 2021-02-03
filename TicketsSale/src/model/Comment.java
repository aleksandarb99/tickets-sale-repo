package model;

public class Comment {
	private int id;
	private Customer customer;
	private Manifestation manifestation;
	private String text;
	private int grade; //from 1 to 5
	private CommentState state;
	
	public Comment() {}
	
	public Comment(int id, Customer customer, Manifestation manifestation, String text, int grade, CommentState state) {
		super();
		this.id = id;
		this.customer = customer;
		this.manifestation = manifestation;
		this.text = text;
		this.grade = grade;
		this.state = state;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public CommentState getState() {
		return state;
	}

	public void setState(CommentState state) {
		this.state = state;
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
