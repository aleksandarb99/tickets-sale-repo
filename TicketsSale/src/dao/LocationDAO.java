package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import model.Location;


public class LocationDAO {
	private Map<Integer, Location> locations = new HashMap<>();
	
	
	public LocationDAO() {}
		
	public LocationDAO(String contextPath) {
		loadData(contextPath);
	}
	
	public Location find(Integer id) {
		return locations.containsKey(id) ? locations.get(id) : null;
	}
	
	public Collection<Location> findAll() {
		return locations.values();
	}
	
	public Location addLocation(Location Location) {
		Integer newId = locations.size() + 1;
		locations.put(newId, Location);
		return Location;
	}
	
	private void loadData(String contextPath) {
		BufferedReader in = null;
		try {
			String separator = System.getProperty("file.separator");
			File file = new File(contextPath + "data" +separator+ "locations.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					Integer id = Integer.parseInt(st.nextToken().trim());
					Double longitude = Double.parseDouble(st.nextToken().trim());
					Double latitude = Double.parseDouble(st.nextToken().trim());
					String address = st.nextToken().trim();
					locations.put(id, 
					new Location(longitude, latitude, address));
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
}
