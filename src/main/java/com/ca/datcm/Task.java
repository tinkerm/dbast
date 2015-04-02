package com.ca.datcm;

import java.net.URLEncoder;

public class Task {
  public int getRequestSeqNo() {
    return requestSeqNo;
  }
  public void setRequestSeqNo(int requestSeqNo) {
    this.requestSeqNo = requestSeqNo;
  }
 
  public int getBufferReferences() {
    return bufferReferences;
  }
  public void setBufferReferences(int bufferReferences) {
    this.bufferReferences = bufferReferences;
  }

  public String getCpuTime() {
    return cpuTime;
  }
  public void setCpuTime(String cpuTime) {
    this.cpuTime = cpuTime;
  }
  
  public String getCurrentStatus() {
    return currentStatus;
  } 
  public void setCurrentStatus(String currentStatus) {
    this.currentStatus = currentStatus;
  }

  public String getDbCommand() {
    return dbCommand;
  }
  public void setDbCommand(String dbCommand) {
    this.dbCommand = dbCommand;
  }  

  public int getDbid() {
    return dbid;
  }
  public void setDbid(int dbid) {
    this.dbid = dbid;
  }
  
  public String getDuration() {
    return duration;
  }
  public void setDuration(String duration) {
    this.duration = duration;
  }

  public Character getEojOk() {
    return eojOk;
  }
  public void setEojOk(Character eojOk) {
    this.eojOk = eojOk;
  }

  public String getJobName() {
    return jobName;
  }
  public void setJobName(String jobName) {
    this.jobName = jobName;
  }
  
  public String getLockValue() {
    return lockValue;
  } 
  public void setLockValue(String lockValue) {
    this.lockValue = lockValue;
  }

  public String getMufName() {
    return mufName;
  }
  public void setMufName(String mufName) {
    this.mufName = mufName;
  } 

  public String getMufplexOwner() {
    return mufplexOwner;
  }
  public void setMufplexOwner(String mufplexOwner) {
    this.mufplexOwner = mufplexOwner;
  } 

  public String getOptionalId() {
    return optionalId;
  }
  public void setOptionalId(String optionalId) {
    this.optionalId = optionalId;
  }

  public int getOwnerTask() {
    return ownerTask;
  }
  public void setOwnerTask(int ownerTask) {
    this.ownerTask = ownerTask;
  }

  public int getPhysicalExcps() {
    return physicalExcps;
  } 
  public void setPhysicalExcps(int physicalExcps) {
    this.physicalExcps = physicalExcps;
  }

  public String getRunTime() {
    return runTime;
  } 
  public void setRunTime(String runTime) {
     this.runTime = runTime;
  }
  
  public int getRunUnit() {
    return runUnit;
  }
  public void setRunUnit(int runUnit) {
    this.runUnit = runUnit;
  }

  public String getTableName() {
    return tableName;
  }
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public int getTaskNumber() {
    return taskNumber;
  }
  public void setTaskNumber(int taskNumber) {
    this.taskNumber = taskNumber;
  } 

  public String getTrnSeqNo() {
    return trnSeqNo;
  }
  public void setTrnSeqNo(String trnSeqNo) {
    this.trnSeqNo = trnSeqNo;
  }

  public String getTsnDuration() {
    return tsnDuration;
  }
  public void setTsnDuration(String tsnDuration) {
    this.tsnDuration = tsnDuration;
  } 

  public String sqlIdStr() {
    return URLEncoder.encode("TASK_NUMBER=" + String.valueOf(taskNumber));  
  } 

  public boolean equals(Task t) {
    return taskNumber == t.getTaskNumber();
  }

  public Item asItem(String baseURI) {
    Item i = new Item();
    i.setHref(baseURI);
    i.addDatum(new Datum("BUFFER_REFERENCES", String.valueOf(bufferReferences)));
    i.addDatum(new Datum("CPU_TIME", cpuTime));
    i.addDatum(new Datum("CURRENT_STATUS", currentStatus));
    i.addDatum(new Datum("DB_COMMAND", dbCommand));
    i.addDatum(new Datum("DBID", String.valueOf(dbid)));
    i.addDatum(new Datum("DURATION", duration));
    i.addDatum(new Datum("EOJ_OK", String.valueOf(eojOk)));
    i.addDatum(new Datum("JOB_NAME", jobName));
    i.addDatum(new Datum("LOCK_VALUE", lockValue));
    i.addDatum(new Datum("MUF_NAME", mufName));
    i.addDatum(new Datum("MUFPLEX_OWNER", mufplexOwner));
    i.addDatum(new Datum("OPTIONAL_ID", optionalId));
    i.addDatum(new Datum("OWNER_TASK", String.valueOf(ownerTask)));
    i.addDatum(new Datum("PHYSICAL_EXCPS", String.valueOf(physicalExcps)));
    i.addDatum(new Datum("REQUEST_SEQ_NO", String.valueOf(requestSeqNo)));
    i.addDatum(new Datum("RUN_TIME", runTime));
    i.addDatum(new Datum("TABLE_NAME", tableName));
    i.addDatum(new Datum("TASK_NUMBER", String.valueOf(taskNumber)));
    i.addDatum(new Datum("TRN_SEQ_NO", trnSeqNo));
    i.addDatum(new Datum("TSN_DURATION", tsnDuration));

    return i;
  }

  private int bufferReferences;
  private String cpuTime;
  private String currentStatus;
  private String dbCommand;
  private int dbid;
  private String duration;
  private Character eojOk;
  private String jobName;
  private String lockValue;
  private String mufName;
  private String mufplexOwner;
  private String optionalId;
  private int ownerTask;
  private int physicalExcps;
  private int requestSeqNo;
  private String runTime;
  private int runUnit; 
  private String tableName;
  private int taskNumber;
  private String trnSeqNo;
  private String tsnDuration;
}
