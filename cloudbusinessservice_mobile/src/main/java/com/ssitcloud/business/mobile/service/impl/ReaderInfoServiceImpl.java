package com.ssitcloud.business.mobile.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.authentication.entity.SoxTemplateEntity;
import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.common.util.ObjectUtils;
import com.ssitcloud.business.mobile.common.util.PropertiesUtil;
import com.ssitcloud.business.mobile.common.util.StringUtils;
import com.ssitcloud.business.mobile.operationEntity.ChangeOperationLog;
import com.ssitcloud.business.mobile.operationEntity.FoundOperationLog;
import com.ssitcloud.business.mobile.operationEntity.LoginOperationLog;
import com.ssitcloud.business.mobile.operationEntity.RegisterOperationLog;
import com.ssitcloud.business.mobile.service.EmailServiceI;
import com.ssitcloud.business.mobile.service.MobileMessageServiceI;
import com.ssitcloud.business.mobile.service.OperationLogServiceI;
import com.ssitcloud.business.mobile.service.PasswordIdentifiedcodeService;
import com.ssitcloud.business.mobile.service.PasswordServiceI;
import com.ssitcloud.business.mobile.service.ReaderAuthServiceI;
import com.ssitcloud.business.mobile.service.ReaderInfoServiceI;
import com.ssitcloud.business.mobile.service.RegisterIdentifiedcodeService;
import com.ssitcloud.business.mobile.service.SoxTemplateServiceI;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.PasswordIdentifiedcodeEntity;
import com.ssitcloud.mobile.entity.ReaderInfoEntity;
import com.ssitcloud.mobile.entity.RegisterIdentifiedcodeEntity;

import net.sf.json.util.JSONUtils;

/**
 * @author LXP
 * @version 创建时间：2017年2月24日 下午4:33:31
 */
@Service
public class ReaderInfoServiceImpl implements ReaderInfoServiceI {

    private final int sox_tpl_id;

    @Autowired
    private SoxTemplateServiceI soxTemplateService;

    @Autowired
    private PasswordServiceI passwordService;

    @Autowired
    private ReaderAuthServiceI readerAuthService;

    @Autowired
    private PasswordIdentifiedcodeService passwordIdentifiedcodeService;

    @Autowired
    private EmailServiceI emailService;

    @Autowired
    private MobileMessageServiceI mobileMessageService;

    private final int passwordCodeTimeout = 10 * 60_000;//找回密码验证码失效时间
    private final int emailCodeTimeout = 10 * 60_000;//注册邮箱验证码失效时间
    // 定义错误码
    private final int NETWORL_ERROR = -2;
    private final int USERNAME_REPEAT = -1;
    private final int SUCCESS = 1;

    private final String URL_INSERT = "insertReaderInfoEntity";
    private final String URL_UPDATE = "updateReaderInfoEntity";
    // private final String URL_DELETE = "deleteReaderInfoEntity";
    private final String URL_SELECT = "selectReaderInfoEntity";
    private final String URL_SELECT_S = "selectReaderInfoEntitys";

    @Resource(name = "requestURL")
    private RequestURLListEntity requestURLEntity;
    private static final String charset = "utf-8";

    @Autowired
    private OperationLogServiceI operationLogService;

    @Autowired
    private RegisterIdentifiedcodeService registerCodeService;


    public ReaderInfoServiceImpl() {
        String s = PropertiesUtil.getValue("sox_tpl_id");
        sox_tpl_id = Integer.valueOf(s);
    }

    @Override
    public int insertReaderInfoEntity(String readerInfoEntityJson) {
        String url = requestURLEntity.getRequestURL(URL_INSERT);
        Map<String, String> map = new HashMap<>(1);
        map.put("json", readerInfoEntityJson);
        String resultJson = HttpClientUtil.doPost(url, map, charset);
        if (resultJson == null || !JSONUtils.mayBeJSON(resultJson)) {
            return NETWORL_ERROR;
        }
        try {
            ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
            return resultEntity != null ? resultEntity.getState() ? SUCCESS : USERNAME_REPEAT : NETWORL_ERROR;
        } catch (Exception e) {
            LogUtils.error("json==>" + resultJson, e);
            return USERNAME_REPEAT;
        }

    }

    @Override
    public ResultEntity selectReaderInfoS(ReaderInfoEntity readerInfoEntity) {
        if (readerInfoEntity == null) {
            throw new IllegalArgumentException("readerInfoEntity is null");
        }
        ResultEntity result = new ResultEntity();
        String url = requestURLEntity.getRequestURL(URL_SELECT_S);
        Map<String, String> map = new HashMap<>(1);
        map.put("json", JsonUtils.toJson(readerInfoEntity));
        String resultJson = HttpClientUtil.doPost(url, map, charset);
        try {
            ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
            if (resultJson == null || !resultEntity.getState()) {
                LogUtils.error(ReaderInfoServiceImpl.class + " 从远程服务器获取数据失败method->selectReaderInfoS");
                result.setValue(false, "查询失败");
            }
            result = resultEntity;
        } catch (Exception e) {
            LogUtils.error(ReaderInfoServiceImpl.class + "从远程服务器获取数据失败method->selectReaderInfoS");
            result.setValue(false, "查询失败");
        }
        return result;
    }

    private List<ReaderInfoEntity> selectReaderInfoSToReaders(ReaderInfoEntity readerInfoEntity) {
        ResultEntity resultEntity = this.selectReaderInfoS(readerInfoEntity);
        try {
            if (resultEntity.getState()) {
                List list = (List) resultEntity.getResult();
                List<ReaderInfoEntity> readers = new ArrayList<>(list.size());
                for (Object object : list) {
                    ReaderInfoEntity reader = JsonUtils.fromJson(JsonUtils.toJson(object), ReaderInfoEntity.class);
                    readers.add(reader);
                }
                return readers;
            } else {
                return new ArrayList<>(0);
            }
        } catch (Exception e) {
            return new ArrayList<>(0);
        }
    }

    @Override
    public boolean isEsistsReaderInfo(ReaderInfoEntity readerInfoEntity) {
        return isEsistsReaderInfo(readerInfoEntity, null);
    }

    @Override
    public boolean isEsistsReaderInfo(ReaderInfoEntity readerInfoEntity, Integer idx) {
        if (readerInfoEntity == null) {
            throw new IllegalArgumentException("readerInfoEntity is null");
        }
        ResultEntity resultEntity = this.selectReaderInfoS(readerInfoEntity);
        if (resultEntity == null || !resultEntity.getState()) {
            LogUtils.error(ReaderInfoServiceImpl.class + "无法正确的获取数据method->isEsistsReaderInfo");
            throw new RuntimeException("无法正确的获取数据");
        }
        if (resultEntity.getResult() == null) {
            return false;
        } else if (((List) resultEntity.getResult()).isEmpty()) {
            return false;
        } else if (idx != null) {
            try {
                String tempJson = JsonUtils.toJson(((List) resultEntity.getResult()).get(0));
                ReaderInfoEntity r = JsonUtils.fromJson(tempJson, ReaderInfoEntity.class);
                if (idx.equals(r.getReader_idx())) {
                    return false;
                }
            } catch (Exception e) {
                LogUtils.debug("json转换成ReaderInfoEntity对象出错");
            }
        }

        return true;
    }

    @Override
    public ResultEntity register(ReaderInfoEntity readerInfoEntity, String vcode) {
        ResultEntity resultEntity = new ResultEntity();
        readerInfoEntity.setSox_tpl_id(sox_tpl_id);//强制设置模板id
        // 获取模板
//		Integer sox_tpl_id = readerInfoEntity.getSox_tpl_id();
//		if (sox_tpl_id == null) {
//			resultEntity.setValue(false, "json数据格式不合格,code 2-1");
//			return resultEntity;
//		}
//		// 验证模板合法性
//		SoxTemplateEntity soxTemplateEntity = soxTemplateService.getSoxTemplateEntityById(sox_tpl_id);
//		if (soxTemplateEntity == null) {
//			LogUtils.error("鉴权表库sox_template没有设置模板id==>"+sox_tpl_id+"的记录");
//			resultEntity.setValue(false, "json数据格式不合格,code 2-2");
//			return resultEntity;
//		}
        // 验证数据合法性------------------------------------------------------------------------
        // 验证密码
//		Integer soxPassword_length = soxTemplateEntity.getPassword_length();// 模板设置的最小密码位数
        Integer soxPassword_length = null;// 模板设置的最小密码位数
        int pwdMinLength = soxPassword_length != null ? soxPassword_length : 0;
        String pwd = readerInfoEntity.getReader_pwd();
        // 验证是否设置了密码，密码是否大于模板设置长度
        if (pwd == null || pwd.length() < pwdMinLength) {
            resultEntity.setValue(false, "密码长度必须大于" + pwdMinLength);
            resultEntity.setRetMessage("pwd_length:" + pwdMinLength);
            return resultEntity;
        }
        // 检查其他项目是否合法
        String checkResult = checkIsLdgal(readerInfoEntity);
        if (checkResult != null) {
            resultEntity.setValue(false, checkResult);
            return resultEntity;
        }
        // 手机，身份证，电子邮件是否重复
        try {
            // mail
            ReaderInfoEntity r = new ReaderInfoEntity();
            if (!StringUtils.isEmpty(readerInfoEntity.getEmail())) {
                r.setEmail(readerInfoEntity.getEmail());
                if (this.isEsistsReaderInfo(r)) {
                    resultEntity.setValue(false, "电子邮箱重复");
                    resultEntity.setRetMessage("mail_repeat");
                    return resultEntity;
                }
            } else {
                readerInfoEntity.setEmail(null);
            }
            // phone
            r.setEmail(null);
            r.setMobile(readerInfoEntity.getMobile());
            if (this.isEsistsReaderInfo(r)) {
                resultEntity.setValue(false, "手机号码重复");
                resultEntity.setRetMessage("mobile_repeat");
                return resultEntity;
            }
            // id card
            if (!StringUtils.isEmpty(readerInfoEntity.getId_card())) {
                r.setEmail(null);
                r.setMobile(null);
                r.setId_card(readerInfoEntity.getId_card());
                if (this.isEsistsReaderInfo(r)) {
                    resultEntity.setValue(false, "身份证号码重复");
                    resultEntity.setRetMessage("idcard_repeat");
                    return resultEntity;
                }
            } else {
                readerInfoEntity.setId_card(null);
            }
        } catch (Exception e) {
            resultEntity.setValue(false, "unKnow error 1");
            resultEntity.setRetMessage("-500");
            return resultEntity;
        }
        //检查邮箱验证码
        RegisterIdentifiedcodeEntity selectCode = registerCodeService.selectCode(readerInfoEntity.getMobile(), emailCodeTimeout);
        if (selectCode == null) {
            resultEntity.setState(false);
            resultEntity.setRetMessage("mobile_code_invalid");
            return resultEntity;
        }
        if (!vcode.equalsIgnoreCase(selectCode.getIdentifying_code())) {
            resultEntity.setState(false);
            resultEntity.setRetMessage("mobile_code_error");
            return resultEntity;
        }
        // 检查完成--------------------------------------------------------------------------
        // 强制设置其他项目
        readerInfoEntity.setIsLock(0);
        readerInfoEntity.setIsLogged(0);
        readerInfoEntity.setLogin_fail_times(0);
        readerInfoEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        // 加密密码
        String encryption = passwordService.encryption(readerInfoEntity.getReader_pwd());
        if (encryption == null) {
            resultEntity.setValue(false, "unKnow error 2");
            return resultEntity;
        }
        readerInfoEntity.setReader_pwd(encryption);
        // 发送插入请求
        int result = this.insertReaderInfoEntity(JsonUtils.toJson(readerInfoEntity));

        if (result == 1) {
            resultEntity.setValue(true, "注册成功");
            resultEntity.setRetMessage("success");
            // 写入日志
            RegisterOperationLog operationLog = new RegisterOperationLog();
            operationLog.setLogin_name(readerInfoEntity.getLogin_name());
            operationLog.setOperation_result(true);
            operationLogService.addOperationLog(operationLog);
            //销毁注册验证码
            registerCodeService.deleteByPkAsync(readerInfoEntity.getMobile());
        } else if (result == USERNAME_REPEAT) {
            resultEntity.setValue(false, "用户名重复了");
            resultEntity.setRetMessage("loginname_repeat");
            // 写入日志
            RegisterOperationLog operationLog = new RegisterOperationLog();
            operationLog.setLogin_name(readerInfoEntity.getLogin_name());
            operationLog.setOperation_result(false);
            operationLogService.addOperationLog(operationLog);
        } else if (result == NETWORL_ERROR) {
            resultEntity.setValue(false, "unKnow error 3");
            resultEntity.setRetMessage("-500");
            // 写入日志
            RegisterOperationLog operationLog = new RegisterOperationLog();
            operationLog.setLogin_name(readerInfoEntity.getLogin_name());
            operationLog.setOperation_result(false);
            operationLogService.addOperationLog(operationLog);
        } else {
            resultEntity.setValue(false, "unKnow error -_-");
            resultEntity.setRetMessage("-500");
            // 写入日志
            RegisterOperationLog operationLog = new RegisterOperationLog();
            operationLog.setLogin_name(readerInfoEntity.getLogin_name());
            operationLog.setOperation_result(false);
            operationLogService.addOperationLog(operationLog);
        }

        return resultEntity;
    }

    @Override
    public ResultEntity sendRegisterMailCode(String mail) {
        ResultEntity resultEntity = new ResultEntity();

        RegisterIdentifiedcodeEntity entity = new RegisterIdentifiedcodeEntity();
        entity.setEmail_address(mail);
        String vcode = StringUtils.creatcVcode(6);
        entity.setIdentifying_code(vcode);
        boolean insertCode = registerCodeService.insert(entity);
        if (insertCode) {
            String content = "您的重置验证码为：<strong>" + vcode + "</strong><br/>" + "请在"
                    + (passwordCodeTimeout / 60_000) + "分钟内输入，验证码忽略大小写。";
            ;
            boolean sendRegisterEmail = emailService.sendRegisterEmail(mail, content);
            if (sendRegisterEmail) {
                resultEntity.setState(true);
            } else {
                resultEntity.setState(false);
            }
        } else {
            resultEntity.setState(false);
        }

        return resultEntity;
    }

    @Override
    public ResultEntity sendRegisterMobileCode(String mobile) {
        ResultEntity resultEntity = new ResultEntity();

        RegisterIdentifiedcodeEntity entity = new RegisterIdentifiedcodeEntity();
        entity.setEmail_address(mobile);
        String vcode = StringUtils.creatcNumberVcode(4);
        entity.setIdentifying_code(vcode);
        boolean insertCode = registerCodeService.insert(entity);
        if (insertCode) {
            boolean sendRegisterEmail = mobileMessageService.sendRegisterMessage(mobile, vcode);
            if (sendRegisterEmail) {
                resultEntity.setState(true);
            } else {
                resultEntity.setState(false);
            }
        } else {
            resultEntity.setState(false);
        }
        return resultEntity;
    }

    /**
     * 验证实体是否符合插入要求
     *
     * @return 返回null说明符合要求，否则返回错误信息字符串
     */
    private String checkIsLdgal(ReaderInfoEntity readerInfo) {
        String reader_sex = readerInfo.getReader_sex();
        if (reader_sex == null || (!reader_sex.equals("1") && !reader_sex.equals("0"))) {
            return "性别信息不正确";
        }
        if (StringUtils.isEmpty(readerInfo.getReader_name()) || readerInfo.getReader_name().length() > 20) {
            return "姓名格式不正确";
        }
//		if (!StringUtils.isEmail(readerInfo.getEmail())) {
//			return "电子邮件格式不正确";
//		}
        if (!StringUtils.isMobile(readerInfo.getMobile())) {
            return "手机号码格式不正确";
        }
//		if (StringUtils.isEmpty(readerInfo.getId_card()) || readerInfo.getId_card().length() > 19) {
//			return "身份证信息不正确";
//		}
        if (StringUtils.isEmpty(readerInfo.getLogin_name()) || readerInfo.getLogin_name().length() > 40) {
            return "用户名信息不正确";
        }
        return null;
    }

    @Override
    public ResultEntity login(ReaderInfoEntity readerInfo, HttpServletRequest request) {
        ResultEntity result = new ResultEntity();
        if ((readerInfo.getEmail() == null && readerInfo.getMobile() == null && readerInfo.getId_card() == null
                && readerInfo.getLogin_name() == null) || readerInfo.getReader_pwd() == null) {

            result.setValue(false, "登陆信息不全");
            return result;
        }
        ResultEntity resultEntity = this.selectReaderInfoS(readerInfo);
        if (resultEntity == null || !resultEntity.getState()) {
            result.setValue(false, "登陆失败，未知原因");
            return result;
        }
        try {
            List<Map> listTemp = (List<Map>) resultEntity.getResult();
            if (listTemp.isEmpty()) {
                result.setValue(false, "登陆失败，请检查登陆信息");
                result.setRetMessage("2");
                return result;
            }
            String json = JsonUtils.toJson(listTemp.get(0));
            ReaderInfoEntity readerInfoEntity = JsonUtils.fromJson(json, ReaderInfoEntity.class);
            // 检查用户是否锁定
            if (readerInfoEntity.getIsLock() != null && readerInfoEntity.getIsLock() == 1) {
                result.setValue(false, "用户被锁定", "1", null);
                return result;
            }

            int loginFailTime = readerInfoEntity.getLogin_fail_times() != null ? readerInfoEntity.getLogin_fail_times()
                    : 0;
            Timestamp last_lock_time = readerInfoEntity.getLast_lock_time();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.HOUR, 0);
            // 验证用户是否超过密码错误次数，先提取远程服务器的sox模板，若提取出错则不验证密码错误次数
            SoxTemplateEntity userSoxTemplate = null;
            if (last_lock_time != null && calendar.getTime().before(last_lock_time) // 检查锁定时间
                    && loginFailTime != 0 && readerInfoEntity.getSox_tpl_id() != null) {

                userSoxTemplate = soxTemplateService.getSoxTemplateEntityById(readerInfoEntity.getSox_tpl_id());
                if (userSoxTemplate != null) {
                    Integer template_login_fail_times = userSoxTemplate.getLogin_fail_times() != null
                            ? userSoxTemplate.getLogin_fail_times() : -1;

                    if (template_login_fail_times > 0 && loginFailTime >= template_login_fail_times) {
                        result.setValue(false, "密码错误次数过多，请明天再试", "4", null);
                        return result;
                    }

                } else {
                    LogUtils.error("没有找到模板设置SoxTemplateEntity,id==>" + readerInfoEntity.getSox_tpl_id());
                }
            }
            // 开始验证密码
            String pwdMing = passwordService.decrypt(readerInfoEntity.getReader_pwd());
            // 构造日志对象
            LoginOperationLog operationLog = new LoginOperationLog();
            operationLog.setClient_ip(StringUtils.getIpAddr(request));
            operationLog.setClient_port(String.valueOf(request.getRemotePort()));
            operationLog.setLogin_name(readerInfoEntity.getLogin_name());
            if (pwdMing != null && pwdMing.equals(readerInfo.getReader_pwd())) {
                result.setValue(true, "登陆成功");
                readerInfoEntity.setReader_pwd(null);// 擦除密码信息
                result.setResult(readerInfoEntity);
                // 构造登陆成功更新信息
                ReaderInfoEntity readerInfoLoginUpdate = new ReaderInfoEntity();
                readerInfoLoginUpdate.setReader_idx(readerInfoEntity.getReader_idx());
                readerInfoLoginUpdate.setLast_login_time(new Timestamp(System.currentTimeMillis()));
                if (request != null) {
                    readerInfoLoginUpdate.setLast_login_ip(StringUtils.getIpAddr(request));
                }
                // 生成识别码
                String acstr = readerAuthService.generateAuthCode(readerInfoEntity.getReader_idx(), pwdMing);
                result.setRetMessage(acstr);
                // 发送更新信息
                updateReaderInfoEntity(readerInfoLoginUpdate, false);
                // 发送日志
                operationLog.setOperation_result(true);
                operationLogService.addOperationLog(operationLog);
            } else {// 密码错误
                // 构造增加密码错误次数过多的json对象
                ReaderInfoEntity readerInfoPwdErrorUpdate = new ReaderInfoEntity();
                readerInfoPwdErrorUpdate.setReader_idx(readerInfoEntity.getReader_idx());
                readerInfoPwdErrorUpdate.setLast_lock_time(new Timestamp(System.currentTimeMillis()));
                int nowLockTime = 0;
                if (last_lock_time != null && calendar.getTime().after(last_lock_time)) {
                    readerInfoPwdErrorUpdate.setLogin_fail_times(1);
                    nowLockTime = 1;
                } else {
                    readerInfoPwdErrorUpdate.setLogin_fail_times(loginFailTime + 1);
                    nowLockTime = loginFailTime + 1;
                }
                // 发送更新信息
                updateReaderInfoEntity(readerInfoPwdErrorUpdate, false);
                // 发送日志
                operationLog.setFail_time(nowLockTime);
                operationLog.setOperation_result(false);
                operationLogService.addOperationLog(operationLog);

                result.setValue(false, "登陆失败，密码错误");
                result.setRetMessage("3");
                // 计算用户剩余失败次数
                if (userSoxTemplate == null) {
                    userSoxTemplate = soxTemplateService.getSoxTemplateEntityById(readerInfoEntity.getSox_tpl_id());
                }
                if (userSoxTemplate != null) {
                    Integer login_fail_times = userSoxTemplate.getLogin_fail_times() != null
                            ? userSoxTemplate.getLogin_fail_times() : -1;
                    if (login_fail_times > 0) {
                        result.setResult(login_fail_times - nowLockTime);
                    }
                } else {
                    LogUtils.error("没有找到模板设置SoxTemplateEntity,id==>" + readerInfoEntity.getSox_tpl_id());
                }
            }

            return result;
        } catch (Exception e) {
            result.setValue(false, "登陆失败，请检查登陆信息");
            result.setRetMessage("2");
            LogUtils.info(e);
            return result;
        }

    }

    @Override
    public ResultEntity updateReaderInfoEntity(ReaderInfoEntity readerInfoEntity, boolean needCheck) {
        if (readerInfoEntity.getReader_idx() == null) {
            throw new IllegalArgumentException("更新请求没有设置主键idx");
        }

        ResultEntity resultEntity = new ResultEntity();

        // 需要检查信息格式
        if (needCheck) {
            // 手机，身份证，电子邮件是否重复
            try {
                // mail
                ReaderInfoEntity r = new ReaderInfoEntity();
                Integer idx = readerInfoEntity.getReader_idx();
                if (readerInfoEntity.getEmail() != null) {
                    r.setEmail(readerInfoEntity.getEmail());
                    if (this.isEsistsReaderInfo(r, idx)) {
                        resultEntity.setValue(false, "电子邮箱重复");
                        resultEntity.setRetMessage("mail_repeat");
                        return resultEntity;
                    }
                }
                // phone
                if (readerInfoEntity.getMobile() != null) {
                    r.setEmail(null);
                    r.setMobile(readerInfoEntity.getMobile());
                    if (this.isEsistsReaderInfo(r, idx)) {
                        resultEntity.setValue(false, "手机号码重复");
                        resultEntity.setRetMessage("mobile_repeat");
                        return resultEntity;
                    }
                }
                // id card
                if (readerInfoEntity.getId_card() != null) {
                    r.setEmail(null);
                    r.setMobile(null);
                    r.setId_card(readerInfoEntity.getId_card());
                    if (this.isEsistsReaderInfo(r, idx)) {
                        resultEntity.setValue(false, "身份证号码重复");
                        resultEntity.setRetMessage("idcard_repeat");
                        return resultEntity;
                    }
                }
            } catch (Exception e) {
                resultEntity.setValue(false, "unKnow error 1");
                return resultEntity;
            }
        }

        // 需要加密密码
        if (readerInfoEntity.getReader_pwd() != null) {
            String mi = passwordService.encryption(readerInfoEntity.getReader_pwd());
            if (mi == null) {
                resultEntity.setValue(false, "失败,请查看日志");
                LogUtils.error(this.getClass() + "=>updateReaderInfoEntity请求加密密码时失败");
                return resultEntity;
            }
            readerInfoEntity.setReader_pwd(mi);
        }

        String url = requestURLEntity.getRequestURL(URL_UPDATE);
        Map<String, String> map = new HashMap<>(1);
        map.put("json", JsonUtils.toJson(readerInfoEntity));
        String resultJson = HttpClientUtil.doPost(url, map, charset);
        if (resultJson == null || !JSONUtils.mayBeJSON(resultJson)) {
            resultEntity.setValue(false, "连接底层服务失败");
            LogUtils.error("连接db层失败，url==>" + url + " retturn json==>" + resultJson);
            return resultEntity;
        }
        try {
            ResultEntity result = JsonUtils.fromJson(resultJson, ResultEntity.class);
            resultEntity.setValue(true, "成功");
            LogUtils.debug("发送" + resultJson + " db层成功返回了数据" + url + " retturn json==>" + resultJson);
            return result != null ? result : resultEntity;
        } catch (Exception e) {
            resultEntity.setValue(false, "没有返回期望数据");
            LogUtils.error("db层没有返回期望数据ResultEntity.class，url==>" + url + " retturn json==>" + resultJson);
            return resultEntity;
        }
    }

    @Override
    public ResultEntity changePwd(Integer reader_idx, String old_pwd, String new_pwd) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            ReaderInfoEntity reader = selectReaderInfoByPk(reader_idx);
            if (reader == null) {
                resultEntity.setState(false);
                resultEntity.setMessage("提交信息有误");
                return resultEntity;
            }
            String oldPwdMi = reader.getReader_pwd();
            String oldPwd = passwordService.decrypt(oldPwdMi);
            // 构造日志对象
            ChangeOperationLog col = new ChangeOperationLog();
            col.setReader_idx(reader_idx);
            if (oldPwd.equals(old_pwd) && new_pwd.length() > 0) {
                ReaderInfoEntity updateEntity = new ReaderInfoEntity();
                updateEntity.setReader_idx(reader_idx);
                updateEntity.setReader_pwd(new_pwd);
                // 写入日志
                col.setOperation_result(true);
                operationLogService.addOperationLog(col);
                return updateReaderInfoEntity(updateEntity, false);
            } else {
                resultEntity.setState(false);
                resultEntity.setMessage("与原密码不符");
                // 写入日志
                col.setOperation_result(false);
                operationLogService.addOperationLog(col);
                return resultEntity;
            }
        } catch (Exception e) {
            resultEntity.setState(false);
            resultEntity.setMessage("提交信息有误");
            return resultEntity;
        }
    }

    @Override
    public ReaderInfoEntity selectReaderInfoByPk(Integer reader_idx) {
        if (reader_idx != null) {
            String url = requestURLEntity.getRequestURL(URL_SELECT);
            Map<String, String> map = new HashMap<>(1);
            map.put("json", "{\"reader_idx\":" + reader_idx + "}");
            String remoteResultJson = HttpClientUtil.doPost(url, map, charset);
            try {
                ResultEntity remoteResultEntity = JsonUtils.fromJson(remoteResultJson, ResultEntity.class);
                if (remoteResultEntity.getState()) {
                    return ObjectUtils.convertMap((Map<String, Object>) remoteResultEntity.getResult(),
                            ReaderInfoEntity.class);
                }
            } catch (Exception e) {
                LogUtils.error(getClass() + " " + url + " 远程服务器没有返回预期格式的数据,预期数据格式ReaderInfoEntity.class  returnData==>"
                        + remoteResultJson);
            }
        }

        return null;
    }

    @Override
    public ResultEntity sendChangePwdEmail(ReaderInfoEntity readerInfoEntity) {
        ResultEntity resultEntity = new ResultEntity();
        if (readerInfoEntity == null
                || (readerInfoEntity.getMobile() == null
                && readerInfoEntity.getEmail() == null
                && readerInfoEntity.getId_card() == null
                && readerInfoEntity.getLogin_name() == null)) {
            resultEntity.setValue(false, "提交信息不完整");
            resultEntity.setRetMessage("1");
            return resultEntity;
        }

        ResultEntity remoteResult = this.selectReaderInfoS(readerInfoEntity);

        if (remoteResult != null && remoteResult.getState() && remoteResult.getResult() != null) {// 正常返回数据
            List selectList = (List) remoteResult.getResult();
            if (!selectList.isEmpty()) {
                try {
                    String tempJson = JsonUtils.toJson(selectList.get(0));
                    ReaderInfoEntity remoteReaderInfo = JsonUtils.fromJson(tempJson, ReaderInfoEntity.class);
                    if (remoteReaderInfo != null && remoteReaderInfo.getReader_idx() != null) {
                        String vcode = StringUtils.creatcVcode(6);//生成验证码

                        PasswordIdentifiedcodeEntity entity = new PasswordIdentifiedcodeEntity();
                        entity.setIdentifying_code(vcode);
                        entity.setReader_idx(remoteReaderInfo.getReader_idx());
                        boolean insertCodeState = passwordIdentifiedcodeService.insert(entity);
                        if (insertCodeState) {//插入数据库成功
                            String email = remoteReaderInfo.getEmail();
                            if (email != null) {
                                //发送邮件
                                String content = "您的重置密码验证码为：<strong>" + vcode + "</strong><br/>" + "请在"
                                        + (passwordCodeTimeout / 60_000) + "分钟内输入，验证码忽略大小写。";
                                boolean emailState = emailService.sendPasswordEmail(email, content);
                                if (emailState) {//发送邮件成功
                                    resultEntity.setValue(true, "success", "0", null);
                                }
                            } else {
                                LogUtils.info("reader_idx==>" + remoteReaderInfo.getReader_idx() + "用户的邮箱为空，不能发送找回密码邮件");
                            }
                        }
                    }
                } catch (Exception e) {
                    LogUtils.info(getClass() + " json转换成ReaderInfoEntity对象出错", e);
                    resultEntity.setState(false);
                    resultEntity.setRetMessage("-1");
                    return resultEntity;
                }
            } else {
                resultEntity.setState(true);
                resultEntity.setMessage("找不到此用户");
                resultEntity.setRetMessage("2");
            }
        } else {
            LogUtils.info(this.getClass() + "=>foundPwd向db层请求出错");
            resultEntity.setState(false);
            resultEntity.setRetMessage("-1");
            return resultEntity;
        }

        return resultEntity;
    }

    @Override
    public ResultEntity sendChangePwdMobile(ReaderInfoEntity readerInfoEntity) {
        ResultEntity resultEntity = new ResultEntity();
        if (readerInfoEntity == null
                || (readerInfoEntity.getMobile() == null
                && readerInfoEntity.getEmail() == null
                && readerInfoEntity.getId_card() == null
                && readerInfoEntity.getLogin_name() == null)) {
            resultEntity.setValue(false, "提交信息不完整");
            resultEntity.setRetMessage("1");
            return resultEntity;
        }

        ResultEntity remoteResult = this.selectReaderInfoS(readerInfoEntity);

        if (remoteResult != null && remoteResult.getState() && remoteResult.getResult() != null) {// 正常返回数据
            List selectList = (List) remoteResult.getResult();
            if (!selectList.isEmpty()) {
                try {
                    String tempJson = JsonUtils.toJson(selectList.get(0));
                    ReaderInfoEntity remoteReaderInfo = JsonUtils.fromJson(tempJson, ReaderInfoEntity.class);
                    if (remoteReaderInfo != null && remoteReaderInfo.getReader_idx() != null) {
                        String vcode = StringUtils.creatcNumberVcode(4);//生成验证码

                        PasswordIdentifiedcodeEntity entity = new PasswordIdentifiedcodeEntity();
                        entity.setIdentifying_code(vcode);
                        entity.setReader_idx(remoteReaderInfo.getReader_idx());
                        boolean insertCodeState = passwordIdentifiedcodeService.insert(entity);
                        if (insertCodeState) {//插入数据库成功
                            String mobile = remoteReaderInfo.getMobile();
                            if (mobile != null) {
                                //发送短信
                                boolean state = mobileMessageService.sendPasswordMessage(mobile, vcode);
                                if (state) {//发送邮件成功
                                    resultEntity.setValue(true, "success", "0", null);
                                }
                            } else {
                                LogUtils.info("reader_idx==>" + remoteReaderInfo.getReader_idx() + "用户的手机号为空，不能发送找回密码短信");
                            }
                        }
                    }
                } catch (Exception e) {
                    LogUtils.info(getClass() + " json转换成ReaderInfoEntity对象出错", e);
                    resultEntity.setState(false);
                    resultEntity.setRetMessage("-1");
                    return resultEntity;
                }
            } else {
                resultEntity.setState(false);
                resultEntity.setMessage("找不到此用户");
                resultEntity.setRetMessage("2");
            }
        } else {
            LogUtils.info(this.getClass() + "=>foundPwd向db层请求出错");
            resultEntity.setState(false);
            resultEntity.setRetMessage("-1");
            return resultEntity;
        }

        return resultEntity;
    }

    @Override
    public ResultEntity changePwdByVcode(ReaderInfoEntity readerInfoEntity, String vcode, HttpServletRequest request) {
        ResultEntity resultEntity = new ResultEntity();
        if (readerInfoEntity == null
                || (StringUtils.isEmpty(readerInfoEntity.getId_card())
                && StringUtils.isEmpty(readerInfoEntity.getLogin_name())
                && StringUtils.isEmpty(readerInfoEntity.getEmail())
                && StringUtils.isEmpty(readerInfoEntity.getMobile())
                && vcode == null
                && StringUtils.isEmpty(readerInfoEntity.getReader_pwd()))) {
            resultEntity.setRetMessage("1");
            resultEntity.setMessage("提交信息不完整");
            return resultEntity;
        }

        String pwd = readerInfoEntity.getReader_pwd();
        readerInfoEntity.setReader_pwd(null);

        List<ReaderInfoEntity> readers = selectReaderInfoSToReaders(readerInfoEntity);
        if (!readers.isEmpty()) {
            Integer reader_idx = readers.get(0).getReader_idx();
            PasswordIdentifiedcodeEntity selectCode = passwordIdentifiedcodeService.selectCode(reader_idx, passwordCodeTimeout);
            if (selectCode != null) {
                if (vcode != null && vcode.equalsIgnoreCase(selectCode.getIdentifying_code())) {
                    ReaderInfoEntity updateReaderinfo = new ReaderInfoEntity();
                    updateReaderinfo.setReader_idx(reader_idx);
                    updateReaderinfo.setReader_pwd(pwd);
                    //解锁
                    updateReaderinfo.setLogin_fail_times(0);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    updateReaderinfo.setUpdateTime(sdf.format(new Date()));
                    ResultEntity updateResult = this.updateReaderInfoEntity(updateReaderinfo, false);
                    resultEntity.setState(updateResult.getState());
                    resultEntity.setMessage(updateResult.getState() ? "success" : "unknow fail");
                    resultEntity.setRetMessage(updateResult.getState() ? "0" : null);
                    //写入日志
                    FoundOperationLog fol = new FoundOperationLog();
                    fol.setReader_idx(reader_idx);
                    fol.setClient_ip(StringUtils.getIpAddr(request));
                    fol.setClient_port(String.valueOf(request.getRemotePort()));
                    fol.setOperation_result(resultEntity.getState());
                    operationLogService.addOperationLog(fol);

                    //销毁验证码
                    passwordIdentifiedcodeService.deleteByPkAsync(reader_idx);
                } else {
                    resultEntity.setState(false);
                    resultEntity.setMessage("验证码不正确");
                    resultEntity.setRetMessage("4");
                }

            } else {
                resultEntity.setState(false);
                resultEntity.setMessage("没有验证码或者验证码过期");
                resultEntity.setRetMessage("3");
            }
        } else {
            resultEntity.setState(false);
            resultEntity.setMessage("找不到此用户");
            resultEntity.setRetMessage("2");
        }

        return resultEntity;
    }
}
