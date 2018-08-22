package com.ssitcloud.app_operator.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.FeedBackBizI;
import com.ssitcloud.app_operator.biz.impl.FeedBackBizImpl;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.view.viewInterface.FeedBackViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;
import java.util.Map;

/**
 * 创建日期：2017/3/31 10:05
 * @author shuangjunjie
 */

public class FeedBackPresenter {

    private final String TAG = "FeedBackPresenter";
    private SoftReference<FeedBackViewI> feedBackView;
    private Context context;
    private Resources resources;
    private FeedBackBizI feedBackBizI;

    public FeedBackPresenter(FeedBackViewI feedBackViewI, Context context) {
        this.feedBackView = new SoftReference<FeedBackViewI>(feedBackViewI);
        this.context = context.getApplicationContext();
        this.feedBackBizI = new FeedBackBizImpl(this.context,context.getResources());
    }

    public void sendFeedBack(Map<String,Object> map){
        final FeedBackViewI feedBackV = feedBackView.get();
        if (null == feedBackV){
            return ;
        }

        resources = context.getResources();
        AsyncTask<Map<String,Object>,Void,ResultEntity> task = new AsyncTask<Map<String,Object>, Void, ResultEntity>() {
            @Override
            protected ResultEntity doInBackground(Map<String,Object>... params) {
                try {
                    return feedBackBizI.sendFeedBack(params[0]);
                }catch (SocketException e){
                    return null;
                }

            }

            @Override
            protected void onPostExecute(ResultEntity data){
//                forgetPwdV.hideWait();
                if (null == data){
                    feedBackV.sendFeedBackFail(resources.getString(R.string.connect_network_fail));
                }else if (data.getState()){
                    feedBackV.sendFeedBackSuccess("发送成功");
                }else {
                    feedBackV.sendFeedBackFail("发送失败");
                }
            }
        };
//        forgetPwdV.showWait();

        task.execute(map);

    }

}
