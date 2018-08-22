package com.ssitcloud.app_operator.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.StringUtils;
import com.ssitcloud.app_operator.component.MyApplication;
import com.ssitcloud.app_operator.presenter.LoginPresenter;
import com.ssitcloud.app_operator.service.PermissionUpdateService;
import com.ssitcloud.app_operator.view.viewInterface.LoginViewI;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建日期：2017/3/17 14:33
 * @author shuangjunjie
 */
public class LoginActivity extends Activity implements LoginViewI{
    private final String TAG = ""+getClass();

    private MyApplication app;
    private TextView operName;
    private TextView operPwd;
    private TextView forgetPwd;
    private LoginPresenter loginPresenter;
    private View waitView;
    private String operator_type;           //操作员类型
    private Integer library_idx;            //操作员所属图书馆idx

    private volatile boolean isSubmit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginPresenter = new LoginPresenter(this,this);

//        LoginInfoDbEntity model = loginPresenter.getLoginInfo();
//
//        if (null != model){
//            Map reqMap = JsonUtils.fromJson(JsonUtils.toJson(model),Map.class);
//            reqMap.remove("operator_idx");
//            reqMap.remove("mobile");
//            reqMap.remove("login_time");
//            isAutoLogin = true;
//            loginPresenter.login(reqMap,isAutoLogin);
//            return ;
//        }

        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        operName = (TextView) findViewById(R.id.user_name);
        operPwd = (TextView) findViewById(R.id.user_pwd);
        forgetPwd = (TextView) findViewById(R.id.login_forget_pwd);
        waitView = findViewById(R.id.waitview);

        waitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        forgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgetPwdActivity.class);
                startActivity(intent);
            }
        });

        Button go = (Button) findViewById(R.id.button_login);

        go.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Map<String,Object> reqMap = null;
                    reqMap = getReqMap();

                    /**
                     * 只要有一种方式不为空即可
                     */
                    if(!isSubmit && !reqMap.containsKey("mobile")
                            && !reqMap.containsKey("email")
                            && !reqMap.containsKey("id_card")
                            && !reqMap.containsKey("operator_name")){
                        Toast.makeText(LoginActivity.this,LoginActivity.this.getString(R.string.login_user_empty_tip),Toast.LENGTH_SHORT).show();
                    }
                    else if (!isSubmit && !reqMap.containsKey("operator_pwd")){
                        Toast.makeText(LoginActivity.this,LoginActivity.this.getString(R.string.login_pwd_tip),Toast.LENGTH_SHORT).show();
                    }
                    else if (!isSubmit){
                        isSubmit = true;
                        loginPresenter.login(reqMap);
                    }
            }
        });
    }

    @Override
    public void loginSuccess(ResultEntity result) {
        isSubmit = false;
        Map<String,Object> o = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()), Map.class);
        app = (MyApplication) getApplication();

        operator_type = o.get("operator_type").toString();
        library_idx = Integer.parseInt(o.get("library_idx").toString());

        app.setOperator_idx(Integer.parseInt(o.get("operator_idx").toString()));
        app.setOperator_id(o.get("operator_id").toString());
        app.setOperator_name(o.get("operator_name").toString());
        app.setOperator_type(operator_type);

        if (null != o.get("mobile")){
            app.setMobile(o.get("mobile").toString());
        }
        if (null != o.get("email")){
            app.setEmail(o.get("email").toString());
        }
        if (null != o.get("id_card")){
            app.setId_card(o.get("id_card").toString());
        }

        app.setLibrary_idx(library_idx);
        app.setLib_id(o.get("lib_id").toString());
        app.setLib_name(o.get("lib_name").toString());

        Toast.makeText(this,R.string.login_success,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,IndexActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFail(String message) {
        isSubmit = false;
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private Map<String,Object> getReqMap(){
        String user = operName.getText().toString();
        String userPwd = operPwd.getText().toString();
        Map<String,Object> map = new HashMap<>();

        if (!StringUtils.isEmpty(user)) {
            if (StringUtils.isEmail(user)) {
                map.put("email", user);
                Log.d(TAG, "init login info email");
            } else if (StringUtils.isMobile(user)) {
                map.put("mobile", user);
                Log.d(TAG, "init login info mobile");
            }
//            else if (StringUtils.isIdNumber(user)) {
//                map.put("id_card", user);
//                Log.d(TAG, "init login info id_card");
//            }
            else if (Character.isLetter(user.charAt(0))) {
                map.put("operator_name", user);
                Log.d(TAG, "init login info operator_name");
            }
        }
        if (!StringUtils.isEmpty(userPwd)) {
            map.put("operator_pwd", userPwd);
        }
        return map;
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
