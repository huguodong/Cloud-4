package com.ssitcloud.app_operator.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.LoginBizI;
import com.ssitcloud.app_operator.biz.impl.LoginBizImpl;
import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.StringUtils;
import com.ssitcloud.app_operator.component.ManageActivity;
import com.ssitcloud.app_operator.component.MyApplication;
import com.ssitcloud.app_operator.entity.OperatorEntity;
import com.ssitcloud.app_operator.presenter.UpdateOperatorInfoPresenter;
import com.ssitcloud.app_operator.view.Dialog.MyAlertDialog;
import com.ssitcloud.app_operator.view.viewInterface.UpdateOperatorInfoViewI;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 创建日期：2017/3/23 11:40
 *
 * @author shuangjunjie
 */

public class UpdateOperatorInfoActivity extends ManageActivity implements UpdateOperatorInfoViewI {

    private final String TAG = "UpdateOperatorInfoActivity";
    private MyApplication app;
    private TextView operatorNameV;
    private TextView idCardV;
    private TextView mobileV;
    private TextView emailV;
    private String rMobile;
    private String rEmail;
    private String rIdCard;
    private ImageView returnV;
    private TextView setNewPwdV;
    private Button submit;
    private Button logout;
    private String rOperator_idx;

    private UpdateOperatorInfoPresenter updateOperatorInfoPresenter;
    private Dialog alertDialog;
    private TextView cancelTextView;            //弹窗 取消
    private TextView confirmTextView;           //弹窗 确认
    private LoginBizI loginBiz;

    private String passwordSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_operator_info);

        if (app == null) {
            // 得到Application对象
            app = (MyApplication) getApplication();
        }

        operatorNameV = (TextView) findViewById(R.id.update_operator_info_operator_name);
        mobileV = (TextView) findViewById(R.id.update_operator_info_mobile);
        emailV = (TextView) findViewById(R.id.update_operator_info_email);
        idCardV = (TextView) findViewById(R.id.update_operator_info_id_card);
        returnV = (ImageView) findViewById(R.id.update_operator_info_return);
        setNewPwdV = (TextView) findViewById(R.id.update_operator_info_set_new_pwd);
        logout = (Button) findViewById(R.id.logout);

        rOperator_idx = String.valueOf(app.getOperator_idx());
        rMobile = app.getMobile();
        rEmail = app.getEmail();
        rIdCard = app.getId_card();

        operatorNameV.setText(app.getOperator_name());
        if (null != rMobile) {
            mobileV.setText(rMobile);
        }
        if (null != rEmail) {
            emailV.setText(rEmail);
        }
        if (null != rIdCard) {
            idCardV.setText(rIdCard);
        }

        submit = (Button) findViewById(R.id.update_operator_info_submit);
        updateOperatorInfoPresenter = new UpdateOperatorInfoPresenter(this, this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> reqMap = getReqMap();
                if (null != reqMap) {
                    showWaitView();
                    updateOperatorInfoPresenter.updateOperatorInfo(reqMap);
                }
            }
        });

        returnV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setNewPwdV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateOperatorInfoActivity.this, ChangePwdActivity.class);
                intent.putExtra("passwordSet", passwordSet);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(UpdateOperatorInfoActivity.this).
                        create();
                final Window window = MyAlertDialog.setDialog(alertDialog, UpdateOperatorInfoActivity.this);

                ((TextView) window.findViewById(R.id.dialog_tip)).setText("是否确认退出？");
                cancelTextView = (TextView) window.findViewById(R.id.dialog_cancel);

                confirmTextView = (TextView) window.findViewById(R.id.dialog_confirm);

                cancelTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                confirmTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        logout(true);
                    }
                });
            }
        });

        //获取用户信息
        showWaitView();
        loginBiz = new LoginBizImpl(this.getResources(), this);
        loginBiz.loginCheck()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OperatorEntity>() {
                    @Override
                    public void accept(@NonNull OperatorEntity operatorEntity) throws Exception {
                        hideWaitView();
                        passwordSet = operatorEntity.getPasswordSet();
                        mobileV.setText(operatorEntity.getMobile());
                        emailV.setText(operatorEntity.getEmail());
                        idCardV.setText(operatorEntity.getId_card());
                        operatorNameV.setText(operatorEntity.getOperator_name());

                        rMobile = operatorEntity.getMobile();
                        rEmail = operatorEntity.getEmail();
                        rIdCard = operatorEntity.getId_card();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if ("logout".equals(throwable.getMessage())) {
                            logout(true);
                        } else {
                            Toast.makeText(UpdateOperatorInfoActivity.this, "网络超时，获取用户信息失败", Toast.LENGTH_SHORT).show();
                            hideWaitView();
                        }
                    }
                });

        //设置等待界面无法点击
        findViewById(R.id.waitview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    protected Map<String, Object> getReqMap() {
        String mobile = mobileV.getText().toString();
        String email = emailV.getText().toString();

        Map<String, Object> map = new HashMap<>();

        if (!StringUtils.isMobile(mobile)) {
            Toast.makeText(this, getResources().getString(R.string.mobile_format), Toast.LENGTH_SHORT).show();
            return null;
        }
        map.put("mobile", mobile);
        if (!StringUtils.isEmail(email)) {
            Toast.makeText(this, getResources().getString(R.string.email_format), Toast.LENGTH_SHORT).show();
            return null;
        }
        map.put("email", email);
//        if (!StringUtils.isIdNumber(idCard)){
//            Toast.makeText(this, getResources().getString(R.string.id_card_format), Toast.LENGTH_SHORT).show();
//            return null;
//        }
//        if (!idCard.equals(rIdCard)){
//            map.put("id_card",idCard);
//        }
        map.put("operator_idx", Integer.valueOf(rOperator_idx));

        return map;
    }


    @Override
    public void updateSuccess(ResultEntity result) {
        Map map = JsonUtils.fromJson(JsonUtils.toJson(result.getResult()), Map.class);
        app.setMobile((String) map.get("mobile"));
        app.setEmail((String) map.get("email"));
        app.setId_card((String) map.get("id_card"));
        app.setOperator_name(String.valueOf(map.get("operator_name")));
        Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void updateFail(String message) {
        hideWaitView();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWait() {

    }

    @Override
    public void hideWait() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("个人信息Activity", "个人信息Activity未销毁");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    private void showWaitView() {
        findViewById(R.id.waitview).setVisibility(View.VISIBLE);
    }

    private void hideWaitView() {
        findViewById(R.id.waitview).setVisibility(View.INVISIBLE);
    }
}
