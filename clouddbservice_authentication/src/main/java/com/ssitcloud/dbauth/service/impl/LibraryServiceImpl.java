package com.ssitcloud.dbauth.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ssitcloud.common.entity.OperatorType;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.dao.LibraryDao;
import com.ssitcloud.dbauth.dao.LibraryInfoDao;
import com.ssitcloud.dbauth.dao.LibraryTemplateDao;
import com.ssitcloud.dbauth.dao.OperationLogDao;
import com.ssitcloud.dbauth.dao.RelLibsDao;
import com.ssitcloud.dbauth.entity.LibraryEntity;
import com.ssitcloud.dbauth.entity.LibraryInfoEntity;
import com.ssitcloud.dbauth.entity.LibraryMetatypeInfoEntity;
import com.ssitcloud.dbauth.entity.LibraryServiceTemplateEntity;
import com.ssitcloud.dbauth.entity.MasterLibAndSlaveLibsEntity;
import com.ssitcloud.dbauth.entity.OperationLogEntity;
import com.ssitcloud.dbauth.entity.RelLibsEntity;
import com.ssitcloud.dbauth.entity.page.LibraryPageEntity;
import com.ssitcloud.dbauth.entity.page.LibraryPageTempInfoEntity;
import com.ssitcloud.dbauth.entity.page.LibraryUnionEntity;
import com.ssitcloud.dbauth.param.LibraryParam;
import com.ssitcloud.dbauth.service.LibraryService;
import com.ssitcloud.dbauth.utils.DateUtils;
import com.ssitcloud.dbauth.utils.JsonUtils;
import com.ssitcloud.dbauth.utils.LogUtils;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;

import net.sf.json.util.JSONUtils;

/**
 * 图书馆处理类
 * <p>2016年4月5日 上午11:31:35
 * @author hjc
 *
 */
@Transactional
@Service
public class LibraryServiceImpl implements LibraryService{
	@Resource
	private LibraryDao libraryDao;
	
	@Resource
	private LibraryTemplateDao templateDao;
	
	@Resource
	private LibraryInfoDao libraryInfoDao;
	
	@Resource
	private OperationLogDao operationLogDao;
	
	@Resource 
	private RelLibsDao relLibsDao;
	
	@Override
	public int addLibrary(LibraryEntity libraryEntity) {
		return libraryDao.addLibrary(libraryEntity);
	}

	@Override
	public int delLibraryByIdx(LibraryEntity libraryEntity) {
		return libraryDao.delLibraryByIdx(libraryEntity);
	}

	@Override
	public LibraryEntity selLibraryByIdxOrId(LibraryEntity libraryEntity) {
		return libraryDao.selLibraryByIdxOrId(libraryEntity);
	}

	@Override
	public List<LibraryEntity> selLibraryByIdxsOrIds(Map<String, Object> infos) {
		try {
			List<String> library_lids = new ArrayList<>();
			List<String> lib_ids = new ArrayList<>();
			Map<String, Object> param = new HashMap<String, Object>();
			if (infos.get("library_idx")!=null && !infos.get("library_idx").toString().equals("")) {
				String[] idxs = infos.get("library_idx").toString().split(",");
				for (int i = 0; i < idxs.length; i++) {
					library_lids.add(idxs[i]);
				}
			}
			if (infos.get("lib_id")!=null && !infos.get("lib_id").toString().equals("")) {
				String[] ids = infos.get("lib_id").toString().split(",");
				for (int i = 0; i < ids.length; i++) {
					lib_ids.add(ids[i]);
				}
			}
			//如果不传递参数，返回所有的图书馆信息
//			if(library_lids.isEmpty() && lib_ids.isEmpty()){
//				return new ArrayList<>(0);
//			}
			param.put("lib_idxs", library_lids);
			param.put("lib_ids", lib_ids);
			return libraryDao.selLibraryByIdxsOrIds(param);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ResultEntity addNewLibrary(LibraryParam libraryParam) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			//根据图书馆模板id获取模板的相关信息，计算到期时间
			LibraryServiceTemplateEntity templateEntity = new LibraryServiceTemplateEntity();
			Integer tplId = libraryParam.getLib_service_tpl_id();
			if (tplId!=null) {
				templateEntity.setLib_service_tpl_id(tplId);
				templateEntity = templateDao.selLibraryServiceTemplateEntity(templateEntity);
			}
			if (templateEntity==null || templateEntity.getLib_service_tpl_id() == null) {
				throw new RuntimeException("无效的图书馆模板id");
			}
			//设置要保存的图书馆信息
			LibraryEntity libraryEntity = new LibraryEntity();
			libraryEntity.setLib_id(libraryParam.getLib_id());
			libraryEntity.setLib_name(libraryParam.getLib_name());
			libraryEntity.setLib_service_tpl_id(tplId);
			libraryEntity.setLib_type(libraryParam.getLib_type());
			
			//计算并且设置到期时间 
			//新增图书馆信息时，图书馆服务时间为管理员设置时间，故将当前时间加服务期限的逻辑注释 add by huanghuang 20170221
//			Integer months = templateEntity.getService_cycle();
//			Date date = new Date();
//			date = DateUtils.addMonths(date, months);
//			libraryEntity.setService_expire_date(new Timestamp(date.getTime()));
			
			Timestamp timestamp=null;
			Timestamp startTime=null;
			try {
				timestamp=new Timestamp(DateUtils.dayFormat(libraryParam.getService_expire_date()).getTime());
				startTime=new Timestamp(DateUtils.dayFormat(libraryParam.getService_start_date()).getTime());
			} catch (java.lang.IllegalArgumentException e) {
				resultEntity.setRetMessage("请输入正确的时间格式，如2016-01-01 10:00:00");
				return  resultEntity;
			}
			libraryEntity.setService_expire_date(timestamp);
			libraryEntity.setService_start_date(startTime);
			
			
			//保存图书馆信息到library表
			int libRet = 0;
			try {
				libRet=libraryDao.addLibrary(libraryEntity);
			} catch (org.springframework.dao.DuplicateKeyException e) {
				String msg=e.getRootCause().getMessage();
				if(StringUtils.contains(msg, "Duplicate entry")&&StringUtils.contains(msg, "lib_id")){
					//throw new RuntimeException("图书馆ID已经存在，请更改");
					resultEntity.setRetMessage("图书馆ID已经存在，请更改");
					return resultEntity;
				}
				if(StringUtils.contains(msg, "Duplicate entry")&&StringUtils.contains(msg, "lib_name")){
					//throw new RuntimeException("图书馆名已经存在，请更改");
					resultEntity.setRetMessage("图书馆名已经存在，请更改");
					return resultEntity;
				}
				resultEntity.setRetMessage(msg);
				LogUtils.error(msg, e);
				return resultEntity;
			}
			
			if (libRet != 1) {throw new RuntimeException("新增图书馆失败");}
			if (libraryEntity.getLibrary_idx()==null) {throw new RuntimeException("新增图书馆失败");}
			
			//缓存图书馆
			JedisUtils.getInstance().hset(RedisConstant.LIBRARY_KEY,libraryEntity.getLib_id(),JsonUtils.toJson(libraryEntity));
			//缓存图书馆idx与id的映射
			JedisUtils.getInstance().set(RedisConstant.LIBRARY_KEYS+libraryEntity.getLibrary_idx()+"", libraryEntity.getLib_id());
			
			//获取到library_idx,添加图书馆信息到 library_info表
			libraryParam.setLibrary_idx(libraryEntity.getLibrary_idx());//保存新增图书馆的idx
			List<LibraryInfoEntity> failedList = new ArrayList<>();//保存失败的library_info的信息
			List<LibraryInfoEntity> successList = new ArrayList<>();//保存成功的library_info信息
			
			//循环添加图书馆信息到library_info
			for (LibraryInfoEntity	info : libraryParam.getLibrary_infos()) {
				info.setLibrary_idx(libraryEntity.getLibrary_idx());
				int infoRet = libraryInfoDao.addLibraryInfo(info);
				if (infoRet <= 0) {
					failedList.add(info);
				}else{
					successList.add(info);
				}
			}
			if (failedList.size()>0) {
				resultEntity.setRetMessage("以下项目没有添加成功:"+JsonUtils.toJson(failedList));
			}
			libraryParam.setLibrary_infos(successList);
			//添加图书馆关系到rel_libs,需要判断主馆的模板 限制参数，能加多少个子馆。
			if(libraryParam.getRellib_info() !=null){
				RelLibsEntity relLibsEntity=new RelLibsEntity();
				relLibsEntity.setMaster_lib_id(libraryParam.getRellib_info().getMaster_lib_id());
				List<RelLibsEntity> relLibsEntitys=relLibsDao.selectByIdx(relLibsEntity);
				if(libraryParam.getRellib_info().getMaster_lib_id()!=null){
					LibraryServiceTemplateEntity libTemp=templateDao.selTempByLibraryIdx(libraryParam.getRellib_info().getMaster_lib_id().toString());
					if(libTemp!=null){
						Integer maxSublibCount=libTemp.getMax_sublib_count();
						if(maxSublibCount!=null){
							if(relLibsEntitys!=null){
								//如果max <= 实际的子馆数量 则不给新增。
								if(maxSublibCount<=relLibsEntitys.size()){
									throw new RuntimeException("超过拥有子馆最大限额，请修改图书馆模版子馆限制数");
								}
							}
						}
					}
				}
				libraryParam.getRellib_info().setSlave_lib_id(libraryParam.getLibrary_idx());
				int rel_id = relLibsDao.addNewRelLibs(libraryParam.getRellib_info());
				if(rel_id <= 0){throw new RuntimeException("新增图书馆主从关系失败");}
			}
			if(libraryParam.getLib_type() == 1){
				for (RelLibsEntity rel : libraryParam.getRelSubLibs()) {
					//虚拟主馆此处不做拥有子馆最大限额判断
					rel.setMaster_lib_id(libraryParam.getLibrary_idx());
					int rel_id = relLibsDao.addNewRelLibs(rel);
					if(rel_id <= 0){throw new RuntimeException("新增图书馆主从关系失败");}
					
				}
			}
			
			//所有操作完成之后，保存用户操作日志  
			// 2017年3月4号修改新命令码
			OperationLogEntity logEntity = new OperationLogEntity();
			logEntity.setOperator_idx(libraryParam.getOperator_idx());
			logEntity.setClient_ip(libraryParam.getClient_ip()==null?"is null":libraryParam.getClient_ip());
			logEntity.setClient_port(libraryParam.getClient_port()==null?"is null":libraryParam.getClient_port());
			logEntity.setOperation_cmd("0102010101");
			logEntity.setOperation_result("true");
			//2017年3月6号修改格式馆IDX||馆名||
			logEntity.setOperation("馆IDX:"+libraryEntity.getLibrary_idx()+"||馆名:"+libraryEntity.getLib_name()+"||");
			operationLogDao.addOperationLog(logEntity);
			
			resultEntity.setValue(true, "success", resultEntity.getMessage(), libraryParam);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return resultEntity;
	}

	@Transactional
	@Override
	public ResultEntity modifyLibrary(LibraryParam libraryParam) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			//获取library表数据，进行更新
			LibraryEntity libraryEntity = new LibraryEntity();
			libraryEntity.setLibrary_idx(libraryParam.getLibrary_idx());
			libraryEntity.setLib_id(libraryParam.getLib_id());
			libraryEntity.setLib_name(libraryParam.getLib_name());
			libraryEntity.setLib_service_tpl_id(libraryParam.getLib_service_tpl_id());
			libraryEntity.setVersion_stamp(libraryParam.getVersion_stamp());
			Timestamp timestamp=null;
			Timestamp startTime=null;
			try {
				timestamp=new Timestamp(DateUtils.dayFormat(libraryParam.getService_expire_date()).getTime());
				startTime=new Timestamp(DateUtils.dayFormat(libraryParam.getService_start_date()).getTime());
			} catch (java.lang.IllegalArgumentException e) {
				resultEntity.setRetMessage("请输入正确的时间格式，如2016-01-01 10:00:00");
				return  resultEntity;
			}
			libraryEntity.setService_expire_date(timestamp);
			libraryEntity.setService_start_date(startTime);
			if(libraryEntity.getLibrary_idx()==0){//虚拟馆禁止通过页面修改
				return resultEntity;
			}
			int libRet =0;
			try {  
				//更新当前主馆的子馆信息 add by huanghuang 20170221 start //修改只更新所有子馆信息 lqw 2017-5-11
//				LibraryPageEntity libraryPage = new LibraryPageEntity();
//				libraryPage.setLibrary_idx(libraryEntity.getLibrary_idx());
				List<LibraryEntity> list = libraryDao.querySlaveLibraryByMasterIdx(libraryEntity.getLibrary_idx());
//				List<LibraryPageEntity> list = resultPageEntity.getRows();
				LibraryEntity lib = new LibraryEntity();
				for (LibraryEntity library : list) {
					lib.setLibrary_idx(library.getLibrary_idx());
					lib.setLib_id(library.getLib_id());
					lib.setLib_name(library.getLib_name());
					lib.setLib_service_tpl_id(libraryParam.getLib_service_tpl_id());
					lib.setService_expire_date(timestamp);
					lib.setService_start_date(startTime);
					libraryDao.updateLibrary(lib);
				}
				//更新当前主馆的子馆信息 add by huanghuang 20170221 end //修改只更新所有子馆信息 lqw 2017-5-11
				//更新当前主馆			
				libRet = libraryDao.updateLibrary(libraryEntity);
				//更新缓存
				JedisUtils.getInstance().hset(RedisConstant.LIBRARY_KEY,libraryEntity.getLib_id(),JsonUtils.toJson(libraryEntity));
				/*//缓存图书馆idx与id的映射
				JedisUtils.getInstance().set(RedisConstant.LIBRARY_KEYS+libraryEntity.getLibrary_idx()+"", libraryEntity.getLib_id());*/
			} catch (org.springframework.dao.DuplicateKeyException e) {
				String msg=e.getRootCause().getMessage();
				if(StringUtils.contains(msg, "Duplicate entry")&&StringUtils.contains(msg, "lib_id")){
					resultEntity.setRetMessage("图书馆ID已经存在，请更改");
					return resultEntity;
				}
				if(StringUtils.contains(msg, "Duplicate entry")&&StringUtils.contains(msg, "lib_name")){
					resultEntity.setRetMessage("图书馆名已经存在，请更改");
					return resultEntity;
				}
				resultEntity.setRetMessage(msg);
				LogUtils.error(msg, e);
				return resultEntity;
			}
			
			
			if(libRet!=1){
				resultEntity.setState(false);
				resultEntity.setRetMessage("optimistic");
				return resultEntity;
			}
			resultEntity.setValue(true, "success", resultEntity.getMessage(), libraryParam);
			
			//获取library_info表数据，进行更新
			int inforet = libraryInfoDao.deleteLibInfo(libraryParam.getLibrary_idx());//删除原来的library_info信息
			
			List<LibraryInfoEntity> failedList = new ArrayList<>();//保存失败的library_info的信息
			List<LibraryInfoEntity> successList = new ArrayList<>();//保存成功的library_info信息
			
			//循环添加图书馆信息到library_info
			for (LibraryInfoEntity	info : libraryParam.getLibrary_infos()) {
				info.setLibrary_idx(libraryEntity.getLibrary_idx());
				inforet = libraryInfoDao.addLibraryInfo(info);
				if (inforet <= 0) {
					failedList.add(info);
				}else{
					successList.add(info);
				}
			}
			if (failedList.size()>0) {
				resultEntity.setRetMessage("以下项目没有添加成功:"+JsonUtils.toJson(failedList));
			}
			libraryParam.setLibrary_infos(successList);
			
			//修改图书馆关系表
			if(libraryParam.getRellib_info() !=null){
				RelLibsEntity relLibsEntity=new RelLibsEntity();
				relLibsEntity.setMaster_lib_id(libraryParam.getRellib_info().getMaster_lib_id());
				List<RelLibsEntity> relLibsEntitys=relLibsDao.selectByIdx(relLibsEntity);
				if(libraryParam.getRellib_info().getMaster_lib_id()!=null){
					LibraryServiceTemplateEntity libTemp=templateDao.selTempByLibraryIdx(libraryParam.getRellib_info().getMaster_lib_id().toString());
					if(libTemp!=null){
						Integer maxSublibCount=libTemp.getMax_sublib_count();
						if(maxSublibCount!=null){
							if(relLibsEntitys!=null){
								//如果max < 实际的子馆数量 则不给新增。
								if(maxSublibCount<relLibsEntitys.size()){
									throw new RuntimeException("超过拥有子馆最大限额，请修改图书馆模版子馆限制数");
								}
							}
						}
					}
				}
				
				libraryParam.getRellib_info().setSlave_lib_id(libraryParam.getLibrary_idx());
				List<RelLibsEntity> rLibsList = relLibsDao.selectSlaveLibsByidx(libraryParam.getLibrary_idx());
				if(!rLibsList.isEmpty()){
					int rel_ret = relLibsDao.updateRelLibs(libraryParam.getRellib_info());
					
					if(rel_ret <= 0 ){throw new RuntimeException("修改图书馆主从关系失败");} 
					
				}else{
					int rel_id = relLibsDao.addNewRelLibs(libraryParam.getRellib_info());
					if(rel_id <= 0){throw new RuntimeException("增加图书馆主从关系失败");}
				}
			}else {
				List<RelLibsEntity> rLibsList = relLibsDao.selectSlaveLibsByidx(libraryParam.getLibrary_idx());
				if(!rLibsList.isEmpty()){
					int rel_ret = relLibsDao.delRellibsBySlaveidx(libraryParam.getLibrary_idx());
					if(rel_ret <= 0 ){throw new RuntimeException("删除图书馆主从关系失败");} 
				}
			}
			
			if(libraryParam.getLib_type() == 1){
				int rel_ret = relLibsDao.delRellibsByMasteridx(libraryParam.getLibrary_idx());
				//if(rel_ret <= 0 ){throw new RuntimeException("删除图书馆主从关系失败");} 
				for (RelLibsEntity rel : libraryParam.getRelSubLibs()) {
					//虚拟主馆此处不做拥有子馆最大限额判断
					rel.setMaster_lib_id(libraryParam.getLibrary_idx());
					int rel_id = relLibsDao.addNewRelLibs(rel);
					if(rel_id <= 0){throw new RuntimeException("新增图书馆主从关系失败");}
					
				}
			}
			
			//所有操作完成之后，保存用户操作日志
			//2017年3月4号修改新命令码
			OperationLogEntity logEntity = new OperationLogEntity();
			logEntity.setOperator_idx(libraryParam.getOperator_idx());
			logEntity.setClient_ip(libraryParam.getClient_ip()==null?"is null":libraryParam.getClient_ip());
			logEntity.setClient_port(libraryParam.getClient_port()==null?"is null":libraryParam.getClient_port());
			logEntity.setOperation_cmd("0102010103");
			logEntity.setOperation_result("true");
			//2017年3月6号修改格式馆IDX||馆名||
			logEntity.setOperation("馆IDX:"+libraryEntity.getLibrary_idx()+"||馆名:"+libraryEntity.getLib_name()+"||");
			operationLogDao.addOperationLog(logEntity);
			
			resultEntity.setValue(true, "success", resultEntity.getMessage(), libraryParam);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return resultEntity;
	}

	@Override
	public List<LibraryEntity> selLibraryByFuzzy(LibraryEntity libraryEntity) {
		return libraryDao.selLibraryFuzzyByID(libraryEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LibraryPageEntity sellibraryInfo(String json) {
		List<Map<String, Object>> libinfo = new ArrayList<>();
		LibraryPageEntity libraryPage = JsonUtils.fromJson(json, LibraryPageEntity.class);
		LibraryPageEntity resultPageEntity = libraryDao.selLibraryinfo(libraryPage);
		List<LibraryPageEntity> list = resultPageEntity.getRows();
		for (LibraryPageEntity libraryEntity : list) {
			Map<String, Object> map = new HashMap<>();
			List<LibraryMetatypeInfoEntity> libMetatypeInfo = libraryInfoDao.selectLibraryInfoByidx(libraryEntity.getLibrary_idx());
			List<RelLibsEntity> libsEntities = relLibsDao.selectRelLibsByidx(libraryEntity.getLibrary_idx());
			map.put("library_idx", libraryEntity.getLibrary_idx());
			map.put("lib_id", libraryEntity.getLib_id());
			map.put("lib_name", libraryEntity.getLib_name());
			map.put("lib_service_tpl_id", libraryEntity.getLib_service_tpl_id());
			String startTime=DateUtils.timestampToYYYYMMDD(libraryEntity.getService_start_date());
			String endTime=DateUtils.timestampToYYYYMMDD(libraryEntity.getService_expire_date());
			
			map.put("service_start_date", startTime);
			map.put("service_expire_date", endTime);
			map.put("createtime", libraryEntity.getCreatetime().toString());
			map.put("version_stamp", libraryEntity.getVersion_stamp());
			map.put("libInfo", libMetatypeInfo);
			map.put("relLibs", libsEntities);
			libinfo.add(map);
		}
		resultPageEntity.setRows(libinfo);
		return resultPageEntity;
	}

	@Override
	public List<LibraryEntity> selMasterLib() {
		return libraryDao.selMasterlib();
	}

	@SuppressWarnings("unchecked")
	@Override
	public LibraryPageTempInfoEntity selLibInfoByParam(String json) {
		List<Map<String, Object>> libinfo = new ArrayList<>();
		LibraryPageTempInfoEntity lTempInfoEntity = JsonUtils.fromJson(json, LibraryPageTempInfoEntity.class);
		LibraryPageTempInfoEntity resultPageEntity = libraryDao.selLibInfoByParam(lTempInfoEntity);
		
		List<LibraryPageTempInfoEntity> list = resultPageEntity.getRows();
		for (LibraryPageTempInfoEntity libraryEntity : list) {
			Map<String, Object> map = new HashMap<>();
			List<LibraryMetatypeInfoEntity> libMetatypeInfo = libraryInfoDao.selectLibraryInfoByidx(libraryEntity.getLibrary_idx());
			List<RelLibsEntity> libsEntities = relLibsDao.selectRelLibsByidx(libraryEntity.getLibrary_idx());
			map.put("library_idx", libraryEntity.getLibrary_idx());
			map.put("lib_id", libraryEntity.getLib_id());
			map.put("lib_name", libraryEntity.getLib_name());
			map.put("lib_service_tpl_id", libraryEntity.getLib_service_tpl_id());
			map.put("service_start_date", libraryEntity.getService_start_date().toString());
			map.put("service_expire_date", libraryEntity.getService_expire_date().toString());
			map.put("createtime", libraryEntity.getCreatetime().toString());
			map.put("version_stamp", libraryEntity.getVersion_stamp());
			map.put("libInfo", libMetatypeInfo);
			map.put("relLibs", libsEntities);
			libinfo.add(map);
		}
		resultPageEntity.setRows(libinfo);
		return resultPageEntity;
	}

	@Transactional
	@Override
	public ResultEntity delLibraryInfoByIdx(String json) {
		
		ResultEntity result=new ResultEntity();
		int delNum=0;
		StringBuilder sb=new StringBuilder();
		StringBuilder idx=new StringBuilder("馆IDX:");
		if(JSONUtils.mayBeJSON(json)){
			List<LibraryEntity> list = JsonUtils.fromJson(json, new TypeReference<List<LibraryEntity>>() {});
			if(CollectionUtils.isNotEmpty(list)){
				for(LibraryEntity libraryEntity:list){
					Integer library_idx=libraryEntity.getLibrary_idx();
					libraryInfoDao.deleteLibInfo(library_idx);
					relLibsDao.deleteRelLibs(library_idx);
					delNum = libraryDao.delLibraryByIdx(libraryEntity);
					//删除缓存中的数据
					JedisUtils.getInstance().hdel(RedisConstant.LIBRARY_KEY, libraryEntity.getLib_id());
					//删除lib_idx与lib_id映射
					JedisUtils.getInstance().del(RedisConstant.LIBRARY_KEYS+libraryEntity.getLibrary_idx());
					if(delNum>0){
						idx.append(library_idx).append(",");
					}else{
						result.setState(false);
						result.setRetMessage("optimistic");
						return result;
					}
				}
			}
			if(sb.length()>0){
				result.setState(false);
				result.setRetMessage(sb.substring(0, sb.length()-1));
			}else{
				result.setState(true);
				result.setRetMessage(idx.toString().substring(0,idx.toString().length()-1));
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity querylibInfoByCurUser(String req) {
		ResultEntity result=new ResultEntity();
		String libraryIdx = null; 
		String operatorType = null; 
		if(JSONUtils.mayBeJSON(req)){
			Map<String,Object> map=JsonUtils.fromJson(req, Map.class);
			if(map!=null){
				if(map.get("library_idx")!=null){
					libraryIdx=map.get("library_idx").toString();
				}
				if(map.get("operator_type")!=null){
					operatorType=map.get("operator_type").toString();
				}
				LibraryEntity library=new LibraryEntity();
				//系统管理员或者海恒维护账号 则
				if(OperatorType.CLOUD_ADMIN.equals(operatorType)||
						OperatorType.LIBRARY_ADMIN.equals(operatorType)||
						OperatorType.MAINTRINER.equals(operatorType)){
					List<LibraryEntity> libList=libraryDao.selMasterlib();
					result.setResult(libList);
					result.setState(true);
				}else{
					//其他用户
					if(libraryIdx!=null){
						library.setLibrary_idx(Integer.parseInt(libraryIdx));
						LibraryEntity libraryEntity=libraryDao.selLibraryByIdxOrId(library);
						result.setResult(libraryEntity);
						result.setState(true);
					}
				}
			}
		}
		
		return result;
	}
	/**
	 * req={
	 * 	library_idx:"....."
	 * }
	 */
	@Override
	public ResultEntity querySlaveLibraryByMasterIdx(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			LibraryEntity library=JsonUtils.fromJson(req, LibraryEntity.class);
			if(library!=null&&library.getLibrary_idx()!=null){
				//父馆信息
				LibraryEntity masterLibrary=selLibraryByIdxOrId(library);
				//下属馆信息
				Map<Integer, LibraryEntity> map = new HashMap<>();
				List<LibraryEntity> libraryEntities = new ArrayList<>();
				libraryEntities.add(masterLibrary);
				querySlaveLibs(libraryEntities, map);
				libraryEntities.clear();
				libraryEntities.addAll(map.values());
				//List<LibraryEntity> slaveLibs=libraryDao.querySlaveLibraryByMasterIdx(library.getLibrary_idx());
				MasterLibAndSlaveLibsEntity masterLibAndSlaveLibs=new MasterLibAndSlaveLibsEntity();
				masterLibAndSlaveLibs.setMasterLibrary(masterLibrary);
				masterLibAndSlaveLibs.setSlaveLibrary(libraryEntities);
				result.setResult(masterLibAndSlaveLibs);
				result.setState(true);
			}
		}
		return result;
	}
	
	private void querySlaveLibs(List<LibraryEntity> libraryEntities,Map<Integer, LibraryEntity>map){
		if(libraryEntities == null || libraryEntities.isEmpty()){
			return;
		}
		List<LibraryEntity> entities = new ArrayList<>();
		for(LibraryEntity libraryEntity : libraryEntities){
			List<LibraryEntity> list = libraryDao.querySlaveLibraryByMasterIdx(libraryEntity.getLibrary_idx());
			for(LibraryEntity entity : list){
				map.put(entity.getLibrary_idx(), entity);
				entities.add(entity);
			}
		}
		if(!entities.isEmpty()){
			querySlaveLibs(entities, map);
		}
	}

	@Override
	public ResultEntity queryAllMasterLibAndSlaveLib(String req) {
		ResultEntity result=new ResultEntity();
		//req assert null or '' 
		List<LibraryEntity> notSlaveLibs=libraryDao.queryLibWhereisNotSlaveLib(new LibraryEntity());
		List<MasterLibAndSlaveLibsEntity> masterLibAndSlaveLibsList=new ArrayList<>();
		if(CollectionUtils.isNotEmpty(notSlaveLibs)){
			for(LibraryEntity library:notSlaveLibs){
				//下属馆信息
				List<LibraryEntity> slaveLibs=libraryDao.querySlaveLibraryByMasterIdx(library.getLibrary_idx());
				MasterLibAndSlaveLibsEntity masterLibAndSlaveLibs=new MasterLibAndSlaveLibsEntity();
				masterLibAndSlaveLibs.setMasterLibrary(library);
				masterLibAndSlaveLibs.setSlaveLibrary(slaveLibs);
				masterLibAndSlaveLibsList.add(masterLibAndSlaveLibs);
			}
			result.setResult(masterLibAndSlaveLibsList);
			result.setState(true);
		}
		return result;
	}

	@Override
	public ResultEntity getLibIdAndLibIdx(String req) {
		ResultEntity result=new ResultEntity();
		result.setResult(libraryDao.getLibIdAndLibIdx());
		result.setState(true);
	    return result;
	}

	@Override
	public ResultEntity delLibraryInfoByIdxArray(Map<String, Object> operMap) {
		ResultEntity result=new ResultEntity();
		StringBuilder lib_idx=new StringBuilder();
		StringBuilder deleteFailIdxs=new StringBuilder();
		int ret=0;
		if(operMap!=null){
			String libparam = JsonUtils.toJson(operMap.get("idx"));
			List<LibraryEntity> list = JsonUtils.fromJson(libparam, new TypeReference<List<LibraryEntity>>() {});
			for (LibraryEntity libraryEntity : list) {
				int res =0;
				try {
					res += libraryInfoDao.deleteLibInfo(libraryEntity.getLibrary_idx());
					res += relLibsDao.deleteRelLibs(libraryEntity.getLibrary_idx());
					ret +=libraryDao.delLibraryByIdx(libraryEntity);
					lib_idx.append(libraryEntity.getLibrary_idx()).append(",");
				} catch (Exception e) {
					if(e instanceof  org.springframework.dao.DataIntegrityViolationException){
						String msg=((org.springframework.dao.DataIntegrityViolationException) e).getRootCause().getMessage();
						LogUtils.error(msg, e);
						if(StringUtils.contains(msg, "Cannot delete or update a parent row: a foreign key constraint fails")){
							deleteFailIdxs.append(libraryEntity.getLibrary_idx()).append(",");
						}
					}else{
						throw new RuntimeException(e);
					}
				}
				if(lib_idx.length()>0&&deleteFailIdxs.length()>0){
					result.setRetMessage("删除成功的图书馆IDX:"+lib_idx.toString().substring(0,lib_idx.length()-1)+"|删除失败的图书馆IDX:"+deleteFailIdxs.toString().substring(0,deleteFailIdxs.length()-1));
				}else if(lib_idx.length()>0){
					result.setState(true);
					result.setRetMessage("删除成功的图书馆IDX:"+lib_idx.toString().substring(0,lib_idx.length()-1));
				}else if(deleteFailIdxs.length()>0){
					result.setRetMessage("删除失败的图书馆IDX:"+deleteFailIdxs.toString().substring(0,deleteFailIdxs.length()-1));
				}
			}
		}
		return result;
	}

	@Override
	public ResultEntity selLibraryIDByFuzzy(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			LibraryEntity libraryEntity=JsonUtils.fromJson(req, LibraryEntity.class);
			if(libraryEntity!=null){
				List<String> libIds=libraryDao.selLibraryIDByFuzzy(libraryEntity);
				result.setResult(libIds);
				result.setState(true);
			}
		}
		return result;
	}

	@Override
	public ResultEntity querylibInfoByCurUserEXT1(String req) {
		ResultEntity result=new ResultEntity();
		String libraryIdx = null; 
		String operatorType = null; 
		if(JSONUtils.mayBeJSON(req)){
			Map<String,Object> map=JsonUtils.fromJson(req, Map.class);
			if(map!=null){
				if(map.get("library_idx")!=null){
					libraryIdx=map.get("library_idx").toString();
				}
				if(map.get("operator_type")!=null){
					operatorType=map.get("operator_type").toString();
				}
				LibraryEntity library=new LibraryEntity();
				//系统管理员或者 海恒维护账号 则
				if(OperatorType.CLOUD_ADMIN.equals(operatorType)||
						OperatorType.MAINTRINER.equals(operatorType)){
					List<LibraryEntity> libList=libraryDao.selMasterlib();
					result.setResult(libList);
					result.setState(true);
				}else{
					if(!StringUtils.isBlank(libraryIdx)){
						
						library.setLibrary_idx(Integer.parseInt(libraryIdx));
					}
					 if(OperatorType.LIBRARY_ADMIN.equals(operatorType)){
						List<LibraryEntity> notSlaveLibs=libraryDao.queryLibWhereisNotSlaveLib(library);
						if(CollectionUtils.isNotEmpty(notSlaveLibs)){
							//父馆信息
							//LibraryEntity masterLibrary=selLibraryByIdxOrId(library);
							LibraryEntity masterLibrary=notSlaveLibs.get(0);
							//下属馆信息
							List<LibraryEntity> slaveLibs=libraryDao.querySlaveLibraryByMasterIdx(library.getLibrary_idx());
							MasterLibAndSlaveLibsEntity masterLibAndSlaveLibs=new MasterLibAndSlaveLibsEntity();
							masterLibAndSlaveLibs.setMasterLibrary(masterLibrary);
							masterLibAndSlaveLibs.setSlaveLibrary(slaveLibs);
							result.setMessage("_MASTER_SLAVE_");
							result.setResult(masterLibAndSlaveLibs);
							result.setState(true);
						}else if(libraryIdx!=null){//分馆用户
							LibraryEntity libraryEntity=libraryDao.selLibraryByIdxOrId(library);
							result.setResult(libraryEntity);
							result.setMessage("_SLAVE_");
							result.setState(true);
						}
					 }
					 //其他用户
					 else if(libraryIdx!=null){
						LibraryEntity libraryEntity=libraryDao.selLibraryByIdxOrId(library);
						result.setResult(libraryEntity);
						result.setMessage("_SLAVE_");//非图书馆管理员 则只能看到本馆
						result.setState(true);
					}
				}
			}
		}
		return result;
	}


	@Override
	public ResultEntity selectChildLibrary(Map<String, Object> param,Integer pageCurrent,Integer pageSize) {
		ResultEntity result = new ResultEntity();
		try{
			Integer limitS = null;
			Integer limitE = null;
			if(pageCurrent != null && pageSize != null){
				limitE = pageSize>0?pageSize:15;
				limitS = pageCurrent>0?(pageCurrent-1)*limitE:0;
			}
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("master_lib_idx", param.get("master_lib_idx"));
			m.put("lib_name",param.get("lib_name"));
			m.put("regionCode", param.get("regionCode"));
			List<LibraryUnionEntity>  librarys = libraryDao.selectChildLibrary(m,limitS,limitE);
			result.setResult(librarys);
			result.setState(true);
		}catch(Exception e){
			LogUtils.info(e);
			result.setMessage(e.getMessage());
		}
		
		return result;
	}


	@Override
	public ResultEntity getAllLibraryList(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			
			List<LibraryEntity> list = libraryDao.getAllLibraryList();
			resultEntity.setValue(true, "success", "", list);
		} catch (Exception e) {
			resultEntity.setValue(false, "获取所有图书馆数据出错");
			LogUtils.error("获取所有图书馆数据出错",e);
		}
		return resultEntity;
	}
	
	

	@Override
	public ResultEntity selLibraryByNameORLibId(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			LibraryEntity libraryEntity=JsonUtils.fromJson(req, LibraryEntity.class);
			if(libraryEntity!=null){
				List<LibraryEntity> list=libraryDao.selLibraryByNameORLibId(libraryEntity);
				result.setResult(list);
				result.setState(true);
			}
		}
		return result;
	}
	
	/**
	 * 根据馆id查询父馆
	 * @param library_idx
	 * @return
	 */
	public ResultEntity queryMasterLibraryBySlaveIdx(String req) {
		ResultEntity result = new ResultEntity();
		if (JSONUtils.mayBeJSON(req)) {
			LibraryEntity libraryEntity = JsonUtils.fromJson(req, LibraryEntity.class);
			if (libraryEntity != null) {
				Integer library_idx = libraryEntity.getLibrary_idx();
				LibraryEntity entity = libraryDao.queryMasterLibraryBySlaveIdx(library_idx);
				if(entity!=null){
					result.setResult(entity);
					result.setState(true);
					return result;
				}
			}
		}
		result.setMessage("未查找到父馆!");
		result.setResult(null);
		result.setState(false);
		return result;
	}

	@Override
	public List<Map<String, Object>> selectChildLibraryRegionCode(Integer mastLibIdx) {
		return libraryDao.selectChildLibraryRegionCode(mastLibIdx);
	}

	@Override
	public List<LibraryUnionEntity> selectLibraryAndInfo(Map<String, Object> param) {
		return libraryDao.selectLibraryAndInfo(param);
	}

	@Override
	public ResultEntity queryMasterSubRelations(String req) {
		ResultEntity result = new ResultEntity();
		if (JSONUtils.mayBeJSON(req)) {
			RelLibsEntity relLibsEntity =  JsonUtils.fromJson(req, RelLibsEntity.class);
			if (relLibsEntity != null) {
				//{"rel_type":"1"}
				List<Map<String, Object>> list = libraryDao.queryMasterSubRelations(relLibsEntity);
				result.setValue(true, "", "", list);
				return result;
			}
		}
		result.setMessage("参数有误!");
		result.setResult(null);
		result.setState(false);
		return result;
	}
	
	@Override
	public ResultEntity selActualLibraryMaster(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			LibraryEntity libraryEntity=JsonUtils.fromJson(req, LibraryEntity.class);
			if(libraryEntity!=null){
				List<LibraryEntity> list=libraryDao.selActualLibraryMaster(libraryEntity);
				result.setResult(list);
				result.setState(true);
			}
		}
		return result;
	}
	
	/**
	 * 加载library数据到redis缓存
	 */
	public void loadLibraryToRedis(){
		List<LibraryEntity> entities = libraryDao.getAllLibraryList();
		for(LibraryEntity libraryEntity : entities){
			//将图书馆存储到redis缓存中
			JedisUtils.getInstance().hset(RedisConstant.LIBRARY_KEY,libraryEntity.getLib_id(),JsonUtils.toJson(libraryEntity));
			//将lib_idx与lib_id存储到缓存中
			JedisUtils.getInstance().set(RedisConstant.LIBRARY_KEYS+libraryEntity.getLibrary_idx()
			, libraryEntity.getLib_id());
		}
	}
	
	
}
