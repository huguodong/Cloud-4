package com.ssitcloud.app_reader.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.entity.BibliosPageEntity;
import com.ssitcloud.app_reader.presenter.RenewPresenter;
import com.ssitcloud.app_reader.view.viewInterface.RenewViewI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/3/21.
 * 续借activity
 */

public class RenewActivity extends BaseActivity implements RenewViewI{
    private RenewPresenter renewPresenter;

    private View waitView;//书目列表等待界面
    private View renewWaitView;//续借等待界面
    private View renewView;
    private ViewGroup bookList;

    private TextView futureRenew;//近7天待还数

    private final int futureRenewUnit = 7;//待还天数界限
    //
    private volatile boolean canCloseRenewView = true;

    private View nowRenewBookView;//当前预期弹窗界面

    private List<String> futureRenewBarcode = new ArrayList<>();//用于报错近期待还的图书条码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew);
        renewPresenter = new RenewPresenter(this,this);

        waitView = findViewById(R.id.renewWaitView);
        renewView = findViewById(R.id.renewView);
        renewWaitView = renewView.findViewById(R.id.bookRenewWaitView);

        bookList = (ViewGroup) findViewById(R.id.bookList);
        futureRenew = (TextView) findViewById(R.id.futureRenew);

        //设置续借弹窗关闭
        View renewViewcoverView = renewView.findViewById(R.id.coverView);
        renewViewcoverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideRenewView();
            }
        });

        waitView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });
        renewWaitView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });

        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //load data
        renewPresenter.loadData();
    }

    @Override
    public void setBookView(List<BibliosPageEntity> data) {
        LayoutInflater inflater = LayoutInflater.from(this);
        Resources resources = getResources();
        String unknow = resources.getString(R.string.book_renew_unknow);
        String no = resources.getString(R.string.book_renew_no);
        String unit = resources.getString(R.string.sub_book_can_borrow_unit);
        int weekReturnNum = 0;
        bookList.removeAllViews();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,1);
        try {
            nowDate = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
        }
        final long day = 86400000;//24*60*60*1000
        for (BibliosPageEntity book : data) {
            final View bookView = inflater.inflate(R.layout.sub_book_renew,null);
            TextView title = (TextView) bookView.findViewById(R.id.subRenewTitle);
            title.setText(StringUtils.getStr(book.getTitle(),unknow));

            String temp;

            TextView barcode = (TextView) bookView.findViewById(R.id.barcode);
            String barcodeStr = book.getBook_barcode();
            temp = String.format(resources.getString(R.string.sub_book_barcode),barcodeStr);
            barcode.setText(temp);

            TextView returnDateView = (TextView) bookView.findViewById(R.id.returnDate);
            Date returnBookDate = dateConverter(book.getReturnDate());
            String returnDateStr;
            if(returnBookDate != null){
                returnDateStr = sdf.format(returnBookDate);
            }else{
                returnDateStr = unknow;
            }
            returnDateView.setText(String.format(resources.getString(R.string.sub_book_return_date),returnDateStr));

            TextView canReadTime = (TextView) bookView.findViewById(R.id.canReadTime);
            String canReadTimeStrTemp;
            int r = 0;
            if(returnBookDate != null){
                long s = nowDate.getTime();
                long e = 0;
                try {
                    e = sdf.parse(sdf.format(new Date(returnBookDate.getTime()))).getTime();
                } catch (ParseException e1) {
                }

                if(e >= s){
                    r = (int) ((e-s)/day)+1;
                    if(r <= futureRenewUnit){
                        ++weekReturnNum;
                        futureRenewBarcode.add(barcodeStr);
                    }
                }
                canReadTimeStrTemp = String.format(resources.getString(R.string.sub_book_can_borrow_date)
                        ,"<font color=\'#333333\'>"+r+unit+"</font>");
            }else{
                canReadTimeStrTemp = String.format(resources.getString(R.string.sub_book_can_borrow_date)
                        ,"<font color=\'#333333\'>"+unknow+"</font>");
            }

            canReadTime.setText(Html.fromHtml(canReadTimeStrTemp));
            //准备续借按钮数据
            Map<String,Object> submitData = new HashMap<>();
            submitData.put("title",title.getText());
            submitData.put("canReadTime",canReadTime.getText());
            submitData.put("barcodeStr",barcodeStr);
            //续借按钮
            View submit = bookView.findViewById(R.id.renewButton);
            submit.setTag(submitData);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,Object> data = (Map<String, Object>) v.getTag();
                    showRenewView(data);
                    setNowRenewBookView(bookView);
                }
            });

            bookList.addView(bookView);
        }

        //设置7天待还数
        futureRenew.setText(""+weekReturnNum);
    }

    @Override
    public void setFailView(List<BibliosPageEntity> data,int code) {
        Resources res = getResources();
        bookList.removeAllViews();
        View messageView = LayoutInflater.from(this).inflate(R.layout.sub_message_view,null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if(code == 1){//未绑定卡
            TextView tv = (TextView) messageView.findViewById(R.id.message);
            tv.setText(res.getString(R.string.book_renew_code_1));
//            Toast.makeText(this,res.getString(R.string.book_renew_code_1),Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("使用该功能需要绑定读者证，是否现在绑定？")
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    })
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(RenewActivity.this,BindCardActivity.class);
                            startActivityForResult(intent,1);
                            dialogInterface.dismiss();
                        }
                    });
//            if(bindCardDialog != null){
//                bindCardDialog.dismiss();
//            }
            AlertDialog bindCardDialog = builder.create();
            bindCardDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    finish();
                }
            });
            bindCardDialog.setCanceledOnTouchOutside(false);
            bindCardDialog.show();
            return ;
        }else if(code == -1){//未登陆
            Toast.makeText(this,res.getString(R.string.book_renew_code_0_1),Toast.LENGTH_SHORT).show();
            logout();
        }else if(code == -2){//网络连接失败
            TextView tv = (TextView) messageView.findViewById(R.id.message);
            tv.setText(res.getString(R.string.book_renew_code_0_2));
        }else if(code == -3){//卡失效
            TextView tv = (TextView) messageView.findViewById(R.id.message);
            tv.setText(res.getString(R.string.book_renew_code_0_3));
        }else if(code == -4){
            TextView tv = (TextView) messageView.findViewById(R.id.message);
            tv.setText(res.getString(R.string.lib_not_support));
        }
        bookList.addView(messageView,layoutParams);
    }

    @Override
    public void showWait() {
        waitView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideWait() {
        waitView.setVisibility(View.GONE);
    }

    private Date dateConverter(String date){
        if(date == null || date.isEmpty())
            return null;
        String[] dateFormats = getResources().getStringArray(R.array.date_formats);
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

    /**
     * 显示续借界面
     */
    private void showRenewView(Map<String,Object> data){
        if(data != null){
            TextView title = (TextView) renewView.findViewById(R.id.bookTitle);
            title.setText((CharSequence) data.get("title"));

            TextView canReadTime = (TextView) renewView.findViewById(R.id.canReadTime);
            canReadTime.setText((CharSequence) data.get("canReadTime"));

            //设置续借返回信息面板为空
            TextView renewReturnMessage = (TextView) renewView.findViewById(R.id.renewReturnMessage);
            renewReturnMessage.setVisibility(View.GONE);
            renewReturnMessage.setText("");
            final String booksn = (String) data.get("barcodeStr");
            View submit = renewView.findViewById(R.id.submit);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    renewPresenter.renew(booksn);
                }
            });
            //显示续借按钮
            renewView.findViewById(R.id.submit).setVisibility(View.VISIBLE);
            //显示续借
            renewView.setVisibility(View.VISIBLE);
        }

    }

    private void hideRenewView(){
        renewView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showRenewWait() {
        canCloseRenewView=false;
        renewWaitView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRenewWait() {
        canCloseRenewView=true;
        renewWaitView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setRenewSuccess(String message,String returnDate) {
        Resources res = getResources();
        //设置新的续借日期
        TextView canReadTime = (TextView) renewView.findViewById(R.id.canReadTime);
        Date d = dateConverter(returnDate);
        if(d != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String mainStr = res.getString(R.string.sub_book_return_date);
            canReadTime.setText(String.format(mainStr,sdf.format(d)));
        }else{
            String mainStr = res.getString(R.string.sub_book_return_date);
            canReadTime.setText(String.format(mainStr,res.getString(R.string.book_renew_unknow)));
        }

        TextView mv = (TextView) renewView.findViewById(R.id.renewReturnMessage);
        if(StringUtils.isEmpty(message)){
            mv.setText(res.getString(R.string.book_renew_renew_success));
        }else {
            mv.setText(message);
        }
        mv.setTextColor(ContextCompat.getColor(this,R.color.success_green));
        mv.setVisibility(View.VISIBLE);
        //隐藏续借按钮
        renewView.findViewById(R.id.submit).setVisibility(View.GONE);
        //更新书名列表面板
        updateNowRenewBookView(returnDate);
    }

    @Override
    public void setRenewFail(String message,int code) {
        Resources res = getResources();
        String m = res.getString(R.string.book_renew_renew_fail);
        String unknow = res.getString(R.string.book_renew_unknow);
        TextView mv = (TextView) renewView.findViewById(R.id.renewReturnMessage);
        if(code == 0) {
            mv.setText(String.format(m,StringUtils.getStr(message,unknow)));
        }else if(code == 1 || code == -3){
            mv.setText(String.format(m,res.getString(R.string.book_renew_renew_fail_1)));
        }else if(code == -2){
            mv.setText(String.format(m,res.getString(R.string.book_renew_renew_fail_network_error)));
        }else{
            setFailView(null,code);
            return ;
        }

        mv.setTextColor(ContextCompat.getColor(this, R.color.fail_red));
        mv.setVisibility(View.VISIBLE);
    }

    @Override
    public void setMaxLoan(int sum) {
        TextView countView = (TextView) findViewById(R.id.countBorrow);
        countView.setText(""+sum);
    }

    @Override
    public void setSurplusLoan(int sum) {
        TextView surplusView = (TextView) findViewById(R.id.canBorrow);
        surplusView.setText(""+sum);
    }

    private void setNowRenewBookView(View v){
        nowRenewBookView = v;
    }

    private void updateNowRenewBookView(String returnDate){
        if(nowRenewBookView == null){
            return ;
        }
        View submitButton = nowRenewBookView.findViewById(R.id.renewButton);

        Resources resources = getResources();
        String unit = resources.getString(R.string.sub_book_can_borrow_unit);

        TextView returnDateView = (TextView) nowRenewBookView.findViewById(R.id.returnDate);
        Resources res = getResources();
        Date d = dateConverter(returnDate);
        if(d != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String mainStr = res.getString(R.string.sub_book_return_date);
            returnDateView.setText(String.format(mainStr,sdf.format(d)));
        }else{
            String mainStr = res.getString(R.string.sub_book_return_date);
            returnDateView.setText(String.format(mainStr,res.getString(R.string.book_renew_unknow)));
        }
        //更新剩余借阅天数
        int r = canReadTime(d);
        String canReadTimeStrTemp = String.format(resources.getString(R.string.sub_book_can_borrow_date)
                ,"<font color=\'#333333\'>"+r+unit+"</font>");
        TextView canReaderView = (TextView) nowRenewBookView.findViewById(R.id.canReadTime);
        canReaderView.setText(Html.fromHtml(canReadTimeStrTemp));

        //检查条码是否在近7天待还列表
        String barcode = null;
        Object tag = submitButton.getTag();
        if(tag != null && tag instanceof Map){
            barcode = String.valueOf(((Map) tag).get("barcodeStr"));
        }
        if(futureRenewBarcode.contains(barcode)) {
            if (r > futureRenewUnit) {
                try {
                    String s = futureRenew.getText().toString();
                    Integer i = Integer.valueOf(s);
                    if (i > 0) {
                        futureRenew.setText("" + (i - 1));
                        futureRenewBarcode.remove(barcode);
                    }
                } catch (Exception e) {
                    Log.i("RenewActivity", e.getMessage());
                }
            }
        }
        if(submitButton != null){
            if(tag != null && tag instanceof Map){
                ((Map) tag).put("canReadTime",canReaderView.getText().toString());
            }
        }
    }

    /**
     * 计算剩余可借天数
     * @param date 还书日期
     * @return 剩余可借天数，day为单位
     */
    private int canReadTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final long day = 86400000;//24*60*60*1000
        Date nowDate = null;
        try {
            nowDate = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            return -1;
        }
        int r = 0;
        if(date != null) {
            long s = nowDate.getTime();
            long e = date.getTime();

            if (e > s) {
                r = (int) ((e - s) / day);
            }
        }
        return r;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(canCloseRenewView && renewView.getVisibility() == View.VISIBLE){
                hideRenewView();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == 200){
                renewPresenter.loadData();
            }else{
                finish();
            }
        }
    }
}
