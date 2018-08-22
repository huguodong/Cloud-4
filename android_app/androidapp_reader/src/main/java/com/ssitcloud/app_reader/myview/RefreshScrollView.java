package com.ssitcloud.app_reader.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.utils.DisplayUtil;

/**
 * Created by LXP on 2017/3/30.
 * 下拉刷新视图
 */

public class RefreshScrollView extends ScrollView {
    private ViewGroup vg;//ScrollView子根视图

    private View refreshView;//刷新视图 下拉刷新
    private View refreshViewTipView;//刷新提示视图 放开可以刷新
    private View onRefreshView;//刷新中视图 刷新中
    private View refreshSuccessView;//刷新成功视图
    private View refreshSuccessNoDataView;//刷新成功，但是没有数据视图

    private int screenHeigth;
    private int refreshViewHeight;//刷新视图高
    private double refreshViewScale = 0.7;//刷新视图显示多少执行刷新
    private boolean isRefreshing = false;//是否正在刷新
    boolean isActionMove = false;//move事件中

    private ViewGroup content;//内容视图

    private RefreshListener refreshListener;

    private Handler handler = new Handler();
    private boolean isOnce = true;

    public RefreshScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        screenHeigth = DisplayUtil.getScreenHeight(context);
        refreshViewHeight = (int) (screenHeigth * 0.2f);
        this.setHorizontalScrollBarEnabled(false);//取消滚动条
//        this.setVerticalScrollBarEnabled(false);//取消滚动条
        this.setOverScrollMode(OVER_SCROLL_NEVER);//取消阴影
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RefreshScrollView);
        int size = ta.getIndexCount();
        for (int i = 0; i < size; ++i) {
            int arr = ta.getIndex(i);
            switch (arr) {
                case R.styleable.RefreshScrollView_refresh_height:
                    refreshViewHeight = (int) ta.getDimension(arr, screenHeigth * 0.2f);
                    break;
            }
        }
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isOnce) {
            vg = (ViewGroup) this.getChildAt(0);//子视图

            refreshView = vg.getChildAt(0);
            content = (ViewGroup) vg.getChildAt(1);

            refreshView.getLayoutParams().height = refreshViewHeight;
            content.setPadding(0, 0, 0, screenHeigth + refreshViewHeight);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(0, refreshViewHeight);
            isOnce = false;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int n = (int) (refreshViewHeight * (1 - refreshViewScale));
        if(isActionMove) {
            if (t < n) {
                if (refreshViewTipView != null) {
                    ViewGroup.LayoutParams params = vg.getChildAt(0).getLayoutParams();
                    vg.removeViewAt(0);
                    vg.addView(refreshViewTipView, 0, params);
                }
            } else if (t < refreshViewHeight) {
                ViewGroup.LayoutParams params = vg.getChildAt(0).getLayoutParams();
                vg.removeViewAt(0);
                vg.addView(refreshView, 0, params);
            }
        }else{
            final int thisHeight = getHeight();
            //重新计算内容高度
            int contentHeight = 0;//内容高度
            int size = content.getChildCount();
            for (int i = 0; i < size; ++i) {
                View cv = content.getChildAt(i);
                contentHeight += cv.getMeasuredHeight();
                contentHeight += ((MarginLayoutParams) cv.getLayoutParams()).topMargin;
                contentHeight += ((MarginLayoutParams) cv.getLayoutParams()).bottomMargin;
            }
            final int fcontentHeight = contentHeight;
            if (t < n) {
                smoothScrollTo(0,refreshViewHeight);
            } else if (contentHeight > thisHeight
                    && t > (contentHeight + refreshViewHeight - thisHeight)) {//内容视图超出屏幕时被下拉，复位
                smoothScrollTo(0, fcontentHeight + refreshViewHeight - thisHeight);
            }else if(t > (contentHeight + refreshViewHeight - thisHeight)){//内容没有超过屏幕时被下拉过多，复位
                smoothScrollTo(0,refreshViewHeight);
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);

        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            isActionMove = true;
        }else if (ev.getAction() == MotionEvent.ACTION_UP) {
            isActionMove = false;
            int y = getScrollY();
            int n = (int) (refreshViewHeight * (1 - refreshViewScale));

            //重新计算内容高度
            int contentHeight = 0;//内容高度
            int size = content.getChildCount();
            for (int i = 0; i < size; ++i) {
                View cv = content.getChildAt(i);
                contentHeight += cv.getMeasuredHeight();
                contentHeight += ((MarginLayoutParams) cv.getLayoutParams()).topMargin;
                contentHeight += ((MarginLayoutParams) cv.getLayoutParams()).bottomMargin;
            }

            int thisHeight = getHeight();
            if (isRefreshing) {//刷新中再次移动则复位
                this.scrollTo(0, refreshViewHeight);
                isRefreshing = false;
            } else if (y < n) {//释放可刷新
                showRefresh();
            } else if (y < refreshViewHeight) {//没有达到刷新高度，复位
                this.scrollTo(0, refreshViewHeight);
            } else if (contentHeight < thisHeight
                    && y > refreshViewHeight) {//内容视图没有超出屏幕时被下拉，复位
                this.scrollTo(0, refreshViewHeight);
            } else if (contentHeight > thisHeight
                    && y > (contentHeight + refreshViewHeight - thisHeight)) {//内容视图超出屏幕时被下拉，复位
                this.scrollTo(0, contentHeight + refreshViewHeight - thisHeight);
            }

        }
        return true;
    }

    /**
     * 显示刷新，并激活刷新事件
     */
    public void showRefresh() {
        isRefreshing = true;
        this.scrollTo(0, 0);
        if (refreshListener != null) {
            refreshListener.refreshListener();
        }
        if (onRefreshView != null) {
            ViewGroup.LayoutParams params = vg.getChildAt(0).getLayoutParams();
            vg.removeViewAt(0);
            vg.addView(onRefreshView, 0, params);
        }
        Log.d("RefreshScrollView", "showRefresh ing...");
    }

    public void setRefreshSuccess() {
        ViewGroup.LayoutParams params = vg.getChildAt(0).getLayoutParams();
        int s;
        if (refreshSuccessView != null) {
            vg.removeViewAt(0);
            vg.addView(refreshSuccessView, 0, params);
            s = 800;
        } else {
            s = 0;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideRefresh();
            }
        }, s);
    }

    public void setsetRefreshNoDataSuccess() {
        ViewGroup.LayoutParams params = vg.getChildAt(0).getLayoutParams();
        int s;
        if (refreshSuccessView != null) {
            vg.removeViewAt(0);
            vg.addView(refreshSuccessNoDataView, 0, params);
            s = 800;
        } else {
            s = 0;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideRefresh();
            }
        }, s);
    }

    /**
     * 结束刷新，并且重置刷新状态
     */
    public void hideRefresh() {
        isRefreshing = false;
        this.scrollTo(0, refreshViewHeight);
        ViewGroup.LayoutParams params = vg.getChildAt(0).getLayoutParams();
        vg.removeViewAt(0);
        vg.addView(refreshView, 0, params);
        Log.d("RefreshScrollView", "hideRefresh ing...");
    }

    /**
     * 设置刷新中视图
     *
     * @param onRefreshView 刷新视图
     */
    public void setOnRefreshView(View onRefreshView) {
        this.onRefreshView = onRefreshView;
    }

    /**
     * 设置放开刷新的提示视图
     *
     * @param refreshViewTipView 放开刷新提示视图
     */
    public void setRefreshViewTipView(View refreshViewTipView) {
        this.refreshViewTipView = refreshViewTipView;
    }

    /**
     * 设置刷新成功视图
     *
     * @param refreshSuccessView  刷新成功视图
     */
    public void setRefreshSuccessView(View refreshSuccessView) {
        this.refreshSuccessView = refreshSuccessView;
    }

    /**
     * 设置刷新成功，但是没有数据视图
     *
     * @param refreshSuccessNoDataView 刷新成功，但是没有数据视图
     */
    public void setRefreshSuccessNoDataView(View refreshSuccessNoDataView) {
        this.refreshSuccessNoDataView = refreshSuccessNoDataView;
    }

    /**
     * 获取内容视图组
     *
     * @return 返回内容视图组供填充视图
     */
    public ViewGroup getContentView() {
        return content;
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    /**
     * 触发刷新事件
     */
    public interface RefreshListener {
        void refreshListener();
    }

}
