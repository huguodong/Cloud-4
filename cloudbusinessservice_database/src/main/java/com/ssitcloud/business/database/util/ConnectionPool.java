package com.ssitcloud.business.database.util;

import java.sql.*;

import com.ssitcloud.database.entity.Server;


public class ConnectionPool {

  private String host = null;
  private int port = 3306;
  private String username = null;
  private String password = null;

  private static ConnectionPool instance = null;

  public static ConnectionPool getInstance() {
    if(instance==null) {
      instance = new ConnectionPool();
    }
    return instance;
  }

  private ConnectionPool() {
    
    try {
      Class.forName("com.mysql.jdbc.Driver");
    }
    catch(Exception e) {
    }
    
  }

  public Connection getConnection(Server server) throws SQLException {
    return DriverManager.getConnection("jdbc:mysql://" + server.getHost() + ":" + server.getPort().intValue() + "/?user=" + server.getUser() + "&password=" + server.getPassword());
  }
  
  public Connection getConnection(Server server,String dbName) throws SQLException {
    return DriverManager.getConnection("jdbc:mysql://" + server.getHost() + ":" + server.getPort().intValue() + "/" + dbName + "?user=" + server.getUser() + "&password=" + server.getPassword());
  }
}
