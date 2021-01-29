package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

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
	
	public Collection<Manifestation> findAllInactive() {
		Collection<Manifestation> collection = manifestations.values().stream()
				.filter(m -> m.getState().equals(ManifestationState.INACTIVE)).collect(Collectors.toList());
		return collection;
	}
	
	public boolean disableManifestation(Manifestation manifestation) {
		Manifestation updatingManifestation = find(manifestation.getName());
		if(updatingManifestation.getState().equals(ManifestationState.INACTIVE)) {
			updatingManifestation.setState(ManifestationState.ACTIVE);
			return true;
		}
		return false;
	}
	
	public boolean checkDateAndLocation(Manifestation manifestation) {
		
		if(manifestation.getDate().before(new Date())) return false;
		
		for(Manifestation m: manifestations.values()) {
			if(m.getDate().equals(manifestation.getDate()) && 
			m.getLocation().getAddress().equals(manifestation.getLocation().getAddress())) {
				return false;
			}
		}
		return true;
	}
	
	public Manifestation updateManifestation(String oldName, Manifestation manifestation, LocationDAO dao) {
		Manifestation updatingManifestation = manifestations.get(oldName);
		manifestations.remove(oldName);
		updatingManifestation.setName(manifestation.getName());
		updatingManifestation.setNumberOfSeats(manifestation.getNumberOfSeats());
		updatingManifestation.setPriceOfRegularTicket(manifestation.getPriceOfRegularTicket());
		updatingManifestation.setUrl(manifestation.getUrl());
		updatingManifestation.setDate(manifestation.getDate());
		updatingManifestation.setType(manifestation.getType());
		dao.updateLocation(manifestation.getLocation(), updatingManifestation.getLocation());
		updatingManifestation.setLocation(manifestation.getLocation());
		manifestations.put(updatingManifestation.getName(), updatingManifestation);
		
		return updatingManifestation;
	}
	
	public Collection<Manifestation> findRecent() {
		Collection<Manifestation> all = manifestations.values().stream().sorted(Comparator.comparingLong(Manifestation::getDateLong))
                .collect(Collectors.toList());
		
		List<Manifestation> recent = new ArrayList<Manifestation>();
		
		int counter = 0;
		for (Manifestation manifestation : all) {
			recent.add(manifestation);
			if(++counter == 3) break;
		}
		
		return recent;
	}
	
	public Manifestation addManifestation(Manifestation manifestation) {
		manifestations.put(manifestation.getName(), manifestation);
		return manifestation;
	}
	
	private void loadData(String contextPath, LocationDAO locationDAO) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
		BufferedReader in = null;
		boolean flag = false;
		try {
			String separator = System.getProperty("file.separator");
			File file = new File(contextPath + "data" +separator+ "manifestations.txt");
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
					if(state.equals(ManifestationState.ACTIVE)) {
						if(date.before(new Date())) {
							state = ManifestationState.INACTIVE;
							flag = true;
						}
					}
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
		if(flag) saveData(contextPath);
	}
	
	public void saveData(String contextPath) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
		StringBuilder builder = new StringBuilder();
		for(Manifestation m : manifestations.values()) {
			builder.append(m.getName() + ";");
			builder.append(m.getType() + ";");
			builder.append(m.getNumberOfSeats() + ";");
			builder.append(sdf.format(m.getDate()) + ";");
			builder.append(m.getPriceOfRegularTicket() + ";");
			builder.append(m.getState() + ";");
			builder.append(m.getLocation().getId() + ";");
			builder.append(m.getUrl() + "\n");
		}
		try {
			String separator = System.getProperty("file.separator");
			File file = new File(contextPath + "data" +separator+ "manifestations.txt");
			PrintWriter myWriter = new PrintWriter(file);
			myWriter.write(builder.toString());
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "ManifestationDAO [manifestations=" + manifestations + "]";
	}
}
