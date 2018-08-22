package com.ssitcloud.view.statistics.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.util.ExceptionHelper;
import com.ssitcloud.view.statistics.statisticsmgmt.service.StatisticsConfigService;

@Controller
@RequestMapping(value = { "templatemanagement" })
public class TemplateManagementController {
	@Resource
	private StatisticsConfigService statisticsConfigService;
	
	/**
	 * 跳转到统计模板配置页面
	 * author lqw
	 * 2017年3月30日
	 * @return
	 */
	@RequestMapping("/main")
	public String gotSoxTempMain(HttpServletRequest request,Model model){
        String currentpage = request.getParameter("currentpage");
        int page = 1;
        if(currentpage !=null && currentpage.length() >0){
            page = Integer.parseInt(currentpage);
        }
        model.addAttribute("page",page);
		return "/page/statisticstemplate/template-manager";
	}
	
	@RequestMapping("/template")
	public String template(HttpServletRequest request,Model model){
        String currentpage = request.getParameter("currentpage");
        String endpage= request.getParameter("endpage");
        int page = 1;
        if(currentpage !=null && currentpage.length() >0){
            page = Integer.parseInt(currentpage);
        }
        model.addAttribute("currentpage", page);
        model.addAttribute("endpage", endpage);
		return "/page/statisticstemplate/template-tongji";
	}
	
	@RequestMapping("/stemplate")
	public String stemplate(HttpServletRequest request,Model model){
        String currentpage = request.getParameter("currentpage");
        String endpage= request.getParameter("endpage");
        int page = 1;
        if(currentpage !=null && currentpage.length() >0){
            page = Integer.parseInt(currentpage);
        }
        model.addAttribute("currentpage", page);
        model.addAttribute("endpage", endpage);
		return "/page/statisticstemplate/template-chaxun";
	}
	
	@RequestMapping("/commontongji")
	public String commontongji(HttpServletRequest request,Model model){
		String statistics_tpl_idx = request.getParameter("req");
		ResultEntity resultEntity = new ResultEntity();
		String req="{\"statistics_tpl_idx\":\""+statistics_tpl_idx+"\"}";
		try {
			resultEntity = statisticsConfigService.queryOneStatisticsConfig(req);
			Object result = resultEntity.getResult();
			if(result !=null && !"".equals(String.valueOf(result).trim())){
				JSONObject jsonObject=JSONObject.fromObject(result);
				if(!jsonObject.isEmpty()&&jsonObject.containsKey("statistics_tpl_type")){
					int type = jsonObject.optInt("statistics_tpl_type");
					if(type==1){
						model.addAttribute("statistics_tpl_idx",statistics_tpl_idx);
						return "/page/statisticstemplate/common-chaxun";
					}else if(type==2){
						model.addAttribute("statistics_tpl_idx",statistics_tpl_idx);
						return "/page/statisticstemplate/common-tongji";
					}
				}else{
					
					//System.out.println("0000000000000000000000");
					System.out.println("commontongji.queryOneStatisticsConfig 结果集中statistics_tpl_type字段不存在");
				}
			}else{
				//System.out.println("1111111111111111111111111111111");
				System.out.println("commontongji.queryOneStatisticsConfig 结果集为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return "";
	}

}
