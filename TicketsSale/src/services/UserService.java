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

import dao.LocationDAO;
import dao.ManifestationDAO;
import dao.TicketDAO;
import dao.UserDAO;
import model.Administrator;
import model.Customer;
import model.Manifestation;
import model.Seller;
import model.Ticket;
import model.TicketState;
import model.TypeOfCustomer;
import model.TypesOfCustomers;
import model.User;

@Path("/users")
public class UserService {

	@Context
	ServletContext ctx;

	public UserService() {
	}

	@POST
	@Path("/customer/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Customer addCustomer(@Context HttpServletRequest request, Customer customer) {
		if (request.getSession().getAttribute("user") == null) {
			return null;
		}
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		if (dao.find(customer.getUsername()) != null) {
			return null;
		}
		customer.setTickets(new ArrayList<Ticket>());
		customer.setType(new TypeOfCustomer(TypesOfCustomers.BRONZE));
		Customer addedCustomer = (Customer) dao.addUser(customer);
		dao.saveData(ctx.getRealPath(""));
		return addedCustomer;
	}

	@POST
	@Path("/addPoints/")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public void addPoints(@Context HttpServletRequest request, String dto) {
		double numberOfPoints = Double.parseDouble(dto);
		if (request.getSession().getAttribute("user") == null) {
			return;
		}
		Customer loggedUser = (Customer) request.getSession().getAttribute("user");

		loggedUser.setCollectedPoints(loggedUser.getCollectedPoints() + numberOfPoints);

		if (loggedUser.getType().getName().equals(TypesOfCustomers.GOLD)) {
			return;
		}
		// Proveri da li treba da se prebaci status kupca
		if (loggedUser.getType().getRequiredPoints() < loggedUser.getCollectedPoints()) {
			changeCustomerStatusOfUser(loggedUser);
		}

	}

	@POST
	@Path("/seller/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Seller addSeller(@Context HttpServletRequest request, Seller seller) {
		if (request.getSession().getAttribute("user") == null) {
			return null;
		}
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		if (dao.find(seller.getUsername()) != null) {
			return null;
		}
		seller.setManifestations(new ArrayList<Manifestation>());
		Seller addedSeller = (Seller) dao.addUser(seller);
		dao.saveData(ctx.getRealPath(""));
		return addedSeller;
	}

	@POST
	@Path("/block/")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public void blockUser(@Context HttpServletRequest request, String username) {
		if (request.getSession().getAttribute("user") == null) {
			return;
		}
		UserDAO dao2 = (UserDAO) ctx.getAttribute("UserDAO");

		try {
			Administrator a = (Administrator) request.getSession().getAttribute("user");
		} catch (Exception e) {
			return;
		}

		try {
			Customer user = (Customer) dao2.find(username);
			user.setBlocked(true);
		} catch (Exception e) {
		}
		try {
			Seller user = (Seller) dao2.find(username);
			user.setBlocked(true);
		} catch (Exception e) {
		}
		dao2.saveData(ctx.getRealPath(""));
	}

	private void changeCustomerStatusOfUser(Customer user) {
		if (user.getType().getName().equals(TypesOfCustomers.BRONZE)) {
			user.getType().setDiscount(3);
			user.getType().setRequiredPoints(4000);
			user.getType().setName(TypesOfCustomers.SILVER);
			;
		}
		if (user.getType().getName().equals(TypesOfCustomers.SILVER)) {
			user.getType().setDiscount(5);
			user.getType().setRequiredPoints(Integer.MAX_VALUE);
			user.getType().setName(TypesOfCustomers.GOLD);
			;
		}
		System.out.println(user.getType());
	}

	@POST
	@Path("/delete/")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public void delUser(@Context HttpServletRequest request, String username) {
		if (request.getSession().getAttribute("user") == null) {
			return;
		}
		UserDAO dao2 = (UserDAO) ctx.getAttribute("UserDAO");

		User user = dao2.find(username);
		user.setDeleted(true);

		dao2.saveData(ctx.getRealPath(""));
	}

	@GET
	@Path("/administrators/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Administrator> getAdmins() {
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		ArrayList<Administrator> list = new ArrayList<Administrator>();
		for (User user : dao.findAll()) {
			try {
				Administrator admin = (Administrator) user;
				list.add(admin);
			} catch (Exception e) {

			}
		}
		return list;
	}

	@GET
	@Path("/customers/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getCustomers() {
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		ArrayList<Customer> list = new ArrayList<Customer>();
		for (User user : dao.findAll()) {
			try {
				Customer customer = (Customer) user;
				list.add(customer);
			} catch (Exception e) {

			}
		}
		return list;
	}

	@GET
	@Path("/sellers/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Seller> getSellers() {
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		ArrayList<Seller> list = new ArrayList<Seller>();
		for (User user : dao.findAll()) {
			try {
				Seller seller = (Seller) user;
				list.add(seller);
			} catch (Exception e) {

			}
		}
		return list;
	}

	@GET
	@Path("/susUsers/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getSusUsers() {
		Collection<Customer> users = getCustomers();
		Collection<Customer> retVal = new ArrayList<Customer>();

		for (Customer customer : users) {
			int counter = 0;
			for (Ticket ticket : customer.getTickets()) {
				Date current = new Date();
				if (ticket.getState().equals(TicketState.CANCELED)) {
					long diff = current.getTime() - ticket.getDate().getTime();
					int days = (int) (diff / 1000 / 60 / 60 / 24);

					if (days <= 30) {
						counter++;
					}
				}
			}

			if (counter > 5) {
				retVal.add(customer);
			}
		}

		return retVal;
	}

	@POST
	@Path("/logIn/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public User getUser(@Context HttpServletRequest request, String data) {
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		String[] params = data.split("\\?");
		User loggedUser = dao.find(params[0]);
		if (loggedUser == null)
			return null;
		if (!loggedUser.getPassword().equals(params[1]))
			return null;

		// Ako je kupac ili prodavac proveri da li je blokiran
		try {
			Customer c = (Customer) loggedUser;
			if (c.isBlocked()) {
				return null;
			}
		} catch (Exception e) {

		}
		try {
			Seller s = (Seller) loggedUser;
			if (s.isBlocked()) {
				return null;
			}

		} catch (Exception e) {
		}

		if (loggedUser.isDeleted()) {
			return null;
		}

		request.getSession().setAttribute("user", loggedUser);
		return loggedUser;
	}

	@PostConstruct
	public void init() {
		LocationDAO locationDAO = null;
		TicketDAO ticketDAO = null;
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
			ticketDAO = new TicketDAO(contextPath, manifestationDAO);
			ctx.setAttribute("TicketDAO", ticketDAO);
		} else {
			ticketDAO = (TicketDAO) ctx.getAttribute("TicketDAO");
		}
		if (ctx.getAttribute("UserDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("UserDAO", new UserDAO(contextPath, ticketDAO, manifestationDAO));
		}
	}

	@GET
	@Path("/logout/")
	public void logout(@Context HttpServletRequest request) {
		request.getSession().setAttribute("user", null);
	}

	@POST
	@Path("/updateUser/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User updateUser(@Context HttpServletRequest request, Customer user) {
		if (request.getSession().getAttribute("user") == null) {
			return null;
		}
		User loggedUser = (User) request.getSession().getAttribute("user");
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		if (dao.find(user.getUsername()) != null && user.getUsername().equals(loggedUser.getUsername())) {
			User retUser = dao.updateUser(loggedUser.getUsername(), user);
			dao.saveData(ctx.getRealPath(""));
			return retUser;
		}
		if (dao.find(user.getUsername()) == null) {
			User retUser = dao.updateUser(loggedUser.getUsername(), user);
			dao.saveData(ctx.getRealPath(""));
			return retUser;
		}

		return null;
	}
}
