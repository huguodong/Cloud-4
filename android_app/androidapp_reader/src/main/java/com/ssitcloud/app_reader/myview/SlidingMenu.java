package com.ssitcloud.app_reader.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.common.utils.DisplayUtil;
import com.ssitcloud.app_reader.myview.myViewInterface.SlidingMenuOpenListener;

/**
 * Created by LXP on 2017/3/15.
 * 侧滑菜单
 */

public class SlidingMenu extends HorizontalScrollView {
    private int screenWidth;//屏幕宽度
    private float menuWidthBi = 0.8f;//菜单占比
    private int menuWidth;//个人菜单宽度
    private int mainViewWidth;//主界面宽度
    private float showMenuScale = 0.5f;
    private boolean isDrawer = true;
    private int menuCriticalValue;

    private boolean once = false;

    private View menuView;
    private View contentView;

    private View contextForeground;
    private float contentForegroundAlpha = 0.1f;
    private View.OnClickListener contextForegroundListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            closeMenu();
            v.setVisibility(View.GONE);
        }
    };
    private boolean isShowMenu = false;//当前是否显示菜单

    //事件
    private SlidingMenuOpenListener slidingMenuOpenListener;

    public SlidingMenu(Context context, AttributeSet attrs){
        super(context,attrs);
        screenWidth = DisplayUtil.getScreenWidth(context);
        this.setHorizontalScrollBarEnabled(false);//取消滚动条
        this.setOverScrollMode(OVER_SCROLL_NEVER);//取消阴影
        //获取自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);
        int size = ta.getIndexCount();
        for(int i = 0;i<size;++i){
            int arr = ta.getIndex(i);
            switch (arr){
                case R.styleable.SlidingMenu_menu_critical:
                    menuWidthBi = ta.getFloat(arr,0.8f);
                    break;
                case R.styleable.SlidingMenu_show_Menu_scale:
                    showMenuScale = ta.getFloat(arr,0.5f);
                    break;
                case R.styleable.SlidingMenu_drawer_menu:
                    isDrawer = ta.getBoolean(arr,true);
                    break;
                case R.styleable.SlidingMenu_content_foreground_alpha:
                    contentForegroundAlpha = ta.getFloat(arr,0.1f);
                    break;
            }
        }
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //是否首次计算尺寸
        if(!once){
            ViewGroup wrapper = (ViewGroup) getChildAt(0);
            menuView = wrapper.getChildAt(0);
            contentView = wrapper.getChildAt(1);
            menuWidth = (int) (screenWidth*menuWidthBi);
            menuView.getLayoutParams().width = menuWidth;
            menuCriticalValue = (int) (menuWidth*(1.0f-showMenuScale));

            mainViewWidth = screenWidth;
            contentView.getLayoutParams().width = mainViewWidth;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            this.scrollTo(menuWidth,0);
            once = true;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l*1.0f/menuWidth;//菜单显示比例
        if(!isDrawer) {
            menuView.setTranslationX(menuWidth * scale * 0.6f);
        }
        if(contextForeground != null){
            contextForeground.setAlpha((1-scale)* contentForegroundAlpha);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(contextForeground != null){
            contextForeground.setVisibility(View.VISIBLE);
        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
                int thisScrollX = getScrollX();
                if(thisScrollX > menuCriticalValue){
                    closeMenu();
                }else{
                    showMenu();
                }
                return true;

        }
        return super.onTouchEvent(ev);
    }

    /**
     * 显示菜单
     */
    public void showMenu(){
        contextForeground.setVisibility(VISIBLE);

        this.smoothScrollTo(0,0);
        isShowMenu = true;
        if(slidingMenuOpenListener != null){
            slidingMenuOpenListener.onOpenListener(menuView);
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu(){
        this.smoothScrollTo(menuWidth,0);
        isShowMenu = false;
        contextForeground.setVisibility(INVISIBLE);
    }

    public void setSlidingMenuOpenListener(SlidingMenuOpenListener slidingMenuOpenListener){
        this.slidingMenuOpenListener = slidingMenuOpenListener;
    }

    public View getContextForeground() {
        return contextForeground;
    }

    public void setContextForeground(View contextForeground) {
        this.contextForeground = contextForeground;
        if(this.contextForeground != null){
            this.contextForeground.setOnClickListener(contextForegroundListener);
            this.contextForeground.setVisibility(View.GONE);
        }
    }

    public View getMenuView() {
        return menuView;
    }

    public void setMenuView(View menuView) {
        this.menuView = menuView;
    }

    public View getContentView() {
        return contentView;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    public float getContentForegroundAlpha() {
        return contentForegroundAlpha;
    }

    public void setContentForegroundAlpha(float contentForegroundAlpha) {
        this.contentForegroundAlpha = contentForegroundAlpha;
    }

    public boolean isShowMenu() {
        return isShowMenu;
    }

    public void setShowMenu(boolean showMenu) {
        isShowMenu = showMenu;
    }

}
