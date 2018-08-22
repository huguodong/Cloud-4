package com.ssitcloud.app_operator.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.component.ManageActivity;
import com.ssitcloud.app_operator.component.MyApplication;
import com.ssitcloud.app_operator.common.utils.StringUtils;
import com.ssitcloud.app_operator.presenter.FeedBackPresenter;
import com.ssitcloud.app_operator.view.spinner.SpinnerPopWindow;
import com.ssitcloud.app_operator.view.viewInterface.FeedBackViewI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建日期：2017/3/29 19:37
 * @author shuangjunjie
 */

public class FeedBackActivity extends ManageActivity implements FeedBackViewI{
    private final String TAG = "FeedBackActivity";

    private ImageView returnView;
    private Button submit;

    private SpinnerPopWindow<String> mSpinerPopWindow;
    private List<Map<String,Object>> list;
    private TextView tvValue;
//    private TextView problemTitleView;
    private TextView problemContentView;
    private FeedBackPresenter feedBackPresenter;

    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        app = (MyApplication) getApplication();

        feedBackPresenter = new FeedBackPresenter(this,this);
        returnView = (ImageView) findViewById(R.id.feedback_return);
        submit = (Button) findViewById(R.id.feedback_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map = getReqMap();
                if (map != null){
                    feedBackPresenter.sendFeedBack(map);
                }
            }
        });

//        problemTitleView = (TextView) findViewById(R.id.feedback_title);
        problemContentView = (TextView) findViewById(R.id.feedback_content);

        initData();
        tvValue = (TextView) findViewById(R.id.feedback_select);
        tvValue.setOnClickListener(clickListener);
        mSpinerPopWindow = new SpinnerPopWindow<>(this, list,itemClickListener);
        mSpinerPopWindow.setOnDismissListener(dismissListener);


        returnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener=new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
//            setTextImage(R.drawable.down);
        }
    };

    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            mSpinerPopWindow.dismiss();
            tvValue.setText(list.get(position).get("feedBack").toString());
//            Toast.makeText(FeedBackActivity.this, "点击了:" + list.get(position),Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 显示PopupWindow
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.feedback_select:
                    mSpinerPopWindow.setWidth(tvValue.getWidth());
                    mSpinerPopWindow.showAsDropDown(tvValue);
//                    setTextImage(R.drawable.icon_up);
                    break;
            }
        }
    };

    /**
     * 给TextView右边设置图片
     * @param resId
     */
    private void setTextImage(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        tvValue.setCompoundDrawables(null, null, drawable, null);
    }


    /**
     * 初始化数据
     */
    private void initData() {
        list = new ArrayList<>();
        Map<String,Object> map = new ArrayMap<>();
        Map<String,Object> map2 = new ArrayMap<>();
        Map<String,Object> map3 = new ArrayMap<>();
        Map<String,Object> map4 = new ArrayMap<>();
        map.put("feedBack","请选择问题类型");
        map2.put("feedBack","手机APP");
        map3.put("feedBack","云平台");
        map4.put("feedBack","硬件");
        list.add(map);
        list.add(map2);
        list.add(map3);
        list.add(map4);
    }

    private Map<String,Object> getReqMap(){
        Map<String,Object> map = new HashMap<>();
        String problemType = tvValue.getText().toString();
//        String problemTitle = problemTitleView.getText().toString();
        String problemContent = problemContentView.getText().toString();

        if ("请选择问题类型".equals(problemType) || StringUtils.isEmpty(problemType)){
            Toast.makeText(this, "请选择问题类型", Toast.LENGTH_SHORT).show();
            return null;
        }else if ("手机APP".equals(problemType)){
            map.put("feedback_type",1);
        }else if ("云平台".equals(problemType)){
            map.put("feedback_type",2);
        }else if ("硬件".equals(problemType)){
            map.put("feedback_type",3);
        }
//        if (StringUtils.isEmpty(problemTitle)){
//            Toast.makeText(this, "问题标题不能为空", Toast.LENGTH_SHORT).show();
//            return null;
//        }
        if (StringUtils.isEmpty(problemContent)){
            Toast.makeText(this, "问题描述不能为空", Toast.LENGTH_SHORT).show();
            return null;
        }
        map.put("reader_idx",app.getOperator_idx());
        map.put("user_type",1);
        map.put("feedback_value",problemContent);


        return map;
    }


    @Override
    public void sendFeedBackSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void sendFeedBackFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
