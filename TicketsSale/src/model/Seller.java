package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Seller extends User {
	
	private List<Manifestation> manifestations;
	
	public Seller() {}

	public Seller(String username, String password, String name, String lastName, Gender gender, Date dateOfBirth) {
		super(username, password, name, lastName, gender, dateOfBirth);
		this.manifestations = new ArrayList<Manifestation>();
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
