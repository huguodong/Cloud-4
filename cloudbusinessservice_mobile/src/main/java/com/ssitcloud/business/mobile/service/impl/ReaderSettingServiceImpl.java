package com.ssitcloud.business.mobile.service.impl;

import com.ssitcloud.business.mobile.common.util.StringUtils;
import com.ssitcloud.mobile.entity.PasswordIdentifiedcodeEntity;
import com.ssitcloud.mobile.entity.ReaderInfoEntity;
import com.ssitcloud.mobile.entity.RegisterIdentifiedcodeEntity;
import com.ssitcloud.business.mobile.service.*;
import com.ssitcloud.common.entity.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LXP on 2017/8/14.
 */
@Service
public class ReaderSettingServiceImpl implements ReaderSettingServiceI {
    @Autowired
    private ReaderInfoServiceI readerInfoService;

    @Autowired
    private PasswordIdentifiedcodeService passwordIdcodeService;

    @Autowired
    private RegisterIdentifiedcodeService registerIdentifiedcodeService;

    @Autowired
    private MobileMessageServiceI mobileMessageService;

    @Override
    public ResultEntity updateIdcard(Integer readerIdx, String idcard) {
        ReaderInfoEntity param = new ReaderInfoEntity();
        param.setReader_idx(readerIdx);
        param.setId_card(idcard);
        return readerInfoService.updateReaderInfoEntity(param, true);
    }

    @Override
    public ResultEntity updateMail(Integer readerIdx, String mail) {
        ReaderInfoEntity param = new ReaderInfoEntity();
        param.setReader_idx(readerIdx);
        param.setEmail(mail);
        return readerInfoService.updateReaderInfoEntity(param, true);
    }

    @Override
    public ResultEntity sendOriginalVcode(Integer readerIdx) {
        ReaderInfoEntity readerInfo = readerInfoService.selectReaderInfoByPk(readerIdx);
        if (readerInfo == null || StringUtils.isEmpty(readerInfo.getMobile())) {
            return getErrorResultEntity(null, null);
        }

        String vcode = StringUtils.creatcNumberVcode(4);//生成验证码
        PasswordIdentifiedcodeEntity entity = new PasswordIdentifiedcodeEntity();
        entity.setIdentifying_code(vcode);
        entity.setReader_idx(readerIdx);
        boolean insertResult = passwordIdcodeService.insert(entity);
        if(insertResult){
            boolean b = mobileMessageService.sendPasswordMessage(readerInfo.getMobile(), vcode);
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setState(b);
            return resultEntity;
        }

        return getErrorResultEntity(null, null);
    }

    @Override
    public ResultEntity sendNewVcode(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return getErrorResultEntity(null, null);
        }
        String vcode = StringUtils.creatcNumberVcode(4);//生成验证码
        RegisterIdentifiedcodeEntity entity = new RegisterIdentifiedcodeEntity();
        entity.setEmail_address(mobile);
        entity.setIdentifying_code(vcode);
        boolean insertResult = registerIdentifiedcodeService.insert(entity);
        if(insertResult){
            boolean b = mobileMessageService.sendPasswordMessage(mobile, vcode);
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setState(b);
            return resultEntity;
        }
        return getErrorResultEntity(null, null);
    }

    @Override
    public ResultEntity setNewMobile(Integer readerIdx, String mobile, String vcode, String newVcode){
        final long timeout = 10*60_000;

        PasswordIdentifiedcodeEntity vSelectCode = passwordIdcodeService.selectCode(readerIdx, timeout);
        if(vSelectCode == null || !vcode.equalsIgnoreCase(vSelectCode.getIdentifying_code())){
            return getErrorResultEntity("原验证码不正确","original_vcode_error");
        }
        RegisterIdentifiedcodeEntity newSelectcode = registerIdentifiedcodeService.selectCode(mobile, timeout);
        if(newSelectcode == null || !newVcode.equalsIgnoreCase(newSelectcode.getIdentifying_code())){
            return getErrorResultEntity("新手机验证码不正确","new_vcode_error");
        }

        ReaderInfoEntity param = new ReaderInfoEntity();
        param.setReader_idx(readerIdx);
        param.setMobile(mobile);
        ResultEntity resultEntity = readerInfoService.updateReaderInfoEntity(param, true);
        if(resultEntity.getState()){
            passwordIdcodeService.deleteByPkAsync(readerIdx);
            registerIdentifiedcodeService.deleteByPkAsync(mobile);
        }

        return resultEntity;
    }

    private ResultEntity getErrorResultEntity(String message, String retMessage) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setState(false);
        resultEntity.setMessage(message);
        resultEntity.setRetMessage(retMessage);
        return resultEntity;
    }
}
