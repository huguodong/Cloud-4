package com.ssitcloud.devmgmt.service.impl;

import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.devmgmt.service.MetadataOperCmdService;
import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * Created by LXP on 2017/9/5.
 */
@Service
public class MetadataOperCmdServiceImpl implements MetadataOperCmdService {
    @Resource(name = "requestURLListEntity")
    private RequestURLListEntity requestURL;

    @Override
    public ResultEntity getMetadataOperCmd() {
        ResultEntity resultEntity = new ResultEntity();
        String url = requestURL.getRequestURL("SelectMetaOperCmd");
        String s = HttpClientUtil.doPost(url, Collections.<String, String>emptyMap(), Consts.UTF_8.toString());
        if (s != null) {
            try {
                ResultEntity r = JsonUtils.fromJson(s, ResultEntity.class);
                resultEntity.setState(r.getState());
                resultEntity.setResult(r.getResult());
                return resultEntity;
            } catch (Exception e) {
                LogUtils.error(getClass() + " getMetadataOperCmd 转换消息为ResultEntity出错 exception message->" + e.getMessage());
            }
        }else{
            LogUtils.error(getClass() + " getMetadataOperCmd 获取MetadataOperCmd出错 resunt json->" + s);
        }

        resultEntity.setState(false);
        return resultEntity;
    }
}
