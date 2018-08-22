package com.ssitcloud.business.statisticsmgmt.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.statisticsmgmt.service.StatisticsTemplateService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value = { "statisticsTemplate" })
public class StatisticsTemplateController {
	@Resource
	private StatisticsTemplateService statisticsTemplateService;
	
	/**
	 * 查找统计的数据源
	 * author huanghuang
	 * 2017年3月16日 上午9:28:53
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"takeDataSource"})
	@ResponseBody
	public ResultEntity takeDataSource(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsTemplateService.takeDataSource(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查找统计的图书馆藏地
	 * 格式
	 * json = {
	 * 		"location_idx":"",//图书馆藏地主键，自增
	 * 		"lib_idx":"",
	 * 		"location_code":"",
	 * 		"location_name":"",
	 * 		"location_mark":""
	 * } 
	 * author huanghuang
	 * 2017年3月17日 上午11:35:43
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectBookLocations"})
	@ResponseBody
	public ResultEntity selectBookLocations(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsTemplateService.selectBookLocations(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 查找统计的图书流通类型
	 * 格式
	 * json = {
	 * 		"cirtype_idx":"",//图书流通类型主键，自增
	 * 		"lib_idx":"",
	 * 		"cirtype_code":"",
	 * 		"cirtype_name":"",
	 * 		"cirtype_mark":""
	 * }
	 * author huanghuang
	 * 2017年3月17日 上午11:36:15
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectBookCirtypes"})
	@ResponseBody
	public ResultEntity selectBookCirtypes(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsTemplateService.selectBookCirtypes(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查找统计的图书载体
	 * 格式
	 * json = {
	 * 		"media_idx":"",//图书载体类型主键，自增
	 * 		"lib_idx":"",
	 * 		"media_code":"",
	 * 		"media_name":"",
	 * 		"media_mark":""
	 * } 
	 * author huanghuang
	 * 2017年3月17日 上午11:36:37
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectBookMediatypes"})
	@ResponseBody
	public ResultEntity selectBookMediatypes(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsTemplateService.selectBookMediatypes(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查找统计的读者类型
	 * 格式
	 * json = {
	 * 		"library_idx":""
	 * }
	 * author huanghuang
	 * 2017年3月17日 上午11:36:57
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectReadertype"})
	@ResponseBody
	public ResultEntity selectReadertype(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsTemplateService.selectReadertype(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查找统计主函数
	 * author huanghuang
	 * 2017年3月17日 上午11:36:57
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectFunMaindatas"})
	@ResponseBody
	public ResultEntity selectFunMaindatas(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsTemplateService.selectFunMaindatas(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 查找统计子函数
	
	 * author huanghuang
	 * 2017年3月17日 上午11:36:57
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectFunSubdatas"})
	@ResponseBody
	public ResultEntity selectFunSubdatas(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsTemplateService.selectFunSubdatas(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 新增统计查询模板配置StatisticsTemplateEntity
	 * 格式
	 * json = {
	 * 		"statistics_tpl_idx":"",//统计查询模板配置主键，自增
	 * 		"statistics_tpl_desc":""
	 * }
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addStatisticsTemplate"})
	@ResponseBody
	public ResultEntity addStatisticsTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsTemplateService.insertStatisticsTemplate(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 修改统计查询模板配置StatisticsTemplateEntity
	 * 格式
	 * json = {
	 * 		"statistics_tpl_idx":"",//统计查询模板配置主键，自增
	 * 		"statistics_tpl_desc":""
	 * }
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateStatisticsTemplate"})
	@ResponseBody
	public ResultEntity updateStatisticsTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsTemplateService.updateStatisticsTemplate(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除统计查询模板配置StatisticsTemplateEntity
	 * 格式
	 * json = {
	 * 		"statistics_tpl_idx":"",//统计查询模板配置主键，自增
	 * 		"statistics_tpl_desc":""
	 * }
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteStatisticsTemplate"})
	@ResponseBody
	public ResultEntity deleteStatisticsTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsTemplateService.deleteStatisticsTemplate(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询一条统计查询模板配置记录StatisticsTemplateEntity
	 * 格式
	 * json = {
	 * 		"statistics_tpl_idx":"",//统计查询模板配置主键，自增
	 * 		"statistics_tpl_desc":""
	 * }
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectStatisticsTemplate"})
	@ResponseBody
	public ResultEntity selectStatisticsTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsTemplateService.queryOneStatisticsTemplate(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询多条统计查询模板配置记录StatisticsTemplateEntity
	 * 格式
	 * json = {
	 * 		"statistics_tpl_idx":"",//统计查询模板配置主键，自增
	 * 		"statistics_tpl_desc":""
	 * }
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectStatisticsTemplates"})
	@ResponseBody
	public ResultEntity selectStatisticsTemplates (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsTemplateService.queryStatisticsTemplates(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 统计查询模板配置StatisticsTemplateEntity分页查询
	 * author lqw
	 * 2017年3月31日 
	 * @param statisticsTemplateEntity
	 * @return
	 */
	@RequestMapping(value={"selectStatisticsTemplatePage"})
	@ResponseBody
	public ResultEntity selectStatisticsTemplatePage (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsTemplateService.selectStatisticsTemplatePage(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	/**
	 * 动态拼接sql查询
	 * author huanghuang
	 * 2017年4月6日 上午10:54:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectBySql"})
	@ResponseBody
	public ResultEntity selectBySql (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String sql = request.getParameter("req");
			resultEntity = statisticsTemplateService.selectBySql(sql);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

    /**
     * 动态拼接sql查询鉴权库
     * author lqw
     * 2017年8月29日 上午10:55:23
     */
    @RequestMapping(value={"selectAutBySql"})
    @ResponseBody
    public ResultEntity selectAutBySql (HttpServletRequest request){
        ResultEntity resultEntity = new ResultEntity();
        try {
            String sql = request.getParameter("req");
            resultEntity = statisticsTemplateService.selectAutBySql(sql);
        } catch (Exception e) {
            String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
            ExceptionHelper.afterException(resultEntity, methodName, e);
        }
        return resultEntity;
    }
    /**
     *根据登录用户获取模板
     * lqw 2017/09/11
     */
    @RequestMapping(value={"selectTemplateMenuByOperidx"})
    @ResponseBody
    public ResultEntity selectTemplateMenuByOperidx (HttpServletRequest request){
        ResultEntity resultEntity = new ResultEntity();
        try {
            String req = request.getParameter("req");
            resultEntity = statisticsTemplateService.selectTemplateMenuByOperidx(req);
        } catch (Exception e) {
            String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
            ExceptionHelper.afterException(resultEntity, methodName, e);
        }
        return resultEntity;
    }

}
