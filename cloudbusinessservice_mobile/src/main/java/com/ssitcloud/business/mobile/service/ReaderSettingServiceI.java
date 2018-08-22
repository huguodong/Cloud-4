package com.ssitcloud.business.mobile.service;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * Created by LXP on 2017/8/14.
 */

public interface ReaderSettingServiceI {

    ResultEntity updateIdcard(Integer readerIdx, String idcard);

    ResultEntity updateMail(Integer readerIdx, String mail);

    ResultEntity sendOriginalVcode(Integer readerIdx);

    ResultEntity sendNewVcode(String mobile);

    ResultEntity setNewMobile(Integer readerIdx, String mobile, String vcode, String newVcode);
}
