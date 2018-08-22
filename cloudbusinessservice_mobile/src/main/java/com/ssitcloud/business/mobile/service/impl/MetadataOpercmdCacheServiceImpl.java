package com.ssitcloud.business.mobile.service.impl;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.common.util.ObjectUtils;
import com.ssitcloud.business.mobile.service.MetadataOpercmdCacheServiceI;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.MetadataOpercmdEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("MetadataOpercmdCacheService")
public class MetadataOpercmdCacheServiceImpl extends AbstractCacheServiceImpl<String, MetadataOpercmdEntity> implements MetadataOpercmdCacheServiceI{
	
	private final String UPDATE_CMD_URL="selectMetaOperCmd";
	private final long TIME_OUT = 1*24*60*60*1000;//1 day
	
	@Resource(name = "requestURL")
	private RequestURLListEntity requestUrl;
	private final String charset = "utf-8";
	
	@Async
	@Override
	public void updateAsync() {
		update();
	}

	@Override
	protected long getTimeOut() {
		return TIME_OUT;
	}

	@Override
	protected List<MetadataOpercmdEntity> getSourceData()
			throws com.ssitcloud.business.mobile.service.impl.AbstractCacheServiceImpl.CanNotObtainDataException {
		return getMetadataOpercmd(null);
	}

	@Override
	protected String getSourceDataKey(MetadataOpercmdEntity v) {
		return v.getOpercmd();
	}

	@Override
	protected MetadataOpercmdEntity getSourceData(String k) throws CanNotObtainDataException {
        MetadataOpercmdEntity me = new MetadataOpercmdEntity();
        me.setOpercmd(k);
        List<MetadataOpercmdEntity> metadataOpercmd = getMetadataOpercmd(me);
        if(metadataOpercmd.size() == 1){
            return metadataOpercmd.get(0);
        }
        throw new CanNotObtainDataException();
	}

	private List<MetadataOpercmdEntity> getMetadataOpercmd(MetadataOpercmdEntity me) throws com.ssitcloud.business.mobile.service.impl.AbstractCacheServiceImpl.CanNotObtainDataException {
        Map<String, String> map;
        if(me == null){
            map = Collections.emptyMap();
        }else{
            map = new HashMap<>(3);
            map.put("json",JsonUtils.toJson(me));
        }

        String url = requestUrl.getRequestURL(UPDATE_CMD_URL);
        String resultJson = HttpClientUtil.doPost(url, map , charset );
        try{
            ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
            if(resultEntity.getState()){
                List<Map<String, Object>> lMapList = (List<Map<String, Object>>) resultEntity.getResult();
                List<MetadataOpercmdEntity> rd = new ArrayList<>(lMapList.size());
                for (Map<String, Object> lmap : lMapList) {
                    try{
                        MetadataOpercmdEntity moe = ObjectUtils.convertMap(lmap, MetadataOpercmdEntity.class);
                        if(moe != null && moe.getOpercmd() != null){
                            rd.add(moe);
                        }
                    }catch(NoSuchMethodException e){
                        LogUtils.debug(ObjectUtils.class+" Map转换 MetadataOpercmdEntity出错",e);
                    }
                }

                return rd;
            }
        }catch(Exception e){
            LogUtils.info(url+" 远程服务器没有返回预期格式数据",e);
        }

        throw new CanNotObtainDataException();
    }
}
