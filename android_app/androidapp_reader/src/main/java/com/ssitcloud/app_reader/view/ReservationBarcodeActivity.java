package com.ssitcloud.app_reader.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.presenter.ReaderAuthPresenter;
import com.ssitcloud.app_reader.view.viewInterface.ReaderAuthViewI;

import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/5/3.
 * 预借二维码界面
 */

public class ReservationBarcodeActivity extends BaseActivity  implements ReaderAuthViewI {
    private ReaderAuthPresenter readerPresenter;
    private ImageView barcode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_barcode);
        readerPresenter = new ReaderAuthPresenter(this,this);
        Intent i = getIntent();
        List<String> sns = i.getStringArrayListExtra("sns");
        String address = i.getStringExtra("address");
        if(sns == null || sns.isEmpty() || address == null){
            finish();
            return ;
        }
        TextView deviceAddr = (TextView) findViewById(R.id.deviceAddr);
        deviceAddr.setText(address);
        barcode = (ImageView) findViewById(R.id.barcode);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        readerPresenter.loadBorrorData(sns);
    }

    @Override
    public void setOtherData(Map<String, Object> data) {

    }

    @Override
    public void setBarcode(Bitmap bm) {
        barcode.setImageBitmap(bm);
//        barcode.setOnClickListener(updateBarcodeClickListener);
//        barcodeView.removeAllViews();
//        barcodeView.addView(barcode);
    }

    @Override
    public void setFailView(int code) {

    }
}
