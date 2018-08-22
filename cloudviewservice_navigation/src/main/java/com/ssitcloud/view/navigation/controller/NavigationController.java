package com.ssitcloud.view.navigation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.libraryinfo.entity.BookUnionEntity;
import com.ssitcloud.navigation.entity.NavigationInfoEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.navigation.service.NavigationViewService;

@Controller
@RequestMapping(value = { "bookcode" })
public class NavigationController extends BasicController {

	@Resource
	private NavigationViewService navigationViewService;
	
	/** 导航界面加载入口 */
	@RequestMapping(value = { "/{libId}/{bookBarCode}" })
	@ResponseBody
	public ModelAndView main(HttpServletRequest request, @PathVariable String libId ,@PathVariable String bookBarCode) {
		
		Map<String,String> map = navigationViewService.getLibIdx(libId);
		if(map == null || map.get("lib_idx") == null){
			return new ModelAndView("/page/error/noFound", null);
		}
		String lib_idx = map.get("lib_idx");
		String lib_name = map.get("lib_name");
		
		BookUnionEntity bookitemEntity = navigationViewService.queryBookInfo(lib_idx,bookBarCode);
		if(bookitemEntity == null || StringUtils.isEmpty(bookitemEntity.getBook_barcode()) || StringUtils.isEmpty(bookitemEntity.getShelflayer_barcode())){
			return new ModelAndView("/page/error/noFound", null);
		}
		
		NavigationInfoEntity navigationInfoEty = navigationViewService.queryInfoByParam(libId,bookitemEntity.getShelflayer_barcode());
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
					if(str != null && !"".equals(str)){
						int a = str.indexOf(",");
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
		model.put("bookitemEntity", bookitemEntity);
		model.put("navigationInfoEty", navigationInfoEty);
		model.put("lib_name", lib_name);
		model.put("lib_id", libId);
		return new ModelAndView("/page/navigation", model);
	}

}
