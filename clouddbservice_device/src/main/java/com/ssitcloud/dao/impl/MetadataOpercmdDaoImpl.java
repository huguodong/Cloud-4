package com.ssitcloud.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.MetadataOpercmdDao;
import com.ssitcloud.entity.MetadataOpercmdEntity;
import com.ssitcloud.entity.page.OperCmdMgmtPageEntity;
import com.ssitcloud.system.entity.MetaOpercmdRemoteEntity;

/**
 * 
 * @comment 实现操作指令元数据表的增删改查。表: metadata_opercmd
 * 
 * @author hwl
 * @data 2016年4月7日
 */
@Repository
public class MetadataOpercmdDaoImpl extends CommonDaoImpl implements
		MetadataOpercmdDao {

	@Override
	public int insert(MetadataOpercmdEntity metadataOpercmdEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("metaopercmd.insert",
				metadataOpercmdEntity);
	}

	@Override
	public int update(MetadataOpercmdEntity metadataOpercmdEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("metaopercmd.update",
				metadataOpercmdEntity);
	}

	@Override
	public int delete(MetadataOpercmdEntity metadataOpercmdEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("metaopercmd.delete",
				metadataOpercmdEntity);
	}

	@Override
	public List<MetadataOpercmdEntity> selectByid(
			MetadataOpercmdEntity metadataOpercmdEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("metaopercmd.selectByidx",
				metadataOpercmdEntity);
	}

	@Override
	public List<MetadataOpercmdEntity> selectAll() {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("metaopercmd.selectAll");
	}
	@Override
	public List<MetaOpercmdRemoteEntity> selectListByRemoteDevice(MetadataOpercmdEntity metadataOpercmdEntity){
		return this.sqlSessionTemplate.selectList("metaopercmd.selectListByRemoteDevice", metadataOpercmdEntity);
		
	}

	@Override
	public List<MetadataOpercmdEntity> selectCmdType() {
		return getSqlSession().selectList("metaopercmd.selectCmdType");
	}
	@Override
	public List<MetadataOpercmdEntity> selectCardCmdType() {
		return getSqlSession().selectList("metaopercmd.selectCardCmdType");
	}

	@Override
	public OperCmdMgmtPageEntity queryServgroupByparam(OperCmdMgmtPageEntity operCmdMgmtPage) {
		OperCmdMgmtPageEntity total=getSqlSession().selectOne("metaopercmd.queryServgroupByparam", operCmdMgmtPage);
		if(total!=null){
			operCmdMgmtPage.setTotal(total.getTotal());
		}
		operCmdMgmtPage.setDoAount(false);
		operCmdMgmtPage.setRows(getSqlSession().selectList("metaopercmd.queryServgroupByparam", operCmdMgmtPage));
		return operCmdMgmtPage;
	}

	@Override
	public List<Map<String, Object>> getBussinessTypeByGroupId(String groupIdx) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_idx", groupIdx);
		return this.sqlSessionTemplate.selectList("metaopercmd.getBussinessTypeByGroupId", map);
	}

	@Override
	public List<Map<String, Object>> getOpercmdListByOpercmd(String opercmd,String groupIdx) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("opercmd", opercmd);
		map.put("group_idx", groupIdx);
		return this.sqlSessionTemplate.selectList("metaopercmd.getOpercmdListByOpercmd", map);
	}

	@Override
	public List<MetadataOpercmdEntity> selectDevAllFunction() {
		return this.sqlSessionTemplate.selectList("metaopercmd.selectDevAllFunction");
	}

	@Override
	public List<MetadataOpercmdEntity> checkPermission(Map<String,Object> param) {
		return this.sqlSessionTemplate.selectList("metaopercmd.selectOpercmdByOperatorIdx", param);
	}
	@Override
	public List<MetadataOpercmdEntity> selectDeviceOperLogCmd() {
		return getSqlSession().selectList("metaopercmd.selectDeviceOperLogCmd");
	}

}
