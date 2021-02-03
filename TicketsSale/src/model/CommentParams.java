package model;

public class CommentParams {
	private String text;
	private String manifestationName;
	private int grade;
	
	public CommentParams() {}

	public CommentParams(String text, String manifestationName, int grade) {
		super();
		this.text = text;
		this.manifestationName = manifestationName;
		this.grade = grade;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getManifestationName() {
		return manifestationName;
	}

	public void setManifestationName(String manifestationName) {
		this.manifestationName = manifestationName;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
}
