package com.ca.datcm;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Properties;
import java.util.List;
import java.util.HashMap;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;


public class MUF {
  public enum MFQ {
    BUFFER_REFERENCES (1),
    CPU_TIME (2),
    CURRENT_STATUS (3),
    DB_COMMAND (4),
    DBID (5),
    DURATION (6),
    EOJ_OK (7),
    JOB_NAME (8),
    LOCK_VALUE (9),
    MUF_NAME (10),
    MUFPLEX_OWNER (11),
    OPTIONAL_ID (12),
    OWNER_TASK (13),
    PHYSICAL_EXCPS (14),
    REQUEST_SEQ_NO (15), 
    RUN_TIME (16),
    RUN_UNIT (17),
    TABLE_NAME (18),
    TASK_NUMBER (19),
    TRN_SEQ_NO (20),
    TSN_DURATION (21);
  
    private int value;
  
    private MFQ(int value) {
      this.value = value;
    }
  }
  
  public static MUF make(String dbservUrl) throws Exception, SQLException {
    PoolProperties p = new PoolProperties();
    p.setDriverClassName("ca.datacom.jdbc.DatacomJdbcDriver");
    p.setUrl(dbservUrl);
    p.setInitialSize(3);
    p.setRemoveAbandonedTimeout(60);
    DataSource ds = new DataSource();
    ds.setPoolProperties(p);
   
    Connection con = null;
    try {
      con = ds.getConnection();
    } finally {
      if (con != null) con.close();
    }

    MUF muf = new MUF();
    muf.setDbservUrl(dbservUrl);
    muf.setDs(ds); 
    
    return muf;
  }

  public String getDbservUrl() {
    return dbservUrl;
  }
  public void setDbservUrl(String dbservUrl) {
    this.dbservUrl = dbservUrl;
  }

  public DataSource getDs() {
    return ds;
  }
  public void setDs(DataSource ds) {
    this.ds = ds;
  }

  public Item asItem(String baseURI) {
    Item i = new Item();
    i.setHref(baseURI);
    i.addDatum(new Datum("MUF_ACTIVE_TASKS", baseURI + "/MFQ"));
    i.addLink(new Link("connection", dbservUrl));

    return i;
  }

  public List<Item> serializeMFQ(String baseURI) throws Exception {
    Connection con = null;
    try {
      con = ds.getConnection();
      List<Item> items = new ArrayList<Item>();

      Statement s = con.createStatement();
      String query = "SELECT BUFFER_REFERENCES, CPU_TIME, CURRENT_STATUS, " 
                        + "DB_COMMAND, DBID, DURATION, EOJ_OK, JOB_NAME, LOCK_VALUE, "
                        + "MUF_NAME, MUFPLEX_OWNER, OPTIONAL_ID, OWNER_TASK, PHYSICAL_EXCPS, "
                        + "REQUEST_SEQ_NO, RUN_TIME, RUN_UNIT, TABLE_NAME, TASK_NUMBER, "
                        + "TRN_SEQ_NO, TSN_DURATION FROM SYSADM.MUF_ACTIVE_TASKS;"; 
      ResultSet rs = s.executeQuery(query);

      while (rs.next()) {
        Task t = new Task();

        t.setRequestSeqNo(rs.getInt(MFQ.REQUEST_SEQ_NO.value));
        t.setBufferReferences(rs.getInt(MFQ.BUFFER_REFERENCES.value));
        t.setCpuTime(rs.getString(MFQ.CPU_TIME.value));
        t.setCurrentStatus(rs.getString(MFQ.CURRENT_STATUS.value));
        t.setDbCommand(rs.getString(MFQ.DB_COMMAND.value));
        t.setDbid(rs.getInt(MFQ.DBID.value));
        t.setDuration(rs.getString(MFQ.DURATION.value));
        t.setEojOk(rs.getString(MFQ.EOJ_OK.value).charAt(0));
        t.setJobName(rs.getString(MFQ.JOB_NAME.value));
        t.setLockValue(rs.getString(MFQ.LOCK_VALUE.value));
        t.setMufName(rs.getString(MFQ.MUF_NAME.value));
        t.setMufplexOwner(rs.getString(MFQ.MUFPLEX_OWNER.value));
        t.setOptionalId(rs.getString(MFQ.OPTIONAL_ID.value));
        t.setOwnerTask(rs.getInt(MFQ.OWNER_TASK.value));
        t.setPhysicalExcps(rs.getInt(MFQ.PHYSICAL_EXCPS.value));
        t.setRunTime(rs.getString(MFQ.RUN_TIME.value));
        t.setRunUnit(rs.getInt(MFQ.RUN_UNIT.value));
        t.setTableName(rs.getString(MFQ.TABLE_NAME.value));
        t.setTaskNumber(rs.getInt(MFQ.TASK_NUMBER.value));
        t.setTrnSeqNo(rs.getString(MFQ.TRN_SEQ_NO.value));
        t.setTsnDuration(rs.getString(MFQ.TSN_DURATION.value));

        items.add(t.asItem(baseURI + "?WHERE=" + t.sqlIdStr()));
      } 
      rs.close();
      s.close();
 
      return items;
    } finally {
      if (con != null) con.close();
    }
  }
  
  private DataSource ds; 
  private String dbservUrl;
}
