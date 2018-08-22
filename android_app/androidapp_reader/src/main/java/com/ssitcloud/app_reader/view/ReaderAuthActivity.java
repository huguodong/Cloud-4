package com.ssitcloud.app_reader.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.presenter.ReaderAuthPresenter;
import com.ssitcloud.app_reader.view.viewInterface.ReaderAuthViewI;

import java.util.Map;

/**
 * Created by LXP on 2017/3/24.
 * 读者认证界面
 */

public class ReaderAuthActivity extends BaseActivity implements ReaderAuthViewI {

    private TextView readerName;
    private TextView cardNo;
    private TextView libraryName;
    private ImageView barcode;
    private ViewGroup barcodeView;
    private ReaderAuthPresenter readerPresenter;
    private View updatebarcodeWait;
    private View.OnClickListener updateBarcodeClickListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_auth);
        readerPresenter = new ReaderAuthPresenter(this,this);

        readerName = (TextView) findViewById(R.id.readerName);
        cardNo = (TextView) findViewById(R.id.cardNo);
        barcode = (ImageView) findViewById(R.id.twoBarcode);
        barcodeView = (ViewGroup) findViewById(R.id.barcodeView);
        libraryName = (TextView) findViewById(R.id.libraryName);
        updatebarcodeWait = findViewById(R.id.updatebarcodeWait);
        updatebarcodeWait.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });

        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReaderAuthActivity.this.finish();
            }
        });

        updateBarcodeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(updatebarcodeWait.getVisibility() == View.VISIBLE){
                    return ;
                }
                updatebarcodeWait.setVisibility(View.VISIBLE);
                readerPresenter.updatePuk();
            }
        };

        readerPresenter.loadData();
    }

    @Override
    public void setOtherData(Map<String, Object> data) {
        updatebarcodeWait.setVisibility(View.GONE);
        readerName.setText((String) data.get("readerName"));
        cardNo.setText((String) data.get("cardNo"));
        libraryName.setText((String) data.get("libraryName"));
    }

    @Override
    public void setBarcode(Bitmap bm) {
        updatebarcodeWait.setVisibility(View.GONE);
        barcode.setImageBitmap(bm);
        barcode.setOnClickListener(updateBarcodeClickListener);
        barcodeView.removeAllViews();
        barcodeView.addView(barcode);
    }

    @Override
    public void setFailView(int code) {
        updatebarcodeWait.setVisibility(View.GONE);
        String message;
        if(code == -1){
            message = "请重新登陆";
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
            logout();
        }else if(code == -3){
            message = "请先绑定读者卡";
            barcodeView.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.sub_message_view,null);
            TextView tv = (TextView) v.findViewById(R.id.message);
            tv.setText(message);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            barcodeView.addView(v,params);
        }else if(code == -4){
            message = "获取二维码配置失败";
            barcodeView.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.sub_message_view,null);
            TextView tv = (TextView) v.findViewById(R.id.message);
            tv.setText(message);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            barcodeView.addView(v,params);
            v.setOnClickListener(updateBarcodeClickListener);
        }else if(code == -4){
            message = "暂时不支持此图书馆，请切换常用卡";
            barcodeView.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.sub_message_view,null);
            TextView tv = (TextView) v.findViewById(R.id.message);
            tv.setText(message);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            barcodeView.addView(v,params);
        }
    }
}
