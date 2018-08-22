package com.ssitcloud.app_reader.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.presenter.ChangePasswordPresenter;
import com.ssitcloud.app_reader.view.viewInterface.ChangePasswordViewI;

/**
 * Created by LXP on 2017/3/27.
 * 修改密码
 */

public class ChangePasswordActivity extends BaseActivity implements ChangePasswordViewI{
    private TextView oldPwd;
    private TextView newPwd;
    private TextView repeatPwd;

    private View waitView;

    private ChangePasswordPresenter changePasswordPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        changePasswordPresenter = new ChangePasswordPresenter(this,this);

        oldPwd = (TextView) findViewById(R.id.oldPwd);
        newPwd = (TextView) findViewById(R.id.newPwd);
        repeatPwd = (TextView) findViewById(R.id.repeatPwd);

        waitView = findViewById(R.id.waitView);
        View back = findViewById(R.id.back);

        View submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oldPwd.getText().toString().isEmpty()){
                    Toast.makeText(ChangePasswordActivity.this,"请输入原密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newPwd.getText().toString().isEmpty()){
                    Toast.makeText(ChangePasswordActivity.this,"请输入新密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!newPwd.getText().toString().equals(repeatPwd.getText().toString())){
                    Toast.makeText(ChangePasswordActivity.this,"密码不相同",Toast.LENGTH_SHORT).show();
                    return;
                }
                changePasswordPresenter.changePwd();
            }
        });

        //back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordActivity.this.finish();
            }
        });
        waitView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });
    }

    @Override
    public void success(){
        Toast.makeText(this,"修改密码成功",Toast.LENGTH_SHORT).show();
        logout();
    }

    @Override
    public void fail(int code){
        if(code == -2){
            Toast.makeText(this,"网络连接失败",Toast.LENGTH_SHORT).show();
        }else if(code == -1){
            Toast.makeText(this,"登陆身份过期，请重新登陆",Toast.LENGTH_SHORT).show();
            logout();
        }else{
            Toast.makeText(this,"原密码不正确",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showWait(){
        waitView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideWait(){
        waitView.setVisibility(View.GONE);
    }

    @Override
    public String getOldPwd() {
        return oldPwd.getText().toString();
    }

    @Override
    public String getNewPwd() {
        return newPwd.getText().toString();
    }
}
