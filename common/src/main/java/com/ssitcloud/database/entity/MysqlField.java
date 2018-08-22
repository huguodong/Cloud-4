/*
 * $Id: MysqlField.java,v 1.1 2003/08/06 08:41:00 neos76 Exp $
 */
package com.ssitcloud.database.entity;

/**
 * @author Amari
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

import java.io.Serializable;

public class MysqlField implements Serializable {

  private String name = null;
  private String type = null;
  private String size = null;;
  private boolean notNull = true;
  private String defaultValue = null;
  private String extra = null;
  private String attribute = null;
  private boolean key = false;
  private boolean unsigned = false;
  private boolean auto_increment = false;

  public MysqlField() {
  }

  public MysqlField(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSize() {
    return this.size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public boolean isNotNull() {
    return this.notNull;
  }

  public void setNotNull(boolean notNull) {
    this.notNull = notNull;
  }
  
  public void setunsigned(boolean b) {
	this.unsigned = b;
  }

  public boolean isUnsigned() {
	return this.unsigned;
  }
  
  public void setAuto_increment(boolean b) {
	this.auto_increment = b;
  }

  public boolean isAuto_increment() {
	return this.auto_increment;
  }

  /*
  public boolean isAutoIncrement() {
    return this.autoIncrement;
  }
  */

  /*
  public void setAutoIncrement(boolean autoIncrement) {
    this.autoIncrement = autoIncrement;
  }
  */

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public String getDefaultValue() {
    if(this.defaultValue!=null) {
      return this.defaultValue;
    }
    else {
      return "";
    }
  }

  public void setExtra(String extra) {
    this.extra = extra;
  }

  public String getExtra() {
    return this.extra;
  }

  public String getAttribute() {
    if(this.attribute!=null) {
      return this.attribute;
    }
    else {
      return "";
    }

  }

  public void setAttribute(String attribute) {
    this.attribute = attribute;
  }
  
  /*public Document toXml() {
    return null;
  }*/
	/**
	 * @return
	 */
	public boolean isKey() {
		return key;
	}

	/**
	 * @param b
	 */
	public void setKey(boolean b) {
		key = b;
	}
}