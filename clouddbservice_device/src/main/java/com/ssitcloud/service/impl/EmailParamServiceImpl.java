package com.ssitcloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.EmailParamDao;
import com.ssitcloud.entity.EmailParamEntity;
import com.ssitcloud.entity.page.EmailParamMgmtPageEntity;
import com.ssitcloud.service.EmailParamService;


@Service
public class EmailParamServiceImpl implements EmailParamService {
	@Resource
	private EmailParamDao emailParamDao;

	@Override
	public int insertEmailParam(
			EmailParamEntity emailParamEntity) {
		return emailParamDao.insertEmailParam(emailParamEntity);
	}

	@Override
	public int updateEmailParam(
			EmailParamEntity emailParamEntity) {
		return emailParamDao.updateEmailParam(emailParamEntity);
	}

	@Override
	public int deleteEmailParam(
			EmailParamEntity emailParamEntity) {
		return emailParamDao.deleteEmailParam(emailParamEntity);
	}

	@Override
	public EmailParamEntity queryOneEmailParam(
			EmailParamEntity emailParamEntity) {
		return emailParamDao.queryOneEmailParam(emailParamEntity);
			
	}

	@Override
	public List<EmailParamEntity> queryEmailParams(
			Map<String, Object> params) {
		return emailParamDao.queryEmailParams(params);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity selectEmailParamByPage(
			String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.hasText(req)){
				Map<String,Object> map = JsonUtils.fromJson(req, Map.class);
				EmailParamMgmtPageEntity emailParamMgmtPageEntity = new EmailParamMgmtPageEntity();
				
				Integer page = map.get("page")==null?1:Integer.valueOf(map.get("page").toString());				
				Integer pageSize = map.get("pageSize")==null?10:Integer.valueOf(map.get("pageSize").toString());
				if(map.get("lib_idx")!=null && !map.get("lib_idx").toString().isEmpty()){
					Integer lib_idx = Integer.valueOf(map.get("lib_idx").toString());
					emailParamMgmtPageEntity.setLib_idx(lib_idx);
				}
				if(map.get("lib_idx_str")!=null && !map.get("lib_idx_str").toString().isEmpty()){
					String lib_idx_str = map.get("lib_idx_str").toString();
					emailParamMgmtPageEntity.setLib_idx_str(lib_idx_str);
				}
//				if(map.get("libIdx")==null && "".equals(map.get("libIdx").toString())){
//					result.setValue(false, "参数不能为空", "", "");
//					return result;
//				}
				
				emailParamMgmtPageEntity.setPage(page);
				emailParamMgmtPageEntity.setPageSize(pageSize);
				
				emailParamMgmtPageEntity = emailParamDao.queryEmailParamByPage(emailParamMgmtPageEntity);	
				result.setValue(true, "", "", emailParamMgmtPageEntity);
			}else{
				result.setValue(false, "参数不能为空", "", "");
			}
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	

}
