package com.ca.datcm.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Properties;

public class HibernateUtil {
  private static final SessionFactory sessionFactory = buildSessionFactory();
  private static final SessionFactory dcmSessionFactory = buildDcmSessionFactory();
  private static final SessionFactory propSessionFactory = buildFromProperties();

  private static SessionFactory buildSessionFactory() {
    try {
      return new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Initial SessionFactory creation failed. " + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  private static SessionFactory buildDcmSessionFactory() {
    try {
      return new Configuration().configure("h2.cfg.xml").buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Initial dcmSessionFactory creation failed. " + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  private static SessionFactory buildFromProperties() {
    try {
      return new Configuration().configure("h3.cfg.xml")
                                .setProperty("hibernate.connection.url", 
                                             "jdbc:datacom://usilca32:8617/ServerName=DBDVMJ_SV")
                                .buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Properties-style configuration failed. " + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public static SessionFactory getDcmSessionFactory() {
    return dcmSessionFactory;
  }

  public static SessionFactory getPropSessionFactory() {
    return propSessionFactory;
  }
}
