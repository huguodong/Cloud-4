package com.ssitcloud.app_reader.view;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.ReaderCardBizI;
import com.ssitcloud.app_reader.biz.impl.ReaderCardBizImpl;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.utils.BarcodeUtil;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.presenter.ReaderCardListPresenter;
import com.ssitcloud.app_reader.view.viewInterface.ReaderCardListViewI;

import java.util.List;

public class ReaderCardListActivity extends BaseActivity implements ReaderCardListViewI {
    private final String TAG = "ReaderCardListActivity";

    private ViewGroup scrollViewGroup;//scroller view child view
    private LinearLayout readerCardViewGroup;
    private ReaderCardListPresenter readerPersenter;
    private View networkErrorView;//等待视图
    private LayoutInflater inflater;//填充工具
    private View waitView;
    private View emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_card_list);
        readerPersenter = new ReaderCardListPresenter(this, this.getApplicationContext());
        scrollViewGroup = (ViewGroup) findViewById(R.id.scroll_main_view);

        Resources resource = this.getResources();
        //init cardViewGroup
        readerCardViewGroup = new LinearLayout(this);
        LinearLayout.LayoutParams readerCardLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int mT = resource.getDimensionPixelOffset(R.dimen.empty_card_top_padding);
        int mRL = resource.getDimensionPixelOffset(R.dimen.empty_card_left_padding);
        readerCardLayoutParams.setMargins(mRL, mT, mRL, 0);
        readerCardViewGroup.setLayoutParams(readerCardLayoutParams);
        readerCardViewGroup.setOrientation(LinearLayout.VERTICAL);
        //init other view
        inflater = LayoutInflater.from(this);
        //init wait view
        waitView = findViewById(R.id.renewWaitView);
        //set empty on click listener
        waitView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });

        //init network error view
        networkErrorView = inflater.inflate(R.layout.sub_network_lost, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        networkErrorView.setLayoutParams(layoutParams);
        networkErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        //init emoty view
        emptyView = inflater.inflate(R.layout.sub_empty_reader_card, null);

        //设置绑定卡点击事件
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindCard();
            }
        };
        //设置空卡绑定卡点击事件
        emptyView.setOnClickListener(onClickListener);
        //设置右上角卡绑定事件
        View addReaderCardButton = findViewById(R.id.add_readercard);
        addReaderCardButton.setOnClickListener(onClickListener);
        //左上角返回按钮
        View backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //load data
        loadData();
    }

    @Override
    public void setView(List<ReaderCardDbEntity> viewData) {
        Resources resource = getResources();
        //清空所有
        if (scrollViewGroup.getChildCount() != 0) {
            scrollViewGroup.removeAllViews();
        }
        if (readerCardViewGroup.getChildCount() != 0) {
            readerCardViewGroup.removeAllViews();
        }

        LinearLayout.LayoutParams layoutPrams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, resource.getDimensionPixelOffset(R.dimen.reader_card_height));
        layoutPrams.setMargins(0, 0, 0, resource.getDimensionPixelOffset(R.dimen.reader_card_margin));
        int barcodeWidth = resource.getDimensionPixelOffset(R.dimen.reader_card_barcode_width);
        int barcodeHeigth = resource.getDimensionPixelOffset(R.dimen.reader_card_barcode_height);
        //填充视图
        for (int i = 0, z = viewData.size(); i < z; ++i) {
            ReaderCardDbEntity data = viewData.get(i);
            View v = inflater.inflate(R.layout.sub_reader_card, null);
            TextView libName = (TextView) v.findViewById(R.id.library_name);
            libName.setText(StringUtils.getStr(data.getLib_name(), "暂无"));
            TextView card_no = (TextView) v.findViewById(R.id.idcard_num);
            card_no.setText(data.getCard_no());
            //设置条形码
            ImageView barcodeView = (ImageView) v.findViewById(R.id.reader_card_barcode);
            try {
                Bitmap imp = BarcodeUtil.encodeToOneBarcode(data.getCard_no(), barcodeWidth, barcodeHeigth, ContextCompat.getColor(this, R.color.barcode_color));
                barcodeView.setImageBitmap(imp);
            } catch (WriterException e) {
                Log.e(TAG, "encode " + data.getCard_no() + " fail " + e.getMessage());
            }

            v.setTag(data);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ReaderCardDbEntity card = (ReaderCardDbEntity) v.getTag();
                    Intent cardIntent = new Intent(ReaderCardListActivity.this, ReaderCardDetailActivity.class);
                    cardIntent.putExtra("cardData", card);
                    startActivityForResult(cardIntent, 2);
                }
            });
            //填充视图
            readerCardViewGroup.addView(v, layoutPrams);
        }
        if (readerCardViewGroup.getChildCount() == 0) {
            readerCardViewGroup.addView(emptyView);
        }
        scrollViewGroup.addView(readerCardViewGroup);
        goneWait();
    }

    @Override
    public void setNetworkErrorView() {
        int size = scrollViewGroup.getChildCount();
        if (size > 1) {
            scrollViewGroup.removeAllViews();
            scrollViewGroup.addView(networkErrorView);
        } else if (size == 1) {
            if (scrollViewGroup.getChildAt(0).getId() != networkErrorView.getId()) {
                scrollViewGroup.removeAllViews();
                scrollViewGroup.addView(networkErrorView);
            }
        } else {
            scrollViewGroup.addView(networkErrorView);
        }
        goneWait();
    }

    @Override
    public void setUnlogin() {
        logout();
    }

    /*
         * @param code 错误码 -1:未登录，-2网络连接失败，-3：刷新后重试
         */
    @Override
    public void setMessageView(int code) {
        if (code == -1) {
            Toast.makeText(this, "请先登陆", Toast.LENGTH_SHORT).show();
            logout();
        } else if (code == -2) {
            Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
        } else if (code == -3) {
            Toast.makeText(this, "请刷新后重试", Toast.LENGTH_SHORT).show();
        }
        goneWait();
    }

    @Override
    public void deleteView(ReaderCardDbEntity viewTag) {
        if (viewTag == null) {
            return;
        }
        int z = readerCardViewGroup.getChildCount();
        for (int i = 0; i < z; ++i) {
            View v = readerCardViewGroup.getChildAt(i);
            ReaderCardDbEntity tagObj = (ReaderCardDbEntity) v.getTag();
            if (tagObj == null) {
                continue;
            }
            if (StringUtils.isEqual(viewTag.getLib_idx(), tagObj.getLib_idx()) && StringUtils.isEqual(viewTag.getCard_no(), tagObj.getCard_no())) {
                readerCardViewGroup.removeViewAt(i);
                //判断是否删除了首选卡
                if(readerCardViewGroup.getChildCount() == 0){
                    readerCardViewGroup.addView(emptyView);
                } else if(v.findViewById(R.id.preferCard).getVisibility() == View.VISIBLE){
                    //调用业务层计算首选卡
                    ReaderCardBizI readerCardBiz = new ReaderCardBizImpl(getResources(),this);
                    setPreferCard(readerCardBiz.obtainPreferCard());
                }
                break;
            }
        }
        goneWait();
    }

    @Override
    public void setPreferCard(ReaderCardDbEntity viewTag){
        if (viewTag == null) {
            return;
        }
        int z = readerCardViewGroup.getChildCount();
        View v = null;
        for (int i = 0; i < z; ++i) {
            v = readerCardViewGroup.getChildAt(i);
            ReaderCardDbEntity tagObj = (ReaderCardDbEntity) v.getTag();
            if (tagObj == null) {
                continue;
            }
            if (StringUtils.isEqual(viewTag.getLib_idx(), tagObj.getLib_idx()) && StringUtils.isEqual(viewTag.getCard_no(), tagObj.getCard_no())) {
                v.findViewById(R.id.preferCard).setVisibility(View.VISIBLE);
                v.setBackgroundResource(R.drawable.reader_prefer_card);
                break;
            }else{
                v.findViewById(R.id.preferCard).setVisibility(View.GONE);
                v.setBackgroundResource(R.drawable.reader_card);
            }
            v = null;//重置V状态
        }
        //若V不为null
        if(v != null){
            readerCardViewGroup.removeView(v);
            readerCardViewGroup.addView(v,0);
        }
    }

    private void showWait() {
        waitView.setVisibility(View.VISIBLE);
    }

    private void goneWait() {
        waitView.setVisibility(View.GONE);
    }

    private void loadData() {
        showWait();
        readerPersenter.loadData();
    }

    /**
     * 打开绑定卡页面，完成后会回调onActivityResult
     */
    private void bindCard() {
        Intent intent = new Intent(this, BindCardActivity.class);
        //绑定卡成功返回200
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 200) {
            loadData();
        } else if (resultCode == RESULT_OK && requestCode == 2) {//requestCode == 2 跳转到卡详情操作
            ReaderCardDbEntity card = (ReaderCardDbEntity) data.getSerializableExtra("ReaderCardDbEntity");
            int order = data.getIntExtra("order",-1);
            if(order == -1){
                return;
            }else if(order == 1) {//解绑卡
                deleteView(card);
            }else if(order == 2) {//设置首选卡
                setPreferCard(card);
            }
        }

    }
}
