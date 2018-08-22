package com.ssitcloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.dao.MetadataOpercmdDao;
import com.ssitcloud.dao.OperatorGroupDao;
import com.ssitcloud.dao.RelOperatorDeviceGroupDao;
import com.ssitcloud.dao.RelOperatorGroupDao;
import com.ssitcloud.dao.RelOperatorServiceGroupDao;
import com.ssitcloud.dao.RelOperatorStatisticsGroupDao;
import com.ssitcloud.entity.OperatorGroupEntity;
import com.ssitcloud.entity.RelOperatorDeviceGroupEntity;
import com.ssitcloud.entity.RelOperatorGroupEntity;
import com.ssitcloud.entity.RelOperatorServiceGroupEntity;
import com.ssitcloud.entity.RelOperatorStatisticsGroupEntity;
import com.ssitcloud.entity.page.OperGroupMgmtPageEntity;
import com.ssitcloud.service.OperatorGroupService;

/**
 * 
 * @comment 操作员组表Service
 * @author hwl
 * 
 * @data 2016年4月5日
 */
@Service
public class OperatorGroupServiceImpl implements OperatorGroupService {

	@Resource
	private OperatorGroupDao operatorgroupDao;
	
	@Resource
	private RelOperatorDeviceGroupDao relOperatorDeviceGroupDao;
	
	@Resource
	private RelOperatorServiceGroupDao relOperatorServiceGroupDao;
	@Resource
	private RelOperatorStatisticsGroupDao relOperatorStatisticsGroupDao;
	
	@Resource
	private RelOperatorGroupDao relOperatorGroupDao;
	
	@Resource
	private MetadataOpercmdDao metadataOpercmdDao;

	@Override
	public int addOperatorGroup(OperatorGroupEntity operatorGroupEntity) {
		// TODO Auto-generated method stub
		return operatorgroupDao.insert(operatorGroupEntity);
	}

	@Override
	public int updOperatorGroup(OperatorGroupEntity operatorGroupEntity) {
		// TODO Auto-generated method stub
		return operatorgroupDao.update(operatorGroupEntity);
	}

	@Override
	public int delOperatorGroup(OperatorGroupEntity operatorGroupEntity) {
		// TODO Auto-generated method stub
		return operatorgroupDao.delete(operatorGroupEntity);
	}

	@Override
	public List<OperatorGroupEntity> selbyidOperatorGroup(
			OperatorGroupEntity operatorGroupEntity) {
		// TODO Auto-generated method stub
		return operatorgroupDao.selectByidx(operatorGroupEntity);
	}

	@Override
	public List<OperatorGroupEntity> selallOperatorGroup() {
		// TODO Auto-generated method stub
		return operatorgroupDao.selectAll();
	}

	@Override
	public OperGroupMgmtPageEntity queryOperGroupByparamDb(String req) {
		OperGroupMgmtPageEntity OperGroupMgmtPage=new OperGroupMgmtPageEntity();
		
		if(JSONUtils.mayBeJSON(req)){
			OperGroupMgmtPage=JsonUtils.fromJson(req, OperGroupMgmtPageEntity.class);
		}
		OperGroupMgmtPageEntity OperGroupMgmt=operatorgroupDao.queryOperGroupByparam(OperGroupMgmtPage);
		return OperGroupMgmt;
	}

	/**
	 * 		"operator_group_id":operGroupId,
			"library_idx":library_idx,
			"operator_group_name":operGroupName,
			"operator_group_desc":operatorGroupDesc,
			"service_group_idx":servGroupIdx,
			"device_group_idx_arr":arrDevGroupIdx   数组
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity addOperGroup(String req) {
		ResultEntity result=new ResultEntity();
		int insertNum=0;
		if(JSONUtils.mayBeJSON(req)){
			Map<String, Object> map=JsonUtils.fromJson(req, Map.class);
			if(map!=null){
				Integer library_idx = null;
				OperatorGroupEntity operatorGroup=new OperatorGroupEntity();
				if(map.get("operator_group_id")!=null){
					operatorGroup.setOperator_group_id(	map.get("operator_group_id").toString());
				}else{
					result.setMessage(Const.FAILED);
					result.setRetMessage("组ID不能为空");
					return result;
				}
				if(map.get("library_idx")!=null&&StringUtils.hasLength(map.get("library_idx").toString())){
					library_idx=Integer.parseInt(map.get("library_idx").toString());
					operatorGroup.setLibrary_idx(library_idx);			
				}else{
					result.setMessage(Const.FAILED);
					result.setRetMessage("library_idx 不能为空");
					return result;
				}
				if(map.get("operator_group_name")!=null){
					operatorGroup.setOperator_group_name(map.get("operator_group_name").toString());
				}else{
					result.setMessage(Const.FAILED);
					result.setRetMessage("组名 不能为空");
					return result;
				}
				if(map.get("operator_group_desc")!=null){
					operatorGroup.setOperator_group_desc(map.get("operator_group_desc").toString());
				}
				if(map.get("operator_idx")!=null){
					operatorGroup.setOperator_idx(Integer.parseInt(map.get("operator_idx").toString()));
				}else{
					result.setMessage(Const.FAILED);
					result.setRetMessage("operator_idx 不能为空,需要登陆后才能新增分组");
					return result;
				}
				//操作员表
				insertNum=operatorgroupDao.insert(operatorGroup);
				Integer operGroupIdx=operatorGroup.getOperator_group_idx();
				if(insertNum<=0||operGroupIdx==null){
					throw new RuntimeException("新增失败");
				}
				if(map.get("service_group_idx")!=null){
					Integer service_group_idx=Integer.parseInt(map.get("service_group_idx").toString());
					RelOperatorServiceGroupEntity relOperatorServiceGroup=new RelOperatorServiceGroupEntity();
					relOperatorServiceGroup.setLibrary_idx(library_idx);
					relOperatorServiceGroup.setOperator_group_idx(operGroupIdx);
					relOperatorServiceGroup.setService_group_idx(service_group_idx);
					insertNum=relOperatorServiceGroupDao.insert(relOperatorServiceGroup);
					if(insertNum<=0){
						throw new RuntimeException("新增 操作员组 权限组 关系 表失败");
					}
				}else{
					//没有选择权限组 可以通过
				}
				if(map.get("device_group_idx_arr")!=null){
					List<String> devGroupIdxList=(List<String>)map.get("device_group_idx_arr");
					if(devGroupIdxList!=null&&devGroupIdxList.size()>0){
						for(String devGroupIdx:devGroupIdxList){
							RelOperatorDeviceGroupEntity relOperatorDeviceGroup=new RelOperatorDeviceGroupEntity();
							relOperatorDeviceGroup.setLibrary_idx(library_idx);
							relOperatorDeviceGroup.setOperator_group_idx(operGroupIdx);
							relOperatorDeviceGroup.setDevice_group_idx(Integer.parseInt(devGroupIdx));
							int inserNum=relOperatorDeviceGroupDao.insert(relOperatorDeviceGroup);
							if(inserNum<=0){
								throw new RuntimeException("新增 操作员组 设备组关系 表 失败");
							}
						}
					}
				}else{
					//没有选择设备组
				}
				
				if(map.get("statistics_group_idx")!=null){
					Integer statistics_group_idx=Integer.parseInt(map.get("statistics_group_idx").toString());
					if(statistics_group_idx > 0){
						RelOperatorStatisticsGroupEntity rosge = new RelOperatorStatisticsGroupEntity();
						rosge.setOperator_group_idx(operGroupIdx);
						rosge.setStatistics_group_idx(statistics_group_idx);
						insertNum=relOperatorStatisticsGroupDao.insertRelOperatorStatisticsGroup(rosge);
						if(insertNum<=0){
							throw new RuntimeException("新增 操作员组 模板组 关系 表失败");
						}
					}
				}else{
					//没有选择模板组 可以通过
				}
				//馆IDX|用户组IDX|用户组名||
				result.setState(true);
				result.setRetMessage("馆IDX:"+library_idx+"|用户组IDX:"+operGroupIdx+"|用户组名:"+operatorGroup.getOperator_group_name()+"||");
			}
		}
		return result;
	}
	/**
	 * operator_group_idx
	 */
	@Override
	public ResultEntity delOperGroup(String req) {
		ResultEntity result=new ResultEntity();
		int delNum=0;
		if(JSONUtils.mayBeJSON(req)){
			OperatorGroupEntity operatorGroup=JsonUtils.fromJson(req, OperatorGroupEntity.class);
			if(operatorGroup!=null&&operatorGroup.getOperator_group_idx()!=null){
				Integer operator_group_idx=operatorGroup.getOperator_group_idx();
				Integer operator_idx=operatorGroup.getOperator_idx();//这里面临时存储的是 做出删除操作的用户IDX
				RelOperatorGroupEntity relOperatorGroup=new RelOperatorGroupEntity();
				//当前 修改的组IDX
				relOperatorGroup.setOperator_group_idx(operatorGroup.getOperator_group_idx());
				//当前 操作用户IDX
				relOperatorGroup.setOperator_idx(operator_idx);
				List<RelOperatorGroupEntity> relOperatorGroups=relOperatorGroupDao.selectByid(relOperatorGroup);
				
				if(!CollectionUtils.isEmpty(relOperatorGroups)){
					throw new RuntimeException("不能删除自己所属的操作员分组");
				}
				
				RelOperatorServiceGroupEntity relOperatorServiceGroup=new RelOperatorServiceGroupEntity();
				relOperatorServiceGroup.setOperator_group_idx(operator_group_idx);//删除operator_group_idx
				List<RelOperatorServiceGroupEntity> relOperatorServiceGroups=relOperatorServiceGroupDao.selectByidx(relOperatorServiceGroup);
				if(relOperatorServiceGroups!=null&&relOperatorServiceGroups.size()>0){
					delNum=relOperatorServiceGroupDao.deleteByOperGroupIdx(relOperatorServiceGroup);
					if(delNum!=relOperatorServiceGroups.size()){
						throw new RuntimeException("删除 操作员组 权限组 关系 表 数据失败");
					}
				}
				RelOperatorDeviceGroupEntity relOperatorDeviceGroup=new RelOperatorDeviceGroupEntity();
				relOperatorDeviceGroup.setOperator_group_idx(operator_group_idx);
				List<RelOperatorDeviceGroupEntity> relOperatorDeviceGroups=relOperatorDeviceGroupDao.selectByidx(relOperatorDeviceGroup);
				if(relOperatorDeviceGroups!=null&&relOperatorDeviceGroups.size()>0){
					delNum=relOperatorDeviceGroupDao.deleteByOperGroupIdx(relOperatorDeviceGroup);
					if(delNum!=relOperatorDeviceGroups.size()){
						throw new RuntimeException("删除  操作员组 设备组关系 表数据 失败");
					}
				}
				RelOperatorStatisticsGroupEntity rosge = new RelOperatorStatisticsGroupEntity();
				rosge.setOperator_group_idx(operator_group_idx);
				RelOperatorStatisticsGroupEntity r = relOperatorStatisticsGroupDao.queryOneRelOperatorStatisticsGroup(rosge);
				if(r !=null){
					delNum = relOperatorStatisticsGroupDao.deleteRelOperatorStatisticsGroup(rosge);
					if(delNum <= 0){
						throw new RuntimeException("删除  操作员组 模板组关系 表数据 失败");
					}
				}
				
				RelOperatorGroupEntity relOperatorGroupEntity=new RelOperatorGroupEntity();
				relOperatorGroupEntity.setOperator_group_idx(operator_group_idx);
				List<RelOperatorGroupEntity> relOperatorGroupsDel=relOperatorGroupDao.selectByid(relOperatorGroupEntity);
				if(relOperatorGroupsDel!=null && relOperatorGroupsDel.size()>0){
					for(RelOperatorGroupEntity d:relOperatorGroupsDel){
						delNum=relOperatorGroupDao.delete(d);
						if(delNum<=0){
							throw new RuntimeException("删除  操作员组  操作员关系表数据失败");
						}
					}
				}
				try {
					delNum=operatorgroupDao.delete(operatorGroup);
				} catch (org.springframework.dao.DataIntegrityViolationException e) {
					LogUtils.error(e);
					throw new RuntimeException("删除  操作员组数据 失败,该操作员组可能正在使用中");
				}
				
				if(delNum<=0){
					//throw new RuntimeException("删除  操作员组数据 失败");
					result.setState(false);
					result.setRetMessage("optimistic");
					return result;
				}
				result.setState(true);
				result.setRetMessage("馆IDX:"+operatorGroup.getLibrary_idx()+"|用户组IDX:"+operatorGroup.getOperator_group_idx()+"|用户组名:"+operatorGroup.getOperator_group_name()+"||");
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity updOperGroup(String req) {
		ResultEntity result=new ResultEntity();
		int updNum=0;
		if(JSONUtils.mayBeJSON(req)){
			Map<String, Object> map= JsonUtils.fromJson(req, Map.class);
			if(map!=null){
				Integer library_idx = null;
				OperatorGroupEntity operatorGroup=new OperatorGroupEntity();
				if(map.get("operator_group_idx")!=null){
					operatorGroup.setOperator_group_idx(Integer.parseInt(map.get("operator_group_idx").toString()));
				}else{
					result.setMessage(Const.FAILED);
					result.setRetMessage("组IDX不能为空");
					return result;
				}
				if(map.get("operator_group_id")!=null){
					operatorGroup.setOperator_group_id(	map.get("operator_group_id").toString());
				}else{
					result.setMessage(Const.FAILED);
					result.setRetMessage("组ID不能为空");
					return result;
				}
				if(map.get("library_idx")!=null){
					library_idx=Integer.parseInt(map.get("library_idx").toString());
					operatorGroup.setLibrary_idx(library_idx);			
				}else{
					result.setMessage(Const.FAILED);
					result.setRetMessage("library_idx 不能为空");
					return result;
				}
				if(map.get("operator_group_name")!=null){
					operatorGroup.setOperator_group_name(map.get("operator_group_name").toString());
				}else{
					result.setMessage(Const.FAILED);
					result.setRetMessage("组名 不能为空");
					return result;
				}
				if(map.get("operator_group_desc")!=null){
					operatorGroup.setOperator_group_desc(map.get("operator_group_desc").toString());
				}else{
					operatorGroup.setOperator_group_desc("");
				}
				if(map.get("operator_idx")!=null){
					operatorGroup.setOperator_idx(Integer.parseInt(map.get("operator_idx").toString()));
				}else{
					result.setMessage(Const.FAILED);
					result.setRetMessage("operator_idx 不能为空,需要登陆后才能修改分组信息");
					return result;
				}
				if(map.get("version_stamp")!=null){
					operatorGroup.setVersion_stamp(Integer.parseInt(map.get("version_stamp").toString()));
				}
				RelOperatorGroupEntity relOperatorGroup=new RelOperatorGroupEntity();
				//当前 修改的组IDX
				relOperatorGroup.setOperator_group_idx(operatorGroup.getOperator_group_idx());
				//当前操作用户IDX
				relOperatorGroup.setOperator_idx(operatorGroup.getOperator_idx());
				List<RelOperatorGroupEntity> relOperatorGroups=relOperatorGroupDao.selectByid(relOperatorGroup);
				
				if(!CollectionUtils.isEmpty(relOperatorGroups)){
					result.setMessage(Const.FAILED);
					result.setRetMessage("不能修改自己所属的操作员分组");
					return result;
				}
				updNum=operatorgroupDao.update(operatorGroup);
				if(updNum<=0){
					//throw new RuntimeException("更新操作员组失败");
					result.setState(false);
					result.setRetMessage("optimistic");
					return result;
				}
				//权限组
				if(map.get("service_group_idx")!=null){
					Integer service_group_idx=Integer.parseInt(map.get("service_group_idx").toString());
					RelOperatorServiceGroupEntity relOperatorServiceGroup=new RelOperatorServiceGroupEntity();
					relOperatorServiceGroup.setOperator_group_idx(operatorGroup.getOperator_group_idx());
					List<RelOperatorServiceGroupEntity> relOperatorServiceGroups=relOperatorServiceGroupDao.selectByidx(relOperatorServiceGroup);
					if(relOperatorServiceGroups!=null&&relOperatorServiceGroups.size()>0){
						int delNum=relOperatorServiceGroupDao.deleteByOperGroupIdx(relOperatorServiceGroup);
						if(delNum!=relOperatorServiceGroups.size()){
							throw new RuntimeException("更新操作 删除操作员组 权限组 关系表数据失败");
						}
					}
					
					relOperatorServiceGroup.setLibrary_idx(library_idx);
					relOperatorServiceGroup.setService_group_idx(service_group_idx);
					int insertNum=relOperatorServiceGroupDao.insert(relOperatorServiceGroup);
					if(insertNum<=0){
						throw new RuntimeException("更新操作 添加操作员组 权限组 关系表数据失败");
					}
				}
				
				//模板组
				if(map.get("statistics_group_idx")!=null){
					Integer statistics_group_idx=Integer.parseInt(map.get("statistics_group_idx").toString());
					if(statistics_group_idx >0){
						RelOperatorStatisticsGroupEntity rosge = new RelOperatorStatisticsGroupEntity();
						rosge.setOperator_group_idx(operatorGroup.getOperator_group_idx());
						RelOperatorStatisticsGroupEntity r = relOperatorStatisticsGroupDao.queryOneRelOperatorStatisticsGroup(rosge);
						if(r !=null){
							relOperatorStatisticsGroupDao.deleteRelOperatorStatisticsGroup(rosge);
						}
						
						rosge.setStatistics_group_idx(statistics_group_idx);
						int insertNum=relOperatorStatisticsGroupDao.insertRelOperatorStatisticsGroup(rosge);
						if(insertNum<=0){
							throw new RuntimeException("更新操作 添加操作员组 模板组 关系表数据失败");
						}
					}else if(statistics_group_idx == -1){
						RelOperatorStatisticsGroupEntity rosge = new RelOperatorStatisticsGroupEntity();
						rosge.setOperator_group_idx(operatorGroup.getOperator_group_idx());
						RelOperatorStatisticsGroupEntity r = relOperatorStatisticsGroupDao.queryOneRelOperatorStatisticsGroup(rosge);
						if(r !=null){
							relOperatorStatisticsGroupDao.deleteRelOperatorStatisticsGroup(rosge);
						}
					}
				}
				//设备组
				if(map.get("device_group_idx_arr")!=null){
					List<String> devGroupIdxList=(List<String>)map.get("device_group_idx_arr");
					if(devGroupIdxList!=null&&devGroupIdxList.size()>0){
						//删除操作
						RelOperatorDeviceGroupEntity relOperatorDeviceGroup=new RelOperatorDeviceGroupEntity();
						relOperatorDeviceGroup.setOperator_group_idx(operatorGroup.getOperator_group_idx());
						List<RelOperatorDeviceGroupEntity> relOperatorDeviceGroups=relOperatorDeviceGroupDao.selectByidx(relOperatorDeviceGroup);
						if(relOperatorDeviceGroups!=null&&relOperatorDeviceGroups.size()>0){
							int delNum=relOperatorDeviceGroupDao.deleteByOperGroupIdx(relOperatorDeviceGroup);
							if(delNum!=relOperatorDeviceGroups.size()){
								throw new RuntimeException("更新操作 删除操作员组 设备组 关系表数据失败");
							}
						}
						//插入操作
						for(String devGroupIdx:devGroupIdxList){
							RelOperatorDeviceGroupEntity insertRelOperatorDeviceGroup=new RelOperatorDeviceGroupEntity();
							insertRelOperatorDeviceGroup.setLibrary_idx(library_idx);
							insertRelOperatorDeviceGroup.setOperator_group_idx(operatorGroup.getOperator_group_idx());
							insertRelOperatorDeviceGroup.setDevice_group_idx(Integer.parseInt(devGroupIdx));
							int inserNum=relOperatorDeviceGroupDao.insert(insertRelOperatorDeviceGroup);
							if(inserNum<=0){
								throw new RuntimeException("更新操作 操作员组 设备组关系 表 失败");
							}
						}
					}else if(devGroupIdxList!=null&&devGroupIdxList.size()==0){
						//删除操作
						RelOperatorDeviceGroupEntity relOperatorDeviceGroup=new RelOperatorDeviceGroupEntity();
						relOperatorDeviceGroup.setOperator_group_idx(operatorGroup.getOperator_group_idx());
						List<RelOperatorDeviceGroupEntity> relOperatorDeviceGroups=relOperatorDeviceGroupDao.selectByidx(relOperatorDeviceGroup);
						if(relOperatorDeviceGroups!=null&&relOperatorDeviceGroups.size()>0){
							int delNum=relOperatorDeviceGroupDao.deleteByOperGroupIdx(relOperatorDeviceGroup);
							if(delNum!=relOperatorDeviceGroups.size()){
								throw new RuntimeException("更新操作 删除操作员组 设备组 关系表数据失败");
							}
						}
					}
				}else{
					//删除操作
					RelOperatorDeviceGroupEntity relOperatorDeviceGroup=new RelOperatorDeviceGroupEntity();
					relOperatorDeviceGroup.setOperator_group_idx(operatorGroup.getOperator_group_idx());
					List<RelOperatorDeviceGroupEntity> relOperatorDeviceGroups=relOperatorDeviceGroupDao.selectByidx(relOperatorDeviceGroup);
					if(relOperatorDeviceGroups!=null&&relOperatorDeviceGroups.size()>0){
						int delNum=relOperatorDeviceGroupDao.deleteByOperGroupIdx(relOperatorDeviceGroup);
						if(delNum!=relOperatorDeviceGroups.size()){
							throw new RuntimeException("更新操作 删除操作员组 设备组 关系表数据失败");
						}
					}	
				}
				result.setState(true);
				result.setRetMessage("馆IDX:"+library_idx+"|用户组IDX:"+operatorGroup.getOperator_group_idx()+"|用户组名:"+operatorGroup.getOperator_group_name()+"||");
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity queryAllServiceGroup(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String, String> param = JsonUtils.fromJson(req, Map.class);
			if (param!=null && param.get("library_idx")!=null && !param.get("library_idx").equals("")) {
			}
//			List<Map<String, Object>> list = relOperatorDeviceGroupDao.queryAllServiceGroup();
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity queryLibraryServiceGroup(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String, String> param = JsonUtils.fromJson(req, Map.class);
			if (param==null || param.get("library_idx")==null || param.get("library_idx").equals("")) {
				resultEntity.setValue(false, "library_idx参数不能为空");
				return resultEntity;
			}
			List<Map<String, Object>> list = relOperatorDeviceGroupDao.queryLibraryServiceGroup(param);
			for (Map<String, Object> map : list) {
				String operator_group_idx = map.get("operator_group_idx").toString();
				//根据用户组id获取对应的权限
				List<Map<String, Object>> typelist = metadataOpercmdDao.getBussinessTypeByGroupId(operator_group_idx);
				if (typelist!=null) {
					map.put("typelist", typelist);
					for (Map<String, Object> map2 : typelist) {
						String opercmd = map2.get("opercmd").toString();
						List<Map<String, Object>> cmdList = metadataOpercmdDao.getOpercmdListByOpercmd(opercmd,operator_group_idx);
						map2.put(opercmd, cmdList);
					}
				}
			}
			resultEntity.setValue(true, "", "", list);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	

}
