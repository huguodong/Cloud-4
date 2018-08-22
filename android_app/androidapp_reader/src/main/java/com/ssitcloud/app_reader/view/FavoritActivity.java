package com.ssitcloud.app_reader.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ssitcloud.app_reader.R;
import com.ssitcloud.app_reader.biz.FavoritBizI;
import com.ssitcloud.app_reader.biz.impl.FavoritBizImpl;
import com.ssitcloud.app_reader.common.BaseActivity;
import com.ssitcloud.app_reader.common.utils.DisplayUtil;
import com.ssitcloud.app_reader.common.utils.ImgHandler;
import com.ssitcloud.app_reader.common.utils.StringUtils;
import com.ssitcloud.app_reader.entity.AppOPACEntity;
import com.ssitcloud.app_reader.entity.BookUnionEntity;
import com.ssitcloud.app_reader.presenter.FavoritPresenter;
import com.ssitcloud.app_reader.view.viewInterface.FavoritViewI;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/4/12.
 *
 */

public class FavoritActivity extends BaseActivity implements FavoritViewI {
    private FavoritPresenter favoritPresenter;

    private LinearLayout contentVg;

    private Map<String,SoftReference<Bitmap>> bitmapCache = new HashMap<>(20,0.9f);

    private List<CheckBox> deviceCheckBoxs = new LinkedList<>();
    private List<CheckBox> bookCheckBoxs = new LinkedList<>();
    private List<BookUnionEntity> selectBook = new LinkedList<>();//选中生成二维码的书籍

    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);

        favoritPresenter = new FavoritPresenter(this,this);

        contentVg = (LinearLayout) findViewById(R.id.content);

        View back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoritActivity.this.finish();
            }
        });

        View submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> barcodes = new ArrayList<>(15);
                selectBook.clear();
                for (CheckBox bookCheckBox : bookCheckBoxs) {
                    if(bookCheckBox.isChecked()){
                        Map<String,Object> d = (Map<String, Object>) bookCheckBox.getTag();
                        BookUnionEntity book = (BookUnionEntity) d.get("bue");
                        if(book != null){
                            barcodes.add(book.getBook_barcode());
                            selectBook.add(book);
                        }
                    }
                }
                if(!barcodes.isEmpty()){
                    Intent i = new Intent(FavoritActivity.this,BorrowBarcodeActivity.class);
                    i.putStringArrayListExtra("bookbarcodes",barcodes);
                    startActivityForResult(i,1);
                }else{
                    Toast.makeText(FavoritActivity.this,"请先选择需要借阅的书籍",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        favoritPresenter.loadData();
    }

    @Override
    public void setView(List<FavoritViewEntity> viewData) {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout.LayoutParams fenggeParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(this,10));
        View.OnClickListener bookItemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> d = (Map<String,Object>) v.getTag();
                if(d != null){
                    Intent i = new Intent(FavoritActivity.this,BookInfoActivity.class);
                    i.putExtra("bue", ((BookUnionEntity) d.get("bue")));
                    i.putExtra("opac",(AppOPACEntity)d.get("opac"));
                    startActivity(i);
                }
            }
        };
        View.OnClickListener deviceListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppOPACEntity opac = (AppOPACEntity) v.getTag();
                if(opac != null){
                    Intent i = new Intent(FavoritActivity.this,BookItemActivity.class);
                    i.putExtra("opac",opac);
                    startActivity(i);
                }
            }
        };
        CompoundButton.OnCheckedChangeListener deviceCBListener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!buttonView.isPressed()){
                    return ;
                }
                if(isChecked){
                    deviceSelect(buttonView);
                }else{
                    deviceUnSelect(buttonView);
                }
            }
        };
        CompoundButton.OnCheckedChangeListener bookCBListener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!buttonView.isPressed()){
                    return ;
                }
                if(isChecked){
                    bookSelect(buttonView);
                }else{
                    bookUnSelect(buttonView);
                }
            }
        };
        contentVg.removeAllViews();
        deviceCheckBoxs.clear();
        bookCheckBoxs.clear();
        for (int i = 0,z = viewData.size(); i < z; i++) {
            FavoritViewEntity favoritViewEntity = viewData.get(i);

            //主视图
            ViewGroup mainView = (ViewGroup)inflater.inflate(R.layout.sub_favorit_device,null);
            TextView deviceNmae = (TextView) mainView.findViewById(R.id.deviceName);
            deviceNmae.setText(favoritViewEntity.opac.getDevName());
            deviceNmae.setTag(favoritViewEntity.opac);
//            deviceNmae.setOnClickListener(deviceListener);
            CheckBox deviceCb = (CheckBox) mainView.findViewById(R.id.checkbox);

            deviceCb.setTag(favoritViewEntity.opac);
            deviceCb.setOnCheckedChangeListener(deviceCBListener);
            deviceCheckBoxs.add(deviceCb);

            //书视图
            for (BookUnionEntity book : favoritViewEntity.bookS) {
                View bookView = inflater.inflate(R.layout.sub_favorit_book,null);
                inflaterBookView(bookView,book);
                mainView.addView(bookView);

                Map<String,Object> d = new HashMap<>(3,1.0f);
                d.put("opac",favoritViewEntity.opac);
                d.put("bue",book);

                bookView.setTag(d);
                bookView.setOnClickListener(bookItemListener);

                CheckBox bookCb = (CheckBox) bookView.findViewById(R.id.checkBox);
                bookCb.setTag(d);
                bookCb.setOnCheckedChangeListener(bookCBListener);
                bookCheckBoxs.add(bookCb);

            }

            contentVg.addView(mainView);

            if(i<z-1) {
                LinearLayout fengge = new LinearLayout(this);
                fengge.setLayoutParams(fenggeParams);
                fengge.setBackgroundColor(Color.parseColor("#1A000000"));
                contentVg.addView(fengge);
            }
        }
    }

    /**
     * 设备按钮选中,通知其他按钮改变状态
     * @param cb
     */
    private void deviceSelect(CompoundButton cb){
        AppOPACEntity opac = (AppOPACEntity) cb.getTag();
        for (CheckBox deviceCheckBox : deviceCheckBoxs) {
            AppOPACEntity dopac = (AppOPACEntity) deviceCheckBox.getTag();
            if(!dopac.equals(opac)){
                deviceCheckBox.setChecked(false);
            }
        }
        for (CheckBox bookCheckBox : bookCheckBoxs) {
            Map<String,Object> d = (Map<String, Object>) bookCheckBox.getTag();
            AppOPACEntity bopac = (AppOPACEntity) d.get("opac");
            if(opac.equals(bopac)){
                bookCheckBox.setChecked(true);
            }else{
                bookCheckBox.setChecked(false);
            }
        }
    }
    /**
     * 设备按钮反选中,通知其他按钮改变状态
     * @param cb
     */
    private void deviceUnSelect(CompoundButton cb){
        AppOPACEntity opac = (AppOPACEntity) cb.getTag();
        for (CheckBox bookCheckBox : bookCheckBoxs) {
            Map<String,Object> d = (Map<String, Object>) bookCheckBox.getTag();
            AppOPACEntity bopac = (AppOPACEntity) d.get("opac");
            if(opac.equals(bopac)){
                bookCheckBox.setChecked(false);
            }
        }
    }

    /**
     * 图书按钮选中,通知其他按钮改变状态
     * @param cb
     */
    private void bookSelect(CompoundButton cb){
        Map<String,Object> d = (Map<String, Object>) cb.getTag();
        AppOPACEntity opac = (AppOPACEntity) d.get("opac");
        //检查全部子按钮是否已经选中
        boolean isAllCheck = true;
        for (CompoundButton bookCheckBox : bookCheckBoxs) {
            d = (Map<String, Object>) bookCheckBox.getTag();
            AppOPACEntity bopac = (AppOPACEntity) d.get("opac");
            if(opac.equals(bopac)){
                if(!bookCheckBox.isChecked()){
                    isAllCheck = false;
                }
            }else{//设置不同按钮不选中
                bookCheckBox.setChecked(false);
            }
        }
        //已经全部选中
        for (CheckBox deviceCheckBox : deviceCheckBoxs) {
            AppOPACEntity bopac = (AppOPACEntity) deviceCheckBox.getTag();
            if (bopac.equals(opac)) {
                if (isAllCheck) {
                    deviceCheckBox.setChecked(true);
                }
            } else {
                deviceCheckBox.setChecked(false);
            }
        }

    }

    /**
     * 图书按钮反选中,通知其他按钮改变状态
     * @param cb
     */
    private void bookUnSelect(CompoundButton cb){
        Map<String,Object> d = (Map<String, Object>) cb.getTag();
        AppOPACEntity opac = (AppOPACEntity) d.get("opac");

        for (CheckBox deviceCheckBox : deviceCheckBoxs) {
            AppOPACEntity bopac = (AppOPACEntity) deviceCheckBox.getTag();
            if (bopac.equals(opac)) {
                deviceCheckBox.setChecked(false);
                break;
            }
        }

    }

    private View inflaterBookView(View view,BookUnionEntity book){
        TextView title = (TextView) view.findViewById(R.id.bookTitle);
        TextView author = (TextView) view.findViewById(R.id.author);
        TextView isbn = (TextView) view.findViewById(R.id.isbn);
        TextView publish = (TextView) view.findViewById(R.id.publish);
        ImageView iv = (ImageView) view.findViewById(R.id.bookImg);

        title.setText(book.getTitle());
        author.setText(String.format("作者：%s", StringUtils.getStr(book.getAuthor(),"暂无")));
        isbn.setText(String.format("ISBN：%s",StringUtils.getStr(book.getISBN(),"暂无")));
        publish.setText(String.format("出版社：%s",StringUtils.getStr(book.getPublish(),"暂无")));

        final String imgUrl = book.getBookimage_url();
        if(imgUrl != null){
            Log.d("ImgHandler", "have img,url==>" + imgUrl);

            boolean needFoundImgOnnetwork = true;
            SoftReference<Bitmap> bitmapRef = getImgCache(imgUrl);//查看是否存在图片缓存
            if(bitmapRef != null){
                Bitmap bm = bitmapRef.get();
                if(bm != null){
                    needFoundImgOnnetwork = false;
                    iv.setImageBitmap(bm);
                    iv.setVisibility(View.VISIBLE);
                }
            }
            if(needFoundImgOnnetwork){
                Log.d("ImgHandler", "start found img to network url==>" + imgUrl);

                ImgHandler r = new ImgHandler(iv, imgUrl);
                ImgHandler.RequestImageSuccess requestImageSuccess = new ImgHandler.RequestImageSuccess() {
                    @Override
                    public void success(Bitmap bitmap) {
                        addImgCache(imgUrl,bitmap);//加入图片到缓存

                    }
                };
                r.setRequestImageSuccess(requestImageSuccess);
                r.setCompress_size(10);

                handler.post(r);
            }
        }

        return view;
    }

    private void addImgCache(String url,Bitmap bm){
        bitmapCache.put(StringUtils.getMd5(url.trim(),false), new SoftReference<>(bm));
    }

    private SoftReference<Bitmap> getImgCache(String url){
        String hash = StringUtils.getMd5(url.trim(),false);
        return bitmapCache.get(hash);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                int isOk = data.getIntExtra("isOk", 0);
                if(isOk == 1) {
                    FavoritBizI favoritBiz = new FavoritBizImpl(this);
                    for (BookUnionEntity book : selectBook) {
                        favoritBiz.delete(book.getBookitem_idx());
                    }
                }
            }
        }
    }
}
