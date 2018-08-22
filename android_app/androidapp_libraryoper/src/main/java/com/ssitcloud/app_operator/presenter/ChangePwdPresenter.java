package com.ssitcloud.app_operator.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.ChangePwdBizI;
import com.ssitcloud.app_operator.biz.impl.ChangePwdBizImpl;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.StringUtils;
import com.ssitcloud.app_operator.view.viewInterface.ChangePwdViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;
import java.util.Map;

/**
 * 创建日期：2017/3/22 18:54
 * @author shuangjunjie
 */

public class ChangePwdPresenter {

    private final String TAG = "ChangePwdPresenter";
    private final String charset = "utf-8";

    private Context context;
    private Resources resources;
    private SoftReference<ChangePwdViewI> changePwdView;
    private ChangePwdBizI changePwdBiz;

    public ChangePwdPresenter(ChangePwdViewI changePwdViewI, Context context) {
        this.changePwdView = new SoftReference<ChangePwdViewI>(changePwdViewI);
        this.context = context.getApplicationContext();
        this.changePwdBiz = new ChangePwdBizImpl(context.getResources(),this.context);
    }

    public void changePwd(final Map<String,Object> map){
        final ChangePwdViewI changePwdV = changePwdView.get();
        if (null == changePwdV){
            return ;
        }
        resources = context.getResources();
        AsyncTask<Map<String,Object>,Void,ResultEntity> task = new AsyncTask<Map<String,Object>, Void, ResultEntity>() {
            @Override
            protected ResultEntity doInBackground(Map<String,Object>... params) {
                try {
                    return changePwdBiz.changePwd(params[0]);
                }catch (SocketException e){
                    return null;
                }

            }

            @Override
            protected void onPostExecute(ResultEntity data){
//                changePwdV.hideWait();
                if (null == data){
                    changePwdV.changePwdFail(resources.getString(R.string.connect_network_fail));
                }else if (data.getState()){
                    changePwdV.changePwdSuccess(data.getResult().toString());
                }else if (!StringUtils.isEmpty(data.getRetMessage())){
                    String retM = data.getRetMessage();
                    String message = data.getMessage();
                    if ("length".equals(retM)){
                        //长度不符合
                        changePwdV.changePwdFail(message);
                    }else if ("charset".equals(retM)){
                        //不符合规范
                        char str = message.charAt(message.length()-1);
                        switch (str){
                            case '1':
                                changePwdV.changePwdFail("密码应该全为大写");
                                break;
                            case '2':
                                changePwdV.changePwdFail("密码应该全为小写");
                                break;
                            case '3':
                                changePwdV.changePwdFail("密码应该全为数字");
                                break;
                            case '4':
                                changePwdV.changePwdFail("不能包含特殊字符^%&',;=?$/*/");
                                break;
                        }
                    }
                }else if (!StringUtils.isEmpty(data.getMessage())){
                    changePwdV.changePwdFail(data.getMessage());
                }
            }
        };
//        changePwdV.showWait();

        task.execute(map);



    }




}
