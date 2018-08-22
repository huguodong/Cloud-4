package com.ssitcloud.app_operator.biz;

import io.reactivex.Observable;

/**
 * Created by LXP on 2017/8/17.
 * *
 */

public interface PermissionBiz {

    boolean checkPermission(String perm);

    Observable<Boolean> getPermission(String perm);
}
