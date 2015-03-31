package com.ca.datcm;

import org.hibernate.Session;

import java.util.*;

import com.ca.datcm.Event;
import com.ca.datcm.util.HibernateUtil;

public class EventManager {
  public static void main(String[] args) {
    EventManager mgr = new EventManager();
    if (args[0].equals("store")) {
      mgr.createAndStoreEvent("My Event", new Date());
    } else if (args[0].equals("list")) {
      List events = mgr.listEvents();
      for (int i = 0; i < events.size(); i++) {
        Event theEvent = (Event)events.get(i);
        System.out.println("Event: " + theEvent.getTitle() + " Time: " + theEvent.getDate());
      }
    } else if (args[0].equals("double")) {
      List visions = mgr.listVisions();
      for (int i = 0; i < visions.size(); i++) {
        Vision theVision = (Vision)visions.get(i);
        System.out.print(" " + theVision.getS());
      }
      System.out.println(" ");
    } else {
      List tasks = mgr.listTasks();
      for (int i = 0; i < tasks.size(); i++) { 
        MFQ task = (MFQ)tasks.get(i);
        System.out.println(task.getCurrentStatus());
      }
    }

    HibernateUtil.getSessionFactory().close();
    HibernateUtil.getFactory().close();
    HibernateUtil.getDcmSessionFactory().close();
    HibernateUtil.getPropSessionFactory().close();
  }

  private void createAndStoreEvent(String title, Date theDate) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    Event theEvent = new Event();
    theEvent.setTitle(title);
    theEvent.setDate(theDate);
    session.save(theEvent);

    session.getTransaction().commit();
  }

  private void addPersonToEvent(Long personId, Long eventId) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    Person aPerson = (Person)session.load(Person.class, personId);
    /* OR..."eager fetch" the collection so we can use it detached.(!?) 
       Person aPerson = (Person) session
              .createQuery("select p from Person p left join fetch p.events where p.id = :pid")
              .setParameters("pid", personId)
              .uniqueResult(); 
       Event anEvent = (Event) session.load(Event.class, eventId);
       session.getTransaction().commit();
       aPerson.getEvents().add(anEvent);    // NOTE: aPerson (and its collection) is detached!
       Session session2 = HibernateUtil.getSessionFactory().getCurrentSession(); 
       session2.beginTransaction();
       session2.update(aPerson); // NOTE: reattachment of aPerson(!!)
       session2.getTransaction().commit(); */
    Event anEvent = (Event)session.load(Event.class, eventId);
    aPerson.getEvents().add(anEvent);
    session.getTransaction().commit();
  }

  private void addEmailToPerson(Long personId, String emailAddress) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession(); 
    session.beginTransaction();
    
    Person aPerson = (Person)session.load(Person.class, personId);
    aPerson.getEmailAddresses().add(emailAddress);

   session.getTransaction().commit();
  }

  private List listVisions() {
/*    Session session = HibernateUtil.getDcmSessionFactory().getCurrentSession();*/
    Session session = HibernateUtil.getPropSessionFactory().getCurrentSession();
    session.beginTransaction();
    List result = session.createQuery("from Vision").list();
    session.getTransaction().commit();
    return result;
  } 
  
  private List listTasks() {
    Session session = HibernateUtil.getFactory().getCurrentSession();
    session.beginTransaction(); 
    List result = session.createQuery("from MFQ").list();
    session.getTransaction().commit();
    return result;
  }

  private List listEvents() {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    List result = session.createQuery("from Event").list();
    session.getTransaction().commit();
    return result;
  }
}
