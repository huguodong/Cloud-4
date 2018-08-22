package com.ssitcloud.app_reader.myview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by LXP on 2017/3/21.
 *
 */

public class MarqueeTextView extends android.support.v7.widget.AppCompatTextView {

    public MarqueeTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect)
    {
        if(focused) super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus)
    {
        if(hasWindowFocus) super.onWindowFocusChanged(hasWindowFocus);
    }
}
