package com.ssitcloud.dao.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.ReaderTypeDao;
import com.ssitcloud.entity.ReaderTypeEntity;

/**
 * 
 * @package com.ssitcloud.dao.impl
 * @comment
 * @data 2016年4月23日
 * @author hwl
 */
@Repository
public class ReaderTypeDaoImpl extends CommonDaoImpl implements ReaderTypeDao {

	@Override
	public int insert(ReaderTypeEntity readerTypeEntity) {
		
		return this.sqlSessionTemplate.insert("readertype.insert", readerTypeEntity);
	}

	@Override
	public int delete(ReaderTypeEntity readerTypeEntity) {
		
		return this.sqlSessionTemplate.delete("readertype.delete", readerTypeEntity);
	}
	@Override
	public int deleteByCon(ReaderTypeEntity readerTypeEntity){
		return this.sqlSessionTemplate.delete("readertype.deleteByCon", readerTypeEntity);
	}

	@Override
	public int update(ReaderTypeEntity readerTypeEntity) {
		
		return this.sqlSessionTemplate.update("readertype.update", readerTypeEntity);
	}

	@Override
	public List<ReaderTypeEntity> select(ReaderTypeEntity readerTypeEntity) {
		
		return this.sqlSessionTemplate.selectList("readertype.select", readerTypeEntity);
	}

	@Override
	public PageEntity selectByPage(Map<String, String> map) {
		ReaderTypeEntity rTypeEntity = JsonUtils.fromJson(map.get("json"), ReaderTypeEntity.class);
		PageEntity pageEntity = JsonUtils.fromJson(map.get("page"), PageEntity.class);
		int total = Integer.parseInt(this.sqlSessionTemplate.selectOne("readertype.count",rTypeEntity).toString());
		pageEntity.setTotal(total);
		RowBounds rowBounds = new RowBounds(pageEntity.getBeginIndex(),pageEntity.getPageSize());
		pageEntity.setRows(this.sqlSessionTemplate.selectList("readertype.select",rTypeEntity,rowBounds));
		return pageEntity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageEntity selectByFuzzy(Map<String, String> map) {
		String lib_idx = map.get("library_idx");
		List<Integer> list=new ArrayList<>();
		if(JSONUtils.mayBeJSON(lib_idx)){
			list = JsonUtils.fromJson(lib_idx, List.class);
		}
		if(list==null || list.size()==0){
			return JsonUtils.fromJson(map.get("page"), PageEntity.class);
		}
		Map<String , Object> infos =new HashMap<>();
		infos.put("library_idx", list);
		infos.put("type_id", map.get("type_id"));
		infos.put("type_distinction", map.get("type_distinction"));
		PageEntity pageEntity = JsonUtils.fromJson(map.get("page"), PageEntity.class);
		int total = Integer.parseInt(this.sqlSessionTemplate.selectOne("readertype.countByFuzzy",infos).toString());
		pageEntity.setTotal(total);
		RowBounds rowBounds = new RowBounds(pageEntity.getBeginIndex(),pageEntity.getPageSize());
		pageEntity.setRows(this.sqlSessionTemplate.selectList("readertype.selectByFuzzy",infos,rowBounds));
		return pageEntity;
	}

	@Override
	public List<ReaderTypeEntity> selectByLibIdx(
			ReaderTypeEntity readerTypeEntity) {
		return getSqlSession().selectList("readertype.selectByLibIdx", readerTypeEntity);
	}

	@Override
	public List<ReaderTypeEntity> selMaintenaceCard(Map<String, Object> map) {
		return this.sqlSessionTemplate.selectList("readertype.selMaintenaceCard",map);
	}
	
	@Override
	public List<ReaderTypeEntity> selectReaderTypeByTypeId(
			ReaderTypeEntity readerType) {
		return this.sqlSessionTemplate.selectList("readertype.selectReaderTypeByTypeId",readerType);
	}
	@Override
	public List<ReaderTypeEntity> selectCardByTypeId(
			ReaderTypeEntity readerType) {
		return this.sqlSessionTemplate.selectList("readertype.selectCardByTypeId",readerType);
	}

	@Override
	public List<ReaderTypeEntity> selectCardByTypeIdAndLibIdx(
			ReaderTypeEntity readerType) {
		return this.sqlSessionTemplate.selectList("readertype.selectCardByTypeIdAndLibIdx",readerType);
	}
	
	@Override
	public List<ReaderTypeEntity> selectReaderTypeByTypeIdAndLibIdx(ReaderTypeEntity readerType){
		return this.sqlSessionTemplate.selectList("readertype.selectReaderTypeByTypeIdAndLibIdx",readerType);
		
	}
	
}
