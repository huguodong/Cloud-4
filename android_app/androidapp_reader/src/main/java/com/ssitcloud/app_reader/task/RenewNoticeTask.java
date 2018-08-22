package com.ssitcloud.app_reader.task;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.BookBizI;
import com.ssitcloud.app_reader.biz.ConfigBizI;
import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.biz.impl.BookBizImpl;
import com.ssitcloud.app_reader.biz.impl.ConfigBizImpl;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderCardBizImpl;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.common.exception.ReaderCardInvalidException;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.entity.BibliosPageEntity;
import com.ssitcloud.app_reader.service.NoticeComponentI;
import com.ssitcloud.app_reader.view.ReaderCardListActivity;
import com.ssitcloud.app_reader.view.RenewActivity;

import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ssitcloud.app_reader.task.RenewNoticeTask.RenewNotoceParam.STATE.CARD_INVALIOD;
import static com.ssitcloud.app_reader.task.RenewNoticeTask.RenewNotoceParam.STATE.HAVE_RENEW_BOOK;

/**
 * Created by LXP on 2017/4/18.
 * 续借通知任务
 */

public class RenewNoticeTask extends AbstractSafeTask {
    private BookBizI bookBiz;
    private LoginBizI loginBiz;
    private ReaderCardBizI readerCardBiz;
    private ConfigBizI configBiz;
    private Context mcontext;
    private NoticeComponentI noticeService;

    public static final int renewNoticeId = 16546461;

    public RenewNoticeTask(NoticeComponentI noticeService, Context context){
        this.noticeService = noticeService;
        bookBiz = new BookBizImpl(context);
        readerCardBiz = new ReaderCardBizImpl(context.getResources(),context);
        loginBiz = new LoginBizImpl(context.getResources(),context);
        configBiz = new ConfigBizImpl(context);
        mcontext = context;
    }

    private void sendNotice(RenewNotoceParam param) {
        if(param.state == CARD_INVALIOD){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(mcontext);
            builder.setPriority(Notification.PRIORITY_DEFAULT);
            builder.setLargeIcon(BitmapFactory.decodeResource(mcontext.getResources(), R.drawable.renew))
                    .setSmallIcon(R.drawable.renew);
            builder.setContentTitle("卡失效");
            builder.setContentText(param.card.getCard_no()+
                    "("+param.card.getLib_name()+")失效");
            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
            bigTextStyle.bigText("您的常用卡"+param.card.getCard_no()+
                    "("+param.card.getLib_name()+")可能失效了,请重新绑定");
            builder.setStyle(bigTextStyle);

            Intent i = new Intent(mcontext, ReaderCardListActivity.class);
            PendingIntent pi = PendingIntent.getActivity(mcontext,renewNoticeId,i,PendingIntent.FLAG_CANCEL_CURRENT);
            builder.setContentIntent(pi);

            Notification notification = builder.build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            noticeService.sendNotice(renewNoticeId,notification);
        }else if(param.state == HAVE_RENEW_BOOK){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(mcontext);
            builder.setPriority(Notification.PRIORITY_DEFAULT);
            builder.setLargeIcon(BitmapFactory.decodeResource(mcontext.getResources(), R.drawable.renew))
                    .setSmallIcon(R.drawable.renew);
            builder.setContentText("待续借图书");
            builder.setContentText("您有"+param.books.size()+"本待续借图书");

            NotificationCompat.InboxStyle inboxStyle = new android.support.v7.app.NotificationCompat.InboxStyle();
            inboxStyle.setBigContentTitle("您有"+param.books.size()+"本待续借图书");
            for (BibliosPageEntity book : param.books) {
                inboxStyle.addLine(book.getTitle());
            }
            builder.setStyle(inboxStyle);

            Intent i = new Intent(mcontext, RenewActivity.class);
            PendingIntent pi = PendingIntent.getActivity(mcontext,renewNoticeId,i,PendingIntent.FLAG_CANCEL_CURRENT);
            builder.setContentIntent(pi);

            Notification notification = builder.build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            noticeService.sendNotice(renewNoticeId,notification);
        }
    }

    @Override
    public void task() {
        Integer readerIdx = loginBiz.isLogin();
        ReaderCardDbEntity card = readerCardBiz.obtainPreferCard();
        if(readerIdx == null || card == null){
            return ;
        }

        int remindTime = configBiz.getRemindTime();
        if(remindTime == -1){
            return ;
        }

        Set<String> remindBarcode = configBiz.getRemindBookBarcode();

        RenewNotoceParam renewNotoceParam = new RenewNotoceParam();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date nowDate = sdf.parse(sdf.format(new Date()));
            List<BibliosPageEntity> books = bookBiz.obtainRenewBook(readerIdx, card.getLib_idx(), card.getCard_no());
            List<BibliosPageEntity> needRenewBooks = new ArrayList<>(books.size());
            Set<String> newRemindBarcodes = new HashSet<>(books.size());
            long s,z,r;
            for (BibliosPageEntity book : books) {
                Date bookDate = dateConverter(book.getReturnDate());
                if(bookDate != null) {
                    s = nowDate.getTime();
                    z = sdf.parse(sdf.format(new Date(bookDate.getTime()))).getTime();
                    if(z >= s) {
                        r = (z - s) / 86400000;//1 day 24*60*60*1000
                        if (r <= remindTime) {
                            if (!remindBarcode.contains(book.getBook_barcode())) {
                                needRenewBooks.add(book);
                            }
                            newRemindBarcodes.add(book.getBook_barcode());
                        }
                    }
                }
            }
            configBiz.setRemindBookBarcode(newRemindBarcodes);
            if(!needRenewBooks.isEmpty()){
                renewNotoceParam.state = HAVE_RENEW_BOOK;
                renewNotoceParam.books = needRenewBooks;
                sendNotice(renewNotoceParam);
            }
        } catch (ParseException|SocketException|AuthException e) {
            e.printStackTrace();
        }catch(ReaderCardInvalidException e){
            Log.i("renew task","ReaderCardInvalidException");
            renewNotoceParam.state = CARD_INVALIOD;
            renewNotoceParam.card = card;
            sendNotice(renewNotoceParam);
        }

    }

    static class RenewNotoceParam{
        enum STATE{CARD_INVALIOD,HAVE_RENEW_BOOK}
        STATE state;
        ReaderCardDbEntity card;
        List<BibliosPageEntity> books;
    }

    private Date dateConverter(String date){
        if(date == null || date.isEmpty())
            return null;
        String[] dateFormats = mcontext.getResources().getStringArray(R.array.date_formats);
        SimpleDateFormat sdf;
        for (String dateFormat : dateFormats) {
            sdf = new SimpleDateFormat(dateFormat);
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
            }
        }

        return null;
    }
}
