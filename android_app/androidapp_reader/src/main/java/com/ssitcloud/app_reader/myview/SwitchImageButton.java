package com.ssitcloud.app_reader.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.ssitcloud.app_reader.R;

/**
 * Created by LXP on 2017/4/17.
 * 图片按钮开关
 */

public class SwitchImageButton extends android.support.v7.widget.AppCompatImageView {
    private Drawable openBitmap;
    private Drawable closeBitmap;
    private boolean isChecked = false;

    public SwitchImageButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SwitchImageButton);
        int size = ta.getIndexCount();
        for (int i = 0; i < size; ++i) {
            int arr = ta.getIndex(i);
            switch (arr) {
                case R.styleable.SwitchImageButton_open_img:
                    openBitmap = ta.getDrawable(arr);
                    break;
                case R.styleable.SwitchImageButton_close_img:
                    closeBitmap = ta.getDrawable(arr);
                    break;
                case R.styleable.SwitchImageButton_android_checked:
                    isChecked = ta.getBoolean(arr, false);
                    break;
            }
        }
        swapBitmap();
        ta.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isChecked = !isChecked;
            swapBitmap();
        }
        return super.onTouchEvent(event);
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
        swapBitmap();
    }

    public boolean isChecked() {
        return isChecked;
    }

    private void swapBitmap() {
        if (isChecked) {
            setImageDrawable(openBitmap);
        } else {
            setImageDrawable(closeBitmap);
        }
    }
}
