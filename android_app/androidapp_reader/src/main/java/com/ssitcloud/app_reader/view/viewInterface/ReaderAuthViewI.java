package com.ssitcloud.app_reader.view.viewInterface;

import android.graphics.Bitmap;

import java.util.Map;

/**
 * Created by LXP on 2017/3/24.
 * 读者认证视图接口
 */

public interface ReaderAuthViewI {

    /**
     * 设置其他数据
     * @param data
     */
    void setOtherData(Map<String,Object> data);

    /**
     * 设置二维码图
     * @param bm
     */
    void setBarcode(Bitmap bm);

    /**
     * 设置失败视图
     * @param code -1未登陆，-2网络连接失败，-3未绑定卡,-4 没有更新密钥,-5不支持的图书馆（查询不到lib_id）
     */
    void setFailView(int code);
}
