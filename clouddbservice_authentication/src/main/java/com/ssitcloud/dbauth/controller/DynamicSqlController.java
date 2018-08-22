package com.ssitcloud.dbauth.controller;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.service.DynamicSqlService;
import com.ssitcloud.dbauth.utils.LogUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by lqw on 2017/8/29.
 */
@Controller
@RequestMapping("/dynamicsql")
public class DynamicSqlController {

    @Resource
    private DynamicSqlService dynamicSqlService;

    @RequestMapping("/selectBySql")
    @ResponseBody
    public ResultEntity selectBySql(HttpServletRequest request){
        ResultEntity resultEntity = new ResultEntity();
        try {
            String sql = request.getParameter("req");
            List<Map<String, Object>> list = dynamicSqlService.selectBySql(sql);
            resultEntity.setValue(true, "success","",list);
        } catch (Exception e) {
            //获取当前方法名称
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
            LogUtils.error(methodName+"()异常", e);
        }
        return resultEntity;

    }
}
