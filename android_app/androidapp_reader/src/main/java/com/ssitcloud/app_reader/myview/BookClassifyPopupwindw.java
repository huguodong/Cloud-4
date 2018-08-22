package com.ssitcloud.app_reader.myview;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.BookBizI;
import com.ssitcloud.app_reader.biz.impl.BookBizImpl;
import com.ssitcloud.app_reader.entity.StaticsTypeEntity;
import com.ssitcloud.app_reader.entity.Tag;
import com.ssitcloud.app_reader.view.BookItemActivity;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by LXP on 2017/5/2.
 * 图书类别弹窗
 */

public class BookClassifyPopupwindw {
    private PopupWindow pw;
    private boolean loadData = false;
    private boolean alreadyLoadData = false;
    private List<StaticsTypeEntity> bookClassifys;
    private ItemOnClickListener itemClickListener;
    private BookBizI bookbiz;
    private StaticsTypeEntity nowBookClassifys;

    public BookClassifyPopupwindw(Context context, ItemOnClickListener listener) {
        this.itemClickListener = listener;
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.sub_book_classify_popupwindow, null);

        pw = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pw.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.all_popupwindow_bg));
        pw.setTouchable(true);

        TagListView tagListView = (TagListView) contentView.findViewById(R.id.tagListView);
        tagListView.setOnTagClickListener(new TagListView.OnTagClickListener() {
            @Override
            public void onTagClick(TagView tagView, Tag tag) {
                int index = tag.getId();
                StaticsTypeEntity entity = bookClassifys.get(index);
                if(itemClickListener != null){
                    nowBookClassifys = entity;
                    itemClickListener.onClick(entity);
                }
            }
        });

        bookbiz = new BookBizImpl(context);
    }

    public BookClassifyPopupwindw(Context context) {
        this(context,null);
    }

    public void setTags(List<? extends Tag> tags) {
        TagListView tagListView = (TagListView) pw.getContentView().findViewById(R.id.tagListView);
        tagListView.setTags(tags);
    }

    public void showAsDropDown(View v) {
        if (!alreadyLoadData) {
            if (!loadData) {
                Log.i(getClass() + "", "loaddataing...");
                pw.getContentView().findViewById(R.id.waitView).setVisibility(View.VISIBLE);
                loadData = true;
                AsyncTask<Void, Void, List<StaticsTypeEntity>> task = new AsyncTask<Void, Void, List<StaticsTypeEntity>>() {
                    @Override
                    protected List<StaticsTypeEntity> doInBackground(Void... params) {
                        try {
                            return bookbiz.bookClassify();
                        } catch (SocketException e) {
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(List<StaticsTypeEntity> data) {
                        if(pw != null) {
                            if (data != null) {
                                Collections.sort(data, new Comparator<StaticsTypeEntity>() {
                                    @Override
                                    public int compare(StaticsTypeEntity o1, StaticsTypeEntity o2) {
                                        return o1.getData_desc().length()-o2.getData_desc().length();
                                    }
                                });
                                StaticsTypeEntity entity = new StaticsTypeEntity();
                                entity.setData_key(null);
                                entity.setData_desc("全部");
                                data.add(0,entity);

                                bookClassifys = data;

                                List<Tag> tags = new ArrayList<>(bookClassifys.size());
                                for (int i = 0,z = bookClassifys.size(); i < z; i++) {
                                    Tag tag = new Tag();
                                    tag.setId(i);
                                    tag.setTitle(bookClassifys.get(i).getData_desc());
                                    tags.add(tag);
                                }
                                setTags(tags);
                                hindWait();
                                hindMessate();
                                alreadyLoadData = true;
                            } else {
                                hindWait();
                                showFailMessate();
                            }
                            Log.i(getClass() + "", "loaddata finish");
                            loadData = false;
                        }
                    }
                };
                showWait();
                task.execute();
            }
        }
        pw.showAsDropDown(v);

    }

    public StaticsTypeEntity getChose(){
        return nowBookClassifys;
    }

    public void dismiss() {
        pw.dismiss();
    }

    private void showWait() {
        pw.getContentView().findViewById(R.id.waitView).setVisibility(View.VISIBLE);
    }

    private void hindWait() {
        pw.getContentView().findViewById(R.id.waitView).setVisibility(View.GONE);
    }

    private void hindMessate() {
        pw.getContentView().findViewById(R.id.message).setVisibility(View.GONE);
    }

    private void showFailMessate() {
        View viewById = pw.getContentView().findViewById(R.id.messageView);
        TextView tv = (TextView) viewById.findViewById(R.id.message);
        tv.setText("获取分类失败，请稍后再试");
        viewById.setVisibility(View.VISIBLE);
    }

    public ItemOnClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemOnClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemOnClickListener{
        void onClick(StaticsTypeEntity bookClassify);
    }
}
