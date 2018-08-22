/*
 * Created on 24-lug-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
 
package com.ssitcloud.database.entity;

/**
 * @author Amari
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MysqlDatabase implements Serializable {
  
  private String name = null;
  private String description = null;
  private List<MysqlTable> tables = new ArrayList<>();
  
  public MysqlDatabase() {
    
  }
  
  public MysqlDatabase(String name) {
    this.name = name;
  }

  public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public List<MysqlTable> getTables() {
	return tables;
}

public void setTables(List<MysqlTable> tables) {
	this.tables = tables;
}

public void addTable(MysqlTable table) {
    tables.add(table);
  }
  
  /*public Document toXml() {
    return null;
  }*/
}