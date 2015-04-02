package com.ca.datcm;

import javax.xml.bind.annotation.*;
import java.util.*;
import org.jboss.resteasy.annotations.providers.jaxb.IgnoreMediaTypes;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@IgnoreMediaTypes("application/*+json")
class Template {
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

  private List<Datum> data;
}
