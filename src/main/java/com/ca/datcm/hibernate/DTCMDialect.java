package com.ca.datcm.hibernate;

import org.hibernate.dialect.Dialect;

import java.sql.ResultSet;
import java.sql.Types;

import org.hibernate.cfg.Environment;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.hibernate.type.descriptor.sql.SmallIntTypeDescriptor;

public class DTCMDialect extends Dialect {

  public DTCMDialect() {
    super();
  
    registerColumnType(Types.BIT, "smallint");
    registerColumnType(Types.BIGINT, "integer");
    registerColumnType(Types.SMALLINT, "smallint");
    registerColumnType(Types.TINYINT, "smallint");
    registerColumnType(Types.INTEGER, "integer");
    registerColumnType(Types.CHAR, "char(1)");
    registerColumnType(Types.VARCHAR, "varchar($l)");
    registerColumnType(Types.FLOAT, "float");
    registerColumnType(Types.DOUBLE, "double");
    registerColumnType(Types.DATE, "date");
    registerColumnType(Types.TIME, "time");
    registerColumnType(Types.TIMESTAMP, "timestamp");
    registerColumnType(Types.VARBINARY, "varchar($l) for bit data");
    registerColumnType(Types.NUMERIC, "varchar($l) for bit data");
    registerColumnType(Types.BLOB, "blob($l)");
    registerColumnType(Types.CLOB, "clob($l)");
    registerColumnType(Types.LONGVARCHAR, "long varchar");
    registerColumnType(Types.LONGVARBINARY, "long varchar for bit data");
    registerColumnType(Types.BINARY, "varchar($l) for bit data");
    registerColumnType(Types.BOOLEAN, "smallint");
  
    registerFunction("avg", new StandardSQLFunction("avg"));
    registerFunction("microsecond", new StandardSQLFunction("microsecond", StandardBasicTypes.INTEGER));
    registerFunction("second", new StandardSQLFunction("second", StandardBasicTypes.INTEGER));
    registerFunction("minute", new StandardSQLFunction("minute", StandardBasicTypes.INTEGER));
    registerFunction("hour", new StandardSQLFunction("hour", StandardBasicTypes.INTEGER));
    registerFunction("time", new StandardSQLFunction("time", StandardBasicTypes.TIME));
    registerFunction("day", new StandardSQLFunction("day", StandardBasicTypes.INTEGER));
    registerFunction("days", new StandardSQLFunction("days", StandardBasicTypes.LONG));
    registerFunction("date", new StandardSQLFunction("date", StandardBasicTypes.DATE));
    registerFunction("month", new StandardSQLFunction("month", StandardBasicTypes.INTEGER));
    registerFunction("year", new StandardSQLFunction("year", StandardBasicTypes.INTEGER));
  
    registerFunction("char", new StandardSQLFunction("char", StandardBasicTypes.CHARACTER));
    registerFunction("real", new StandardSQLFunction("float", StandardBasicTypes.FLOAT));
    registerFunction("integer", new StandardSQLFunction("integer", StandardBasicTypes.INTEGER));
  
    registerFunction("digits", new StandardSQLFunction("digits", StandardBasicTypes.STRING));
    registerFunction("upper", new StandardSQLFunction("upper"));
    registerFunction("ucase", new StandardSQLFunction("ucase"));
    registerFunction("lower", new StandardSQLFunction("lower"));
    registerFunction("lcase", new StandardSQLFunction("lcase"));
    registerFunction("ltrim", new StandardSQLFunction("ltrim"));
    registerFunction("rtrim", new StandardSQLFunction("rtrim"));
  
    registerFunction("substring", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
    registerFunction("bit_length", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "length(?1)*8"));
    registerFunction("trim", new SQLFunctionTemplate(StandardBasicTypes.STRING, "trim(?1 ?2 ?3 ?4)"));
  
    registerFunction("concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "", "||", ""));
  
    registerFunction("str", new SQLFunctionTemplate(StandardBasicTypes.STRING, "rtrim(char(?1))"));
  
    registerKeyword("current");
    registerKeyword("date");
    registerKeyword("time");
    registerKeyword("timestamp");
    registerKeyword("fetch");
    registerKeyword("first");
    registerKeyword("rows");
    registerKeyword("only");
  
    getDefaultProperties().setProperty(Environment.STATEMENT_BATCH_SIZE, NO_BATCH);
  }
  
  @Override
  public String getLowercaseFunction() {
    return "lcase";
  }
  
  @Override
  public String getAddColumnString() {
    return "add";
  }
  
  @Override
  public boolean dropConstraints() {
    return false;
  }
  
  @Override
  @SuppressWarnings("deprecation")
  public boolean supportsLimit() {
    return true;
  }
  
  @Override
  @SuppressWarnings("deprecation")
  public boolean supportsLimitOffset() {
    return false;
  }
  
  @Override
  @SuppressWarnings("deprecation")
  public boolean supportsVariableLimit() {
    return false;
  }
  
  @Override
  @SuppressWarnings("deprecation")
  public String getLimitString(String sql, int offset, int limit) {
    if (offset == 0)
      return sql + " fetch first " + limit + " rows only";
    else
      throw new UnsupportedOperationException("Limit offsets not supported by "
  		+ getClass().getName());
  }
  
  @Override
  public boolean supportsOuterJoinForUpdate() {
    return false;
  }
  
  @Override
  public boolean supportsExistsInSelect() {
    return false;
  }
  
  @Override
  public boolean supportsLockTimeouts() {
    return false;
  }
  
  @Override
  public String getSelectClauseNullString(int sqlType) {
    String literal;
    switch (sqlType) {
      case Types.VARCHAR: 
      case Types.CHAR:
        literal =  "' '";
        break;
      case Types.DATE:
        literal = "'1-1-1'";
      case Types.TIMESTAMP:
        literal = "'1-1-1 00:00:00.000000'";
        break;
      case Types.TIME:
        literal = "'00:00:00'";
      default:
        literal = "0";
    }
    return "value(" + literal + "," + literal + ")";
  }

  @Override
  public boolean supportsUnionAll() {
    return true;
  }

  @Override
  public String getCrossJoinSeparator() {
    return ", ";
  }

  @Override
  public boolean supportsTableCheck() {
    return false;
  }

  @Override
  public boolean supportsEmptyInList() {
    return false;
  }

  @Override
  public boolean supportsLobValueChangePropogation() {
    return false;
  }

  @Override 
  public boolean doesReadCommittedCauseWritersToBlockReaders() {
    return true;
  }

  @Override
  public boolean supportsTupleDistinctCounts() {
    return false;
  }

  @Override
  protected SqlTypeDescriptor getSqlTypeDescriptorOverride(int sqlCode) {
    return sqlCode == Types.BOOLEAN ? SmallIntTypeDescriptor.INSTANCE : super.getSqlTypeDescriptorOverride(sqlCode);
  }

  @Override 
  @SuppressWarnings("deprecation")
  public boolean supportsNotNullUnique() {
    return false;
  }

  @Override
  public String getNotExpression(String expression) {
    return "not (" + expression + ")";
  }
}

