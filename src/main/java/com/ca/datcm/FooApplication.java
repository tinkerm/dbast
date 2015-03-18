package com.ca.datcm;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class FooApplication extends Application {
  private Set<Object> singletons = new HashSet<Object>();
  
  public FooApplication() {
    singletons.add(new FooService());
  }

  @Override
  public Set<Object> getSingletons() {
    return singletons;
  }
}

