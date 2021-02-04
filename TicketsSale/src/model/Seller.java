package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Seller extends User {

	private boolean isBlocked;
	private List<Manifestation> manifestations;

	public Seller() {
	}

	public Seller(String username, String password, String name, String lastName, Gender gender, Date dateOfBirth,
			boolean isBlocked, boolean isDeleted) {
		super(username, password, name, lastName, gender, dateOfBirth, isDeleted);
		this.manifestations = new ArrayList<Manifestation>();
		this.isBlocked = isBlocked;
	}

	public void addManifestation(Manifestation manifestation) {
		manifestations.add(manifestation);
	}

	public List<Manifestation> getManifestations() {
		return manifestations;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public void setManifestations(List<Manifestation> manifestations) {
		this.manifestations = manifestations;
	}
}
