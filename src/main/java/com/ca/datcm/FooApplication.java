package com.ca.datcm;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class FooApplication extends Application {
  private static Set<Object> singletons = new HashSet<Object>();
  private static Set<Class<?>> sets = new HashSet<Class<?>>();
  
  public FooApplication() {
    singletons.add(new FooService());
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

