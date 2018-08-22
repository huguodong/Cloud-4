package com.ssitcloud.app_reader.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.entity.FeedbackEntity;
import com.ssitcloud.app_reader.presenter.FeedbackPresenter;
import com.ssitcloud.app_reader.view.viewInterface.StandardViewI;

import java.sql.Timestamp;

/**
 * Created by LXP on 2017/4/18.
 * 已经反馈视图
 */

public class FeedbackActivity extends BaseActivity implements StandardViewI<Void>{
    private FeedbackPresenter presenter;
    private TextView feedbackContent;
    private View waitView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        presenter = new FeedbackPresenter(this,this);
        feedbackContent = (TextView) findViewById(R.id.feedbackContent);

        View submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FeedbackEntity feedback = new FeedbackEntity();
                feedback.setFeedback_value(feedbackContent.getText().toString());
                feedback.setFeedback_type("1");
                feedback.setCreatetime(new Timestamp(System.currentTimeMillis()));
                if(feedback.getFeedback_value().isEmpty()){
                    Toast.makeText(FeedbackActivity.this,"请输入反馈内容",Toast.LENGTH_SHORT).show();
                    return ;
                }
                waitView.setVisibility(View.VISIBLE);
                presenter.sendFeedback(feedback);
            }
        });

        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackActivity.this.finish();
            }
        });

        waitView = findViewById(R.id.waitView);
        waitView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });
    }

    @Override
    public void setView(Standard_State state, Void aVoid) {
        waitView.setVisibility(View.GONE);
        if(state == Standard_State.AUTH_ERROR){
            logout();
        }else if(state == Standard_State.SUCCESS){
            Toast.makeText(this,"反馈成功",Toast.LENGTH_SHORT).show();
            finish();
        }else if(state == Standard_State.FAIL){
            Toast.makeText(this,"反馈失败",Toast.LENGTH_SHORT).show();
        }else if(state == Standard_State.NETOWRK_ERROR){
            Toast.makeText(this,"网络连接失败",Toast.LENGTH_SHORT).show();
        }
    }
}
