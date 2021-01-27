package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Customer;
import model.Manifestation;
import model.OrderDTO;
import model.Ticket;
import model.TicketState;
import model.TypeOfTicket;
import model.User;
import dao.LocationDAO;
import dao.ManifestationDAO;
import dao.TicketDAO;

@Path("/tickets")
public class TicketService {
	
	@Context
	ServletContext ctx;
	public TicketService() {
	}
	
	@PostConstruct
	public void init() {
		LocationDAO locationDAO = null;
		ManifestationDAO manifestationDAO = null;
		if (ctx.getAttribute("LocationDAO") == null) {
	    	String contextPath = ctx.getRealPath("");    	
	    	locationDAO = new LocationDAO(contextPath);
			ctx.setAttribute("LocationDAO", locationDAO);
		} else {
			locationDAO = (LocationDAO) ctx.getAttribute("LocationDAO");
		}
		if (ctx.getAttribute("ManifestationDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	manifestationDAO = new ManifestationDAO(contextPath, locationDAO);
			ctx.setAttribute("ManifestationDAO", manifestationDAO);
		} else {
			manifestationDAO = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		}
		if (ctx.getAttribute("TicketDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("TicketDAO",  new TicketDAO(contextPath, manifestationDAO));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ticket> getTickets() {
		TicketDAO dao = (TicketDAO) ctx.getAttribute("TicketDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/order/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User orderTickets(@Context HttpServletRequest request, OrderDTO dto) {
		if(request.getSession().getAttribute("user") == null) {			//Provera da li je korisnik vec ulogovan
			return null;
		}
		
		TypeOfTicket totOfTicket;
		if(dto.getType().equals("1")) {
			totOfTicket = TypeOfTicket.REGULAR;
		} else if (dto.getType().equals("2")) {
			totOfTicket = TypeOfTicket.VIP;
		} else {
			totOfTicket = TypeOfTicket.FAN_PIT;
		}
		
		TicketDAO dao = (TicketDAO) ctx.getAttribute("TicketDAO");
		ManifestationDAO dao2 = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		
		Customer loggedUser = (Customer)request.getSession().getAttribute("user");
		
		// TODO save this
		
		for (int i = 0; i < dto.getQuantity(); i++) {
			Ticket t = new Ticket(dao.getNextId(), dao2.find(dto.getManifestation()), new Date(), dto.getPrice()/dto.getQuantity(), 
					loggedUser.getName()+" "+loggedUser.getLastName(), TicketState.RESERVED, totOfTicket);
			
			dao.addTicket(t);
			loggedUser.getTickets().add(t);
		}
		
		
		return loggedUser;
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Ticket> findTickectOfManifestation(@Context HttpServletRequest request, Manifestation manifestation) {
		if(request.getSession().getAttribute("user") == null) {			//Provera da li je korisnik vec ulogovan
			return null;
		}
		TicketDAO dao = (TicketDAO) ctx.getAttribute("TicketDAO");

		return dao.findTickets(manifestation);
	}
}
