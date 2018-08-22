package com.ssitcloud.app_reader.view.viewInterface;

import com.ssitcloud.app_reader.entity.BibliosPageEntity;

import java.util.List;

/**
 * Created by LXP on 2017/3/21.
 * 续借视图
 */

public interface RenewViewI {

    /**
     *设置数目列表
     * @param data
     */
    void setBookView(List<BibliosPageEntity> data);

    /**
     *
     * @param data
     * @param code -1未登陆，-2网络异常,-3卡已经失效,-4图书馆暂时不支持服务
     *             1 未绑定卡
     */
    void setFailView(List<BibliosPageEntity> data,int code);

    void showWait();

    void hideWait();

    /**
     * 显示续借等待界面
     */
    void showRenewWait();

    /**
     * 隐藏续借等待界面
     */
    void hideRenewWait();

    /**
     * 设置续借成功界面
     * @param message
     * @param returnDate 还书日期
     */
    void setRenewSuccess(String message,String returnDate);
    /**
     *
     * @param message
     * @param code -1未登陆，-2网络异常,-3卡已经失效
     *             0正常失败（就是续借失败的意思） 1 未绑定卡
     */
    void setRenewFail(String message,int code);

    /**
     *
     * @param sum
     */
    void setMaxLoan(int sum);

    /**
     *
     * @param sum
     */
    void setSurplusLoan(int sum);

}
