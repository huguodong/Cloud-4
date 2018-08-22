package com.ssitcloud.app_operator.view.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.ssitcloud.app_operator.R;

/**
 * 创建日期：2017/4/14 22:37
 * 设置弹窗 相关属性
 * @author shuangjunjie
 */

public class MyAlertDialog{

    /**
     * 设置弹窗 宽度和高度
     * @param alertDialog
     * @param width
     * @param activity
     */
    public static void setAlertDialogParam(Dialog alertDialog, double width, Activity activity){
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) (d.getWidth() * width);    //宽度设置为屏幕的0.5
        alertDialog.getWindow().setAttributes(p);     //设置生效
//        return alertDialog;
    }

    /**
     * 设置window
     * @param dialog
     * @param activity
     * @return
     */
    public static Window setDialog(Dialog dialog,Activity activity){
        Window window = dialog.getWindow();
        dialog.show();
        setAlertDialogParam(dialog,0.75,activity);
        window.setContentView(R.layout.dialog);
        return window;
    }
}
