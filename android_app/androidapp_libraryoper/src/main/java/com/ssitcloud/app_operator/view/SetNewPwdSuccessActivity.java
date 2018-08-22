package com.ssitcloud.app_operator.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ssitcloud.app_operator.R;

/**
 * 创建日期：2017/3/22 14:58
 * @author shuangjunjie
 */
public class SetNewPwdSuccessActivity extends Activity {

    private final String TAG = ""+getClass();
    private Button returnButton;
    private ImageView returnV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_pwd_success);

        returnButton = (Button) findViewById(R.id.set_new_pwd_success_submit);
        returnV = (ImageView) findViewById(R.id.set_new_pwd_success_return);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetNewPwdSuccessActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        returnV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetNewPwdSuccessActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
