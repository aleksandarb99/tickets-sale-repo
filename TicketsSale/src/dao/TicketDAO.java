package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import model.Manifestation;
import model.Ticket;
import model.TicketState;
import model.TypeOfTicket;


public class TicketDAO {
	private Map<String, Ticket> tickets = new HashMap<>();	
	
	public TicketDAO() {}
		
	public TicketDAO(String contextPath, ManifestationDAO dao) {
		loadData(contextPath, dao);
	}
	
	public Ticket find(String id) {
		return tickets.containsKey(id) ? tickets.get(id) : null;
	}
	
	public Collection<Ticket> findAll() {
		return tickets.values();
	}
	
	public Collection<Ticket> findTickets(Manifestation manifestation) {
		Collection<Ticket> collection = tickets.values().stream()
				.filter(t -> t.getReservedManifestation().getName().equals(manifestation.getName())).collect(Collectors.toList());
		return collection;
	}
	
	public Ticket addTicket(Ticket ticket) {
		tickets.put(ticket.getId(), ticket);
		return ticket;
	}
	
	private void loadData(String contextPath, ManifestationDAO dao) {
		BufferedReader in = null;
		try {
			String separator = System.getProperty("file.separator");
			File file = new File(contextPath + "data" +separator+ "tickets.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					String id = st.nextToken().trim();
					String manifestationName = st.nextToken().trim();
					Double price = Double.parseDouble(st.nextToken().trim());
					String nameLastName = st.nextToken().trim();
					TicketState state = TicketState.valueOf(st.nextToken().trim());
					TypeOfTicket type = TypeOfTicket.valueOf(st.nextToken().trim());
					Manifestation manifestation = dao.find(manifestationName);
					Date date = manifestation.getDate();
					tickets.put(id, 
					new Ticket(id, manifestation, date, price, nameLastName, state, type));
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
