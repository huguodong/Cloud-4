package com.ssitcloud.dbauth.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbauth.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbauth.dao.MetadataInfotypeDao;
import com.ssitcloud.dbauth.entity.MetadataInfotypeEntity;

/** 
 *
 * <p>2016年3月30日 下午1:48:54  
 * @author hjc 
 *
 */
@Repository
public class MetadataInfotypeDaoImpl extends CommonDaoImpl implements
		MetadataInfotypeDao {

	@Override
	public int addMetadataInfotype(
			MetadataInfotypeEntity metadataInfotypeEntity) {
		return this.sqlSessionTemplate.insert("infotype.addMetadataInfotype", metadataInfotypeEntity);
	}

	@Override
	public List<MetadataInfotypeEntity> selMetadataInfotype() {
		return this.sqlSessionTemplate.selectList("infotype.select");
	}

	@Override
	public int delMetadataInfotypeByIdx(MetadataInfotypeEntity metadataInfotypeEntity) {
		return this.sqlSessionTemplate.delete("infotype.delMetadataInfotypeByIdx",metadataInfotypeEntity);
	}

	@Override
	public List<MetadataInfotypeEntity> selLibdataInfotype() {
		return this.sqlSessionTemplate.selectList("infotype.selectLibdata");
	}

	@Override
	public List<MetadataInfotypeEntity> selOperatorInfotypeList() {
		return this.sqlSessionTemplate.selectList("infotype.selOperatorInfotypeList");
	}

	@Override
	public MetadataInfotypeEntity selectInfoByIdx(
			MetadataInfotypeEntity metadataInfotype) {
		return sqlSessionTemplate.selectOne("infotype.selectInfoByIdx", metadataInfotype);
	}

	@Override
	public int update(MetadataInfotypeEntity metadataInfotype) {
		return sqlSessionTemplate.update("infotype.update", metadataInfotype);
	}
	
	
	
	
	
	

	
}
