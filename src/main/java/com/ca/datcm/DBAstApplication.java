package com.ca.datcm;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class DBAstApplication extends Application {
  private static Set<Object> singletons = new HashSet<Object>();
  private static Set<Class<?>> sets = new HashSet<Class<?>>();
  
  public DBAstApplication() {
    singletons.add(new DBAstService());
  }

  @Override
  public Set<Object> getSingletons() {
    return singletons;
  }

  @Override
  public Set<Class<?>> getClasses() {
    return sets;
  }
}

