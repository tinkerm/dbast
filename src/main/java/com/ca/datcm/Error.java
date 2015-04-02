package com.ca.datcm;

import javax.xml.bind.annotation.*;
import org.jboss.resteasy.annotations.providers.jaxb.IgnoreMediaTypes;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@IgnoreMediaTypes("application/*+json")
public class Error {
  public Error() { }
  public Error(String title, String code, String message) {
    this.title = title;
    this.code = code;
    this.message = message;
  }
 
  @XmlElement
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  @XmlElement
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  @XmlElement
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }

  private String title;
  private String code;
  private String message;
}
