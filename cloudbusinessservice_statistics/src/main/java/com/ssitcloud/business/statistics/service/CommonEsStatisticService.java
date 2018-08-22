package com.ssitcloud.business.statistics.service;

import java.util.Map;

import net.sf.json.JSONObject;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 统计查询公共类
 * @author yyl
 *
 */
public interface CommonEsStatisticService {
	
	/**
	 * 读者维度 走statistics的查询
	 */
	public ResultEntity statisticsForReader(String req) ;
	
	/**
	 * 读者维度 通过数据源获得其名下的分组
	 * @param indexTab 索引
	 * @param gArr  分组
	 * @param map
	 * @param functions
	 * @param groupFlag 
	 * @param condition 查询条件
	 * @param topN
	 * @return
	 */
	public StringBuffer gtreeForReader(String[] indexTab, String[] gArr,
			Map<String, String> map, JSONObject functions, boolean groupFlag,JSONObject condition,int topN) ;
	
	/**
	 * 创建索引，并且设置主分片以及备用分片数量，一般情况下，主分片默认有一个备用分片
	 *
	 * <p>2017年11月08日 下午4:54:28 
	 * <p>create by yyl
	 * @param indexName 索引名称
	 * @param number_of_shards 主分片数量
	 * @param number_of_replicas  每个主分片对应的备用分片数量
	 * @return
	 */
	public boolean createIndex(String indexName, Integer number_of_shards, Integer number_of_replicas);	
	
	/**
	 * 判断索引是否存在
	 * @param indexName
	 * @param typeName
	 * @return
	 */
	public boolean isExistsIndex(String indexName,String typeName );
	
	/**
	 * 创建类型的映射，类似于定义关系型数据库的表结构，一旦定义，则不可修改: 如果要更改，则先将原索引给个别名，然后新建映射，移除原别名，将新建的映射指定原索引别名
	 * <p>2017年11月08日 下午4:54:28 
	 * <p>create by yyl
	 * @param indexName
	 * @param typeName
	 * @param mappingStr
	 * @return
	 */
	public boolean createTypeMapping(String indexName, String typeName, String mappingStr);
	
	/**
	 * 根据实体类映射es Mapping
	 * <p>2017年11月08日 下午4:54:28 
	 * <p>create by yyl
	 * @param indexName
	 * @param typeName
	 * @param obj
	 * @return
	 */
	public <T> String setTypeMapping(String indexName, String typeName, T obj) ;
	
	/**
	 * 插入或者更新文档，如果保存的实体没有id，则es自动生成,
	 * <br>如果该ID的数据已经存在，则强制更新
	 * <br>新增成功之后，返回此条数据(包括自增ID)
	 * <br>elasticsearch 不存在更新操作，只有替换
	 * <p>2017年11月09日 
	 * <p>create by yyl
	 * @param indexName 索引名称
	 * @param typeName 类型名称
	 * @param entity 要保存的实体
	 * @return
	 */
	public <T> T saveOrUpdateDocument(String indexName, String typeName, T entity);
	
	/**
	 * 获取查询模板数据
	 * @param qb
	 * @param termsBuilder
	 * @param fieldArr
	 * @param sortArr
	 * @param pageNo
	 * @param pageSize
	 * @param indices
	 * @return
	 */
	public  JSONObject searchIndex(QueryBuilder qb,TermsBuilder termsBuilder,String[] fieldArr,String[] sortArr,int pageNo,int pageSize,String[] indices);
	
	/**
	 *  组装查询条件
	 * @param json
	 * @return
	 */
	public BoolQueryBuilder  getBqb(JSONObject condition);
	/**
	 * 获取统计数据
	 */
	public ResultEntity getData(String req) ;
}
