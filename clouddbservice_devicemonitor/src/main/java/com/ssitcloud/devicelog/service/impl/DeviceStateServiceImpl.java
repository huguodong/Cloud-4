package com.ssitcloud.devicelog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.MapUtils;
import org.apache.http.Consts;
import org.bson.Document;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.system.ExtStateMap;
import com.ssitcloud.common.system.MongoDB;
import com.ssitcloud.common.system.MongoInstance;
import com.ssitcloud.common.system.MongoInstanceManager;
import com.ssitcloud.common.util.CloseUtil;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.devicelog.dao.BookrackDao;
import com.ssitcloud.devicelog.dao.DeviceExtDao;
import com.ssitcloud.devicelog.service.DeviceStateService;
import com.ssitcloud.operlog.bookenum.EnumClass;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;

@Service
public class DeviceStateServiceImpl extends BasicServiceImpl implements DeviceStateService {

	private static Logger LOG=LoggerFactory.getLogger("DeviceStateServiceImpl"); 
	
	public static final String charset=Consts.UTF_8.toString();
	
	public static final String URL_queryDevStatusCode = "queryDevStatusCode";

	@Resource(name = "deviceExtDaoImpl")
	private DeviceExtDao deviceExtDao;
	@Resource
	private BookrackDao bookrackDao;

	//保存{"device_id1":"状态（1，2，3）","device_id2":"状态（1，2，3）"}

	
	@Resource(name = "mongoInstanceManager")
	private MongoInstanceManager mongoInstanceManager;
	
	@Resource(name="mongoDBImpl")
	private MongoDB mongoDB;
	
	@Resource(name = "requestURL")
	private RequestURLListEntity requestURL;
	
	private  MongoClient client = MongoInstanceManager.mongoClient;
	
	public static final String SUCCESS = "success";
	
	/**
	 * 初始化redis客户端
	 */
	private static JedisUtils redis = null;
	
	static
	{
		 redis = JedisUtils.getInstance();
	}
	

	/***********************/
	/** 更新机器状态逻辑： ********/
	/** 1.查询当前库的最后条信息 ***/
	/** 2.查询到的信息与接口的信息进行比较，把不一致的数据存储到state_log **/
	/** 3.插入当前的日志到bin_state，ext_state **/
	/***********************/

	/**
	 * 检查EXT_STATE状态变化
	 * 
	 * @param @param collectionName
	 * @param @param instance
	 * @param @param map
	 * @return void
	 * @throws
	 * @author lbh
	 * @date 2016年5月9日
	 */
	@SuppressWarnings("unchecked")
	private void checkExtStateAndInsertStateLog(String collectionName, MongoClient client,String database, Map<String, Object> map,String dev_id) {
		// 计算出新的设备状态、、start
		String new_state = null;
		JSONArray myJsonArray=null;
		StringBuilder sb=new StringBuilder();
		Document doc = new Document((Map<String, Object>) map.get(collectionName));
		if (doc != null) {
			//System.out.println(doc);
			Map<String,String> map1=new HashMap<>();
			String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDevStatusCode), map1, charset);
			if(JSONUtils.mayBeJSON(result)){
				ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
				myJsonArray = JSONArray.fromObject(resultEntity.getResult());
				//List<StatusCode> userList = gson.fromJson(myJsonArray, new TypeToken<List<StatusCode>>() {}.getType()); 
				/*for(int i=0;i<myJsonArray.size();i++){//{"ext_logic_obj":"Printer","ext_model_code":"257","ext_model_info":"纸将尽","ext_code_type":0}
					JSONObject job = myJsonArray.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
					job.get("ext_logic_obj");
					job.get("ext_model_code"); // 得到 每个对象中的属性值
					job.get("ext_model_info");
					job.get("ext_code_type");
				}*/
			
			}
			/**
			 * identityReader/authorityReader/itemRfidReader/itemLoadReader/
			 * ItemBarcode/registerReader/cashAcceptor/cardDispenser/printer
			 */
			String extState=EnumClass.EXT_STATE.NORMAL.getExt_state();
			String ext_logic_obj = null;
			String ext_model_code = null;
			String ext_model_info = null;
			Object ext_code_type = null;
			for(Entry<String, Object> e:doc.entrySet()){
				Object val=e.getValue();
				for(int i=0;i<myJsonArray.size();i++){//{"ext_logic_obj":"Printer","ext_model_code":"257","ext_model_info":"纸将尽","ext_code_type":0}
					JSONObject job = myJsonArray.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
					ext_logic_obj = (String) job.get("ext_logic_obj");
					ext_model_code = (String) job.get("ext_model_code"); // 得到 每个对象中的属性值
					ext_model_info = (String) job.get("ext_model_info");
					ext_code_type = job.get("ext_code_type");
					//if(ext_logic_obj.equals(e.getKey())&&val.equals(ext_model_code)){
					if(val.equals(ext_model_code)){
						if(1==(Integer)ext_code_type){
							new_state = String.valueOf(ext_code_type);
							sb.append(e.getKey()).append(",");
						}
					}
				}
				//0 !=1
				/*if(val instanceof String && !extState.equals(e.getValue())){
					System.out.println("key:"+e.getKey()+"------val:"+e.getValue());
					new_state = EnumClass.EXT_STATE.ALERT.getExt_state();
					sb.append(e.getKey()).append(",");
				}*/
			}

			// plcSSL plcReturn plcSorter
			if (doc.containsKey("PlcSSL")) {
				Object o=doc.get("PlcSSL");
				if(o instanceof Map){
					Map<String, Object> plcSSLDoc = (Map<String, Object>) o;
					for(Entry<String, Object> e:plcSSLDoc.entrySet()){
						Object val=e.getValue();
						for(int i=0;i<myJsonArray.size();i++){//{"ext_logic_obj":"Printer","ext_model_code":"257","ext_model_info":"纸将尽","ext_code_type":0}
							JSONObject job = myJsonArray.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
							ext_logic_obj = (String) job.get("ext_logic_obj");
							ext_model_code = (String) job.get("ext_model_code"); // 得到 每个对象中的属性值
							ext_model_info = (String) job.get("ext_model_info");
							ext_code_type = job.get("ext_code_type");
							if(val.equals(ext_model_code)){
								if(1==(Integer)ext_code_type){
									new_state = String.valueOf(ext_code_type);
									sb.append(e.getKey()).append(",");
								}
							}
						}
						/*if(val instanceof String && !EnumClass.EXT_STATE.NORMAL.equals(e.getValue())){
							if (new_state == null) {
								new_state = EnumClass.EXT_STATE.ALERT.getExt_state();
							}
							sb.append(e.getKey()).append(",");
							break;
						}*/
					}
				}
			}

			if (doc.containsKey("plcReturn")) {
				 Object o=doc.get("plcReturn");
				 if(o instanceof Map){
					 Map<String, Object> plcReturnDoc = (Map<String, Object>) o;
						for(Entry<String, Object> e:plcReturnDoc.entrySet()){
							Object val=e.getValue();
							if(val instanceof String && !EnumClass.EXT_STATE.NORMAL.equals(e.getValue())){
								if (new_state == null) {
									new_state = EnumClass.EXT_STATE.ALERT.getExt_state();
								}
								sb.append(e.getKey()).append(",");
								break;
							}
						}
				 }
				
			}
			if (doc.containsKey("plcSorter")) {
				 Object o=doc.get("plcSorter");
				 if(o instanceof Map){
					 Map<String, Object> plcSorterDoc =(Map<String, Object>)o;
						for(Entry<String, Object> e:plcSorterDoc.entrySet()){
							Object val=e.getValue();
							if(val instanceof String && !EnumClass.EXT_STATE.NORMAL.equals(e.getValue())){
								if (new_state == null) {
									new_state = EnumClass.EXT_STATE.ALERT.getExt_state();
								}
								sb.append(e.getKey()).append(",");
								break;
							}
						}
				 }
			}
			// 最后，检测,如果没有值,则设置成正常状态
			if (new_state == null) {
				new_state = EnumClass.EXT_STATE.NORMAL.getExt_state();
			}
		}
		// 计算出新的设备状态、、end
		// 过滤出table_name=collectionName的记录
		Document localDoc = deviceExtDao.findOneAndSort( client, database, EnumClass.COLLECTION.state_log.name(), new Document(
				"table_name", collectionName), new Document("create_time", -1));
		/**
		 * database==deivce_id
		 */
		String deivce_id=database;
		
		if (localDoc == null) {// 没有创建集合 或者 没有符合条件的数据
			// 没有查到数据，新的，这直接插入
			Document logStateDoc = new Document();
			logStateDoc.put("table_name", collectionName);
			logStateDoc.put("dev_id", dev_id);
			logStateDoc.put("old_id", "");
			logStateDoc.put("old_state", "");
			logStateDoc.put("new_state", new_state);
			logStateDoc.put("alert_obj", sb.toString());
			logStateDoc.put("create_time", new DateTime().toString("yyyyMMddHHmmss"));
			// 插入到state_log
			deviceExtDao.insertOne( client, database, EnumClass.COLLECTION.state_log.name(), logStateDoc);
			//另外，保存在内存中一份，以便于 查询使用,只保存在内存中的话，重启状态会丢失
			ExtStateMap.putState(dev_id, new_state);
		} else {
			String new_state_ = localDoc.getString("new_state");// 获取本地的新状态
			if (new_state != null) {
				if (new_state.equals(new_state_)) {// 状态相同，则不做更改,但需要修改上传时间
					Document updateDoc=new Document();
					updateDoc.put("create_time",new DateTime().toString("yyyyMMddHHmmss"));
					updateDoc.put("alert_obj",sb.toString());
					updateDoc.put("dev_id", dev_id);
					UpdateResult ur=deviceExtDao.updateOne(client, deivce_id, EnumClass.COLLECTION.state_log.name(), 
							Filters.eq("_id",localDoc.getObjectId("_id")),
							new Document("$set",updateDoc)
							//new Document("$set",new Document("create_time",new DateTime().toString("yyyyMMddHHmmss")))
					);
				   ExtStateMap.putState(dev_id, new_state);//由于可能没有 dev_id 则需要更新。
				} else {
					//删除原纪录
					//数据不一致，清除原来数据
					Document deleteFilter = new Document();
					deleteFilter.append("table_name", collectionName);// 删除表名为改集合的所有记录
					DeleteResult dr = deviceExtDao.deleteMany( client, database, EnumClass.COLLECTION.state_log.name(),deleteFilter);
					String old_id = localDoc.getObjectId("_id").toHexString();
					Document logStateDoc = new Document();
					logStateDoc.put("table_name", collectionName);
					logStateDoc.put("dev_id", dev_id);
					logStateDoc.put("old_id", old_id);
					logStateDoc.put("old_state", new_state_);// 本地的新状态
					logStateDoc.put("new_state", new_state);// 远程的新状态
					logStateDoc.put("alert_obj", sb.toString());
					logStateDoc.put("create_time", new DateTime().toString("yyyyMMddHHmmss"));
					// 插入到state_log
					deviceExtDao.insertOne(client, database, EnumClass.COLLECTION.state_log.name(), logStateDoc);
					//另外，保存在内存中一份，以便于 查询使用
					ExtStateMap.putState(dev_id, new_state);
				}
			}

		}

	}

	/**
	 * 设备段上传状态数据
	 * 
	 * 
	 * req 格式见 DeviceLogController.java
	 * 
	 * 
	 * 
	 * @methodName: transData
	 * @param req
	 * @return
	 * @author: liuBh
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntityF<Object> transData(String req) {
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		Assert.hasText(req, "transData parameter must has content");
		Map<String, Object> map = null;
		
		try {
			map = JsonUtils.fromJson(req, Map.class);
		} catch (Exception e) {
			result.setState(false);
			result.setMessage(e.getMessage());
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
			return result;
		}
		
		if (map != null && map.containsKey("device_id") && map.containsKey("library_id")) {
			String dev_id = (String) map.get("device_id");
			String library_id =(String)map.get("library_id");
			//先查询一下到底存不存在这个数据库,如果不存在 则创建 存在则跳过
			if(StringUtils.hasText(dev_id)&&StringUtils.hasText(library_id)){
				String libName=library_id+"_"+dev_id;
				checkIftheDBExist(libName);//check mem leak 
			}else{
				result.setState(false);
				result.setMessage("device_id and library_id is not exists");
				return result;
			}
			String libName=library_id+"_"+dev_id;
			MongoInstance instance = mongoInstanceManager.getInstance(libName);
//			MongoClient client=null;
			try {
//				client=mongoDB.getDBClient(instance);
				//不存在的情况下需要动态添加？
				if (instance != null) {
					map = (Map<String, Object>) map.get("content");
					if (map == null) {
						result.setState(false);
						result.setMessage("content is  null");
						return result;
					}
					/**
					 * 状态日志
					 */  
					if (map.containsKey(EnumClass.COLLECTION.bin_state.name())) {
						// 需要检查数据是否发生变化
						Insert(client,instance.getOperDatabase(), EnumClass.COLLECTION.bin_state.name(), map);
						result.setState(true);
					}else if (map.containsKey(EnumClass.COLLECTION.ext_state.name())) {
						// 需要检查数据是否发生变化
						checkExtStateAndInsertStateLog(EnumClass.COLLECTION.ext_state.name(),  client, instance.getOperDatabase(), map,dev_id);
						Insert(client,instance.getOperDatabase(), EnumClass.COLLECTION.ext_state.name(), map);
						result.setState(true);
					}else if (map.containsKey(EnumClass.COLLECTION.bookrack_state.name())) {
						// 需要检查数据是否发生变化
						Insert(client,instance.getOperDatabase(), EnumClass.COLLECTION.bookrack_state.name(), map);
						result.setState(true);
					}else if (map.containsKey(EnumClass.COLLECTION.soft_state.name())) {
						// 需要检查数据是否发生变化
						Insert(client,instance.getOperDatabase(), EnumClass.COLLECTION.soft_state.name(), map);
						result.setState(true);
					}else if (map.containsKey(EnumClass.COLLECTION.bookbox_log.name())) {//日志操作记录
						Insert(client,instance.getOperDatabase(), EnumClass.COLLECTION.bookbox_log.name(), map);
						result.setState(true);
					}else if (map.containsKey(EnumClass.COLLECTION.bookrack_log.name())) {
						Insert(client,instance.getOperDatabase(), EnumClass.COLLECTION.bookrack_log.name(), map);
						result.setState(true);
					}else if (map.containsKey(EnumClass.COLLECTION.cardbox_log.name())) {
						Insert(client,instance.getOperDatabase(), EnumClass.COLLECTION.cardbox_log.name(), map);
						result.setState(true);
					}
					/* 借还 财经 办卡日志走别的接口
					else if (map.containsKey(EnumClass.COLLECTION.cardissue_log.name())) {
						Insert(client,instance.getOperDatabase(), EnumClass.COLLECTION.cardissue_log.name(), map);
					}else if (map.containsKey(EnumClass.COLLECTION.finance_log.name())) {
						Insert(client,instance.getOperDatabase(), EnumClass.COLLECTION.finance_log.name(), map);
					}else 0if (map.containsKey(EnumClass.COLLECTION.loan_log.name())) {
						Insert(client,instance.getOperDatabase(), EnumClass.COLLECTION.loan_log.name(), map);
					}
					*/
				}
				//
			} catch (Exception e) {
				result.setState(false);
				result.setMessage(e.getMessage());
				LOG.error(e.getMessage(), e);
				e.printStackTrace();
			}finally{
				//CloseUtil.close(client);
			}
			
		}
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntityF<Object> transOperationLog(String req) {
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		Assert.hasText(req, "transOperationLog parameter must has content");
		Map<String, Object> map = null;
		try {
			map = JsonUtils.fromJson(req, Map.class);
		} catch (Exception e) {
			result.setState(false);
			result.setMessage(e.getMessage());
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
			return result;
		}
		String dev_id  = "";
		String library_id = "";
		if (map != null && map.containsKey("device_id") && map.containsKey("library_id")) {
			dev_id = (String) map.get("device_id");
			library_id =(String)map.get("library_id");
			//先查询一下到底存不存在这个数据库,如果不存在 则创建 存在则跳过
			if(StringUtils.hasText(dev_id)&&StringUtils.hasText(library_id)){
				String libName=library_id+"_"+dev_id;
				checkIftheDBExist(libName);//check mem leak 
			}else{
				result.setState(false);
				result.setMessage("device_id or library_id is not exists ");
				return result;
			}
			String libName=library_id+"_"+dev_id;
			MongoInstance instance = mongoInstanceManager.getInstance(libName);
//			MongoClient client=null;
			try {
//				client=mongoDB.getDBClient(instance);
				//不存在的情况下需要动态添加？
				if (instance != null) {
					map = (Map<String, Object>) map.get("content");
					if (map == null) {
						result.setState(false);
						result.setMessage("content is null");
						return result;
					}
					/**
					 * 日志操作记录
					 */
					if (map.containsKey("collection")) {
						String collection = map.get("collection")+"";
						map.remove("collection");//这个字段是在同步程序转发中添加的， 要删除
						if (EnumClass.COLLECTION.cardissue_log.name().equals(collection)) {
							InsertDirect(client,instance.getOperDatabase(), EnumClass.COLLECTION.cardissue_log.name(), map);
						}
						if (EnumClass.COLLECTION.finance_log.name().equals(collection)) {
							InsertDirect(client,instance.getOperDatabase(), EnumClass.COLLECTION.finance_log.name(), map);
						}
						if (EnumClass.COLLECTION.loan_log.name().equals(collection)) {
							InsertDirect(client,instance.getOperDatabase(), EnumClass.COLLECTION.loan_log.name(), map);
						}
						map.put("collection",collection);
					}
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				e.printStackTrace();
			}finally{
				//CloseUtil.close(client);
			}
			
		}
		
		result.setState(true);
		
		//是否借还操作
		boolean flag =isLoanLog(map);
		
		if(!flag){
			return result;
		}
		
		//根据library_id获取library_idx
		String libraryIdx = getLibraryIdxByLibraryId(library_id);
		
		//根据用户借阅信息更新推荐结果
		updateRecommendResult(map,libraryIdx);
		
		//封装馆藏数据
		Map<String, String>  bookItemMap =setterBookItemState(map,libraryIdx);
		
		//设备端操作结果：0-操作成功，其他-操作失败
		String operateResult = (String) map.get("operresult");
		
		//更新馆藏图书状态
		if("0".equals(operateResult) && !MapUtils.isEmpty(bookItemMap)){
			
			try {
				String request = JsonUtils.object2String(bookItemMap);
				updateBookItemState(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		
		return result;
	}

	/**
	 * 将数据插入数据库，并且增加 创建时间。
	 * 
	 * @param @param instance 连接数据库实例
	 * @param @param collection_name 集合名称
	 * @param @param map
	 * @return void
	 * @throws
	 * @author lbh
	 * @date 2016年5月9日
	 */
	private void Insert(MongoClient client,String databaseName, String collection_name, Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		Map<String, Object> data = (Map<String, Object>) map.get(collection_name);
		data.put("createTime", new DateTime().toString("yyyyMMddHHmmss"));
		deviceExtDao.insertJSON(JsonUtils.toJson(data), client, databaseName, collection_name);
	}
	
	/**
	 * 直接将map数据插入到mongodb中
	 *
	 * <p>2017年3月2日 下午3:36:53 
	 * <p>create by hjc
	 * @param client
	 * @param databaseName
	 * @param collection_name
	 * @param map
	 */
	@SuppressWarnings("unused")
	private void InsertDirect(MongoClient client,String databaseName, String collection_name, Map<String, Object> map){
		map.put("createTime", new DateTime().toString("yyyyMMddHHmmss"));
		deviceExtDao.insertJSON(JsonUtils.toJson(map), client, databaseName, collection_name);
	}


	/**
	 * 检测 设备外设状态
	 * req ={["device_id":"1","device_id":"2",......]}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntityF<Object> queryDeviceState(String req) {
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		Map<String, Object> devState = new HashMap<>();
		Assert.hasText(req, "queryDeviceState method parameter must has content");
		List<String> list = JsonUtils.fromJson(req, List.class);
		if (list != null ) {
			//数据库实例名集合
			//List<String> deviceIdList = (List<String>) map.get("device_id");
			for (String dev_id : list) {
				com.ssitcloud.common.util.Log.DebugOnScr("queryDeviceState DEV_ID:"+dev_id);
				MongoInstance instance = mongoInstanceManager.getInstance(dev_id);
				if (instance != null) {
//					MongoClient client=null;
					try {
//						client=mongoDB.getDBClient(instance);
						Document filter = new Document("table_name","ext_state");
						Document sort = new Document("create_time", -1);
						Document doc = deviceExtDao.findOneAndSort(client,instance.getOperDatabase(), EnumClass.COLLECTION.state_log.name(), filter,sort);
						if (doc != null) {
							String new_state = doc.getString("new_state");
							String alert_obj=doc.getString("alert_obj");
							String create_time=doc.getString("create_time");
							Map<String,Object> map=new HashMap<>();
							map.put("new_state", new_state);
							map.put("alert_obj", alert_obj);
							map.put("create_time", create_time);
							map.put("current_time", System.currentTimeMillis());
							devState.put(dev_id, map);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						//CloseUtil.close(client);
					}
				}
			}
			com.ssitcloud.common.util.Log.DebugOnScr("DevState:"+devState);
			result.setState(true);
			result.setResult(devState);
		}
		return result;
	}
	/**
	 * 获取书架信息
	 * req ={["device_id":"1","device_id":"2",......]}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntityF<Object> selectBookrackState(String req) {
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		Map<String,Object> resultMap=new HashMap<>();
		Assert.hasText(req, "selectBookrackState method parameter must has content");
		List<String> deviceIdList = JsonUtils.fromJson(req, List.class);
		if (deviceIdList != null ){
			for(String dev_id:deviceIdList){
				MongoInstance instance = mongoInstanceManager.getInstance(dev_id);
				if(instance!=null){
//					MongoClient client=null;
					try {
//						client=mongoDB.getDBClient(instance);
						//获取最新的一个记录
						Document sort = new Document("createTime", -1);
						Document doc=bookrackDao.findOneAndSort(client,instance.getOperDatabase(), EnumClass.COLLECTION.bookrack_state.name(), new Document(), sort);
						if(doc!=null){
							resultMap.put(dev_id, doc);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						//CloseUtil.close(client);
					}
				}
			}
			result.setResult(resultMap);
			result.setState(true);
		}
		
		return result;
	}
	/**
	 * 获取箱子状态信息
	 * req ={["lib_id_device_id1","lib_id_device_id2",......]}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntityF<Object> selectBinState(String req) {
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		Map<String,Object> map=new HashMap<>();
		Assert.hasText(req, "selectBinState method parameter must has content");
		List<String> deviceIdList = JsonUtils.fromJson(req, List.class);
		if (deviceIdList != null ){
			for(String dev_id:deviceIdList){//获取多个 lib_id_device_id1
				MongoInstance instance = mongoInstanceManager.getInstance(dev_id);
				if(instance!=null){
//					MongoClient client=null;
					try {
//						client=mongoDB.getDBClient(instance);
						//获取最新的一个记录
						Document sort = new Document("createTime", -1);
						Document doc=bookrackDao.findOneAndSort(client,instance.getOperDatabase(), EnumClass.COLLECTION.bin_state.name(), new Document(), sort);
						if(doc!=null){//如果 bin state 没有数据.....页面会提示报错！
							doc.remove("_id");
							map.put(dev_id, doc);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						//CloseUtil.close(client);
					}
				}
			}
			result.setResult(map);
			result.setState(true);
		}
		return result;
	}
	/**
	 * 获取设备状态信息
	 * req ={["device_id1","device_id2",......]}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntityF<Object> selectDeviceExtState(String req) {
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		Map<String,Object> map=new HashMap<>();
		Assert.hasText(req, "selectDeviceExtState method parameter must has content");
		List<String> deviceIdList = JsonUtils.fromJson(req, List.class);
		if (deviceIdList != null ){
			for(String dev_id:deviceIdList){//获取多个
				MongoInstance instance = mongoInstanceManager.getInstance(dev_id);
				if(instance!=null){
//					MongoClient client=null;
					try {
//						client=mongoDB.getDBClient(instance);
						Document sort = new Document("createTime", -1);
						Document doc=bookrackDao.findOneAndSort(client,instance.getOperDatabase(), EnumClass.COLLECTION.ext_state.name(), new Document(), sort);
						if(doc!=null){
							doc.remove("_id");
							map.put(dev_id, doc);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						//CloseUtil.close(client);
					}
				}
			}
			result.setResult(map);
			result.setState(true);
		}
		return result;
	}
	/**
	 * 功能状态
	 * req ={["device_id1","device_id2",......]}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntityF<Object> selectSoftState(String req){
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		Map<String,Object> map=new HashMap<>();
		Assert.hasText(req, "selectSoftState method parameter must has content");
		List<String> deviceIdList = JsonUtils.fromJson(req, List.class);
		if (deviceIdList != null ){
			for(String dev_id:deviceIdList){//获取多个
				MongoInstance instance = mongoInstanceManager.getInstance(dev_id);
				if(instance!=null){
//					MongoClient client=null;
					try {
//						client=mongoDB.getDBClient(instance);
						Document sort = new Document("createTime", -1);
						Document doc=bookrackDao.findOneAndSort(client,instance.getOperDatabase(), EnumClass.COLLECTION.soft_state.name(), new Document(), sort);
						if(doc!=null){
							doc.remove("_id");
							map.put(dev_id, doc);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						//CloseUtil.close(client);
					}
					
				}
			}
			result.setResult(map);
			result.setState(true);
		}
		return result;
	}
	public static final String QUERY_NO_STATE="-2";
	public static final String QUERY_ALL_STATE="-1";
	public static final String QUERY_NORMAL_STATE="0";
	public static final String QUERY_ALERT_STATE="1";
	public static final String QUERY_ERROR_STATE="2";//失效？
	
	//{req=0/1/2} 返回符合条件的设备ID List
	@Override
	public ResultEntityF<Object> queryDeviceIdByState(String req) {
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		List<String> devIdList=new ArrayList<>();
		if(QUERY_ALL_STATE.equals(req)){
			//查询全部状态，即没有选择任何状态
			result.setState(true);
			return result;
		}
		for(Entry<String, String> e:ExtStateMap.entrySet()){
			String device_id=e.getKey();//Devid_Libid
			String state=e.getValue();
			if(state.equals(req)){
				devIdList.add(device_id);
			}
		}
		if(devIdList.size()>0){
			result.setResult(devIdList);
			result.setState(true);
		}else{
			//非查询全部状态，没有查询到结果，这
			devIdList.add("NO_RESULT");
			result.setResult(devIdList);
			result.setState(true);
		}
		return result;
	}

	@Override
	public ResultEntityF<Object> getMongodbNames(String req) {
		ResultEntityF<Object> result=new ResultEntityF<Object>();
		List<String> devNames=new ArrayList<>();
		for(Entry<String, MongoInstance> e:MongoInstanceManager.instancesCache.entrySet()){
			String deviceName=e.getKey();
			//MongoInstance instance=e.getValue();
			devNames.add(deviceName);
		}
		result.setState(true);
		result.setResult(devNames);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntityF<Object> getDBInstanceByDBName(String req) {
		ResultEntityF<Object> result=new ResultEntityF<Object>();
		if(JSONUtils.mayBeJSON(req)){
			Map<String,String> mapParam=JsonUtils.fromJson(req, Map.class);
			if(mapParam!=null){
				String dbName=mapParam.get("dbName");
				if(dbName!=null){
					MongoInstance instance=mongoInstanceManager.getInstance(dbName);
					if(instance!=null){
						result.setResult(instance);
						result.setState(true);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 接收设备端 状态数据 上传到特殊设备：人流量服务器、3D导航、智能家具
	 * 
	 * @methodName: transData
	 * @param req
	 * @return
	 * @author: liuBh
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntityF<Object> transDataToSpecialDevice(String req) {

		ResultEntityF<Object> result = new ResultEntityF<Object>();
		result.setState(true);
		Assert.hasText(req, "transData parameter must has content");
		Map<String, Object> map = null;
		try {
			 map = JsonUtils.fromJson(req, Map.class);
		}catch(Exception e){
			result.setState(false);
			result.setMessage(e.getMessage());
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
			return result;
		}
		if (map != null && map.containsKey("device_id")
				&& map.containsKey("library_id")) {
			String dev_id = (String) map.get("device_id");
			String library_id = (String) map.get("library_id");
			// 先查询一下到底存不存在这个数据库,如果不存在 则创建 存在则跳过
			if (StringUtils.hasText(dev_id) && StringUtils.hasText(library_id)) {
				String libName = library_id + "_" + dev_id;
				checkIftheDBExist(libName);// check mem leak
			} else {
				result.setMessage("device_id and library_id is null,please check  device_id and library_id");
				return result;
			}
			String libName = library_id + "_" + dev_id;
			MongoInstance instance = mongoInstanceManager.getInstance(libName);
//			MongoClient client = null;
			//client = new MongoInstanceManager().initClient();
			try {
//				client = mongoDB.getDBClient(instance);
				// 不存在的情况下需要动态添加？
				if (instance != null) {
					map = (Map<String, Object>) map.get("content");
					if (map == null) {
						result.setMessage("content is null,please check  content");
						return result;
					}
					/**
					 * 外设状态日志
					 */
					if (map.containsKey(EnumClass.COLLECTION.ext_state
							.name())) {
						// 需要检查数据是否发生变化
						checkExtStateAndInsertStateLog(
								EnumClass.COLLECTION.ext_state.name(), client,
								instance.getOperDatabase(), map, dev_id);
						Insert(client, instance.getOperDatabase(),
								EnumClass.COLLECTION.ext_state.name(), map);
						result.setState(true);
					}
					if (map.containsKey("soft_state") ){
						// 需要检查数据是否发生变化
						checkExtStateAndInsertStateLog(EnumClass.COLLECTION.soft_state.name(),  client, instance.getOperDatabase(), map,dev_id);
						Insert(client, instance.getOperDatabase(),
								EnumClass.COLLECTION.soft_state.name(), map);
						result.setState(true);
					} 
					if (map.containsKey(EnumClass.COLLECTION.loan_log.name())) {
						if(map.get(EnumClass.COLLECTION.loan_log.name())!=null){
							JSONArray loan_logs = JSONArray.fromObject(map.get(EnumClass.COLLECTION.loan_log.name()));
							Map<String, Object> loanLogMap = new HashMap<String, Object>();  
							for (int i = 0;i<loan_logs.size();i ++){
								loanLogMap.clear();
								loanLogMap.put(EnumClass.COLLECTION.loan_log.name(), loan_logs.get(i));
								Insert(client, instance.getOperDatabase(),
										EnumClass.COLLECTION.loan_log.name(), loanLogMap);
							}
							result.setState(true);
						}
					}
				}
			} catch (Exception e) {
				result.setState(false);
				result.setMessage(e.getMessage());
				LOG.error(e.getMessage(), e);
				e.printStackTrace();
			} finally {
				//CloseUtil.close(client);
			}

		}
		return result;

	}
	/**
	 * 是否借还操作
	 * @param map
	 * @return
	 */
	private boolean isLoanLog(Map<String, Object> map) {
		boolean flag = false;
		if(MapUtils.isEmpty(map)){
			return false;
		}
		
		if(!map.containsKey("collection")){
			return false;
		}
		
		String collection = (String) map.get("collection");
		
		
		if (EnumClass.COLLECTION.loan_log.name().equals(collection)) {
			flag = true ;
		}
		return flag;
	}
	/**
	 * 根据library_id获取library_idx
	 * @param library_id
	 * @return
	 */
	private String getLibraryIdxByLibraryId(String library_id) {
		String libraryIdx = "";
		
		if(StringUtils.isEmpty(library_id)){
			
			return  libraryIdx;
		}
		
		String query = "{\"lib_id\":\"" + library_id + "\"}";
		
		Map<String, String> req = new HashMap<String, String>();
		req.put("json",query);
		String postUrl = requestURL.getRequestURL("selLibraryByIdxOrId");
		String result = HttpClientUtil.doPost(postUrl, req, Consts.UTF_8.toString());
		ResultEntity response = JsonUtils.fromJson(result, ResultEntity.class);
		if (response != null && response.getState()) {
			JSONArray libArray = JSONArray
					.fromObject(response.getResult());
			JSONObject value = libArray.getJSONObject(0);
			libraryIdx = value.optString("library_idx", "");
		}
		return libraryIdx;
	}

	/**
	 * 根据借还日志中的操作（opercmd字段）封装馆藏数据
	 * <br>1、借书：00010201,更新bookitem表中state为2-已借出
	 * <br>2、还书：00010202,更新bookitem表中state为1-已上架
	 * <br>3、续借：00010203,更新bookitem表中state为2-已借出
	 * 
	 * <p>state字段说明：(图书状态：0–已分派,1–已上架,2–已借出,3–已下架)
	 * @param map
	 */
	private Map<String, String>  setterBookItemState(Map<String, Object> map,String libraryIdx) {
		Map<String, String> bookItemMap = new HashMap<String, String>();
		//图书条码号
		String barCode = "" + map.get("Barcode");
		//操作结果
		String operCmd = "" + map.get("opercmd");
		//馆藏图书状态
		String  state = "";
		
		if("00010201".equals(operCmd) || "00010203".equals(operCmd) ){
			
			state = "2" ;
		}else if( "00010202".equals(operCmd)){
			state = "1" ;
		}
		
		if(StringUtils.isEmpty(barCode)){
			return null;
		}
		bookItemMap.put("state",state);
		bookItemMap.put("book_barcode",barCode);
		bookItemMap.put("lib_idx",libraryIdx);
		
		return bookItemMap;
	}

	/**
	 * 根据用户借阅信息更新推荐结果
	 * @param map
	 */
	private void updateRecommendResult(Map<String, Object> map,String libraryIdx) {
		//读者证号码
		String readerCardNo = (String) map.get("AuthCardno");
		//读者名称
		String readerName = (String) map.get("name");
		//借阅图书名称
		String bookName = (String) map.get("Title");
		
		//更新redis推荐结果
		updateRecommendResultInRedis(readerCardNo,readerName,bookName,libraryIdx);
	}
	/**
	 * 更新馆藏图书状态
	 * @param bookItemMap
	 */
	private void updateBookItemState(String request) {
		Map<String, String> req = new HashMap<String, String>();
		req.put("req",request);
		String postUrl = requestURL.getRequestURL("UPDATEBOOKITEM_STATE");
		String result = HttpClientUtil.doPost(postUrl, req, Consts.UTF_8.toString());
		if (!SUCCESS.equals(result)) {
			StackTraceElement element = Thread.currentThread().getStackTrace()[1];
			String clazz = element.getClassName();
			String method = element.getMethodName();
			LogUtils.info(clazz + "." + method + "()更新馆藏图书状态结果：" + result);
		}
		
	}
	
	/**
	 * 更新redis中推荐结果
	 * @param readerCardNo
	 * @param readerName
	 * @param bookName
	 * @param libraryIdx
	 */
	private void updateRecommendResultInRedis(String readerCardNo,
			String readerName, String bookName,String libraryIdx) {
		
		LogUtils.info("更新redis中推荐结果,start");
		//推荐记录key
		String recommendLogKey = "recommendLog:" + libraryIdx+":"+readerCardNo;//Redis的key值
		
		//推荐记录结果key
		String recommendResultKey = "recommendResult:" + libraryIdx+":"+readerCardNo;//Redis的key值

		//从Redis获取推荐记录
		String recommend = redis.get(recommendLogKey);
		
		if(StringUtils.isEmpty(recommend)||StringUtils.isEmpty(bookName)){
			
			return ;
		}
		
		Map<String,String> recommendResultMap = new HashMap<String,String>();
		
		if(recommend.contains(bookName)){
			
			recommendResultMap.put(bookName, "推荐成功");
			
			LogUtils.info("更新redis中推荐结果,key="+recommendResultKey+",value="+recommendResultMap);
			
			redis.hmset(recommendResultKey, recommendResultMap);
			redis.expire(recommendResultKey, RedisConstant.ONE_MONTH_EXPIRE_TIME);//30天后过期
		}
		
		LogUtils.info("更新redis中推荐结果,end");
	}

}
