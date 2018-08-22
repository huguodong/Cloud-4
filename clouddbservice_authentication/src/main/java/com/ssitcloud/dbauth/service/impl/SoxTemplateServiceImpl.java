package com.ssitcloud.dbauth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.dao.OperatorDao;
import com.ssitcloud.dbauth.dao.SoxTemplateDao;
import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.SoxTemplateEntity;
import com.ssitcloud.dbauth.entity.page.SoxTempPageEntity;
import com.ssitcloud.dbauth.service.SoxTemplateService;
import com.ssitcloud.dbauth.utils.JsonUtils;
import com.ssitcloud.dbauth.utils.LogUtils;

/** 
 *  
 * <p>2016年3月24日 下午5:46:07 
 * @author hjc 
 *
 */
@Service
public class SoxTemplateServiceImpl implements SoxTemplateService{
	
	@Resource
	private SoxTemplateDao soxTemplateDao;
	
	@Resource
	private OperatorDao operatorDao;

	@Override
	public SoxTemplateEntity getSoxTemplateEntity(SoxTemplateEntity soxTemplateEntity) {
		return soxTemplateDao.getSoxTemplateEntity(soxTemplateEntity);
	}

	@Override
	public int addSoxTemplateEntity(SoxTemplateEntity soxTemplateEntity) {
		return soxTemplateDao.addSoxTemplateEntity(soxTemplateEntity);
	}

	@Override
	public int delSoxTemplateById(SoxTemplateEntity soxTemplateEntity) {
		return soxTemplateDao.delSoxTemplateById(soxTemplateEntity);
	}
	
	@Override
	public List<SoxTemplateEntity> queryAllSoxTemp(){
		return soxTemplateDao.queryAllSoxTemp();
	}

	@Override
	public SoxTempPageEntity getSoxTempListByParam(SoxTempPageEntity soxTempPageEntity) {
		return soxTemplateDao.getSoxTempListByParam(soxTempPageEntity);
	}

	@Override
	public ResultEntity addSoxTemp(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			SoxTemplateEntity soxTemplateEntity = JsonUtils.fromJson(req, SoxTemplateEntity.class);
			int ret = soxTemplateDao.addSoxTemplateEntity(soxTemplateEntity);
			if (ret==1) {
				resultEntity.setValue(true, "success");
				resultEntity.setRetMessage("密码模板IDX:"+soxTemplateEntity.getSox_tpl_id()+"|密码模板名:"+soxTemplateEntity.getSox_tpl_name());
			}else{
				resultEntity.setValue(false, "");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			throw new RuntimeException("更新模板信息出错！"+e.getMessage());
		}
		return resultEntity;
	}

	@Override
	public ResultEntity updateSoxTemp(String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			SoxTemplateEntity soxTemplateEntity = JsonUtils.fromJson(req, SoxTemplateEntity.class);
			if (soxTemplateEntity.getSox_tpl_id()==null) {
				resultEntity.setValue(false, "模板id为空");
				return resultEntity;
			}
			int ret = soxTemplateDao.updSoxTemplateEntityById(soxTemplateEntity);
			if (ret==1) {
				//把使用这个模板的用户的锁定次数修改一遍
				operatorDao.updateOperatorLockTimes(soxTemplateEntity);
				resultEntity.setValue(true, "success");
				resultEntity.setRetMessage("密码模板IDX"+soxTemplateEntity.getSox_tpl_id()+"｜密码模板名:"+soxTemplateEntity.getSox_tpl_name());//馆IDX｜密码模板IDX｜密码模板名
			}else{
				resultEntity.setValue(false, "optimistic");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			throw new RuntimeException("更新模板信息出错！"+e.getMessage());
		}
		return resultEntity;
	}

	@Override
	public ResultEntity delSoxTemp(String req) {
		
		ResultEntity resultEntity=new ResultEntity();
		StringBuilder idx=new StringBuilder("密码模板IDX:");
		if(JSONUtils.mayBeJSON(req)){
			List<SoxTemplateEntity> list = JsonUtils.fromJson(req, new TypeReference<List<SoxTemplateEntity>>() {});
			if(!CollectionUtils.isEmpty(list)){
				for(SoxTemplateEntity soxTemplateEntity:list){
					if(soxTemplateEntity!=null){
						Integer sox_id=soxTemplateEntity.getSox_tpl_id();
						
						if ("1".equals(sox_id) || "2".equals(sox_id)) {
							resultEntity.setValue(false, "模板不能删除");
							return resultEntity;
						}
						
						//查询模板是不是被使用了
						OperatorEntity operatorEntity = new OperatorEntity();
						operatorEntity.setSox_tpl_id(sox_id);
						int ret = operatorDao.selCountOperatorBySoxId(operatorEntity);
						if(ret > 0){
							resultEntity.setValue(false, "1");//模板正在被使用，无法删除
							return resultEntity;
						}
						
						int ret2 = soxTemplateDao.delSoxTemplateById(soxTemplateEntity);
						if (ret2 > 0) {
							idx.append(sox_id).append(",");
						}else{
							resultEntity.setState(false);
							resultEntity.setMessage("optimistic");
							return resultEntity;
						}
					}
				}
				resultEntity.setState(true);
				resultEntity.setRetMessage(idx.toString().substring(0,idx.toString().length()-1));
			}
			
		}
		return resultEntity;
	}

	@Override
	public ResultEntity delMultiSoxTemp(String req) {
		ResultEntity resultEntity = new ResultEntity();
		StringBuilder idxSB=new StringBuilder("密码模板IDX:");
		try {
			if (StringUtils.isBlank(req)) {
				resultEntity.setValue(false, "参数不能为空！");
				return resultEntity;
			}
			List<Map<String, Object>> list = JsonUtils.fromJson(req, new TypeReference<List<Map<String,Object>>>() {});
			Map<String, Object> resMap = new HashMap<>();
			String cannotDel = "";
			for (Map<String, Object> map : list) {
				String idx = map.get("idx")==null?"":map.get("idx").toString();
				String soxName = map.get("soxName")==null?"":map.get("soxName").toString();
				
				if (idx.equals("") || "1".equals(idx) || "2".equals(idx)) {
					cannotDel += soxName+",";
					continue;
				}
				OperatorEntity operatorEntity = new OperatorEntity();
				operatorEntity.setSox_tpl_id(Integer.valueOf(idx));
				int ret = operatorDao.selCountOperatorBySoxId(operatorEntity);
				if(ret > 0){
					cannotDel += soxName+",";
					continue;
				}
				SoxTemplateEntity soxTemplateEntity = new SoxTemplateEntity();
				soxTemplateEntity.setSox_tpl_id(Integer.valueOf(idx));
				int ret2 = soxTemplateDao.delSoxTemplateById(soxTemplateEntity);
				idxSB.append(soxTemplateEntity.getSox_tpl_id()).append(",");
			}
			if (cannotDel.length()>0) {
				cannotDel = cannotDel.substring(0,cannotDel.length()-1);
			}
			resMap.put("cannotDel", cannotDel);
  			resultEntity.setValue(true, "success","",resMap);
			resultEntity.setRetMessage(idxSB.toString().substring(0, idxSB.length()-1));//馆IDX｜密码模板IDX｜密码模板名
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			throw new RuntimeException("批量删除模板信息出错！"+e.getMessage());
		}
		return resultEntity;
	}

	//start author by lxp
	@Override
	public SoxTemplateEntity getSoxTemplateEntityById(Integer sox_tpl_id) {
		Map<String, Object> m = new HashMap<>(1);
		m.put("sox_tpl_id", sox_tpl_id);
		return soxTemplateDao.selectOneByMap(m);
	}
	//end author by lxp
	
	
	
}
