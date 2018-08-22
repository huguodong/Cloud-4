package com.ssitcloud.app_reader.view.viewInterface;

import com.ssitcloud.app_reader.entity.AppOPACEntity;
import com.ssitcloud.app_reader.entity.BookUnionEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/4/12.
 *
 */

public interface FavoritViewI {
    /**
     * 设置视图
     * @param viewData
     */
    void setView(List<FavoritViewEntity> viewData);

    class FavoritViewEntity{
        public AppOPACEntity opac;
        public List<BookUnionEntity> bookS;
    }
}
