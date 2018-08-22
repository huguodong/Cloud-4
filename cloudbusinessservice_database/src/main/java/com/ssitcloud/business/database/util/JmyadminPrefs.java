package com.ssitcloud.business.database.util;

import com.ssitcloud.database.entity.Server;

import java.util.*;

public class JmyadminPrefs implements java.io.Serializable {

  private Integer itemInPage = null;
  private String language = null;
  private Vector servers = new Vector();
  
  public JmyadminPrefs() {
  }
  
  public Integer getItemInPage() {
    return this.itemInPage;
  }
  
  public void setItemInPage(Integer itemInPage) {
    this.itemInPage = itemInPage;
  }
  
  public void addServer(Server server) {
    this.servers.add(server);
  }
  
  public void removeServer(Server server) {
    this.servers.remove(server);
  }
  
  public Vector getServers() {
    return this.servers;
  }
  
  public Server getServer(int index) {
    return (Server) this.servers.get(index);
  }
  
  public Server getServerById(Integer id) {
    int size = servers.size();
    Server server = null;
    for(int i=0;i<size;i++) {
      server = (Server) servers.get(i);
      if(server.getId().intValue() == id.intValue()) {
        return server;
      }
    }
    return null;
  }
  
  public String getLanguage() {
    return this.language;
  }
  
  public void setLanguage(String language) {
    this.language = language;
  }
}