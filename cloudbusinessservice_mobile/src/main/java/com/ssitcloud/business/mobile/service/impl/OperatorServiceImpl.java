package com.ssitcloud.business.mobile.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssitcloud.authentication.entity.SoxTemplateEntity;
import com.ssitcloud.business.mobile.service.*;
import org.apache.http.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.authentication.entity.OperatorAppEntity;
import com.ssitcloud.business.mobile.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.common.util.StringUtils;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.PasswordIdentifiedcodeEntity;

@Service
public class OperatorServiceImpl extends BasicServiceImpl implements OperatorService {
    private static final String URL_selectOperaotrByIdxOrId = "selectOperaotrByIdxOrId";
    private static final String URL_updateOperAppByIdx = "updateOperAppByIdx";
    private static final String URL_updateOperAppPwdByIdx = "updateOperAppPwdByIdx";
    private static final String URL_changePassword = "changePassword";
    private static final String URL_retrievePassword = "retrievePassword";
    private static final String URL_loginCheck = "loginCheck";
    private static final String URL_checkPwdFormat = "checkPwdFormat";
    private static final String URL_getDevExtModel = "getDevExtModel";
    private static final String URL_selectDeviceExtState = "selectDeviceExtState";
    private static final String URL_selectSoftState = "selectSoftState";
    private static final String URL_selectBookrackState = "selectBookrackState";
    private static final String URL_selectBinState = "selectBinState";
    private static final String URL_selectDeviceByPage = "selectDeviceByPage";
    private static final String URL_selectOperatorIdByParam = "selectOperatorIdByParam";
    private static final String URL_queryDeviceByParam = "queryDeviceByParam";
    private static final String URL_selectDevicTypeByDeviceId = "selectDevicTypeByDeviceId";
    private static final String URL_selectDevIdByIdx = "selectDevIdByIdx";
    private static final String URL_selectMetaDevTypeIdxByType = "selectMetaDevTypeIdxByType";
    private static final String URL_selFunctionByDeviceIdx = "selFunctionByDeviceIdx";
    private static final String URL_getLastHeatBeatTime = "GetLastHeatBeatTime";
    private static final String URL_selectDeviceState = "selectDeviceState";
    // private static final String URL_checkPermission = "checkPermission";
    public static final String URL_SelectDevice = "SelectDeviceMgmt";
    public static final String URL_SelectDeviceMgmtByLibraryIdxs = "SelectDeviceMgmtByLibraryIdxs";
    public static final String URL_sendOrder = "sendOrder";
    public static final String URL_selectDeviceTroublesByLibIdxs = "selectDeviceTroublesByLibIdxs";
    public static final String URL_updateDeviceTroubles = "updateDeviceTroubles";
    public static final String URL_selRegionIdxsByLibIdxs = "selRegionIdxsByLibIdxs";
    public static final String URL_selectOperaotrByParam = "selOperaotrByParam";

    private final int passwordCodeTimeout = 10 * 60_000;//找回密码验证码失效时间

    @Autowired
    private PasswordIdentifiedcodeService passwordIdentifiedcodeService;

    @Autowired
    private MobileMessageServiceI mobileMessageService;

    @Autowired
    private PasswordServiceI passwordService;

    @Autowired
    private SoxTemplateServiceI soxTemplateService;

    @Override
    public ResultEntity selectOperaotrByIdxOrId(String req) {

        return postURL(URL_selectOperaotrByIdxOrId, req);
    }

    @Override
    public ResultEntity updateOperator(String req) {

        return postURL(URL_updateOperAppByIdx, req);
    }

    @Override
    public ResultEntity changePassword(String req) {

        return postURL(URL_changePassword, req);
    }

    @Override
    public ResultEntity retrievePassword(String req) {

        return postURL(URL_retrievePassword, req);
    }

    @Override
    public ResultEntity loginCheck(String req) {

        ResultEntity resultEntity = new ResultEntity();
        String reqURL = requestURL.getRequestURL(URL_loginCheck);
        Map<String, String> map = new HashMap<>();
        map.put("operInfo", req);
        String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
        if (result != null && !result.contains(">")) {
            ResultEntity r = JsonUtils.fromJson(result, ResultEntity.class);
            if (r.getResult() instanceof Map) {
                Map<String,Object> userData = (Map<String, Object>) r.getResult();
                userData.remove("operator_pwd");
                userData.remove("last_login_ip");
                userData.remove("last_chgpwd_time");
                userData.remove("login_fail_times");
                String soxTplId = (String) userData.get("sox_tpl_id");
                if(soxTplId != null){
                    SoxTemplateEntity soxTemplate = soxTemplateService.getSoxTemplateEntityById(Integer.valueOf(soxTplId));
                    if (soxTemplate != null){
                        userData.put("password_set"
                                ,"{\"charset\":\"" + soxTemplate.getPassword_charset() + "\",\"length\":" + soxTemplate.getPassword_length() + "}");
                    }
                }
                resultEntity.setState(true);
                resultEntity.setResult(userData);
            }else{
                return r;
            }
        }
        return resultEntity;
    }

    @Override
    public ResultEntity checkPwdFormat(String req) {

        return postURL(URL_checkPwdFormat, req);
    }

    @Override
    public ResultEntity getDevExtModel(String req) {

        return postURL(URL_getDevExtModel, req);
    }

    @Override
    public ResultEntity selectDeviceExtState(String req) {

        return postURL(URL_selectDeviceExtState, req);
    }

    @Override
    public ResultEntity selectSoftState(String req) {

        return postURL(URL_selectSoftState, req);
    }

    @Override
    public ResultEntity selectBookrackState(String req) {

        return postURL(URL_selectBookrackState, req);
    }

    @Override
    public ResultEntity selectBinState(String req) {

        return postURL(URL_selectBinState, req);
    }

    @Override
    public ResultEntity updateOperAppPwdByIdx(String req) {

        return postURL(URL_updateOperAppPwdByIdx, req);
    }

    @Override
    public ResultEntity selectDeviceByPage(String req) {

        return postURL(URL_selectDeviceByPage, req);
    }

    @Override
    public ResultEntity selectOperatorIdByParam(String req) {

        return postURL(URL_selectOperatorIdByParam, req);
    }

    @Override
    public ResultEntity queryDeviceByParam(String req) {

        return postURL(URL_queryDeviceByParam, req);
    }

    @Override
    public ResultEntity selectDevicTypeByDeviceId(String req) {

        return postURL(URL_selectDevicTypeByDeviceId, req);
    }

    @Override
    public ResultEntity selectDevIdByIdx(String req) {

        return postURL(URL_selectDevIdByIdx, req);
    }

    @Override
    public ResultEntity selectMetaDevTypeIdxByType(String req) {

        return postURL(URL_selectMetaDevTypeIdxByType, req);
    }

    @Override
    public ResultEntity selFunctionByDeviceIdx(String req) {

        return postURL(URL_selFunctionByDeviceIdx, req);
    }

    @Override
    public ResultEntity getLastHeatBeatTime(String req) {

        return postURL(URL_getLastHeatBeatTime, req);
    }

    @Override
    public ResultEntity selectDeviceState(String req) {

        return postURL(URL_selectDeviceState, req);
    }
    //
    // @Override
    // public ResultEntity checkPermission(String req) {
    //
    // return postURL(URL_checkPermission, req);
    // }

    @Override
    public ResultEntity SelectDeviceMgmtByLibraryIdxs(Map<String, String> map) {

        return postURL(URL_SelectDeviceMgmtByLibraryIdxs, map);
    }

    @Override
    public ResultEntity sendOrder(String req) {
        return postURL(URL_sendOrder, req);
    }

    @Override
    public ResultEntity selectDeviceTroublesByLibIdxs(String req) {
        return postURL(URL_selectDeviceTroublesByLibIdxs, req);
    }

    @Override
    public ResultEntity updateDeviceTroubles(String req) {
        return postURL(URL_updateDeviceTroubles, req);
    }

    @Override
    public ResultEntity selRegionIdxsByLibIdxs(String req) {
        return postURL(URL_selRegionIdxsByLibIdxs, req);
    }

    @Override
    public ResultEntity sendFoundPwdVcode(String mobile) {
        ResultEntity resultEntity = new ResultEntity();
        String req = "{\"mobile\":\"" + mobile + "\"}";
        ResultEntity r = postURL(URL_selectOperaotrByParam, req);
        if (!r.getState()) {
            resultEntity.setState(false);
            resultEntity.setMessage("-1");
            return resultEntity;
        }
        List<Map<String, Object>> l = (List<Map<String, Object>>) r.getResult();
        if (l != null && !l.isEmpty()) {
            Map<String, Object> userData = l.get(0);
            if (userData == null || !userData.containsKey("operator_idx")) {
                resultEntity.setState(false);
                resultEntity.setMessage("-1");
                return resultEntity;
            }
            Integer soxTemplateIdx = (Integer) userData.get("sox_tpl_id");
            if(soxTemplateIdx != null){
                SoxTemplateEntity soxTemplate = soxTemplateService.getSoxTemplateEntityById(soxTemplateIdx);
                if(soxTemplate != null){
                    resultEntity.setResult("{\"charset\":\"" + soxTemplate.getPassword_charset() + "\",\"length\":" + soxTemplate.getPassword_length() + "}");
                }
            }
            Integer idx = (Integer) userData.get("operator_idx");
            String vcode = StringUtils.creatcNumberVcode(4);

            // 插入验证码到数据库
            PasswordIdentifiedcodeEntity entity = new PasswordIdentifiedcodeEntity();
            entity.setReader_idx(-1 * idx);
            entity.setIdentifying_code(vcode);
            boolean b = passwordIdentifiedcodeService.insert(entity);

            if (b) {
                b = mobileMessageService.sendPasswordMessage(mobile, vcode);
                if (b) {
                    resultEntity.setMessage("1");
                } else {
                    resultEntity.setMessage("-500");
                }
            } else {
                LogUtils.error(getClass() + "sendFoundPwdVcode插入馆员验证码到数据失败");
            }

            resultEntity.setState(b);
        } else {
            resultEntity.setState(false);
            resultEntity.setMessage("-1");
        }

        return resultEntity;
    }

    @Override
    public ResultEntity changePwdByVcode(Map<String, Object> data) {
        ResultEntity resultEntity = new ResultEntity();
        if (!data.containsKey("mobile")
                || !data.containsKey("pwd")
                || !data.containsKey("vcode")) {
            resultEntity.setMessage("必须提交mobile,pwd,vcode");
            return resultEntity;
        }


        String req = "{\"mobile\":\"" + data.get("mobile") + "\"}";
        ResultEntity r = postURL(URL_selectOperaotrByParam, req);
        if (!r.getState()) {
            resultEntity.setState(false);
            resultEntity.setMessage("用户不存在");
            resultEntity.setMessage("-1");
            return resultEntity;
        }
        List<Map<String, Object>> l = (List<Map<String, Object>>) r.getResult();
        if (l != null && !l.isEmpty()) {
            Map<String, Object> userData = l.get(0);
            if (userData == null || !userData.containsKey("operator_idx")) {
                resultEntity.setState(false);
                resultEntity.setMessage("用户不存在");
                resultEntity.setMessage("-1");
                return resultEntity;
            }
            Integer idx = (Integer) userData.get("operator_idx");

            PasswordIdentifiedcodeEntity entity = passwordIdentifiedcodeService.selectCode(-1 * idx, passwordCodeTimeout);
            if (entity != null) {
                if (entity.getIdentifying_code().equals(data.get("vcode"))) {
                    //修改用户密码操作
                    OperatorAppEntity operatorAppEntity = new OperatorAppEntity();
                    operatorAppEntity.setOperator_idx(idx);
                    String p = passwordService.encryption(String.valueOf(data.get("pwd")));
                    if (p == null) {
                        resultEntity.setMessage("-500");
                        return resultEntity;
                    }
                    operatorAppEntity.setOperator_pwd(p);
                    r = updateOperAppPwdByIdx(JsonUtils.toJson(operatorAppEntity));
                    if (r.getState()) {
                        passwordIdentifiedcodeService.deleteByPkAsync(-1 * idx);
                    }
                    resultEntity.setState(r.getState());
                    resultEntity.setMessage(r.getState() ? "1" : "-500");
                    return resultEntity;
                } else {
                    resultEntity.setState(false);
                    resultEntity.setMessage("验证码错误");
                    resultEntity.setMessage("-2");
                    return resultEntity;
                }
            } else {
                resultEntity.setState(false);
                resultEntity.setMessage("验证码错误");
                resultEntity.setMessage("-2");
                return resultEntity;
            }
        } else {
            resultEntity.setState(false);
            resultEntity.setMessage("用户不存在");
            resultEntity.setMessage("-1");
            return resultEntity;
        }

    }

}
