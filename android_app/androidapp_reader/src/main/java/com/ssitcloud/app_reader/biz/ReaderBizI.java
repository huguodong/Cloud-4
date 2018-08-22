package com.ssitcloud.app_reader.biz;

import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.db.entity.LoginInfoDbEntity;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.entity.ReaderInfoEntity;

import java.net.SocketException;

/**
 * Created by LXP on 2017/3/27.
 * 读者信息业务
 */

public interface ReaderBizI {
    int success = 0;//成功
    int noUser = 2;//找不到用户
    int vcodeTimeOut = 3;//找不到用户
    int vcodeError = 4;//找不到用户
    int unKnowError= -500;//未知错误
    /**
     * 找回密码
     * @param reader 读者信息
     * @param vcode 验证码，若为null则为发送验证邮件
     * @return 返回码success or noUser or vcodeTimeOut or vcodeError
     */
    int forgetPwd(ReaderInfoEntity reader,String vcode) throws SocketException;

    /**
     * 修改密码
     * @param idx 用户idx
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return 成功返回true
     */
    boolean changePwd(Integer idx, String oldPwd, String newPwd) throws SocketException,AuthException;

    /**
     * 修改个人信息
     * @param readerInfo 新的读者信息
     * @return 成功返回true
     * @throws SocketException 网络异常
     * @throws AuthException 用户身份信息过期
     */
    boolean modifyInfo(ReaderInfoEntity readerInfo) throws SocketException,AuthException;

    boolean readerAuth(LoginInfoDbEntity loginInfo, ReaderCardDbEntity card,String barcode) throws SocketException,AuthException;
}
