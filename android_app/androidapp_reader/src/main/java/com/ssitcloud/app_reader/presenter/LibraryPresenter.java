package com.ssitcloud.app_reader.presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ssitcloud.app_reader.biz.LibraryBizI;
import com.ssitcloud.app_reader.biz.impl.LibraryBizImpl;
import com.ssitcloud.app_reader.entity.LibraryEntity;
import com.ssitcloud.app_reader.view.viewInterface.LibraryListViewI;

import java.lang.ref.SoftReference;
import java.net.SocketException;
import java.util.List;

/**
 * Created by LXP on 2017/3/10.
 *
 */

public class LibraryPresenter {
    private final String TAG="LibraryPresenter";
    private Context context;
    private LibraryBizI libraryBzi;
    private SoftReference<LibraryListViewI> libraryListView;

    public LibraryPresenter(LibraryListViewI libraryListView, Context context){
        this.context = context.getApplicationContext();
        libraryBzi = new LibraryBizImpl(this.context);
        this.libraryListView = new SoftReference<>(libraryListView);
    }

    /**
     * 加载图书馆数据
     */
    public void loadData(){
        AsyncTask<Void,Void,List<LibraryEntity>> task = new AsyncTask<Void, Void, List<LibraryEntity>>() {
            @Override
            protected List<LibraryEntity> doInBackground(Void... params) {
                try {
                    List<LibraryEntity> libraryEntities = libraryBzi.obtainLibrary();
                    return libraryEntities;
                } catch (SocketException e) {
                    Log.i(TAG,"获取网络数据失败");
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<LibraryEntity> libraryEntities) {
                LibraryListViewI v = libraryListView.get();
                if(libraryEntities == null){
                    if(v != null){
                        v.setMessageView(-2);
                    }
                }else {
                    if (v != null) {
                        v.setView(libraryEntities);
                    }
                }
            }
        };
        task.execute((Void[]) null);
    }
}
