package com.ssitcloud.view.statistics.controller;


import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.controller.BasicController;
import com.ssitcloud.view.statistics.common.util.ExceptionHelper;
import com.ssitcloud.view.statistics.common.util.JsonUtils;
import com.ssitcloud.view.statistics.service.RegionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/region")
public class RegionController extends BasicController {

	@Resource
    RegionService regionService;
    /**
     * 根据馆IDX查询所有设备的地区分布
     * 2017-08-24 lqw
     * @param request
     * @param req
     * @return
     */
    @RequestMapping(value={"/selRegionsByLibidx"})
    @ResponseBody
    public ResultEntity selRegionsByLibidx(HttpServletRequest request,String req){
        ResultEntity result = new ResultEntity();
        try {
            result = regionService.selRegionsByLibidx(req);
        } catch (Exception e) {
            ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
        return result;
    }

    @RequestMapping(value={"/selectRelLibsByid"})
    @ResponseBody
    public ResultEntity selectRelLibsByid(HttpServletRequest request){
        ResultEntity result = new ResultEntity();
        try {
            String req = "{}";
            Operator oper=getCurrentUser();
            if(!oper.getOperator_type().equals("1")){
                req = "{\"library_idx\":"+oper.getLibrary_idx()+"}";
            }
            result = regionService.selectRelLibsByid(req);
        } catch (Exception e) {
            ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
        return result;
    }

    /**
     * 根据当前登陆用户获取相应的图书馆信息。和分馆信息
     * 如果是系统管理员 ，则 可以获取全部图书馆信息。
     * @comment
     * @param request
     * @param json
     * @return
     * @data 2017年9月13日
     * @author
     */
    @RequestMapping("/querylibInfoByCurUserEXT1")
    @ResponseBody
    public ResultEntity querylibInfoByCurUserEXT1(HttpServletRequest request,String json){
        ResultEntity result = new ResultEntity();
        try {
            String operator_type = getCurrentUser().getOperator_type();
            String library_idx = getCurrentUser().getLibrary_idx();
            String params = "{\"library_idx\":\""+library_idx+"\",\"operator_type\":\""+operator_type+"\"}";
            Map<String, String> map = new HashMap<>();
            map.put("req", params);
            String resps = regionService.querylibInfoByCurUserEXT1(map);
            result = JsonUtils.fromJson(resps, ResultEntity.class);
        } catch (Exception e) {
            String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
            ExceptionHelper.afterException(result, methodName, e);
        }
        return result;
    }
}
