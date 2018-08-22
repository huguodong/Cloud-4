package com.ssitcloud.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.ThirdPartyServiceDao;
import com.ssitcloud.devmgmt.entity.ThirdPartyServiceEntity;
import com.ssitcloud.entity.DisplayInfoEntity;
import com.ssitcloud.entity.page.DisplayInfoPageEntity;
import com.ssitcloud.entity.page.ThirdPartyServicePageEntity;
import com.ssitcloud.service.ThirdPartyServiceService;
import com.ssitcloud.utils.AESHelper;

@Service
public class ThirdPartyServiceServiceImpl implements ThirdPartyServiceService {
	@Resource
	private ThirdPartyServiceDao thirdPartyServiceDao;

	public ResultEntity queryThirdPartyServiceByParams(String req) {
		ResultEntity resultEntity = new ResultEntity();
		ThirdPartyServiceEntity entity = null;
		if (JSONUtils.mayBeJSON(req)) {
			entity = JsonUtils.fromJson(req, ThirdPartyServiceEntity.class);
		}else{
			entity = new ThirdPartyServiceEntity();
		}
		List<ThirdPartyServiceEntity> list = thirdPartyServiceDao.queryThirdPartyServiceByParams(entity);
		resultEntity.setState(true);
		resultEntity.setResult(list);
		return resultEntity;
	}

	@Override
	public ResultEntity deleteThirdPartyService(String req) {
		ResultEntity resultEntity = new ResultEntity();
		if (JSONUtils.mayBeJSON(req)) {
			JSONObject obj = JSONObject.fromObject(req);
			if (obj.containsKey("service_idx")) {
				JSONArray service_idxs = obj.optJSONArray("service_idx");
				int[] array = new int[service_idxs.size()];
				for (int i = 0; i < service_idxs.size(); i++) {
					array[i] = service_idxs.getInt(i);
				}

				int count = thirdPartyServiceDao.deleteThirdPartyService(array);
				if (count > 0) {
					resultEntity.setState(true);
					resultEntity.setMessage("删除成功" + count + "条数据");
				} else {
					resultEntity.setState(false);
					resultEntity.setMessage("删除失败");
				}
				return resultEntity;
			}
		}
		resultEntity.setState(false);
		resultEntity.setMessage("参数不正确");
		return resultEntity;
	}

	@Override
	public ResultEntity queryThirdPartyServiceByPage(String req) {
		ResultEntity resultEntity = new ResultEntity();
		ThirdPartyServicePageEntity entity = null;
		if (JSONUtils.mayBeJSON(req)) {
			entity = JsonUtils.fromJson(req, ThirdPartyServicePageEntity.class);
		}else{
			entity = new ThirdPartyServicePageEntity();
		}
		entity = thirdPartyServiceDao.queryThirdPartyServiceByPage(entity);
		resultEntity.setState(true);
		resultEntity.setResult(entity);
		return resultEntity;
	}

	@Override
	public ResultEntity editThirdPartyService(String req) {
		ResultEntity resultEntity = new ResultEntity();
		if (JSONUtils.mayBeJSON(req)) {
			ThirdPartyServiceEntity entity = JsonUtils.fromJson(req, ThirdPartyServiceEntity.class);
			entity.setOperate_time(new Date());
			Integer service_idx = entity.getService_idx();
			Integer library_idx = entity.getLibrary_idx();
			String flag="update";
			int count = 0;
			if (service_idx == null || service_idx < 0) {
				flag="insert";
				count = thirdPartyServiceDao.addThirdPartyService(entity);
			} else {
				count = thirdPartyServiceDao.editThirdPartyService(entity);
			}
			if (count > 0) {
				if("insert".equals(flag)){
					service_idx = entity.getService_idx();
					String app_key = createAppKey(library_idx, service_idx);
					System.out.println(app_key);
					if(StringUtils.hasText(app_key)){
						entity = new ThirdPartyServiceEntity();
						entity.setService_idx(service_idx);
						entity.setApp_key(app_key);
						count = thirdPartyServiceDao.editThirdPartyService(entity);
						if (count <= 0)System.out.println("插入appkey失败！");
					}
				}
				resultEntity.setState(true);
				resultEntity.setMessage("操作成功");
			} else {
				resultEntity.setState(false);
				resultEntity.setMessage("操作失败");
			}
			return resultEntity;

		}
		resultEntity.setState(false);
		resultEntity.setMessage("参数不正确");
		return resultEntity;
	}
	
	
	@Override
	public ResultEntity editDisplayInfo(String req) {
		ResultEntity resultEntity = new ResultEntity();
		if (JSONUtils.mayBeJSON(req)) {
			DisplayInfoEntity entity = JsonUtils.fromJson(req, DisplayInfoEntity.class);
			Integer display_info_idx = entity.getDisplay_info_idx();
			int count = 0;
			if (display_info_idx == null || display_info_idx < 0) {
				count = thirdPartyServiceDao.addDisplayInfo(entity);
			} else {
				count = thirdPartyServiceDao.editDisplayInfo(entity);
			}
			if (count > 0) {
				resultEntity.setState(true);
				resultEntity.setMessage("操作成功");
			} else {
				resultEntity.setState(false);
				resultEntity.setMessage("操作失败");
			}
			return resultEntity;

		}
		resultEntity.setState(false);
		resultEntity.setMessage("参数不正确");
		return resultEntity;
	}

	@Override
	public ResultEntity queryDisplayInfo(String req) {
		ResultEntity resultEntity = new ResultEntity();
		DisplayInfoEntity entity = null;
		if (JSONUtils.mayBeJSON(req)) {
			entity = JsonUtils.fromJson(req, DisplayInfoEntity.class);
		}else{
			entity = new DisplayInfoEntity();
		}
		entity = thirdPartyServiceDao.queryDisplayInfo(entity);
		resultEntity.setState(true);
		resultEntity.setResult(entity);
		return resultEntity;
	}

	@Override
	public ResultEntity queryDisplayInfoList(String req) {
		ResultEntity resultEntity = new ResultEntity();
		DisplayInfoPageEntity entity = null;
		if (JSONUtils.mayBeJSON(req)) {
			entity = JsonUtils.fromJson(req, DisplayInfoPageEntity.class);
		}else{
			entity = new DisplayInfoPageEntity();
		}
		entity = thirdPartyServiceDao.queryDisplayInfoList(entity);
		resultEntity.setState(true);
		resultEntity.setResult(entity);
		return resultEntity;
	}

	@Override
	public ResultEntity deleteDisplayInfo(String req) {
		ResultEntity resultEntity = new ResultEntity();
		if (JSONUtils.mayBeJSON(req)) {
			JSONObject obj = JSONObject.fromObject(req);
			if (obj.containsKey("service_idx")) {
				JSONArray service_idxs = obj.optJSONArray("service_idx");
				int[] array = new int[service_idxs.size()];
				for (int i = 0; i < service_idxs.size(); i++) {
					array[i] = service_idxs.getInt(i);
				}

				int count = thirdPartyServiceDao.deleteDisplayInfo(array);
				if (count > 0) {
					resultEntity.setState(true);
					resultEntity.setMessage("删除成功" + count + "条数据");
				} else {
					resultEntity.setState(false);
					resultEntity.setMessage("删除失败");
				}
				return resultEntity;
			}
		}
		resultEntity.setState(false);
		resultEntity.setMessage("参数不正确");
		return resultEntity;
	}
	

	/**
	 * 创建appKey
	 * @param library_idx
	 * @param service_idx
	 * @return
	 */
	private String createAppKey(int library_idx, int service_idx) {
		StringBuilder content = new StringBuilder();
		content.append(library_idx).append("#").append(service_idx);
		return AESHelper.encrypt(content.toString());
	}
}
