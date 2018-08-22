package com.ssitcloud.app_reader.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ssitcloud.app_reader.common.entity.HttpByteResponce;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;

/**
 * Created by LXP on 2017/4/10.
 *
 */

public class ImgHandler implements Runnable{

    private SoftReference<ImageView> viewRef;
    private String imgUrl;
    private int compress_size = -1;
    private RequestImageSuccess requestImageSuccess;

    public ImgHandler(ImageView view, String imgUrl){
        if(view == null || imgUrl == null){
            throw new IllegalArgumentException("viewRef and imgUlr must not null");
        }
        this.viewRef = new SoftReference<>(view);
        this.imgUrl = imgUrl;
    }

    @Override
    public void run() {
        AsyncTask<Void,Void,Bitmap> task = new AsyncTask<Void, Void, Bitmap>() {
            private volatile int state = 0;
            @Override
            protected Bitmap doInBackground(Void... params) {
                HttpByteResponce hbr = HttpClientUtil.dogetToByte(imgUrl,null);
                if(hbr.getState() == 200){
                    byte[] data = hbr.getBody();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        if(compress_size != -1){
                            bitmap = compressImage(bitmap,compress_size);
                        }
                        state = 1;
                        return bitmap;
                    }catch (Exception e){
                        Log.i("ImgHandler",imgUrl+" found img fail");
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (state == 1 && bitmap != null) {
                    ImageView v = viewRef.get();
                    if (v != null) {
                        v.setVisibility(View.VISIBLE);
                        v.setImageBitmap(bitmap);
                    }
                    if(requestImageSuccess != null){
                        requestImageSuccess.success(bitmap);
                    }
                }

            }
        };
        task.execute();
    }

    private Bitmap compressImage(Bitmap image,int size/*kb*/) throws IOException{
        ByteArrayOutputStream baos = null;
        ByteArrayInputStream isBm = null;

        baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 80;
        while (baos.toByteArray().length / 1024 > size && options>-1) {//循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 20;//每次都减少10
        }

        isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;

    }

    public int getCompress_size() {
        return compress_size;
    }

    public void setCompress_size(int compress_size) {
        this.compress_size = compress_size;
    }

    public RequestImageSuccess getRequestImageSuccess() {
        return requestImageSuccess;
    }

    public void setRequestImageSuccess(RequestImageSuccess requestImageSuccess) {
        this.requestImageSuccess = requestImageSuccess;
    }

    public interface RequestImageSuccess{
        void success(Bitmap bitmap);
    }
}