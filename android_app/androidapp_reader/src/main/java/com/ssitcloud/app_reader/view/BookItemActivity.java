package com.ssitcloud.app_reader.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.ConfigBizI;
import com.ssitcloud.app_reader.biz.FavoritBizI;
import com.ssitcloud.app_reader.biz.impl.ConfigBizImpl;
import com.ssitcloud.app_reader.biz.impl.FavoritBizImpl;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.utils.ImgHandler;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.entity.AppOPACEntity;
import com.ssitcloud.app_reader.entity.BookUnionEntity;
import com.ssitcloud.app_reader.entity.StaticsTypeEntity;
import com.ssitcloud.app_reader.myview.BookClassifyPopupwindw;
import com.ssitcloud.app_reader.myview.SearchEditText;
import com.ssitcloud.app_reader.presenter.BookItemPresenter;
import com.ssitcloud.app_reader.view.viewInterface.BookItemViewI;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/4/8.
 * 图书列表视图，可从Intent传入opac参数控制检索设备，若没有传入，则使用读者上次选的的opac参数
 */

public class BookItemActivity extends BaseActivity implements BookItemViewI {
    private final int SELECT_DEVICE_CODE = 200;

    private BookItemPresenter bookitemPresenter;
    private View waitView;
    private View networkError;

    private PullToRefreshListView normalPullToRefreshView;
    private BookItemAdapter normalAdapter;

    private View searchView;//搜索视图
    private Animation searchViewOpenAnim;
    private Animation searchViewCloseAnim;
    private PullToRefreshListView searchListview;//搜索视图的数据视图
    private BookItemAdapter searchAdapter;
    private SearchEditText searchEdit;
    private Animation searchEditOpenAnim;
    private Animation searchEditCloseAnim;

    private boolean isFristLoad = true;//是否为首次加载视图
    private boolean isFinish = false;//此视图是否已经关闭
    private boolean isSearchModel = false;//是否为搜索模式
    private AppOPACEntity opac;

    private Handler handler = new Handler();

    private StaticsTypeEntity bookClassify;
    private volatile long searchDataTime = 0;//当前数据更新时间
    private volatile long normalUpdateTime = 0;//当前数据更新时间
    private BookClassifyPopupwindw bookClassifyPopupwindw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiry_book_search);

        if (savedInstanceState != null) {
            opac = (AppOPACEntity) savedInstanceState.get("opac");
        } else {//从Intent中获取参数
            Intent intent = getIntent();
            opac = (AppOPACEntity) intent.getSerializableExtra("opac");
        }
        //删除旧的收藏数据
        FavoritBizI favoritBiz = new FavoritBizImpl(BookItemActivity.this);
        favoritBiz.deleteAll();

        bookitemPresenter = new BookItemPresenter(this, this,10);

        normalPullToRefreshView = (PullToRefreshListView) findViewById(R.id.normal_pull_to_refresh_listview);
        normalPullToRefreshView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        normalPullToRefreshView.getLoadingLayoutProxy(false, true).setPullLabel("继续滑动加载更多");
        normalPullToRefreshView.getLoadingLayoutProxy(false, true).setReleaseLabel("放开加载更多");
        normalPullToRefreshView.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中");
        //设置刷新监听
        normalPullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                nextPage();
            }
        });
        normalAdapter = new BookItemAdapter(this);
        normalPullToRefreshView.setAdapter(normalAdapter);
        //设置搜索组件

        searchView = findViewById(R.id.search_view);
        searchViewOpenAnim = AnimationUtils.loadAnimation(this, R.anim.search_content_open_translate);
        searchViewCloseAnim = AnimationUtils.loadAnimation(this, R.anim.search_content_close_translate);
        searchEdit = (SearchEditText) findViewById(R.id.searchStr);
        searchEditOpenAnim = AnimationUtils.loadAnimation(this, R.anim.search_edittext_open_translate);
        searchEditCloseAnim = AnimationUtils.loadAnimation(this, R.anim.search_edittext_close_translate);

        searchAdapter = new BookItemAdapter(this);
        searchListview = (PullToRefreshListView) findViewById(R.id.search_listview);
        searchListview.setAdapter(searchAdapter);
        ListViewData scarchTag = new ListViewData();
        scarchTag.setPageCurrent(1);
        searchListview.setTag(scarchTag);
        searchListview.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        searchListview.getLoadingLayoutProxy(false, true).setPullLabel("继续滑动加载更多");
        searchListview.getLoadingLayoutProxy(false, true).setReleaseLabel("放开加载更多");
        searchListview.getLoadingLayoutProxy(false, true).setRefreshingLabel("加载中");
        searchListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                nextSearchPage();
            }
        });

        searchEdit.setIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchStr = ((EditText) v).getText().toString();
                if (!StringUtils.isEmpty(searchStr)) {
                    ListViewData tag = (ListViewData) searchListview.getTag();
                    tag.setPageCurrent(1);
                    waitView.setVisibility(View.VISIBLE);
                    searchDataTime = System.currentTimeMillis();
                    StaticsTypeEntity chose = bookClassifyPopupwindw.getChose();
                    String bClass = chose!=null?chose.getData_key():null;
                    bookitemPresenter.loadData(opac,bClass,searchStr,1,searchDataTime);
                } else {
                    Toast.makeText(BookItemActivity.this, "请输入关键字", Toast.LENGTH_SHORT).show();
                }
            }
        });

        waitView = findViewById(R.id.waitView);
        waitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        networkError = findViewById(R.id.networkError);
        networkError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        //导航栏搜索图标
        View search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFristLoad && !isSearchModel) {
                    searchModel();
                }
            }
        });

        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookItemActivity.this.finish();
            }
        });

        View deviceView = findViewById(R.id.deviceView);
        TextView dvName = (TextView) deviceView.findViewById(R.id.deivceName);

        //设置重新选择设备事件
        deviceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookItemActivity.this, DeviceActivity.class);
                startActivityForResult(i, SELECT_DEVICE_CODE);
            }
        });

        //显示已选书
        View showFavorit = findViewById(R.id.showFavorit);
        showFavorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookItemActivity.this, FavoritActivity.class);
                startActivity(i);
            }
        });

        bookClassifyPopupwindw = new BookClassifyPopupwindw(BookItemActivity.this);
        bookClassifyPopupwindw.setItemClickListener(new BookClassifyPopupwindw.ItemOnClickListener() {
            @Override
            public void onClick(StaticsTypeEntity bookClassify) {
                selectBookClassify(bookClassify);
                bookClassifyPopupwindw.dismiss();
            }
        });
        View searchClassify = findViewById(R.id.bookClassify);
        searchClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookClassifyPopupwindw.dismiss();
                bookClassifyPopupwindw.showAsDropDown(v);
            }
        });

        //加载设备数据
        ConfigBizI configBiz = new ConfigBizImpl(this);
        boolean canLoad;
        if (opac == null) {
            opac = configBiz.getPreferDevice();
            if (opac == null) {
                normalUpdateTime = System.currentTimeMillis();
                setFail(FAIL_STATE.un_select_device, normalUpdateTime);
                canLoad = false;
            }else{
                //有选择设备，设置设备名
                dvName.setText(opac.getDevName());
                canLoad = true;
            }
        }else {
            //有选择设备，设置设备名
            dvName.setText(opac.getDevName());
            canLoad = true;
        }
        if (canLoad) {
            //加载数据
            waitView.setVisibility(View.VISIBLE);
            loadInitialData(opac);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isFinish = true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isSearchModel) {
                normalModel(true);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 下一页
     */
    public void nextPage() {
        ListViewData tag = (ListViewData) normalPullToRefreshView.getTag();
        normalUpdateTime = System.currentTimeMillis();
        String bClass = bookClassify!=null?bookClassify.getData_key():null;
        bookitemPresenter.loadData(opac,bClass,null,tag.getPageCurrent(),normalUpdateTime);
    }

    public void nextSearchPage(){
        ListViewData tag = (ListViewData) searchListview.getTag();
        searchDataTime = System.currentTimeMillis();
        StaticsTypeEntity bookClassify = bookClassifyPopupwindw.getChose();
        String bClass = bookClassify!=null?bookClassify.getData_key():null;
        String searchStr = searchEdit.getText().toString();
        bookitemPresenter.loadData(opac,bClass,searchStr,tag.getPageCurrent(), searchDataTime);
    }

    private void searchModel() {
        isSearchModel = true;
        searchDataTime = System.currentTimeMillis();
        searchEdit.setVisibility(View.VISIBLE);
        searchEdit.startAnimation(searchEditOpenAnim);
        searchView.setVisibility(View.VISIBLE);
        searchView.startAnimation(searchViewOpenAnim);
    }

    /**
     * @param needAnim 是否需要动画
     */
    private void normalModel(boolean needAnim) {
        isSearchModel = false;
        if (needAnim) {
            searchEdit.startAnimation(searchEditCloseAnim);
            searchView.startAnimation(searchViewCloseAnim);
        }
        searchEdit.setVisibility(View.INVISIBLE);
        searchEdit.setText("");

        searchView.setVisibility(View.INVISIBLE);

        waitView.setVisibility(View.INVISIBLE);
        networkError.setVisibility(View.INVISIBLE);

        searchAdapter.getDataSource().clear();
        searchAdapter.notifyDataSetChanged();

        selectBookClassify(bookClassify);
    }

    @Override
    public void setFail(FAIL_STATE state,long sync) {
        if (isFinish || normalUpdateTime != sync) {
            return;
        }
        waitView.setVisibility(View.INVISIBLE);
        if (state == FAIL_STATE.un_select_device) {
            Intent i = new Intent(this, DeviceActivity.class);
            startActivityForResult(i, SELECT_DEVICE_CODE);
        } else if (state == FAIL_STATE.network_error) {//网络连接失败
            if (isFristLoad) {
                networkError.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "网络出错了", Toast.LENGTH_SHORT).show();
            }
        }
        normalPullToRefreshView.onRefreshComplete();
    }

    @Override
    public void setSuccessData(List<BookUnionEntity> data,long sync) {
        if (isFinish || normalUpdateTime != sync) {
            return;
        }
        isFristLoad = false;
        waitView.setVisibility(View.INVISIBLE);
        ListViewData tag = (ListViewData) normalPullToRefreshView.getTag();
        if (tag != null) {
            if (!data.isEmpty()) {
                normalAdapter.getDataSource().addAll(data);
                normalAdapter.notifyDataSetChanged();
                tag.setPageCurrent(tag.getPageCurrent()+1);
            } else {
                Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show();
            }
        }
        normalPullToRefreshView.onRefreshComplete();
    }

    @Override
    public void setSearchData(List<BookUnionEntity> data,long sync) {
        if (isFinish || !isSearchModel || sync != searchDataTime) {
            return;
        }
        networkError.setVisibility(View.INVISIBLE);
        waitView.setVisibility(View.INVISIBLE);
        ListViewData tag = (ListViewData) searchListview.getTag();
        if (tag != null) {
            if (!data.isEmpty()) {
                tag.setPageCurrent(tag.getPageCurrent()+1);
                searchAdapter.getDataSource().addAll(data);
            } else {
                Toast.makeText(this, "没有搜索到数据", Toast.LENGTH_SHORT).show();
            }
            searchAdapter.notifyDataSetChanged();
        }
        searchListview.onRefreshComplete();
    }

    @Override
    public void setSearchFail(FAIL_STATE state,long sync) {
        if(sync != searchDataTime){
            return ;
        }

        waitView.setVisibility(View.INVISIBLE);
        if (state == FAIL_STATE.network_error) {
            networkError.setVisibility(View.VISIBLE);
        } else if (state == FAIL_STATE.un_select_device) {
            Intent i = new Intent(this, DeviceActivity.class);
            startActivityForResult(i, SELECT_DEVICE_CODE);
        }
        searchListview.onRefreshComplete();
    }

    /**
     * 重新选择了设备方法
     *
     * @param opac
     */
    private void reSelectionDevice(AppOPACEntity opac) {
        View deviceView = findViewById(R.id.deviceView);
        TextView dvName = (TextView) deviceView.findViewById(R.id.deivceName);
        dvName.setText(opac.getDevName());
        isFristLoad = true;
        normalModel(false);
        waitView.setVisibility(View.VISIBLE);
        loadInitialData(opac);
    }

    /**
     * 载入初始数据
     */
    private void loadInitialData(AppOPACEntity opac) {
        networkError.setVisibility(View.INVISIBLE);
        ListViewData lvd = new ListViewData();
        lvd.setPageCurrent(1);
        normalPullToRefreshView.setTag(lvd);
        normalUpdateTime = System.currentTimeMillis();
        String bClass = bookClassify!=null?bookClassify.getData_key():null;
        bookitemPresenter.loadData(opac,bClass,null,1,normalUpdateTime);
        normalAdapter.getDataSource().clear();
        normalAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_DEVICE_CODE) {
            if (resultCode == RESULT_OK) {
                opac = (AppOPACEntity) data.getSerializableExtra("opac");
                reSelectionDevice(opac);
            } else {
                if (opac == null) {//没有选择设备，也没有选过设备
                    finish();
                }
            }
        }
    }

    private void selectBookClassify(StaticsTypeEntity entity){
        TextView tv = (TextView) findViewById(R.id.classifyName);
        if (entity != null) {
            tv.setText(entity.getData_desc());
        }else{
            tv.setText("全部");
        }
        String searchStr = searchEdit.getText().toString();
        if(isSearchModel){
            if (!StringUtils.isEmpty(searchStr)) {
                searchAdapter.getDataSource().clear();
                searchAdapter.notifyDataSetChanged();
                waitView.setVisibility(View.VISIBLE);
                ListViewData tag = (ListViewData) searchListview.getTag();
                tag.setPageCurrent(1);
                searchDataTime = System.currentTimeMillis();
                String bClass = entity!=null?entity.getData_key():null;
                bookitemPresenter.loadData(opac,bClass,searchStr,1,searchDataTime);
            }
        }else{
            String bClass = bookClassify!=null?bookClassify.getData_key():null;
            if(entity != null && !StringUtils.isEqual(bClass,entity.getData_key())){
                bookClassify = entity;
                loadInitialData(opac);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("opac", opac);
    }
    /**
     * 数据适配器
     */
    private class BookItemAdapter extends BaseAdapter {
        private List<BookUnionEntity> dataSource;
        private Map<String, SoftReference<Bitmap>> bitmapCache = new HashMap<>(64);

        private LayoutInflater inflater;
        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookUnionEntity bue = (BookUnionEntity) v.getTag();
                if (bue != null) {
                    Intent i = new Intent(BookItemActivity.this, BookInfoActivity.class);
                    i.putExtra("bue", bue);
                    i.putExtra("opac", opac);
                    startActivity(i);
                }
            }
        };

        private BookItemAdapter(Context context) {
            this(new ArrayList<BookUnionEntity>(), context);
        }

        private BookItemAdapter(List<BookUnionEntity> data, Context context) {
            dataSource = data;
            inflater = LayoutInflater.from(context.getApplicationContext());
        }

        @Override
        public int getCount() {
            return dataSource.size();
        }

        @Override
        public Object getItem(int position) {
            return dataSource.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewGroup v;
            if (convertView == null) {
                v = (ViewGroup) inflater.inflate(R.layout.sub_view_book_item, null);

            } else {
                v = (ViewGroup) convertView;
            }
            BookUnionEntity bookItem = dataSource.get(position);

            TextView title = (TextView) v.findViewById(R.id.bookTitle);
            TextView author = (TextView) v.findViewById(R.id.author);
            TextView isbn = (TextView) v.findViewById(R.id.isbn);
            TextView publish = (TextView) v.findViewById(R.id.publish);
            ImageView iv = (ImageView) v.findViewById(R.id.bookImg);

            title.setText(bookItem.getTitle());
            author.setText(String.format("作者：%s", StringUtils.getStr(bookItem.getAuthor(), "暂无")));
            isbn.setText(String.format("ISBN：%s", StringUtils.getStr(bookItem.getISBN(), "暂无")));
            publish.setText(String.format("出版社：%s", StringUtils.getStr(bookItem.getPublish(), "暂无")));

            final String imgUrl = bookItem.getBookimage_url();
            if (imgUrl != null) {
                Log.d("ImgHandler", "have img,url==>" + imgUrl);

                boolean needFoundImgOnnetwork = true;
                SoftReference<Bitmap> bitmapRef = getImgCache(imgUrl);//查看是否存在图片缓存
                if (bitmapRef != null) {
                    Bitmap bm = bitmapRef.get();
                    if (bm != null) {
                        needFoundImgOnnetwork = false;
                        iv.setImageBitmap(bm);
                        iv.setVisibility(View.VISIBLE);
                    }
                }
                if (needFoundImgOnnetwork) {
                    Log.d("ImgHandler", "start found img to network url==>" + imgUrl);

                    ImgHandler r = new ImgHandler(iv, imgUrl);
                    ImgHandler.RequestImageSuccess requestImageSuccess = new ImgHandler.RequestImageSuccess() {
                        @Override
                        public void success(Bitmap bitmap) {
                            addImgCache(imgUrl, bitmap);//加入图片到缓存

                        }
                    };
                    r.setRequestImageSuccess(requestImageSuccess);
                    r.setCompress_size(10);

                    handler.post(r);
                }
            } else {
                iv.setVisibility(View.GONE);
            }
            v.setTag(bookItem);

            v.setOnClickListener(onClickListener);

            return v;
        }

        private List<BookUnionEntity> getDataSource() {
            return dataSource;
        }

        private void setOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        private void addImgCache(String url, Bitmap bm) {
            bitmapCache.put(StringUtils.getMd5(url.trim(), false), new SoftReference<>(bm));
        }

        private SoftReference<Bitmap> getImgCache(String url) {
            String hash = StringUtils.getMd5(url.trim(), false);
            return bitmapCache.get(hash);
        }
    }

    /**
     * listview必要数据
     */
    private static class ListViewData{
        private int pageCurrent;

        public int getPageCurrent() {
            return pageCurrent;
        }

        public void setPageCurrent(int pageCurrent) {
            this.pageCurrent = pageCurrent;
        }
    }
}
