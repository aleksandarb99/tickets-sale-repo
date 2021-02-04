package model;

public class ManifestationDTO {
	private Manifestation manifestation;
	private String oldName;

	public ManifestationDTO() {
	}

	public ManifestationDTO(Manifestation manifestation, String oldName) {
		super();
		this.manifestation = manifestation;
		this.oldName = oldName;
	}

	public Manifestation getManifestation() {
		return manifestation;
	}

	public String getOldName() {
		return oldName;
	}

	public void setManifestation(Manifestation manifestation) {
		this.manifestation = manifestation;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
}
