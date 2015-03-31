package com.ca.datcm;

import org.hibernate.Session;
import java.util.*;
import com.ca.datcm.Event;
import com.ca.datcm.util.HibernateUtil;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;

public class EventManager {
  public static void main(String[] args) throws Exception, SQLException {
    EventManager mgr = new EventManager();
/*    List tasks = mgr.listTasks();
    for (int i = 0; i < tasks.size(); i++) { 
      Task task = (Task)tasks.get(i);
      System.out.println(task.getCurrentStatus());
    }

    HibernateUtil.getFactory().close(); */

    DataSourceFactory dsf = new DataSourceFactory();
    Properties p = new Properties();
    p.setProperty("driverClassName", "ca.datacom.jdbc.DatacomJdbcDriver");
    p.setProperty("url", "jdbc:datacom://usilca32:8617/ServerName=DBDVMJ_SV");
    DataSource ds = dsf.createDataSource(p);
    Connection con = ds.getConnection();
    Statement s = con.createStatement();
    String query = "SELECT BUFFER_REFERENCES, CPU_TIME, CURRENT_STATUS, " 
                      + "DB_COMMAND, DBID, DURATION, EOJ_OK, JOB_NAME, LOCK_VALUE, "
                      + "MUF_NAME, MUFPLEX_OWNER, OPTIONAL_ID, OWNER_TASK, PHYSICAL_EXCPS, "
                      + "REQUEST_SEQ_NO, RUN_TIME, RUN_UNIT, TABLE_NAME, TASK_NUMBER, "
                      + "TRN_SEQ_NO, TSN_DURATION FROM SYSADM.MUF_ACTIVE_TASKS;"; 
    ResultSet rs = s.executeQuery(query);
    while (rs.next()) {
      System.out.println("ROW BEGIN ----------------------------");
      System.out.println("\tBUFFER_REFERENCES: |" + rs.getInt(1) + "|");
      System.out.println("\tCPU_TIME: |" + rs.getString(2) + "|");
      System.out.println("\tCURRENT_STATUS: |" + rs.getString(3) + "|");
      System.out.println("\tDB_COMMAND: |" + rs.getString(4) + "|");
      System.out.println("\tDBID: |" + rs.getInt(5) + "|");
      System.out.println("\tDURATION: |" + rs.getString(6) + "|");
      System.out.println("\tEOJ_OK: |" + rs.getString(7) + "|");
      System.out.println("\tJOB_NAME: |" + rs.getString(8) + "|");
      System.out.println("\tLOCK_VALUE: |" + rs.getString(9) + "|");
      System.out.println("\tMUF_NAME: |" + rs.getString(10) + "|");
      System.out.println("\tMUFPLEX_OWNER: |" + rs.getString(11) + "|");
      System.out.println("\tOPTIONAL_ID: |" + rs.getString(12) + "|");
      System.out.println("\tOWNER_TASK: |" + rs.getInt(13) + "|");
      System.out.println("\tPHYSICAL_EXCPS: |" + rs.getInt(14) + "|");
      System.out.println("\tREQUEST_SEQ_NO: |" + rs.getInt(15) + "|");
      System.out.println("\tRUN_TIME: |" + rs.getString(16) + "|");
      System.out.println("\tRUN_UNIT: |" + rs.getInt(17) + "|");
      System.out.println("\tTABLE_NAME: |" + rs.getString(18) + "|");
      System.out.println("\tTASK_NUMBER: |" + rs.getInt(19) + "|");
      System.out.println("\tTRN_SEQ_NO: |" + rs.getString(20) + "|");
      System.out.println("\tTSN_DURATION: |" + rs.getString(21) + "|");
      System.out.println("-----------------------------------");
    } 
    rs.close(); 
    con.close();
  }

  private List listTasks() {
    Session session = HibernateUtil.getFactory().getCurrentSession();
    session.beginTransaction(); 
    List result = session.createQuery("from Task").list();
    session.getTransaction().commit();
    return result;
  }
}
