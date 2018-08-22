package com.ssitcloud.dbauth.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbauth.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbauth.dao.LibraryDao;
import com.ssitcloud.dbauth.entity.LibraryEntity;
import com.ssitcloud.dbauth.entity.LibraryServiceTemplateEntity;
import com.ssitcloud.dbauth.entity.RelLibsEntity;
import com.ssitcloud.dbauth.entity.page.LibraryPageEntity;
import com.ssitcloud.dbauth.entity.page.LibraryPageTempInfoEntity;
import com.ssitcloud.dbauth.entity.page.LibraryUnionEntity;



/**
 * <p>2016年4月5日 上午11:24:16
 * @author hjc
 *
 */
@Repository
public class LibraryDaoImpl extends CommonDaoImpl implements LibraryDao {

	@Override
	public int addLibrary(LibraryEntity libraryEntity) {
		return this.sqlSessionTemplate.insert("library.addLibrary",libraryEntity);
	}

	@Override
	public int delLibraryByIdx(LibraryEntity libraryEntity) {
		return this.sqlSessionTemplate.delete("library.delLibraryByIdx",libraryEntity);
	}

	@Override
	public LibraryEntity selLibraryByIdxOrId(LibraryEntity libraryEntity) {
		return this.sqlSessionTemplate.selectOne("library.selLibraryByIdxOrId",libraryEntity);
	}

	@Override
	public List<LibraryEntity> selLibraryByIdxsOrIds(Map<String, Object> param) {
		return this.sqlSessionTemplate.selectList("library.selLibraryByIdxsOrIds",param);
	}

	@Override
	public List<LibraryEntity> selLibraryFuzzyByID(LibraryEntity libraryEntity) {
		return this.sqlSessionTemplate.selectList("library.selLibraryByFuzzy",libraryEntity);
	}

	@Override
	public LibraryPageEntity selLibraryinfo(LibraryPageEntity libraryPageEntity) {
		LibraryPageEntity total = this.sqlSessionTemplate.selectOne("library.selLibrarybyPage", libraryPageEntity);
		libraryPageEntity.setTotal(total.getTotal());
		libraryPageEntity.setDoAount(false);
		libraryPageEntity.setRows(this.sqlSessionTemplate.selectList("library.selLibrarybyPage", libraryPageEntity));
		return libraryPageEntity;
	}

	@Override
	public List<LibraryEntity> selMasterlib() {
		return this.sqlSessionTemplate.selectList("library.selMasterlib");
	}

	@Override
	public LibraryPageTempInfoEntity selLibInfoByParam(
			LibraryPageTempInfoEntity lInfoEntity) {
		LibraryPageTempInfoEntity totalEntity = this.sqlSessionTemplate.selectOne("library.selLibrarybyParam", lInfoEntity);
		lInfoEntity.setTotal(totalEntity.getTotal());
		lInfoEntity.setDoAount(false);
		lInfoEntity.setRows(this.sqlSessionTemplate.selectList("library.selLibrarybyParam", lInfoEntity));
		return lInfoEntity;
	}

	@Override
	public int updateLibrary(LibraryEntity libraryEntity) {
		return this.sqlSessionTemplate.update("library.updateLibrary", libraryEntity);
	}

	@Override
	public List<LibraryEntity> querySlaveLibraryByMasterIdx(Integer library_idx) {
		return  this.sqlSessionTemplate.selectList("library.querySlaveLibraryByMasterIdx", library_idx);
	}
	
	@Override
	public LibraryEntity queryMasterLibraryBySlaveIdx(Integer library_idx) {
		return  this.sqlSessionTemplate.selectOne("library.queryMasterLibraryBySlaveIdx", library_idx);
	}

	@Override
	public List<LibraryEntity> queryLibWhereisNotSlaveLib(LibraryEntity libraryEntity) {
		return this.sqlSessionTemplate.selectList("library.queryLibWhereisNotSlaveLib",libraryEntity);
	}

	@Override
	public Map<Integer,String> getLibIdAndLibIdx() {
		return this.sqlSessionTemplate.selectMap("library.getLibIdAndLibIdx", "library_idx");
	}

	@Override
	public int countLibraryByTempId(LibraryServiceTemplateEntity templateEntity) {
		return this.sqlSessionTemplate.selectOne("library.countLibraryByTempId",templateEntity);
	}

	@Override
	public List<String> selLibraryIDByFuzzy(LibraryEntity libraryEntity) {
		return this.sqlSessionTemplate.selectList("library.selLibraryIDByFuzzy",libraryEntity);
	}
	@Override
	public List<LibraryEntity> selectLibraryByTempId(Integer lib_service_tpl_id){
		return this.sqlSessionTemplate.selectList("library.selectLibraryByTempId",lib_service_tpl_id);
	}

	@Override
	public int addLibraryWithIdx(LibraryEntity libraryEntity) {
		return this.sqlSessionTemplate.insert("library.addLibraryWithIdx",libraryEntity);

	}


	@Override
	public List<LibraryUnionEntity> selectChildLibrary(Map<String, Object> param,Integer limitS,Integer limitE) {
		
		List<LibraryUnionEntity> temp=this.sqlSessionTemplate.selectList("library.selectChildLibrary",param);
		if(limitS != null && limitE != null){
			if(limitS > temp.size()-1){
				temp.clear();
			}else{
				int toIndex = limitS+limitE;
				toIndex = toIndex>temp.size()?temp.size():toIndex;
				temp = temp.subList(limitS, toIndex);
			}
		}
		
		return temp;
	}

	@Override
	public List<LibraryEntity> getAllLibraryList() {
		return this.sqlSessionTemplate.selectList("library.getAllLibraryList");
	}


	@Override
	public List<LibraryEntity> selLibraryByNameORLibId(
			LibraryEntity libraryEntity) {
		return this.sqlSessionTemplate.selectList("library.selLibraryByNameORLibId",libraryEntity);
	}

	@Override
	public List<Map<String, Object>> selectChildLibraryRegionCode(Integer mastLibIdx) {
		return this.sqlSessionTemplate.selectList("library.selectChildLibraryRegionCode",mastLibIdx);
	}

	@Override
	public List<LibraryUnionEntity> selectLibraryAndInfo(Map<String, Object> param) {
		List<LibraryUnionEntity> temp=this.sqlSessionTemplate.selectList("library.selectLibraryAndInfo",param);
		return temp;
	}

	@Override
	public List<Map<String, Object>> queryMasterSubRelations(
			RelLibsEntity relLibsEntity) {
		//list:[{"master_lib_id":1, "slave_lib_id":[1,2,3]},{"master_lib_id":1, "slave_lib_id":[1,2,3]}]
		List<Map<String, Object>> list = this.sqlSessionTemplate.selectList("library.queryMasterSubRelations",relLibsEntity);
		return list;
	}
	
	@Override
	public List<LibraryEntity> selActualLibraryMaster(
			LibraryEntity libraryEntity) {
		return this.sqlSessionTemplate.selectList("library.selActualLibraryMaster",libraryEntity);
	}
}
