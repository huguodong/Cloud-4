package com.ssitcloud.app_operator.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.common.utils.StringUtils;
import com.ssitcloud.app_operator.component.ManageActivity;
import com.ssitcloud.app_operator.presenter.DeviceMaintainPresenter;
import com.ssitcloud.app_operator.view.Dialog.MyAlertDialog;
import com.ssitcloud.app_operator.view.viewInterface.DeviceMaintainViewI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建日期：2017/4/14 11:23
 * 设备维护
 * @author shuangjunjie
 */

public class DeviceMaintainActivity extends ManageActivity implements DeviceMaintainViewI{

    private final String TAG = "DeviceMaintainActivity";

    private TextView sendShutdown;          //发送关机指令
    private TextView cancelShutdown;          //取消关机指令
    private TextView sendRestart;          //发送重启指令
    private TextView cancelRestatr;          //取消重启指令
    private TextView deviceNameTextView;        //设备名
    private TextView deviceIdTextView;          //设备ID
    private Dialog alertDialog;                 //弹窗
    private TextView cancelTextView;            //弹窗 取消
    private TextView confirmTextView;           //弹窗 确认
    private DeviceMaintainPresenter deviceMaintainPresenter;
    private String libraryIdx;
    private String deviceId;
    private String deviceName;
    private List<Map> listMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        libraryIdx = bundle.getString("library_idx");
        deviceId = bundle.getString("device_id");
        deviceName = bundle.getString("device_name");

        setContentView(R.layout.device_maintain);
        deviceMaintainPresenter = new DeviceMaintainPresenter(this,this);

        deviceNameTextView = (TextView) findViewById(R.id.ssl_device_maintain_device_name);
        deviceIdTextView = (TextView) findViewById(R.id.ssl_device_maintain_device_id);
        sendShutdown = (TextView) findViewById(R.id.ssl_device_maintain_send_shutdown_order);
        cancelShutdown = (TextView) findViewById(R.id.ssl_device_maintain_cancel_shutdown_order);
        sendRestart = (TextView) findViewById(R.id.ssl_device_maintain_send_restart_order);
        cancelRestatr = (TextView) findViewById(R.id.ssl_device_maintain_cancel_restart_order);


        deviceNameTextView.setText(deviceName);
        deviceIdTextView.setText("设备名: "+deviceId);

        sendShutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送关机指令
//                cancelShutdown.setVisibility(View.VISIBLE);
                alertDialog = new AlertDialog.Builder(DeviceMaintainActivity.this).
                        create();
                final Window window = MyAlertDialog.setDialog(alertDialog,DeviceMaintainActivity.this);

                ((TextView) window.findViewById(R.id.dialog_tip)).setText("是否发送关机指令");
                cancelTextView = (TextView)window.findViewById(R.id.dialog_cancel);

                confirmTextView = (TextView)window.findViewById(R.id.dialog_confirm);

                cancelTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                confirmTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.waitView).setVisibility(View.VISIBLE);
                        cancelShutdown.setVisibility(View.VISIBLE);
//                        Toast.makeText(DeviceMaintainActivity.this, "确认发送关机指令", Toast.LENGTH_SHORT).show();
                        listMap = new ArrayList<Map>();
                        Map order = new HashMap();
                        order.put("control_info",1);
                        order.putAll(getReqMap());
                        listMap.add(order);
                        deviceMaintainPresenter.sendOrder(listMap);
                        alertDialog.dismiss();

                    }
                });
            }
        });

        sendRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送重启指令
                alertDialog = new AlertDialog.Builder(DeviceMaintainActivity.this).
                        create();
                final Window window = MyAlertDialog.setDialog(alertDialog,DeviceMaintainActivity.this);

                ((TextView) window.findViewById(R.id.dialog_tip)).setText("是否发送重启指令");
                cancelTextView = (TextView)window.findViewById(R.id.dialog_cancel);

                confirmTextView = (TextView)window.findViewById(R.id.dialog_confirm);

                cancelTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(DeviceMaintainActivity.this, "取消>>>重启", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });

                confirmTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.waitView).setVisibility(View.VISIBLE);
//                        Toast.makeText(DeviceMaintainActivity.this, "确认发送重启指令", Toast.LENGTH_SHORT).show();
                        cancelRestatr.setVisibility(View.VISIBLE);
                        listMap = new ArrayList<Map>();
                        Map order = new HashMap();
                        order.put("control_info",2);
                        order.putAll(getReqMap());
                        listMap.add(order);
                        deviceMaintainPresenter.sendOrder(listMap);
                        alertDialog.dismiss();

                    }
                });
            }
        });

        cancelShutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消关机指令

                alertDialog = new AlertDialog.Builder(DeviceMaintainActivity.this).
                        create();
                final Window window = MyAlertDialog.setDialog(alertDialog,DeviceMaintainActivity.this);

                ((TextView) window.findViewById(R.id.dialog_tip)).setText("是否取消发送关机指令");
                cancelTextView = (TextView)window.findViewById(R.id.dialog_cancel);

                confirmTextView = (TextView)window.findViewById(R.id.dialog_confirm);

                cancelTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                confirmTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.waitView).setVisibility(View.VISIBLE);
//                        Toast.makeText(DeviceMaintainActivity.this, "确认发送取消关机指令", Toast.LENGTH_SHORT).show();
                        sendShutdown.setVisibility(View.VISIBLE);
                        cancelShutdown.setVisibility(View.INVISIBLE);
                        listMap = new ArrayList<Map>();
                        Map order = new HashMap();
                        order.put("control_info",5);
                        order.putAll(getReqMap());
                        listMap.add(order);
                        deviceMaintainPresenter.sendOrder(listMap);
                        alertDialog.dismiss();

                    }
                });
            }
        });

        cancelRestatr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消重启指令

                alertDialog = new AlertDialog.Builder(DeviceMaintainActivity.this).
                        create();
                final Window window = MyAlertDialog.setDialog(alertDialog,DeviceMaintainActivity.this);

                ((TextView) window.findViewById(R.id.dialog_tip)).setText("是否取消发送重启指令");
                cancelTextView = (TextView)window.findViewById(R.id.dialog_cancel);

                confirmTextView = (TextView)window.findViewById(R.id.dialog_confirm);

                cancelTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                confirmTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findViewById(R.id.waitView).setVisibility(View.VISIBLE);
                        sendRestart.setVisibility(View.VISIBLE);
                        cancelRestatr.setVisibility(View.INVISIBLE);
                        listMap = new ArrayList<Map>();
                        Map order = new HashMap();
                        order.put("control_info",5);
                        order.putAll(getReqMap());
                        listMap.add(order);
                        deviceMaintainPresenter.sendOrder(listMap);
                        alertDialog.dismiss();

                    }
                });
            }
        });

        ImageView returnV = (ImageView) findViewById(R.id.ssl_device_maintain_back);
        returnV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        View waitView = findViewById(R.id.waitView);
        waitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public void sendOrderSuccess(String message) {
        findViewById(R.id.waitView).setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendOrderFail(String message) {
        findViewById(R.id.waitView).setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected Map getReqMap(){
        Map param = new HashMap();
        if (!StringUtils.isEmpty(deviceId)){
            param.put("device_id",deviceId);
        }
        if (!StringUtils.isEmpty(libraryIdx)){
            param.put("library_idx",libraryIdx);
        }
        param.put("control",1);

        return param;
    }
}
