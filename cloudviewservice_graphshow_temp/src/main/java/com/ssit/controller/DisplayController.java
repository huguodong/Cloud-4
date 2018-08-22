package com.ssit.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ssit.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssit.common.entity.ResultEntity;
import com.ssit.service.DisplayService;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DisplayController {
    private static Logger logger = LoggerFactory.getLogger(DisplayController.class);
    @Resource
    private DisplayService displayService;

    @RequestMapping(value = {"index"})
    public ModelAndView index(HttpServletRequest request) {
        Map<String, String> param = PackParamUils.packParam(request);
        String videoCode = request.getParameter("videoCode");
        if (StringUtils.isEmpty(videoCode)) {
            videoCode = "1";
        }
        param.put("videoCode", videoCode);
        return new ModelAndView("index", param);
    }

    @RequestMapping(value = {"video"})
    public ModelAndView video(HttpServletRequest request) {
        Map<String, String> param = PackParamUils.packParam(request);
        String videoCode = request.getParameter("videoCode");
        if (StringUtils.isEmpty(videoCode)) {
            videoCode = "1";
        } else {
            int videoCodeInt = Integer.parseInt(videoCode);
            videoCode = videoCodeInt == 5 ? 1 + "" : videoCodeInt + 1 + "";
        }
        String videoName = VideoDataUtils.videoMap.get(videoCode);
        param.put("videoCode", videoCode);
        param.put("videoName", videoName);
        return new ModelAndView("video", param);
    }

    @RequestMapping(value = {"book/rank"})
    @ResponseBody
    public ResultEntity bookRank() {
        return displayService.bookRank();
    }

    @RequestMapping(value = {"book/category"})
    @ResponseBody
    public ResultEntity bookCategory() throws Exception {
        return displayService.bookCategory();
    }

    @RequestMapping(value = {"book/circulate"})
    @ResponseBody
    public ResultEntity bookCirculate() {
        return displayService.bookCirculate();
    }

    @RequestMapping(value = {"equipment/count"})
    @ResponseBody
    public ResultEntity equipmentCount() {
        return displayService.equipmentCount();
    }

    @RequestMapping(value = {"library"})
    @ResponseBody
    public ResultEntity library() {
        return displayService.libraryList();
    }

    @RequestMapping(value = {"visit/record"})
    @ResponseBody
    public ResultEntity visitRecord() {
        return displayService.visitRecord();
    }

    @RequestMapping(value = {"visit/record/today"})
    @ResponseBody
    public ResultEntity visitRecordToday() {
        return displayService.visitRecordToday();
    }

    @RequestMapping(value = {"statistics"})
    @ResponseBody
    public ResultEntity statistics() {
        return displayService.statistics();
    }

    /**
     * 获取时间、天气
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"getTitle"})
    @ResponseBody
    public ResultEntity getTitle(HttpServletRequest request) {
        ResultEntity result = new ResultEntity();
        String date = DateUtil.getDate(new Date()) + " " + DateUtil.getWeek(new Date()).getChineseName();
        Map<String, String> map = new HashMap<>();
        map.put("date", date);
        String weather_city = request.getParameter("weather_city");
        if (!StringUtils.isEmpty(weather_city)) {
            map.putAll(WeatherUtil.getWeather(weather_city));
        }
        result.setState(true);
        result.setResult(map);
        return result;
    }

    @RequestMapping(value = {"test"})
    @ResponseBody
    public ResultEntity test(){
        displayService.initData();
        //凌晨1点到凌晨6点
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j < 2; j++) {
                displayService.updateData(i);
            }
        }
//        //早上7点到下午9点
        for (int i = 5; i <= 20; i++) {
            for (int j = 0; j < 60; j++) {
                displayService.updateData(i);
            }
        }
        //晚上10点到0点
        for (int i = 21; i <= 23; i++) {
            for (int j = 0; j < 2; j++) {
                displayService.updateData(i);
            }
        }
        ResultEntity result = new ResultEntity();
        result.setState(true);
        return result;
    }
}
