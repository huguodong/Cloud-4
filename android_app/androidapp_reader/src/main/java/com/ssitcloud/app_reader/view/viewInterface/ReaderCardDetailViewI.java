package com.ssitcloud.app_reader.view.viewInterface;

import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;

/**
 * Created by LXP on 2017/3/23.
 * 读者卡详情视图接口
 */

public interface ReaderCardDetailViewI {
    /**
     * 设置视图
     * @param card
     */
    void setView(ReaderCardDbEntity card);

    /**
     * @param code -1未登录，-2网络连接失败 ,
     *             0操作成功
     *             1操作失败
     * @param orderCode 操作代码 1 解绑 2设置首选卡
     */
    void setMessageView(int code,int orderCode);
    /**
     *
     * @return
     */
    ReaderCardDbEntity getCard();

    void showWait();

    void hideWait();
}
