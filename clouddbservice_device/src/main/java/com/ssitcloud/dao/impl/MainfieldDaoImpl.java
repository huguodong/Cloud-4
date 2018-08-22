package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.MainfieldDao;
import com.ssitcloud.entity.MainfieldEntity;
import com.ssitcloud.entity.page.MainFieldPageEntity;

/**
 * 信息主字段表
 *
 * <p>2017年2月10日 下午2:16:39  
 * @author hjc 
 *
 */
@Repository
public class MainfieldDaoImpl extends CommonDaoImpl implements MainfieldDao {

	@Override
	public int addMainField(MainfieldEntity mainfieldEntity) {
		return this.sqlSessionTemplate.insert("mainfield.addMainField",mainfieldEntity);
	}

	@Override
	public int delMainField(MainfieldEntity mainfieldEntity) {
		return this.sqlSessionTemplate.delete("mainfield.delMainField",mainfieldEntity);
	}

	@Override
	public int updMainField(MainfieldEntity mainfieldEntity) {
		return this.sqlSessionTemplate.update("mainfield.updMainField",mainfieldEntity);
	}

	@Override
	public MainfieldEntity selMainfieldByIdx(MainfieldEntity mainfieldEntity) {
		return this.sqlSessionTemplate.selectOne("mainfield.selMainfieldByIdx",mainfieldEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public MainFieldPageEntity selectMainFieldByPage(
			MainFieldPageEntity mainFieldPageEntity) {
		if(null == mainFieldPageEntity) mainFieldPageEntity = new MainFieldPageEntity();
		MainFieldPageEntity total = getSqlSession().selectOne("mainfield.selectMainFieldByPage", mainFieldPageEntity);
		mainFieldPageEntity.setDoAount(false);
		mainFieldPageEntity.setTotal(total.getTotal());
		mainFieldPageEntity.setRows(getSqlSession().selectList("mainfield.selectMainFieldByPage", mainFieldPageEntity));
		return mainFieldPageEntity;
	}

	@Override
	public List<Map<String, Object>> listMainfieldCollection() {
		return getSqlSession().selectList("mainfield.listMainfieldCollection");
	}

	@Override
	public int countMainfieldByTableAndField(MainfieldEntity mainfieldEntity) {
		return getSqlSession().selectOne("mainfield.countMainfieldByTableAndField",mainfieldEntity);
	}

	@Override
	public List<MainfieldEntity> selectMainFieldList(MainfieldEntity mainfieldEntity) {
		return getSqlSession().selectList("mainfield.selectMainFieldList",mainfieldEntity);
	}
	
	
	
	

}
