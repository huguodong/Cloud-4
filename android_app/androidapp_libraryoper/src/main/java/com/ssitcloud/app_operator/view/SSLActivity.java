package com.ssitcloud.app_operator.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.DeviceMonitorBizI;
import com.ssitcloud.app_operator.biz.impl.DeviceMonitorBizImpl;
import com.ssitcloud.app_operator.common.utils.JsonUtils;
import com.ssitcloud.app_operator.common.utils.StringUtils;
import com.ssitcloud.app_operator.component.ManageActivity;
import com.ssitcloud.app_operator.databinding.ActivitySslBinding;
import com.ssitcloud.app_operator.databinding.BookcaseBinding;
import com.ssitcloud.app_operator.databinding.CardBoxBinding;
import com.ssitcloud.app_operator.databinding.MoneySizeAmountBinding;
import com.ssitcloud.app_operator.databinding.ShelfbookBinding;
import com.ssitcloud.app_operator.entity.ShiftEntity;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 创建日期：2017/4/7 11:18
 * 24小时自助图书馆    SSL
 *
 * @author shuangjunjie
 */

public class SSLActivity extends ManageActivity {
    private final String TAG = "SSLActivity";

    private ActivitySslBinding databind;

    private String deviceType;
    private String libraryIdx;
    private String deviceIdx;
    private String deviceId;
    private String deviceName;
    private DeviceMonitorBizI deviceMonitorBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databind = DataBindingUtil.setContentView(this, R.layout.activity_ssl);

        deviceMonitorBiz = new DeviceMonitorBizImpl(getResources(), this);
        Intent intent = getIntent();
        deviceType = intent.getStringExtra("device_type");
        deviceName = intent.getStringExtra("deviceName");
        libraryIdx = intent.getStringExtra("library_idx");
        deviceIdx = intent.getStringExtra("device_idx");
        deviceId = intent.getStringExtra("device_id");
        if (StringUtils.isEmpty(deviceId, deviceIdx, libraryIdx, deviceName, deviceType)) {
            Toast.makeText(this, "刷新后重试", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        databind.deviceName.setText(deviceName);
        databind.deviceMaintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SSLActivity.this, DeviceMaintainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("library_idx", libraryIdx);
                bundle.putString("device_id", deviceId);
                bundle.putString("device_name", deviceName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        databind.plcState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = v.getTag();
                if (tag != null) {
                    Intent intent = new Intent(SSLActivity.this, PlcStateActivity.class);
                    intent.putExtra("plcStateData", JsonUtils.toJson(tag));
                    startActivity(intent);
                }
//                else {
//                    Toast.makeText(SSLActivity.this, "暂无详细数据", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        findViewById(R.id.waitView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        deviceMonitorBiz.selectDeviceState(libraryIdx, deviceIdx)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Map<String, Object>>() {
                               @Override
                               public void accept(@NonNull Map<String, Object> stringObjectMap) throws Exception {
                                   setVisibility(R.id.waitView, View.INVISIBLE);
                                   showView(stringObjectMap);
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                setVisibility(R.id.waitView, View.INVISIBLE);
                                if (throwable instanceof SocketException) {
                                    setVisibility(R.id.netWorkLost, View.VISIBLE);
                                } else {
                                    setVisibility(R.id.messageView, View.VISIBLE);
                                }
                            }
                        });
    }

    private void setVisibility(int id, int visibility) {
        View viewById = findViewById(id);
        if (viewById != null) {
            viewById.setVisibility(visibility);
        }
    }

    private void showView(Map<String, Object> data) {
        LayoutInflater inflater = LayoutInflater.from(this);
        //bin_state
        Object binStateData = data.get("bin_state");
        if (binStateData instanceof Map) {
            showBinstate(inflater, (Map<String, Object>) binStateData);
        }
        //soft_state
        Object softStateData = data.get("soft_state");
        if (softStateData instanceof Map) {
            showSoftstate(inflater, (Map<String, Object>) softStateData);
        }
        //ext_state
        Object extStateData = data.get("ext_state");
        if (extStateData instanceof Map) {
            showExtState(inflater, (Map<String, Object>) extStateData);
        }
        //检查是否有数据
        if (databind.binState.getChildCount() == 0
                && databind.softState.getChildCount() == 0
                && databind.extState.getChildCount() == 0
                && databind.plcState.getVisibility() == View.GONE) {
            setVisibility(R.id.messageView, View.VISIBLE);
        }
    }

    private Set<Map.Entry<String, Object>> sortSet(Set<Map.Entry<String, Object>> set) {
        Set<Map.Entry<String, Object>> treeSet = new TreeSet<>(new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(Map.Entry<String, Object> t1, Map.Entry<String, Object> t2) {
                if (t1.getKey() == null && t2.getKey() != null) {
                    return -1;
                }
                if (t1.getKey() != null && t2.getKey() == null) {
                    return 1;
                }
                return t1.getKey().compareTo(t2.getKey());
            }
        });

        for (Map.Entry<String, Object> stringObjectEntry : set) {
            treeSet.add(stringObjectEntry);
        }

        return treeSet;
    }

    private void showExtState(LayoutInflater inflater, Map<String, Object> softStateData) {
        Object plcSSL = softStateData.remove("PlcSSL");
        if (plcSSL instanceof Map) {
            Map<String, Object> m = ((Map<String, Object>) plcSSL);
            Object s = m.remove("state");
            if (!m.isEmpty()) {
                databind.plcImage.setVisibility(View.GONE);
                databind.plcRight.setVisibility(View.VISIBLE);
                databind.plcState.setTag(plcSSL);
            }else{
                databind.plcImage.setVisibility(View.VISIBLE);
                databind.plcRight.setVisibility(View.GONE);
            }
            DeviceState ds = getDeviceState(s);
            databind.plcImage.setImageDrawable(ds.getDrawable());
            databind.plcStateDesc.setText(ds.getStateDesc());
            databind.plcStateDesc.setTextColor(ds.getColor());
            databind.plcState.setVisibility(View.VISIBLE);
        }
        Set<Map.Entry<String, Object>> entries = sortSet(softStateData.entrySet());
        int index = 0, size = entries.size();
        for (Map.Entry<String, Object> entry : entries) {
            Map<String, Object> value = (Map<String, Object>) entry.getValue();
            if (value != null) {
                ViewGroup.LayoutParams params
                        = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                DeviceState ds = getDeviceState(value.get("state"));
                View item = inflaterItem(inflater, StringUtils.getDesc(value.get("desc"))
                        , ds.getStateDesc(), ds.getDrawable(), ds.getColor());
                if (index < size - 1) {
                    View bottom = item.findViewById(R.id.bottom);
                    if (bottom != null) {
                        bottom.setVisibility(View.VISIBLE);
                    }
                }
                databind.extState.addView(item, params);
            }
        }
    }

    private void showSoftstate(LayoutInflater inflater, Map<String, Object> softStateData) {
        Object t = softStateData.get("function_state");
        if (t instanceof Map) {
            Map<String, Object> functionState = (Map<String, Object>) t;
            Set<Map.Entry<String, Object>> entries = sortSet(functionState.entrySet());
            int index = 0, size = entries.size();
            for (Map.Entry<String, Object> entry : entries) {
                Map<String, Object> value = (Map<String, Object>) entry.getValue();
                if (value != null) {
                    ViewGroup.LayoutParams params
                            = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    DeviceState ds = getDeviceState(value.get("state"));
                    View item = inflaterItem(inflater, StringUtils.getDesc(value.get("desc"))
                            , ds.getStateDesc(), ds.getDrawable(), ds.getColor());
                    if (index < size - 1) {
                        View bottom = item.findViewById(R.id.bottom);
                        if (bottom != null) {
                            bottom.setVisibility(View.VISIBLE);
                        }
                    }
                    databind.softState.addView(item, params);
                    index++;
                }
            }
        }
    }

    private View inflaterItem(LayoutInflater inflater, String title, String state, Drawable image, Integer color) {
        View item = inflater.inflate(R.layout.activity_items, null);
        ((TextView) item.findViewById(R.id.items_textview)).setText(title);
        ((TextView) item.findViewById(R.id.items_state_desc)).setText(state);
        if (color != null) {
            ((TextView) item.findViewById(R.id.items_state_desc)).setTextColor(color);
        }
        if (image != null) {
            ((ImageView) item.findViewById(R.id.items_image)).setImageDrawable(image);
        }
        return item;
    }

    private void showBinstate(LayoutInflater inflater, Map<String, Object> binStateData) {
        List<View> spView = new ArrayList<>(5);
        //钱箱数据
        Object cashs = binStateData.get("cashs");
        if (cashs instanceof List) {
            View cashView = inflaterCashView(inflater, (List<Map<String, Object>>) cashs);
            spView.add(cashView);
        }
        //书架数据
        Object bookRack = binStateData.get("bookracks");
        if (bookRack != null) {
            View bookRackView = inflaterBookRack(inflater, (Map<String, Object>) bookRack);
            spView.add(bookRackView);
        }
        //卡箱数据
        Object card = binStateData.get("cards");
        if (card != null) {
            View cardBoxView = inflaterCardBox(inflater, (Map<String, Object>) card);
            spView.add(cardBoxView);
        }
        //填充特殊视图
        LinearLayout spLL = getTwoViewGroup(this);
        for (int i = 0; i < spView.size(); i++) {
            ViewGroup.LayoutParams params
                    = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i % 2 == 0) {
                spLL = getTwoViewGroup(this);
                ((ViewGroup) spLL.getChildAt(0)).addView(spView.get(i), params);
                databind.binState.addView(spLL);
            } else {
                ((ViewGroup) spLL.getChildAt(1)).addView(spView.get(i), params);
            }
        }

        //书箱数据
        Object bookBoxs = binStateData.get("bookBoxs");
        if (bookBoxs instanceof List) {
            List<View> bookBoxViews = new ArrayList<>();
            for (Map<String, Object> bookBoxData : ((List<Map<String, Object>>) bookBoxs)) {
                bookBoxViews.add(inflaterBookBox(inflater, bookBoxData));
            }
            if (!bookBoxViews.isEmpty()) {
                List<View> temp = new ArrayList<>(bookBoxViews.size() / 2 + 1);
                LinearLayout ll = null;
                for (int i = 0; i < bookBoxViews.size(); i++) {
                    ViewGroup.LayoutParams params
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                            , ViewGroup.LayoutParams.WRAP_CONTENT);
                    if (i % 2 == 0) {
                        ll = getTwoViewGroup(this);
                        ((ViewGroup) ll.getChildAt(0)).addView(bookBoxViews.get(i), params);
                        temp.add(ll);
                    } else {
                        ((ViewGroup) ll.getChildAt(1)).addView(bookBoxViews.get(i), params);
                    }
                }
                for (View view : temp) {
                    ViewGroup.LayoutParams params
                            = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                            , ViewGroup.LayoutParams.WRAP_CONTENT);
                    databind.binState.addView(view, params);
                }
            }
        }
    }

    private View inflaterBookBox(LayoutInflater inflater, Map<String, Object> bookBoxData) {
        BookcaseBinding bcb = DataBindingUtil.inflate(inflater, R.layout.bookcase, null, false);
        bcb.setBinno((String) bookBoxData.get("binno"));
        bcb.setAmount((String) bookBoxData.get("binno"));
        bcb.setDesc(StringUtils.getDesc(bookBoxData.get("desc")));
        return bcb.getRoot();
    }

    private View inflaterCashView(LayoutInflater inflater, List<Map<String, Object>> cashDatas) {
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.cash_box, null);
        ViewGroup cash_box_parent = (ViewGroup) vg.findViewById(R.id.cash_box_parent);
        for (Map<String, Object> cashData : cashDatas) {
            MoneySizeAmountBinding msab = DataBindingUtil.inflate(inflater, R.layout.money_size_amount, null, false);
            msab.setSize((String) cashData.get("subtype"));
            msab.setAmount((String) cashData.get("amount"));
            msab.setState("0".equals(cashData.get("state")));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            msab.getRoot().setLayoutParams(params);
            cash_box_parent.addView(msab.getRoot());
        }

        return vg;
    }

    private View inflaterBookRack(LayoutInflater inflater, Map<String, Object> bookrackData) {
        ShelfbookBinding sbb = DataBindingUtil.inflate(inflater, R.layout.shelfbook, null, false);
        sbb.setLevel1(getShiftEntity(bookrackData.get("level1")));
        sbb.setLevel2(getShiftEntity(bookrackData.get("level2")));
        sbb.setLevel3(getShiftEntity(bookrackData.get("level3")));

        sbb.setEbox(getShiftEntity(bookrackData.get("exbox")));
        sbb.setPrecheckout((String) bookrackData.get("precheckout"));
        sbb.setStandard(getShiftEntity(bookrackData.get("standardized")));
        sbb.setOverSize(getShiftEntity(bookrackData.get("oversized")));
        sbb.setAbnormal((String) bookrackData.get("abnormal"));

        return sbb.getRoot();
    }

    private View inflaterCardBox(LayoutInflater inflater, Map<String, Object> cardBoxData) {
        CardBoxBinding cbb = DataBindingUtil.inflate(inflater, R.layout.card_box, null, false);
        cbb.setAmount((String) cardBoxData.get("amount"));
        return cbb.getRoot();
    }

    private ShiftEntity getShiftEntity(Object o) {
        if (o instanceof String) {
            String[] s = ((String) o).split("/");
            if (s.length == 2) {
                return new ShiftEntity(s[1], s[0]);
            }
        }
        return null;
    }

    private LinearLayout getTwoViewGroup(Context context) {
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams childParam = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        childParam.weight = 1;

        LinearLayout left = new LinearLayout(context);
        left.setLayoutParams(childParam);

        LinearLayout right = new LinearLayout(context);
        right.setLayoutParams(childParam);

        ll.addView(left);
        ll.addView(right);

        return ll;
    }

    private DeviceState getDeviceState(Object state){
        DeviceState ds = new DeviceState();
        if(state == null){
            ds.setStateDesc("异常");
            ds.setDrawable(ContextCompat.getDrawable(this,R.drawable.disable));
            ds.setColor(0xFFC7C7C7);
        }else{
            if("0".equals(state)){
                ds.setStateDesc("正常");
                ds.setDrawable(ContextCompat.getDrawable(this,R.drawable.state_normal));
                ds.setColor(0xFF55C76A);
            }else{
                ds.setStateDesc("告警");
                ds.setColor(0xFFF85B5B);
                ds.setDrawable(ContextCompat.getDrawable(this,R.drawable.alarm));
            }
        }
        return ds;
    }

    private class DeviceState{
        private String stateDesc;
        private int color = 0xFFC7C7C7;
        private Drawable drawable;

        String getStateDesc() {
            return stateDesc;
        }

        void setStateDesc(String stateDesc) {
            this.stateDesc = stateDesc;
        }

        Drawable getDrawable() {
            return drawable;
        }

        void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }

        int getColor() {
            return color;
        }

        void setColor(int color) {
            this.color = color;
        }
    }

    public void finish(View view) {
        finish();
    }
}
