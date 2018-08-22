package com.ssitcloud.app_reader.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.databinding.ActivityPersonalSettingsBinding;
import com.ssitcloud.app_reader.fragment.ReturnBookSetting;
import com.ssitcloud.app_reader.myview.SwitchImageButton;
import com.ssitcloud.app_reader.presenter.PersonalSettingPresenter;
import com.ssitcloud.app_reader.view.viewInterface.PersonalSettingViewI;

/**
 * Created by LXP on 2017/3/23.
 * 个人设置
 */
public class PersonalSettingActivity extends BaseActivity implements PersonalSettingViewI{
    private TextView remindTv;
    private PersonalSettingPresenter personalSettingPresenter;
    private int remindTime;
    private SwitchImageButton messagePushButton;

    private ActivityPersonalSettingsBinding dataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_personal_settings);
        personalSettingPresenter = new PersonalSettingPresenter(this,this);

        View changePwd = findViewById(R.id.changePwd);
        changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ChangePasswordActivity.class);
            }
        });

        //设置返回
        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalSettingActivity.this.finish();
            }
        });

        //修改还书提醒
        View returnRemind = findViewById(R.id.returnRemind);
        returnRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReturnBookSetting();
            }
        });
        remindTv = (TextView) findViewById(R.id.returnRemindTime);

        //修改个人信息
        View modifyInfo = findViewById(R.id.modifyInfo);
        modifyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ModifyPersonalInfoActivity.class);
            }
        });

        //设置登出
        View logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalSettingPresenter.logout(new PersonalSettingPresenter.Callback() {
                    @Override
                    public void success(Object obj) {
                        PersonalSettingActivity.this.logout();
                    }

                    @Override
                    public void fail(Object obj) { }
                });
            }
        });

        //设置通知开关
        messagePushButton = (SwitchImageButton) findViewById(R.id.messagePushSwitch);
        messagePushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalSettingPresenter.setMessagePushState(messagePushButton.isChecked());
            }
        });

        //意见反馈
        View feedbackView = findViewById(R.id.feedback);
        feedbackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FeedbackActivity.class);
            }
        });

        //网页修改设置
        dataBinding.websetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(WebSettingActivity.class);
            }
        });

        personalSettingPresenter.loadData();
    }

    private void startActivity(Class<?> cls) {
        Intent i = new Intent(PersonalSettingActivity.this,cls);
        startActivity(i);
    }

    @Override
    public void setRemindTime(int day) {
        remindTime = day;
        if(day == -1){
            remindTv.setText("不提醒");
        }else {
            remindTv.setText("最后" + day + "天");
        }
    }

    @Override
    public void setMessagePushButton(boolean isCheck){
        messagePushButton.setChecked(isCheck);
    }

    private void showReturnBookSetting(){
        ReturnBookSetting rbs = new ReturnBookSetting();
        Bundle args = new Bundle();
        args.putInt("remindTime",remindTime);
        rbs.setArguments(args);
        rbs.setOkListener(new ReturnBookSetting.OkListener() {
            @Override
            public void onListener(int remindTime) {
                personalSettingPresenter.setRemindTime(remindTime);
            }
        });
        rbs.show(getFragmentManager(),"ReturnBookSetting");
    }
}
