package com.ssitcloud.app_operator.biz;

import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_operator.db.entity.MessageRemindDbEntity;
import com.ssitcloud.app_operator.entity.AppSettingEntity;
import com.ssitcloud.app_operator.entity.OperatorEntity;

import java.net.SocketException;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * 创建日期：2017/3/16 11:46
 * @author shuangjunjie
 */

public interface LoginBizI {

    ResultEntity login(Map<String,Object> map) throws SocketException;

    /**
     * 获取appsetting设置并存储到数据库中
     * @param lib_idx 馆idx
     */
    Observable<List<AppSettingEntity>> getAppSetting(Integer lib_idx);

    List<AppSettingEntity> getAppSettingByDb(Integer lib_idx);

    void logout();

    LoginInfoDbEntity getLoginInfo();

    /**
     * 若返回OperatorEntity == null，说明登录失败
     */
    Observable<OperatorEntity> loginCheck();
}
