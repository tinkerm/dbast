package com.ca.datcm;

import javax.xml.bind.annotation.*;
import org.jboss.resteasy.annotations.providers.jaxb.IgnoreMediaTypes;
import java.util.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder={"version", "href", "links", "items", "queries", "template", "error"})
@IgnoreMediaTypes("application/*+json")
public class TopLevelObject {
  @XmlElement
  public String getVersion() {
    return version;
  }
  public void setVersion(String version) {
    this.version = version;
  }

  @XmlElement
  public String getHref() {
    return href;  
  }
  public void setHref(String href) {
    this.href = href;
  }

  @XmlElement
  public Error getError() {
    return error;  
  }
  public void setError(Error error) {
    this.error = error;
  }

  @XmlElement
  public Template getTemplate() {
    return template;
  }
  public void setTemplate(Template template) {
    this.template = template;
  }

  @XmlElement
  @XmlElementWrapper(name = "links")
  public List<Link> getLinks() {
    return links;
  }
  public void setLinks(List<Link> links) {
    this.links = links;
  }
  public void addLink(Link l) {
    if (links == null) links = new ArrayList<Link>();

    links.add(l);
  }

  @XmlElement
  @XmlElementWrapper(name = "items")
  public List<Item> getItems() {
    return items;
  }
  public void setItems(List<Item> items) {
    this.items = items;
  }
  
  public void addItem(Item i) {
    if (items == null) items = new ArrayList<Item>();

    items.add(i);
  } 

  @XmlElement
  @XmlElementWrapper(name = "queries")
  public List<Query> getQueries() {
    return queries;
  }
  public void setQueries(List<Query> queries) {
    this.queries = queries;
  }
  
  public void addQuery(Query q) {
    if (queries == null) queries = new ArrayList<Query>();

    queries.add(q);
  } 
  
  private String href = "";
  private String version = "1.0";
  private List<Item> items;
  private List<Link> links;
  private List<Query> queries;
  private Template template;
  private Error error;
}
