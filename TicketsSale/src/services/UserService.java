package services;


import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Customer;
import model.Ticket;
import model.TypeOfCustomer;
import model.TypesOfCustomers;
import model.User;
import dao.LocationDAO;
import dao.ManifestationDAO;
import dao.TicketDAO;
import dao.UserDAO;

@Path("/users")
public class UserService {

	@Context
	ServletContext ctx;
	
	public UserService() {
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
		}
		if (ctx.getAttribute("ManifestationDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	manifestationDAO = new ManifestationDAO(contextPath, locationDAO);
			ctx.setAttribute("ManifestationDAO", manifestationDAO);
		}
		if (ctx.getAttribute("TicketDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	ticketDAO = new TicketDAO(contextPath, manifestationDAO);
			ctx.setAttribute("TicketDAO", ticketDAO);
		}
		if (ctx.getAttribute("UserDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("UserDAO", new UserDAO(contextPath, ticketDAO, manifestationDAO));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getUsers() {
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/customer/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Customer getCustomer(Customer customer) {
		UserDAO dao = (UserDAO) ctx.getAttribute("UserDAO");
		if(dao.find(customer.getUsername()) != null) {
			return null;
		}
		customer.setTickets(new ArrayList<Ticket>());
		customer.setType(new TypeOfCustomer(TypesOfCustomers.BRONZE, 0, 500));
		Customer addedCustomer = (Customer)dao.addUser(customer);
		dao.saveData(ctx.getRealPath(""));
		return addedCustomer;
	}
	
    /*@GET
    @Path("/sortiraj")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Student> getSortiranePacijente(@QueryParam("sorter")String sorter) {
        StudentDAO pd = (StudentDAO) ctx.getAttribute("StudentDAO");
        
        if(sorter.equals("opadajuce")) {
        	return pd.findAll().stream().sorted(Comparator.comparingInt(Student::getBodovi).reversed())
                    .collect(Collectors.toList());
        }
        
        if(sorter.equals("rastuce")) {
        	return pd.findAll().stream().sorted(Comparator.comparingInt(Student::getBodovi))
                    .collect(Collectors.toList());
        }

        return null;
    }*/
}
