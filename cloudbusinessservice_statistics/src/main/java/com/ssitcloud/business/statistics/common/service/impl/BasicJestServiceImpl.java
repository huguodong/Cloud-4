package com.ssitcloud.business.statistics.common.service.impl;

import com.ssitcloud.business.statistics.entity.BookItemEntity;
import com.ssitcloud.common.entity.Const;
import com.ssitcloud.statistics.entity.*;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.exception.CouldNotConnectException;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.mapping.PutMapping;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.statistics.common.service.BasicJestService;
import com.ssitcloud.business.statistics.common.utils.LogUtils;

@Service
public class BasicJestServiceImpl implements BasicJestService{
	
	@Autowired
	private JestClient jestClient;
	
	/** #号替换  **/
	public static final String INSTEAD_OF_SHAPE = "";
	
	public JestClient getJestClient() {
		return jestClient;
	}
	
	/**
	 * json构造工具
	 * startXXX需要跟endXXX一一对应
	 * String source = jsonBuilder()
		.startObject()
		.field("user", "kimchy")
		.field("postDate", "date")
		.field("message", "trying out Elastic Search")
		.endObject().string();
	 *
	 * <p>2017年3月15日 下午5:17:05 
	 * <p>create by hjc
	 * @return
	 * @throws IOException
	 */
	public XContentBuilder jsonBuilder() throws IOException{
		return XContentFactory.jsonBuilder();
	}
	
	/**
	 * 创建es索引，索引类似关系型数据库中的数据库
	 *
	 * <p>2017年3月13日 下午4:29:17 
	 * <p>create by hjc
	 * @param indexName
	 * @return
	 */
	public boolean createIndex(String indexName){
		return createIndex(indexName, 1, 1);//默认一个主分片
	}
	
	/**
	 * 创建索引，并且设置主分片以及备用分片数量，一般情况下，主分片默认有一个备用分片
	 *
	 * <p>2017年3月13日 下午4:54:28 
	 * <p>create by hjc
	 * @param indexName 索引名称
	 * @param number_of_shards 主分片数量
	 * @param number_of_replicas  每个主分片对应的备用分片数量
	 * @return
	 */
	public boolean createIndex(String indexName, Integer number_of_shards, Integer number_of_replicas) {
		if (indexName.contains("#")) {
			indexName = indexName.replaceAll("#", INSTEAD_OF_SHAPE);
		}
		indexName = indexName.toLowerCase();
		try {
			if(number_of_shards == null){
				number_of_shards = 1;
			}
			if (number_of_replicas == null) {
				number_of_replicas = number_of_shards.intValue();
			}
			Settings.Builder settingBuilder = Settings.builder();
			settingBuilder.put("number_of_shards",number_of_shards);
			settingBuilder.put("number_of_replicas",number_of_replicas);
			
			CreateIndex createIndex = new CreateIndex.Builder(indexName).settings(settingBuilder.build()).build();
			JestResult jestResult = jestClient.execute(createIndex);
			
			if (jestResult != null) {
				if (!jestResult.isSucceeded()) {
					throw new RuntimeException("创建索引失败!" + jestResult.getErrorMessage());
				}
			}else {
				throw new RuntimeException("创建索引失败!");
			}
		} catch (Exception e) {
			LogUtils.error("创建索引【" + indexName + "】失败",e);
			return false;
		}
		return true;
	}
	
	
	/**
	 * 删除索引 
	 *
	 * <p>2017年3月13日 下午5:09:27 
	 * <p>create by hjc
	 * @param indexName 索引名称
	 * @return
	 */
	public boolean deleteIndex(String indexName) {
		if (indexName.contains("#")) {
			indexName = indexName.replaceAll("#", INSTEAD_OF_SHAPE);
		}
		indexName = indexName.toLowerCase();
		try {
			DeleteIndex deleteIndex = new DeleteIndex.Builder(indexName).build();
			JestResult jestResult = jestClient.execute(deleteIndex);
			if (jestResult != null) {
				if (!jestResult.isSucceeded()) {
					throw new RuntimeException("删除索引失败!" + jestResult.getErrorMessage());
				}
			}else {
				throw new RuntimeException("删除索引失败!");
			}
		} catch (Exception e) {
			LogUtils.error("删除索引【" + indexName + "】失败",e);
			return false;
		}
		return true;
	}
	
	/**
	 * 创建类型的映射，类似于定义关系型数据库的表结构，一旦定义，则不可修改
	 *
	 * <p>2017年3月13日 下午6:27:39 
	 * <p>create by hjc
	 * @param indexName
	 * @param typeName
	 * @param mappingStr
	 * @return
	 */
	public boolean createTypeMapping(String indexName, String typeName, String mappingStr){
		if (indexName.contains("#")) {
			indexName = indexName.replaceAll("#", INSTEAD_OF_SHAPE);
		}
		indexName = indexName.toLowerCase();
		PutMapping putMapping = new PutMapping.Builder(indexName, typeName, mappingStr).build();
		try {
			JestResult jestResult = jestClient.execute(putMapping);
			if (jestResult != null) {
				if (!jestResult.isSucceeded()) {
					throw new RuntimeException("创建mapping失败!" + jestResult.getErrorMessage());
				}
			}else {
				throw new RuntimeException("创建mapping失败!");
			}
		} catch (IOException e) {
			LogUtils.error("创建【" + indexName + "-" + typeName + "】mapping失败",e);
			return false;
		}
		return true;
	}
	
	/**
	 * 获取index中type各个字段的映射
	 *
	 * <p>2017年3月14日 上午10:25:15 
	 * <p>create by hjc
	 * @param indexName
	 * @param typeName
	 * @return
	 */
	public String getMapping(String indexName, String typeName){
		if (indexName.contains("#")) {
			indexName = indexName.replaceAll("#", INSTEAD_OF_SHAPE);
		}
		indexName = indexName.toLowerCase();
		try {
			GetMapping getMapping = new GetMapping.Builder().addIndex(indexName).addType(typeName).build();
			JestResult jestResult = jestClient.execute(getMapping);
			if (jestResult != null && jestResult.isSucceeded()) {
				return jestResult.getJsonString();
			}else {
				throw new RuntimeException("获取映射失败!" + jestResult==null?"":jestResult.getErrorMessage());
			}
		} catch (Exception e) {
			LogUtils.error("获取【" + indexName + "-" + typeName + "】mapping失败",e);
			return null;
		}
	}
	
	/**
	 * 
	 *
	 * <p>2017年3月14日 上午11:28:07 
	 * <p>create by hjc
	 * @param indexName
	 * @param typeName
	 * @param mapping
	 * @return
	 */
	public String updateMapping(String indexName, String typeName, String mapping){
		if (indexName.contains("#")) {
			indexName = indexName.replaceAll("#", INSTEAD_OF_SHAPE);
		}
		indexName = indexName.toLowerCase();
		try {
			GetMapping getMapping = new GetMapping.Builder().addIndex(indexName).addType(typeName).build();
			JestResult jestResult = jestClient.execute(getMapping);
			if (jestResult != null && jestResult.isSucceeded()) {
				return jestResult.getJsonString();
			}else {
				throw new RuntimeException("获取映射失败!" + jestResult==null?"":jestResult.getErrorMessage());
			}
		} catch (Exception e) {
			LogUtils.error("获取【" + indexName + "-" + typeName + "】mapping失败",e);
			return null;
		}
	}
	
	
	/**
	 * 插入或者更新文档，如果保存的实体没有id，则es自动生成,
	 * <br>如果该ID的数据已经存在，则强制更新
	 * <br>新增成功之后，返回此条数据(包括自增ID)
	 * <br>elasticsearch 不存在更新操作，只有替换
	 * <p>2017年3月14日 下午5:30:14 
	 * <p>create by hjc
	 * @param indexName 索引名称
	 * @param typeName 类型名称
	 * @param entity 要保存的实体
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T saveOrUpdateDocument(String indexName, String typeName, T entity){
		if (indexName.contains("#")) {
			indexName = indexName.replaceAll("#", INSTEAD_OF_SHAPE);
		}
		indexName = indexName.toLowerCase();
		try {
			Map<String, Object> m = (Map<String, Object>) entity;
			Index index = new Index.Builder(entity).index(indexName)
					.type(typeName).id(String.valueOf(m.get("id"))).build();
			DocumentResult documentResult = jestClient.execute(index);
			if (documentResult != null ) {
				if(documentResult.isSucceeded()){
					Get get = new Get.Builder(indexName, documentResult.getId()).type(typeName).build();
					DocumentResult documentResult2 = jestClient.execute(get);
					entity = (T) documentResult2.getSourceAsObject(entity.getClass());
				}else{
					System.out.println("saveOrUpdateDocument失败,【indexName】" + indexName+"---------------【typeName】" + typeName +"---------------【entity】" + entity.toString());
					throw new RuntimeException("saveOrUpdateDocument失败!" + documentResult.getErrorMessage());
				}
			}else {
				System.out.println("saveOrUpdateDocument失败,【indexName】" + indexName+"---------------【typeName】" + typeName +"---------------【entity】" + entity.toString());
				throw new RuntimeException("saveOrUpdateDocument失败!");
			}
		} catch (CouldNotConnectException e){
        	System.out.println("------->连接不上elasticsearch");
		} catch (Exception e) {
			System.out.println("saveOrUpdateDocument失败,【indexName】" + indexName+"---------------【typeName】" + typeName +"---------------【entity】" + entity.toString());
			LogUtils.error("saveOrUpdateDocument失败",e);
		}
		return entity;
	}
	
	
	/**
	 * 删除单条数据
	 *
	 * <p>2017年3月15日 下午3:24:28 
	 * <p>create by hjc
	 * @param indexName
	 * @param typeName
	 * @return
	 */
	public boolean deleteDocument(String indexName, String typeName, String id){
		if (indexName.contains("#")) {
			indexName = indexName.replaceAll("#", INSTEAD_OF_SHAPE);
		}
		indexName = indexName.toLowerCase();
		try {
			Delete delete = new Delete.Builder(id).index(indexName).type(typeName).build();
			DocumentResult documentResult = jestClient.execute(delete);
			
			if (documentResult != null ) {
				if(!documentResult.isSucceeded()){
					throw new RuntimeException("删除文档数据失败!index:" + documentResult.getErrorMessage());
				}
			}else {
				throw new RuntimeException("删除文档数据失败!index:" + indexName + ",type:" + typeName + ",id:" + id);
			}
		} catch (CouldNotConnectException e){
        	System.out.println("------->连接不上elasticsearch");
		} catch (Exception e) {
			LogUtils.error("删除文档数据失败",e);
			return false;
		}
		return true;
	}
	
	
	/**
	 * 获取单条文本数据，返回对应的实体
	 *
	 * <p>2017年3月16日 上午11:13:09 
	 * <p>create by hjc
	 * @param <T>
	 * @param indexName
	 * @param typeName
	 * @param id
	 * @return
	 */
	public <T> T getDocument(String indexName, String typeName, String id, Class<T> clazz){
		if (indexName.contains("#")) {
			indexName = indexName.replaceAll("#", INSTEAD_OF_SHAPE);
		}
		indexName = indexName.toLowerCase();
		try {
			Get get = new Get.Builder(indexName, id).type(typeName).build();
			DocumentResult documentResult = jestClient.execute(get);
			
			if (documentResult!=null) {
				if (!documentResult.isSucceeded()) {
					throw new RuntimeException("获取单条文档数据失败！" + documentResult.getErrorMessage());
				}else {
					return documentResult.getSourceAsObject(clazz);
				}
			}
		} catch (CouldNotConnectException e){
        	System.out.println("------->连接不上elasticsearch");
		} catch (Exception e) {
			LogUtils.error("获取单条文档数据失败",e);
		}
		return null;
	}
	
	
	/**
	 * 查询单条数据，返回查询结果
	 *
	 * <p>2017年3月16日 下午3:11:54 
	 * <p>create by hjc
	 * @param indexName
	 * @param typeName
	 * @param id
	 * @return
	 */
	public DocumentResult getDocumentResult(String indexName, String typeName, String id){
		if (indexName.contains("#")) {
			indexName = indexName.replaceAll("#", INSTEAD_OF_SHAPE);
		}
		indexName = indexName.toLowerCase();
		try {
			Get get = new Get.Builder(indexName, id).type(typeName).build();
			DocumentResult documentResult = jestClient.execute(get);
			
			if (documentResult!=null) {
				if (!documentResult.isSucceeded()) {
					throw new RuntimeException("获取单条文档数据失败！" + documentResult.getErrorMessage());
				}else {
					return documentResult;
				}
			}
		} catch (CouldNotConnectException e){
        	System.out.println("------->连接不上elasticsearch");
		} catch (Exception e) {
			LogUtils.error("获取单条文档数据失败",e);
		}
		return null;
	}
	
	
	/**
	 * 判断index 是否存在
	 *
	 * <p>2017年4月5日 下午7:04:00 
	 * <p>create by hjc
	 * @param indexName
	 * @param typeName
	 */
	public boolean checkIndexExisted(String indexName, String typeName) {
		indexName = indexName.replaceAll("#", INSTEAD_OF_SHAPE).toLowerCase();
		Search search = new Search.Builder("").addIndex(indexName).addType(typeName).build();
		try {
			SearchResult searchResult = getJestClient().execute(search);
			if (!searchResult.isSucceeded()  && searchResult.getErrorMessage().contains("no such index")) {
				//创建新的index
				createIndex(indexName, 1, 1);
				//创建mapping
				if (indexName.indexOf(Const.LOAN_LOG + "_" + Const.STATISTICS)>-1) {
					createLoanMapping(indexName,typeName);
				}else if(indexName.indexOf(Const.CARDISSUE_LOG + "_" + Const.STATISTICS)>-1){
					createCardMapping(indexName,typeName);
				}else if(indexName.indexOf(Const.FINANCE_LOG + "_" + Const.STATISTICS)>-1){
					createFineMapping(indexName,typeName);
				}else if(indexName.indexOf(Const.BOOKITEM)>-1){
                    createBookitemMapping(indexName,typeName);
                }
				
			}
		} catch (CouldNotConnectException e){
        	System.out.println("------->连接不上elasticsearch");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 创建流通的mapping
	 * author huanghuang
	 * 2017年8月22日 下午3:45:50
	 * @param indexName
	 * @param typeName
	 */
	private void createLoanMapping(String indexName, String typeName) {
		String upperTypeName = typeName.toUpperCase();
		try {
			Object obj = null;
			switch (typeName) {
			case Const.YEAR:
				obj = new CirculationYearDataEntity();
				break;
			case Const.MONTH:
				obj = new CirculationMonthDataEntity();
				break;
			case Const.DAY:
				obj = new CirculationDayDataEntity();
				break;
			case Const.WEEK:
				obj = new CirculationWeekDataEntity();
				break;

			default:
				break;
			}
			if(obj!=null){
				XContentBuilder jsonBuilder = jsonBuilder();
				jsonBuilder = jsonBuilder.startObject()
						.field(upperTypeName)
						.startObject().field("properties")
						.startObject();
				for (Field field : obj.getClass().getDeclaredFields()) {
					String type = field.getGenericType().toString();
					type = type.substring(type.lastIndexOf(".")+1);
					jsonBuilder = jsonBuilder
							.startObject(field.getName()+"_group")
							.field("type",type)
							.field("index","not_analyzed")
							.endObject()
							.startObject(field.getName())
							.field("type",type)
							.endObject();
				}
				//国家省市区的mapping start
				jsonBuilder = jsonBuilder
    					.startObject("country")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject()
    					.startObject("province")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject()
    					.startObject("city")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject()
    					.startObject("area")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject();
				//国家省市区的mapping end
				jsonBuilder = jsonBuilder.endObject().endObject().endObject();
				createTypeMapping(indexName, upperTypeName, jsonBuilder.string());
			}
		} catch (Exception e) {
			throw new RuntimeException("createLoanMapping is error", e);
		}
	}

	/**
	 * 创建办证的mapping
	 * author huanghuang
	 * 2017年8月22日 下午3:45:56
	 * @param indexName
	 * @param typeName
	 */
	private void createCardMapping(String indexName, String typeName) {
		String upperTypeName = typeName.toUpperCase();
		try {
			Object obj = null;
			switch (typeName) {
			case Const.YEAR:
				obj = new NewCardYearDataEntity();
				break;
			case Const.MONTH:
				obj = new NewCardMonthDataEntity();
				break;
			case Const.DAY:
				obj = new NewCardDayDataEntity();
				break;
			case Const.WEEK:
				obj = new NewCardWeekDataEntity();
				break;

			default:
				break;
			}
			if(obj!=null){
				XContentBuilder jsonBuilder = jsonBuilder();
				jsonBuilder = jsonBuilder.startObject()
						.field(upperTypeName)
						.startObject().field("properties")
						.startObject();
				for (Field field : obj.getClass().getDeclaredFields()) {
					String type = field.getGenericType().toString();
					type = type.substring(type.lastIndexOf(".")+1);
					jsonBuilder = jsonBuilder
							.startObject(field.getName()+"_group")
							.field("type",type)
							.field("index","not_analyzed")
							.endObject()
							.startObject(field.getName())
							.field("type",type)
							.endObject();
				}
				//国家省市区的mapping start
				jsonBuilder = jsonBuilder
    					.startObject("country")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject()
    					.startObject("province")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject()
    					.startObject("city")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject()
    					.startObject("area")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject();
				//国家省市区的mapping end
				jsonBuilder = jsonBuilder.endObject().endObject().endObject();
				createTypeMapping(indexName, upperTypeName, jsonBuilder.string());
			}
		} catch (Exception e) {
			throw new RuntimeException("createCardMapping is error", e);
		}
	}


	/**
	 * 创建财经的mapping
	 * author huanghuang
	 * 2017年8月22日 下午3:21:28
	 * @param indexName
	 * @param typeName
	 */
	private void createFineMapping(String indexName, String typeName) {
		String upperTypeName = typeName.toUpperCase();
		try {
			Object obj = null;
			switch (typeName) {
			case Const.YEAR:
				obj = new FineYearDataEntity();
				break;
			case Const.MONTH:
				obj = new FineMonthDataEntity();
				break;
			case Const.DAY:
				obj = new FineDayDataEntity();
				break;
			case Const.WEEK:
				obj = new FineWeekDataEntity();
				break;

			default:
				break;
			}
			if(obj!=null){
				XContentBuilder jsonBuilder = jsonBuilder();
				jsonBuilder = jsonBuilder.startObject()
						.field(upperTypeName)
						.startObject().field("properties")
						.startObject();
				for (Field field : obj.getClass().getDeclaredFields()) {
					String type = field.getGenericType().toString();
					type = type.substring(type.lastIndexOf(".")+1);
					jsonBuilder = jsonBuilder
							.startObject(field.getName()+"_group")
							.field("type",type)
							.field("index","not_analyzed")
							.endObject()
							.startObject(field.getName())
							.field("type",type)
							.endObject();
				}
				//国家省市区的mapping start
				jsonBuilder = jsonBuilder
    					.startObject("country")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject()
    					.startObject("province")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject()
    					.startObject("city")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject()
    					.startObject("area")
    					.field("type","string")
    					.field("index","not_analyzed")
    					.endObject();
				//国家省市区的mapping end
				jsonBuilder = jsonBuilder.endObject().endObject().endObject();
				createTypeMapping(indexName, upperTypeName, jsonBuilder.string());
			}
		} catch (Exception e) {
			throw new RuntimeException("createFineMapping is error", e);
		}
	}

    private void createBookitemMapping(String indexName, String typeName) {
        String upperTypeName = typeName.toUpperCase();
        try {
            BookItemEntity obj = new BookItemEntity();
            XContentBuilder jsonBuilder = jsonBuilder();
            jsonBuilder = jsonBuilder.startObject()
                    .field(upperTypeName)
                    .startObject().field("properties")
                    .startObject();
            for (Field field : obj.getClass().getDeclaredFields()) {
            	if (field.getName().equals("createtime")) {
					jsonBuilder = jsonBuilder.field(field.getName())
							.startObject()
							.field("type","date")
							.field("index","not_analyzed")
							.field("format","yyyy-MM-dd HH:mm:ss")
							.endObject();
				}else if (field.getName().equals("regdate")) {
					String type = field.getGenericType().toString();
					type = type.substring(type.lastIndexOf(".")+1);
					jsonBuilder = jsonBuilder.field(field.getName())
							.startObject()
							.field("type","date")
							.field("index","not_analyzed")
							.field("format","yyyy-MM-dd HH:mm:ss")
							.endObject()
							.startObject(field.getName()+"_group")
							.field("type","date")
							.field("index","not_analyzed")
							.field("format","yyyy-MM-dd HH:mm:ss")
							.endObject();
				}else{//除了日期外，其余的字段都有个分组的和不分组的	add by huanghuang 20170822
					String type = field.getGenericType().toString();
					type = type.substring(type.lastIndexOf(".")+1);
					jsonBuilder = jsonBuilder
							.startObject(field.getName()+"_group")
							.field("type",type)
							.field("index","not_analyzed")
							.endObject()
							.startObject(field.getName())
							.field("type",type)
							.endObject();
				}
            }
            jsonBuilder = jsonBuilder
					.startObject("callNumber")
					.field("type","string")
					.field("index","not_analyzed")
					.endObject();
			//国家省市区的mapping start
			jsonBuilder = jsonBuilder
					.startObject("country")
					.field("type","string")
					.field("index","not_analyzed")
					.endObject()
					.startObject("province")
					.field("type","string")
					.field("index","not_analyzed")
					.endObject()
					.startObject("city")
					.field("type","string")
					.field("index","not_analyzed")
					.endObject()
					.startObject("area")
					.field("type","string")
					.field("index","not_analyzed")
					.endObject();
			//国家省市区的mapping end
            jsonBuilder = jsonBuilder.endObject().endObject().endObject();
            createTypeMapping(indexName, upperTypeName, jsonBuilder.string());
        } catch (Exception e) {

        }
    }
	

}
