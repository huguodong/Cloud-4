package com.ssitcloud.business.mobile.controller;

import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.mobile.entity.ReaderInfoEntity;
import com.ssitcloud.business.mobile.service.ReaderAuthServiceI;
import com.ssitcloud.business.mobile.service.ReaderInfoServiceI;
import com.ssitcloud.business.mobile.service.ReaderSettingServiceI;
import com.ssitcloud.business.mobile.service.impl.ReaderAuthServiceImpl;
import com.ssitcloud.common.entity.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by LXP on 2017/8/11.
 */
@Controller
@RequestMapping("/readerSetting")
public class ReaderSettingController {

    @Autowired
    private ReaderSettingServiceI readerSettingService;

    @Autowired
    private ReaderInfoServiceI readerInfoService;

    @Autowired
    private ReaderAuthServiceI readerAuthService;

    @RequestMapping("/index")
    public ModelAndView index(String reader_auth) throws UnsupportedEncodingException {
        ModelAndView mav = new ModelAndView("mobile/editIndex");
        mav.addObject("auth", URLEncoder.encode(reader_auth, "utf-8"));
        return mav;
    }

    @RequestMapping("/mobile")
    public ModelAndView mobile(String auth) {
        ModelAndView mav = new ModelAndView("mobile/editMobile");
        ReaderAuthServiceImpl.AuthCode authCode = readerAuthService.getAuthCode(auth);
        if (authCode == null) {
            mav.setViewName("redirect:index");
            return mav;
        }
        mav.addObject("reader_idx", authCode.getReader_idx());
        mav.addObject("auth", auth);
        return mav;
    }

    @RequestMapping("/idcard")
    public ModelAndView idcard(String auth) {
        ModelAndView mav = new ModelAndView("mobile/editIdCard");
        ReaderAuthServiceImpl.AuthCode authCode = readerAuthService.getAuthCode(auth);
        if (authCode == null) {
            mav.setViewName("redirect:index");
            return mav;
        }
        ReaderInfoEntity readerInfoEntity = readerInfoService.selectReaderInfoByPk(authCode.getReader_idx());
        if (readerInfoEntity == null) {
            throw new RuntimeException("unKnow user");
        }
        mav.addObject("reader_idx", authCode.getReader_idx());
        mav.addObject("auth", auth);
        mav.addObject("idcard",readerInfoEntity.getId_card());
        return mav;
    }

    @RequestMapping("/mail")
    public ModelAndView mail(String auth) {
        ModelAndView mav = new ModelAndView("mobile/editMail");
        ReaderAuthServiceImpl.AuthCode authCode = readerAuthService.getAuthCode(auth);
        if (authCode == null) {
            mav.setViewName("redirect:index");
            return mav;
        }
        ReaderInfoEntity readerInfoEntity = readerInfoService.selectReaderInfoByPk(authCode.getReader_idx());
        if (readerInfoEntity == null) {
            throw new RuntimeException("unKnow user");
        }
        mav.addObject("reader_idx", authCode.getReader_idx());
        mav.addObject("auth", auth);
        mav.addObject("mail",readerInfoEntity.getEmail());
        return mav;
    }

    @RequestMapping("/setIdcard")
    @ResponseBody
    public ResultEntity setIdcard(String json) {
        ResultEntity resultEntity = new ResultEntity();
        Map<String, Object> m = JsonUtils.fromJson(json, Map.class);
        if (m == null || m.get("id_card") == null || m.get("reader_idx") == null) {
            return resultEntity;
        }
        return readerSettingService.updateIdcard((Integer) m.get("reader_idx"), (String) m.get("id_card"));
    }

    @RequestMapping("/setMail")
    @ResponseBody
    public ResultEntity setMail(String json) {
        ResultEntity resultEntity = new ResultEntity();
        Map<String, Object> m = JsonUtils.fromJson(json, Map.class);
        if (m == null || m.get("mail") == null || m.get("reader_idx") == null) {
            return resultEntity;
        }
        return readerSettingService.updateMail((Integer) m.get("reader_idx"), (String) m.get("mail"));

    }

    @RequestMapping("/vcode")
    @ResponseBody
    public ResultEntity vcode(String json) {
        ResultEntity resultEntity = new ResultEntity();
        Map<String, Object> m = JsonUtils.fromJson(json, Map.class);
        if (m == null || m.get("reader_idx") == null) {
            return resultEntity;
        }
        return readerSettingService.sendOriginalVcode((Integer) m.get("reader_idx"));
    }

    @RequestMapping("/newVcode")
    @ResponseBody
    public ResultEntity newVcode(String json) {
        ResultEntity resultEntity = new ResultEntity();
        Map<String, Object> m = JsonUtils.fromJson(json, Map.class);
        if (m == null || m.get("mobile") == null) {
            return resultEntity;
        }
        return readerSettingService.sendNewVcode((String) m.get("mobile"));
    }

    @RequestMapping("/setNewMobile")
    @ResponseBody
    public ResultEntity setNewMobile(String json) {
        ResultEntity resultEntity = new ResultEntity();
        Map<String, Object> m = JsonUtils.fromJson(json, Map.class);
        if (m == null || m.get("reader_idx") == null
                || m.get("newMobile") == null || m.get("vcode") == null || m.get("newVcode") == null) {
            return resultEntity;
        }
        return readerSettingService.setNewMobile((Integer) m.get("reader_idx"), (String) m.get("newMobile"), (String) m.get("vcode"), (String) m.get("newVcode"));
    }
}
