package com.ssitcloud.dbstatistics.controller;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbstatistics.common.utils.ExceptionHelper;
import com.ssitcloud.dbstatistics.common.utils.JsonUtils;
import com.ssitcloud.dbstatistics.entity.MainfieldExtendEntity;
import com.ssitcloud.dbstatistics.service.MainfieldExtendService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public ResultEntity selectByMfid(HttpServletRequest request,String req){
        ResultEntity result = new ResultEntity();
        try {
            MainfieldExtendEntity mainfieldExtendEntity = new MainfieldExtendEntity();
            List<MainfieldExtendEntity> list = mainfieldExtendService.selectByMfid(mainfieldExtendEntity);
            result.setValue(true, "success","",list);
        } catch (Exception e) {
            String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
            ExceptionHelper.afterException(result, methodName, e);
        }
        return result;
    }
}
