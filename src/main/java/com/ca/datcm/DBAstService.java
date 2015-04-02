package com.ca.datcm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import javax.ws.rs.core.MultivaluedMap;
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
public class DBAstService {
  private ConcurrentHashMap<Integer, MUF> mufs = new ConcurrentHashMap<Integer, MUF>();
  private AtomicInteger mufIds = new AtomicInteger();

  public DBAstService() {}

  private CollectionPlusJSON createCJSON(String uri, boolean queryLink, boolean templateLink) {
    CollectionPlusJSON cjson = new CollectionPlusJSON();

    cjson.getTlo().setHref(uri);
    if (queryLink) cjson.getTlo().addLink(new Link("queries", uri + "/queries"));
    if (templateLink) cjson.getTlo().addLink(new Link("template", uri + "/template"));

    return cjson;
  }

  @GET
  @Produces("application/vnd.collection+json")
  @Path("/mufs")
  public CollectionPlusJSON getMUFs(@Context UriInfo uri) {
    String baseUri = uri.getAbsolutePath().toString();
    CollectionPlusJSON cjson = createCJSON(baseUri, true, true);
    Integer id;

    for (Enumeration<Integer> e = mufs.keys(); e.hasMoreElements(); ) { 
      id = e.nextElement();
      cjson.getTlo().addItem(mufs.get(id).asItem(baseUri + "/" + id.toString()));  
    } 

    return cjson;
  }

  @GET
  @Produces("application/vnd.collection+json")
  @Path("/mufs/{id}")
  public CollectionPlusJSON getMUF(@Context UriInfo uri,
                                   @PathParam("id") Integer id) {
    String baseUri = uri.getBaseUri().toString() + "mufs";
    CollectionPlusJSON cjson = createCJSON(baseUri, false, false);

    cjson.getTlo().addItem(mufs.get(id).asItem(baseUri + "/" + id.toString()));  

    return cjson; 
  }

  @GET
  @Produces("application/vnd.collection+json")
  @Path("/mufs/{id}/MFQ")
  public Response getMUF_MFQ(@PathParam("id") Integer id, 
                             @Context UriInfo uri) {
    String baseUri = uri.getAbsolutePath().toString();
    CollectionPlusJSON cjson = createCJSON(baseUri, true, true);
    MultivaluedMap<String, String> params = uri.getQueryParameters(true); 
    String WHERE = params.getFirst("WHERE");
    String ORDERBY = params.getFirst("ORDERBY");
    System.out.println("WHERE: " + WHERE);
    System.out.println("ORDERBY: " + ORDERBY);

    try {
      cjson.getTlo().setItems(mufs.get(id).serializeMFQ(baseUri));
    } catch (Exception e) {
      cjson = createCJSON(baseUri, false, false);
      cjson.getTlo().setError(new Error("Unable to connect to DBSERV '" + mufs.get(id).getDbservUrl() + "'", 
                                        "503", e.getMessage()));         
      return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                     .entity(cjson).type(new MediaType("application", "vnd.collection+json")).build();
    }

    return Response.ok().entity(cjson).build();
  }

  @GET
  @Produces("application/vnd.collection+json")
  @Path("/mufs/template")
  public CollectionPlusJSON getTemplate(@Context UriInfo uri) {
    String baseUri = uri.getBaseUri().toString() + "mufs";
    CollectionPlusJSON cjson = createCJSON(baseUri, false, false);
    Template t = new Template();

    t.addDatum(new Datum("Connection URL for a CA Datacom Server connected to the MUF", 
                         "DBSERV URL",
                         "jdbc:datacom://example.org:9090/ServerName=MYDBSRV")); 
    cjson.getTlo().setTemplate(t);

    return cjson;
  }

  @POST
  @Path("/mufs")
  @Consumes("application/json")
  public Response addMUF(@Context UriInfo uri, 
                         TopLevelObject tlo) {
    int nextId = 0;
    String baseUri = uri.getAbsolutePath().toString();

    for (Datum d : tlo.getTemplate().getData()) {
      if ("DBSERV URL".equals(d.getName())) {
        try { 
          MUF muf = MUF.make(d.getValue());
          nextId = mufIds.incrementAndGet();
          mufs.put(nextId, muf);
          break;
        } catch (Exception e) {
          CollectionPlusJSON cjson = createCJSON(baseUri, false, false);
          cjson.getTlo().setError(new Error("Unable to connect to DBSERV '" + 
                                            d.getValue() + "'", "400", e.getMessage()));         
          return Response.status(Response.Status.BAD_REQUEST)
                         .entity(cjson).type(new MediaType("application", "vnd.collection+json")).build();
        }
      } 
    }

    return Response.created(URI.create(baseUri + "/" + Integer.toString(nextId))).build();
  }
}
