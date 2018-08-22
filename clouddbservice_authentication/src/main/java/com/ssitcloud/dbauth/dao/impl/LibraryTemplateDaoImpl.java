package com.ssitcloud.dbauth.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.stereotype.Repository;

import com.ssitcloud.dbauth.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbauth.dao.LibraryTemplateDao;
import com.ssitcloud.dbauth.entity.LibraryServiceTemplateEntity;
import com.ssitcloud.dbauth.entity.page.LibraryServiceTemplatePageEntity;

/**
 * <p>2016年4月5日 上午11:24:16
 * @author hjc
 *
 */
@Repository
public class LibraryTemplateDaoImpl extends CommonDaoImpl implements LibraryTemplateDao {

	@Override
	public int addLibraryTemplate(LibraryServiceTemplateEntity templateEntity) {
		return this.sqlSessionTemplate.insert("libraryTemplate.addLibraryTemplate",templateEntity);
	}

	@Override
	public int delLibraryTemplateById(LibraryServiceTemplateEntity templateEntity) {
		return this.sqlSessionTemplate.delete("libraryTemplate.delLibraryTemplateById",templateEntity);
	}

	@Override
	public LibraryServiceTemplateEntity selLibraryServiceTemplateEntity(LibraryServiceTemplateEntity templateEntity) {
		return this.sqlSessionTemplate.selectOne("libraryTemplate.selLibraryServiceTemplateEntity",templateEntity);
	}

	@Override
	public List<LibraryServiceTemplateEntity> selLibraryTempList(LibraryServiceTemplateEntity templateEntity) {
		return this.sqlSessionTemplate.selectList("libraryTemplate.selLibraryTempList", templateEntity);
	}

	@Override
	public int updLibraryTemplate(LibraryServiceTemplateEntity templateEntity) {
		return this.sqlSessionTemplate.update("libraryTemplate.updLibraryTemplate",templateEntity);
	}

	@Override
	public LibraryServiceTemplatePageEntity selLibTempBypage(LibraryServiceTemplatePageEntity libtemPageEntity) {
		
		LibraryServiceTemplatePageEntity total=this.sqlSessionTemplate.selectOne("libraryTemplate.selectBypage", libtemPageEntity);
		libtemPageEntity.setTotal(total.getTotal());
		libtemPageEntity.setDoAount(false);
		libtemPageEntity.setRows(this.sqlSessionTemplate.selectList("libraryTemplate.selectBypage", libtemPageEntity));
		return libtemPageEntity;
	}

	@Override
	public List<LibraryServiceTemplateEntity> selAllTemp() {
		return this.sqlSessionTemplate.selectList("libraryTemplate.selLibraryTempList");
	}

	@Override
	public LibraryServiceTemplateEntity selTempByLibraryIdx(String libraryIdx) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("library_idx", libraryIdx);
		return this.sqlSessionTemplate.selectOne("libraryTemplate.selTempByLibraryIdx",map);
	}

	@Override
	public int addLibraryTemplateFully(
			LibraryServiceTemplateEntity serviceTemplateEntity) {
		return this.sqlSessionTemplate.insert("libraryTemplate.addLibraryTemplateFully",serviceTemplateEntity);
	}

}
