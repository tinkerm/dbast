package com.ca.datcm;

import java.util.*;
import javax.xml.bind.annotation.*;
import org.jboss.resteasy.annotations.providers.jaxb.IgnoreMediaTypes;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder={"href","data","links"})
@IgnoreMediaTypes("application/*+json")
public class Item {
  @XmlElement(name = "href")
  public String getHref() {
    return href;
  }
  public void setHref(String href) {
    this.href = href;
  }
  
  @XmlElement
  @XmlElementWrapper(name = "data")
  public List<Datum> getData() {
    return data;    
  }
  public void setData(List<Datum> data) {
    this.data = data;
  }
  public void addDatum(Datum d) {
    if (data == null) data = new ArrayList<Datum>();

    data.add(d);
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
  
  private String href;
  private List<Datum> data;
  private List<Link> links; 
}
