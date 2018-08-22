package com.ssitcloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.RelOperatorGroupDao;
import com.ssitcloud.entity.RelOperatorGroupEntity;
import com.ssitcloud.service.RelOperatorGroupService;

/**
 * 
 * @comment 操作员分组关联表Service
 * 
 * @author hwl
 * @data 2016年4月6日
 */
@Service
public class RelOperatorGroupServiceImpl implements RelOperatorGroupService {

	@Resource
	RelOperatorGroupDao relOperatorGroupDao;
	
	@Override
	public int addRelOperatorGroup(RelOperatorGroupEntity relOperatorGroupEntity) {
		// TODO Auto-generated method stub
		return relOperatorGroupDao.insert(relOperatorGroupEntity);
	}

	@Override
	public int updRelOperatorGroup(RelOperatorGroupEntity relOperatorGroupEntity) {
		// TODO Auto-generated method stub
		return relOperatorGroupDao.update(relOperatorGroupEntity);
	}

	@Override
	public int delRelOperatorGroup(RelOperatorGroupEntity relOperatorGroupEntity) {
		// TODO Auto-generated method stub
		return relOperatorGroupDao.delete(relOperatorGroupEntity);
	}

	@Override
	public List<RelOperatorGroupEntity> selbyidRelOperatorGroup(
			RelOperatorGroupEntity relOperatorGroupEntity) {
		// TODO Auto-generated method stub
		return relOperatorGroupDao.selectByid(relOperatorGroupEntity);
	}

	@Override
	public List<RelOperatorGroupEntity> selallRelOperatorGroup() {
		// TODO Auto-generated method stub
		return relOperatorGroupDao.selectAll();
	}

	@Override
	public RelOperatorGroupEntity queryOperatorGroupByOperIdx(RelOperatorGroupEntity groupEntity) {
		return relOperatorGroupDao.queryOperatorGroupByOperIdx(groupEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity deleteRelOperatorGroupByOperatorIdxs(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			List<Integer> operatorIdxs=JsonUtils.fromJson(req, List.class);
			if(CollectionUtils.isNotEmpty(operatorIdxs)){
				int delNum=relOperatorGroupDao.deleteRelOperatorGroupByOperatorIdxs(operatorIdxs);
			}
			result.setState(true);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity updateOperatorGroup(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空！");
				return resultEntity;
			}
			Map<String, Object> map = JsonUtils.fromJson(req, Map.class);
			Map<String, Object> operator = (Map<String, Object>) map.get("operator");
			if (operator==null) {
				resultEntity.setValue(false, "参数不能为空！");
				return resultEntity;
			}
			
			if(operator.get("library_idx")!=null && operator.get("groupId")!=null && operator.get("operator_idx")!=null
					&& !operator.get("library_idx").toString().equals("") && !operator.get("groupId").toString().equals("")
					&& !operator.get("operator_idx").toString().equals("")){
				String library_idx = operator.get("library_idx").toString();
				String groupId = operator.get("groupId").toString();
				String operator_idx = operator.get("operator_idx").toString();
				
				RelOperatorGroupEntity groupEntity = new RelOperatorGroupEntity();
				groupEntity.setOperator_idx(Integer.valueOf(operator_idx));
				groupEntity.setLibrary_idx(Integer.valueOf(library_idx));
				groupEntity.setOperator_group_idx(Integer.valueOf(groupId));
				
				//先删掉原来的用户组关系
				relOperatorGroupDao.deleteRelByOperatorIdx(groupEntity);
				relOperatorGroupDao.insert(groupEntity);
				
				resultEntity.setValue(true, "success");
			}else{
				resultEntity.setValue(false, "参数不足！");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
			throw new RuntimeException("修改用户的用户组信息失败！",e);
		}
		return resultEntity;
	}
	
	

}
