package com.ssitcloud.app_operator.biz.impl;

import android.content.Context;
import android.content.res.Resources;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.IndexBizI;
import com.ssitcloud.app_operator.common.entity.HttpResponce;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.HttpClientUtil;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.RequestUrlUtil;
import com.ssitcloud.app_operator.db.DbHelper;
import com.ssitcloud.app_operator.db.dao.MessageRemindDao;
import com.ssitcloud.app_operator.db.entity.MessageRemindDbEntity;

import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建日期：2017/4/6 15:12
 * @author shuangjunjie
 */

public class IndexBizImpl implements IndexBizI{
    private final String TAG ="IndexBizImpl";
    private final String charset = "utf-8";
    private Resources resources;
    private Context context;

    public IndexBizImpl(Resources resources, Context context) {
        this.resources = resources;
        this.context = context.getApplicationContext();
    }

    @Override
    public ResultEntity querySlaveLibraryByMasterIdx(Map<String,Object> reqMap) throws SocketException{
        String url = RequestUrlUtil.getUrl(resources, R.string.get_slave_master_url);
        Map<String, String> map = new HashMap<>();
        map.put("json", JsonUtils.toJson(reqMap));
        HttpResponce hr = HttpClientUtil.dopost(url,map,charset);
        if (hr.getState() == 200){
            try {
                ResultEntity result = JsonUtils.fromJson(hr.getBody(), ResultEntity.class);
                return result;
            }catch (Exception e){
                Log.e(TAG,"to json exception",e);
                return null;
            }
        }else {
            throw new SocketException("网络异常");
        }
    }

}
