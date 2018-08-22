package com.ssitcloud.app_operator.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.LibraryBiz;
import com.ssitcloud.app_operator.biz.LoginBizI;
import com.ssitcloud.app_operator.biz.MessageRemindBizI;
import com.ssitcloud.app_operator.biz.PermissionBiz;
import com.ssitcloud.app_operator.biz.impl.LibraryBizImpl;
import com.ssitcloud.app_operator.biz.impl.LoginBizImpl;
import com.ssitcloud.app_operator.biz.impl.MessageRemindBizImpl;
import com.ssitcloud.app_operator.biz.impl.PermissionBizImpl;
import com.ssitcloud.app_operator.common.utils.DisplayUtil;
import com.ssitcloud.app_operator.component.ManageActivity;
import com.ssitcloud.app_operator.component.MyApplication;
import com.ssitcloud.app_operator.databinding.ActivityIndexBinding;
import com.ssitcloud.app_operator.db.entity.MessageRemindDbEntity;
import com.ssitcloud.app_operator.entity.AppSettingEntity;
import com.ssitcloud.app_operator.entity.LibraryEntity;
import com.ssitcloud.app_operator.service.PermissionUpdateService;
import com.ssitcloud.app_operator.view.viewInterface.IndexViewI;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 创建日期：2017/3/28 10:34
 *
 * @author shuangjunjie
 */

public class IndexActivity extends ManageActivity implements IndexViewI {
    private final String TAG = "IndexActivity";
    private TextView libNameView;

    private RelativeLayout messageRemindView;   //消息提醒view
    private FrameLayout newMsgView;             //显示新消息view
    private TextView newMsgNumView;             //显示新消息条数view
    private List<LibraryEntity> libs;               //图书馆list
    private DeviceTroubleTask task;
    private Timer timer;
    private int newMsgNum = 0;          //新消息条数
    private long exitTime = 0;

    private View operInfoView;
    private MyApplication app;

    private ActivityIndexBinding dataBind;
    private LoginBizI loginBiz;
    private PermissionBiz permissionBiz;
    private MessageRemindBizI messageRemindBiz;
    private LibraryBiz libraryBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBind = DataBindingUtil.setContentView(this, R.layout.activity_index);

        messageRemindBiz = new MessageRemindBizImpl(this);
        permissionBiz = PermissionBizImpl.getInstance(this);
        libraryBiz = new LibraryBizImpl(this);
        loginBiz = new LoginBizImpl(this.getResources(), this);
        app = (MyApplication) getApplication();

        newMsgView = (FrameLayout) findViewById(R.id.index_new_msg);
        newMsgNumView = (TextView) findViewById(R.id.index_new_msg_num);


        libNameView = (TextView) findViewById(R.id.libname);
        libNameView.setText(app.getLib_name());

        operInfoView = findViewById(R.id.oper_info);
        messageRemindView = (RelativeLayout) findViewById(R.id.message_remind_img);

        operInfoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, UpdateOperatorInfoActivity.class);
                startActivity(intent);
            }
        });

        messageRemindView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, MessageRemindActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.waitView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        timer = new Timer();

        //更新权限服务
        Intent updatePermission = new Intent(this, PermissionUpdateService.class);
        startService(updatePermission);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //填充功能视图
        if (app.getLibrary_idx() != null) {
            List<AppSettingEntity> menus = loginBiz.getAppSettingByDb(app.getLibrary_idx());
            inflaterMenu(menus);
        }
        task = new DeviceTroubleTask();
        timer.schedule(task, 5000, 60_000);

        //计算未读消息数
        newMsgNum = messageRemindBiz.getMessageRemindCount()[0];
        if(newMsgNum>0){
            newMsgNumView.setText(String.valueOf(newMsgNum));
            newMsgView.setVisibility(View.VISIBLE);
        }else{
            newMsgView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        task.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void querySlaveLibraryByMasterIdxSuccess(List<LibraryEntity> libs) {
        toDeviceMonitor(libs);
    }

    @Override
    public void querySlaveLibraryByMasterIdxFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public void toDeviceMonitor(List<LibraryEntity> libs) {
        Intent intent = new Intent(IndexActivity.this, DeviceMonitorActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("libs",new ArrayList<>(libs));
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void selectDeviceMgmtByLibraryIdxsFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 加载主视图菜单
     *
     * @param menus 菜单
     */
    private void inflaterMenu(List<AppSettingEntity> menus) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int height = DisplayUtil.getScreenWidth(this) / 3;
        int basePX = DisplayUtil.dp2px(this, 10);
        List<ViewGroup> vgs = new ArrayList<>(8);
        for (AppSettingEntity menu : menus) {
            View v = createChildView(menu.getService_id());
            if (vgs.isEmpty()) {
                LinearLayout view = new LinearLayout(this);
                view.setOrientation(LinearLayout.HORIZONTAL);
                view.setLayoutParams(lp);

                LinearLayout.LayoutParams childLp = new LinearLayout.LayoutParams(0, height);
                childLp.weight = 1;
                childLp.setMargins(basePX, basePX, basePX / 2, 0);

                view.addView(v, childLp);
                vgs.add(view);
            } else {
                ViewGroup vg = vgs.get(vgs.size() - 1);
                if (vg.getChildCount() == 1) {
                    LinearLayout.LayoutParams childLp = new LinearLayout.LayoutParams(0, height);
                    childLp.weight = 1;
                    childLp.setMargins(basePX / 2, basePX, basePX, 0);

                    vg.addView(v, childLp);
                } else {
                    LinearLayout view = new LinearLayout(this);
                    view.setOrientation(LinearLayout.HORIZONTAL);
                    view.setLayoutParams(lp);

                    LinearLayout.LayoutParams childLp = new LinearLayout.LayoutParams(0, height);
                    childLp.weight = 1;
                    childLp.setMargins(basePX, basePX, basePX / 2, 0);

                    view.addView(v, childLp);
                    vgs.add(view);
                }
            }

        }
        if (!vgs.isEmpty()) {
            ViewGroup vg = vgs.get(vgs.size() - 1);
            if (vg.getChildCount() == 1) {
                View v = createChildView("right");
                LinearLayout.LayoutParams childLp = new LinearLayout.LayoutParams(0, height);
                childLp.weight = 1;
                childLp.setMargins(basePX / 2, basePX, basePX, 0);

                vg.addView(v, childLp);
            }
        }

        dataBind.menus.removeAllViews();
        for (ViewGroup vg : vgs) {
            dataBind.menus.addView(vg);
        }
    }

    private View createChildView(String serviceIdx) {
        int resource;
        View.OnClickListener listener = null;
        if ("020301".equals(serviceIdx)) {
            listener = deviceMonitorListener;
            resource = R.layout.activity_index_device_monitor;
        } else if ("020303".equals(serviceIdx)) {
            listener = feedbackListener;
            resource = R.layout.activity_index_feedback;
        } else if ("020304".equals(serviceIdx)) {
            listener = environmentMonitorListener;
            resource = R.layout.activity_index_environment_monitor;
        } else {
            resource = R.layout.activity_index_empty_layout;
        }

        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout view = (LinearLayout) inflater.inflate(resource, null);
        view.setOnClickListener(listener);
        return view;
    }

    private View.OnClickListener deviceMonitorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setVisibility(R.id.waitView,View.VISIBLE);
            if (permissionBiz.checkPermission("010301")) {
                libraryBiz.getSlaveLibrary(app.getLibrary_idx())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<LibraryEntity>>() {
                    @Override
                    public void accept(@NonNull List<LibraryEntity> libraryEntities) throws Exception {
                        setVisibility(R.id.waitView,View.GONE);
                        querySlaveLibraryByMasterIdxSuccess(libraryEntities);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setVisibility(R.id.waitView,View.GONE);
                        Toast.makeText(IndexActivity.this, "获取数据失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(IndexActivity.this, "您没有使用此功能的权限", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener environmentMonitorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (permissionBiz.checkPermission("010302")) {
                Toast.makeText(IndexActivity.this, "该功能正在开发中...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(IndexActivity.this, "您没有使用此功能的权限", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener feedbackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(IndexActivity.this, FeedBackActivity.class);
            startActivity(intent);
        }
    };

    private class DeviceTroubleTask extends TimerTask {
        private AtomicBoolean isTask = new AtomicBoolean(false);

        @Override
        public void run() {
            if (isTask.compareAndSet(false,true)) {
                messageRemindBiz.selectUnReadDeviceTroublesByLibIdxs()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<MessageRemindDbEntity>>() {
                            @Override
                            public void accept(@NonNull List<MessageRemindDbEntity> o) throws Exception {
                                selectDeviceTroublesByLibIdxsSuccess(o);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                isTask.set(false);
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                isTask.set(false);
                            }
                        });
            }
        }
    }

    public void selectDeviceTroublesByLibIdxsSuccess(List<MessageRemindDbEntity> messages) {
        if (!messages.isEmpty()) {
            newMsgView.setVisibility(View.VISIBLE);
            newMsgNum += messages.size();
            newMsgNumView.setText(String.valueOf(newMsgNum));
        }
    }

    /**
     * 判断是否是未读消息已经由业务层处理
     */
    @Deprecated
    public List<MessageRemindDbEntity> getNewMsgList(List<MessageRemindDbEntity> list1, List<MessageRemindDbEntity> list2) {
        List<MessageRemindDbEntity> newListMap = new ArrayList<>();

        List<Integer> troubleIdxs = new ArrayList<>();
        List<Integer> logIdxs = new ArrayList<>();
        for (MessageRemindDbEntity messageRemindDbEntity : list1) {
            Integer troubleIdx = messageRemindDbEntity.getTrouble_idx();
            if(troubleIdx != null){
                troubleIdxs.add(troubleIdx);
            }
            Integer logIdx = messageRemindDbEntity.getLog_idx();
            if(logIdx != null){
                logIdxs.add(logIdx);
            }
        }
        for (MessageRemindDbEntity messageRemindDbEntity : list2) {
            Integer troubleIdx = messageRemindDbEntity.getTrouble_idx();
            if(troubleIdx != null && !troubleIdxs.contains(troubleIdx)){
                newListMap.add(messageRemindDbEntity);
            }
            Integer logIdx = messageRemindDbEntity.getLog_idx();
            if (logIdx != null && !logIdxs.contains(logIdx)) {
                newListMap.add(messageRemindDbEntity);
            }
        }

        return newListMap;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setVisibility(int id,int visibility){
        View viewById = findViewById(id);
        if(viewById != null){
            viewById.setVisibility(visibility);
        }
    }
}
