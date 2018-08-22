package com.ssitcloud.app_reader.biz;

import com.ssitcloud.app_reader.db.entity.FavoritDbEntity;

import java.util.List;

/**
 * Created by LXP on 2017/4/12.
 *
 */

public interface FavoritBizI {
    List<FavoritDbEntity> selectAll();

    FavoritDbEntity select(Integer idx);

    void insert(FavoritDbEntity entity);

    void delete(Integer idx);

    void deleteAll();
}
