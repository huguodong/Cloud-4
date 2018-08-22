package com.ssitcloud.app_reader.view;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.BookBizI;
import com.ssitcloud.app_reader.biz.FavoritBizI;
import com.ssitcloud.app_reader.biz.impl.BookBizImpl;
import com.ssitcloud.app_reader.biz.impl.FavoritBizImpl;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.utils.ImgHandler;
import com.ssitcloud.app_reader.common.utils.JsonUtils;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.db.entity.FavoritDbEntity;
import com.ssitcloud.app_reader.entity.AppOPACEntity;
import com.ssitcloud.app_reader.entity.BibliosEntity;
import com.ssitcloud.app_reader.entity.BookUnionEntity;
import com.ssitcloud.app_reader.entity.ReservationMessage;
import com.ssitcloud.app_reader.presenter.BookInfoPresenter;
import com.ssitcloud.app_reader.view.viewInterface.BookInfoViewI;

import java.net.SocketException;

import static com.ssitcloud.app_reader.view.viewInterface.BookInfoViewI.BookInfo_State.NETOWRK_ERROR;

/**
 * Created by LXP on 2017/4/10.
 * 书籍详细详细视图
 */

public class BookInfoActivity extends BaseActivity implements BookInfoViewI {
    private final String TAG = "BookInfoActivity";

    private AppOPACEntity opac;
    private BookUnionEntity bue;
    private Bitmap bookImg;
    private BibliosEntity bibliosEntity;
    private ImageView bookImgView;
    private Button advance;
    private TextView bookInfo;
    private TextView title;
    private TextView author;
    private TextView publish;
    private TextView deviceName;
    private TextView deviceLoan;
    private View waitView;

    private Handler handler = new Handler();

    private FavoritBizI favoritBiz;

    private BookInfoPresenter presenter;
    private boolean reserve = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        presenter = new BookInfoPresenter(this,this);

        favoritBiz = new FavoritBizImpl(this);

        if (savedInstanceState != null) {
            bue = (BookUnionEntity) savedInstanceState.get("bue");
            bookImg = (Bitmap) savedInstanceState.get("bookImg");
            opac = (AppOPACEntity) savedInstanceState.get("opac");
        } else {
            Intent i = getIntent();
            bue = (BookUnionEntity) i.getSerializableExtra("bue");
            opac = (AppOPACEntity) i.getSerializableExtra("opac");
        }

        if (bue == null || opac == null) {
            finish();
        }

        //主内容vg
        ViewGroup contentVg = (ViewGroup) findViewById(R.id.id_container);
        //设置动画
        LayoutTransition transition = new LayoutTransition();
        transition.setAnimator(LayoutTransition.APPEARING, transition.getAnimator(LayoutTransition.APPEARING));
        transition.setAnimator(LayoutTransition.CHANGE_APPEARING, transition.getAnimator(LayoutTransition.CHANGE_APPEARING));
        contentVg.setLayoutTransition(transition);


        bookImgView = (ImageView) findViewById(R.id.bookImg);
        title = (TextView) findViewById(R.id.bookTitle);

        author = (TextView) findViewById(R.id.author);

        publish = (TextView) findViewById(R.id.publish);

        deviceName = (TextView) findViewById(R.id.deviceName);

        deviceLoan = (TextView) findViewById(R.id.deviceLoan);

        bookInfo = (TextView) findViewById(R.id.bookInfo);
        String temp = bue.getTitle() != null ? bue.getTitle() : "";
        title.setText(temp);
        if (temp.length() > 15) {
            title.setGravity(Gravity.CENTER | Gravity.LEFT);
        }

        temp = bue.getAuthor() != null ? bue.getAuthor() : "暂无";
        author.setText(Html.fromHtml("<font color=\"#888888\">作者：</font>"
                + String.format("<font color=\"#333333\">%s</font>", temp)));

        temp = bue.getPublish() != null ? bue.getPublish() : "暂无";
        publish.setText(Html.fromHtml("<font color=\"#888888\">出版社：</font>"
                + String.format("<font color=\"#333333\">%s</font>", temp)));

        temp = opac.getDevName() != null ? opac.getDevName() : "未知";
        deviceName.setText(temp);

        temp = opac.getLocation() != null ? opac.getLocation() : "暂无";
        deviceLoan.setText(temp);

        if (bue.getBookimage_url() != null) {
            if (bookImg == null) {
                ImgHandler imgHandler = new ImgHandler(bookImgView, bue.getBookimage_url());
                imgHandler.setCompress_size(200);
                imgHandler.setRequestImageSuccess(new ImgHandler.RequestImageSuccess() {
                    @Override
                    public void success(Bitmap bitmap) {
                        bookImg = bitmap;
                        bookImgView.setVisibility(View.VISIBLE);
                    }
                });
                handler.post(imgHandler);
            } else {
                bookImgView.setImageBitmap(bookImg);
                bookImgView.setVisibility(View.VISIBLE);
            }
        }

        advance = (Button) findViewById(R.id.advance);
        advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setClickable(false);
                presenter.reservation(bue.getBook_barcode(), opac.getIdData());
            }
        });
        if(!opac.getIdData().containsKey("logistics_number")){
            reserve = false;
            findViewById(R.id.advanceLy).setVisibility(View.GONE);
        }

        Button favorit = (Button) findViewById(R.id.addFavorit);
        //检查是否加入收藏
        boolean isFavorit = favoritBiz.select(bue.getBookitem_idx()) != null;
        favorit.setTag(isFavorit);
        if(isFavorit){
            favorit.setText("已选书");
            findViewById(R.id.advanceLy).setVisibility(View.GONE);
        }else{
            favorit.setText("借书");
        }
        favorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFavorit = (boolean) v.getTag();
                if(isFavorit) {
                    try {
                        removeFavorit();
                        ((Button) v).setText("借书");
                        if(reserve) {
                            findViewById(R.id.advanceLy).setVisibility(View.VISIBLE);
                        }
                        v.setTag(false);
                    } catch (Exception e) {
                        Log.d(TAG,"删除收藏夹失败，bue==>"+ JsonUtils.toJson(bue));
                    }
                }else {

                    try {
                        addFavorit();
                        ((Button) v).setText("已选书");
                        v.setTag(true);
                        if(reserve) {
                            findViewById(R.id.advanceLy).setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        Log.d(TAG,"插入收藏夹失败，bue==>"+ JsonUtils.toJson(bue));
                    }
                }
            }
        });

        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookInfoActivity.this.finish();
            }
        });
        waitView = findViewById(R.id.waitView);
        waitView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { } });

        //获取书目详情
        AsyncTask<Integer, Void, BibliosEntity> task = new AsyncTask<Integer, Void, BibliosEntity>() {
            private volatile int state = 0;

            @Override
            protected BibliosEntity doInBackground(Integer... params) {
                BookBizI bookBiz = new BookBizImpl(BookInfoActivity.this);
                try {
                    BibliosEntity bibliosEntity = bookBiz.findBibliosEntity(params[0]);
                    state = 1;
                    return bibliosEntity;
                } catch (SocketException e) {
                    state = -1;
                }
                return null;
            }

            @Override
            protected void onPostExecute(BibliosEntity bibliosEntity) {
                if (state == 1) {
                    BookInfoActivity.this.bibliosEntity = bibliosEntity;
                    bookInfo.setText(StringUtils.getStr(bibliosEntity.getContents(), "无"));
                }
            }
        };
        task.execute(bue.getBib_idx());

        presenter.queryBookState(bue.getBookitem_idx());
    }

    private void addFavorit() {
        FavoritDbEntity entity = new FavoritDbEntity();
        entity.setBookitem_idx(bue.getBookitem_idx());
        entity.setBue(bue);
        entity.setOpac(opac);
        favoritBiz.insert(entity);
    }

    private void removeFavorit() {
        favoritBiz.delete(bue.getBookitem_idx());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("bue", bue);
        outState.putParcelable("bookImg", bookImg);
        outState.putSerializable("opac", opac);
    }

    @Override
    public void setReservationResult(ReservationMessage message) {
        String m;
        if("-1".equals(message.getCode())) {
            m = "卡无效，请重新绑定卡";
        }else if("-2".equals(message.getCode())){
            m = "卡密码错误，请重新绑定卡";
        }else if("lib_not_support".equals(message.getCode())){
            m = this.getResources().getString(R.string.lib_not_support);
        }else{
            m = message.getMessage();
        }

        String messageStr = message.isState()?"预借成功":("预借失败"+" 信息："+ m);
        if(message.isState()){
            advance.setOnClickListener(null);
            advance.setText("已预借");
            findViewById(R.id.addFavorit).setVisibility(View.GONE);
            findViewById(R.id.line).setVisibility(View.GONE);
        }
        advance.setClickable(true);
        Toast.makeText(this,messageStr,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setInReservationResult(ReservationMessage message) {
        String m;
        if("-1".equals(message.getCode())) {
            m = "卡无效，请重新绑定卡";
        }else if("-2".equals(message.getCode())){
            m = "卡密码错误，请重新绑定卡";
        }else if("lib_not_support".equals(message.getCode())){
            m = this.getResources().getString(R.string.lib_not_support);
        }else{
            m = message.getMessage();
        }
        String messageStr = (message.isState()?"取消预借成功":"取消预借失败")+" 信息："+ m;

        Toast.makeText(this,messageStr,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setFail(BookInfo_State s) {
        if(s == BookInfo_State.AUTH_ERROR){
            logout();
        }else if(s == NETOWRK_ERROR){
            Toast.makeText(this,"网络连接失败",Toast.LENGTH_SHORT).show();
        }
        advance.setClickable(true);
    }

    @Override
    public void setBookState(int type) {
        if(type == 0){
            findViewById(R.id.functionVG).setVisibility(View.GONE);
            findViewById(R.id.unAvaliable).setVisibility(View.VISIBLE);
            removeFavorit();
        }
    }
}
