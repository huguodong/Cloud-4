package com.ssitcloud.common.system;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.Assert;

import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.datasync.entity.MetadataOpercmdTableEntity;

/**
 * 定时获取metadata_opercmd_table表中的数据保存到缓存总
 *
 * <p>2017年8月29日 下午1:50:34  
 * @author hjc 
 *
 */
public class ScheduledCmdTable {
	
	@Resource(name = "requestURLListEntity")
	private RequestURLListEntity requestURLListEntity;
	
	/*@Resource(name="CMDTABLE_CACHE")
	private Cache cache;
	*/
	private final static String charset=Consts.UTF_8.toString();
	
	private static final String URL_QUERYALLOPERCMDTABLE = "queryAllOpercmdTable";
	
	/**
	 * 定时更新mainfield的字段到缓存中
	 *
	 * <p>2017年3月3日 下午6:30:37 
	 * <p>create by hjc
	 */
	/*public void updateOpercmdTable(){
		Assert.notNull(cache, "Need a cache. Please use setCache(Cache) create it.");  
		System.out.println("###########################获取opercmdTable信息##################################");
		String result = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_QUERYALLOPERCMDTABLE), new HashMap<String, String>(), charset);
		if (StringUtils.isNotBlank(result)) {
			ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
			if (resultEntity.getState() && resultEntity.getResult() instanceof List) {
				List<MetadataOpercmdTableEntity> list = (List<MetadataOpercmdTableEntity>) JsonUtils
						.fromJson(JsonUtils.toJson(resultEntity.getResult()),
								new TypeReference<List<MetadataOpercmdTableEntity>>() {});
				for (MetadataOpercmdTableEntity opercmd : list) {
					String key = opercmd.getOpercmd_id();
					if(StringUtils.isNotBlank(key)){
						Element element = new Element(key, opercmd);
						cache.put(element);
					}
				}
			}
		}
	}*/
	

}
