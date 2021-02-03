package services;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Location;
import model.Manifestation;
import model.ManifestationDTO;
import model.ManifestationState;
import model.QueryParams;
import model.Seller;
import model.Ticket;
import dao.LocationDAO;
import dao.ManifestationDAO;
import dao.TicketDAO;
import dao.UserDAO;

@Path("/manifestations")
public class ManifestationService {
	
	@Context
	ServletContext ctx;
	
	public ManifestationService() {
	}
	
	@PostConstruct
	public void init() {
		LocationDAO locationDAO;
		if (ctx.getAttribute("LocationDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	locationDAO = new LocationDAO(contextPath);
			ctx.setAttribute("LocationDAO", locationDAO);
		} else {
			locationDAO = (LocationDAO) ctx.getAttribute("LocationDAO");
		}
		
		if (ctx.getAttribute("ManifestationDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("ManifestationDAO", new ManifestationDAO(contextPath, locationDAO));
		}
	}
	
	@POST
	@Path("/delete/")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public void delUser(@Context HttpServletRequest request, String name) {	
		if(request.getSession().getAttribute("user") == null) {	
			return;
		}
		ManifestationDAO dao2 = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		
		Manifestation t = dao2.find(name);
		t.setDeleted(true);

		dao2.saveData(ctx.getRealPath(""));
	}
	
	@GET
	@Path("/all/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getAllManifestations() {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		return dao.findAll();
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getManifestations() {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		return dao.findAll().stream()
		.filter(t -> t.isDeleted()==false).collect(Collectors.toList());
	}
	
	@GET
	@Path("/inactive")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getInactiveManifestations() {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		return dao.findAllInactive();
	}
	
	@GET
	@Path("/recent/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getRecentManifestations() {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		return dao.findRecent().stream()
				.filter(t -> t.isDeleted()==false).collect(Collectors.toList());
	}
	
	@GET
	@Path("/{name: .+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Manifestation getManifestation(@PathParam("name") String name) {
		
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		Manifestation m = dao.find(name);
		if(m.isDeleted()) {
			return null;
		}
		return dao.find(name);
	}
	
	@POST
	@Path("/add/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Manifestation addManifestation(@Context HttpServletRequest request, Manifestation manifestation) {
		if(request.getSession().getAttribute("user") == null) {			//Provera da li je korisnik vec ulogovan
			return null;
		}
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		if(dao.find(manifestation.getName()) != null) {
			return null;
		}
		if(!dao.checkDateAndLocation(manifestation)) return null;
		
		Seller seller = (Seller)request.getSession().getAttribute("user");
		manifestation.setState(ManifestationState.INACTIVE);
		Manifestation addedManifestation = (Manifestation)dao.addManifestation(manifestation);
		LocationDAO daoLocation = (LocationDAO) ctx.getAttribute("LocationDAO");
		Location location = daoLocation.checkLocation(manifestation.getLocation());
		if(location != null) {
			manifestation.setLocation(location);
		}else {
			manifestation.setLocation(daoLocation.addLocation(manifestation.getLocation()));
		}
		seller.addManifestation(addedManifestation);
		daoLocation.saveData(ctx.getRealPath(""));
		dao.saveData(ctx.getRealPath(""));
		UserDAO daoUser = (UserDAO) ctx.getAttribute("UserDAO");
		daoUser.saveData(ctx.getRealPath(""));
		return addedManifestation;
	}
	
	@POST
	@Path("/approve/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Manifestation approveManifestation(@Context HttpServletRequest request, Manifestation manifestation) {
		if(request.getSession().getAttribute("user") == null) {			//Provera da li je korisnik vec ulogovan
			return null;
		}
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		boolean retVal = dao.disableManifestation(manifestation);
		
		if(retVal) {
			dao.saveData(ctx.getRealPath(""));
			return manifestation;
		}
		
		return null;
	}
	
	@POST
	@Path("/update/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Manifestation updateManifestation(@Context HttpServletRequest request, ManifestationDTO dto) {
		if(request.getSession().getAttribute("user") == null) {			//Provera da li je korisnik vec ulogovan
			return null;
		}
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		LocationDAO daoLocation = (LocationDAO) ctx.getAttribute("LocationDAO");
		
		if(!dao.checkDateAndLocation(dto.getManifestation())) return null;
		
		if(dao.find(dto.getManifestation().getName()) != null && dto.getOldName().equals(dto.getManifestation().getName())) {
			Manifestation retM = dao.updateManifestation(dto.getOldName(), dto.getManifestation(), daoLocation);
			daoLocation.saveData(ctx.getRealPath(""));
			dao.saveData(ctx.getRealPath(""));
			return retM;
		}
		if(dao.find(dto.getManifestation().getName()) == null) {
			Manifestation retM = dao.updateManifestation(dto.getOldName(), dto.getManifestation(), daoLocation);
			daoLocation.saveData(ctx.getRealPath(""));
			dao.saveData(ctx.getRealPath(""));
			return retM;
		}
		
		return null;
		
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getSearchedManifestations(QueryParams params) {
		
		if(!validateParams(params)) {
			return new ArrayList<Manifestation>();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		
		Collection<Manifestation> collection = dao.findAll();
				
		if(!params.getName().equals("")) {
			collection = collection.stream()
				.filter(manifestation -> manifestation.getName().toLowerCase().startsWith(params.getName().toLowerCase())).collect(Collectors.toList());
		}
		
		if(!params.getLocation().equals("")) {
			collection = collection.stream()
				.filter(manifestation -> manifestation.getLocation().getAddress().toLowerCase().contains(params.getLocation().toLowerCase())).collect(Collectors.toList());
		}
		if(!params.getPriceFrom().equals("")) {
			collection = collection.stream()
				.filter(manifestation -> manifestation.getPriceOfRegularTicket() > Double.parseDouble(params.getPriceFrom())).collect(Collectors.toList());
		}
		
		if(!params.getPriceUntil().equals("")) {
			collection = collection.stream()
					.filter(manifestation -> manifestation.getPriceOfRegularTicket() < Double.parseDouble(params.getPriceUntil())).collect(Collectors.toList());
		}
		
		if(!params.getDateFrom().equals("")) {	
			try {
				final Date d = sdf.parse(params.getDateFrom());
				collection = collection.stream()
						.filter(manifestation -> manifestation.getDate().after(d)).collect(Collectors.toList());
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		
		if(!params.getDateUntil().equals("")) {
			try {
				final Date d = sdf.parse(params.getDateUntil());
				collection = collection.stream()
						.filter(manifestation -> manifestation.getDate().before(d)).collect(Collectors.toList());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return collection.stream()
				.filter(t -> t.isDeleted()==false).collect(Collectors.toList());
	}
	
	private boolean validateParams(QueryParams params) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			double num;
			if(!params.getPriceFrom().equals("")) {
				num = Double.parseDouble(params.getPriceFrom());
			}
			if(!params.getPriceUntil().equals("")) {
				num = Double.parseDouble(params.getPriceUntil());
			}
			
			Date d;
			if(!params.getDateFrom().equals("")) {
				d = sdf.parse(params.getDateFrom());
			}
			if(!params.getDateFrom().equals("")) {
				d = sdf.parse(params.getDateFrom());
			}				
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
}
