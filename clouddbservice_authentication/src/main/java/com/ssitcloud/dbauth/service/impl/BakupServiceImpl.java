package com.ssitcloud.dbauth.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.common.CommonException;
import com.ssitcloud.dbauth.common.dao.CommonDao;
import com.ssitcloud.dbauth.dao.LibraryDao;
import com.ssitcloud.dbauth.dao.LibraryTemplateDao;
import com.ssitcloud.dbauth.dao.MetadataInfotypeDao;
import com.ssitcloud.dbauth.dao.OperatorDao;
import com.ssitcloud.dbauth.dao.SoxTemplateDao;
import com.ssitcloud.dbauth.entity.LibraryEntity;
import com.ssitcloud.dbauth.entity.LibraryServiceTemplateEntity;
import com.ssitcloud.dbauth.entity.MetadataInfotypeEntity;
import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.SoxTemplateEntity;
import com.ssitcloud.dbauth.service.BakupService;
import com.ssitcloud.dbauth.utils.HttpClientUtil;
import com.ssitcloud.dbauth.utils.JsonUtils;
import com.ssitcloud.dbauth.utils.LogUtils;
import com.ssitcloud.dbauth.utils.Md5CaculateUtil;
import com.ssitcloud.dbauth.utils.PropertiesUtil;

@Service
public class BakupServiceImpl implements BakupService{
	
	@Resource(name="requestURL")
	private RequestURLListEntity requestUrl;
	@Resource
	private CommonDao commonDao;
	@Resource
	private LibraryDao libraryDao;
	@Resource
	private LibraryTemplateDao libraryTemplateDao;
	@Resource
	private SoxTemplateDao soxTemplateDao;
	@Resource
	private OperatorDao operatorDao;
	@Resource
	private MetadataInfotypeDao metadataInfotypeDao;

	
	/**
	 * 这里备份完成之后，需要通信到 device,让device库备份，device备份如果报错则两边回滚，Device成功后与鉴权通信，
	 * 备份，鉴权如果备份失败则 与device通信，两边回滚。如果成功则通知device成功继续，然后继续函数。
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity bakupOnlyByLiraryIdxSsitAuth(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String,Object> m=JsonUtils.fromJson(req, Map.class);
			if(m.containsKey("tableName")  && m.containsKey("library_idx")){
				String tableName=m.get("tableName").toString();
				//根据library_idx备份全表数据的表
				if(tableName.equals("library")
						||"operator".equals(tableName)
						||"library_info".equals(tableName)
						){
					List<Map<String,Object>> lists= commonDao.selectList("common.select", m);
					result.setResult(lists);
					result.setState(true);
					return result;
				}
				//Integer library_idx=Integer.parseInt(m.get("library_idx").toString());

				/**
				 *  tableNameListByLibIdxSsitAuthAdd("library_info");
					tableNameListByLibIdxSsitAuthAdd("library_service_template");
					tableNameListByLibIdxSsitAuthAdd("metadata_infotype");
					tableNameListByLibIdxSsitAuthAdd("password_history");
					tableNameListByLibIdxSsitAuthAdd("operator_info");
					tableNameListByLibIdxSsitAuthAdd("ip_white");
					tableNameListByLibIdxSsitAuthAdd("sox_template");
				 */
				//SELECT * FROM operator_info oi WHERE oi.`operator_idx` IN (SELECT o.`operator_idx` FROM operator o WHERE o.library_idx=16)
				if("library_service_template".equals(tableName)){
					List<Map<String,Object>> lists=commonDao.selectList("common.selectLibraryServiceTemplateByLibraryIdx", m);
					result.setResult(lists);
					result.setState(true);
				}
				if("metadata_infotype".equals(tableName)){
					List<Map<String,Object>> lists=commonDao.selectList("common.selectMetadataInfotypeLibraryIdx", m);
					result.setResult(lists);
					result.setState(true);
				}
				if("password_history".equals(tableName)){
					List<Map<String,Object>> lists=commonDao.selectList("common.selectPassWordHistoryByLibraryIdx", m);
					result.setResult(lists);
					result.setState(true);
				}
				if("operator_info".equals(tableName)){
					List<Map<String,Object>> lists=commonDao.selectList("common.selectOperatorInfoByLibraryIdx", m);
					result.setResult(lists);
					result.setState(true);
				}
				if("ip_white".equals(tableName)){
					List<Map<String,Object>> lists=commonDao.selectList("common.selectIpWhiteByLibraryIdx", m);
					result.setResult(lists);
					result.setState(true);
				}
				if("sox_template".equals(tableName)){
					List<Map<String,Object>> lists=commonDao.selectList("common.selectSoxTemplateByLibraryIdx", m);
					result.setResult(lists);
					result.setState(true);
				}
			}
		}
		return result;
	}
	
	private static List<String> deleteByLibIdxArr=Arrays.asList(new String[]{
			"password_history",
			"ip_white",
			"library_info",
			//"metadata_infotype",
			"operator_info",
			"operator"
	});
	//插入順序為反順序
	/***
	 * 1.library_service_template (or 更新) 
	 * 2.sox_template (or 更新) 
	 * 3.library (or 更新) 
	 * 4.metadata_infotype (or 更新)
	 * 5.operator (or 刪除)
	 * 6.library_info / operator_info / ip_white / password_history /(or 刪除)
	 * 
	 */
	
	/**
	 * 
	 * @param library_idx
	 * @return
	 */
	private boolean deleteDataHandler(Integer library_idx,String fileName){
		Map<String,Object> map=new HashMap<>();
		map.put("library_idx", library_idx);
		for(int i=0;i<deleteByLibIdxArr.size();i++){
			String tableName=deleteByLibIdxArr.get(i);
			//删除 rel_device_group
			map.put("tableName", tableName);
			//int d=0;
			//System.out.println("還原刪除數據："+JsonUtils.toJson(map));
			if(tableName.equals("password_history")){
				List<Map<String,Object>> deleteList=commonDao.selectList("common.selectByOperatorLibIdx", map);
				LogUtils.LogOnFile(fileName, "删除的数据-->"+JsonUtils.toJson(deleteList)+"\n");
				commonDao.delete("common.deletePasswordHistoryByLibIdx", map);
			}else if("ip_white".equals(tableName)){
				List<Map<String,Object>> deleteList=commonDao.selectList("common.selectByOperatorLibIdx", map);
				LogUtils.LogOnFile(fileName, "删除的数据-->"+JsonUtils.toJson(deleteList)+"\n");
				commonDao.delete("common.deleteIpWhiteByLibIdx", map);
			}else if("operator_info".equals(tableName)){
				List<Map<String,Object>> deleteList=commonDao.selectList("common.selectByOperatorLibIdx", map);
				LogUtils.LogOnFile(fileName, "删除的数据-->"+JsonUtils.toJson(deleteList)+"\n");
				commonDao.delete("common.deleteOperatorInfoByLibIdx", map);
			}else if("operator".equals(tableName)
					||"library_info".equals(tableName)){
				List<Map<String,Object>> deleteList=commonDao.selectList("common.selectByLibrary_idx", map);
				LogUtils.LogOnFile(fileName, "删除的数据-->"+JsonUtils.toJson(deleteList)+"\n");
				commonDao.delete("common.deleteTableByLibIdx", map);
			}
			//刪除返回零，可能是本身沒有數據，不拋出異常
		}
		return true;
	}
	/**
	 * Lib_Id  值為更新過的新舊 鍵值（key）  
	 */
	// operator_idx
	private static Map<String, Map<Integer,Integer>> operator_idx_map=new HashMap<>();
	// library_service_template
	private static Map<String, Map<Integer,Integer>> lib_serv_tpl_map=new HashMap<>();
	// sox_template
	private static Map<String, Map<Integer,Integer>> sox_template_map=new HashMap<>();
	// metadata_infotype
	private static Map<String, Map<Integer,Integer>> metadata_info_map=new HashMap<>();

	
	/**
	 * TIPS：
	 * 1.还原table 的顺序以还原的顺序为准，如果备份文件备份的表顺序出现问题则，则还原时抛出异常不允许还原。
	 * 2.执行该函数时 该删除的表已经删除
	 * 
	 * @param rows
	 * @param tableName
	 * @param library_idx
	 * @return
	 */
	private boolean insertDataHandler(List<Map<String,Object>> rows,String tableName,Integer library_idx,String library_id,String fileName){
		Map<String,Object> param=new HashMap<>();
		param.put("library_idx", library_idx);
		if("library_service_template".equals(tableName)){
			Map<Integer,Integer> map=null;
			if(lib_serv_tpl_map.containsKey(library_id)){
				map=lib_serv_tpl_map.get(library_id);
				map.clear();
				//lib_serv_tpl_map.put(library_id, map);
			}else{
				map=new HashMap<>();
				lib_serv_tpl_map.put(library_id, map);
			}
			for(Map<String,Object> m:rows){
				LibraryServiceTemplateEntity row=JsonUtils.convertMapToObject(m,LibraryServiceTemplateEntity.class);
				LibraryServiceTemplateEntity serviceTemplateEntity=new LibraryServiceTemplateEntity();
				serviceTemplateEntity.setLib_service_tpl_id(Integer.parseInt(m.get("lib_service_tpl_id").toString()));
				LibraryServiceTemplateEntity temp=libraryTemplateDao.selLibraryServiceTemplateEntity(serviceTemplateEntity);
				if(temp!=null){
					int u=libraryTemplateDao.updLibraryTemplate(row);
					if(u<=0){
						throw new RuntimeException("library_service_template update failed");
					}
					//System.out.println("還原更新數據："+tableName+"--->"+JsonUtils.toJson(row));
					LogUtils.LogOnFile(fileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(row)+"\n");;
				}else{//沒有數據？
					//旧的IDX
					Integer old_tpl_id=serviceTemplateEntity.getLib_service_tpl_id();
					//如果主鍵被佔用或者存在則提示還原失敗
					int i=libraryTemplateDao.addLibraryTemplate(serviceTemplateEntity);
					if(i<=0){
						throw new RuntimeException("library insert failed");
					}
					//System.out.println("還原新增數據："+tableName+"--->"+JsonUtils.toJson(row));
					LogUtils.LogOnFile(fileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(row)+"\n");;

					//新的
					Integer new_tpl_id=serviceTemplateEntity.getLib_service_tpl_id();
					map.put(old_tpl_id, new_tpl_id);//old key : new key
				}
			}
		}else if("sox_template".equals(tableName)){
			Map<Integer,Integer> map=null;
			if(sox_template_map.containsKey(library_id)){
				map=sox_template_map.get(library_id);
				map.clear();
				//sox_template_map.put(library_id, map);
			}else{
				map=new HashMap<>();
				sox_template_map.put(library_id, map);
			}
			for(Map<String,Object> m:rows){
				SoxTemplateEntity row=JsonUtils.convertMapToObject(m,SoxTemplateEntity.class);
						SoxTemplateEntity soxTemp=soxTemplateDao.selectOneByMap(m);
				if(soxTemp!=null){
					int u=soxTemplateDao.updSoxTemplateEntityById(row);
					if(u<=0){
						throw new RuntimeException("sox_template update failed");
					}
					//System.out.println("還原更新數據："+tableName+"--->"+JsonUtils.toJson(row));
					LogUtils.LogOnFile(fileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(row)+"\n");;

				}else{
					Integer old_tpl_id =row.getSox_tpl_id();
					int i=soxTemplateDao.addSoxTemplateEntity(row);
					if(i<=0){
						throw new RuntimeException("sox_template insert failed");
					}
//					System.out.println("還原新增數據："+tableName+"--->"+JsonUtils.toJson(row));
					LogUtils.LogOnFile(fileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(row)+"\n");;

					Integer new_tpl_id=row.getSox_tpl_id();
					map.put(old_tpl_id, new_tpl_id);//old key : new key
				}
			}
		}else if("library".equals(tableName)){//1
			for(Map<String,Object> m:rows){//正確的話應該有且只有一個館
				LibraryEntity row=JsonUtils.convertMapToObject(m, LibraryEntity.class);
				LibraryEntity lib=new LibraryEntity();
				lib.setLibrary_idx(library_idx);
				LibraryEntity searchLib=libraryDao.selLibraryByIdxOrId(lib);
				if(row.getLibrary_idx()==library_idx){
					Map<Integer,Integer> map=lib_serv_tpl_map.get(library_id);
					if(searchLib==null){
						//如果Map能找到舊的key，則表示已經更新誠信的
						if(row.getLib_service_tpl_id()!=null && map.containsKey(row.getLib_service_tpl_id())){
							row.setLib_service_tpl_id(map.get(row.getLib_service_tpl_id()));
						}
						int i=libraryDao.addLibraryWithIdx(row);//主鍵帶主鍵插入，這個表不順序插入
						if(i<=0){
							throw new RuntimeException("library insert failed");
						}
						//System.out.println("還原新增數據："+tableName+"--->"+JsonUtils.toJson(row));
						LogUtils.LogOnFile(fileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(row)+"\n");;

					}
					else if(!row.equals(searchLib)){//不相等則更新
						//如果Map能找到舊的key，則表示已經更新誠信的
						if(row.getLib_service_tpl_id()!=null && map.containsKey(row.getLib_service_tpl_id())){
							row.setLib_service_tpl_id(map.get(row.getLib_service_tpl_id()));
						}
						int u=libraryDao.updateLibrary(row);
						if(u<=0){
							throw new RuntimeException("library update failed");
						}
						//System.out.println("還原更新數據："+tableName+"--->"+JsonUtils.toJson(row));
						LogUtils.LogOnFile(fileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(row)+"\n");;
					}
				}
			}
		}else if("metadata_infotype".equals(tableName)){
			Map<Integer,Integer> map=null;
			if(metadata_info_map.containsKey(library_id)){
				map=metadata_info_map.get(library_id);
				map.clear();
			}else{
				map=new HashMap<>();
				metadata_info_map.put(library_id, map);
			}
			for(Map<String,Object> m:rows){
				MetadataInfotypeEntity metadataInfotype=JsonUtils.convertMapToObject(m, MetadataInfotypeEntity.class);
				MetadataInfotypeEntity mi=metadataInfotypeDao.selectInfoByIdx(metadataInfotype);
				if(mi!=null){//update
					int u=metadataInfotypeDao.update(metadataInfotype);
					if(u<=0){
						throw new RuntimeException("metadata_infotype update failed");
					}
					LogUtils.LogOnFile(fileName, "還原更新數據："+tableName+"--->"+JsonUtils.toJson(metadataInfotype)+"\n");
				}else{
					Integer old_idx=metadataInfotype.getInfotype_idx();
					int i=metadataInfotypeDao.addMetadataInfotype(metadataInfotype);
					if(i<=0){
						throw new RuntimeException("metadata_infotype insert failed");
					}
					LogUtils.LogOnFile(fileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(metadataInfotype)+"\n");;
					Integer new_idx=metadataInfotype.getInfotype_idx();
					map.put(old_idx, new_idx);
					
				}
			}
		}else if("operator".equals(tableName)){//前面已經刪除過，這裡只負責新增
			Map<Integer,Integer> map=null;
			if(operator_idx_map.containsKey(library_id)){
				map=operator_idx_map.get(library_id);
				map.clear();
			}else{
				map=new HashMap<>();
				operator_idx_map.put(library_id, map);
			}
			for(Map<String,Object> m:rows){
				OperatorEntity row=JsonUtils.fromJson(JsonUtils.toJson(m), OperatorEntity.class);
				Integer old_idx=row.getOperator_idx();
				int i=operatorDao.addOperatorFully(row);
				if(i<=0){
					throw new RuntimeException("operator insert failed");
				}
				LogUtils.LogOnFile(fileName, "還原新增數據："+tableName+"--->"+JsonUtils.toJson(row)+"\n");
				Integer new_idx=row.getOperator_idx();
				map.put(old_idx,new_idx);
			}
		}else if(deleteByLibIdxArr.contains(tableName)){//组装sql语句  能通过library_idx删除和备份的 //模板中的数据不在这里插入 
			//不用 處理 返回值得表在這裡插入
			Map<String,Object> map=new HashMap<>();
			map.put("tableName", tableName);
			System.out.println("tableName:"+tableName);
			List<Map<String,Object>> lists=commonDao.selectList("common.getColumsByTable", map);
			StringBuilder sb=new StringBuilder("insert into ").append(tableName);
			String fieldsVal="";
			StringBuilder values=new StringBuilder("(");
			Map<Integer,Integer> operMap=operator_idx_map.get(library_id);
			Map<Integer,Integer> infoMap=metadata_info_map.get(library_id);
			Map<Integer,Integer> soxMap=sox_template_map.get(library_id);
			for(Map<String,Object> m:rows){
				StringBuilder fields=new StringBuilder("(");
				for(Map<String,Object> mm:lists){
					String fieldname=mm.get("Field").toString();
					String keyType=mm.get("Key").toString();//
					String fieldType=mm.get("Type").toString();
					if(keyType.equals("PRI") && !"ip_white".equals(tableName)){//去除主键操作
						continue;
					}
					Object v=m.get(fieldname);
					//如果包含有operator_idx，則需要更新為新的值，因為前面的的主鍵已經發生改變
					if(fieldname.equals("operator_idx")){//operator_idx
						if(operMap!=null && operMap.containsKey((Integer)v)){
							v=operMap.get((Integer)v);
							System.out.println("operator_idx:"+v);
						}
					}else if(fieldname.equals("infotype_idx")){
						if(infoMap!=null && infoMap.containsKey((Integer)v)){
							v=operMap.get((Integer)v);
						}
					}else if(fieldname.equals("sox_tpl_id")){
						if(soxMap!=null && soxMap.containsKey((Integer)v)){
							v=soxMap.get((Integer)v);
						}
					}
					fields.append(fieldname).append(",");
					if(v instanceof String){
						values.append("'").append(v.toString()).append("',");
					}else if(v instanceof Integer){
						values.append((Integer)v).append(",");
					}else if(v instanceof Long){
						if("timestamp".equals(fieldType)){
							values.append("'").append(new Timestamp((Long)v)).append("',");
						}else{
							values.append((Long)v).append(",");
						}
					}else if(v instanceof Timestamp){
						values.append("'").append((Timestamp)v).append("',");
					}else if(v instanceof Double){
						values.append((Double)v).append(",");
					}else if(v instanceof Date){
						values.append("'").append((Date)v).append("',");
					}else if(v==null){
						values.append("NULL").append(",");
					}
				}
				fieldsVal=fields.deleteCharAt(fields.length()-1).append(")").toString();
				values.deleteCharAt(values.length()-1).append("),(");
			}
			if(rows!=null && rows.size()>0){
				sb.append(fieldsVal).append(" values ").append(values.deleteCharAt(values.length()-1).deleteCharAt(values.length()-1));
				LogUtils.LogOnFile(fileName, sb.toString()+"\n");;
				int i=commonDao.insertBySql(sb.toString());
				if(i<=0){
					throw new RuntimeException("TABLE_INSERT_FAILED|"+tableName);
				}
			}
		}
		return true;
	}
	
	
	
	static final String DATABASE="database";
	
	static final String TABLE="table";
	
	static final String ROW="row";
	private static final String URL_restoreDataByLibraryIdxState = "restoreDataByLibraryIdxState";
	
	/**
	 * 鉴权库还原操作
	 * {
	 * 	library_idx:1
	 * }
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity restoreDataByLibraryIdx(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String,Object> map=JsonUtils.fromJson(req, Map.class);
			if(map.containsKey("file_path") && map.containsKey("library_idx")){
				String file_path=map.get("file_path").toString();//传输备份文件到db层所在服务器
				String file_name=map.get("file_name").toString();
				String library_id=map.get("library_id").toString();
				Integer library_idx=Integer.parseInt(map.get("library_idx").toString());
				File bakupFile=recvFile(file_path,file_name);//传输备份文件到db层所在服务器
				
				if(bakupFile!=null){
					if(bakupFile.exists() && bakupFile.isFile()){
						boolean hasTables=true;
//						String fileName="/usr/restoreLog/"+library_id+"/"+file_name+"_AUTH_"+System.currentTimeMillis()+".log";
						String fileName="/usr/restoreLog/"+library_id+"/"+file_name+"_AUTH_"+System.currentTimeMillis()+".log";
						//还原的话 特殊的表特殊处理了。
						XMLInputFactory factory = XMLInputFactory.newInstance();
						try {
							String defaultDBName="ssitcloud_authentication";
							String databaseName=null;
							String tablename=null;
							
							List<Map<String,Object>> rowList=new ArrayList<>();
							XMLStreamReader reader=factory.createXMLStreamReader(new FileInputStream(bakupFile));
							//这个会抛异常！先删除原来表的信息
							deleteDataHandler(library_idx,fileName);
							while(reader.hasNext()){
							    int type = reader.next();
				                //是否是开始节点，开始节点就是<>
				                if(type==XMLStreamReader.START_ELEMENT){
				                	String name=reader.getName().toString();
				                	if("database".equals(name)){
				                		hasTables=true;
				                		databaseName=reader.getAttributeValue(0);
				                	}else if("table".equals(name)){
				                    	String attrName=reader.getAttributeName(0).toString();
				                    	if("tablename".equals(attrName)){
				                    		tablename=reader.getAttributeValue(0);
				                    	}
				                    }else if("row".equals(name)){
				                    	if(defaultDBName.equals(databaseName)){
				                    		String json=reader.getElementText();
					                    	Map<String, Object> rowMap=JsonUtils.fromJson(json, Map.class);
					                    	rowList.add(rowMap);
				                    	}
				                    }
				                    //是否是文本节点,开始节点和结束节点之间的内容
				                }else if(type==XMLStreamReader.CHARACTERS){
				                    //是否是结束节点,结束节点就是</>
				                }else if(type==XMLStreamReader.END_ELEMENT){
				                	String name=reader.getName().toString();
				                	if("table".equals(name)){// 获取rows数据结束
				                		if(defaultDBName.equals(databaseName)){
				                			//插入数据
				                			insertDataHandler(rowList, tablename,library_idx,library_id,fileName);
				                		}
				                		tablename=null;
				                		rowList.clear();
				                	}
				                	if("database".equals(name)){
				                		databaseName=null;
				                	}
				                }
							}
							if(!hasTables){
								throw new RuntimeException("XML文件不完整");
							}
							LogUtils.LogOnFile(fileName, "operator_idx_map["+library_id+"] -->"+JsonUtils.toJson(operator_idx_map.get(library_id)));
							Map<String,String> pra=new HashMap<>();
							pra.put("req",JsonUtils.toJson(map));
							System.out.println("~~~~~~~~~~~~~restoreDataByLibraryIdxState start~~~~~~~~~~~~");
							//发送请求到设备数据库进行还原
							String resp=HttpClientUtil.doPostLongtime(requestUrl.getRequestURL(URL_restoreDataByLibraryIdxState), pra, "UTF-8");
							System.out.println("resp--->"+resp);
							ResultEntity re=JsonUtils.fromJson(resp, ResultEntity.class);
							if(re==null || !re.getState()){
								System.out.println(JsonUtils.toJson(re));
								throw new RuntimeException("device restore failed");
							}
							System.out.println("~~~~~~~~~~~~~restore end~~~~~~~~~~~~");
							result.setState(true);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (XMLStreamException e) {
							throw new CommonException(e);
						}
					}
				}
			}
		}
		return result;
	}
	
	
	private static String GetBusUrl(){
		String developer_model=PropertiesUtil.getConfigPropValueAsText("developer_model");
		if("true".equals(developer_model)){
			return PropertiesUtil.getConfigPropValueAsText("cloudbusinessservice_device_url_developer");
		}
		if("false".equals(developer_model)){
			return PropertiesUtil.getConfigPropValueAsText("cloudbusinessservice_device_url");
		}
		return PropertiesUtil.getConfigPropValueAsText("cloudbusinessservice_device_url");	
	}
	
	/**
	 * 
	 * 接受备份文件数据
	 * @param filePath
	 * @param file_name
	 * @return
	 */
	private static File recvFile(String filePath,String file_name){
		//如果在同一台服务器上感觉有问题，不好测试，则修改一下路径
    	String path=StringUtils.delete(filePath, file_name);
    	String fix="db";
		File file = new File(path+File.separator+fix);
		File filep = new File(path);
		if(!filep.exists()){
		   if(!filep.mkdirs()){
			   throw new RuntimeException("创建路径失败");
		   }
	    }
	    if(!file.exists()){
		  boolean isMk=file.mkdir();
		  if(!isMk) throw new RuntimeException("创建文件夹失败");
	    }
	    //需要保存的地址
	    File relFile=new File(path+File.separator+fix+File.separator+file_name);
	    if(relFile.exists()){
		    System.out.println("^^^^^^^^ relFile 存在 ^^^^^^^^");
		    //存在则,则直接返回供下载，此处有点问题，发生过文件存在，但是文件内容为空的情况， 不清楚什么条件下发生的
		    return relFile;
	    }
	    
		String url=GetBusUrl();//获取device business层的ip
		String ip=StringUtils.delete(url.split(":")[1], "//");
		System.out.println("cloudbusinessservice_device ip:"+ip);
		Socket socket = null;
		try {
			socket=new Socket(ip, 33456);//连接device business开启的socket监听
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		DataOutputStream dos = null;
		BufferedOutputStream bos = null; //What can BufferedOutputStream help ? 
		BufferedInputStream dis=null;
		byte[] bytes = new byte[1024];
			try {
				/*
				 * new a File with the filePath
				 * new a FileInputStream with the File to read the file by byte
				 * new a BufferedInputStream with the FileInputStream to use buffered stream
				 */
				dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				//首先发送文件名     客户端发送使用writeUTF方法，服务器端应该使用readUTF方法
				dos.writeUTF(filePath);//bus服务器，真实文件路径
				dos.flush();
				bos=new BufferedOutputStream(new FileOutputStream(relFile));
				dis=new BufferedInputStream(socket.getInputStream());
				int length = 0;
				while ((length = dis.read(bytes, 0, bytes.length)) > 0) {
					bos.write(bytes, 0, length);
				}
				bos.flush();
				
				return relFile;
			} catch (IOException e) {
				//e.printStackTrace();
				LogUtils.error(e);
			} finally {
				//使用完毕后，应关闭输入、输出流和socket
					try {
						if(dis != null)
							dis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						try {
							if(bos != null)
								bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}finally{
							try {
							  if(dos != null)
								dos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}finally{
								try {
									if(socket != null)
										socket.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
			}
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity GetChangedIDXByIdxNameAndLibraryInfo(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String,Object> map=JsonUtils.fromJson(req, Map.class);
			Integer library_idx=Integer.parseInt(map.get("library_idx").toString());
			String library_id=map.get("library_id").toString();
			String idx_name=map.get("idx_name").toString();
			if("operator_idx".equals(idx_name)){
				if(operator_idx_map.containsKey(library_id)){
					Map<Integer,Integer> idxs=operator_idx_map.get(library_id);
					result.setResult(idxs);
					result.setState(true);
					if(idxs==null||idxs.isEmpty()){
						result.setMessage("NO_DATA");
					}
				}
			}
		}
		return result;
	}
	
	

}
