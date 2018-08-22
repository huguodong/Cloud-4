package com.ssitcloud.service.impl;

import java.util.List;
import java.util.TreeSet;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.dao.RelOperatorServiceGroupDao;
import com.ssitcloud.dao.RelServiceGroupDao;
import com.ssitcloud.dao.ServiceGroupDao;
import com.ssitcloud.entity.RelOperatorServiceGroupEntity;
import com.ssitcloud.entity.RelServiceGroupEntity;
import com.ssitcloud.entity.ServiceGroupEntity;
import com.ssitcloud.entity.page.OperCmdMgmtPageEntity;
import com.ssitcloud.service.ServiceGroupService;

/**
 * @comment 业务组表Service
 * @author hwl
 * 
 * @data 2016年4月5日
 * 
 */
@Transactional
@Service
public class ServiceGroupServiceImpl implements ServiceGroupService {

	@Resource
	private ServiceGroupDao servicegroupDao;
	
	@Resource
	private RelServiceGroupDao relServiceGroupDao;
	
	@Resource
	private RelOperatorServiceGroupDao relOperatorServiceGroupDao;

	@Override
	public List<ServiceGroupEntity> selbyidServiceGroup(
			ServiceGroupEntity serviceGroupEntity) {
		// TODO Auto-generated method stub
		return servicegroupDao.selectByidx(serviceGroupEntity);
	}

	@Override
	public List<ServiceGroupEntity> selallServiceGroup() {
		// TODO Auto-generated method stub
		return servicegroupDao.selectAll();
	}

	@Override
	public ResultEntity addservgroup(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			OperCmdMgmtPageEntity operCmdMgmtPage=JsonUtils.fromJson(req, OperCmdMgmtPageEntity.class);
			if(operCmdMgmtPage!=null){
				String service_group_id=operCmdMgmtPage.getService_group_id();
				String service_group_desc=operCmdMgmtPage.getService_group_desc();
				String service_group_name=operCmdMgmtPage.getService_group_name();
				Integer library_idx=operCmdMgmtPage.getLibrary_idx();
				if(!StringUtils.hasText(service_group_id)){
					result.setMessage("组ID不能为空");
					return result;
				}
				if(!StringUtils.hasText(service_group_name)){
					result.setRetMessage("组名不能为空");
					return result;
				}
				if(library_idx==null){
					result.setRetMessage("图书馆IDX不能为空");
					return result;
				}
				ServiceGroupEntity serviceGroup=new ServiceGroupEntity();
				serviceGroup.setService_group_id(StringUtils.trimWhitespace(service_group_id));
				serviceGroup.setLibrary_idx(library_idx);
				serviceGroup.setService_group_desc(service_group_desc);
				serviceGroup.setService_group_name(StringUtils.trimWhitespace(service_group_name));
				int insertNum = 0;
				try {
					insertNum=servicegroupDao.insert(serviceGroup);
				} catch (org.springframework.dao.DuplicateKeyException e) {
					LogUtils.error(e);
					String msg=e.getRootCause().getMessage();
					AntPathMatcher matcher=new AntPathMatcher();
					if(matcher.match("*service_group_id*", msg)){
						throw new RuntimeException("组ID已经被使用，请更改后保存");
					}else if(matcher.match("*service_group_name*", msg)){
						throw new RuntimeException("组名称已经被使用，请更改后保存");
					}else{
						throw new RuntimeException(e);
					}				
				}
				if(insertNum<=0){
					throw new RuntimeException("新增权限组失败！！");
				}
				Integer service_group_idx=serviceGroup.getService_group_idx();
				String meta_opercmd_idx_str=operCmdMgmtPage.getMeta_opercmd_idx_str();
				
				if(StringUtils.hasText(meta_opercmd_idx_str)){
					if(service_group_idx==null){
						throw new RuntimeException("获取 service_group_idx失败！！");
					}
					String[] meta_opercmd_idx_arr=meta_opercmd_idx_str.split(",");
					if(meta_opercmd_idx_arr!=null){
						for(String meta_opercmd_idx:meta_opercmd_idx_arr){
							RelServiceGroupEntity relServiceGroup=new RelServiceGroupEntity();
							relServiceGroup.setLibrary_idx(library_idx);
							relServiceGroup.setMeta_opercmd_idx(Integer.parseInt(meta_opercmd_idx));
							relServiceGroup.setService_group_idx(service_group_idx);
							insertNum=relServiceGroupDao.insert(relServiceGroup);
							if(insertNum<=0){
								throw new RuntimeException("新增权限与权限关系表失败！！");
							}
						}
					}
				}
				result.setState(true);
				result.setRetMessage("IDX:"+service_group_idx+"|组名:"+service_group_name+"|||");
			}
		}
		return result;
	}

	@Override
	public ResultEntity delservgroup(String req) {
		ResultEntity result=new ResultEntity();
		int deleteNum=0;
		StringBuilder idx=new StringBuilder("IDX:");
		if(JSONUtils.mayBeJSON(req)){
			List<OperCmdMgmtPageEntity> list = JsonUtils.fromJson(req, new TypeReference<List<OperCmdMgmtPageEntity>>() {});
			if(!CollectionUtils.isEmpty(list)){
				for(OperCmdMgmtPageEntity operCmdMgmtPage:list){
					if(operCmdMgmtPage!=null){
						Integer service_group_idx=operCmdMgmtPage.getService_group_idx();
						Integer library_idx=operCmdMgmtPage.getLibrary_idx();
						Integer version_stamp=operCmdMgmtPage.getVersion_stamp();
						if(service_group_idx!=null&&library_idx!=null){
							RelServiceGroupEntity relServiceGroupEntity=new RelServiceGroupEntity();
							relServiceGroupEntity.setLibrary_idx(library_idx);
							relServiceGroupEntity.setService_group_idx(service_group_idx);
							Integer selNum=relServiceGroupDao.selectNumByServiceGroupIdxAndLibIdx(relServiceGroupEntity);
							if(selNum>0){
								//执行删除
								deleteNum=relServiceGroupDao.deleteByServiceGroupIdxAndLibIdx(relServiceGroupEntity);
								if(selNum==deleteNum){
									//删除成功，否则不成功，然后删除service_group
									ServiceGroupEntity servicegroupEntity=new ServiceGroupEntity();
									servicegroupEntity.setService_group_idx(service_group_idx);
									servicegroupEntity.setVersion_stamp(version_stamp);
									try {
										deleteNum=servicegroupDao.delete(servicegroupEntity);
									} catch (org.springframework.dao.DataIntegrityViolationException e) {
										throw new RuntimeException("使用中的数据不能删除！");
									}
									if(deleteNum>0){
										idx.append(service_group_idx).append(",");
									}else{
										//throw new RuntimeException("删除权限组失败！");
										result.setState(false);
										result.setRetMessage("optimistic");
										return result;
									}
								}else{
									throw new RuntimeException("删除权限关系表数据失败！");
								}
							}else{
								//权限关系表没有查到数据，则直接删除权限组
								//删除成功，否则不成功，然后删除service_group
								ServiceGroupEntity servicegroupEntity=new ServiceGroupEntity();
								servicegroupEntity.setService_group_idx(service_group_idx);
								servicegroupEntity.setVersion_stamp(version_stamp);
								deleteNum=servicegroupDao.delete(servicegroupEntity);
								if(deleteNum>0){
									idx.append(service_group_idx).append(",");
								}else{
									//throw new RuntimeException("删除权限组失败！");
									result.setState(false);
									result.setRetMessage("optimistic");
									return result;
								}
							}
						}
						
					}
				}
				result.setState(true);
				result.setRetMessage(idx.toString().substring(0,idx.toString().length()-1));
			}
			
		}
		return result;
	}

	@Override
	public ResultEntity updservgroup(String req) {
		ResultEntity result=new ResultEntity();
		int updateNum=0;
		if(JSONUtils.mayBeJSON(req)){
			OperCmdMgmtPageEntity operCmdMgmtPage=JsonUtils.fromJson(req, OperCmdMgmtPageEntity.class);
			if(operCmdMgmtPage!=null){
				//
				//删除 制定的权限的不处理。
				//如果有修改 设备权限的话，则要清空缓存
				//
				Integer library_idx=operCmdMgmtPage.getLibrary_idx();
				String service_group_desc=operCmdMgmtPage.getService_group_desc();
				Integer service_group_idx=operCmdMgmtPage.getService_group_idx();
				String service_group_id=operCmdMgmtPage.getService_group_id();
				String service_group_name=operCmdMgmtPage.getService_group_name();
				String meta_opercmd_idx_str=operCmdMgmtPage.getMeta_opercmd_idx_str();
				Integer version_stamp=operCmdMgmtPage.getVersion_stamp();
				
				if(service_group_idx==null){
					result.setRetMessage("组IDX不能为空");
					return result;
				}
				if(library_idx==null){
					result.setRetMessage("图书馆IDX不能为空");
					return result;
				}
				if(org.apache.commons.lang.StringUtils.isBlank(service_group_name)){
					result.setRetMessage("组名不能为空");
					return result;
				}
				if(org.apache.commons.lang.StringUtils.isBlank(service_group_id)){
					result.setRetMessage("组ID不能为空");
					return result;
				}
				ServiceGroupEntity serviceGroup=new ServiceGroupEntity();
				serviceGroup.setLibrary_idx(library_idx);
				serviceGroup.setService_group_idx(service_group_idx);
				serviceGroup.setService_group_id(StringUtils.trimWhitespace(service_group_id));
				serviceGroup.setService_group_name(StringUtils.trimWhitespace(service_group_name));
				serviceGroup.setService_group_desc(service_group_desc);
				serviceGroup.setVersion_stamp(version_stamp);
				try {
					updateNum=servicegroupDao.update(serviceGroup);
				} catch (org.springframework.dao.DuplicateKeyException e) {
					String msg=e.getRootCause().getMessage();
					System.out.println(msg);
					AntPathMatcher matcher=new AntPathMatcher();
					if(matcher.match("*service_group_id*", msg)){
						throw new RuntimeException("组ID已存在，请修改");
					}else if(matcher.match("*service_group_name*", msg)){
						throw new RuntimeException("组名称已存在，请修改");
					}else{
						throw new RuntimeException(e);
					}				
				}catch (org.springframework.dao.DataIntegrityViolationException e) {
					String msg=e.getRootCause().getMessage();
					AntPathMatcher matcher=new AntPathMatcher();
					if(matcher.match("*Data too long*", msg)&&matcher.match("*service_group_desc*", msg)){
						throw new RuntimeException("备注字数超过100字，请修改");
					}else{
						throw new RuntimeException(e);
					}		
				}
				//LogUtils.info("更新 service_group 表 updateNum："+updateNum);
				if(updateNum<=0){
					result.setState(false);
					result.setRetMessage("optimistic");
					return result;
				}
				TreeSet<Integer> idxSet=new TreeSet<>();
				TreeSet<Integer> idxSetLocal=new TreeSet<>();
				if(StringUtils.hasText(meta_opercmd_idx_str)){
					for(String idxString :meta_opercmd_idx_str.split(",")){
						idxSet.add(Integer.parseInt(idxString));
					}
				}
				RelServiceGroupEntity relServiceGroupEntity=new RelServiceGroupEntity();
				//relServiceGroupEntity.setLibrary_idx(library_idx);
				relServiceGroupEntity.setService_group_idx(service_group_idx);
				//selectByidx 所有条件都有
				List<RelServiceGroupEntity> relServiceGroups=relServiceGroupDao.selectByidx(relServiceGroupEntity);
				if(relServiceGroups!=null){
					for(RelServiceGroupEntity relServiceGroup:relServiceGroups){
						Integer opercmd_idx=relServiceGroup.getMeta_opercmd_idx();
						if(opercmd_idx!=null){
							idxSetLocal.add(opercmd_idx);
						}
					}
				}
				//LogUtils.info("opercmd_idx SetLocal:"+idxSetLocal.toString());
				//LogUtils.info("opercmd_idx Set:"+idxSet.toString());
				if(idxSet.equals(idxSetLocal)){
					//相同的话则不须要删除 和添加操作
					//LogUtils.info("rel_service_group表数据相同，不需要操作");
				}else{
					//meta_opercmd_idx_str
					//如果没有变化，则不管，如果发生变化，则把原来的全部删除，新增加
					int delNum=relServiceGroupDao.deleteByServiceGroupIdxAndLibIdx(relServiceGroupEntity);
					//LogUtils.info("删除 rel_service_group 表数据 delNum："+delNum);
					if(delNum!=relServiceGroups.size()){
						//删除的数量不对等，则
						throw new RuntimeException("更新操作 删除数据失败！！");
					}else{
						while(!idxSet.isEmpty()){
							RelServiceGroupEntity addRelServiceGroup=new RelServiceGroupEntity();
							Integer cmdIdx=idxSet.pollFirst();
							addRelServiceGroup.setLibrary_idx(library_idx);
							addRelServiceGroup.setMeta_opercmd_idx(cmdIdx);
							addRelServiceGroup.setService_group_idx(service_group_idx);
							int inserNum=relServiceGroupDao.insert(addRelServiceGroup);
							//LogUtils.info("增加 rel_service_group 表数据 inserNum："+inserNum);
							if(inserNum<=0){
								throw new RuntimeException("更新操作 新增数据失败！！");
							}
						}
					}
					
				}
				RelOperatorServiceGroupEntity relOperatorServiceGroupEntity=new RelOperatorServiceGroupEntity();
				relOperatorServiceGroupEntity.setService_group_idx(service_group_idx);
				List<RelOperatorServiceGroupEntity> relOperatorServiceGroups=relOperatorServiceGroupDao.selectByidx(relOperatorServiceGroupEntity);
				if(relOperatorServiceGroups!=null && relOperatorServiceGroups.size()>0){
					for(RelOperatorServiceGroupEntity r:relOperatorServiceGroups){
						//设备默认分组IDX
						if(r.getOperator_group_idx()!=null && r.getOperator_group_idx()==1){
							result.setResult("DEV_PERMESSION_HAS_CHANGED");
						}
					}
				}
				result.setState(true);
				result.setRetMessage("IDX:"+service_group_idx+"|组名:"+service_group_name+"|||");
			}
		}
		return result;
	}



}
