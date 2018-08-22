package com.ssitcloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.MetadataOpercmdDao;
import com.ssitcloud.entity.MetadataOpercmdEntity;
import com.ssitcloud.entity.page.OperCmdMgmtPageEntity;
import com.ssitcloud.service.MetadataOpercmdService;
/**
 * 
 * @comment 操作指令元数据表Service
 * 
 * @author hwl
 * @data 2016年4月7日
 */
@Service
public class MetadataOpercmdServiceImpl implements MetadataOpercmdService {

	@Resource
	MetadataOpercmdDao metadataOpercmdDao;
	
	@Override
	public int addMetadataOpercmd(MetadataOpercmdEntity metadataOpercmdEntity) {
		// TODO Auto-generated method stub
		return metadataOpercmdDao.insert(metadataOpercmdEntity);
	}

	@Override
	public int updMetadataOpercmd(MetadataOpercmdEntity metadataOpercmdEntity) {
		// TODO Auto-generated method stub
		return metadataOpercmdDao.update(metadataOpercmdEntity);
	}

	@Override
	public int delMetadataOpercmd(MetadataOpercmdEntity metadataOpercmdEntity) {
		// TODO Auto-generated method stub
		return metadataOpercmdDao.delete(metadataOpercmdEntity);
	}

	@Override
	public List<MetadataOpercmdEntity> selbyidMetadataOpercmd(
			MetadataOpercmdEntity metadataOpercmdEntity) {
		// TODO Auto-generated method stub
		return metadataOpercmdDao.selectByid(metadataOpercmdEntity);
	}

	@Override
	public List<MetadataOpercmdEntity> selallMetadataOpercmd() {
		return metadataOpercmdDao.selectAll();
	}

	@Override
	public List<MetadataOpercmdEntity> selectCmdType() {
		return metadataOpercmdDao.selectCmdType();
	}
	@Override
	public List<MetadataOpercmdEntity> selectCardCmdType() {
		return metadataOpercmdDao.selectCardCmdType();
	}

	@Override
	public OperCmdMgmtPageEntity queryServgroupByparam(String req) {
		OperCmdMgmtPageEntity operCmdMgmtPage=new OperCmdMgmtPageEntity();
		if(JSONUtils.mayBeJSON(req)){
			operCmdMgmtPage=JsonUtils.fromJson(req, OperCmdMgmtPageEntity.class);
		}
		OperCmdMgmtPageEntity operCmdMgmtPageRet=metadataOpercmdDao.queryServgroupByparam(operCmdMgmtPage);
		return operCmdMgmtPageRet;
	}

	@Override
	public List<MetadataOpercmdEntity> checkPermission(Map<String,Object> param) {
		
		return metadataOpercmdDao.checkPermission(param);
	}

	@Override
	public List<MetadataOpercmdEntity> selectDeviceOperLogCmd() {
		return metadataOpercmdDao.selectDeviceOperLogCmd();
	}
}
