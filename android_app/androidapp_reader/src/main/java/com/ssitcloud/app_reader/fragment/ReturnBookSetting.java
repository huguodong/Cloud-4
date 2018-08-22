package com.ssitcloud.app_reader.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ssitcloud.app_reader.R;

/**
 * Created by LXP on 2017/4/15.
 * 还书提醒设置
 */

public class ReturnBookSetting extends DialogFragment {
    private int remindTime;
    private TextView tv;
    private View lost;
    private View add;
    private final int MAX_DAY = 7;

    private OkListener okListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments == null){
            Log.e("ReturnBookSetting","must set args remindTime==>(int)");
            dismiss();
            return ;
        }
        remindTime = arguments.getInt("remindTime",-1);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.sub_return_book_setting,null);
        tv = (TextView) v.findViewById(R.id.time);
        tv.setText(getShowStr(remindTime));

        lost = v.findViewById(R.id.lost);
        add = v.findViewById(R.id.add);

        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remindTime == -1){
                    remindTime = MAX_DAY +1;
                }
                if(remindTime > 1) {
                    --remindTime;
                    tv.setText(getShowStr(remindTime));
                }
                setView(remindTime);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remindTime == -1){
                    return ;
                }
                if(remindTime < MAX_DAY) {
                    ++remindTime;
                    tv.setText(getShowStr(remindTime));
                }else{
                    remindTime = -1;
                    tv.setText("不提醒");
                }
                setView(remindTime);
            }
        });

        ad.setView(v).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(okListener != null){
                    okListener.onListener(remindTime);
                }
            }
        }).setNegativeButton("取消",null);

        setView(remindTime);

        return ad.create();
    }

    private String getShowStr(int day){
        if(day == -1){
            return "不提醒";
        }else {
            return "最后" + day + "天";
        }
    }

    private void setView(int day){
        if(day == -1){
            add.setVisibility(View.INVISIBLE);
            lost.setVisibility(View.VISIBLE);
        }else if(day == 1){
            add.setVisibility(View.VISIBLE);
            lost.setVisibility(View.INVISIBLE);
        }else{
            add.setVisibility(View.VISIBLE);
            lost.setVisibility(View.VISIBLE);
        }
    }

    public void setOkListener(OkListener okListener) {
        this.okListener = okListener;
    }

    public interface OkListener{
        void onListener(int remindTime);
    }
}
