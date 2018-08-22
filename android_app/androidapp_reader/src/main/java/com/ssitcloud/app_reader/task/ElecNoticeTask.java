package com.ssitcloud.app_reader.task;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.ConfigBizI;
import com.ssitcloud.app_reader.biz.ElecBizI;
import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.biz.impl.ConfigBizImpl;
import com.ssitcloud.app_reader.biz.impl.ElecBizImpl;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderCardBizImpl;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.entity.AppElectronicEntity;
import com.ssitcloud.app_reader.service.NoticeComponentI;
import com.ssitcloud.app_reader.view.ElectronicCertificateActivity;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/4/17.
 * 电子凭证通知
 */

public class ElecNoticeTask extends AbstractSafeTask {
    public static final int elecNotifyId = 123456798;

    private ElecBizI elecBiz;
    private ReaderCardBizI readerCardBiz;
    private LoginBizI loginBiz;
    private ConfigBizI configBiz;
    private NoticeComponentI noticeService;
    private Context mcontext;


    public ElecNoticeTask(NoticeComponentI noticeService, Context context){
        this.noticeService = noticeService;
        elecBiz = new ElecBizImpl(context);
        readerCardBiz = new ReaderCardBizImpl(context.getResources(),context);
        loginBiz = new LoginBizImpl(context.getResources(),context);
        configBiz = new ConfigBizImpl(context);
        mcontext = context;
    }

    @Override
    public void task() {
        Integer idx = loginBiz.isLogin();
        LoginInfoDbEntity entity = loginBiz.getLoginReturnData();
        if(idx == null){
            cancel();
            return ;
        }
        try {
            //电子凭证
            List<AppElectronicEntity> appElectronicEntities = elecBiz.obtainUnReaderElecByService(idx, null,null);
            if(!appElectronicEntities.isEmpty()){
                int unReader = configBiz.getElecNoticeCount()+appElectronicEntities.size();

                //设置通知已读
                try{
                    List<Integer> ids = new ArrayList<>(appElectronicEntities.size());
                    for (AppElectronicEntity appElectronicEntity : appElectronicEntities) {
                        ids.add(appElectronicEntity.getElectronic_idx());
                    }
                    elecBiz.setReadElec(idx,ids);
                }catch (Exception e){

                }
                boolean messagePushState = configBiz.getMessagePushState();
                if(messagePushState) {
                    configBiz.setElecNoticeCount(unReader);

                    Map<String, Object> map = new HashMap<>(3, 1.0f);
                    map.put("data", appElectronicEntities);
                    map.put("unReadCount", unReader);
                    sendNotice(map);
                }
            }
        } catch (SocketException|AuthException e) {
        }

        //图书推荐
        try {
           //查找读者卡号
            List<ReaderCardDbEntity> cardList = readerCardBiz.obtainReaderCardByService(idx);
            ReaderCardDbEntity cardDbEntity = null;
            if(cardList!=null&&cardList.size()>0){
                cardDbEntity = cardList.get(0);
                if(cardDbEntity == null){
                    return;
                }
                //根据读者卡号和图书馆id查询推荐图书列表
                List<String> bookList = elecBiz.getRecommendList(cardDbEntity.getLib_idx(),cardDbEntity.getCard_no());
                String content="热门图书推荐：";
                for(String book:bookList){
                    content += book + ",";
                }

                AppElectronicEntity electronicEntity = new AppElectronicEntity();
                electronicEntity.setConetent(content);
                electronicEntity.setState(0);
                electronicEntity.setLibraryName(cardDbEntity.getLib_name());
                electronicEntity.setTitle("热门图书推荐");

                Map<String, Object> map = new HashMap<>(3, 1.0f);
                map.put("data", electronicEntity);
                map.put("unReadCount", 1);
                sendNotice(map);
            }
        }catch (SocketException|AuthException e) {

        }

    }


    private void sendNotice(Map<String,Object> param) {
        int unReadCount = (Integer) param.get("unReadCount");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mcontext);
        builder.setContentTitle("最新消息");//电子凭证消息
        builder.setContentText("您有"+unReadCount+"条新的消息");//电子凭证
        builder.setPriority(Notification.PRIORITY_LOW);
        builder.setLargeIcon(BitmapFactory.decodeResource(mcontext.getResources(), R.drawable.message))
                .setSmallIcon(R.drawable.message);
        Intent i = new Intent(mcontext, ElectronicCertificateActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mcontext,elecNotifyId,i,PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        noticeService.sendNotice(elecNotifyId,notification);
    }
}
