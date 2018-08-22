package com.ssitcloud.business.statistics.controller;

import com.ssitcloud.business.statistics.common.utils.ExceptionHelper;
import com.ssitcloud.business.statistics.service.MainfieldExtendService;
import com.ssitcloud.common.entity.ResultEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by LQW on 2017/9/1.
 */
@Controller
@RequestMapping("/mfextend")
public class MainfieldExtendController {
    @Resource
    MainfieldExtendService mainfieldExtendService;

    /**
     * 查询mainfield_extend所有数据
     * @param request
     * @param req
     * @return
     */
    @RequestMapping("/selectByMfid")
    @ResponseBody
    public ResultEntity selectByMfid(HttpServletRequest request, String req){
        ResultEntity result = new ResultEntity();
        try {
            result = mainfieldExtendService.selectByMfid(req);
        } catch (Exception e) {
            String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
            ExceptionHelper.afterException(result, methodName, e);
        }
        return result;
    }

}
