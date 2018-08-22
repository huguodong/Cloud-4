package com.ssitcloud.business.database.util;

import java.sql.*;

import org.apache.log4j.*;

import com.ssitcloud.database.entity.MysqlDatabase;
import com.ssitcloud.database.entity.MysqlField;
import com.ssitcloud.database.entity.MysqlTable;
import com.ssitcloud.database.entity.Server;

public class JService implements java.io.Serializable {
  
  private static String LOG_CATEGORY = "org.jmyadmin";

  public JService() {
  }

  public static String getFieldTypesAsHtml(String type) {
    if(type==null || type.equals("")) type = "VARCHAR";
    StringBuffer buffer = new StringBuffer();
    buffer.append("<option value=\"VARCHAR\" " + (type.equalsIgnoreCase("VARCHAR") ? "selected" : "") + ">VARCHAR</option>");
    buffer.append("<option value=\"TINYINT\" " + (type.equalsIgnoreCase("TINYINT") ? "selected" : "") + ">TINYINT</option>");
    buffer.append("<option value=\"TEXT\" " + (type.equalsIgnoreCase("TEXT") ? "selected" : "") + ">TEXT</option>");
    buffer.append("<option value=\"DATE\" " + (type.equalsIgnoreCase("DATE") ? "selected" : "") + ">DATE</option>");
    buffer.append("<option value=\"SMALLINT\" " + (type.equalsIgnoreCase("SMALLINT") ? "selected" : "") + ">SMALLINT</option>");
    buffer.append("<option value=\"MEDIUMINT\" " + (type.equalsIgnoreCase("MEDIUMINT") ? "selected" : "") + ">MEDIUMINT</option>");
    buffer.append("<option value=\"INT\" " + (type.equalsIgnoreCase("INT") ? "selected" : "") + ">INT</option>");
    buffer.append("<option value=\"BIGINT\" " + (type.equalsIgnoreCase("BIGINT") ? "selected" : "") + ">BIGINT</option>");
    buffer.append("<option value=\"FLOAT\" " + (type.equalsIgnoreCase("FLOAT") ? "selected" : "") + ">FLOAT</option>");
    buffer.append("<option value=\"DOUBLE\" " + (type.equalsIgnoreCase("DOUBLE") ? "selected" : "") + ">DOUBLE</option>");
    buffer.append("<option value=\"DECIMAL\" " + (type.equalsIgnoreCase("DECIMAL") ? "selected" : "") + ">DECIMAL</option>");
    buffer.append("<option value=\"DATETIME\" " + (type.equalsIgnoreCase("DATETIME") ? "selected" : "") + ">DATETIME</option>");
    buffer.append("<option value=\"TIMESTAMP\" " + (type.equalsIgnoreCase("TIMESTAMP") ? "selected" : "") + ">TIMESTAMP</option>");
    buffer.append("<option value=\"TIME\" " + (type.equalsIgnoreCase("TIME") ? "selected" : "") + ">TIME</option>");
    buffer.append("<option value=\"YEAR\" " + (type.equalsIgnoreCase("YEAR") ? "selected" : "") + ">YEAR</option>");
    buffer.append("<option value=\"CHAR\" " + (type.equalsIgnoreCase("CHAR") ? "selected" : "") + ">CHAR</option>");
    buffer.append("<option value=\"TINYBLOB\" " + (type.equalsIgnoreCase("TINYBLOB") ? "selected" : "") + ">TINYBLOB</option>");
    buffer.append("<option value=\"TINYTEXT\" " + (type.equalsIgnoreCase("TINYTEXT") ? "selected" : "") + ">TINYTEXT</option>");
    buffer.append("<option value=\"BLOB\" " + (type.equalsIgnoreCase("BLOB") ? "selected" : "") + ">BLOB</option>");
    buffer.append("<option value=\"MEDIUMBLOB\" " + (type.equalsIgnoreCase("MEDIUMBLOB") ? "selected" : "") + ">MEDIUMBLOB</option>");
    buffer.append("<option value=\"MEDIUMTEXT\" " + (type.equalsIgnoreCase("MEDIUMTEXT") ? "selected" : "") + ">MEDIUMTEXT</option>");
    buffer.append("<option value=\"LONGBLOB\" " + (type.equalsIgnoreCase("LONGBLOB") ? "selected" : "") + ">LONGBLOB</option>");
    buffer.append("<option value=\"LONGTEXT\" " + (type.equalsIgnoreCase("LONGTEXT") ? "selected" : "") + ">LONGTEXT</option>");
    buffer.append("<option value=\"ENUM\" " + (type.equalsIgnoreCase("ENUM") ? "selected" : "") + ">ENUM</option>");
    buffer.append("<option value=\"SET\" " + (type.equalsIgnoreCase("SET") ? "selected" : "") + ">SET</option>");
    return buffer.toString();
  }
  
  public static String getNotNullAsHtml(boolean value) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("<option value=\"NOT NULL\" " + (value ? "selected" : "") + ">not null</option>\n");
    buffer.append("<option value=\"\" " + (!value ? "selected" : "") + ">null</option>\n");
    return buffer.toString();
  }
  
  public static String getExtraAsHtml(String value) {
    if(value==null) value="";
    StringBuffer buffer = new StringBuffer();
    buffer.append("<option value=\"\" " + (value.equals("") ? "selected" : "") + "></option>\n");
    buffer.append("<option value=\"AUTO_INCREMENT\" " + (value.equalsIgnoreCase("auto_increment") ? "selected" : "") + ">auto_increment</option>\n");
    return buffer.toString();
  }
  
  /*
  public static String normalizeSQL(String sql) {
    String result = StringUtil.replace(sql,"\"","\\\"");
    result = StringUtil.replace(sql,"\'","\\\'");
    return result;
  }
  */
  
  public static String getIndexTypes(String value) {
    if(value==null) value="";
    StringBuffer buffer = new StringBuffer();
    buffer.append("<option value=\"PRIMARY\" " + (value.equalsIgnoreCase("PRIMARY") ? "selected" : "") + ">PRIMARY</option>\n");
    buffer.append("<option value=\"INDEX\" " + (value.equalsIgnoreCase("INDEX") ? "selected" : "") + ">INDEX</option>\n");
    buffer.append("<option value=\"UNIQUE\" " + (value.equalsIgnoreCase("UNIQUE") ? "selected" : "") + ">UNIQUE</option>\n");
    buffer.append("<option value=\"FULLTEXT\" " + (value.equalsIgnoreCase("FULLTEXT") ? "selected" : "") + ">FULLTEXT</option>\n");
    return buffer.toString();
  }
  
  public static String getTableFields(MysqlTable table,String value) {
    if(value==null) value = "";
    StringBuffer buffer = new StringBuffer();
    MysqlField field = null;
    int size = table.getFields().size();
    for(int i=0;i<size;i++) {
      field = (MysqlField) table.getField(i);
      buffer.append("<option value=\"" + field.getName() + "\" " + (value.equalsIgnoreCase(field.getName()) ? "selected" : "") + ">" + field.getName() + "</option>");
    }
    return buffer.toString();
  }
  
  public static int getTableRows(Server server,String tableName,String databaseName) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    Logger logger = Logger.getLogger(JService.LOG_CATEGORY);
    
    try {
      conn = ConnectionPool.getInstance().getConnection(server,databaseName);
      stmt = conn.prepareStatement("SELECT COUNT(*) FROM " + tableName);
      rs = stmt.executeQuery();
      if(rs.next()) {
        return rs.getInt(1);
      }
      return 0;
    }
    catch (SQLException e){
      logger.error(e);
      return 0;
    }
    finally {
      try {
        if(rs!=null) rs.close();
        if(stmt!=null) stmt.close();
        if(conn!=null) conn.close();
      }
      catch(SQLException e){
        logger.error(e);
      }
    }
  }
  
  public static MysqlDatabase loadDatabase(Server server,String databaseName) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    Logger logger = Logger.getLogger(JService.LOG_CATEGORY);
    try {
      conn = ConnectionPool.getInstance().getConnection(server);
      stmt = conn.prepareStatement("SHOW TABLE STATUS FROM " + databaseName);
      rs = stmt.executeQuery();
      MysqlDatabase database = new MysqlDatabase(databaseName);
      MysqlTable table = null;
      while(rs.next()) {
        table = new MysqlTable(databaseName,rs.getString("name"));
        table.setType(rs.getString("type"));
        table.setRows(rs.getInt("rows"));
        table.setComment(rs.getString("comment"));
        database.addTable(table);
      }
      return database;
    }
    catch(SQLException e) {
      logger.error(e);
      return null;
    }
    finally {
      try {
        if(rs!=null) rs.close();
        if(stmt!=null) stmt.close();
        if(conn!=null) conn.close();
      }
      catch(SQLException e){
        logger.error(e);
      }
    }
  }
  
  public static String getTablesAsHtml(Server server,String databaseName,String table) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    Logger logger = Logger.getLogger(JService.LOG_CATEGORY);
    
    StringBuffer buffer = new StringBuffer();
    try {
      conn = ConnectionPool.getInstance().getConnection(server,databaseName);
      stmt = conn.prepareStatement("SHOW TABLES");
      rs = stmt.executeQuery();
      while(rs.next()) {
        if(rs.getString(1).equals(table)) {
          buffer.append("<option selected value=\"" + rs.getString(1) + "\">" + rs.getString(1) + "</option>");
        }
        else {
          buffer.append("<option value=\"" + rs.getString(1) + "\">" + rs.getString(1) + "</option>");
        }
      }
      return buffer.toString();
    }
    catch(SQLException e) {
      logger.error(e);
      return null;
    }
    finally {
      try {
        if(rs!=null) rs.close();
        if(stmt!=null) stmt.close();
        if(conn!=null) conn.close();
      }
      catch(SQLException e){
        logger.error(e);
      }
    }
  }
  
  public static String getFieldsAsHtml(Server server,String databaseName,String tableName,String field) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    Logger logger = Logger.getLogger(JService.LOG_CATEGORY);
    
    StringBuffer buffer = new StringBuffer();
    try {
      conn = ConnectionPool.getInstance().getConnection(server,databaseName);
      stmt = conn.prepareStatement("DESCRIBE " + tableName);
      rs = stmt.executeQuery();
      while(rs.next()) {
        buffer.append("<option value=\"" + rs.getString("field") + "\">" + rs.getString("field") + "(" + rs.getString("type") + ")</option>");
      }
      return buffer.toString();
    }
    catch(SQLException e) {
      logger.error(e);
      return null;
    }
    finally {
      try {
        if(rs!=null) rs.close();
        if(stmt!=null) stmt.close();
        if(conn!=null) conn.close();
      }
      catch(SQLException e){
        logger.error(e);
      }
    }
  }
}