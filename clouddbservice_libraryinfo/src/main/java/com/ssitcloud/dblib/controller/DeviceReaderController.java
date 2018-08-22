package com.ssitcloud.dblib.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.LibraryEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.service.impl.BasicServiceImpl;
import com.ssitcloud.dblib.common.utils.HttpClientUtil;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.common.utils.LogUtils;
import com.ssitcloud.dblib.entity.ReaderCardEntity;
import com.ssitcloud.dblib.service.DeviceReaderService;
import com.ssitcloud.dblib.service.ReaderCardService;

@Controller
@RequestMapping("/devicereader")
public class DeviceReaderController  extends BasicServiceImpl {

	@Resource
	private DeviceReaderService deviceReaderService;
	
	@Resource
	private ReaderCardService readerCardService;
	
	/**
	 * 保存读者数据
	 *
	 *
	 *{
	 *		"library_idx":"16",
		    "device_id":"STA001",
		    "library_id":"LIB001",
		    "content":{
		        "opertime":"2017-03-22 15:58:16",
		        "operator":"admin",
		        "opercmd":"00010301",
		        "operresult":"Y",
		        "AuthCardno":"004555",
		        "AuthType":"N", 认证类型
		        "Cardtype":"1", 卡类型
		        "ExpireDate":"20180303",  有效期
		        "PrivilegeFee":"100", 预付款
		        "name":"杨三三",
		        "Birth":"20010101",
		        "Gender":"1",  1 男  0女
		        "EthnicGroup":"汉",
		        "Address":"China",
		        "E-mail":"qq@qq.com",
		        "Telephone":"77777777",
		        "Mobile":"13000000000",
		        "collection":"cardissue_log"
		    }
		}
	 * <p>2017年4月20日 下午2:15:18 
	 * <p>create by hjc
	 * @param request
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveDeviceReaderInfo")
	@ResponseBody
	public ResultEntity saveDeviceReaderInfo(HttpServletRequest request,String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isNotBlank(req)) {
				Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
				if (!map.containsKey("library_idx")) {
					String library_id =(String) map.get("library_id");
					if(library_id!=null&&!"".equals(library_id)){
						Map<String, String> libMap = new HashMap<String, String>();
						libMap.put("json","{\"lib_id\":\""+library_id+"\"}");
						String resp = HttpClientUtil.doPost(requestURL.getRequestURL("selLibraryByIdxOrId"), libMap, Consts.UTF_8.toString());
						ResultEntity libResultEntity = JsonUtils.fromJson(resp, ResultEntity.class);
						if (libResultEntity.getState()) {
							Map<String,Object> resultMap = (Map<String, Object>) libResultEntity.getResult();
							LibraryEntity libraryInfo = JsonUtils.convertMapToObject(resultMap,LibraryEntity.class);
							//LibraryEntity libraryInfo = (LibraryEntity) libResultEntity.getResult();
							library_id = libraryInfo.getLib_name();
							map.put("library_idx", libraryInfo.getLibrary_idx());
						}
					}else{
						resultEntity.setValue(false, "缺少library_idx参数", "", "");
						return resultEntity;
					}
				}
				if(map.containsKey("content")){
					Map<String, Object> content = JsonUtils.fromJson(JsonUtils.toJson(map.get("content")), Map.class);
					//DeviceReaderEntity deviceReaderEntity = new DeviceReaderEntity();
					ReaderCardEntity readerCardEntity = new ReaderCardEntity();
					readerCardEntity.setResource("0");
					readerCardEntity.setCard_no(content.get("AuthCardno")+"");
					readerCardEntity.setActual_card_no(content.get("AuthCardno")+"");
					readerCardEntity.setCard_type(content.get("Cardtype")+"");
					readerCardEntity.setMobile((content.get("Mobile")+""));
					readerCardEntity.setId_card((content.get("operator")+""));
					DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");   
					String expire_date = content.get("ExpireDate")+"";
					try{
						expire_date = expire_date.split("-")[1];
						String[] expireDate = expire_date.split("\\.");
						String expire = expireDate[0]+"-"+expireDate[1]+"-"+expireDate[2];
						readerCardEntity.setExpire_date(format1.parse(expire));;
					}catch(Exception e){
						System.out.println("expire_date日期传递错误!");
					}
					
					try {
					readerCardEntity.setAdvance_charge(Double.valueOf(content.get("PrivilegeFee")+""));
					} catch (Exception e) {
						readerCardEntity.setAdvance_charge(0);
					}
					
					readerCardEntity.setReader_name(content.get("Name")+"");
					String birth = content.get("Birth")+"";
					String year = birth.substring(0,4);
					String month = birth.substring(4,6);
					String day = birth.substring(6,8);
					birth = year+"-"+month+"-"+day;
					readerCardEntity.setBirth(format1.parse(birth));
					if ("男".equals(content.get("Gender")+"")) {
						readerCardEntity.setGender("男");
					}
					if ("女".equals(content.get("Gender")+"")) {
						readerCardEntity.setGender("女");
					}
					readerCardEntity.setAddress(content.get("Address")+"");
					readerCardEntity.setLib_idx(Integer.valueOf(map.get("library_idx")+""));
					
					/*deviceReaderEntity.setAuthcardno(content.get("AuthCardno")+"");
					deviceReaderEntity.setAuthtype(content.get("AuthType")+"");
					deviceReaderEntity.setCardtype(content.get("Cardtype")+"");
					deviceReaderEntity.setExpiredate(content.get("ExpireDate")+"");
					try {
						deviceReaderEntity.setPrivilegefee(Double.valueOf(content.get("PrivilegeFee")+""));
					} catch (Exception e) {
						deviceReaderEntity.setPrivilegefee(0);
					}
					deviceReaderEntity.setName(content.get("Name")+"");
					deviceReaderEntity.setBirth(content.get("Birth")+"");
					if ("1".equals(content.get("Gender")+"")) {
						deviceReaderEntity.setGender("M");
					}
					if ("0".equals(content.get("Gender")+"")) {
						deviceReaderEntity.setGender("F");
					}
					deviceReaderEntity.setEthnicgroup(content.get("EthnicGroup")+"");
					deviceReaderEntity.setAddress(content.get("Address")+"");
					
					deviceReaderEntity.setLib_idx(Integer.valueOf(map.get("library_idx")+""));*/
					
					//int ret = deviceReaderService.countReaderByLibIdxAndCardno(deviceReaderEntity);
					int ret = readerCardService.countReaderByLibIdxAndCardno(readerCardEntity);
			
					int ret1 = 0;
					if (ret>=1) {
						ret1 = readerCardService.updateReaderCard(readerCardEntity);
						//ret1 = deviceReaderService.updateDeviceReaderInfo(deviceReaderEntity);
					}else {
						ret1 = readerCardService.insertReaderCard(readerCardEntity);
						//ret1 = deviceReaderService.saveDeviceReaderInfo(deviceReaderEntity);
					}
					if (ret1>=1) {
						resultEntity.setValue(true, "success");
					}else{
						resultEntity.setValue(true, "failed");
					}
				}else {
					resultEntity.setValue(false, "参数不完整");
				}
			}
		} catch (Exception e) {
			LogUtils.error("",e);
			resultEntity.setValue(false, e.getMessage());
		}
		return resultEntity;
	}
}
