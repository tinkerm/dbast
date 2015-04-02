package com.ca.datcm;

import javax.xml.bind.annotation.*;
import java.util.*;
import org.jboss.resteasy.annotations.providers.jaxb.IgnoreMediaTypes;

@XmlRootElement(name = "link")
@IgnoreMediaTypes("application/*+json")
public class Link { 
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

  @XmlElement(name = "name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  @XmlElement(name = "render")
  public String getRender() {
    return render;
  }
  public void setRender(String render) {
    this.render = render;
  }

  public Link() {}
  public Link(String rel, String href) {
    this.rel = rel;
    this.href = href;
  }
  
  private String rel;
  private String href;
  private String prompt;
  private String name;
  private String render;
}
