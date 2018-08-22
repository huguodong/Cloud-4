package com.ssitcloud.dblib.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import net.sf.json.util.JSONUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.service.impl.BasicServiceImpl;
import com.ssitcloud.dblib.common.utils.HttpClientUtil;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.service.TransferFileService;
@Service
public class TransferFileServiceImpl extends BasicServiceImpl implements TransferFileService {

	private static final String URL_NOTIFYLIBRARYINFO = "notifyLibraryInfo";
	
	@Override
	public ResultEntity transfer(Map<String,String> map) {
		ResultEntity result = new ResultEntity();
		String filePath = map.get("filePath");
		String libId = map.get("libId");
		String deviceId = map.get("deviceId");
		File localFile = new File(filePath);
		String url = requestURL.getRequestURL("transferToBusinessDatasync");
		String res= HttpClientUtil.postUpload(url, localFile,libId,deviceId);
		System.out.println("libraryinfo上传文件到bussinessDatasync层"+ res);
    	if(JSONUtils.mayBeJSON(res)){
    		 result=JsonUtils.fromJson(res, ResultEntity.class);
    	}
    	return result;
	}

	@Override
	public ResultEntity notifyLibraryInfo(String req) {
		ResultEntity result = new ResultEntity();
		Map<String, String> param = new HashMap<>();
		param.put("req", req);
		String operResult = HttpClientUtil.doPost(requestURL.getRequestURL(URL_NOTIFYLIBRARYINFO), param, charset);
		if (StringUtils.hasText(operResult)) {
			result = JsonUtils.fromJson(operResult, ResultEntity.class);
		}
		return result;
	}
	
	public static void main(String[] args) {
		
		Map map = new HashMap<String, String>();
		map.put("req", "123");
		//HttpClientUtil.doPost("http://localhost:8080/cloudbusinessservice_datasyn/sync/transferToBusinessDatasync", map, "utf-8");

		String url = "http://127.0.0.1:8080/cloudbusinessservice_datasyn/sync/transferToBusinessDatasync";
		
		File localFile = new File("/usr/libraryInfoFile/bookitem/download/bookitem_HHTEST_DH_TEST_20171228095906.txt");
		
		String libId = "HHTEST";
		
		String deviceId = "DH_TEST";
		
		String res = HttpClientUtil.postUpload(url, localFile,libId,deviceId);
		
		if(JSONUtils.mayBeJSON(res)){
   		 	ResultEntity result=JsonUtils.fromJson(res, ResultEntity.class);
   		 	if(result.getState()){
   		 	String fps = String.valueOf(result.getResult());
   		 	String url1 = "http://localhost:8080/cloudbusinessservice_datasyn/sync/notifyLibraryInfo";
	 		String req = "{\"fileType\":\"txt\",\"tableName\":\"bookitem\",\"filePath\":\"/admin/download\",\"fps\":\"/usr/libraryInfoFile/bookitem/download/bookitem_HHTEST_DH_TEST_20171228095906.txt\",\"fileName\":\"bookitem_HHTEST_DH_TEST_20171228095906.txt\",\"lib_id\":\"HHTEST\",\"device_id\":\"DH_TEST\",\"fullPath\":\"ftp://172.16.0.112/admin/download/\",\"tableField\":\"author,title,shelflayer_barcode,book_barcode,ISBN,publish,book_uid\"}";
	 		
	 		Map<String, String> param = new HashMap<>();
	 		param.put("req", req);
	 		String operResult = HttpClientUtil.doPost(url1, param, "utf-8");
   		 	}
		}
		
		
	}
	
}
