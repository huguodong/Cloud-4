package com.ssitcloud.app_reader.view;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.entity.ReaderInfoEntity;
import com.ssitcloud.app_reader.presenter.LoginPresenter;
import com.ssitcloud.app_reader.service.DataUpdateService;
import com.ssitcloud.app_reader.view.viewInterface.LoginViewI;

/**
 * Created by LXP on 2017/3/7.
 * 登录视图
 */

public class LoginActivity extends BaseActivity implements LoginViewI{
//    private final String TAG = ""+getClass();

    private TextView user_nameView;
    private TextView user_pwdView;
    private LoginPresenter loginPersenter;
    private Resources resource;
    private volatile boolean isSubmit = false;
    private View waitView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        loginPersenter = new LoginPresenter(this,this);

        user_nameView = (TextView) findViewById(R.id.login_name);
        user_pwdView = (TextView) findViewById(R.id.password);
        waitView = findViewById(R.id.renewWaitView);
        //拦截点击事件
        waitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button sumbit = (Button) findViewById(R.id.submit);
        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReaderInfoEntity readerInfoEntity = getReaderInfo();
                if(!isSubmit/*没有提交*/ && StringUtils.isEmpty(readerInfoEntity.getEmail())
                        && StringUtils.isEmpty(readerInfoEntity.getMobile())
                        && StringUtils.isEmpty(readerInfoEntity.getId_card())
                        && StringUtils.isEmpty(readerInfoEntity.getLogin_name())){
                    Toast.makeText(LoginActivity.this,resource.getString(R.string.login_user_tip2),Toast.LENGTH_SHORT).show();
                }else if(!isSubmit/*没有提交*/ && StringUtils.isEmpty(readerInfoEntity.getReader_pwd())){
                    Toast.makeText(LoginActivity.this,resource.getString(R.string.login_pwd_tip2),Toast.LENGTH_SHORT).show();
                }else if(!isSubmit/*没有提交*/){
                    isSubmit = true;
                    loginPersenter.login(readerInfoEntity);
                }
            }
        });

        View forgetPwd = findViewById(R.id.forget_pwd);
        forgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(i);
            }
        });

        View register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        resource = getResources();
    }


    @Override
    public ReaderInfoEntity getReaderInfo() {
        String user = user_nameView.getText().toString();
        String pwd = user_pwdView.getText().toString();

        ReaderInfoEntity readerInfoEntity = new ReaderInfoEntity();
        if(StringUtils.isEmail(user)){
            readerInfoEntity.setEmail(user.toLowerCase());
        }else if(StringUtils.isMobile(user)){
            readerInfoEntity.setMobile(user);
        }else if(user.length()>5 && Character.isLetter(user.charAt(0))){
            readerInfoEntity.setLogin_name(user);
        }else if(StringUtils.isIdNumber(user)){
            readerInfoEntity.setId_card(user.toUpperCase());
        }

        readerInfoEntity.setReader_pwd(pwd);
        return readerInfoEntity;
    }

    @Override
    public void loginSuccess() {
        isSubmit = false;
        Toast.makeText(this,resource.getString(R.string.login_success),Toast.LENGTH_SHORT).show();

        //跳转到主界面
        Intent i = new Intent(this,WelComeActivity.class);
        i.putExtra(WelComeActivity.ORDER,WelComeActivity.TO_MAIN_ORDER);
        startActivity(i);
        finish();
    }

    @Override
    public void loginFail(String message) {
        isSubmit = false;
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
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
