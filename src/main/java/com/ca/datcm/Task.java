package com.ca.datcm;

import java.net.URLEncoder;

public class Task {
  public Integer getRequestSeqNo() {
    return requestSeqNo;
  }
  public void setRequestSeqNo(Integer requestSeqNo) {
    this.requestSeqNo = requestSeqNo;
  }
 
  public Integer getBufferReferences() {
    return bufferReferences;
  }
  public void setBufferReferences(Integer bufferReferences) {
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

  public Integer getDbid() {
    return dbid;
  }
  public void setDbid(Integer dbid) {
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

  public Integer getOwnerTask() {
    return ownerTask;
  }
  public void setOwnerTask(Integer ownerTask) {
    this.ownerTask = ownerTask;
  }

  public Integer getPhysicalExcps() {
    return physicalExcps;
  } 
  public void setPhysicalExcps(Integer physicalExcps) {
    this.physicalExcps = physicalExcps;
  }

  public String getRunTime() {
    return runTime;
  } 
  public void setRunTime(String runTime) {
     this.runTime = runTime;
  }
  
  public Integer getRunUnit() {
    return runUnit;
  }
  public void setRunUnit(Integer runUnit) {
    this.runUnit = runUnit;
  }

  public String getTableName() {
    return tableName;
  }
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public Integer getTaskNumber() {
    return taskNumber;
  }
  public void setTaskNumber(Integer taskNumber) {
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

  public String getUserPath() {
    return userPath;
  }
  public void setUserPath(String userPath) {
    this.userPath = userPath;
  }

  public String getUserJobId() {
    return userJobId;
  }
  public void setUserJobId(String userJobId) {
    this.userJobId = userJobId;
  }

  public String getUserRqData() {
    return userRqData;
  }
  public void setUserRqData(String userRqData) {
    this.userRqData = userRqData;
  }

  public String getUserSystemName() {
    return userSystemName;
  } 
  public void setUserSystemName(String userSystemName) {
    this.userSystemName = userSystemName;
  }
  
  public String getWaitDuration() {
    return waitDuration;
  }
  public void setWaitDuration(String waitDuration) {
    this.waitDuration = waitDuration;
  }

  public String getWaitTime() {
    return waitTime;
  }
  public void setWaitTime(String waitTime) {
    this.waitTime = waitTime;
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
    if (bufferReferences != null) 
      i.addDatum(new Datum("BUFFER_REFERENCES", String.valueOf(bufferReferences)));
    if (cpuTime != null) 
      i.addDatum(new Datum("CPU_TIME", cpuTime));
    if (currentStatus != null) 
      i.addDatum(new Datum("CURRENT_STATUS", currentStatus));
    if (dbCommand != null) 
      i.addDatum(new Datum("DB_COMMAND", dbCommand));
    if (dbid != null) 
      i.addDatum(new Datum("DBID", String.valueOf(dbid)));
    if (duration != null) 
      i.addDatum(new Datum("DURATION", duration));
    if (eojOk != null)
      i.addDatum(new Datum("EOJ_OK", String.valueOf(eojOk)));
    if (jobName != null) 
      i.addDatum(new Datum("JOB_NAME", jobName));
    if (lockValue != null) 
      i.addDatum(new Datum("LOCK_VALUE", lockValue));
    if (mufName != null) 
      i.addDatum(new Datum("MUF_NAME", mufName));
    if (mufplexOwner != null) 
      i.addDatum(new Datum("MUFPLEX_OWNER", mufplexOwner));
    if (optionalId != null) 
      i.addDatum(new Datum("OPTIONAL_ID", optionalId));
    if (ownerTask != null) 
      i.addDatum(new Datum("OWNER_TASK", String.valueOf(ownerTask)));
    if (physicalExcps != null) 
      i.addDatum(new Datum("PHYSICAL_EXCPS", String.valueOf(physicalExcps)));
    if (requestSeqNo != null) 
      i.addDatum(new Datum("REQUEST_SEQ_NO", String.valueOf(requestSeqNo)));
    if (runTime != null) 
      i.addDatum(new Datum("RUN_TIME", runTime));
    if (tableName != null) 
      i.addDatum(new Datum("TABLE_NAME", tableName));
    if (taskNumber != null) 
      i.addDatum(new Datum("TASK_NUMBER", String.valueOf(taskNumber)));
    if (trnSeqNo != null) 
      i.addDatum(new Datum("TRN_SEQ_NO", trnSeqNo));
    if (tsnDuration != null) 
      i.addDatum(new Datum("TSN_DURATION", tsnDuration));
    if (userPath != null) 
      i.addDatum(new Datum("USER_PATH", userPath));
    if (userJobId != null) 
      i.addDatum(new Datum("USER_JOBID", userJobId));
    if (userRqData != null) 
      i.addDatum(new Datum("USER_RQ_DATA", userRqData));
    if (userSystemName != null) 
      i.addDatum(new Datum("USER_SYSTEM_NAME", userSystemName));
    if (waitDuration != null)  
      i.addDatum(new Datum("WAIT_DURATION", waitDuration));
    if (waitTime != null) 
      i.addDatum(new Datum("WAIT_TIME", waitTime));

    return i;
  }

  private Integer bufferReferences;
  private String cpuTime;
  private String currentStatus;
  private String dbCommand;
  private Integer dbid;
  private String duration;
  private Character eojOk;
  private String jobName;
  private String lockValue;
  private String mufName;
  private String mufplexOwner;
  private String optionalId;
  private Integer ownerTask;
  private Integer physicalExcps;
  private Integer requestSeqNo;
  private String runTime;
  private Integer runUnit; 
  private String tableName;
  private Integer taskNumber;
  private String trnSeqNo;
  private String tsnDuration;
  private String userPath;
  private String userJobId;
  private String userRqData;
  private String userSystemName;
  private String waitDuration;
  private String waitTime;
}
