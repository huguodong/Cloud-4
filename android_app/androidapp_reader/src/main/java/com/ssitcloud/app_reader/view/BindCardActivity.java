package com.ssitcloud.app_reader.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.entity.BindCardEntity;
import com.ssitcloud.app_reader.entity.LibraryEntity;
import com.ssitcloud.app_reader.presenter.BindCardPresenter;
import com.ssitcloud.app_reader.view.viewInterface.BindCardViewI;

/**
 * Created by LXP on 2017/3/10.
 * 绑定卡视图，会设置200作为绑定成功的resultcode
 */

public class BindCardActivity extends BaseActivity implements BindCardViewI {

    //view
    private View selectLibraryView;
    private TextView libraryShowView;
//    private Button submitView;
    private EditText card_noView;
    private EditText card_pwdView;

    private View waitView;

    //Persenter
    private BindCardPresenter bindCardPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_card);
        //组织者
        bindCardPresenter = new BindCardPresenter(this, this);

        //获取组件引用
        selectLibraryView = this.findViewById(R.id.select_library);
        libraryShowView = (TextView) selectLibraryView.findViewById(R.id.library_name);
        card_noView = (EditText) this.findViewById(R.id.card_no);
        card_pwdView = (EditText) this.findViewById(R.id.card_pwd);
        //等待视图
        waitView = this.findViewById(R.id.renewWaitView);
        //拦截点击事件
        waitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //设置选择图书馆
        selectLibraryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BindCardActivity.this, LibraryListActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        //设置提交监听
        View submitView = this.findViewById(R.id.submit);
        submitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BindCardEntity bce = getBindCardData();
                if (bce.getLib_idx() == null) {
                    Toast.makeText(BindCardActivity.this, getResources().getString(R.string.bind_card_library_tip1), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtils.isEmpty(bce.getCard_no())) {
                    Toast.makeText(BindCardActivity.this, getResources().getString(R.string.bind_card_card_no_tip1), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtils.isEmpty(bce.getCard_pwd())) {
                    Toast.makeText(BindCardActivity.this, getResources().getString(R.string.bind_card_card_pwd_tip1), Toast.LENGTH_SHORT).show();
                    return;
                }
                showWait();
                bindCardPresenter.bindCard(bce);
            }
        });
        //设置左上角返回监听
        View backButton = this.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            LibraryEntity d = (LibraryEntity) data.getSerializableExtra("library_info");
            selectLibraryView.setTag(d);
            libraryShowView.setText(StringUtils.getStr(d.getLib_name(), getResources().getString(R.string.empty_library_name)));
        }
    }

    private BindCardEntity getBindCardData() {
        String card_no = String.valueOf(card_noView.getText());
        String card_pwd = String.valueOf(card_pwdView.getText());
        LibraryEntity d = (LibraryEntity) selectLibraryView.getTag();
        BindCardEntity bce = new BindCardEntity();
        if (d != null) {
            bce.setLib_idx(d.getLibrary_idx());
        }
        bce.setCard_no(card_no);
        bce.setCard_pwd(card_pwd);
        return bce;
    }

    @Override
    public void bindSuccess() {
        goneWait();
        Toast.makeText(BindCardActivity.this, getResources().getString(R.string.bind_card_success), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        setResult(200, intent);
        finish();
    }

    @Override
    public void bindFail(int code) {
        goneWait();
        Resources resource = getResources();
        if (code == -1) {
            Toast.makeText(BindCardActivity.this, resource.getString(R.string.bind_card_unlogin), Toast.LENGTH_SHORT).show();
            logout();
        } else if (code == -2) {
            Toast.makeText(BindCardActivity.this, resource.getString(R.string.bind_card_network_error), Toast.LENGTH_SHORT).show();
        } else if (code == -3) {
            Toast.makeText(BindCardActivity.this, resource.getString(R.string.bind_card_card_invalid), Toast.LENGTH_SHORT).show();
        } else if (code == -4) {
            Toast.makeText(BindCardActivity.this, resource.getString(R.string.bind_card_pwd_error), Toast.LENGTH_SHORT).show();
        } else if (code == -6) {
            Toast.makeText(BindCardActivity.this, resource.getString(R.string.lib_not_support), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(BindCardActivity.this, resource.getString(R.string.bind_card_card_already_bind), Toast.LENGTH_SHORT).show();
        }
    }

    private void showWait() {
        waitView.setVisibility(View.VISIBLE);
    }

    private void goneWait() {
        waitView.setVisibility(View.GONE);
    }
}
