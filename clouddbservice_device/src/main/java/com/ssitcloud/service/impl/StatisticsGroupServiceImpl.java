package com.ssitcloud.service.impl;

import java.util.List;
import java.util.TreeSet;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.dao.RelServiceGroupDao;
import com.ssitcloud.dao.RelStatisticsGroupDao;
import com.ssitcloud.dao.StatisticsGroupDao;
import com.ssitcloud.entity.RelOperatorServiceGroupEntity;
import com.ssitcloud.entity.RelServiceGroupEntity;
import com.ssitcloud.entity.RelStatisticsGroupEntity;
import com.ssitcloud.entity.ServiceGroupEntity;
import com.ssitcloud.entity.StatisticsGroupEntity;
import com.ssitcloud.entity.page.OperCmdMgmtPageEntity;
import com.ssitcloud.entity.page.OperGroupMgmtPageEntity;
import com.ssitcloud.entity.page.StatisticsGroupMgmtPageEntity;
import com.ssitcloud.service.StatisticsGroupService;

/**
 * 模板组
 *
 * <p>2017年2月10日 下午2:21:49  
 * @author hjc 
 *
 */
@Service
public class StatisticsGroupServiceImpl implements StatisticsGroupService {
	@Resource
	private StatisticsGroupDao statisticsGroupDao;
	@Resource
	private RelStatisticsGroupDao relStatisticsGroupDao;

	@Override
	public int insertStatisticsGroup(
			StatisticsGroupEntity statisticsGroupEntity) {
		return statisticsGroupDao.insertStatisticsGroup(statisticsGroupEntity);
	}

	@Override
	public int updateStatisticsGroup(
			StatisticsGroupEntity statisticsGroupEntity) {
		return statisticsGroupDao.updateStatisticsGroup(statisticsGroupEntity);
	}

	@Override
	public int deleteStatisticsGroup(
			StatisticsGroupEntity statisticsGroupEntity) {
		return statisticsGroupDao.deleteStatisticsGroup(statisticsGroupEntity);
	}

	@Override
	public StatisticsGroupEntity queryOneStatisticsGroup(
			StatisticsGroupEntity statisticsGroupEntity) {
		return statisticsGroupDao.queryOneStatisticsGroup(statisticsGroupEntity);
			
	}

	@Override
	public List<StatisticsGroupEntity> queryStatisticsGroups(
			StatisticsGroupEntity statisticsGroupEntity) {
		return statisticsGroupDao.queryStatisticsGroups(statisticsGroupEntity);
		
	}

	@Override
	public StatisticsGroupMgmtPageEntity selectStatisticsGroupByPage(StatisticsGroupMgmtPageEntity sGroupMgmtPageEntity) {
		return statisticsGroupDao.queryStatisticsGroupByPage(sGroupMgmtPageEntity);
	}

	@Override
	public ResultEntity addStatisticsGroup(String req) {
		ResultEntity result=new ResultEntity();
		JSONObject reqJson = null;
		if(JSONUtils.mayBeJSON(req)){
			reqJson = JSONObject.fromObject(req);
			String statistics_group_id=reqJson.getString("statistics_group_id");
			String statistics_group_name=reqJson.getString("statistics_group_name");
			String statistics_group_desc=reqJson.getString("statistics_group_desc");
			if(!StringUtils.hasText(statistics_group_id)){
				result.setMessage("组ID不能为空");
				return result;
			}
			if(!StringUtils.hasText(statistics_group_name)){
				result.setRetMessage("组名不能为空");
				return result;
			}
			StatisticsGroupEntity statisticsGroupEntity = new StatisticsGroupEntity();
			statisticsGroupEntity.setStatistics_group_id(statistics_group_id);
			statisticsGroupEntity.setStatistics_group_name(statistics_group_name);
			statisticsGroupEntity.setStatistics_group_desc(statistics_group_desc);
			int insertNum = 0;
			try {
				insertNum=statisticsGroupDao.insertStatisticsGroup(statisticsGroupEntity);//插入模板组
			} catch (org.springframework.dao.DuplicateKeyException e) {
				LogUtils.error(e);
				String msg=e.getRootCause().getMessage();
				AntPathMatcher matcher=new AntPathMatcher();
				if(matcher.match("*statistics_group_id*", msg)){
					throw new RuntimeException("组ID已经被使用，请更改后保存");
				}else if(matcher.match("*statistics_group_name*", msg)){
					throw new RuntimeException("组名称已经被使用，请更改后保存");
				}else{
					throw new RuntimeException(e);
				}				
			}
			if(insertNum<=0){
				throw new RuntimeException("新增模板组失败！！");
			}
			Integer statistics_group_idx=statisticsGroupEntity.getStatistics_group_idx();
			if(statistics_group_idx==null||statistics_group_idx==0){
				throw new RuntimeException("获取 statistics_group_idx失败！！");
			}
			String[] selectTIDs_arr = reqJson.getString("selectTIDs").split(",");
//			System.out.println(selectTIDs_arr.length);
			String[] statisticsTIDs_arr = reqJson.getString("statisticsTIDs").split(",");
//			System.out.println(statisticsTIDs_arr.length);
			if(selectTIDs_arr!=null){
				for(String statistics_idx:selectTIDs_arr){//模板组和查询模板
					if(org.apache.commons.lang.StringUtils.isNotBlank(statistics_idx)){
						RelStatisticsGroupEntity relStatisticsGroupEntity = new RelStatisticsGroupEntity();
						relStatisticsGroupEntity.setStatistics_group_idx(statistics_group_idx);
						relStatisticsGroupEntity.setStatistics_idx(Integer.parseInt(statistics_idx));
						insertNum=relStatisticsGroupDao.insertRelStatisticsGroup(relStatisticsGroupEntity);
						if(insertNum<=0){
							throw new RuntimeException("新增模板组与查询模板关系表失败！！");
						}
					}
				}
			}
			if(statisticsTIDs_arr!=null){
				for(String statistics_idx:statisticsTIDs_arr){//模板组和统计模板
					if(org.apache.commons.lang.StringUtils.isNotBlank(statistics_idx)){
						RelStatisticsGroupEntity relStatisticsGroupEntity = new RelStatisticsGroupEntity();
						relStatisticsGroupEntity.setStatistics_group_idx(statistics_group_idx);
						relStatisticsGroupEntity.setStatistics_idx(Integer.parseInt(statistics_idx));
						insertNum=relStatisticsGroupDao.insertRelStatisticsGroup(relStatisticsGroupEntity);
						if(insertNum<=0){
							throw new RuntimeException("新增模板组与统计模板关系表失败！！");
						}
					}
				}
			}
			result.setState(true);
			result.setRetMessage("|模板组idx"+statistics_group_id+"|模板组名称："+statistics_group_name+"||");
		}
		return result;
	}

	@Override
	public ResultEntity updStatisticsGroup(String req) {
		ResultEntity result=new ResultEntity();
		int updateNum=0;
		JSONObject reqJson = null;
		Integer statistics_group_idx = 0;
		if(JSONUtils.mayBeJSON(req)){
			reqJson = JSONObject.fromObject(req);
			statistics_group_idx=reqJson.getInt("statistics_group_idx");
			String statistics_group_id=reqJson.getString("statistics_group_id");
			String statistics_group_name=reqJson.getString("statistics_group_name");
			String statistics_group_desc=reqJson.getString("statistics_group_desc");
			if(!StringUtils.hasText(statistics_group_id)){
				result.setMessage("组ID不能为空");
				return result;
			}
			if(!StringUtils.hasText(statistics_group_name)){
				result.setRetMessage("组名不能为空");
				return result;
			}
			StatisticsGroupEntity statisticsGroupEntity = new StatisticsGroupEntity();
			statisticsGroupEntity.setStatistics_group_idx(statistics_group_idx);
			statisticsGroupEntity.setStatistics_group_id(statistics_group_id);
			statisticsGroupEntity.setStatistics_group_name(statistics_group_name);
			statisticsGroupEntity.setStatistics_group_desc(statistics_group_desc);
			try {
				updateNum=statisticsGroupDao.updateStatisticsGroup(statisticsGroupEntity);
			} catch (org.springframework.dao.DuplicateKeyException e) {
				String msg=e.getRootCause().getMessage();
				System.out.println(msg);
				AntPathMatcher matcher=new AntPathMatcher();
				if(matcher.match("*statistics_group_id*", msg)){
					throw new RuntimeException("组ID已存在，请修改");
				}else if(matcher.match("*statistics_group_name*", msg)){
					throw new RuntimeException("组名称已存在，请修改");
				}else{
					throw new RuntimeException(e);
				}				
			}catch (org.springframework.dao.DataIntegrityViolationException e) {
				String msg=e.getRootCause().getMessage();
				AntPathMatcher matcher=new AntPathMatcher();
				if(matcher.match("*Data too long*", msg)&&matcher.match("*setStatistics_group_desc*", msg)){
					throw new RuntimeException("备注字数超过100字，请修改");
				}else{
					throw new RuntimeException(e);
				}		
			}
			if(updateNum<=0){
				result.setState(false);
				result.setRetMessage("更新模板组失败");
				return result;
			}
			TreeSet<Integer> idxSet=new TreeSet<>();
			TreeSet<Integer> idxSetLocal=new TreeSet<>();
			String selectTIDs = reqJson.getString("selectTIDs");
			String statisticsTIDs = reqJson.getString("statisticsTIDs");
			if(StringUtils.hasText(selectTIDs)){
				for(String selidxString :selectTIDs.split(",")){
					idxSet.add(Integer.parseInt(selidxString));
				}
			}
			if(StringUtils.hasText(statisticsTIDs)){
				for(String staidxString :statisticsTIDs.split(",")){
					idxSet.add(Integer.parseInt(staidxString));
				}
			}
			RelStatisticsGroupEntity relStatisticsGroupEntity = new RelStatisticsGroupEntity();
			relStatisticsGroupEntity.setStatistics_group_idx(statistics_group_idx);
			List<RelStatisticsGroupEntity> relStatisticsGroupEntitys = relStatisticsGroupDao.queryRelStatisticsGroups(relStatisticsGroupEntity);
			if(relStatisticsGroupEntitys!=null){
				for(RelStatisticsGroupEntity relStatisticsGroup:relStatisticsGroupEntitys){
					Integer statisticsGroupidx = relStatisticsGroup.getStatistics_group_idx();
					if(statisticsGroupidx!=null){
						idxSetLocal.add(statisticsGroupidx);
					}
				}
			}
			if(idxSet.equals(idxSetLocal)){
				//相同的话则不须要删除 和添加操作
			}else{
				//如果没有变化，则不管，如果发生变化，则把原来的全部删除，新增加
				int delNum=relStatisticsGroupDao.deleteRelStatisticsGroup(relStatisticsGroupEntity);
				if(delNum!=relStatisticsGroupEntitys.size()){
					//删除的数量不对等，则
					throw new RuntimeException("更新操作时删除数据失败！！");
				}else{
					while(!idxSet.isEmpty()){
						RelStatisticsGroupEntity relStatisticsGroup = new RelStatisticsGroupEntity();
						Integer gIdx=idxSet.pollFirst();
						relStatisticsGroup.setStatistics_group_idx(statistics_group_idx);
						relStatisticsGroup.setStatistics_idx(gIdx);
						int inserNum=relStatisticsGroupDao.insertRelStatisticsGroup(relStatisticsGroup);
						if(inserNum<=0){
							throw new RuntimeException("更新操作新增数据失败！！");
						}
					}
				}
			}
			result.setState(true);
			result.setRetMessage("IDX:"+statistics_group_id+"|组名:"+statistics_group_name+"|||");
		}
		return result;
	
	}

	@Override
	public ResultEntity delStatisticsGroup(String req) {
		ResultEntity result=new ResultEntity();
		int deleteNum=0;
		StringBuilder idx=new StringBuilder("IDX:");
		JSONObject reqJson = null;
		if(JSONUtils.mayBeJSON(req)){
			reqJson = JSONObject.fromObject(req);
			String[] gidx_arr = reqJson.getString("gidx").split(",");
			for(String gidx:gidx_arr){
				Integer group_idx=Integer.parseInt(gidx);
				if(group_idx!=null){
					StatisticsGroupEntity statisticsGroupEntity = new StatisticsGroupEntity();
					statisticsGroupEntity.setStatistics_group_idx(group_idx);
					deleteNum = statisticsGroupDao.deleteStatisticsGroup(statisticsGroupEntity);
					if(deleteNum>0){
						//执行删除
						RelStatisticsGroupEntity relStatisticsGroup = new RelStatisticsGroupEntity();
						relStatisticsGroup.setStatistics_group_idx(group_idx);
						deleteNum=relStatisticsGroupDao.deleteRelStatisticsGroup(relStatisticsGroup);
//						if(deleteNum>0){
							idx.append(group_idx+",");
//						}else{
//							throw new RuntimeException("删除模板组关联的模板数据失败！");
//						}
					}
				}
					
			}
			result.setState(true);
			result.setRetMessage(idx.toString().substring(0,idx.toString().length()-1));
		}
		return result;
	
	}

	@Override
	public StatisticsGroupMgmtPageEntity querysGroupByPage(
			StatisticsGroupMgmtPageEntity sGroupMgmtPageEntity) {
		return statisticsGroupDao.querysGroupByPage(sGroupMgmtPageEntity);
	}
}
