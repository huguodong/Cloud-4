package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.ssitcloud.app_reader.biz.BookBizI;
import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderBizI;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.biz.impl.BookBizImpl;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderCardBizImpl;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.entity.ReservationEntity;
import com.ssitcloud.app_reader.entity.ReservationMessage;
import com.ssitcloud.app_reader.view.viewInterface.BookInfoViewI;

import java.net.SocketException;
import java.util.Map;

import static com.ssitcloud.app_reader.view.viewInterface.BookInfoViewI.BookInfo_State.AUTH_ERROR;
import static com.ssitcloud.app_reader.view.viewInterface.BookInfoViewI.BookInfo_State.NETOWRK_ERROR;

/**
 * Created by LXP on 2017/4/27.
 *
 */

public class BookInfoPresenter {
    private BookInfoViewI view;
    private Context mcontext;
    private BookBizI bookBiz;
    private LoginBizI LoginBiz;
    private ReaderCardBizI readerCardBiz;

    public BookInfoPresenter(BookInfoViewI view, Context context){
        this.view = view;
        mcontext = context;
        bookBiz = new BookBizImpl(context);
        LoginBiz = new LoginBizImpl(context.getResources(),context);
        readerCardBiz = new ReaderCardBizImpl(context.getResources(),context);
    }

    public void reservation(String booksn, final Map<String,Object> idData){
        Integer readerIdx = LoginBiz.isLogin();
        if(readerIdx == null){
            view.setFail(AUTH_ERROR);
            return;
        }

        ReaderCardDbEntity card = readerCardBiz.obtainPreferCard();
        if(card == null){
            ReservationMessage m = new ReservationMessage();
            m.setState(false);
            m.setMessage("请先绑定读者卡");
            view.setReservationResult(m);
            return ;
        }

        final ReservationEntity entity = new ReservationEntity();
        entity.setReader_idx(readerIdx);
        entity.setLib_idx(card.getLib_idx());
        entity.setCard_no(card.getCard_no());
        entity.setSn(booksn);

        AsyncTask<Void,Void,ReservationMessage> task = new AsyncTask<Void, Void, ReservationMessage>() {
            private volatile int state = 0;

            @Override
            protected ReservationMessage doInBackground(Void... params) {
                try {
                    ReservationMessage reservationMessage = bookBiz.reservationBook(entity, idData);
                    state = 1;
                    return reservationMessage;
                } catch (SocketException e) {
                    state = -1;
                } catch (AuthException e) {
                    state = -2;
                }
                return null;
            }

            @Override
            protected void onPostExecute(ReservationMessage reservationMessage) {
                if(state == 1){
                    view.setReservationResult(reservationMessage);
                }else if(state == -1){
                    view.setFail(NETOWRK_ERROR);
                }else if(state == -2){
                    view.setFail(AUTH_ERROR);
                }
            }
        };

        task.execute();
    }

    /**
     * 查询是否可以预借
     * @param bookitem_idx 书在架idx
     */
    public void queryBookState(final Integer bookitem_idx){
        AsyncTask<Void,Void,Boolean> task = new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    String s = bookBiz.queryReservationAble(bookitem_idx);
                    return "AVALIABLE".equals(s);
                } catch (SocketException e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if(Boolean.valueOf(false).equals(aBoolean)){
                    view.setBookState(0);
                }
            }
        };

        task.execute();
    }
}
