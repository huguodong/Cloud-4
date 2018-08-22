package com.ssitcloud.app_operator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.ssitcloud.app_operator.component.MyApplication;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_operator.presenter.MainPresenter;
import com.ssitcloud.app_operator.service.AppSettingIntentService;
import com.ssitcloud.app_operator.service.PermissionUpdateService;
import com.ssitcloud.app_operator.view.IndexActivity;
import com.ssitcloud.app_operator.view.LoginActivity;
import com.ssitcloud.app_operator.view.viewInterface.MainViewI;

import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements MainViewI{

    private final String TAG = "MainActivity";

    private MainPresenter mainPresenter;
    private MyApplication app;
    private String operator_type;           //操作员类型
    private Integer library_idx;            //操作员所属图书馆idx
    private List listMessage;               //消息提醒数据
    private String result;                  //存放菜单信息
    private volatile  boolean isAutoLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        app = (MyApplication) getApplication();
        //更新appsetting
        AppSettingIntentService.startInterService(app);

        mainPresenter = new MainPresenter(this,this);

        LoginInfoDbEntity model = mainPresenter.getLoginInfo();
        if (null != model){
            Map reqMap = JsonUtils.fromJson(JsonUtils.toJson(model),Map.class);
            reqMap.remove("operator_idx");
            reqMap.remove("mobile");
            reqMap.remove("login_time");
            isAutoLogin = true;
            mainPresenter.login(reqMap,isAutoLogin);
        }else{
            //目的是1.5秒后打开登录页面
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    /* Create an Intent that will start the Main WordPress Activity. */
                    Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                }
            }, 1500);
        }
    }

    @Override
    public void loginSuccess(ResultEntity result) {
//        isSubmit = false;
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

        if (!isAutoLogin){
            Toast.makeText(this,R.string.login_success,Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this,IndexActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFail(String message) {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }

}
