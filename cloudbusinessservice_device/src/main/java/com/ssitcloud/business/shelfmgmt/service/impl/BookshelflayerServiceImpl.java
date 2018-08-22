package com.ssitcloud.business.shelfmgmt.service.impl;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.shelfmgmt.service.BookshelflayerService;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class BookshelflayerServiceImpl extends BasicServiceImpl implements BookshelflayerService {

	private static final String URL_queryAllBookshelflayer = "queryAllBookshelflayer";
	private static final String URL_deleteBookshelflayer = "deleteBookshelflayer";
	private static final String URL_updateBookshelflayer = "updateBookshelflayer";
	private static final String URL_addBookshelflayer = "addBookshelflayer";
	private static final String URL_exportBookshelflayer = "exportBookshelflayer";
	private static final String URL_uploadBookshelflayer = "uploadBookshelflayer";
	
	@Resource(name="requestURL")
	protected  RequestURLListEntity requestURL;
	
	@Override
	public ResultEntity queryAllBookshelflayer(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_queryAllBookshelflayer, req);
	}

	@Override
	public ResultEntity updateBookshelflayer(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_updateBookshelflayer, req);
	}

	@Override
	public ResultEntity deleteBookshelflayer(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_deleteBookshelflayer, req);
	}

	@Override
	public ResultEntity addBookshelflayer(String req) {
		// TODO Auto-generated method stub
		return postUrl(URL_addBookshelflayer, req);
	}
	
	@Override
	public ResultEntity exportBookshelflayer(String req){
		// TODO Auto-generated method stub
		return postUrl(URL_exportBookshelflayer, req);
	}
	
	@Override
	public  ResultEntity uploadBookshelflayer(String req){
		ResultEntity result = new ResultEntity();
		Map<String,String> map1 = JsonUtils.fromJson(req, Map.class);
		String filePath = map1.get("filePath");
		String libId = map1.get("libId");
		File localFile = new File(filePath);
		String url = requestURL.getRequestURL("uploadShelfInfoExcelFile");
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
		return postUrl(URL_uploadBookshelflayer, req);
	}

}
