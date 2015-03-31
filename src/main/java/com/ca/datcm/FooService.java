package com.ca.datcm;

import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jboss.resteasy.links.AddLinks;
import org.jboss.resteasy.links.LinkResource;
import java.net.URI;
import org.hibernate.HibernateException;

@Path("/")
public class FooService {
  private static Map<String, Programmer> hackers = new HashMap<String, Programmer>();
  private Map<String, Server> servers = new ConcurrentHashMap<String, Server>();
  private ConcurrentHashMap<Integer, MUF> mufs = new ConcurrentHashMap<Integer, MUF>();
  private AtomicInteger mufIds = new AtomicInteger();
  private AtomicInteger serverIds = new AtomicInteger();
  private CollectionPlusJSON baseObj = new CollectionPlusJSON();
  private static final String baseURI = "http://10.130.255.16:8080/hwapp/api/mufs";
  public static final DataSourceFactory dsf = new DataSourceFactory();

  static {
    Programmer dennis = Programmer.make("1", "Dennis Richie", new String[] {"c", "assembler" });
    hackers.put(dennis.getId(), dennis);
    Programmer richard = Programmer.make("2", "Richard Stallman", new String[] {"c", "java", "ruby" });
    hackers.put(richard.getId(), richard);
  }

  public FooService() {
    baseObj.getTlo().setHref(baseURI);
    baseObj.getTlo().addLink(new Link("queries", baseURI + "/queries"));
    baseObj.getTlo().addLink(new Link("template", baseURI + "/template")); 
  }

  private CollectionPlusJSON getBasicCJSON(String myBaseURI) {
    CollectionPlusJSON mfq = new CollectionPlusJSON();
    mfq.getTlo().setHref(myBaseURI);
    mfq.getTlo().addLink(new Link("queries", myBaseURI + "/queries"));
    mfq.getTlo().addLink(new Link("template", myBaseURI + "/template"));
    return mfq;
  }

  @GET
  @Produces("application/vnd.collection+json")
  @Path("/mufs")
  public CollectionPlusJSON getMUFs() {
    TopLevelObject tlo = baseObj.getTlo();
    if (tlo.getItems() != null) tlo.getItems().clear();
    tlo.setTemplate(null);
    tlo.setError(null);
    Integer key;
    for (Enumeration<Integer> e = mufs.keys(); e.hasMoreElements(); ) { 
      key = e.nextElement();
      Item i = mufs.get(key).asItem(baseURI + "/" + key.toString());
      tlo.addItem(i); 
    } 
    return baseObj;
  }

  @GET
  @Produces("application/vnd.collection+json")
  @Path("/mufs/{id}/MFQ")
  public CollectionPlusJSON getMUFmfq(@PathParam("id") Integer id) {
    String myBaseURI = baseURI + "/" + id.toString() + "/MFQ";
    CollectionPlusJSON cjson = getBasicCJSON(myBaseURI);
    try {
      cjson.getTlo().setItems(mufs.get(id).serializeMFQ(myBaseURI));
    } catch (Exception e) {
      System.out.println("NOPE: " + e.getMessage());
    }
    return cjson;
  }

  @GET
  @Produces("application/vnd.collection+json")
  @Path("/mufs/template")
  public CollectionPlusJSON getTemplate() {
    TopLevelObject tlo = baseObj.getTlo();
    tlo.setItems(null);
    tlo.setError(null);
    Template t = new Template();
    t.addDatum(new Datum("Connection URL for a CA Datacom Server connected to the MUF", 
                         "DBSERV URL",
                         "jdbc:datacom://example.org:9090/ServerName=MYDBSRV")); 
    tlo.setTemplate(t);
    return baseObj;
  }

  @POST
  @Path("/mufs")
  @Consumes("application/json")
  public Response addMUF(TopLevelObject tlo) {
    int nextId = 0;
    for (Datum d : tlo.getTemplate().getData()) {
      if ("DBSERV URL".equals(d.getName())) {
        try { 
          MUF muf = MUF.make(d.getValue());
          nextId = mufIds.incrementAndGet();
          mufs.put(nextId, muf);
          break;
        } catch (Exception e) {
          TopLevelObject ourTlo = baseObj.getTlo();
          ourTlo.setItems(null);
          ourTlo.setTemplate(null);
          ourTlo.setError(new Error("Unable to connect to specified DBSERV", "400", e.getMessage()));         
          return Response.status(Response.Status.BAD_REQUEST)
                         .entity(baseObj).type(new MediaType("application", "vnd.collection+json")).build();
        }
      } 
    }
    return Response.created(URI.create(baseURI + "/" + Integer.toString(nextId))).build();
  }

  @LinkResource(value = Server.class)
  @GET
  @Produces("application/json")
  @Path("/servers")
  public Collection<Server> getServers() {
    return servers.values(); 
  }  

  @AddLinks
  @LinkResource(value = Server.class)
  @GET
  @Path("/servers/{id}")
  @Produces("application/json")
  public Server getServer(@PathParam("id") String id) {
    return servers.get(id);
  }

  @POST
  @Consumes("application/json")
  public Response addServer(Server server) {
    server.setId(Integer.toString(serverIds.incrementAndGet()));
    servers.put(server.getId(), server);
    return Response.created(URI.create("/servers/" + server.getId())).build();
  }

  @LinkResource(value = Server.class)
  @DELETE
  @Path("/{id}")
  public Response deleteServer(@PathParam("id") String id) {
    servers.remove(id); 
    return Response.status(Response.Status.NO_CONTENT).build();
  }

  @AddLinks
  @LinkResource(value = Book.class)
  @GET
  @Path("/books")
  @Produces("application/json")
  public Collection<Book> listBooks() {
    ArrayList<Book> res = new ArrayList<Book>();
    res.add(new Book("Vladimir Nabokov", "Lolita"));
    return res;
  }

  @GET
  @Path("/hello")
  @Produces("text/plain")
  public String hello() {
    return "Hello world!";
  }

  @GET
  @Path("/echo/{message}")
  @Produces("text/plain")
  public String echo(@PathParam("message") String message) {
    return message;
  }

  @GET
  @Path("/hackers")
  @Produces("text/xml")
  public List<Programmer> listHackers() {
    return new ArrayList<Programmer>(hackers.values());
  }

  @GET
  @Path("/hacker/{id}")
  @Produces("text/xml")
  public Programmer getHacker(@PathParam("id") String id) {
    return hackers.get(id);
  }

  @GET
  @Path("/json/hackers")
  @Produces("text/json")
  public List<Programmer> listHackersJson() {
    return new ArrayList<Programmer>(hackers.values());
  }

  @GET
  @Path("/json/hacker/{id}")
  @Produces("text/json")
  public Programmer getHackerJson(@PathParam("id") String id) {
    return hackers.get(id);
  }
}
