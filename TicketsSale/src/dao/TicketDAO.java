package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
	
	public void updateTicket(Manifestation oldManifestation, Manifestation newManifestation) {
		for(Ticket t: tickets.values()) {
			if(t.getReservedManifestation().getName().equals(oldManifestation.getName())) {
				t.setReservedManifestation(newManifestation);
			}
		}
	}
	
	private Double calculatePrice(Double regular, TypeOfTicket type) {
		if(type.equals(TypeOfTicket.VIP)) return regular * 4;
		if(type.equals(TypeOfTicket.FAN_PIT)) return regular * 2;
		return regular;
	}
	
	private void loadData(String contextPath, ManifestationDAO dao) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
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
					String nameLastName = st.nextToken().trim();
					TicketState state = TicketState.valueOf(st.nextToken().trim());
					TypeOfTicket type = TypeOfTicket.valueOf(st.nextToken().trim());
					Date date = sdf.parse(st.nextToken().trim());
					boolean deleted = Boolean.parseBoolean(st.nextToken().trim());
					Manifestation manifestation = dao.find(manifestationName);
					Double price = calculatePrice(manifestation.getPriceOfRegularTicket(), type);
					tickets.put(id, new Ticket(id, manifestation, date, price, nameLastName, state, type, deleted));
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
	
	public void saveData(String contextPath) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm");
		StringBuilder builder = new StringBuilder();
		for(Ticket t : tickets.values()) {
			builder.append(t.getId() + ";");
			builder.append(t.getReservedManifestation().getName() + ";");
			builder.append(t.getNameLastName() + ";");
			builder.append(t.getState() + ";");
			builder.append(t.getType() + ";");
			builder.append(sdf.format(t.getDate()) + ";");
			builder.append(t.isDeleted());
			builder.append("\n");
		}
		try {
			String separator = System.getProperty("file.separator");
			File file = new File(contextPath + "data" +separator+ "tickets.txt");
			PrintWriter myWriter = new PrintWriter(file);
			myWriter.write(builder.toString());
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getNextId() {
		String size = (tickets.values().size()+1) + "";
		while (true) {
			if(size.length()==10) {
				break;
			}
			size = "0"+size;
		}
		return size;
	}
	
}
