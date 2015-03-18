package com.ca.datcm;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "programmer")
public class Programmer {
  private String id;
  private String name;
  private String[] languages;

  @XmlElement
  public String[] getLanguages() {
    return languages;
  }

  public void setLanguages(String[] languages) {
    this.languages = languages;
  }

  @XmlElement
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @XmlElement
  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }

  public static Programmer make(String id, String name, String[] languages) {
    Programmer programmer = new Programmer();
    programmer.setId(id);
    programmer.setName(name);
    programmer.setLanguages(languages);
    return programmer;
  }
}
