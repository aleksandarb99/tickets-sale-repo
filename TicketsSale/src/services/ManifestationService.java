package services;


import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


import model.Manifestation;
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
	
	/*@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Student getStudents(Student student) {
		StudentDAO dao = (StudentDAO) ctx.getAttribute("StudentDAO");
		if(dao.find(student.getBrojIndexa()) != null) {
			return null;
		}
		return dao.addStudent(student);
	}
	
    @GET
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
