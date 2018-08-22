package com.ssitcloud.business.statistics.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.statistics.common.utils.ExceptionHelper;
import com.ssitcloud.business.statistics.service.CommonEsStatisticService;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 统计查询公共接口
 * 
 * @author yeyalin 2017-08-21
 */
@Controller
@RequestMapping(value = { "comelasticsearch" })
public class CommonElasitcsearchController {

	@Resource
	private CommonEsStatisticService commonEsStatisticService;

	/**
	 * 更具条件获取统计数据
	 * @param request
	 * @param req
	 * @return
	 * 请求参数格式：
	 req={
		  "indexName": "lzulib_*_reader_circulation_log",//索引名称
		  "groupCondition": "academy,readerType,grade",//聚合分组
		  "searchCondition": {
		    "opercmd":"00010201",
		    "lib_id": "LZULIB"
		  },
		  "topHits": 3,
		  "timeConditin": {
		    "field": "opertime",
		    "start": "20170101",
		    "end": "20180306",
		    "format": "yyyyMMdd"
		  },
		  "exclusiveConditon":{
		  	"lib_idx":"XXX",
		  	"lib_id":"XXXX"
		  }
		}
	 */
	@RequestMapping(value = { "getData" })
	@ResponseBody
	public ResultEntity getData(HttpServletRequest request, String req) {
		ResultEntity  result = new ResultEntity();
		try{
			result =commonEsStatisticService.getData(req) ;
		} catch (Exception e) {
            ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
		return result;
	}
}
