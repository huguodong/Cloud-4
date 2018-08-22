/*
 * $Id: MysqlTable.java,v 1.1 2003/08/06 08:41:00 neos76 Exp $
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

public class MysqlTable implements Serializable {
  
  public static final int ISAM = 0;
  public static final int MYISAM = 1;
  public static final int INNODB = 2;
  
  private String databaseName = null;
  private String name = null;
  private int rows = 0;
  private String type = null;
  private String comment = null;
  private List<MysqlField> fields = new ArrayList<>();
  private List<MysqlKey> keys = new ArrayList<>();
  private List<MysqlIndex> indexs = new ArrayList<>();
  private boolean primaryKey = false;
  private Integer server_id = 0;
  
	public MysqlTable() {

	}

	public MysqlTable(String name) {
		this.name = name;
	}

	public MysqlTable(String databaseName, String name) {
		this.databaseName = databaseName;
		this.name = name;
	}
	
	public MysqlField getField(String fieldName){
		for(MysqlField field : fields){
			if(fieldName.equals(field.getName())){
				return field;
			}
		}
		return null;
	}
	
	public MysqlKey getKey(String keyName){
		for(MysqlKey key : keys){
			if(keyName.equals(key.getKey_name())){
				return key;
			}
		}
		return null;
	}
	
	public MysqlIndex getIndex(String indexName){
		for(MysqlIndex index : indexs){
			if(indexName.equals(index.getKeyName())){
				return index;
			}
		}
		return null;
	}
	/**
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param string
	 */
	public void setComment(String string) {
		comment = string;
	}

	/**
	 * @param string
	 */
	public void setDatabaseName(String string) {
		databaseName = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param i
	 */
	public void setRows(int i) {
		rows = i;
	}

	/**
	 * @param string
	 */
	public void setType(String string) {
		type = string;
	}

	public void addField(MysqlField field) {
		fields.add(field);
	}

	public void removeField(MysqlField field) {
		fields.remove(field);
	}

	public MysqlField getField(int i) {
		return (MysqlField) fields.get(i);
	}

	/*
	 * public Document toXml() { return null; }
	 */

	public int getServer_id() {
		return server_id;
	}

	public void setServer_id(int server_id) {
		this.server_id = server_id;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean b) {
		primaryKey = b;
	}

	public List<MysqlField> getFields() {
		return fields;
	}

	public void setFields(List<MysqlField> fields) {
		this.fields = fields;
	}

	public List<MysqlKey> getKeys() {
		return keys;
	}

	public void setKeys(List<MysqlKey> keys) {
		this.keys = keys;
	}

	public void addKey(MysqlKey key) {
		keys.add(key);
	}

	public void removeKey(MysqlKey key) {
		keys.remove(key);
	}

	public List<MysqlIndex> getIndexs() {
		return indexs;
	}

	public void setIndexs(List<MysqlIndex> indexs) {
		this.indexs = indexs;
	}

	public void addIndex(MysqlIndex index) {
		indexs.add(index);
	}

	public void removeIndex(MysqlIndex index) {
		indexs.remove(index);
	}

}