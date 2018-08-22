package com.ssitcloud.datasync.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.FtpUtils;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.datasync.service.DataSyncCommand;

@Component(value="askUploadOsbSync")
public class AskUploadOsbSync extends BasicServiceImpl implements DataSyncCommand{
	
	public static final String URL_SELDEVICECODE = "selectDeviceCode";

	@Override
	public RespEntity execute(RequestEntity requestEntity) {
		RespEntity resp = new RespEntity(requestEntity);
		ResultEntity resEntity=new ResultEntity();
		Map<String, Object> map = requestEntity.getData();
		
		try {
			String library_id=(String) map.get("library_id");
			String device_id=(String) map.get("device_id");
			String username = null;
			String passwd = "000000";
			
			if(StringUtils.hasLength(library_id) && StringUtils.hasLength(device_id)){
				
				Map<String, Object> m = new HashMap<>();
    			m.put("device_id", device_id);
				Map<String, String> p = new HashMap<>();
				p.put("req", JsonUtils.toJson(m));
				String str = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_SELDEVICECODE), p, charset);
				if (JSONUtils.mayBeJSON(str)) {
            		ResultEntity res = JsonUtils.fromJson(str, ResultEntity.class);
            		if(res.getState() && res.getResult() != null){
            			username = device_id;
            			passwd = res.getResult().toString().substring(0, 8);
            		}
				}
				
				String ftpInfo = FtpUtils.createDirectory(username,passwd,"/upload");
				if(StringUtils.hasLength(ftpInfo)){
					resEntity.setState(true);
					resEntity.setResult(ftpInfo);
				}else{
					resEntity.setState(false);
					resEntity.setMessage("访问ftp服务出错");
				}
			}else{
				resEntity.setState(false);
				resEntity.setMessage("请求参数格式不正确,必须参数包括library_id、device_id");
			}
			
			resp.setData(resEntity);
		} catch (Exception e) {
			ExceptionHelper.afterException(resp, Thread.currentThread(), e);
		}

		return resp;
	}
}
