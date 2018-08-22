package com.ssitcloud.app_operator.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ssitcloud.app_operator.R;
import com.ssitcloud.app_operator.biz.MessageRemindBizI;
import com.ssitcloud.app_operator.biz.impl.MessageRemindBizImpl;
import com.ssitcloud.app_operator.component.ManageActivity;
import com.ssitcloud.app_operator.db.entity.MessageRemindDbEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.schedulers.Schedulers;

/**
 * 创建日期：2017/4/19 10:39
 * 消息提醒
 *
 * @author shuangjunjie
 */

public class MessageRemindActivity extends ManageActivity {
    private final String TAG = "MessageRemindActivity";
    private int page = 2;
    private final int pageSize = 5;

    private ImageView returnV;
    private LayoutInflater inflater;
    private ListViewAdapter adapter;
    private LinearLayout noMsgView;
    private PullToRefreshListView normalPullToRefreshView;

    private Handler handler = new Handler();
    private MessageRemindBizI messageRemindBiz;
    private Comparator messageComparator = new Comparator<MessageRemindDbEntity>() {
        @Override
        public int compare(MessageRemindDbEntity o1, MessageRemindDbEntity o2) {
            if (o1.getTrouble_time() == null) {
                return -1;
            }
            if (o2.getTrouble_time() == null) {
                return 1;
            }
            return o1.getTrouble_time().compareTo(o2.getTrouble_time());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        messageRemindBiz = new MessageRemindBizImpl(this);
        super.onCreate(savedInstanceState);

        adapter = new ListViewAdapter();

        setContentView(R.layout.activity_message_remind);
        noMsgView = (LinearLayout) findViewById(R.id.message_remind_no_msg_view);
        normalPullToRefreshView = (PullToRefreshListView) findViewById(R.id.message_remind_normal_pull_to_refresh_listview);

        List<MessageRemindDbEntity> messages = messageRemindBiz.getMessageRemind(1, pageSize);
        if (!messages.isEmpty()) {
            normalPullToRefreshView.setVisibility(View.VISIBLE);
            noMsgView.setVisibility(View.INVISIBLE);
            adapter.addMessage(messages);
        } else {
            normalPullToRefreshView.setVisibility(View.INVISIBLE);
            noMsgView.setVisibility(View.VISIBLE);
        }

        inflater = LayoutInflater.from(MessageRemindActivity.this);

        returnV = (ImageView) findViewById(R.id.message_remind_return);

        normalPullToRefreshView.setAdapter(adapter);

        normalPullToRefreshView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉刷新
        normalPullToRefreshView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        normalPullToRefreshView.getLoadingLayoutProxy(true, false).setReleaseLabel("释放立即刷新");
        normalPullToRefreshView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载...");
        normalPullToRefreshView.getLoadingLayoutProxy(true, false).setLoadingDrawable(this.getResources().getDrawable(R.drawable.load));
        //设置刷新监听
        normalPullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                List<MessageRemindDbEntity> messages = messageRemindBiz.getMessageRemind(page, pageSize);
                if (!messages.isEmpty()) {
                    adapter.addMessage(messages);
                    page++;
                } else {
                    Toast.makeText(MessageRemindActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                }
                onRefreshComplete();
            }
        });

        normalPullToRefreshView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        returnV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void onRefreshComplete() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                normalPullToRefreshView.onRefreshComplete();
                adapter.notifyDataSetChanged();
            }
        },100);
    }

    private class ListViewAdapter extends BaseAdapter {

        private List<MessageRemindDbEntity> mList;

        public ListViewAdapter() {
            this.mList = new ArrayList<>();
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.activity_message_remind_items, null);
                holder = new ViewHolder();

                holder.messageTimeView = (TextView) convertView.findViewById(R.id.items_message_time);
                holder.messageNameView = (TextView) convertView.findViewById(R.id.items_message_name);
//                holder.messageTroubleTitleView = (TextView) convertView.findViewById(R.id.items_message_trouble_title);
                holder.messageTroubleTimeView = (TextView) convertView.findViewById(R.id.items_message_trouble_time);
                holder.messageContentView = (TextView) convertView.findViewById(R.id.items_message_content);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            MessageRemindDbEntity messageEntity = mList.get(position);
            String messageTime = sdf.format(messageEntity.getCreate_time());
            String messageName = messageEntity.getTrouble_name();
            String messageTroubleTime = messageEntity.getTrouble_time();
            String messageContent = messageEntity.getTrouble_info();

            holder.messageTimeView.setText(messageTime);
            holder.messageNameView.setText(messageName);
//            holder.messageTroubleTitleView.setText(messageTroubleTitle);
            holder.messageTroubleTimeView.setText(messageTroubleTime);
            holder.messageContentView.setText(messageContent);

            return convertView;
        }

        public void addMessage(List<MessageRemindDbEntity> messages) {
            Collections.sort(messages, messageComparator);
            mList.addAll(0, messages);
            //检查未读消息
            List<Integer> unReads = new ArrayList<>();
            for (MessageRemindDbEntity message : messages) {
                if(message.getState() == 0){
                    unReads.add(message.getTrouble_idx());
                }
            }
            messageRemindBiz.updateDeviceTroubles(unReads)
                    .subscribeOn(Schedulers.io()).subscribe();
        }
    }

    private class ViewHolder {

        private TextView messageTimeView;
        private TextView messageNameView;
        //        private TextView messageTroubleTitleView;
        private TextView messageTroubleTimeView;
        private TextView messageContentView;
    }

}
