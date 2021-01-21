package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import model.Manifestation;
import model.ManifestationState;
import model.TypeOfManifestation;
import model.Location;


public class ManifestationDAO {
	private Map<String, Manifestation> manifestations = new HashMap<>();	
	
	public ManifestationDAO() {}
		
	public ManifestationDAO(String contextPath, LocationDAO locationDAO) {
		loadData(contextPath, locationDAO);
	}
	
	public Manifestation find(String name) {
		return manifestations.containsKey(name) ? manifestations.get(name) : null;
	}
	
	public Collection<Manifestation> findAll() {
		return manifestations.values();
	}
	
	public Manifestation addManifestation(Manifestation manifestation) {
		manifestations.put(manifestation.getName(), manifestation);
		return manifestation;
	}
	
	private void loadData(String contextPath, LocationDAO locationDAO) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/data/manifestations.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					String name = st.nextToken().trim();
					TypeOfManifestation type = TypeOfManifestation.valueOf(st.nextToken().trim());
					Integer numberOfSeats = Integer.parseInt(st.nextToken().trim());
					Date date = sdf.parse(st.nextToken().trim());
					Double priceOfRegularTickets = Double.parseDouble(st.nextToken().trim());
					ManifestationState state = ManifestationState.valueOf(st.nextToken().trim());
					Location location = locationDAO.find(Integer.parseInt(st.nextToken().trim()));
					String url = st.nextToken().trim();
					manifestations.put(name, 
					new Manifestation(name, type, numberOfSeats, date, priceOfRegularTickets, state, location, url));
				}		
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}

	@Override
	public String toString() {
		return "ManifestationDAO [manifestations=" + manifestations + "]";
	}
	
}
