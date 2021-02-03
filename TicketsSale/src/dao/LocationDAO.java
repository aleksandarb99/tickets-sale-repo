package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import model.Location;

public class LocationDAO {
	private Map<Integer, Location> locations = new HashMap<>();

	public LocationDAO() {
	}

	public LocationDAO(String contextPath) {
		loadData(contextPath);
	}

	public Location addLocation(Location location) {
		Integer newId = locations.size() + 1;
		location.setId(newId);
		locations.put(newId, location);
		return location;
	}

	public Location checkLocation(Location location) {
		for (Location l : locations.values()) {
			if (l.getAddress().equals(location.getAddress()))
				return l;
		}
		return null;
	}

	public Location find(Integer id) {
		return locations.containsKey(id) ? locations.get(id) : null;
	}

	public Collection<Location> findAll() {
		return locations.values();
	}

	private void loadData(String contextPath) {
		BufferedReader in = null;
		try {
			String separator = System.getProperty("file.separator");
			File file = new File(contextPath + "data" + separator + "locations.txt");
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
					locations.put(id, new Location(id, longitude, latitude, address));
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public void removeLocation(int id) {
		locations.remove(id);
	}

	public void replaceLocation(Location location) {
		locations.remove(location.getId());
		locations.put(location.getId(), location);
	}

	public void saveData(String contextPath) {
		StringBuilder builder = new StringBuilder();
		for (Location l : locations.values()) {
			builder.append(l.getId() + ";");
			builder.append(l.getLongitude() + ";");
			builder.append(l.getLatitude() + ";");
			builder.append(l.getAddress() + "\n");
		}
		try {
			String separator = System.getProperty("file.separator");
			File file = new File(contextPath + "data" + separator + "locations.txt");
			PrintWriter myWriter = new PrintWriter(file);
			myWriter.write(builder.toString());
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateLocation(Location newL, Location old) {
		Location l = find(old.getId());
		l.setAddress(newL.getAddress());
		l.setLongitude(newL.getLongitude());
		l.setLatitude(newL.getLatitude());
	}
}
