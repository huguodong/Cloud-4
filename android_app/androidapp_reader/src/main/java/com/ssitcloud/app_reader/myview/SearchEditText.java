package com.ssitcloud.app_reader.myview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by LXP on 2017/4/7.
 * 搜索EditText
 */

public class SearchEditText extends android.support.v7.widget.AppCompatEditText {
    private View.OnClickListener iconClickListener;

    private boolean haveIcon = false;

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Drawable searchIcon = this.getCompoundDrawables()[2];
        if(searchIcon != null){
            haveIcon = true;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP && haveIcon){
            boolean touchable = (event.getX()) > (getWidth() - getTotalPaddingRight())
                    && ((event.getX()) < (getWidth() - getPaddingRight()));
            if (touchable) {
                if(iconClickListener != null) {
                    iconClickListener.onClick(this);
                    clearFocus();
                    InputMethodManager im = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(this.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void setIconClickListener(OnClickListener listener){
        this.iconClickListener = listener;
    }
}
