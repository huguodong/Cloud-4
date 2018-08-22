package com.ssitcloud.business.shelfmgmt.service.impl;

import java.io.File;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.shelfmgmt.service.BookshelfService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class BookshelfServiceImpl extends BasicServiceImpl implements BookshelfService {

	private static final String URL_queryAllBookshelf = "queryAllBookshelf";
	private static final String URL_queryBookshelfById = "queryBookshelfById";
	private static final String URL_deleteBookshelf = "deleteBookshelf";
	private static final String URL_updateBookshelf = "updateBookshelf";
	private static final String URL_addBookshelf = "addBookshelf";
	private static final String URL_updateShelfimage = "updateShelfimage";
	private static final String URL_updateFloorimage = "updateFloorimage";
	private static final String URL_uploadPoint = "uploadPoint";
	
	@Override
	public ResultEntity queryAllBookshelf(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_queryAllBookshelf, req);
	}
	
	@Override
	public ResultEntity queryBookshelfById(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_queryBookshelfById, req);
	}
	
	@Override
	public ResultEntity updateBookshelf(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_updateBookshelf, req);
	}

	@Override
	public ResultEntity deleteBookshelf(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_deleteBookshelf, req);
	}

	@Override
	public ResultEntity addBookshelf(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_addBookshelf, req);
	}
	
	@Override
	public ResultEntity updateShelfimage(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_updateShelfimage, req);
	}

	@Override
	public ResultEntity updateFloorimage(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_updateFloorimage, req);
	}
	
	@Override
	public ResultEntity uploadPoint(String req) {
		ResultEntity result = new ResultEntity();
		Map<String,String> map1 = JsonUtils.fromJson(req, Map.class);
		String filePath = map1.get("filePath");
		String libId = map1.get("libId");
		File localFile = new File(filePath);
		String url = requestURL.getRequestURL("uploadPointFile");
		String res= HttpClientUtil.postUpload(url, localFile,libId);
		System.out.println("上传文件到db层"+ res);
    	if(JSONUtils.mayBeJSON(res)){
    		 result=JsonUtils.fromJson(res, ResultEntity.class);
    		 filePath = String.valueOf(result.getResult());
    		 map1.remove("filePath");
    		 map1.put("filePath", filePath);
    		 req = JsonUtils.toJson(map1);
    	}else{
    		return result;
    	}
		return postUrl(URL_uploadPoint, req);
	}

}
