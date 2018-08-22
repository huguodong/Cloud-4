package com.ssitcloud.app_operator.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.LoginBizI;
import com.ssitcloud.app_operator.biz.impl.LoginBizImpl;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.StringUtils;
import com.ssitcloud.app_operator.presenter.ForgetPwdPresenter;
import com.ssitcloud.app_operator.view.viewInterface.ForgetPwdViewI;

import java.util.Map;

/**
 * 创建日期：2017年6月8日14:33:12
 *
 * @author LXP
 */
public class ForgetPwdActivity extends Activity implements ForgetPwdViewI {

    private TextView mobileV;
    private TextView vcodeV;
    private TextView pwdV;
    private TextView rpwdV;
    private View waitView;
    private ForgetPwdPresenter forgetPwdPresenter;
    private TextView sendVcode;

    //验证码倒计时
    private int m = 60;
    private String s;
    private Handler handle = new Handler();
    private Runnable vcodeRunnable = new Runnable() {
        @Override
        public void run() {
            if (m > 0) {
                sendVcode.setText(m + "秒后重新发送");
                handle.postDelayed(vcodeRunnable, 1_000);
            } else {
                sendVcode.setText(s);
                sendVcode.setClickable(true);
                m = 60;
            }
            m--;
        }
    };
    private String passwordCharset;
    private int passwordLength = 6;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        forgetPwdPresenter = new ForgetPwdPresenter(this, this);
        waitView = findViewById(R.id.waitview);

        mobileV = (TextView) findViewById(R.id.mobile);
        vcodeV = (TextView) findViewById(R.id.vcode);
        pwdV = (TextView) findViewById(R.id.pwd);
        rpwdV = (TextView) findViewById(R.id.repeat_pwd);

        sendVcode = (TextView) findViewById(R.id.sendVcode);
        s = sendVcode.getText().toString();
        sendVcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = mobileV.getText().toString();
                if (!StringUtils.isEmpty(mobile)) {
                    v.setClickable(false);
                    forgetPwdPresenter.sendVcode(mobile);
                } else {
                    Toast.makeText(ForgetPwdActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button submit = (Button) findViewById(R.id.forget_pwd_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = mobileV.getText().toString();
                String vcode = vcodeV.getText().toString();
                String pwd = pwdV.getText().toString();
                String rpwd = rpwdV.getText().toString();
                if (StringUtils.isEmpty(mobile)) {
                    Toast.makeText(ForgetPwdActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtils.isEmpty(vcode)) {
                    Toast.makeText(ForgetPwdActivity.this, "请输入手机验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tip = StringUtils.checkPwd(pwd,passwordCharset,passwordLength);
                if (tip != null) {
                    Toast.makeText(ForgetPwdActivity.this, tip, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pwd.equals(rpwd)) {
                    Toast.makeText(ForgetPwdActivity.this, "两次密码输入不相同", Toast.LENGTH_SHORT).show();
                    return;
                }
                forgetPwdPresenter.changePwd(mobile,vcode,pwd);
            }
        });

        ImageView returnV = (ImageView) findViewById(R.id.forget_pwd_return);
        returnV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handle.removeCallbacks(vcodeRunnable);
    }

    @Override
    public void changePwd(int state) {
        if(state == changePwdSuccess){
            Toast.makeText(this, "修改密码成功", Toast.LENGTH_SHORT).show();
            LoginBizI loginBizI = new LoginBizImpl(getResources(),this);
            loginBizI.logout();
            finish();
        }else if(state == changePwdUnknowUser){
            Toast.makeText(this, "没有此用户", Toast.LENGTH_SHORT).show();
        }else if(state == vcodeError){
            Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
        }else if(state == vcodeOld){
            Toast.makeText(this, "验证码过期", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void sendVcode(int state) {
        if (state == sendVcodeSuccess) {
            Toast.makeText(this, "发送验证码成功", Toast.LENGTH_SHORT).show();
            handle.post(vcodeRunnable);
        } else if (state == sendVcodeFail) {
            Toast.makeText(this, "发送验证码失败", Toast.LENGTH_SHORT).show();
            sendVcode.setClickable(true);
        } else if (state == sendVcodeUnknowUser) {
            Toast.makeText(this, "没有此用户", Toast.LENGTH_SHORT).show();
            sendVcode.setClickable(true);
        } else {
            Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
            sendVcode.setClickable(true);
        }
    }

    @Override
    public void setPasswordType(String type) {
        if(type != null){
            try {
                Map<String,Object> m = JsonUtils.fromJson(type,Map.class);
                passwordCharset = (String) m.get("charset");
                passwordLength = (Integer) m.get("length");
            } catch (Exception e) {
            }
        }

    }

    @Override
    public void showWait() {
        waitView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideWait() {
        waitView.setVisibility(View.INVISIBLE);
    }
}
