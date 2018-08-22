package com.ssitcloud.app_reader.common.utils;

import android.content.res.Resources;

import com.ssitcloud.app_reader.R;

/**
 * Created by LXP on 2017/3/6.
 */

public class RequestUrlUtil {
    /**
     * 获取完整url地址，需要在request_url中配置，然后传入资源id即可
     * @param resources
     * @param resourceId 资源id
     * @return
     */
    public static String getUrl(Resources resources,int resourceId){
        String baseUrl = resources.getString(R.string.baseUrl);
        return baseUrl+resources.getString(resourceId);
    }
}
