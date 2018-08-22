package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.ssitcloud.app_reader.biz.BookBizI;
import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.biz.impl.BookBizImpl;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderCardBizImpl;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.entity.InReservationEntity;
import com.ssitcloud.app_reader.entity.ReservationBookEntity;
import com.ssitcloud.app_reader.entity.ReservationEntity;
import com.ssitcloud.app_reader.entity.ReservationMessage;
import com.ssitcloud.app_reader.view.viewInterface.ReservationViewI;
import com.ssitcloud.app_reader.view.viewInterface.StandardViewI;

import java.net.SocketException;
import java.util.List;

public class ReservationPresenter {
    private Context mcontext;
    private ReservationViewI view;
    private LoginBizI loginBiz;
    private ReaderCardBizI readerCardBiz;
    private BookBizI bookBiz;

    public ReservationPresenter(Context context, ReservationViewI view){
        mcontext = context.getApplicationContext();
        this.view = view;
        loginBiz = new LoginBizImpl(mcontext.getResources(),mcontext);
        readerCardBiz = new ReaderCardBizImpl(mcontext.getResources(),mcontext);
        bookBiz = new BookBizImpl(mcontext);
    }

    public void loadData(){
        Integer readerIdx = loginBiz.isLogin();
        if(readerIdx == null){
            view.setState(StandardViewI.Standard_State.AUTH_ERROR);
            return ;
        }
        ReaderCardDbEntity card = readerCardBiz.obtainPreferCard();
        if(card == null){
            view.setState(StandardViewI.Standard_State.UNBIND_CARD);
            return ;
        }
        ReservationEntity param = new ReservationEntity();
        param.setReader_idx(readerIdx);
        param.setCard_no(card.getCard_no());
        param.setLib_idx(card.getLib_idx());

        AsyncTask<ReservationEntity,Void,List<ReservationBookEntity>> task = new AsyncTask<ReservationEntity, Void, List<ReservationBookEntity>>() {
            private volatile int state = 0;

            @Override
            protected List<ReservationBookEntity> doInBackground(ReservationEntity... params) {
                try {
                    List<ReservationBookEntity> books = bookBiz.reservationBookList(params[0]);
                    state = 1;
                    return books;
                } catch (SocketException e) {
                    if("lib_not_support".equals(e.getMessage())){
                        state = -3;
                    }else {
                        state = -1;
                    }
                } catch (AuthException e) {
                    state = -2;
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<ReservationBookEntity> books) {
                if(view == null){
                    return ;
                }
                if(state == 1){
                    view.setBookList(books);
                }else if(state == -1){
                    view.setState(StandardViewI.Standard_State.NETOWRK_ERROR);
                }else if(state == -2){
                    view.setState(StandardViewI.Standard_State.AUTH_ERROR);
                }else if(state == -3){
                    view.setState(StandardViewI.Standard_State.LIB_NOT_SUPPORT);
                }
            }
        };

        task.execute(param);
    }

    /**
     * 取消预借
     */
    public void inReservation(InReservationEntity entity){
        Integer readerIdx = loginBiz.isLogin();
        if(readerIdx == null){
            view.setState(StandardViewI.Standard_State.AUTH_ERROR);
            return ;
        }
        ReaderCardDbEntity card = readerCardBiz.obtainPreferCard();
        if(card == null){
            view.setState(StandardViewI.Standard_State.UNBIND_CARD);
            return ;
        }
        entity.setReader_idx(readerIdx);
        entity.setCard_no(card.getCard_no());
        entity.setLib_idx(card.getLib_idx());

        AsyncTask<InReservationEntity,Void,ReservationMessage> task = new AsyncTask<InReservationEntity, Void,ReservationMessage>() {
            private volatile int state = 0;

            @Override
            protected ReservationMessage doInBackground(InReservationEntity... params) {
                try {
                    ReservationMessage message = bookBiz.inReservationBook(params[0]);
                    state = 1;
                    return message;
                } catch (SocketException e) {
                    state = -1;
                } catch (AuthException e) {
                    state = -2;
                }
                return null;
            }

            @Override
            protected void onPostExecute(ReservationMessage message) {
                if(view == null){
                    return ;
                }
                if(state == 1){
                    view.setInreservationState(StandardViewI.Standard_State.SUCCESS,message);
                }else if(state == -1){
                    view.setInreservationState(StandardViewI.Standard_State.NETOWRK_ERROR,null);
                }else if(state == -2){
                    view.setInreservationState(StandardViewI.Standard_State.AUTH_ERROR,null);
                }
            }
        };

        task.execute(entity);
    }
}
