package com.ssitcloud.app_reader.common.utils;

import android.app.Activity;

import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by LXP on 2017/3/27.
 * activity管理器
 */

public class ActivityManager {
    private static ActivityManager am = new ActivityManager();
    private Map<Integer,SoftReference<Activity>> activityStackMap = new LinkedHashMap<>();

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        return am;
    }

    /**
     * 将activity从管理器中移除
     */
    public void pop(Activity activity){
        int hashCode = activity.hashCode();
        activityStackMap.remove(hashCode);
    }

    /**
     * 将activity添加到管理器中
     * @param activity activity
     */
    public void push(Activity activity){
        int hashCode = activity.hashCode();
        SoftReference<Activity> activityRef = new SoftReference<>(activity);
        activityStackMap.put(hashCode,activityRef);
    }

    /**
     * 弹出并结束所有的activity
     */
    public void popFinishAll(){
        Collection<SoftReference<Activity>> values = activityStackMap.values();
        for (SoftReference<Activity> activityRef : values) {
            if(activityRef.get() != null){
                activityRef.get().finish();
            }
        }
        activityStackMap.clear();
    }
}
