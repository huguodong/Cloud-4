package com.ssitcloud.app_reader.view;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.entity.ReaderInfoEntity;
import com.ssitcloud.app_reader.presenter.ForgetPasswordPresenter;
import com.ssitcloud.app_reader.view.viewInterface.ForgetPasswordViewI;

/**
 * Created by LXP on 2017/3/27.
 * 找回密码视图
 */

public class ForgetPasswordActivity extends BaseActivity implements ForgetPasswordViewI {

    private View waitView;

    private TextView readerInfo;
    private TextView sendVcode;
    private TextView vcode;
    private TextView pwd;
    private TextView repeatPwd;

    private boolean canSendVcode = true;
    private int s = 30;
    private Handler handle = new Handler();
    private Runnable vcodeHandle = new Runnable() {
        @Override
        public void run() {
            if(s > 0){
                sendVcode.setText(s+"秒后重新发送");
                --s;
                handle.postDelayed(this,1000);
            }else{
                sendVcode.setText("发送验证码");
                s = 30;
                canSendVcode = true;
                handle.removeCallbacks(this);
            }
        }
    };
    //presenter
    private ForgetPasswordPresenter passwordPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        passwordPresenter = new ForgetPasswordPresenter(this,this);

        readerInfo = (TextView) findViewById(R.id.readerInfo);
        sendVcode = (TextView) findViewById(R.id.sendVcode);
        vcode = ((TextView) findViewById(R.id.vcode));
        pwd = (TextView) findViewById(R.id.newPwd);
        repeatPwd = (TextView) findViewById(R.id.repeatPwd);
        waitView = findViewById(R.id.waitView);


        sendVcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canSendVcode) {
                    if (readerInfo.getText().toString().isEmpty()) {
                        Toast.makeText(ForgetPasswordActivity.this, "请填写账号信息", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    canSendVcode = false;
                    passwordPresenter.sendVcode();
                }
            }
        });

        View submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readerInfo.getText().toString().isEmpty()){
                    Toast.makeText(ForgetPasswordActivity.this, "请填写账号信息", Toast.LENGTH_SHORT).show();
                    return ;
                }else if(vcode.getText().toString().isEmpty()){
                    Toast.makeText(ForgetPasswordActivity.this, "请填验证码", Toast.LENGTH_SHORT).show();
                    return ;
                } else if(pwd.getText().toString().isEmpty()){
                    Toast.makeText(ForgetPasswordActivity.this, "请填写新密码", Toast.LENGTH_SHORT).show();
                    return ;
                }else if(!pwd.getText().toString().equals(repeatPwd.getText().toString())){
                    Toast.makeText(ForgetPasswordActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                    return ;
                }
                passwordPresenter.forgetPwd();

            }
        });

        //back
        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPasswordActivity.this.finish();
            }
        });

        waitView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handle.removeCallbacks(vcodeHandle);
    }

    @Override
    public void showWait() {
        waitView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideWait() {
        waitView.setVisibility(View.GONE);
    }

    @Override
    public ReaderInfoEntity getInfo() {
        ReaderInfoEntity reader = new ReaderInfoEntity();
        String user = readerInfo.getText().toString();
        if(StringUtils.isEmail(user)){
            reader.setEmail(user.toLowerCase());
        }else if(StringUtils.isMobile(user)){
            reader.setMobile(user);
        }else if(StringUtils.isIdNumber(user)){
            reader.setId_card(user.toUpperCase());
        }else{
            reader.setLogin_name(user);
        }

        reader.setReader_pwd(pwd.getText().toString());
        return reader;
    }

    @Override
    public String getVocde() {
        return vcode.getText().toString();
    }

    @Override
    public void setSuccess() {
        Toast.makeText(this,"找回密码成功",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void setMessage(int code) {
        if(code == 0) {
            Toast.makeText(this, "发送验证码成功", Toast.LENGTH_SHORT).show();
            canSendVcode = false;
            handle.post(vcodeHandle);
            return;
        }else if(code == 1){
            Toast.makeText(this, "找不到此用户", Toast.LENGTH_SHORT).show();
        }else if(code == 2){
            Toast.makeText(this, "验证码不正确", Toast.LENGTH_SHORT).show();
        }else if(code == 3){
            Toast.makeText(this, "验证码已经过期", Toast.LENGTH_SHORT).show();
        }else if(code == -2){
            Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
        }else if(code == -3){
            Toast.makeText(this, "发送验证码失败", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
        }

        canSendVcode = true;
    }

}
