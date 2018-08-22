package com.ssitcloud.shelfmgmt.dao.impl;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.shelfmgmt.dao.BibliosDao;
import com.ssitcloud.shelfmgmt.entity.BibliosBean;

@Repository
public class BibliosDaoImpl extends CommonDaoImpl implements BibliosDao {

	@Override
	public BibliosBean queryMarcDataByItemCtrlNum(BibliosBean biblios) {
		return this.sqlSessionTemplate.selectOne("biblios.selectByByItemCtrlNum",biblios);
	}

	@Override
	public int addBiblios(BibliosBean biblios) {
		return this.sqlSessionTemplate.insert("biblios.add",biblios);
	}

}
