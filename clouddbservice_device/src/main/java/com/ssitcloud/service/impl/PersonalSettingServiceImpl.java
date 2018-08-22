package com.ssitcloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.PersonalSettingDao;
import com.ssitcloud.entity.PersonalSettingEntity;
import com.ssitcloud.entity.page.PersonalSettingMgmtPageEntity;
import com.ssitcloud.service.PersonalSettingService;

/**
 * 个人菜单设置
 *
 * <p>2017年2月10日 下午2:17:41  
 * @author hjc 
 *
 */
@Service
public class PersonalSettingServiceImpl implements PersonalSettingService{
	
	@Resource
	private PersonalSettingDao personalSettingDao;

	@Override
	public int addPersonalSetting(PersonalSettingEntity personalSettingEntity) {
		return personalSettingDao.addPersonalSetting(personalSettingEntity);
	}

	@Override
	public int delPersonalSetting(PersonalSettingEntity personalSettingEntity) {
		return personalSettingDao.delPersonalSetting(personalSettingEntity);
	}

	@Override
	public int updPersonalSetting(PersonalSettingEntity personalSettingEntity) {
		return personalSettingDao.updPersonalSetting(personalSettingEntity);
	}

	@Override
	public PersonalSettingEntity selPersonalSettingByIdx(
			PersonalSettingEntity personalSettingEntity) {
		return personalSettingDao.selPersonalSettingByIdx(personalSettingEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity selectPersonalSettingByPage(String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.hasText(req)){
				Map<String,Object> map = JsonUtils.fromJson(req, Map.class);
				PersonalSettingMgmtPageEntity personalSettingMgmtPageEntity = new PersonalSettingMgmtPageEntity(); 
				
				Integer page = map.get("page")==null?1:Integer.valueOf(map.get("page").toString());				
				Integer pageSize = map.get("pageSize")==null?10:Integer.valueOf(map.get("pageSize").toString());
				if(map.get("setting_desc")!=null && !map.get("setting_desc").toString().isEmpty()){
					personalSettingMgmtPageEntity.setSetting_desc(map.get("setting_desc").toString());
				}
				if(map.get("operator_idx")!=null && !map.get("operator_idx").toString().isEmpty()){
					personalSettingMgmtPageEntity.setOperator_idx(Integer.parseInt(map.get("operator_idx").toString()));
				}
				personalSettingMgmtPageEntity.setPage(page);
				personalSettingMgmtPageEntity.setPageSize(pageSize);
				personalSettingMgmtPageEntity = personalSettingDao.selectPersonalSettingByPage(personalSettingMgmtPageEntity);
				result.setValue(true, "", "", personalSettingMgmtPageEntity);
			}else{
				result.setValue(false, "参数不能为空", "", "");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	@Override
	public List<PersonalSettingEntity> selPersonalSettingList(
			PersonalSettingEntity personalSettingEntity) {
		return personalSettingDao.selPersonalSettingList(personalSettingEntity);
	}

	@Override
	public boolean isExists(PersonalSettingEntity personalSettingEntity) {
		List<PersonalSettingEntity> list = personalSettingDao.selPersonalSettingList(personalSettingEntity);
		if (list != null && !list.isEmpty()) {
			return true;
		}
		return false;
	}

}
