package com.ssitcloud.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.MetadataMenuDao;
import com.ssitcloud.entity.MetadataMenuEntity;

@Repository
public class MetadataMenuDaoImpl extends CommonDaoImpl  implements MetadataMenuDao {

	@Override
	public List<MetadataMenuEntity> queryAll(String flags) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<>();
		if(StringUtils.isNotBlank(flags)){
			String [] str = flags.split(",");
			for (String string : str) {
				if (StringUtils.isNotBlank(string)) {
					list.add(string);
				}
			}
			map.put("flags", list);
		}
		return this.sqlSessionTemplate.selectList("metamenu.queryall",map);
	}

	@Override
	public List<MetadataMenuEntity> SelMenuByOperIdx(String operator_idx,String flags) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<>();
		if(StringUtils.isNotBlank(flags)){
			String [] str = flags.split(",");
			for (String string : str) {
				if (StringUtils.isNotBlank(string)) {
					list.add(string);
				}
			}
			map.put("flags", list);
		}
		map.put("operator_idx",operator_idx);
		return this.sqlSessionTemplate.selectList("metamenu.SelMenuByOperIdx",map);
	}

	@Override
	public List<MetadataMenuEntity> selectByCode(
			MetadataMenuEntity metadataMenuEntity) {
		return this.sqlSessionTemplate.selectList("metamenu.selectByCode",metadataMenuEntity);
	}

	
	
}
