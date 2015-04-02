package com.ca.datcm;

import javax.xml.bind.annotation.*;
import org.jboss.resteasy.annotations.providers.jaxb.IgnoreMediaTypes;
import java.util.*;

@XmlRootElement(name = "query")
@IgnoreMediaTypes("application/*+json")
public class Query {
  public Query() {}

  @XmlElement(name = "href")
  public String getHref() {
    return href;
  }
  public void setHref(String href) {
    this.href = href;
  }
  @XmlElement(name = "rel")
  public String getRel() {
    return rel;
  }
  public void setRel(String rel) {
    this.rel = rel;
  }
  @XmlElement(name = "prompt")
  public String getPrompt() {
    return prompt;
  }
  public void setPrompt(String prompt) {
    this.prompt = prompt;
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
   
  private String href = "";
  private String rel = "";
  private String name;
  private String prompt;
  private List<Datum> data;
}
