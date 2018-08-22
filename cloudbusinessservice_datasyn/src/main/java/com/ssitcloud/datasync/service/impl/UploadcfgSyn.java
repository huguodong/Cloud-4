package com.ssitcloud.datasync.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.datasync.entity.UploadcfgSynResultEntity;
import com.ssitcloud.datasync.service.DataSyncCommand;
import com.ssitcloud.library.service.LibraryService;

@Component(value="uploadcfgSyn")
public class UploadcfgSyn extends BasicServiceImpl implements DataSyncCommand{
	
	public static final String uploadcfgSyn_table = "table";
	public static final String uploadcfgSyn_fields = "fields";
	public static final String uploadcfgSyn_records = "records";
	public static final String URL_UPLOADCFGSYNC = "uploadcfgSyn";
	private static final String URL_selLibraryByIdxOrId = "selLibraryByIdxOrId";
	
	/*@Resource(name="LIBRARY_CACHE")
	private Cache librarycache;*/
	@Resource
	private LibraryService libService;
	@Override
	public RespEntity execute(RequestEntity conditionInfo) {
		RespEntity resp = new RespEntity(conditionInfo);
		Map<String, Object> map = conditionInfo.getData();
		
		if (map.containsKey(uploadcfgSyn_table) && map.containsKey(uploadcfgSyn_fields)
				&& map.containsKey(uploadcfgSyn_records)) {
			Map<String, String> params = new HashMap<>();
			String lib_id = map.get(LIB_ID) + "";
			String lib_idx = "";
			if (StringUtils.hasLength(lib_id)) {
				lib_idx = libService.getLibraryIdxById(lib_id)+"";
				/*if(librarycache != null){
					Element e = librarycache.get(lib_id);
					if(e!=null){
						lib_idx = (String) e.getObjectValue();
					}
				}*/
			}
			try {
				map.put("library_id", lib_idx);
				map.put("lib_id", lib_id);
				params.put("req", JsonUtils.toJson(map));
			} catch (Exception e) {
				e.printStackTrace();
				resp.getData().setRetMessage(e.getMessage());
				return resp;
			}
			String result = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_UPLOADCFGSYNC), params, charset);
			if (StringUtils.hasLength(result)) {// ResultEntity.java
				resp.setData(JsonUtils.fromJson(result, ResultEntity.class));
			}
		} else {// 缺少字段uploadcfgSyn_table /uploadcfgSyn_fields/
				// uploadcfgSyn_records
			resp.getData().setState(false);
			UploadcfgSynResultEntity uploadcfgSynFailResult = new UploadcfgSynResultEntity(
					map.get(DEV_ID) + "", map.get(LIB_ID) + "", "0", "缺少字段table或者fields或者records");
			resp.getData().setResult(uploadcfgSynFailResult);
		}

		return resp;
	}
}
