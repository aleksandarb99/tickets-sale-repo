package model;

public class CommentParams {
	private int grade;
	private String manifestationName;
	private String text;

	public CommentParams() {
	}

	public CommentParams(String text, String manifestationName, int grade) {
		super();
		this.text = text;
		this.manifestationName = manifestationName;
		this.grade = grade;
	}

	public int getGrade() {
		return grade;
	}

	public String getManifestationName() {
		return manifestationName;
	}

	public String getText() {
		return text;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public void setManifestationName(String manifestationName) {
		this.manifestationName = manifestationName;
	}

	public void setText(String text) {
		this.text = text;
	}
}
