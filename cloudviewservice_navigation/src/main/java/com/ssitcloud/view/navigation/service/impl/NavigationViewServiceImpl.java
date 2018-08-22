package com.ssitcloud.view.navigation.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.libraryinfo.entity.BookUnionEntity;
import com.ssitcloud.navigation.entity.NavigationInfoEntity;
import com.ssitcloud.view.common.exception.CommonException;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.LogUtils;
import com.ssitcloud.view.navigation.service.NavigationViewService;

@Service
public class NavigationViewServiceImpl extends BasicServiceImpl implements NavigationViewService {
	private static final  String URL_QUERYALLBOOKITEMANDBOOKINFOBYCODE = "queryAllBookitemAndBookInfoByCode";
	private static final  String URL_EXPORTBOOKSHELFLAYER = "exportBookshelflayer";
	private static final  String URL_selLibraryByIdxOrId = "selLibraryByIdxOrId";


	@Override
	public NavigationInfoEntity queryInfoByParam(String libId ,String shelflayer_barcode) {
		Map<String, Object> mapb = new HashMap<>();
		mapb.put("lib_id", libId);
		mapb.put("shelflayer_barcode", shelflayer_barcode);
		Map<String, String> params = new HashMap<>();
		params.put("req", JsonUtils.toJson(mapb));
		String resultb = HttpClientUtil.doPost(requestURL.getRequestURL(URL_EXPORTBOOKSHELFLAYER), params, Consts.UTF_8.toString());
		JsonNode node = null;
		try {
			node=JsonUtils.readTree(resultb);
		} catch (Exception e) {
			throw new CommonException(e);
		}
		if (!node.get("state").booleanValue()) {
			throw new CommonException("查询3d导航信息出错!!!");
		}
		JsonNode resultNode = node.get("result");
		List<NavigationInfoEntity> cmds = JsonUtils.fromNode(resultNode,new TypeReference<List<NavigationInfoEntity>>() {});
		if (cmds == null || cmds.size() == 0) {
			LogUtils.info("查询不到3d导航信息!!!\n");
			return null;
		}
		return cmds.get(0);
	}

	@Override
	public BookUnionEntity queryBookInfo(String lib_idx ,String bookBarCode) {
		BookUnionEntity bookitem = new BookUnionEntity();
		Map<String, String> map = new HashMap<>();
		map.put("lib_idx", lib_idx);
		map.put("book_barcode", bookBarCode);
		Map<String, String> param = new HashMap<>();
		param.put("req", JsonUtils.toJson(map));
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_QUERYALLBOOKITEMANDBOOKINFOBYCODE), param, Consts.UTF_8.toString());
		if (result != null && JSONUtils.mayBeJSON(result)) {
			ResultEntityF<BookUnionEntity> rs = JsonUtils.fromJson(result, new TypeReference<ResultEntityF<BookUnionEntity>>() {});
			if (rs != null) {
				if(!rs.getState()){
					throw new CommonException("查询图书在架信息出错!!!");
				}
				if(rs.getResult() == null){
					LogUtils.info("查询不到图书在架信息!!!\n");
					return null;
				}
				bookitem = rs.getResult();
			}
		}
		//bookitem.setLib_name(lib_name);
		return bookitem;
	}
	
	@Override
	public Map<String,String> getLibIdx(String libId){
		Map<String,String> map = new HashMap<>();
		Map<String,String> libraryIdMap=new HashMap<>();
		libraryIdMap.put("json", "{\"lib_id\":\""+libId+"\"}");
		if(libId != null && !"".equals(libId)){
			String resLibrary=HttpClientUtil.doPost(requestURL.getRequestURL(URL_selLibraryByIdxOrId), libraryIdMap, charset);
			if(resLibrary != null && !"".equals(resLibrary)){
				JsonNode libNode=JsonUtils.readTree(resLibrary);
				if(libNode!=null){										   //library_idx
					JsonNode libIdxNode=libNode.get("result");
					if(libIdxNode!=null && !libIdxNode.isNull()){
						String library_Idx=libIdxNode.get("library_idx").asText();//library_idx
						if(library_Idx!=null){
							map.put("lib_idx", library_Idx);
						}
						String lib_name=libIdxNode.get("lib_name").asText();//library_name
						if(lib_name!=null){
							map.put("lib_name", lib_name);
						}
					}
				}
			}
		}
		return map;
	}
}
