package com.ssitcloud.app_reader.db;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by LXP on 2017/3/7.
 */

public class DbUtil {
    private final static String TAG="DbUtil";

    /**
     * 获取model对应的insert方法
     * @param model
     * @param tableName
     * @return
     */
    public static String insert(Object model,String tableName){
        Class modelClass = model.getClass();
        Method[] methods = modelClass.getMethods();
        StringBuilder sbbase = new StringBuilder("insert into "+tableName);
        StringBuilder sbinset = new StringBuilder(128);
        StringBuilder sbvalues = new StringBuilder(128);
        for(int i = 0,z=methods.length;i<z;++i){
            String methodName = methods[i].getName();
            if(methods[i].getParameterTypes().length == 0 && !methodName.equals("getClass")
                    && methodName.startsWith("get") && methodName.length()>3){//检查是否为pojo的get方法
                String fildName = methodName.substring(3).toLowerCase();
                try {
                    Object value = methods[i].invoke(model);
                    sbinset.append(fildName).append(',');
                    if(value == null){
                        sbvalues.append("null").append(',');
                    }else if(value.getClass() == String.class){
                        sbvalues.append("'").append(value).append("'").append(',');
                    }else{
                        sbvalues.append(value).append(',');
                    }
                } catch (IllegalAccessException e) {
                    Log.d(TAG,"不可访问的方法");
                } catch (InvocationTargetException e) {
                    Log.d(TAG,"执行get方法抛出异常",e);
                }
            }
        }
        sbinset.deleteCharAt(sbinset.length()-1);
        sbinset.insert(0,'(');
        sbinset.append(')');
        sbvalues.deleteCharAt(sbvalues.length()-1);
        sbvalues.insert(0,'(');
        sbvalues.append(')');
        sbbase.append(sbinset).append("VALUES ").append(sbvalues);

        return sbbase.toString();
    }


}
