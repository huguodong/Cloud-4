package com.ssitcloud.business.devmgmt.controller;


import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.devmgmt.service.RegionService;
import com.ssitcloud.common.entity.ResultEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/region")
public class RegionController {

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
    public ResultEntity selectRelLibsByid(HttpServletRequest request,String req){
        ResultEntity result = new ResultEntity();
        try {
            result = regionService.selectRelLibsByid(req);
        } catch (Exception e) {
            ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
        return result;
    }
	
}
