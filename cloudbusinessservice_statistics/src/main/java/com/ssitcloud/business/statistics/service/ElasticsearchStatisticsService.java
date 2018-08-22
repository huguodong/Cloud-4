package com.ssitcloud.business.statistics.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ssitcloud.authentication.entity.RelLibsEntity;
import com.ssitcloud.common.entity.ResultEntity;
/**
 * 从Elasticsearch获取查询与统计模板的数据
 * @author lqw  2017年4月8号
 *
 */
public interface ElasticsearchStatisticsService {
	/**
	 * 统计模板
	 * @param req
	 * @return
	 */
	ResultEntity statistics(String req);
	/**
	 * 查询模板
	 * @param req
	 * @return
	 */
	String query(String req);
	 /**
	 * 通过数据源获得其名下的分组
	 * author huanghuang
	 * 2017年4月10日 上午9:55:00
	 * @param indexTab 索引名
	 * @param gArr 组
	 * @param groupFlag
	 * @param condition 查询条件
	 * @Param topN
	 * @return
	 */
	StringBuffer gtree(String[] indexTab,String[] gArr,Map<String,String> map,JSONObject functions,boolean groupFlag,JSONObject condition,int topN);
	/**
	 * 导出查询
	 * author huanghuang
	 * 2017年5月31日 下午6:48:38
	 * @param req
	 * @return
	 */
	String exportSelect(String req);
	/**
	 * 设备的缓存
	 * author huanghuang
	 * 2017年5月31日 下午6:48:53
	 * @param key
	 * @return
	 */
	JSONArray takeDev(Integer key);
	/**
	 * 图书馆的缓存
	 * author huanghuang
	 * 2017年5月31日 下午6:48:58
	 * @param key
	 * @return
	 */
	JSONObject takeLib(Integer key);
	/**
	 * 查询子馆
	 * author huanghuang
	 * 2017年5月31日 下午6:49:02
	 * @param key
	 * @return
	 */
	List<RelLibsEntity> takeRelLibs(Integer key);
	
    /**
     * 动态拼接sql查询鉴权库
     * author lqw
     * 2017年8月29日 上午10:55:23
     * @param req
     * @return
     */
    ResultEntity selectAutBySql(String req);
    
    
    String[] libArr(String[] indexTab,String[] gArr);
    
    String[] devArr(String[] indexTab,String[] gArr);
}
