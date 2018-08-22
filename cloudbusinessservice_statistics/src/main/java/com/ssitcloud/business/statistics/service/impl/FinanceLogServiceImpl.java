package com.ssitcloud.business.statistics.service.impl;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.statistics.entity.FinanceLogEntity;

import io.searchbox.client.config.exception.CouldNotConnectException;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.statistics.service.FinanceLogService;
@Service
public class FinanceLogServiceImpl extends BasicServiceImpl implements FinanceLogService {
	private static final String URL_QUERY_ALL_FINANCELOG_FROM_MONGODB = "queryAllFinanceLogFromMongodb";

	@Override
	public ResultEntity queryAllFinanceLog() {
		return null;
	}

	@Override
	public FinanceLogEntity queryEsLastFinancelog(String indexName,
                                                  String typeName) {
		indexName = indexName.replaceAll("#", INSTEAD_OF_SHAPE).toLowerCase();
		Search search = new Search.Builder("").addIndex(indexName).addType(typeName).build();
		try {
			SearchResult searchResult = getJestClient().execute(search);
			if (!searchResult.isSucceeded()  && searchResult.getErrorMessage().contains("no such index")) {
				//创建新的index
				createIndex(indexName, 1, 1);
				//创建之后，建立mapping
				FinanceLogEntity finance = new  FinanceLogEntity();
				XContentBuilder jsonBuilder = jsonBuilder();
				jsonBuilder = jsonBuilder.startObject()
						.field(Const.FINANCE_LOG.toUpperCase())
						.startObject().field("properties")
						.startObject();
				//"{ \"my_type\" : { \"properties\" : { \"message\" : {\"type\" : \"string\", \"store\" : \"yes\"} } } }"
				for (Field field : finance.getClass().getDeclaredFields()) {
					if (field.getName().equals("opertime") 
							|| field.getName().equals("TransDate")) {
						jsonBuilder = jsonBuilder.field(field.getName())
								.startObject()
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
				
				//创建mapping
				createTypeMapping(indexName, "FINANCE_LOG", jsonBuilder.string());
				
				return new FinanceLogEntity();
			}
			
		} catch (CouldNotConnectException e){
			System.out.println("------->连接不上elasticsearch");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new FinanceLogEntity();
	}

	@Override
	public ResultEntity queryAllFinancelogFromMongodb(String mongodbName) {
		return postURL(URL_QUERY_ALL_FINANCELOG_FROM_MONGODB, mongodbName);
	}

	@Override
	public void saveFinanceLog(String indexName, String typeName,
			FinanceLogEntity financeLogEntity) {
		saveOrUpdateDocument(indexName, typeName, financeLogEntity);

	}

	@Override
	public void saveFinanceLog(String indexName, String typeName,
			Map<String, Object> map) {
		saveOrUpdateDocument(indexName, typeName, map);

	}

}
