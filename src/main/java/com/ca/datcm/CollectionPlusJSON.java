package com.ca.datcm;

import javax.xml.bind.annotation.*;
import org.jboss.resteasy.annotations.providers.jaxb.IgnoreMediaTypes;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@IgnoreMediaTypes("application/*+json")
class CollectionPlusJSON {
  @XmlElement(name = "collection")
  public TopLevelObject getTlo() {
    return tlo;
  }
  
  private TopLevelObject tlo = new TopLevelObject();  
}
