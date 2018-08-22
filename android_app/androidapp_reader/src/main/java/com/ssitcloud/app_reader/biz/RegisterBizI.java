package com.ssitcloud.app_reader.biz;

import com.ssitcloud.app_reader.entity.ReaderInfoEntity;

import java.net.SocketException;

/**
 * Created by LXP on 2017/3/6.
 */

public interface RegisterBizI {

    /**
     * 发送注册邮箱验证码
     * @param mail
     * @return
     * @throws SocketException
     */
    boolean seneRegisterMailCode(String mail) throws SocketException;

    /**
     * 发送注册手机验证码
     * @param mobile
     * @return
     * @throws SocketException
     */
    boolean seneRegisterMobileCode(String mobile) throws SocketException;

    /**
     * 注册用户
     * @param readerInfo
     * @return 服务器提示信息，success成功，mail_repeat邮箱重复，mobile_repeat手机号重复，idcard_repeat身份证重复，loginname_repeat用户名重复，-500或null服务器错误
     */
    String register(ReaderInfoEntity readerInfo,String vcode);
}
