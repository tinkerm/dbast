package com.ca.datcm.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Properties;

public class HibernateUtil {
  private static final SessionFactory factory = buildFactory();

  private static SessionFactory buildFactory() {
    try {
      return new Configuration().configure("dtcmapi.cfg.xml")
                                .setProperty("hibernate.connection.url", 
                                             "jdbc:datacom://usilca32:8617/ServerName=DBDVMJ_SV")
                                .buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Configuration failed. " + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static SessionFactory getFactory() {
    return factory;
  } 
}
