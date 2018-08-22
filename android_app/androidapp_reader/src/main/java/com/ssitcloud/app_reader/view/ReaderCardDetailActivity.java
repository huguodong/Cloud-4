package com.ssitcloud.app_reader.view;

import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.presenter.ReaderCardDetailPresenter;
import com.ssitcloud.app_reader.view.viewInterface.ReaderCardDetailViewI;

/**
 * Created by LXP on 2017/3/23.
 * 读者详情界面
 */

public class ReaderCardDetailActivity extends BaseActivity implements ReaderCardDetailViewI {
    private ReaderCardDbEntity card;

    private TextView cardNo;
    private View nameLayout;
    private TextView readerName;
    private TextView libraryName;
    private TextView deposit;//押金
    private TextView advanceCharge;//预付款
    private TextView arrearage;//欠款
    private TextView alreadyNum;//已借数
    private TextView allNum;//总共可借

    private View waitView;//等待视图

    private ReaderCardDetailPresenter readerCardDetailPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_card_detail);
        readerCardDetailPresenter = new ReaderCardDetailPresenter(this,this);

        if(savedInstanceState == null) {
            Intent i = getIntent();
            card = (ReaderCardDbEntity) i.getSerializableExtra("cardData");
        }else{
            card = (ReaderCardDbEntity) savedInstanceState.getSerializable("cardData");
        }

        if(card == null){
            finish();
        }

        cardNo = (TextView) findViewById(R.id.card_no);
        nameLayout = findViewById(R.id.nameLayout);
        readerName = (TextView) findViewById(R.id.readerName);
        libraryName = (TextView) findViewById(R.id.libraryName);
        deposit = (TextView) findViewById(R.id.deposit);//押金
        advanceCharge = (TextView) findViewById(R.id.advanceCharge);//预付款
        arrearage= (TextView) findViewById(R.id.arrearage);//欠款
        alreadyNum= (TextView) findViewById(R.id.alreadyNum);//已借数
        allNum= (TextView) findViewById(R.id.allNum);//总共可借
        waitView = findViewById(R.id.waitView);

        waitView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });
        //解绑卡
        View unbind =findViewById(R.id.unbind);
        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUnbindCardDialog();
            }
        });

        //激活卡
        View preferCard = findViewById(R.id.preferCard);
        preferCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readerCardDetailPresenter.setPreferCard(card);
            }
        });

        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReaderCardDetailActivity.this.finish();
            }
        });

        setView(card);
    }

    @Override
    public void setMessageView(int code,int orderCode) {
        if(code == -1) {
            logout();
        }else if(code == -2){
            Toast.makeText(this,"网络连接失败",Toast.LENGTH_SHORT).show();
        }else if(code == 0){
            Toast.makeText(this,"操作成功",Toast.LENGTH_SHORT).show();
            if(orderCode == 1){//解绑卡操作
                Intent i = new Intent();
                i.putExtra("ReaderCardDbEntity",card);
                i.putExtra("order",1);
                setResult(RESULT_OK,i);
                finish();
            } else if(orderCode == 2){//设置首选卡
                Intent i = new Intent();
                i.putExtra("ReaderCardDbEntity",card);
                i.putExtra("order",2);
                setResult(RESULT_OK,i);
                finish();
            }
        }else if(code == 1){
            Toast.makeText(this,"操作失败",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public ReaderCardDbEntity getCard() {
        return card;
    }

    @Override public void showWait() { waitView.setVisibility(View.VISIBLE); }

    @Override public void hideWait() { waitView.setVisibility(View.GONE); }

    @Override
    public void setView(ReaderCardDbEntity card) {
        Resources res = getResources();
        String moneyUnit = res.getString(R.string.local_money);
        if(StringUtils.isEmpty(card.getReader_name())){
            nameLayout.setVisibility(View.GONE);
        }else{
            readerName.setText(card.getReader_name());
        }
        cardNo.setText(card.getCard_no());
        libraryName.setText(StringUtils.getStr(card.getLib_name(),"暂无"));
        deposit.setText(StringUtils.getStr(card.getDeposit(),"0")+moneyUnit);
        advanceCharge.setText(StringUtils.getStr(card.getAdvance_charge(),"0")+moneyUnit);
        arrearage.setText(StringUtils.getStr(card.getArrearage(),"0")+moneyUnit);
        int allown_loancount = card.getAllown_loancount() != null?card.getAllown_loancount():0;
        int aurplus_count = card.getSurplus_count() != null?card.getSurplus_count():0;
        allNum.setText("/"+allown_loancount);
        int a = allown_loancount-aurplus_count;
        alreadyNum.setText(""+(a>-1?a:0));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("cardData",card);
    }

    private void showUnbindCardDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确定要解绑此卡？");
        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                readerCardDetailPresenter.unBindCard(card);
            }
        });
        builder.create().show();
    }
}
