package services;

import java.util.Collection;

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

import model.Manifestation;
import model.Ticket;
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
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Ticket> addCustomer(@Context HttpServletRequest request, Manifestation manifestation) {
		if(request.getSession().getAttribute("user") == null) {			//Provera da li je korisnik vec ulogovan
			return null;
		}
		TicketDAO dao = (TicketDAO) ctx.getAttribute("TicketDAO");

		return dao.findTickets(manifestation);
	}
}
