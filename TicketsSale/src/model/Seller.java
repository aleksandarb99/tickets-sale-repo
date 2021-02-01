package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Seller extends User {
	
	private List<Manifestation> manifestations;
	private boolean isBlocked;
	
	public Seller() {}
	
	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public Seller(String username, String password, String name, String lastName, Gender gender, Date dateOfBirth,  boolean isBlocked, boolean isDeleted) {
		super(username, password, name, lastName, gender, dateOfBirth, isDeleted);
		this.manifestations = new ArrayList<Manifestation>();
		this.isBlocked = isBlocked;
	}

	public List<Manifestation> getManifestations() {
		return manifestations;
	}
	public void addManifestation(Manifestation manifestation) {
		manifestations.add(manifestation);
	}

	public void setManifestations(List<Manifestation> manifestations) {
		this.manifestations = manifestations;
	}
}
