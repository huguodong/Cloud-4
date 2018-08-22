package com.ssitcloud.app_reader.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.LoginBizI;
import com.ssitcloud.app_reader.biz.ReaderBizI;
import com.ssitcloud.app_reader.biz.impl.LoginBizImpl;
import com.ssitcloud.app_reader.biz.impl.ReaderBizImpl;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_reader.entity.ReaderInfoEntity;

import java.net.SocketException;

/**
 * Created by LXP on 2017/4/15.
 * 个人信息修改
 */

public class ModifyPersonalInfoActivity extends BaseActivity {
    private TextView username;
    private RadioGroup sexGroup;
    private View waitView;
    private ReaderBizI readerBiz;
    private LoginBizI loginBiz;
    private LoginInfoDbEntity loginReturnData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);
        readerBiz = new ReaderBizImpl(this);
        loginBiz = new LoginBizImpl(getResources(),this);


        loginReturnData = loginBiz.getLoginReturnData();
        if(loginReturnData == null){
            logout();
            return ;
        }

        username = (TextView) findViewById(R.id.name);
        username.setText(loginReturnData.getReader_name());
        sexGroup = (RadioGroup) findViewById(R.id.sex_group);
        if(loginReturnData.getReader_sex().equals("1")){
            sexGroup.check(R.id.boy);
        }else{
            sexGroup.check(R.id.girl);
        }
        waitView = findViewById(R.id.waitView);
        waitView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });

        View submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String sex;
                int sId = sexGroup.getCheckedRadioButtonId();
                if (sId == R.id.boy) {
                    sex = "1";
                } else {
                    sex = "0";
                }
                if (name.isEmpty() || name.length() > 25) {
                    Toast.makeText(ModifyPersonalInfoActivity.this, "请输入正确的姓名", Toast.LENGTH_SHORT).show();
                    return;
                }

                ReaderInfoEntity readerinfo = new ReaderInfoEntity();
                readerinfo.setReader_name(name);
                readerinfo.setReader_sex(sex);
                readerinfo.setReader_idx(loginReturnData.getReader_idx());

                modify(readerinfo);
            }
        });

        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPersonalInfoActivity.this.finish();
            }
        });
    }

    private void modify(ReaderInfoEntity readerinfo){
        if(waitView.getVisibility() == View.VISIBLE){
            return ;
        }
        waitView.setVisibility(View.VISIBLE);
        AsyncTask<ReaderInfoEntity,Void,Boolean> task = new AsyncTask<ReaderInfoEntity, Void, Boolean>() {
            private volatile int state = 0;
            @Override
            protected Boolean doInBackground(ReaderInfoEntity... params) {
                try {
                    boolean b = readerBiz.modifyInfo(params[0]);
                    if(b){
                        try{
                            loginBiz.obtainReader(params[0].getReader_idx());
                        }catch (Exception e){
                            //only update info
                        }
                    }
                    state = 1;
                    return b;
                } catch (SocketException e) {
                    state = -2;
                } catch (AuthException e) {
                    state = -1;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Boolean aVoid) {
                waitView.setVisibility(View.GONE);
                if(state == 1){
                    if(aVoid) {
                        ModifyPersonalInfoActivity.this.finish();
                        Toast.makeText(ModifyPersonalInfoActivity.this, "修改信息成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ModifyPersonalInfoActivity.this, "修改信息失败", Toast.LENGTH_SHORT).show();
                    }
                }else if(state == -2){
                    Toast.makeText(ModifyPersonalInfoActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }else if(state == -1){
                    ModifyPersonalInfoActivity.this.logout();
                }
            }
        };
        task.execute(readerinfo);
    }
}
