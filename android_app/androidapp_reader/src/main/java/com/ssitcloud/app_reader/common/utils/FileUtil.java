package com.ssitcloud.app_reader.common.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by LXP on 2017/4/11.
 * 文件工具
 */

public class FileUtil {
    private final static String mainPathName="ssitcloud_reader";
    private final static String imgPathName="image";

    /**
     * 是否存在sd卡
     * @return
     */
    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取主目录，若存在返回路径
     * @return
     */
    public static File getMainPath(){
        boolean isExsits = isSdCardExist();
        if(isExsits){
            File path = Environment.getExternalStorageDirectory();
            File main = new File(path,mainPathName);
            if(!main.exists()){
                main.mkdirs();
            }
            Log.d("FileUtil",""+main.getAbsolutePath());
            return main;
        }else{
            return null;
        }
    }
}
