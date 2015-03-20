package com.ca.datcm.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
  private static final SessionFactory sessionFactory = buildSessionFactory();
  private static final SessionFactory dcmSessionFactory = buildDcmSessionFactory();

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

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public static SessionFactory getDcmSessionFactory() {
    return dcmSessionFactory;
  }
}
