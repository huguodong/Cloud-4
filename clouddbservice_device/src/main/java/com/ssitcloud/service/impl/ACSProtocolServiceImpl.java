package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dao.ACSProtocolDAO;
import com.ssitcloud.entity.ACSProtocolEntity;
import com.ssitcloud.service.ACSProtocolService;

@Service
public class ACSProtocolServiceImpl implements ACSProtocolService{

	@Resource
	private ACSProtocolDAO acsProtocolDAO;
	
	@Override
	public ResultEntity UpdOneByIdx(ACSProtocolEntity acsProtocolEntity) {
		int updNum=acsProtocolDAO.UpdOneByIdx(acsProtocolEntity);
		return null;
	}

	@Override
	public ResultEntity DelOneByIdx(ACSProtocolEntity acsProtocolEntity) {
		int delNum=acsProtocolDAO.DelOneByIdx(acsProtocolEntity);
		return null;
	}
	
	@Override
	public ResultEntity DelByIdxS(List<ACSProtocolEntity> acsProtocolEntitys) {
		//必须要全部删除成功 应该是删除模版 连带删除这个
		if(!CollectionUtils.isEmpty(acsProtocolEntitys)){
			for(ACSProtocolEntity aCSProtocolEntity:acsProtocolEntitys){
				int delNum=acsProtocolDAO.DelOneByIdx(aCSProtocolEntity);
			}
		}
		return null;
	}

	@Override
	public ResultEntity IncOne(ACSProtocolEntity acsProtocolEntity) {
		ResultEntity result=new ResultEntity();
		result.setResult(acsProtocolDAO.IncOne(acsProtocolEntity));
		result.setState(true);
		return result;
	}

	@Override
	public ResultEntity IncMany(List<ACSProtocolEntity> acsProtocolEntitys) {
		ResultEntity result=new ResultEntity();
		// TODO Auto-generated method stub
		if(!CollectionUtils.isEmpty(acsProtocolEntitys)){
			for(ACSProtocolEntity ACSProtocolEntity:acsProtocolEntitys){
				try {
					int f=acsProtocolDAO.IncOne(ACSProtocolEntity);
				} catch (Exception e) {
					// TODO:新增失败 
					
				}
			
				
			}
		}
		
		return result;
	}

	@Override
	public ResultEntity SelList(ACSProtocolEntity acsProtocolEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultEntity SelPage(ACSProtocolEntity acsProtocolEntity) {
		ResultEntity result=new ResultEntity();
		//ACSProtocolEntity queryEntity=acsProtocolDAO.queryDatagridPage(acsProtocolEntity, "acsProtocol.SelPage");
		//result.setResult(queryEntity);
		//result.setState(true);
		return result;
	}

}
