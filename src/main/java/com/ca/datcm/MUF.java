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
    TSN_DURATION (21),
    USER_PATH (22),
    USER_JOBID (23),
    USER_RQ_DATA (24),
    USER_SYSTEM_NAME (25),
    WAIT_DURATION (26),
    WAIT_TIME (27);
  
    private int value;
  
    private MFQ(int value) {
      this.value = value;
    }
  }

  static String[] MFQcols = { "N/A", "BUFFER_REFERENCES", "CPU_TIME", "CURRENT_STATUS", 
                              "DB_COMMAND", "DBID", "DURATION", "EOJ_OK", "JOB_NAME", 
                              "LOCK_VALUE", "MUF_NAME", "MUFPLEX_OWNER", "OPTIONAL_ID", 
                              "OWNER_TASK", "PHYSICAL_EXCPS", "REQUEST_SEQ_NO", 
                              "RUN_TIME", "RUN_UNIT", "TABLE_NAME", "TASK_NUMBER", 
                              "TRN_SEQ_NO", "TSN_DURATION", "USER_PATH", "USER_JOBID", 
                              "USER_RQ_DATA", "USER_SYSTEM_NAME", "WAIT_DURATION", "WAIT_TIME" };
  
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

  public List<Item> serializeMFQ(String baseURI, String COLLIST, String WHERE, String ORDERBY) throws Exception {
    Connection con = null;
    int i;
 
    try {
      boolean[] inCOLLIST = new boolean[MFQ.WAIT_TIME.value + 1];
      for (i = 1; i <= MFQ.WAIT_TIME.value; i++) {
        inCOLLIST[i] = false;
      }

      con = ds.getConnection();
      List<Item> items = new ArrayList<Item>();

      Statement s = con.createStatement();
      StringBuilder query = new StringBuilder(1024);
      query.append("select ");
      if (COLLIST != null) {
        for (i = 1; i <= MFQ.WAIT_TIME.value; i++) {
          if (COLLIST.indexOf(MFQcols[i]) != -1) { 
            inCOLLIST[i] = true;
          }
        }
      } else {
        for (i = 1; i <= MFQ.WAIT_TIME.value; i++) {
          inCOLLIST[i] = true;
        }
      }
      query.append("BUFFER_REFERENCES, CPU_TIME, CURRENT_STATUS, ");
      query.append("DB_COMMAND, DBID, DURATION, EOJ_OK, JOB_NAME, LOCK_VALUE, ");
      query.append("MUF_NAME, MUFPLEX_OWNER, OPTIONAL_ID, OWNER_TASK, PHYSICAL_EXCPS, ");
      query.append("REQUEST_SEQ_NO, RUN_TIME, RUN_UNIT, TABLE_NAME, TASK_NUMBER, ");
      query.append("TRN_SEQ_NO, TSN_DURATION, USER_PATH, USER_JOBID, USER_RQ_DATA, ");
      query.append("USER_SYSTEM_NAME, WAIT_DURATION, WAIT_TIME ");
      query.append("from SYSADM.MUF_ACTIVE_TASKS");
      if (WHERE != null) {
        query.append(" where ");
        query.append(WHERE);
      }
      if (ORDERBY != null) {
        query.append(" order by ");
        query.append(ORDERBY);
      }
      query.append(";");

      ResultSet rs = s.executeQuery(query.toString());

      while (rs.next()) {
        Task t = new Task();

        if (inCOLLIST[MFQ.REQUEST_SEQ_NO.value])
          t.setRequestSeqNo(rs.getInt(MFQ.REQUEST_SEQ_NO.value));
        if (inCOLLIST[MFQ.BUFFER_REFERENCES.value])
          t.setBufferReferences(rs.getInt(MFQ.BUFFER_REFERENCES.value));
        if (inCOLLIST[MFQ.CPU_TIME.value])
          t.setCpuTime(rs.getString(MFQ.CPU_TIME.value));
        if (inCOLLIST[MFQ.CURRENT_STATUS.value])
          t.setCurrentStatus(rs.getString(MFQ.CURRENT_STATUS.value));
        if (inCOLLIST[MFQ.DB_COMMAND.value])
          t.setDbCommand(rs.getString(MFQ.DB_COMMAND.value));
        if (inCOLLIST[MFQ.DBID.value])
          t.setDbid(rs.getInt(MFQ.DBID.value));
        if (inCOLLIST[MFQ.DURATION.value])
          t.setDuration(rs.getString(MFQ.DURATION.value));
        if (inCOLLIST[MFQ.EOJ_OK.value])
          t.setEojOk(rs.getString(MFQ.EOJ_OK.value).charAt(0));
        if (inCOLLIST[MFQ.JOB_NAME.value])
          t.setJobName(rs.getString(MFQ.JOB_NAME.value));
        if (inCOLLIST[MFQ.LOCK_VALUE.value])
          t.setLockValue(rs.getString(MFQ.LOCK_VALUE.value));
        if (inCOLLIST[MFQ.MUF_NAME.value])
          t.setMufName(rs.getString(MFQ.MUF_NAME.value));
        if (inCOLLIST[MFQ.MUFPLEX_OWNER.value])
          t.setMufplexOwner(rs.getString(MFQ.MUFPLEX_OWNER.value));
        if (inCOLLIST[MFQ.OPTIONAL_ID.value])
          t.setOptionalId(rs.getString(MFQ.OPTIONAL_ID.value));
        if (inCOLLIST[MFQ.OWNER_TASK.value])
          t.setOwnerTask(rs.getInt(MFQ.OWNER_TASK.value));
        if (inCOLLIST[MFQ.PHYSICAL_EXCPS.value])
          t.setPhysicalExcps(rs.getInt(MFQ.PHYSICAL_EXCPS.value));
        if (inCOLLIST[MFQ.RUN_TIME.value])
          t.setRunTime(rs.getString(MFQ.RUN_TIME.value));
        if (inCOLLIST[MFQ.RUN_UNIT.value])
          t.setRunUnit(rs.getInt(MFQ.RUN_UNIT.value));
        if (inCOLLIST[MFQ.TABLE_NAME.value])
          t.setTableName(rs.getString(MFQ.TABLE_NAME.value));
        if (true)
          t.setTaskNumber(rs.getInt(MFQ.TASK_NUMBER.value));
        if (inCOLLIST[MFQ.TRN_SEQ_NO.value])
          t.setTrnSeqNo(rs.getString(MFQ.TRN_SEQ_NO.value));
        if (inCOLLIST[MFQ.TSN_DURATION.value])
          t.setTsnDuration(rs.getString(MFQ.TSN_DURATION.value));
        if (inCOLLIST[MFQ.USER_PATH.value])
          t.setUserPath(rs.getString(MFQ.USER_PATH.value));
        if (inCOLLIST[MFQ.USER_JOBID.value])
          t.setUserJobId(rs.getString(MFQ.USER_JOBID.value));
        if (inCOLLIST[MFQ.USER_RQ_DATA.value])
          t.setUserRqData(rs.getString(MFQ.USER_RQ_DATA.value));
        if (inCOLLIST[MFQ.USER_SYSTEM_NAME.value])
          t.setUserSystemName(rs.getString(MFQ.USER_SYSTEM_NAME.value));
        if (inCOLLIST[MFQ.WAIT_DURATION.value])
          t.setWaitDuration(rs.getString(MFQ.WAIT_DURATION.value));
        if (inCOLLIST[MFQ.WAIT_TIME.value])
          t.setWaitTime(rs.getString(MFQ.WAIT_TIME.value));

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
