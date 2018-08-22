package com.ssitcloud.app_operator.biz;

import com.ssitcloud.app_operator.common.entity.ResultEntity;

import java.net.SocketException;
import java.util.Map;

/**
 * 创建日期：2017/3/21 15:44
 * @author shuangjunjie
 */

public interface ForgetPwdBizI {

    /**
     * 发送找回密码验证码
     */
    ResultEntity sendFoundVcodeVcode(String mobile) throws SocketException;

    /**
     * 通过验证码找回密码
     */
    ResultEntity changePwdByVocde(String mobile, String vcode, String pwd) throws SocketException;
}
