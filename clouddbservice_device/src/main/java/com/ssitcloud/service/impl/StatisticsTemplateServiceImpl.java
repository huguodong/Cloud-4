package com.ssitcloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.StatisticsTemplateDao;
import com.ssitcloud.entity.StatisticsTemplateEntity;
import com.ssitcloud.entity.page.StatisticsTemplatePageEntity;
import com.ssitcloud.service.StatisticsTemplateService;

/**
 * 统计查询模板配置
 *
 * <p>2017年2月10日 下午2:22:45  
 * @author hjc 
 *
 */
@Service
public class StatisticsTemplateServiceImpl implements StatisticsTemplateService {
	@Resource
	private StatisticsTemplateDao statisticsTemplateDao;
	@Resource
	private CommonDao commonDao;

	@Override
	public int insertStatisticsTemplate(
			StatisticsTemplateEntity statisticsTemplateEntity) {
		return statisticsTemplateDao.insertStatisticsTemplate(statisticsTemplateEntity);
	}

	@Override
	public int updateStatisticsTemplate(
			StatisticsTemplateEntity statisticsTemplateEntity) {
		return statisticsTemplateDao.updateStatisticsTemplate(statisticsTemplateEntity);
	}

	@Override
	public int deleteStatisticsTemplate(
			StatisticsTemplateEntity statisticsTemplateEntity) {
		return statisticsTemplateDao.deleteStatisticsTemplate(statisticsTemplateEntity);
	}

	@Override
	public StatisticsTemplateEntity queryOneStatisticsTemplate(
			StatisticsTemplateEntity statisticsTemplateEntity) {
		return statisticsTemplateDao.queryOneStatisticsTemplate(statisticsTemplateEntity);
			
	}

	@Override
	public List<StatisticsTemplateEntity> queryStatisticsTemplates(
			StatisticsTemplateEntity statisticsTemplateEntity) {
		return statisticsTemplateDao.queryStatisticsTemplates(statisticsTemplateEntity);
		
	}

	@Override
	public ResultEntity selectStatisticsTemplatePage(String req) {
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.hasText(req)){
				Map<String,Object> map = JsonUtils.fromJson(req, Map.class);
				StatisticsTemplatePageEntity statisticsTemplatePageEntity = new StatisticsTemplatePageEntity(); 
				
				Integer page = map.get("page")==null?1:Integer.valueOf(map.get("page").toString());				
				Integer pageSize = map.get("pageSize")==null?10:Integer.valueOf(map.get("pageSize").toString());
				if(map.get("statistics_tpl_type")!=null && !map.get("statistics_tpl_type").toString().isEmpty()){
					statisticsTemplatePageEntity.setStatistics_tpl_type(Integer.parseInt(map.get("statistics_tpl_type").toString()));
				}
				if(map.get("statistics_tpl_desc")!=null && !map.get("statistics_tpl_desc").toString().isEmpty()){
					statisticsTemplatePageEntity.setStatistics_tpl_desc(map.get("statistics_tpl_desc").toString());
				}
				statisticsTemplatePageEntity.setPage(page);
				statisticsTemplatePageEntity.setPageSize(pageSize);
				Boolean daFlag = map.get("daFlag")==null?true:false;
				if(!daFlag){//不分页
					statisticsTemplatePageEntity.setWhetherPaging(false);
				}
				statisticsTemplatePageEntity = statisticsTemplateDao.selectStatisticsTemplatePage(statisticsTemplatePageEntity);
				result.setValue(true, "success","",statisticsTemplatePageEntity);
			}else{
				result.setValue(false, "参数不能为空", "", "");
			}
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> selectBySql(String sql) {
		List<Map<String,Object>> recordlist = commonDao.selectBySql(sql);
		return recordlist;
	}

    @Override
    public List<StatisticsTemplateEntity> selectTemplateMenuByOperidx(int id) {
        return statisticsTemplateDao.selectTemplateMenuByOperidx(id);
    }

}
