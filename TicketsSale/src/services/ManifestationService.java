package services;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.Manifestation;
import model.QueryParams;
import dao.LocationDAO;
import dao.ManifestationDAO;

@Path("/manifestations")
public class ManifestationService {
	
	@Context
	ServletContext ctx;
	LocationDAO locationDAO;
	
	public ManifestationService() {
	}
	
	@PostConstruct
	public void init() {
		if (ctx.getAttribute("LocationDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	locationDAO = new LocationDAO(contextPath);
			ctx.setAttribute("LocationDAO", locationDAO);
		}
		if (ctx.getAttribute("ManifestationDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("ManifestationDAO", new ManifestationDAO(contextPath, locationDAO));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getManifestations() {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		return dao.findAll();
	}
	
	@GET
	@Path("/recent/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getRecentManifestations() {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		return dao.findRecent();
	}
	
	@GET
	@Path("/{name: .+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Manifestation getManifestation(@PathParam("name") String name) {
		
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("ManifestationDAO");
		return dao.find(name);
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
		
		return collection;
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
	
	/*@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Customer getStudents(Customer customer) {
		StudentDAO dao = (StudentDAO) ctx.getAttribute("StudentDAO");
		if(dao.find(student.getBrojIndexa()) != null) {
			return null;
		}
		return dao.addStudent(student);
	}*/
	
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
