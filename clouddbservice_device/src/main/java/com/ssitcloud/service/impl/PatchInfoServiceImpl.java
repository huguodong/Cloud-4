/**
 * 
 */
package com.ssitcloud.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.DeviceDao;
import com.ssitcloud.dao.PatchInfoDao;
import com.ssitcloud.entity.AskVersionResultEntity;
import com.ssitcloud.entity.DeviceEntity;
import com.ssitcloud.entity.PatchInfoEntity;
import com.ssitcloud.entity.UpgradeStrategyEntity;
import com.ssitcloud.entity.VersionUrlEntity;
import com.ssitcloud.entity.page.PatchInfoPageEntity;
import com.ssitcloud.service.PatchInfoService;

/**
 * @comment 版本信息表Service
 * 
 * @author hwl
 * @data 2016年4月11日
 */
@Service(value="patchInfoService")
public class PatchInfoServiceImpl implements PatchInfoService {

	@Resource
	PatchInfoDao patchInfoDao;
	
	@Resource
	private DeviceDao deviceDao;
	
	@Override
	public int addPatchInfo(PatchInfoEntity patchInfoEntity) {
		return patchInfoDao.insert(patchInfoEntity);
	}

	@Override
	public int deletePatchInfo(PatchInfoEntity patchInfoEntity) {
		return patchInfoDao.delete(patchInfoEntity);
	}

	@Override
	public int updatePatchInfo(PatchInfoEntity patchInfoEntity) {
		return patchInfoDao.update(patchInfoEntity);
	}

	@Override
	public List<PatchInfoEntity> selectPatchInfo(PatchInfoEntity patchInfoEntity) {
		return patchInfoDao.select(patchInfoEntity);
	}

	@Override
	public AskVersionResultEntity askVersion(UpgradeStrategyEntity updStrategy) {
		AskVersionResultEntity AskVersionResult=new AskVersionResultEntity();
		AskVersionResult.setLibrary_id(updStrategy.getLibrary_id());
		AskVersionResult.setDevice_id(updStrategy.getDevice_id());
		Assert.notNull(updStrategy, "UpgradeStrategyEntity == null error!!");
		//{"v1 v2 v3"}
		List<VersionUrlEntity> versionUrls=updStrategy.transforToVersionUrl().get(updStrategy.getOld_version());
		String targetVersion=updStrategy.getTarget_version();
		if(versionUrls==null){return AskVersionResult;}
		
		List<String> versions=new ArrayList<>();
	
		for(VersionUrlEntity versionUrlEntity:versionUrls){
			versions.add(versionUrlEntity.getVersion());
		}
		if(!versions.contains(targetVersion)){
			versions.add(targetVersion);
		}
		//拿到对应的版本信息，过滤出 全网/lib/dev类型
		List<PatchInfoEntity> patchInfos=patchInfoDao.selectListByVersions(versions);
		//取得设备类型
		Integer device_type_int=deviceDao.selectDevicTypeByDeviceId(updStrategy.getDevice_id());
		Assert.notNull(device_type_int, "查询不到设备类型！！！！");
		String device_type=device_type_int.toString();//String类型
		if(patchInfos!=null){
			boolean hasLibId=false;
			for(int i=0;i<patchInfos.size() ;i++){
				 PatchInfoEntity patchInfo=patchInfos.get(i);
				//图书馆限定
				if(PatchInfoEntity.LIB_RESTRICT.equals(patchInfo.getPatch_type())){
					String restrict_info=patchInfo.getRestrict_info();
					if(StringUtils.hasLength(restrict_info)){
						JsonNode node=JsonUtils.readTree(restrict_info);
						Iterator<JsonNode> it=node.getElements();
					NEXT1:while(it.hasNext()){
							JsonNode vnode=it.next();
							JsonNode textNode=vnode.get("library_id");
							String lib_id=textNode.getTextValue();
							if(lib_id!=null&&lib_id.equals(updStrategy.getLibrary_id())){
								hasLibId=true;
								break NEXT1;
							}
						}
					}
					//如果没有匹陪的lib_id，则移除
					if(!hasLibId){
						patchInfos.remove(i);
					}
					continue;
				}
				boolean hasDevice_type=false;
				//设备限定
				if(PatchInfoEntity.DEV_RESTRICT.equals(patchInfo.getPatch_type())){
					String restrict_info=patchInfo.getRestrict_info();
					if(StringUtils.hasLength(restrict_info)){
						JsonNode node=JsonUtils.readTree(restrict_info);
						Iterator<JsonNode> it=node.getElements();
					NEXT2:while(it.hasNext()){
							JsonNode vnode=it.next();
							JsonNode textNode=vnode.get("device_type");
							String device_typ=textNode.getTextValue();
							if(device_typ!=null&&device_typ.equals(device_type)){
								hasDevice_type=true;
								break NEXT2;
							}
						}
					}
					//如果没有匹陪的device_type，则移除
					if(!hasDevice_type){
						patchInfos.remove(i);
					}
				}
			}
		}
		versionUrls.clear();
		for(int i=0;i<patchInfos.size() ;i++){
			 PatchInfoEntity patchInfo=patchInfos.get(i);
			 VersionUrlEntity versionUrl=new VersionUrlEntity();
			 versionUrl.setVersion(patchInfo.getPatch_version());
			 versionUrl.setPath(patchInfo.getPatch_directory());//ftp
			 versionUrls.add(versionUrl);
		}
		AskVersionResult.setUpgradeStrategy(versionUrls);

		return AskVersionResult;
	}

	@Override
	public PatchInfoPageEntity selPatchInfoByPage(PatchInfoPageEntity patchInfoPageEntity) {
		return patchInfoDao.selectPage(patchInfoPageEntity);
	}

	@Override
	public ResultEntity deleteSinglePatchInfo(String idx) {
		ResultEntity resultEntity = new ResultEntity();
		StringBuilder sb=new StringBuilder();
		try {
			PatchInfoEntity patchInfoEntity = new PatchInfoEntity();
			patchInfoEntity.setPatch_idx(Integer.valueOf(idx));
			int ret= patchInfoDao.delete(patchInfoEntity);
			if (ret>=0) {
				resultEntity.setValue(true, "success");
			}else{
				resultEntity.setValue(false, "删除失败");
			}
			sb.append("模板IDX：").append(patchInfoEntity.getPatch_idx());
			resultEntity.setRetMessage(sb.toString());
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity delMultiPatchInfo(String json) {
		ResultEntity resultEntity = new ResultEntity();
		StringBuilder sb=new StringBuilder("模板IDX：");
		StringBuilder sbf=new StringBuilder("模板IDX：");
		try {
			if (!StringUtils.hasText(json)||"{}".equals(json)||"[]".equals(json)) {
				resultEntity.setValue(false, "参数不能为空");
				return resultEntity;
			}
			List<Map<String, Object>> list = JsonUtils.fromJson(json, List.class);
			Map<String, Object> resMap = new HashMap<>();
			String cannotDel = "";
			for (Map<String, Object> map : list) {
				String idx = map.get("idx").toString();
				String desc = map.get("desc").toString();
				PatchInfoEntity p = new PatchInfoEntity();
				p.setPatch_idx(Integer.valueOf(idx));
				int ret = patchInfoDao.delete(p);
				if (ret<0) {
					cannotDel += desc+",";
					sbf.append(idx).append(",");
				}else{
					sb.append(idx).append(",");
				}
			}
			if (!cannotDel.equals("")) {
				cannotDel = cannotDel.substring(0,cannotDel.length()-1);
			}
			resMap.put("cannotDel", cannotDel);
			resultEntity.setValue(true, "success", "", resMap);
			String ret="";
			if(sb.toString().endsWith(",")){
				sb.append(" 删除成功；");
				ret=sb.toString();
			}
			if(sbf.toString().endsWith(",")){
				sbf.append(" 删除失败");
				ret+=sbf.toString();
			}
			resultEntity.setRetMessage(ret);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	@Override
	public ResultEntity SetDeviceIdsLibDistinctInHeartBeatByPathInfoIdx(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			@SuppressWarnings("rawtypes")
			Map m=JsonUtils.fromJson(req, Map.class);
			if(m.containsKey("dataIdx")){
				String dataIdxStr=(String)m.get("dataIdx");
				if(dataIdxStr!=null){
					Integer dataIdx=Integer.parseInt(dataIdxStr);
					PatchInfoEntity p=patchInfoDao.selectByIdx(dataIdx);
					if(p==null){
						result.setRetMessage("PatchInfoEntity is NULL dataIdx:"+dataIdx);
						return result;
					}
					//关约束
					String r_info=p.getRestrict_info();
					if(JSONUtils.mayBeJSON(r_info)){
						List<Map<String,String>> listLibs=JsonUtils.fromJson(r_info, new TypeReference<List<Map<String,String>>>() {});
						List<String> libs=new ArrayList<>();
						if(listLibs!=null && !listLibs.isEmpty()){
							for(Map<String,String> ml:listLibs){
								libs.add(ml.get("library_id"));
							}
						}
						result.setState(true);
						result.setResult(libs);
					}
				}
			}
		}
		return result;
	}
	
	@Override
	public ResultEntity SetDeviceIdsDevTypeDistinctInHeartBeatByPathInfoIdx(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String, Object> m=JsonUtils.fromJson(req, Map.class);
			if(m.containsKey("dataIdx")){
				String dataIdxStr=(String)m.get("dataIdx");
				if(dataIdxStr!=null){
					Integer dataIdx=Integer.parseInt(dataIdxStr);
					PatchInfoEntity p=patchInfoDao.selectByIdx(dataIdx);
					if(p!=null){
						String r_info=p.getRestrict_info();
						if(JSONUtils.mayBeJSON(r_info)){
							List<String> devType=new ArrayList<String>();
							List<Map<String,Object>> devs=JsonUtils.fromJson(r_info, new TypeReference<List<Map<String,Object>>>() {});
							if(CollectionUtils.isNotEmpty(devs)){
								for(Map<String,Object> dev:devs){
									devType.add(dev.get("device_type").toString());
								}
							}
							List<DeviceEntity> deviceEntitys=deviceDao.selectByDevTypeList(devType);
							if(deviceEntitys!=null){
								result.setState(true);
								result.setResult(deviceEntitys);
							}
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public Integer selectPatchInfoCountByVersion(PatchInfoEntity patchInfoEntity) {
		return patchInfoDao.selectPatchInfoCountByVersion(patchInfoEntity);
	}
	
	
	

}
