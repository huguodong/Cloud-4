package com.ssitcloud.dbauth.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.dao.LibraryDao;
import com.ssitcloud.dbauth.dao.LibraryTemplateDao;
import com.ssitcloud.dbauth.dao.OperationLogDao;
import com.ssitcloud.dbauth.entity.LibraryEntity;
import com.ssitcloud.dbauth.entity.LibraryServiceTemplateEntity;
import com.ssitcloud.dbauth.entity.OperationLogEntity;
import com.ssitcloud.dbauth.entity.page.LibraryServiceTemplatePageEntity;
import com.ssitcloud.dbauth.service.LibraryTemplateService;
import com.ssitcloud.dbauth.utils.DateUtils;
import com.ssitcloud.dbauth.utils.JsonUtils;
import com.ssitcloud.dbauth.utils.LogUtils;
/**
 * 图书馆模板处理
 * <p>2016年4月5日 上午11:31:35
 * @author hjc
 *
 */
@Service
public class LibraryTemplateServiceImpl implements LibraryTemplateService{
	@Resource
	private LibraryTemplateDao libraryTemplateDao;
	
	@Resource
	private LibraryDao libraryDao;

	@Resource
	private OperationLogDao operationLogDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public int addLibraryTemplate(String json) {
		int ret = 0;
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map.class );
			
			LibraryServiceTemplateEntity lTemplateEntity = JsonUtils.fromJson(JsonUtils.toJson(operMap.get("libconfig")), LibraryServiceTemplateEntity.class); 
			ret = libraryTemplateDao.addLibraryTemplate(lTemplateEntity);
			if(ret <= 0 ){throw new RuntimeException("增加图书馆配置信息失败");} 
			
			//操作成功之后，保存用户的操作日志
			OperationLogEntity logEntity = new OperationLogEntity();
			logEntity.setOperator_idx(Integer.valueOf(operMap.get("operator_idx").toString()) );
			logEntity.setClient_ip(operMap.get("client_ip").toString() == null ?"null":operMap.get("client_ip").toString());
			logEntity.setClient_port(operMap.get("client_port").toString() == null ?"null":operMap.get("client_port").toString());
			logEntity.setOperation_cmd("0102010201");
			logEntity.setOperation_result("true");
			//馆IDX｜服务模板IDX｜服务模板名  2017年3月6号修改格式 馆IDX|馆模板IDX|服务模板名||  
			logEntity.setOperation("服务模板IDX:"+lTemplateEntity.getLib_service_tpl_id()+"｜服务模板名:"+lTemplateEntity.getLib_service_tpl_desc()+"||");                           
			operationLogDao.addOperationLog(logEntity);
			
		} catch (Exception e) {
			String msg=e.getCause().getMessage();
			if(StringUtils.contains(msg, "Duplicate entry") 
					&&
					StringUtils.contains(msg, "tpl_desc") ){
				throw new RuntimeException("模板名重复，请修改");
				
			}
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int delLibraryTemplateById(String json) {
		int ret = 0;
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map.class );
			StringBuilder idsb=new StringBuilder("服务模板IDX");
			List<LibraryServiceTemplateEntity> list = JsonUtils.fromJson(JsonUtils.toJson(operMap.get("idx")), new TypeReference<List<LibraryServiceTemplateEntity>>() {}); 
			String cannotDel = "";
			for (LibraryServiceTemplateEntity lTemplateEntity : list) {
				//先查询是否有图书馆使用这个模板
				int cou1 = libraryDao.countLibraryByTempId(lTemplateEntity);
				if(cou1>0){
					cannotDel += lTemplateEntity.getLib_service_tpl_id()+",";
					continue;
				}
				ret = libraryTemplateDao.delLibraryTemplateById(lTemplateEntity);
				if(ret <= 0 ){throw new RuntimeException("删除图书馆配置信息失败");} 
				idsb.append(lTemplateEntity.getLib_service_tpl_id()).append(",");
			}
			if (!cannotDel.equals("")) {
				cannotDel = cannotDel.substring(0,cannotDel.length()-1);
			}
			//操作成功之后，保存用户的操作日志
			OperationLogEntity logEntity = new OperationLogEntity();
			logEntity.setOperator_idx(Integer.valueOf(operMap.get("operator_idx").toString()) );
			logEntity.setClient_ip(operMap.get("client_ip").toString() == null ?"null":operMap.get("client_ip").toString());
			logEntity.setClient_port(operMap.get("client_port").toString() == null ?"null":operMap.get("client_port").toString());
			logEntity.setOperation_cmd("0102010202");
			logEntity.setOperation_result("true");
			logEntity.setOperation(idsb.toString());//服务模板IDX
			operationLogDao.addOperationLog(logEntity);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return ret;
	}
	
	

	@Transactional
	@Override
	public ResultEntity delLibraryTemplate(String req) {
		
		
		ResultEntity result=new ResultEntity();
		int delNum=0;
		StringBuilder sb=new StringBuilder();
		StringBuilder idx=new StringBuilder("服务模板IDX:");
		if(JSONUtils.mayBeJSON(req)){
			List<LibraryServiceTemplateEntity> libraryConfigTemplates= JsonUtils.fromJson(req, new TypeReference<List<LibraryServiceTemplateEntity>>() {});
			if(CollectionUtils.isNotEmpty(libraryConfigTemplates)){
				for(LibraryServiceTemplateEntity templateEntity:libraryConfigTemplates){
					Integer lib_service_tpl_id=templateEntity.getLib_service_tpl_id();
					
					int cou1 = libraryDao.countLibraryByTempId(templateEntity);
					if(cou1>=1){
						//表示存在外键
						sb.append(lib_service_tpl_id).append(",");
						continue;//跳过删除环节
					}
					delNum = libraryTemplateDao.delLibraryTemplateById(templateEntity);
					if(delNum>0){
						idx.append(lib_service_tpl_id).append(",");
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

	@Override
	public LibraryServiceTemplateEntity selLibraryServiceTemplateEntity(LibraryServiceTemplateEntity templateEntity) {
		return libraryTemplateDao.selLibraryServiceTemplateEntity(templateEntity);
	}

	@Override
	public List<LibraryServiceTemplateEntity> selLibraryTempList(LibraryServiceTemplateEntity templateEntity) {
		return libraryTemplateDao.selLibraryTempList(templateEntity);
	}

	@Transactional
	@Override
	public ResultEntity updLibraryTemplate(String json) {
		int ret = 0;
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(json)){
			LibraryServiceTemplateEntity lTemplateEntity=JsonUtils.fromJson(json, LibraryServiceTemplateEntity.class);
			ret = libraryTemplateDao.updLibraryTemplate(lTemplateEntity);
			if(ret <= 0 ){
				result.setState(false);
				result.setRetMessage("optimistic");
				return result;
			} 
			//需要修改对应的图书馆的服务结束时间。
			Integer serviceCycle=lTemplateEntity.getService_cycle();//小时
			if(serviceCycle!=null){
				//LInfoEntity
				List<LibraryEntity> Librarys=libraryDao.selectLibraryByTempId(lTemplateEntity.getLib_service_tpl_id());
				if(Librarys!=null && Librarys.size()>0){
					for(LibraryEntity lib:Librarys){
						Date d=DateUtils.addMonths(lib.getService_start_date(), serviceCycle);
						lib.setService_expire_date(new Timestamp(d.getTime()));
						int upd=libraryDao.updateLibrary(lib);
						LogUtils.info("update service_expire_date:"+d);
						if(upd<=0){
							throw new RuntimeException("更新馆服务结束时间失败,ID:"+lib.getLib_id());
						}
					}
				}
			}
			result.setState(true);
			//馆IDX｜服务模板IDX｜服务模板名 2017年3月6号修改格式 馆IDX|馆模板IDX|服务模板名||
			result.setRetMessage("服务模板IDX:"+lTemplateEntity.getLib_service_tpl_id()+"｜服务模板名:"+lTemplateEntity.getLib_service_tpl_desc()+"||");
		}
		return result;
	}

	@Override
	public LibraryServiceTemplatePageEntity selLibraryTemp(String libTempInfo) {
		LibraryServiceTemplatePageEntity libtemPageEntity = new LibraryServiceTemplatePageEntity();
		libtemPageEntity = JsonUtils.fromJson(libTempInfo, LibraryServiceTemplatePageEntity.class);
		return libraryTemplateDao.selLibTempBypage(libtemPageEntity);
	}

	@Override
	public List<LibraryServiceTemplateEntity> selAllLibraryTemp() {
		// TODO Auto-generated method stub
		return libraryTemplateDao.selAllTemp();
	}

	@Override
	public LibraryServiceTemplateEntity selTempByLibraryIdx(String libraryIdx) {
		return libraryTemplateDao.selTempByLibraryIdx(libraryIdx);
	}

	@Override
	public LibraryServiceTemplateEntity selLibraryServiceTemplateByIdx(
			String req) {
		if(JSONUtils.mayBeJSON(req)){
			LibraryServiceTemplateEntity libraryServiceTemplate=JsonUtils.fromJson(req, LibraryServiceTemplateEntity.class);
			libraryServiceTemplate=libraryTemplateDao.selLibraryServiceTemplateEntity(libraryServiceTemplate);
			return libraryServiceTemplate;
		}
		return null;
	}
	
}
