package com.ssitcloud.navigation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.navigation.entity.NavigationInfoEntity;
import com.ssitcloud.navigation.service.NavigationService;


@Controller
@RequestMapping(value = { "navigation" })
public class NavigationController {

	@Resource
	private NavigationService navigationService;
	
	/** 导航界面加载入口 */
	@RequestMapping(value = { "/{bookBarCode}" })
	@ResponseBody
	public ModelAndView main(HttpServletRequest request,@PathVariable String bookBarCode) {
		
		NavigationInfoEntity navigationInfoEty = navigationService.queryInfoByParam(bookBarCode);
		
		//查询不到数据或者查询过程报错 跳转到404界面
		if(navigationInfoEty == null){
			return new ModelAndView("/page/error/noFound", null);
		}
		
		List<String[]> points = new ArrayList<>();
		if(navigationInfoEty.getShelfpoint() != null){
			String[] pointA=navigationInfoEty.getShelfpoint().split("-");
			if(pointA.length > 0){
				for(int i=0;i<pointA.length;i++){
					String str = pointA[i];
					int a = str.indexOf(",");
					if(a>0){
						String x = str.substring(1, a);
						String y = str.substring(a+1, str.length()-1);
						String[] pt = {x,y};
						points.add(pt);
					}
				}
			}
		}
		
		String[] locations = null;
		if(navigationInfoEty.getInfo_value() != null){
			locations = navigationInfoEty.getInfo_value().split(",");
		}

		Map<String, Object> model = new HashMap<>();
		model.put("locations", locations);
		model.put("points", points);
		model.put("navigationInfoEty", navigationInfoEty);
		return new ModelAndView("/page/navigation/dispalymap", model);
	}
	
	@RequestMapping(value = { "/getLocationData" })
	@ResponseBody
	public ResultEntity getLocationData(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		Map map = JsonUtils.fromJson(req, Map.class);
		
		String bookBarCode = (String) map.get("book_barcode");
		
		if(bookBarCode == null || "".equals(bookBarCode)){
			result.setState(false);
			return result;
		}
		
		NavigationInfoEntity navigationInfoEty = navigationService.queryInfoByParam(bookBarCode);
		
		if(navigationInfoEty == null){
			result.setState(false);
			return result;
		}
		
		String[] locations = null;
		if(navigationInfoEty.getInfo_value() != null){
			locations = navigationInfoEty.getInfo_value().split(",");
			navigationInfoEty.setLocations(locations);
			result.setState(true);
			result.setResult(navigationInfoEty);
			return result;
		}

		result.setState(false);
		return result;
	}

}
