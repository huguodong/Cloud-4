package com.ssitcloud.app_operator.view.viewInterface;

import com.ssitcloud.app_operator.common.entity.ResultEntity;

/**
 * 创建日期：2017/3/23 18:11
 * @author shuangjunjie
 */

public interface UpdateOperatorInfoViewI {

    /**
     * 更新operator信息成功
     * @param result
     */
    void updateSuccess(ResultEntity result);

    /**
     * 更新operator信息失败
     * @param message
     */
    void updateFail(String message);

    /**
     * 显示进度条
     */
    void showWait();

    /**
     * 隐藏进度条
     */
    void hideWait();

}
