package com.ssitcloud.business.mobile.service.impl;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ssitcloud.authentication.entity.LibraryInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.mobile.common.util.Base64Helper;
import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.common.util.RsaHelper;
import com.ssitcloud.business.mobile.common.util.StringUtils;
import com.ssitcloud.business.mobile.service.LibraryServiceI;
import com.ssitcloud.business.mobile.service.PasswordServiceI;
import com.ssitcloud.business.mobile.service.PublicKeyServiceI;
import com.ssitcloud.business.mobile.service.ReaderCardServiceI;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.ReaderCardEntity;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年3月1日 下午4:58:53
 */
@Service
public class LibraryServiceImpl implements LibraryServiceI {
	private final String URL_SELECT_PK = "selLibraryByIdxOrId";
	private final String URL_SELECT_S = "selLibraryByIdxsOrIds";
	private final String URL_SELECT_X = "sellibraryinfo";
	private final String URL_querySlaveLibraryByMasterIdx = "querySlaveLibraryByMasterIdx";
	private final String URL_selLibraryByIdxOrId = "selLibraryByIdxOrId";
	private final String URL_queryAllMasterLibAndSlaveLib = "queryAllMasterLibAndSlaveLib";
	private final String URL_selectChildIdxAndRegionCode = "selectChildIdxAndRegionCode";
	private final String URL_sendbarcode = "recevieAppData";

	@Resource(name = "requestURL")
	private RequestURLListEntity requestURLEntity;
	private static final String charset = "utf-8";

	@Autowired
	private PasswordServiceI passwordService;
	
	@Autowired
	private PublicKeyServiceI publicKeyService;

	@Override
	public Map<String, Object> selectLibraryByPk(Integer lib) {
		String url = requestURLEntity.getRequestURL(URL_SELECT_PK);
		Map<String, String> map = new HashMap<>(3);
		map.put("json", "{\"library_idx\":" + lib + "}");
		try {
			String remoteJson = HttpClientUtil.doPost(url, map, charset);
			ResultEntity resultEntity = JsonUtils.fromJson(remoteJson, ResultEntity.class);
			if (remoteJson == null || !resultEntity.getState()) {
				LogUtils.error("从" + url + "获取数据失败");
				return null;
			}
			return (Map<String, Object>) resultEntity.getResult();
		} catch (Exception e) {
			LogUtils.error(e);
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> selectLibrarys(List<Integer> libIdList) {
		if (libIdList.isEmpty()) {
			return new ArrayList<>(0);
		}
		String url = requestURLEntity.getRequestURL(URL_SELECT_S);
		Map<String, String> map = new HashMap<>(3);
		StringBuilder sb = new StringBuilder("{\"library_idx\":\"");
		for (int i = 0, z = libIdList.size(); i < z; ++i) {
			sb.append(libIdList.get(i));
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("\"}");
		map.put("json", sb.toString());
		try {
			String remoteJson = HttpClientUtil.doPost(url, map, charset);
			ResultEntity resultEntity = JsonUtils.fromJson(remoteJson, ResultEntity.class);
			if (remoteJson == null || !resultEntity.getState()) {
				LogUtils.error("从" + url + "获取数据失败");
				return null;
			}
			return (List<Map<String, Object>>) resultEntity.getResult();
		} catch (Exception e) {
			LogUtils.error(e);
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> selectLibrarys(Map<String, Object> param) {
		if (param == null) {
			param = new HashMap<>();
		}
		String url = requestURLEntity.getRequestURL(URL_SELECT_X);
		if (param.get("pageSize") == null) {
			param.put("pageSize", Integer.MAX_VALUE);
		}
		if (param.get("pageCurrent") == null) {
			param.put("page", 1);
		} else {
			param.put("page", param.get("pageCurrent"));
			param.remove("pageCurrent");
		}
		Map<String, String> map = new HashMap<>(3);
		map.put("json", JsonUtils.toJson(param));
		try {
			String remoteJson = HttpClientUtil.doPost(url, map, charset);
			ResultEntity resultEntity = JsonUtils.fromJson(remoteJson, ResultEntity.class);
			if (remoteJson == null || !resultEntity.getState()) {
				LogUtils.error("从" + url + "获取数据失败");
				return null;
			}
			Map<String, Object> resultMap = (Map<String, Object>) resultEntity.getResult();
			List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.get("rows");
			return list;
		} catch (Exception e) {
			LogUtils.error(e);
			return null;
		}
	}

	@Override
	public ResultEntity querySlaveLibraryByMasterIdx(String req) {
		ResultEntity resultEntity = new ResultEntity();
		Map<String, Object> p = JsonUtils.fromJson(req, Map.class);
		Integer library_idx = (Integer) p.get("library_idx");
		if (library_idx != null && library_idx.intValue() == 0) {
			List<Map<String, Object>> librarys = selectLibrarys(new HashMap<String, Object>(0));
			//去掉云平台的数据
			Iterator<Map<String, Object>> iterator = librarys.iterator();
			while(iterator.hasNext()){
				Map<String, Object> map = iterator.next();
				if(library_idx.equals(map.get("library_idx"))){
					iterator.remove();
					break;	
				}
			}
			Map<String, Object> r = new HashMap<>();
			r.put("slaveLibrary", librarys);
			resultEntity.setState(true);
			resultEntity.setResult(r);
			return resultEntity;
		}
		String reqURL = requestURLEntity.getRequestURL(URL_querySlaveLibraryByMasterIdx);
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, charset);
		if (result != null && !result.contains(">")) {
			resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
		}
		return resultEntity;
	}

	@Override
	public ResultEntity selectLibraryIdByIdx(String req) {
		ResultEntity resultEntity = new ResultEntity();
		String reqURL = requestURLEntity.getRequestURL(URL_selLibraryByIdxOrId);
		Map<String, String> map = new HashMap<>();
		map.put("json", req);
		String result = HttpClientUtil.doPost(reqURL, map, charset);
		if (result != null && !result.contains(">")) {
			resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
		}
		return resultEntity;
	}

	@Override
	public ResultEntity queryAllMasterLibAndSlaveLib(String req) {
		ResultEntity resultEntity = new ResultEntity();
		String reqURL = requestURLEntity.getRequestURL(URL_queryAllMasterLibAndSlaveLib);
		Map<String, String> map = new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, charset);
		if (result != null && !result.contains(">")) {
			resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
		}
		return resultEntity;
	}

	@Override
	public List<Map<String, Object>> selectChildIdxAndRegionCode(int master_lib_idx) {
		String reqURL = requestURLEntity.getRequestURL(URL_selectChildIdxAndRegionCode);
		Map<String, String> map = new HashMap<>();
		map.put("req", "{\"master_lib_idx\":" + master_lib_idx + "}");
		String result = HttpClientUtil.doPost(reqURL, map, charset);
		try {
			ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
			if (resultEntity.getState()) {
				return (List<Map<String, Object>>) resultEntity.getResult();
			} else {
				LogUtils.info("查询子馆idx和地区码失败，服务器返回" + result);
			}
		} catch (Exception e) {
			LogUtils.info(getClass() + "发生了异常", e);
		}
		return new ArrayList<>(0);
	}

	@Override
	public ResultEntity sendBarcode(String json,ReaderCardServiceI readerCardService) {
		ResultEntity resultEntity = new ResultEntity();
		Map<String, Object> map = null;
		try {
			map = JsonUtils.fromJson(json, Map.class);
		} catch (Exception e) {
			LogUtils.info(getClass() + " 提交数据不合法");
			resultEntity.setState(false);
			resultEntity.setMessage("提交数据不合法");
			return resultEntity;
		}

		if (map != null) {
			Integer reader_idx = (Integer) map.get("reader_idx");
			Integer lib_idx = (Integer) map.get("lib_idx");
			String card_no = (String) map.get("card_no");
			String app_type = String.valueOf(map.get("app_type"));
			String barcode = (String) map.get("barcode");
			if (reader_idx == null || lib_idx == null || barcode == null || card_no == null
					|| !app_type.matches("1|2")) {
				resultEntity.setMessage("check reader_idx or lib_idx or card_no or app_type");
				return resultEntity;
			}
			ResultEntity r = publicKeyService.selectNewPublic(Integer.valueOf(app_type));
			if (!r.getState()) {
				LogUtils.error(getClass()+" 获取app公钥失败");
				return resultEntity;
			}
			String publicKeyXml = (String) ((Map<String, Object>) r.getResult()).get("publickey");
			String publicKeyVersion = (String) ((Map<String, Object>) r.getResult()).get("key_version");

			ReaderCardEntity readerCardEntity = new ReaderCardEntity();
			readerCardEntity.setReader_idx(reader_idx);
			readerCardEntity.setCard_no(card_no);
			readerCardEntity.setLib_idx(lib_idx);
			List<Map<String, Object>> readerCards = readerCardService.selectReaderCards(readerCardEntity);
			if (!readerCards.isEmpty()) {
				Map<String, Object> readerCard = readerCards.get(0);
				String miPwd = (String) readerCard.get("card_password");
				if (miPwd != null) {
					String mingPwd = passwordService.decrypt(miPwd);
					if (mingPwd != null) {
						// 准备发送数据
						String[] barcodeDatas = StringUtils.split(barcode, "|");
						int length = barcodeDatas.length;
						if (length < 2) {
							resultEntity.setMessage("二维码格式不正确");
						} else {
							String lib_id = barcodeDatas[0];
							String device_id = barcodeDatas[1];
							String bdata;
							if (length > 3) {
								bdata = barcodeDatas[3];
							} else {
								bdata = "";
							}
							long times = System.currentTimeMillis();
							String ming = times + "|" + mingPwd;
							String mingMd5 = StringUtils.getMd5(ming, true, false);
							ming = mingMd5+"|" +ming;
							PublicKey pubKey = RsaHelper.decodePublicKeyFromXml(publicKeyXml);
							String mi = Base64Helper.encode(RsaHelper.encryptData(ming.getBytes(), pubKey));

							StringBuilder sendData = new StringBuilder(256);
							sendData.append(app_type).append('|')// APP版本
									.append(publicKeyVersion).append('|')// 公钥版本
									.append(lib_id).append('|')// 图书馆id
									.append(card_no).append('|')// 读者证号
									.append('|')// 图书列表
									.append(mi).append('|')// 密文
									.append(bdata);// 设备数据

							Map<String, Object> param = new HashMap<>();
							param.put("device_id", device_id);
							param.put("library_id", lib_id);
							param.put("app_data", sendData.toString());

							String url = requestURLEntity.getRequestURL(URL_sendbarcode);

							Map<String, String> reqMap = new HashMap<>(1, 1.0f);
							reqMap.put("req", JsonUtils.toJson(param));

							String reJson = HttpClientUtil.doPost(url, reqMap, charset);
							try {
								ResultEntity re = JsonUtils.fromJson(reJson, ResultEntity.class);
								resultEntity.setState(re.getState());
								LogUtils.debug(getClass() + " 发送数据到datasync返回结果:"+re.getMessage());
								return resultEntity;
							} catch (Exception e) {
								LogUtils.info(getClass() + " 发送数据到datasync失败", e);
							}
						}
					} else {
						LogUtils.error(getClass() + " 解密读者密码失败");
					}
				} else {
					LogUtils.info(getClass() + " 获取读者密码失败");
				}
			}
		}

		return resultEntity;
	}

    @Override
    public List<LibraryInfoEntity> selectLibraryInfo(LibraryInfoEntity param) {
        LibraryInfoEntity selectParam = param != null?param:new LibraryInfoEntity();

        String url = requestURLEntity.getRequestURL("selectLibraryInfo");
        Map<String,String> m = new HashMap<>(3);
        m.put("req",JsonUtils.toJson(selectParam));
        String s = HttpClientUtil.doPost(url, m, charset);
        if(s != null){
            ResultEntity r = JsonUtils.fromJson(s,ResultEntity.class);
            if(r.getState()){
                List<Map<String,Object>> resultData = (List<Map<String, Object>>) r.getResult();
                List<LibraryInfoEntity> returnData = new ArrayList<>(resultData.size());
                for (Map<String, Object> resultDatum : resultData) {
                    LibraryInfoEntity lie = JsonUtils.fromJson(JsonUtils.toJson(resultDatum),LibraryInfoEntity.class);
                    returnData.add(lie);
                }
                return returnData;
            }else{
                LogUtils.error(getClass()+" selectLibraryInfo查询失败,responce->"+s);
            }
        }
        return null;
    }
}
