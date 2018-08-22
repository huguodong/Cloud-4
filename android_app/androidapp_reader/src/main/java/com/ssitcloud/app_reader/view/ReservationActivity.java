package com.ssitcloud.app_reader.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.entity.InReservationEntity;
import com.ssitcloud.app_reader.entity.ReservationBookEntity;
import com.ssitcloud.app_reader.entity.ReservationEntity;
import com.ssitcloud.app_reader.entity.ReservationMessage;
import com.ssitcloud.app_reader.presenter.ReservationPresenter;
import com.ssitcloud.app_reader.view.viewInterface.ReservationViewI;
import com.ssitcloud.app_reader.view.viewInterface.StandardViewI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LXP on 2017/5/2.
 * 预借视图
 */

public class ReservationActivity extends BaseActivity implements ReservationViewI {
    private ViewGroup bookList;
    private View waitView;
    private View networkView;
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ReservationBookEntity book = (ReservationBookEntity) v.getTag();
            ReservationActivity.this.book = book;
            InReservationEntity entity = new InReservationEntity();
            entity.setSn(book.getBook_barcode());
            entity.setSn(book.getBookRecode());

            waitView.setVisibility(View.VISIBLE);
            presenter.inReservation(entity);
        }
    };
    private ReservationPresenter presenter;
    private ReservationBookEntity book;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        presenter = new ReservationPresenter(this, this);

        bookList = (ViewGroup) findViewById(R.id.bookList);

        waitView = findViewById(R.id.waitView);
        waitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        networkView = findViewById(R.id.networkError);
        networkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        final View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        View createBarcode = findViewById(R.id.createBarcode);
        createBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> sns = new ArrayList<>();
                String address = null;
                for(int i = 0,z=bookList.getChildCount();i<z;i++){
                    View view = bookList.getChildAt(i);
                    if (((CheckBox) view.findViewById(R.id.check)).isChecked()) {
                        ReservationBookEntity book = (ReservationBookEntity) view.getTag();
                        sns.add(book.getBook_barcode());
                        if(address != null && !address.equals(book.getDeliverAddr())){
                            Toast.makeText(ReservationActivity.this,"只能同时选择一个取书地址的书",Toast.LENGTH_SHORT).show();
                            return;
                        }else if(address == null){
                            address = book.getDeliverAddr();
                        }
                    }
                }
                if(!sns.isEmpty()){
                    Intent i = new Intent(ReservationActivity.this,ReservationBarcodeActivity.class);
                    i.putStringArrayListExtra("sns",sns);
                    i.putExtra("address",address);
                    startActivity(i);
                }else{
                    Toast.makeText(ReservationActivity.this,"请选择书",Toast.LENGTH_SHORT).show();
                }
                Log.i("123",sns.toString());
;            }
        });

        waitView.setVisibility(View.VISIBLE);
        presenter.loadData();
    }

    @Override
    public void setBookList(List<ReservationBookEntity> books) {
        networkView.setVisibility(View.INVISIBLE);
        waitView.setVisibility(View.INVISIBLE);
        bookList.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (ReservationBookEntity book : books) {
            View view = inflater.inflate(R.layout.sub_book_reservation, null);
            TextView title = (TextView) view.findViewById(R.id.bookTitle);
            TextView putAddr = (TextView) view.findViewById(R.id.putAddr);
            TextView deadLine = (TextView) view.findViewById(R.id.deadline);
            View b = view.findViewById(R.id.inResButton);

            title.setText(book.getTitle());

            String temp = book.getDeliverAddr();
            if(!StringUtils.isEmpty(temp)) {
                putAddr.setText(String.format("取书地点：%s",temp));
            }else {
                putAddr.setText("暂未投递");
            }

            Date date = dateConverter(book.getDeadline());
            if (date != null) {
                deadLine.setText(String.format("预借截止日期：%s",sdf.format(date)));
            }

            view.setTag(book);
            b.setTag(book);

            b.setOnClickListener(clickListener);

            bookList.addView(view);
        }
    }

    @Override
    public void setState(StandardViewI.Standard_State state) {
        waitView.setVisibility(View.GONE);
        if (state == StandardViewI.Standard_State.AUTH_ERROR) {
            logout();
        } else if (state == StandardViewI.Standard_State.UNBIND_CARD) {
            Intent i = new Intent(this, ReaderCardListActivity.class);
            startActivity(i);
            finish();
        }else if(state == StandardViewI.Standard_State.LIB_NOT_SUPPORT){
            networkView.setVisibility(View.VISIBLE);
            TextView tv = (TextView) networkView.findViewById(R.id.textView);
            if(tv != null){
                tv.setText(getResources().getString(R.string.lib_not_support));
            }
        } else {
            networkView.setVisibility(View.VISIBLE);
            TextView tv = (TextView) networkView.findViewById(R.id.textView);
            if(tv != null){
                tv.setText("网络出错了，请稍后再试");
            }
        }
    }

    @Override
    public void setInreservationState(StandardViewI.Standard_State state, ReservationMessage message) {
        waitView.setVisibility(View.GONE);
        if (state == StandardViewI.Standard_State.SUCCESS) {
            if(message.isState()) {
                for (int i = 0, z = bookList.getChildCount(); i < z; i++) {
                    View v = bookList.getChildAt(i);
                    if (v.getTag().equals(book)) {
                        bookList.removeView(v);
                        break;
                    }
                }
                Toast.makeText(this,"取消成功:"+message.getMessage(),Toast.LENGTH_SHORT).show();
            }else{
                String m;
                if("-1".equals(message.getCode())) {
                    m = "卡无效，请重新绑定卡";
                }else if("-2".equals(message.getCode())){
                    m = "卡密码错误，请重新绑定卡";
                }else if("lib_not_support".equals(message.getCode())){
                    m = this.getResources().getString(R.string.lib_not_support);
                }else{
                    m = message.getMessage();
                }
                Toast.makeText(this,"取消失败:"+m,Toast.LENGTH_SHORT).show();
            }
        } else if (state == StandardViewI.Standard_State.AUTH_ERROR) {
            logout();
        } else if (state == StandardViewI.Standard_State.UNBIND_CARD) {
            Intent i = new Intent(this, ReaderCardListActivity.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(this,"网络错误，请重试",Toast.LENGTH_SHORT).show();
        }
    }

    private Date dateConverter(String date) {
        if (date == null || date.isEmpty())
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
}
