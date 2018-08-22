package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.MetadataDeviceTypeDao;
import com.ssitcloud.entity.MetadataDeviceTypeEntity;

/**
 * 
 * @comment 实现设备类型描述元数据表的增删改查。表名：metadata_devicetype
 * 
 * @author hwl
 * @data 2016年4月8日
 */
@Repository
public class MetadataDeviceTypeDaoImpl extends CommonDaoImpl implements
		MetadataDeviceTypeDao {

	@Override
	public int insert(MetadataDeviceTypeEntity metadataDeviceTypeEntity) {

		return this.sqlSessionTemplate.insert("metadevicetype.insert",
				metadataDeviceTypeEntity);
	}

	@Override
	public int update(MetadataDeviceTypeEntity metadataDeviceTypeEntity) {

		return this.sqlSessionTemplate.update("metadevicetype.update",
				metadataDeviceTypeEntity);
	}

	@Override
	public int delete(MetadataDeviceTypeEntity metadataDeviceTypeEntity) {

		return this.sqlSessionTemplate.delete("metadevicetype.delete",
				metadataDeviceTypeEntity);
	}

	@Override
	public List<MetadataDeviceTypeEntity> selectByid(
			MetadataDeviceTypeEntity metadataDeviceTypeEntity) {

		return this.sqlSessionTemplate.selectList("metadevicetype.selectByidx",metadataDeviceTypeEntity);
	}
	
	

	@Override
	public MetadataDeviceTypeEntity selOneByIdx(MetadataDeviceTypeEntity metadataDeviceTypeEntity) {
		return this.sqlSessionTemplate.selectOne("metadevicetype.selOneByIdx",metadataDeviceTypeEntity);
	}

	@Override
	public List<MetadataDeviceTypeEntity> selAllMetadataDeviceType() {
		return this.sqlSessionTemplate.selectList("metadevicetype.selAllMetadataDeviceType");
	}


	@Override
	public PageEntity selectPage(Map<String, String> map) {
		MetadataDeviceTypeEntity m = JsonUtils.fromJson(map.get("json"), MetadataDeviceTypeEntity.class);
		PageEntity p = JsonUtils.fromJson(map.get("page"), PageEntity.class);
		int total=Integer.parseInt(this.sqlSessionTemplate.selectOne("metadevicetype.Count", m).toString());
		p.setTotal(total);
		RowBounds rowBounds = new RowBounds(p.getBeginIndex(), p.getPageSize());
		//p.setRows(this.sqlSessionTemplate.selectList("metadevicetype.selectByidx", m, rowBounds));
		p.setRows(this.sqlSessionTemplate.selectList("metadevicetype.selectListLike", m, rowBounds));
		return p;
	}

	/*@Override
	public List<MetadataDeviceTypeEntity> selectType() {
		return this.sqlSessionTemplate.selectList("metadevicetype.selectType");
	}*/
	@Override
	public List<MetadataDeviceTypeEntity> selAllDeviceTypeGroupByType(){
		return getSqlSession().selectList("metadevicetype.selAllDeviceTypeGroupByType");
	}

	@Override
	public int updateByMap(Map<String,Object> param) {
		return getSqlSession().update("metadevicetype.updateByMap",param);
	}

	@Override
	public int insertWithIdx(Map<String, Object> m) {
		return getSqlSession().insert("metadevicetype.insertWithIdx", m);
	}

	@Override
	public MetadataDeviceTypeEntity queryDeviceTypeByName(MetadataDeviceTypeEntity entity) {
		return this.sqlSessionTemplate.selectOne("devicedisplay.queryDeviceTypeByName",entity);
	}

	@Override
	public List<Integer> selectMetaDevTypeIdxByType(MetadataDeviceTypeEntity entity) {
		return this.sqlSessionTemplate.selectList("metadevicetype.selectMetaDevTypeIdxByType", entity);
	}

	@Override
	public List<MetadataDeviceTypeEntity> selectAllDeviceType() {
		return this.sqlSessionTemplate.selectList("metadevicetype.selectAllDeviceType");
	}
}
