package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.DeviceConfigDao;
import com.ssitcloud.dao.DeviceDBSyncConfDao;
import com.ssitcloud.dao.DeviceExtConfDao;
import com.ssitcloud.dao.DeviceMonConfDao;
import com.ssitcloud.dao.DeviceRunConfDao;
import com.ssitcloud.dao.TableChangeTriDao;
import com.ssitcloud.entity.TableChangeTriEntity;
import com.ssitcloud.service.TableChangeTriService;

@Service
public class TableChangeTriServiceImpl implements TableChangeTriService{

	@Resource
	private TableChangeTriDao changeTriDao;
	@Resource
	private DeviceConfigDao deviceConfigDao;
	@Resource
	private DeviceExtConfDao deviceExtConfDao;
	@Resource
	private DeviceDBSyncConfDao deviceDBSyncConfDao;
	@Resource
	private DeviceRunConfDao deviceRunConfDao;
	@Resource
	private DeviceMonConfDao deivceMonConfDao;
	
	@Override
	public List<TableChangeTriEntity> queryAllgourpByTableOrderByCreatimeDesc() {
		return 	changeTriDao.selectBycreatTimeDescByIdx(new TableChangeTriEntity());
	}
	
	@Override
	public List<TableChangeTriEntity> selTableChangeTriPatchInfo() {
		List<TableChangeTriEntity> TableChangeTris=	changeTriDao.selectPatchInfoChangesOrderByCreatimeDescByIdx(new TableChangeTriEntity());
		return TableChangeTris;
	}
	
	
	@Override
	public int deleteDateWhereisOutof(int day) {
		return changeTriDao.deleteDateWhereisOutof(day);
	}

	@Override
	public ResultEntity setRequestTimeByTriIdxs(String req) {
		ResultEntity result=new ResultEntity();
		int updateNum=0;
		if(JSONUtils.mayBeJSON(req)){
			List<Integer> idxList=JsonUtils.fromJson(req, new TypeReference<List<Integer>>() {});
			if(idxList!=null){
				updateNum=changeTriDao.updateInTriIdx(idxList);
				if(updateNum==idxList.size()){
					result.setState(true);
				}else{
					result.setRetMessage("更新的数据有存在错误");
				}
			}
		}
		return result;
	}

	@Override
	public ResultEntity updateRequestTimeByTriTableName(String req) {
		ResultEntity result=new ResultEntity();
		int updateNum=0;
		if(JSONUtils.mayBeJSON(req)){
			List<String> tableNameList=JsonUtils.fromJson(req, new TypeReference<List<String>>() {});
			if(tableNameList!=null){
				updateNum=changeTriDao.updateBytableNameList(tableNameList);
				if(updateNum>0)
					result.setState(true);
			}
		}
		return result;
	}

}
