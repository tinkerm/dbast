package com.ca.datcm;

import javax.xml.bind.annotation.*;
import org.jboss.resteasy.annotations.providers.jaxb.IgnoreMediaTypes;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@IgnoreMediaTypes("application/*+json")
public class Datum {
  public Datum() { }
  public Datum(String name, String value) {
    this.name = name; 
    this.value = value;
  }
  public Datum(String prompt, String name, String value) {
    this.prompt = prompt;
    this.name = name;
    this.value = value;
  }

  @XmlElement
  public String getPrompt() {
    return prompt;
  }
  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }
  @XmlElement
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  @XmlElement
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }
 
  private String prompt;
  private String name;
  private String value; 
}
