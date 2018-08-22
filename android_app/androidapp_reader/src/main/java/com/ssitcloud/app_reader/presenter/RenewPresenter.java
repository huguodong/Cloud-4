package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.biz.BookBizI;
import com.ssitcloud.app_reader.biz.impl.BookBizImpl;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderCardBizImpl;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.common.exception.ReaderCardInvalidException;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.entity.BibliosPageEntity;
import com.ssitcloud.app_reader.entity.RenewEntity;
import com.ssitcloud.app_reader.view.viewInterface.RenewViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by LXP on 2017/3/21.
 * 续借presenter
 */

public class RenewPresenter {
    private SoftReference<RenewViewI> renewViewIReference;
    private Context mcontext;

    private BookBizI renewBiz;
    private LoginBizI loginBiz;
    private ReaderCardBizI readerCardBiz;
    private BookBizI bookBiz;
    private Handler handler = new Handler();

    public RenewPresenter(RenewViewI renewViewI, Context context) {
        mcontext = context.getApplicationContext();
        renewViewIReference = new SoftReference<>(renewViewI);
        renewBiz = new BookBizImpl(mcontext);
        loginBiz = new LoginBizImpl(mcontext.getResources(), mcontext);
        readerCardBiz = new ReaderCardBizImpl(mcontext.getResources(), mcontext);
        bookBiz = new BookBizImpl(mcontext);
    }

    public void loadData() {
        final RenewViewI renewView = renewViewIReference.get();
        if (renewView == null) {
            return;
        }

        Integer reader_idx = loginBiz.isLogin();
        if (reader_idx == null) {
            renewView.setFailView(null, -1);
            return;
        }
        ReaderCardDbEntity card = readerCardBiz.obtainPreferCard();
        if (card == null) {
            renewView.setFailView(null, 1);
            return;
        }

        AsyncTask<Map<String, Object>, Void, List<BibliosPageEntity>> task = new AsyncTask<Map<String, Object>, Void, List<BibliosPageEntity>>() {
            @Override
            protected List<BibliosPageEntity> doInBackground(Map<String, Object>... params) {
                Integer reader_idx = (Integer) params[0].get("reader_idx");
                Integer lib_idx = (Integer) params[0].get("lib_idx");
                String card_no = (String) params[0].get("card_no");
                try {
                    List<BibliosPageEntity> data = renewBiz.obtainRenewBook(reader_idx, lib_idx, card_no);
                    return data;
                } catch (SocketException e) {
                    if ("lib_not_support".equals(e.getMessage())) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                renewView.setFailView(null, -4);
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                renewView.setFailView(null, -2);
                            }
                        });
                    }
                } catch (ReaderCardInvalidException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            renewView.setFailView(null, -3);
                        }
                    });
                } catch (Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            renewView.setFailView(null, -1);
                        }
                    });
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<BibliosPageEntity> bibliosPageEntities) {
                if (bibliosPageEntities != null) {
                    renewView.setBookView(bibliosPageEntities);
                }
                renewView.hideWait();
            }
        };

        Map<String, Object> map = new HashMap<>(8);
        map.put("reader_idx", reader_idx);
        map.put("lib_idx", card.getLib_idx());
        map.put("card_no", card.getCard_no());

        renewView.showWait();
        task.execute(map);

        //加载统计面板
        Observable<ReaderCardDbEntity> observable = readerCardBiz.obtainReaderCardByService(reader_idx, card.getLib_idx(), card.getCard_no());
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ReaderCardDbEntity>() {
                    @Override
                    public void accept(@NonNull ReaderCardDbEntity readerCardDbEntity) throws Exception {
                        if(readerCardDbEntity != null){
                            if (readerCardDbEntity.getAllown_loancount() != null) {
                                renewView.setMaxLoan(readerCardDbEntity.getAllown_loancount());
                            }
                            if (readerCardDbEntity.getSurplus_count() != null) {
                                renewView.setSurplusLoan(readerCardDbEntity.getSurplus_count());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if(throwable instanceof AuthException){
                            renewView.setFailView(null,-1);
                        }
                    }
                });
    }

    /**
     * 使用默认卡进行续借
     *
     * @param booksn
     */
    public void renew(String booksn) {
        final RenewViewI renewView = renewViewIReference.get();
        if (renewView == null) {
            return;
        }

        Integer reader_idx = loginBiz.isLogin();
        if (reader_idx == null) {
            renewView.setRenewFail(null, -1);
            return;
        }
        ReaderCardDbEntity card = readerCardBiz.obtainPreferCard();
        if (card == null) {
            renewView.setRenewFail(null, 1);
            return;
        }

        AsyncTask<Map<String, Object>, Void, RenewEntity> task = new AsyncTask<Map<String, Object>, Void, RenewEntity>() {
            @Override
            protected RenewEntity doInBackground(Map<String, Object>... params) {
                Integer reader_idx = (Integer) params[0].get("reader_idx");
                Integer lib_idx = (Integer) params[0].get("lib_idx");
                String card_no = (String) params[0].get("card_no");
                String sn = (String) params[0].get("booksn");
                try {
                    RenewEntity renew = bookBiz.renew(reader_idx, lib_idx, card_no, sn);
                    return renew;
                } catch (SocketException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            renewView.setRenewFail(null, -2);
                        }
                    });
                } catch (ReaderCardInvalidException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            renewView.setRenewFail(null, -3);
                        }
                    });
                } catch (Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            renewView.setRenewFail(null, -1);
                        }
                    });
                }
                return null;
            }

            @Override
            protected void onPostExecute(RenewEntity renewEntity) {
                if (renewEntity != null && renewEntity.isState()) {
                    renewView.setRenewSuccess(renewEntity.getMessage(), renewEntity.getReturnDate());
                } else if (renewEntity != null) {
                    renewView.setRenewFail(renewEntity.getMessage(), 0);
                }
                renewView.hideRenewWait();
            }
        };

        Map<String, Object> map = new HashMap<>(8);
        map.put("reader_idx", reader_idx);
        map.put("lib_idx", card.getLib_idx());
        map.put("card_no", card.getCard_no());
        map.put("booksn", booksn);

        renewView.showRenewWait();
        task.execute(map);
    }

}
