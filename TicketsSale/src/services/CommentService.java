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

import dao.CommentDAO;
import dao.LocationDAO;
import dao.ManifestationDAO;
import dao.TicketDAO;
import dao.UserDAO;
import model.Comment;
import model.CommentParams;
import model.Customer;
import model.Manifestation;

@Path("/comments")
public class CommentService {

	@Context
	ServletContext ctx;

	public CommentService() {
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addComment(@Context HttpServletRequest request, CommentParams cp) {
		if (request.getSession().getAttribute("user") == null) {
			return;
		}

		Customer loggedUser = (Customer) request.getSession().getAttribute("user");
		CommentDAO dao = (CommentDAO) ctx.getAttribute("CommentDAO");
		ManifestationDAO manDao = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		Manifestation manifestation = manDao.find(cp.getManifestationName());

		dao.createComment(cp, manifestation, loggedUser);
		dao.saveData(ctx.getRealPath(""));
	}

	@POST
	@Path("/allowCommenting/")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Comment allowCommenting(@Context HttpServletRequest request, String manifestationName) {
		if (request.getSession().getAttribute("user") == null) {
			return null;
		}

		ManifestationDAO manDao = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		if (manDao.checkDate(manifestationName))
			return null; // Vraca true ako nisu ispunjeni uslovi

		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		Customer loggedUser = (Customer) request.getSession().getAttribute("user");
		if (dao.checkTicket(manifestationName, loggedUser))
			return null; // Vraca true ako nije ispunjen uslov

		return new Comment();
	}

	@POST
	@Path("/approve/")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Comment approveComment(@Context HttpServletRequest request, String commentId) {
		if (request.getSession().getAttribute("user") == null) {
			return null;
		}
		CommentDAO dao = (CommentDAO) ctx.getAttribute("CommentDAO");
		boolean retVal = dao.approveComment(commentId);

		if (retVal) {
			dao.saveData(ctx.getRealPath(""));
			return new Comment();
		}

		return null;
	}

	@POST
	@Path("/active/")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> getActiveComments(@Context HttpServletRequest request, String manifestationName) {
		if (request.getSession().getAttribute("user") == null) {
			return null;
		}
		CommentDAO dao = (CommentDAO) ctx.getAttribute("CommentDAO");
		return dao.findAllActive(manifestationName);
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> getComments(@Context HttpServletRequest request) {
		if (request.getSession().getAttribute("user") == null) {
			return null;
		}
		CommentDAO dao = (CommentDAO) ctx.getAttribute("CommentDAO");
		return dao.findAll();
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

	@POST
	@Path("/reject/")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Comment rejectComment(@Context HttpServletRequest request, String commentId) {
		if (request.getSession().getAttribute("user") == null) {
			return null;
		}
		CommentDAO dao = (CommentDAO) ctx.getAttribute("CommentDAO");
		boolean retVal = dao.rejectComment(commentId);

		if (retVal) {
			dao.saveData(ctx.getRealPath(""));
			return new Comment();
		}

		return null;
	}
}
