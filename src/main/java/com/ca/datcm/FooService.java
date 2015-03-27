package com.ca.datcm;

import java.util.ArrayList;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jboss.resteasy.links.AddLinks;
import org.jboss.resteasy.links.LinkResource;

@Path("/foo")
public class FooService {
  private static Map<String, Programmer> hackers = new HashMap<String, Programmer>();
  private DTCMServers servers = new DTCMServers();

  static {
    Programmer dennis = Programmer.make("1", "Dennis Richie", new String[] {"c", "assembler" });
    hackers.put(dennis.getId(), dennis);
    Programmer richard = Programmer.make("2", "Richard Stallman", new String[] {"c", "java", "ruby" });
    hackers.put(richard.getId(), richard);
  }

  @Path("/servers")
  public DTCMServers accessServers() {
    return servers;
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
