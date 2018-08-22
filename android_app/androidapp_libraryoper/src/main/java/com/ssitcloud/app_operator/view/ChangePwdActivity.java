package com.ssitcloud.app_operator.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.component.ManageActivity;
import com.ssitcloud.app_operator.component.MyApplication;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.StringUtils;
import com.ssitcloud.app_operator.presenter.ChangePwdPresenter;
import com.ssitcloud.app_operator.view.viewInterface.ChangePwdViewI;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建日期：2017/3/22 16:25
 * @author shuangjunjie
 */

public class ChangePwdActivity extends ManageActivity implements ChangePwdViewI{

    private final String TAG = "ChangePwdActivity";

    private TextView oldPwdV;
    private TextView newPwdV;
    private TextView confirmPwdV;
    private ChangePwdPresenter changePwdPresenter;

    private String passwordCharset;
    private int passwordLength = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        changePwdPresenter = new ChangePwdPresenter(this,this);

        String passwordSet = getIntent().getStringExtra("passwordSet");
        if(!StringUtils.isEmpty(passwordSet)){
            try{
                Map<String,Object> m = JsonUtils.fromJson(passwordSet,Map.class);
                passwordCharset = (String) m.get("charset");
                passwordLength = (Integer) m.get("length");
            }catch (Exception e){
            }
        }

        oldPwdV = (TextView) findViewById(R.id.change_pwd_old_password);
        newPwdV = (TextView) findViewById(R.id.change_pwd_new_password);
        confirmPwdV = (TextView) findViewById(R.id.change_pwd_confirm_password);
        Button submit = (Button) findViewById(R.id.change_pwd_submit);
        ImageView returnV = (ImageView) findViewById(R.id.change_pwd_return);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> reqMap = getReqMap();
                if (null != reqMap){
                    changePwdPresenter.changePwd(reqMap);
                }
            }
        });

        returnV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    protected Map<String,Object> getReqMap(){
        String oldPwd = oldPwdV.getText().toString();
        String newPwd = newPwdV.getText().toString();
        String confirmPwd = confirmPwdV.getText().toString();
        MyApplication app = (MyApplication) getApplication();
        Map<String,Object> map = new HashMap<>();

        if (StringUtils.isEmpty(oldPwd)){
            Toast.makeText(this, getResources().getString(R.string.change_pwd_empty_old_pwd_tip), Toast.LENGTH_SHORT).show();
            return null;
        }
        String tip = StringUtils.checkPwd(newPwd,passwordCharset,passwordLength);
        if (tip != null){
            Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
            return null;
        }else if (StringUtils.isEmpty(confirmPwd)){
            Toast.makeText(this, getResources().getString(R.string.change_pwd_empty_confirm_pwd_tip), Toast.LENGTH_SHORT).show();
            return null;
        }else if (!newPwd.equals(confirmPwd)){
            Toast.makeText(this, getResources().getString(R.string.change_pwd_empty_pwd_not_same_tip), Toast.LENGTH_SHORT).show();
            return null;
        }
        map.put("old_pwd",oldPwd);
        map.put("new_pwd",newPwd);
        map.put("operator_idx",String.valueOf(app.getOperator_idx()));

        return map;
    }



    @Override
    public void changePwdSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        logout(false);
        Intent intent = new Intent(this,SetNewPwdSuccessActivity.class);
        startActivity(intent);
    }

    @Override
    public void changePwdFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWait() {

    }

    @Override
    public void hideWait() {

    }
}
