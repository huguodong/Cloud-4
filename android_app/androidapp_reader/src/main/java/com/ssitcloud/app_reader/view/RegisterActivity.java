package com.ssitcloud.app_reader.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.entity.ReaderInfoEntity;
import com.ssitcloud.app_reader.presenter.RegisterPresenter;
import com.ssitcloud.app_reader.view.viewInterface.RegisterViewI;

/**
 * Created by LXP on 2017/3/6.
 * 注册成功后会返回一个intent
 * 当Result_OK内含readerInfo={读者输入信息，ReaderInfoEntity}
 */

public class RegisterActivity extends Activity implements RegisterViewI{
    private final String TAG = ""+getClass();

    private TextView userNameView;
    private TextView nameView;
    private TextView pwdView;
    private TextView mailView;
    private TextView mobileView;
    private RadioGroup sexView;
    private TextView pwd_repeatView;
    private TextView idcardView;
    private TextView vcodeView;
    private TextView sendVodeCode;
    private View progressView;
    private RegisterPresenter registerPersenter;

    private volatile  boolean isSubmit = false;
    //读者输入的数据
    private ReaderInfoEntity readerInfo;

    private boolean canSendVcode = true;
    private int s = 30;
    private Handler handle = new Handler();
    private Runnable vcodeHandle = new Runnable() {
        @Override
        public void run() {
            if(s > 0){
                sendVodeCode.setText(s+"秒后重新发送");
                --s;
                handle.postDelayed(this,1000);
            }else{
                sendVodeCode.setText("发送验证码");
                s = 30;
                canSendVcode = true;
                handle.removeCallbacks(this);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerPersenter = new RegisterPresenter(this,this.getApplicationContext());

        String s = "<font color='#FF0000'>* </font>";

        TextView usernameTip = (TextView) findViewById(R.id.username_tip);
        String temp = s+usernameTip.getText().toString();
        usernameTip.setText(Html.fromHtml(temp));
        TextView pwdTip = (TextView) findViewById(R.id.pwd_tip);
        temp = s+pwdTip.getText().toString();
        pwdTip.setText(Html.fromHtml(temp));
        TextView repwdTip = (TextView) findViewById(R.id.repwd_tip);
        temp = s+repwdTip.getText().toString();
        repwdTip.setText(Html.fromHtml(temp));
        TextView nameTip = (TextView) findViewById(R.id.name_tip);
        temp = s+nameTip.getText().toString();
        nameTip.setText(Html.fromHtml(temp));
        TextView mobileTip = (TextView) findViewById(R.id.mobile_tip);
        temp = s+mobileTip.getText().toString();
        mobileTip.setText(Html.fromHtml(temp));
        TextView vcodeTip = (TextView) findViewById(R.id.vcode_tip);
        temp = s+vcodeTip.getText().toString();
        vcodeTip.setText(Html.fromHtml(temp));

        userNameView = (TextView) this.findViewById(R.id.username);
        nameView = (TextView) this.findViewById(R.id.name);
        pwdView = (TextView) this.findViewById(R.id.pwd);
        pwd_repeatView = (TextView) this.findViewById(R.id.pwd_repeat);
        mailView = (TextView) this.findViewById(R.id.mail);
        mobileView = (TextView) this.findViewById(R.id.mobile);
        sexView = (RadioGroup) this.findViewById(R.id.sex_group);
        idcardView = (TextView) this.findViewById(R.id.id_card);

        progressView = findViewById(R.id.renewWaitView);
        vcodeView = (TextView) findViewById(R.id.vcode);
        sendVodeCode = (TextView) findViewById(R.id.sendVcode);

        sendVodeCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canSendVcode) {
                    Resources res = RegisterActivity.this.getResources();
                    if (!StringUtils.isMobile(getMobile())) {
                        Toast.makeText(RegisterActivity.this, res.getString(R.string.register_check_mobile), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    canSendVcode = false;
                    registerPersenter.sendVcode();
                }
            }
        });

        View go = findViewById(R.id.register);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReaderInfoEntity r = getReaderInfo();
                if(r == null || isSubmit){
                    return ;
                }
                isSubmit = true;
                registerPersenter.register();
            }
        });

        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
        //空事件，阻止底层被点击
        progressView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });
    }

    @Override
    public ReaderInfoEntity getReaderInfo() {
        Resources res = getResources();

        String name = nameView.getText().toString();
        String pwd = pwdView.getText().toString();
        String pwd_repeat = pwd_repeatView.getText().toString();
        String mail = mailView.getText().toString();
        String mobile = mobileView.getText().toString();
        String username = userNameView.getText().toString();
        String idcard = idcardView.getText().toString();
        String sex = "1";
        int sexCheckId = sexView.getCheckedRadioButtonId();

        //验证数据
        if(username.length()<5 || !username.matches("^[a-zA-Z][a-zA-Z0-9]*$")){
            Toast.makeText(this,res.getString(R.string.register_check_loginname),Toast.LENGTH_SHORT).show();
            return null;
        }
        if(username.indexOf('@')!= -1 || username.indexOf('.') != -1){
            Toast.makeText(this,res.getString(R.string.register_check_loginname_tip2),Toast.LENGTH_SHORT).show();
            return null;
        }
        if(StringUtils.isEmpty(pwd)){
            Toast.makeText(this,res.getString(R.string.register_check_pwd),Toast.LENGTH_SHORT).show();
            return null;
        }
        if(!StringUtils.isEqual(pwd,pwd_repeat)){
            Toast.makeText(this,res.getString(R.string.register_check_pwd2),Toast.LENGTH_SHORT).show();
            return null;
        }
        if(StringUtils.isEmpty(name)){
            Toast.makeText(this,res.getString(R.string.register_check_name),Toast.LENGTH_SHORT).show();
            return null;
        }
        switch (sexCheckId){
            case R.id.boy:
                sex = "1";
                break;
            case R.id.girl:
                sex = "0";
                break;
        }
        if(!StringUtils.isMobile(mobile)){
            Toast.makeText(this,res.getString(R.string.register_check_mobile),Toast.LENGTH_SHORT).show();
            return null;
        }
        if(!StringUtils.isEmpty(idcard) && !StringUtils.isIdNumber(idcard)){
            Toast.makeText(this,res.getString(R.string.register_check_idcard),Toast.LENGTH_SHORT).show();
            return null;
        }
        if(!StringUtils.isEmpty(mail) && !StringUtils.isEmail(mail)){
            Toast.makeText(this,res.getString(R.string.register_check_mail),Toast.LENGTH_SHORT).show();
            return null;
        }
        if(StringUtils.isEmpty(vcodeView.getText().toString())){
            Toast.makeText(this,"请输入验证码",Toast.LENGTH_SHORT).show();
            return null;
        }
        readerInfo = new ReaderInfoEntity();
        readerInfo.setEmail(mail.toLowerCase().trim());
        readerInfo.setId_card(idcard.toUpperCase().trim());
        readerInfo.setMobile(mobile.trim());
        readerInfo.setLogin_name(username.trim());
        readerInfo.setReader_pwd(pwd);
        readerInfo.setReader_sex(sex);
        readerInfo.setReader_name(name.trim());
        //注入sox
        readerInfo.setSox_tpl_id(getResources().getInteger(R.integer.sox_temple));
        return readerInfo;
    }

    @Override
    public String getMobile() {
        return mobileView.getText().toString();
    }

    @Override
    public String getVcode() {
        return vcodeView.getText().toString();
    }

    @Override
    public void sendVcodeInfo(int state) {
        Resources res = getResources();
        if(state == -1){
            Toast.makeText(this,res.getString(R.string.register_network_error),Toast.LENGTH_LONG).show();
            canSendVcode = true;
        }else if(state == 1){
            Toast.makeText(this,"发送验证码成功",Toast.LENGTH_LONG).show();
            handle.post(vcodeHandle);
            canSendVcode = false;
        }else if(state == 2){
            Toast.makeText(this,"发送验证码失败",Toast.LENGTH_LONG).show();
            canSendVcode = true;
        }
    }

    @Override
    public void registerSuccess() {
        hideWait();
        isSubmit=false;
        Toast.makeText(this,getResources().getString(R.string.register_success),Toast.LENGTH_LONG).show();

        Intent resultData = new Intent();
        resultData.putExtra("readerInfo",readerInfo);
        setResult(RESULT_OK,resultData);
        finish();
    }

    @Override
    public void registerFail(String failMessage) {
        hideWait();
        isSubmit=false;
        String message;
        Resources res = getResources();

        if("mail_repeat".equals(failMessage)){
            message = res.getString(R.string.register_mail_repeat);
        }else if("loginname_repeat".equals(failMessage)){
            message = res.getString(R.string.register_user_repeat);
        }else if("idcard_repeat".equals(failMessage)){
            message = res.getString(R.string.register_idcard_repeat);
        }else if("mobile_repeat".equals(failMessage)){
            message = res.getString(R.string.register_mobile_repeat);
        }else if(failMessage != null && failMessage.startsWith("pwd_length:")){
            message = res.getString(R.string.register_pwd_length)+failMessage.substring("pwd_length:".length());
        }else if("mail_code_invalid".equals(failMessage)){
            message = res.getString(R.string.register_mail_code_invalid);
        }else if("mail_code_error".equals(failMessage)){
            message = res.getString(R.string.register_mail_code_error);
        }else if("mobile_code_invalid".equals(failMessage)){
            message = res.getString(R.string.register_mail_code_invalid);
        }else if("mobile_code_error".equals(failMessage)){
            message = res.getString(R.string.register_mail_code_error);
        } else {
            message = res.getString(R.string.register_network_error);
        }
        Log.d(TAG,"failMessage==>"+failMessage);
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showWait() {
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideWait() {
        progressView.setVisibility(View.INVISIBLE);
    }
}
