package com.ssitcloud.app_reader.biz;

import com.ssitcloud.app_reader.entity.AppOPACEntity;
import com.ssitcloud.app_reader.entity.PublicKeyEntity;

import java.util.Set;

/**
 * Created by LXP on 2017/3/29.
 * 配置业务接口
 */

public interface ConfigBizI {

    void saveAuthCode(String value);

    String getAuthCode();

    /**
     * 存入设备检索偏好
     * @param opac 设备信息,若传入null，则为删除设备检索偏好
     */
    void savePreferDevice(AppOPACEntity opac);

    /**
     * 获取设备偏好
     * @return 若存在偏好设备返回；否则返回null
     */
    AppOPACEntity getPreferDevice();

    /**
     * 删除所有的偏好数据
     */
    void deletePreferData();

    /**
     * 保存公钥
     * @param pukData 公钥数据
     */
    void updatePuk(PublicKeyEntity pukData);

    /**
     * 获取公钥，如不存在返回null
     * @return 公钥；若不存在返回null
     */
    PublicKeyEntity getPuk();

    /**
     * 设置还书提醒期限
     * @param day 还剩多少天
     */
    void setRemindTime(int day);

    /**
     * 获取还书提醒期限
     * @return 天数
     */
    int getRemindTime();

    /**
     * 获取消息推送状态
     * @return true为打开消息推送开关，false为关闭消息推送开关
     */
    boolean getMessagePushState();

    /**
     * 设置消息推送开关
     * @param state true为打开消息推送开关，false为关闭消息推送开关
     */
    void setMessagePushState(boolean state);

    /**
     * 获取未读通知条数
     * @return 未读通知条数
     */
    int getElecNoticeCount();

    /**
     * 设置未读通知条数
     * @param count 未读通知条数
     */
    void setElecNoticeCount(int count);

    /**
     * 设置已经提醒的图书列表
     * @param bookBarcodes 已经提醒的图书列表
     */
    void setRemindBookBarcode(Set<String> bookBarcodes);

    /**
     * 获取已经提醒的图书条码
     * @return 图书条码
     */
    Set<String> getRemindBookBarcode();
}
