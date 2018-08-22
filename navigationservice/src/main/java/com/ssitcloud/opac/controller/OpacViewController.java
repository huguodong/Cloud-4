package com.ssitcloud.opac.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.common.controller.BasicController;

@Controller
@RequestMapping(value = { "opac" })
public class OpacViewController extends BasicController {
	
	/** 分页查询 */
	@RequestMapping(value = { "index" })
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, String req) {
		Map<String, Object> model = new HashMap<>();
		return new ModelAndView("/page/navigation/opac-index", model);
	}

}
