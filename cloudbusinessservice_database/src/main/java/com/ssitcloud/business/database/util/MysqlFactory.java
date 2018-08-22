/*
 * $Id: MysqlFactory.java,v 1.2 2008/02/29 18:44:12 neos76 Exp $
 */
package com.ssitcloud.business.database.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ssitcloud.database.entity.MysqlDatabase;
import com.ssitcloud.database.entity.MysqlTable;
import com.ssitcloud.database.entity.Server;

public class MysqlFactory {
  
  public static MysqlDatabase loadDatabase(Server server,String databaseName) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    Logger logger = Logger.getLogger(MysqlFactory.class.getName());
    try {
      conn = ConnectionPool.getInstance().getConnection(server);
      stmt = conn.prepareStatement("SHOW TABLE STATUS FROM " + databaseName);
      rs = stmt.executeQuery();
      MysqlDatabase database = new MysqlDatabase(databaseName);
      MysqlTable table = null;
      while(rs.next()) {
        table = new MysqlTable(databaseName,rs.getString("name"));
        table.setType(rs.getString("engine"));
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
}
