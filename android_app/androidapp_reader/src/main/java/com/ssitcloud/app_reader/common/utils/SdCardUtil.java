package com.ssitcloud.app_reader.common.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by LXP on 2017/4/24.
 * sd卡工具类
 */

public class SdCardUtil {
    /**
     * app sd卡存放文件夹
     */
    public static final String appFileName="ssitcloud_app_reader";

    /**
     * 日志文件夹
     */
    public static final String logFileName="log";

    /**
     * 获取内置SD卡路径
     * @return 内置SD卡路径，若不存在返回null
     */
    public static File getInnerSDCard(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir;
    }
}
