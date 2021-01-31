package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Comment;
import dao.CommentDAO;
import dao.LocationDAO;
import dao.ManifestationDAO;
import dao.TicketDAO;
import dao.UserDAO;

@Path("/comments")
public class CommentService {
	
	@Context
	ServletContext ctx;
	public CommentService() {
	}
	
	@PostConstruct
	public void init() {
		LocationDAO locationDAO = null;
		TicketDAO ticketDAO = null;
		ManifestationDAO manifestationDAO = null;
		UserDAO userDAO = null;
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
	    	ticketDAO = new TicketDAO(contextPath, manifestationDAO);
			ctx.setAttribute("TicketDAO", ticketDAO);
		} else {
			ticketDAO = (TicketDAO) ctx.getAttribute("TicketDAO");
		}
		if (ctx.getAttribute("UserDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	userDAO = new UserDAO(contextPath, ticketDAO, manifestationDAO);
			ctx.setAttribute("UserDAO", userDAO);
		} else {
			userDAO = (UserDAO) ctx.getAttribute("UserDAO");
		}
		if (ctx.getAttribute("CommentDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("CommentDAO", new CommentDAO(contextPath, manifestationDAO, userDAO));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> getComments() {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("CommentDAO");
		return dao.findAll();
	}
}
