package com.ssitcloud.app_reader.biz;

import com.ssitcloud.app_reader.common.entity.ResultEntity;
import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.entity.ReaderInfoEntity;

import java.net.SocketException;

/**
 * Created by LXP on 2017/3/7.
 *
 */

public interface LoginBizI {
    static enum login_state{SUCCESS,FAIL,NOTWORK_ERROR}

    /**
     * 登陆并更新数据库
     * @param readerInfo
     * @return 登陆状态数据，若成功，state值应true，且result(ReaderInfoEntity.class)中包含用户数据（不含密码）
     * @throws SocketException
     */
    ResultEntity login(ReaderInfoEntity readerInfo) throws SocketException;

    void logout();

    /**
     * 已经登陆返回用户的idx，没有登陆则返回null
     * @return
     */
    Integer isLogin();

    /**
     * 已经登陆成功返回数据库登陆信息，否则返回null
     * @return
     */
    LoginInfoDbEntity getLoginReturnData();

    /**
     * 获取用户信息，并更新数据到本地缓存
     * @param reader_idx 用户主键
     * @return
     * @throws SocketException
     * @throws AuthException
     */
    ReaderInfoEntity obtainReader(Integer reader_idx) throws SocketException, AuthException;
}
