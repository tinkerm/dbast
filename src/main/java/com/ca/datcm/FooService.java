package com.ca.datcm;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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

@Path("/foo")
public class FooService {
  private static Map<String, Programmer> hackers = new HashMap<String, Programmer>();
  private Map<String, Server> servers = new ConcurrentHashMap<String, Server>();
  private AtomicInteger serverIds = new AtomicInteger();

  static {
    Programmer dennis = Programmer.make("1", "Dennis Richie", new String[] {"c", "assembler" });
    hackers.put(dennis.getId(), dennis);
    Programmer richard = Programmer.make("2", "Richard Stallman", new String[] {"c", "java", "ruby" });
    hackers.put(richard.getId(), richard);
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
