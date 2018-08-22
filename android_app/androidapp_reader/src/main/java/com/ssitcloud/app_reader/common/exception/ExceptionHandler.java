package com.ssitcloud.app_reader.common.exception;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.ssitcloud.app_reader.common.utils.ActivityManager;
import com.ssitcloud.app_reader.common.utils.SdCardUtil;
import com.ssitcloud.app_reader.view.WelComeActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LXP on 2017/4/24.
 * 未检查异常处理器
 */

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Context mcontext;

    public ExceptionHandler(Context context){
        mcontext = context;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            if(ContextCompat.checkSelfPermission(mcontext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){//检查是否有写卡权限
                File sdPath = SdCardUtil.getInnerSDCard();
                if(sdPath != null){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    File appFile = new File(sdPath,SdCardUtil.appFileName+'/'+SdCardUtil.logFileName);
                    if(!appFile.exists()){
                        boolean b = appFile.mkdirs();
                        if(!b){
                            throw new RuntimeException("创建文件夹失败");
                        }
                    }
                    File logFile = new File(appFile,sdf.format(new Date())+".txt");
                    sdf.applyPattern("yyyy-MM-dd HH:mm");
                    if(!logFile.exists()){
                        boolean b = logFile.createNewFile();
                        if(!b){
                            throw new RuntimeException("创建文件夹失败");
                        }
                    }
                    FileWriter fw = null;
                    try {
                        final int MAX_LOG_SIZE = 5 * 1024 * 1024;
                        if (logFile.length() > MAX_LOG_SIZE) {
                            fw = new FileWriter(logFile, false);
                        } else {
                            fw = new FileWriter(logFile, true);
                        }
                        fw.write("time:"+sdf.format(new Date())+" thread:"+t.getId()+"_"+t.getName());
                        fw.write("  Exception:"+e.getMessage()+"\n");
                        e.printStackTrace(new PrintWriter(fw));
                        fw.write('\n');
                        fw.flush();
                    }finally {
                        if(fw != null){
                            fw.close();
                        }
                    }
                }
            }
        }catch (Exception e1){
            Log.e("ExceptionHandler","记录异常时发生了异常"+e1.getMessage());
        }

        //重启程序
        ActivityManager.getInstance().popFinishAll();
        Intent resetIntent = new Intent(mcontext, WelComeActivity.class);
        resetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        resetIntent.putExtra("crash", true);
        mcontext.startActivity(resetIntent);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(-1);
    }
}
