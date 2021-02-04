package services;

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

import dao.LocationDAO;
import dao.ManifestationDAO;
import dao.TicketDAO;
import dao.UserDAO;
import model.Customer;
import model.Manifestation;
import model.OrderDTO;
import model.Ticket;
import model.TicketState;
import model.TypeOfTicket;
import model.User;

@Path("/tickets")
public class TicketService {

	@Context
	ServletContext ctx;

	public TicketService() {
	}

	@POST
	@Path("/cancelTicket/")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public void cancelTicket(@Context HttpServletRequest request, String id) {
		if (request.getSession().getAttribute("user") == null) {
			return;
		}
		Customer c = (Customer) request.getSession().getAttribute("user");

		TicketDAO dao = (TicketDAO) ctx.getAttribute("TicketDAO");
		Ticket t = dao.find(id);

		UserDAO dao2 = (UserDAO) ctx.getAttribute("UserDAO");

		if (t != null) {
			t.setState(TicketState.CANCELED);
			c.setCollectedPoints(c.getCollectedPoints() - t.getPrice() / 1000 * 133 * 4);

			dao2.saveData(ctx.getRealPath(""));
			dao.saveData(ctx.getRealPath(""));
		}
	}

	@POST
	@Path("/delete/")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public void delUser(@Context HttpServletRequest request, String id) {
		if (request.getSession().getAttribute("user") == null) {
			return;
		}
		TicketDAO dao2 = (TicketDAO) ctx.getAttribute("TicketDAO");

		Ticket t = dao2.find(id);
		t.setDeleted(true);

		dao2.saveData(ctx.getRealPath(""));
	}

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Ticket> findTickectOfManifestation(@Context HttpServletRequest request,
			Manifestation manifestation) {
		if (request.getSession().getAttribute("user") == null) {
			return null;
		}
		TicketDAO dao = (TicketDAO) ctx.getAttribute("TicketDAO");

		return dao.findTickets(manifestation);
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ticket> getTickets() {
		TicketDAO dao = (TicketDAO) ctx.getAttribute("TicketDAO");
		return dao.findAll();
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
			ctx.setAttribute("TicketDAO", new TicketDAO(contextPath, manifestationDAO));
		}
	}

	@POST
	@Path("/order/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User orderTickets(@Context HttpServletRequest request, OrderDTO dto) {
		if (request.getSession().getAttribute("user") == null) {
			return null;
		}

		TypeOfTicket totOfTicket;
		if (dto.getType().equals("1")) {
			totOfTicket = TypeOfTicket.REGULAR;
		} else if (dto.getType().equals("2")) {
			totOfTicket = TypeOfTicket.VIP;
		} else {
			totOfTicket = TypeOfTicket.FAN_PIT;
		}

		TicketDAO dao = (TicketDAO) ctx.getAttribute("TicketDAO");
		UserDAO daouser = (UserDAO) ctx.getAttribute("UserDAO");
		ManifestationDAO dao2 = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");

		Customer loggedUser = (Customer) request.getSession().getAttribute("user");

		for (int i = 0; i < dto.getQuantity(); i++) {
			Ticket t = new Ticket(dao.getNextId(), dao2.find(dto.getManifestation()), new Date(),
					dto.getPrice() / dto.getQuantity(), loggedUser.getName() + " " + loggedUser.getLastName(),
					TicketState.RESERVED, totOfTicket, false);

			dao.addTicket(t);
			loggedUser.getTickets().add(t);
		}

		Manifestation m = dao2.find(dto.getManifestation());
		m.setNumberOfSeats(m.getNumberOfSeats() - dto.getQuantity());

		dao2.saveData(ctx.getRealPath(""));
		daouser.saveData(ctx.getRealPath(""));
		dao.saveData(ctx.getRealPath(""));

		return loggedUser;
	}
}
