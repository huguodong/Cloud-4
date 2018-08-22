package com.ssitcloud.app_operator.biz;

import com.ssitcloud.app_operator.entity.LibraryEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by LXP on 2017/8/25.
 *
 */

public interface LibraryBiz {

    Observable<List<LibraryEntity>> getSlaveLibrary(Integer mastLibIdx);
}
