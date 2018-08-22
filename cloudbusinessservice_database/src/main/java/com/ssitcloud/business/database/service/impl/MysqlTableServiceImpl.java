package com.ssitcloud.business.database.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.common.util.LogUtils;
import com.ssitcloud.business.database.service.MysqlTableService;
import com.ssitcloud.business.database.util.ConnectionPool;
import com.ssitcloud.business.database.util.ServerUtil;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.database.entity.MysqlField;
import com.ssitcloud.database.entity.MysqlIndex;
import com.ssitcloud.database.entity.MysqlKey;
import com.ssitcloud.database.entity.MysqlTable;
import com.ssitcloud.database.entity.Server;

@Service
public class MysqlTableServiceImpl extends BasicServiceImpl implements MysqlTableService {

	@Resource(name = "dataBaseMenus")
    private ConcurrentMap<String, List<Server>> dataBaseMenus;
	
	@Override
	public MysqlTable tableField(String req) {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	    	MysqlTable table = JsonUtils.fromJson(req, MysqlTable.class);
	    	List<MysqlField> list = new ArrayList<>();
	    	table.setFields(list);
	        Server server = getMysqlServer(table.getServer_id());
	        conn = ConnectionPool.getInstance().getConnection(server,table.getDatabaseName());
	        
		    //查询字段
	        stmt = conn.prepareStatement("select column_name,column_key,column_type,extra,column_default,column_comment,is_nullable from INFORMATION_SCHEMA.Columns where table_name='" + table.getName()+"' and TABLE_SCHEMA ='" + table.getDatabaseName()+"'");
	        rs = stmt.executeQuery();
	        MysqlField field = null;
	        int isPrimaryKeyFlag = 0;
			while (rs.next()) {
				String name = rs.getString("column_name");
				field = new MysqlField(name);
				 if(rs.getString("column_key") != null){
				 if(rs.getString("column_key").equals("PRI")){ 
					  field.setKey(true);
					  isPrimaryKeyFlag = 1;
				  }else{
					  field.setKey(false); 
					  } 
				  }
				 
				StringTokenizer tokenizer = new StringTokenizer(rs.getString("column_type"), "(");
				int count = tokenizer.countTokens();
				switch (count) {
				case 0:
					field.setType(rs.getString("column_type"));
					field.setSize("");
					break;
				case 1:
					field.setType(rs.getString("column_type"));
					field.setSize("");
					break;
				case 2:
					field.setType(tokenizer.nextToken());
					field.setSize(new StringTokenizer(tokenizer.nextToken(),")").nextToken());
					break;
				default:
					LogUtils.error("");
				}
				if (rs.getString("column_type").indexOf(" ") != -1) {
					String[] tokens = rs.getString("column_type").split(" ");
					if("unsigned".equals(tokens[1])){
						field.setunsigned(true);
					}
				}
				if(rs.getString("extra") != null && !"".equals(rs.getString("extra"))){
					if(rs.getString("extra").equals("auto_increment")){
						field.setAuto_increment(true);
					}
				}
				field.setDefaultValue(rs.getString("column_default"));
				field.setAttribute(rs.getString("column_comment"));
				if (rs.getString("is_nullable") != null) {
					if (rs.getString("is_nullable").equals("YES"))
						field.setNotNull(true);
					else if (rs.getString("is_nullable").equals("NO"))
						field.setNotNull(false);
					else
						field.setNotNull(true);
				}
				table.addField(field);
			}
			if(isPrimaryKeyFlag == 1){
				table.setPrimaryKey(true);
			}
			//other(server, table);
			//getTableComment(server, table);
			return table;
	    }catch(SQLException e) {
	      LogUtils.error(e);
	    }
	    finally {
	      try {
	        if(rs!=null) rs.close();
	        if(stmt!=null) stmt.close();
	        if(conn!=null) conn.close();
	      }
	      catch(SQLException e){
	    	  LogUtils.error(e);
	      }
	    }
		return null;
	}
	
	@Override
	public MysqlTable tableIndex(String req) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			MysqlTable table = JsonUtils.fromJson(req, MysqlTable.class);
			List<MysqlIndex> list = new ArrayList<>();
			table.setIndexs(list);
			Server server = getMysqlServer(table.getServer_id());
			conn = ConnectionPool.getInstance().getConnection(server,
					table.getDatabaseName());
			stmt = conn.prepareStatement("SHOW KEYS FROM `" + table.getName() + "` where key_name <> 'PRIMARY'");
			rs = stmt.executeQuery();
			Map<String, MysqlIndex> map = new HashMap<>();
			String type = null;
			while (rs.next()) {
				if (rs.getString("key_name").equalsIgnoreCase("primary")) {
					type = "PRIMARY";
					table.setPrimaryKey(true);
				} else {
					if (rs.getInt("non_unique") == 0) {
						type = "UNIQUE";
					} else if (rs.getInt("non_unique") == 1) {
						type = "INDEX";
					}
				}
				String key_name = rs.getString("key_name");
				String column_name = rs.getString("column_name");
				if (map.get(key_name) != null) {
					MysqlIndex enty = map.get(key_name);
					enty.addField(column_name);

				} else {
					MysqlIndex index = new MysqlIndex();
					index.setKeyName(key_name);
					index.setType(type);
					index.setCardinality(rs.getInt("cardinality"));
					index.addField(rs.getString("column_name"));
					map.put(index.getKeyName(), index);
				}
			}
			for (Map.Entry<String, MysqlIndex> entry : map.entrySet()) {
				table.addIndex(entry.getValue());
			}
			return table;
		} catch (SQLException e) {
			LogUtils.error(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				LogUtils.error(e);
			}
		}
		return null;
	}
	
	@Override
	public MysqlTable tableKey(String req) {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	    	MysqlTable table = JsonUtils.fromJson(req, MysqlTable.class);
	    	List<MysqlKey> list = new ArrayList<>();
	    	table.setKeys(list);
	    	Server server = getMysqlServer(table.getServer_id());
	        conn = ConnectionPool.getInstance().getConnection(server,table.getDatabaseName());
	        String sql = "SELECT C.TABLE_SCHEMA, C.REFERENCED_TABLE_SCHEMA, C.REFERENCED_TABLE_NAME, C.REFERENCED_COLUMN_NAME, C.TABLE_NAME, C.COLUMN_NAME, C.CONSTRAINT_NAME, R.UPDATE_RULE, R.DELETE_RULE "
	        		+" FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE C "
	        		+" LEFT JOIN INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS R ON R.TABLE_NAME = C.TABLE_NAME AND R.CONSTRAINT_NAME = C.CONSTRAINT_NAME AND R.REFERENCED_TABLE_NAME = C.REFERENCED_TABLE_NAME "
	        		+" WHERE C.REFERENCED_TABLE_NAME IS NOT NULL "
	        		+" AND C.TABLE_SCHEMA = '"+table.getDatabaseName()+"'"
	        		+" AND C.TABLE_NAME = '"+table.getName()+"'";
	        stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			MysqlKey key = null;
			while (rs.next()) {
				key = new MysqlKey();
				key.setKey_name(rs.getString("CONSTRAINT_NAME"));
				key.setField(rs.getString("COLUMN_NAME"));
				key.setRef_database(rs.getString("REFERENCED_TABLE_SCHEMA"));
				key.setRef_table(rs.getString("REFERENCED_TABLE_NAME"));
				key.setRef_field(rs.getString("REFERENCED_COLUMN_NAME"));
				key.setUpdate_rule(rs.getString("UPDATE_RULE"));
				key.setDelete_rule(rs.getString("DELETE_RULE"));
				table.addKey(key);
		      }
			return table;
		} catch (SQLException e) {
			LogUtils.error(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				LogUtils.error(e);
			}
		}
		return null;
	}
	
	
	@Override
	public Boolean updateField(String req) throws SQLException {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    
	    try {
	    	JSONObject jsonObject =JSONObject.fromObject(req);
	    	MysqlTable table = JsonUtils.fromJson(jsonObject.getString("json1"), MysqlTable.class);
	    	MysqlField oldfield = JsonUtils.fromJson(jsonObject.getString("json2"), MysqlField.class);
	    	MysqlField field = JsonUtils.fromJson(jsonObject.getString("json3"), MysqlField.class);
	    	Server server = getMysqlServer(table.getServer_id());
	        sql = editFieldSQL(table.getName(),oldfield.getName(),field.getName(),field.getType(),field.getSize(),field.getAttribute(),field.getDefaultValue(),field.getExtra(),field.isNotNull(),field.isUnsigned(),field.isAuto_increment());
	        conn = ConnectionPool.getInstance().getConnection(server,table.getDatabaseName());
	        stmt = conn.prepareStatement(sql);
	        stmt.executeUpdate();
	        if(oldfield.isKey()==field.isKey()){
	        	return true;
	        }else{
	        	
	        	if(!table.isPrimaryKey()){
	        		//表没有主键
	        		//增加主键
		        	sql = "ALTER TABLE `" + table.getName() + "` ADD PRIMARY KEY (`" + field.getName() + "`);";
		        	stmt = conn.prepareStatement(sql);
		        	stmt.executeUpdate();
	        	}else{
	        		//表存在主键
	        		//增加主键字段
	        		if(field.isKey()){
	        			String primaryStr = "";
	        			for(MysqlField ety:table.getFields()){
	        				if(ety.isKey() && !field.getName().equals(ety.getName())){
	        					primaryStr = primaryStr + ety.getName() +",";
	        				}
	        			}
	        			primaryStr = primaryStr +field.getName() ;
		        		//增加主键
			        	sql = "ALTER TABLE `" + table.getName() + "` DROP PRIMARY KEY ,ADD PRIMARY KEY (`" + primaryStr + "`);";
			        	stmt = conn.prepareStatement(sql);
			        	stmt.executeUpdate();
			        }else{
			        	String primaryStr = "";
	        			for(MysqlField ety:table.getFields()){
	        				if(ety.isKey() && !oldfield.getName().equals(ety.getName())){
	        					primaryStr = primaryStr + ety.getName() +",";
	        				}
	        			}
	        			if("".equals(primaryStr)){
	        				sql = "ALTER TABLE `" + table.getName() + "` DROP PRIMARY KEY ";
				        	stmt = conn.prepareStatement(sql);
				        	stmt.executeUpdate();
	        			}else{
	        				primaryStr = primaryStr.substring(0, primaryStr.length()-1);
		        			//增加主键
				        	sql = "ALTER TABLE `" + table.getName() + "` DROP PRIMARY KEY ,ADD PRIMARY KEY (`" + primaryStr + "`);";
				        	stmt = conn.prepareStatement(sql);
				        	stmt.executeUpdate();
	        			}
			        }
	        	}
	        }
        	return true;
	    }catch(SQLException e) {
	      throw new SQLException(e);
	    }
	    finally {
	      try {
	        if(rs!=null) rs.close();
	        if(stmt!=null) stmt.close();
	        if(conn!=null) conn.close();
	      }
	      catch(SQLException e){
	    	  throw new SQLException(e);
	      }
	    }
	}
	
	private String editFieldSQL(String tableName,String name,String newFieldName,String type,String size,String fieldAttribute,String defaultValue,String extra,Boolean notNull,Boolean unsigned,Boolean auto_increment) {
		StringBuffer buffer = new StringBuffer();
	    buffer.append("ALTER TABLE `" + tableName + "` CHANGE ");
	      buffer.append(" `" + name + "` `" + newFieldName + "` " + type);
	      if(size!=null && !size.equals("")) {
	        buffer.append("(" + size + ")");
	      }
	      if(unsigned){
	    	  buffer.append(" UNSIGNED");
	      }
	      if(auto_increment){
	    	  buffer.append(" AUTO_INCREMENT");
	      }
	      if(fieldAttribute!=null && !fieldAttribute.equals("")) {
	        buffer.append(" COMMENT '" + fieldAttribute+"'");
	      }
	      if(notNull) {
	        buffer.append(" NOT NULL");
	      }
	      if(defaultValue!=null && !defaultValue.equals("")) {
	        buffer.append(" DEFAULT '" + defaultValue + "'");
	      }
	      if(extra!=null && !extra.equals("")) {
	        buffer.append(" " + extra);
	      }
	      buffer.append(";");
	    return buffer.toString();
	  }
	
	@Override
	public Boolean updateIndex(String req) throws SQLException {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    
	    try {
	    	JSONObject jsonObject =JSONObject.fromObject(req);
	    	MysqlTable table = JsonUtils.fromJson(jsonObject.getString("json1"), MysqlTable.class);
	    	MysqlIndex oldindex = JsonUtils.fromJson(jsonObject.getString("json2"), MysqlIndex.class);
	    	MysqlIndex index = JsonUtils.fromJson(jsonObject.getString("json3"), MysqlIndex.class);
	    	Server server = getMysqlServer(table.getServer_id());
	        sql = editIndexSQL(table.getName(),oldindex.getKeyName(),index.getKeyName(),index.getType(),index.getColumnStr());
	        conn = ConnectionPool.getInstance().getConnection(server,table.getDatabaseName());
	        stmt = conn.prepareStatement(sql);
	        stmt.executeUpdate();
        	return true;
	    }catch(SQLException e) {
	    	throw new SQLException(e);
	    }
	    finally {
	      try {
	        if(rs!=null) rs.close();
	        if(stmt!=null) stmt.close();
	        if(conn!=null) conn.close();
	      }
	      catch(SQLException e){
	    	  throw new SQLException(e);
	      }
	    }
	}
	
	private String editIndexSQL(String tableName,String name,String newIndexName,String type,String columnStr) {
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("ALTER TABLE `" + tableName +"` DROP INDEX `"+name+ "`, ADD "+type+" `"+newIndexName+"` ("+returnStr(columnStr)+")");
        buffer.append(";");
	    return buffer.toString();
	  }
	
	
	@Override
	public Boolean updateconstraint(String req) throws SQLException {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    
	    try {
	    	JSONObject jsonObject =JSONObject.fromObject(req);
	    	MysqlTable table = JsonUtils.fromJson(jsonObject.getString("json1"), MysqlTable.class);
	    	MysqlKey oldKey = JsonUtils.fromJson(jsonObject.getString("json2"), MysqlKey.class);
	    	MysqlKey key = JsonUtils.fromJson(jsonObject.getString("json3"), MysqlKey.class);
	    	Server server = getMysqlServer(table.getServer_id());
	        //sql = editConstraintSQL(table.getName(),oldKey.getKey_name(),key.getKey_name(),key.getField(),key.getRef_database(),key.getRef_table(),key.getRef_field(),key.getDelete_rule(),key.getUpdate_rule());
	        try{
	        	sql = "ALTER TABLE `" + table.getName() +"` DROP FOREIGN KEY `"+oldKey.getKey_name()+ "`";
		        conn = ConnectionPool.getInstance().getConnection(server,table.getDatabaseName());
		        stmt = conn.prepareStatement(sql);
		        stmt.executeUpdate();
	        }catch(Exception e){
	        	throw new SQLException(e);
	        }
	        try{
	        	sql =  "ALTER TABLE `" + table.getName() +"` ADD CONSTRAINT `"+key.getKey_name()+"` FOREIGN KEY ("+returnStr(key.getField())+") REFERENCES `"+key.getRef_database()+"`.`"+key.getRef_table()+"`("+returnStr(key.getRef_field())+") ON DELETE "+key.getDelete_rule()+" ON UPDATE "+key.getUpdate_rule();
		        stmt = conn.prepareStatement(sql);
		        stmt.executeUpdate();
	        }catch(Exception e){
	        	LogUtils.error("添加外键失败");
	        	//还原之前删除操作
	        	sql =  "ALTER TABLE `" + table.getName() +"` ADD CONSTRAINT `"+oldKey.getKey_name()+"` FOREIGN KEY ("+returnStr(oldKey.getField())+") REFERENCES `"+oldKey.getRef_database()+"`.`"+oldKey.getRef_table()+"`("+returnStr(oldKey.getRef_field())+") ON DELETE "+oldKey.getDelete_rule()+" ON UPDATE "+oldKey.getUpdate_rule();
		        stmt = conn.prepareStatement(sql);
		        stmt.executeUpdate();
		        throw new SQLException(e);
	        }
        	return true;
	    }catch(SQLException e) {
	    	throw new SQLException(e);
	    }
	    finally {
	      try {
	        if(rs!=null) rs.close();
	        if(stmt!=null) stmt.close();
	        if(conn!=null) conn.close();
	      }
	      catch(SQLException e){
	    	 throw new SQLException(e);
	      }
	    }
	}
	
	private String editConstraintSQL(String tableName,String name,String newName,String field,String ref_database,String ref_table,String ref_field,String delete_relation,String update_relation) {
	    StringBuffer buffer = new StringBuffer();
	    //alter table test2 drop foreign key old_name,add constraint new_name foreign key (a) references test1(id) ON UPDATE CASCADE ON DELETE CASCADE;
	    //ADD CONSTRAINT `FK_BOOK_INFO_1` FOREIGN KEY (`coupon_group_id`) REFERENCES `coupon_group` (`id`);
	    buffer.append("ALTER TABLE `" + tableName +"` DROP FOREIGN KEY `"+name+ "`, ADD CONSTRAINT `"+newName+"` FOREIGN KEY (`"+field+"`) REFERENCES `"+ref_database+"`.`"+ref_table+"`(`"+ref_field+"`) ON DELETE "+delete_relation+" ON UPDATE "+update_relation);
        buffer.append(";");
	    return buffer.toString();
	  }
	
	
	
	@Override
	public Boolean deleteField(String req) throws SQLException {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    
	    try {
	    	JSONObject jsonObject =JSONObject.fromObject(req);
	    	MysqlTable table = JsonUtils.fromJson(jsonObject.getString("json1"), MysqlTable.class);
	    	JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("json2"));
	    	List<MysqlField> fields = jsonArray.toList(jsonArray, MysqlField.class);
	    	Server server = getMysqlServer(table.getServer_id());
	        for(MysqlField field : fields){
	        	sql = deleteFieldSQL(table.getName(),field.getName());
		        conn = ConnectionPool.getInstance().getConnection(server,table.getDatabaseName());
		        stmt = conn.prepareStatement(sql);
		        stmt.executeUpdate();
		        
	        	if(table.isPrimaryKey()){
	        		//表存在主键
	        		//增加主键字段
	        		if(field.isKey()){
			        	String primaryStr = "";
	        			for(MysqlField ety:table.getFields()){
	        				if(ety.isKey() && !field.getName().equals(ety.getName())){
	        					primaryStr = primaryStr + ety.getName() +",";
	        				}
	        			}
	        			if("".equals(primaryStr)){
	        				sql = "ALTER TABLE `" + table.getName() + "` DROP PRIMARY KEY ";
				        	stmt = conn.prepareStatement(sql);
				        	stmt.executeUpdate();
	        			}else{
	        				primaryStr = primaryStr.substring(0, primaryStr.length()-1);
		        			//增加主键
				        	sql = "ALTER TABLE `" + table.getName() + "` DROP PRIMARY KEY ,ADD PRIMARY KEY (`" + primaryStr + "`);";
				        	stmt = conn.prepareStatement(sql);
				        	stmt.executeUpdate();
	        			}
			        }
	        	}
	        }
        	return true;
	    }catch(SQLException e) {
	      throw new SQLException(e);
	    }
	    finally {
	      try {
	        if(rs!=null) rs.close();
	        if(stmt!=null) stmt.close();
	        if(conn!=null) conn.close();
	      }
	      catch(SQLException e){
	    	  throw new SQLException(e);
	      }
	    }
	}
	
	//  alter table t2 drop column c
	private String deleteFieldSQL(String tableName,String name) {
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("ALTER TABLE `" + tableName + "` DROP COLUMN `"+name+"`");
        buffer.append(";");
	    return buffer.toString();
	  }
	
	
	@Override
	public Boolean deleteIndex(String req) throws SQLException {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    
	    try {
	    	JSONObject jsonObject =JSONObject.fromObject(req);
	    	MysqlTable table = JsonUtils.fromJson(jsonObject.getString("json1"), MysqlTable.class);
	    	JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("json2"));
	    	List<MysqlIndex> indexs = jsonArray.toList(jsonArray, MysqlIndex.class);
	    	Server server = getMysqlServer(table.getServer_id());
	        for(MysqlIndex index : indexs){
	        	sql = deleteIndexSQL(table.getName(),index.getKeyName());
		        conn = ConnectionPool.getInstance().getConnection(server,table.getDatabaseName());
		        stmt = conn.prepareStatement(sql);
		        stmt.executeUpdate();
	        }
        	return true;
	    }catch(SQLException e) {
	    	throw new SQLException(e);
	    }
	    finally {
	      try {
	        if(rs!=null) rs.close();
	        if(stmt!=null) stmt.close();
	        if(conn!=null) conn.close();
	      }
	      catch(SQLException e){
	    	  throw new SQLException(e);
	      }
	    }
	}
	
	private String deleteIndexSQL(String tableName,String name) {
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("ALTER TABLE `" + tableName +"` DROP INDEX `"+name+ "`");
        buffer.append(";");
	    return buffer.toString();
	  }
	
	
	@Override
	public Boolean deleteConstraint(String req) throws SQLException {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    
	    try {
	    	JSONObject jsonObject =JSONObject.fromObject(req);
	    	MysqlTable table = JsonUtils.fromJson(jsonObject.getString("json1"), MysqlTable.class);
	    	JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("json2"));
	    	List<MysqlKey> keys = jsonArray.toList(jsonArray, MysqlKey.class);
	    	Server server = getMysqlServer(table.getServer_id());
	        for(MysqlKey key : keys){
	        	sql = "ALTER TABLE `" + table.getName() +"` DROP FOREIGN KEY `"+key.getKey_name()+ "`";
		        conn = ConnectionPool.getInstance().getConnection(server,table.getDatabaseName());
		        stmt = conn.prepareStatement(sql);
		        stmt.executeUpdate();
	        }
        	return true;
	    }catch(SQLException e) {
	    	throw new SQLException(e);
	    }
	    finally {
	      try {
	        if(rs!=null) rs.close();
	        if(stmt!=null) stmt.close();
	        if(conn!=null) conn.close();
	      }
	      catch(SQLException e){
	    	 throw new SQLException(e);
	      }
	    }
	}
	
	
	@Override
	public Boolean addField(String req) throws SQLException {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    
	    try {
	    	JSONObject jsonObject =JSONObject.fromObject(req);
	    	MysqlTable table = JsonUtils.fromJson(jsonObject.getString("json1"), MysqlTable.class);
	    	MysqlField field = JsonUtils.fromJson(jsonObject.getString("json2"), MysqlField.class);
	    	Server server = getMysqlServer(table.getServer_id());
	        sql = addFieldSQL(table.getName(),field.getName(),field.getType(),field.getSize(),field.getAttribute(),field.getDefaultValue(),field.getExtra(),field.isNotNull());
	        conn = ConnectionPool.getInstance().getConnection(server,table.getDatabaseName());
	        stmt = conn.prepareStatement(sql);
	        stmt.executeUpdate();
	        	
        	if(!table.isPrimaryKey()){
        		//表没有主键
        		//增加主键
	        	sql = "ALTER TABLE `" + table.getName() + "` ADD PRIMARY KEY (`" + field.getName() + "`);";
	        	stmt = conn.prepareStatement(sql);
	        	stmt.executeUpdate();
        	}else{
        		//表存在主键
        		//增加主键字段
        		if(field.isKey()){
        			String primaryStr = "";
        			for(MysqlField ety:table.getFields()){
        				if(ety.isKey() && !field.getName().equals(ety.getName())){
        					primaryStr = primaryStr + ety.getName() +",";
        				}
        			}
        			primaryStr = primaryStr +field.getName() ;
	        		//增加主键
		        	sql = "ALTER TABLE `" + table.getName() + "` DROP PRIMARY KEY ,ADD PRIMARY KEY (`" + primaryStr + "`);";
		        	stmt = conn.prepareStatement(sql);
		        	stmt.executeUpdate();
		        }
        	}
        	return true;
	    }catch(SQLException e) {
	      throw new SQLException(e);
	    }
	    finally {
	      try {
	        if(rs!=null) rs.close();
	        if(stmt!=null) stmt.close();
	        if(conn!=null) conn.close();
	      }
	      catch(SQLException e){
	    	  throw new SQLException(e);
	      }
	    }
	}
	
	private String addFieldSQL(String tableName,String name,String type,String size,String fieldAttribute,String defaultValue,String extra,Boolean notNull) {
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("ALTER TABLE `" + tableName + "` ADD ");
	      buffer.append(" `" + name + "` " + type);
	      if(size!=null && !size.equals("")) {
	        buffer.append("(" + size + ")");
	      }
	      if(fieldAttribute!=null && !fieldAttribute.equals("")) {
	        buffer.append(" " + fieldAttribute);
	      }
	      if(notNull) {
	        buffer.append(" NOT NULL");
	      }
	      if(defaultValue!=null && !defaultValue.equals("")) {
	        buffer.append(" DEFAULT '" + defaultValue + "'");
	      }
	      if(extra!=null && !extra.equals("")) {
	        buffer.append(" " + extra);
	      }
	      buffer.append(";");
	    return buffer.toString();
	  }
	
	
	@Override
	public Boolean addIndex(String req) throws SQLException {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    
	    try {
	    	JSONObject jsonObject =JSONObject.fromObject(req);
	    	MysqlTable table = JsonUtils.fromJson(jsonObject.getString("json1"), MysqlTable.class);
	    	MysqlIndex index = JsonUtils.fromJson(jsonObject.getString("json2"), MysqlIndex.class);
	    	Server server = getMysqlServer(table.getServer_id());
	        sql = addIndexSQL(table.getName(),index.getKeyName(),index.getType(),index.getColumnStr());
	        conn = ConnectionPool.getInstance().getConnection(server,table.getDatabaseName());
	        stmt = conn.prepareStatement(sql);
	        stmt.executeUpdate();
        	return true;
	    }catch(SQLException e) {
	    	throw new SQLException(e);
	    }
	    finally {
	      try {
	        if(rs!=null) rs.close();
	        if(stmt!=null) stmt.close();
	        if(conn!=null) conn.close();
	      }
	      catch(SQLException e){
	    	  throw new SQLException(e);
	      }
	    }
	}
	
	private String addIndexSQL(String tableName,String name,String type,String columnStr) {
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("ALTER TABLE `" + tableName +"` ADD "+type+" `"+name+"` ("+returnStr(columnStr)+")");
        buffer.append(";");
	    return buffer.toString();
	  }
	
	
	@Override
	public Boolean addConstraint(String req) throws SQLException {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    
	    try {
	    	JSONObject jsonObject =JSONObject.fromObject(req);
	    	MysqlTable table = JsonUtils.fromJson(jsonObject.getString("json1"), MysqlTable.class);
	    	MysqlKey key = JsonUtils.fromJson(jsonObject.getString("json2"), MysqlKey.class);
	    	Server server = getMysqlServer(table.getServer_id());
	        
	        try{
	        	String columnStr = returnStr(key.getField());
	        	String columnStr_ = returnStr(key.getRef_field());
	        	sql =  "ALTER TABLE `" + table.getName() +"` ADD CONSTRAINT `"+key.getKey_name()+"` FOREIGN KEY ("+columnStr+") REFERENCES `"+key.getRef_database()+"`.`"+key.getRef_table()+"`("+columnStr_+") ON DELETE "+key.getDelete_rule()+" ON UPDATE "+key.getUpdate_rule();
	        	conn = ConnectionPool.getInstance().getConnection(server,table.getDatabaseName());
	        	stmt = conn.prepareStatement(sql);
		        stmt.executeUpdate();
	        }catch(Exception e){
	        	LogUtils.error("添加外键失败");
		        throw new SQLException(e);
	        }
        	return true;
	    }catch(SQLException e) {
	    	throw new SQLException(e);
	    }
	    finally {
	      try {
	        if(rs!=null) rs.close();
	        if(stmt!=null) stmt.close();
	        if(conn!=null) conn.close();
	      }
	      catch(SQLException e){
	    	 throw new SQLException(e);
	      }
	    }
	}
	
	
	@Override
	public Boolean addtabAction(String req) throws SQLException {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    
	    try {
	    	MysqlTable table = JsonUtils.fromJson(req, MysqlTable.class);
	    	Server server = getMysqlServer(table.getServer_id());
	        
	        try{
	        	sql = "CREATE TABLE `" + table.getDatabaseName() +"`.`" + table.getName() +"` (column1 varchar(100) NULL) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci ;";
	        	conn = ConnectionPool.getInstance().getConnection(server,table.getDatabaseName());
	        	stmt = conn.prepareStatement(sql);
		        stmt.executeUpdate();
		        //修改缓存
		        server = ServerUtil.mysqlServer(server);
		        List<Server> list = new ArrayList<>();
		        List<Server> newlist = new ArrayList<>();
		        list = dataBaseMenus.get("Mysql");
		        for(Server ety : list){
		        	if(ety.getId() == server.getId()){
		        		newlist.add(server);
		        	}else{
		        		newlist.add(ety);
		        	}
		        }
				dataBaseMenus.remove("Mysql");
				dataBaseMenus.put("Mysql", newlist);
	        }catch(Exception e){
	        	LogUtils.error("创建表失败");
		        throw new SQLException(e);
	        }
        	return true;
	    }catch(SQLException e) {
	    	throw new SQLException(e);
	    }
	    finally {
	      try {
	        if(rs!=null) rs.close();
	        if(stmt!=null) stmt.close();
	        if(conn!=null) conn.close();
	      }
	      catch(SQLException e){
	    	 throw new SQLException(e);
	      }
	    }
	}
	
	@Override
	public ResultEntity excuteSql(String req) throws SQLException {
		ResultEntity result = new ResultEntity();
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	    	JSONObject jsonObject =JSONObject.fromObject(req);
	    	MysqlTable table = JsonUtils.fromJson(jsonObject.getString("json"), MysqlTable.class);
	    	String sql = JsonUtils.fromJson(jsonObject.getString("sqltxt"), String.class);
	    	Server server = getMysqlServer(table.getServer_id());
	    	// 新建一个map list集合用于存放多条查询记录
	        List<List<Object>> list = new ArrayList<List<Object>>();
	        List<String> columnList = new ArrayList<>();
	        Map<String,Object> map = new HashMap<>();
	        
	        try{
	        	conn = ConnectionPool.getInstance().getConnection(server,table.getDatabaseName());
	        	stmt = conn.prepareStatement(sql);
	        	if(sql.startsWith("select")){
	        		rs = stmt.executeQuery();
	        		ResultSetMetaData md = rs.getMetaData();// 结果集(rs)的结构信息，比如字段数、字段名等。
	                int columnCount = md.getColumnCount();// 取得查询出来的字段个数
	                for (int i = 1; i <= columnCount; i++) {// 循环所有查询出字段
	                	columnList.add(md.getColumnName(i));
                    }
	        		while (rs.next()) {
	        			// 新建一个map集合 将查询出内容按照字段名：值 的键值对形式存储在map集合中
	        			List<Object> rowData = new ArrayList<>();
	                    for (int i = 1; i <= columnCount; i++) {// 循环所有查询出字段
	                        rowData.add(rs.getObject(i));
	                    }
	                    list.add(rowData);// 将map放入list集合中
	        		}
	        		map.put("column", columnList);
	        		map.put("value", list);
	        		result.setValue(true, "", "", map);
	        	}else{
	        		stmt.execute();
		        	server = ServerUtil.mysqlServer(server);
			        List<Server> oldlist = new ArrayList<>();
			        List<Server> newlist = new ArrayList<>();
			        oldlist = dataBaseMenus.get("Mysql");
			        for(Server ety : oldlist){
			        	if(ety.getId() == server.getId()){
			        		newlist.add(server);
			        	}else{
			        		newlist.add(ety);
			        	}
			        }
					dataBaseMenus.remove("Mysql");
					dataBaseMenus.put("Mysql", newlist);
					result.setValue(true, "");
	        	}
	        }catch(Exception e){
	        	LogUtils.error("执行sql语句失败");
		        throw new SQLException(e);
	        }
        	return result;
	    }catch(SQLException e) {
	    	throw new SQLException(e);
	    }
	    finally {
	      try {
	        if(rs!=null) rs.close();
	        if(stmt!=null) stmt.close();
	        if(conn!=null) conn.close();
	      }
	      catch(SQLException e){
	    	 throw new SQLException(e);
	      }
	    }
	}
	  //条数
	  private void other(Server server,MysqlTable table) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	      conn = ConnectionPool.getInstance().getConnection(server,table.getDatabaseName());
	      stmt = conn.prepareStatement("SELECT COUNT(*) FROM `" + table.getName()+"`");
	      rs = stmt.executeQuery();
	      if(rs.next()) {
	        table.setRows(rs.getInt(1));
	      }
	    }
	    catch(SQLException e) {
	    	LogUtils.error(e);
	    }
	    finally {
	      try {
	        if(rs!=null) rs.close();
	        if(stmt!=null) stmt.close();
	        if(conn!=null) conn.close();
	      }
	      catch(SQLException e){
	    	  LogUtils.error(e);
	      }
	    }
	  }
	  //表注释
	  private void getTableComment(Server server,MysqlTable table) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	      conn = ConnectionPool.getInstance().getConnection(server,table.getDatabaseName());
	      stmt = conn.prepareStatement("SHOW TABLE STATUS FROM `" + table.getDatabaseName() + "` LIKE '" + table.getName() + "'");
	      rs = stmt.executeQuery();
	      if(rs.next()) {
	        table.setComment(rs.getString("comment"));
	      }
	    }
	    catch(SQLException e) {
	    	LogUtils.error(e);
	    }
	    finally {
	      try {
	        if(rs!=null) rs.close();
	        if(stmt!=null) stmt.close();
	        if(conn!=null) conn.close();
	      }
	      catch(SQLException e){
	    	  LogUtils.error(e);
	      }
	    }
	  }

	@Override
	public Server getMysqlServer(Integer server_id) {
		List<Server> list = dataBaseMenus.get("Mysql");
		for(Server server : list){
			if(server_id == server.getId()){
				return server;
			}
		}
		return null;
	}
	
	public String returnStr(String columnStr){
		 if(columnStr.contains(",")){
  	    	columnStr = "`" + columnStr.replace(",", "`,`") + "`";
  	    }else{
  	    	columnStr = "`" + columnStr + "`";
  	    }
		 return columnStr;
	}
	
}
