package com.ssitcloud.dbauth.dao;

import java.util.List;

import com.ssitcloud.dbauth.common.dao.CommonDao;
import com.ssitcloud.dbauth.entity.LibraryServiceTemplateEntity;
import com.ssitcloud.dbauth.entity.page.LibraryServiceTemplatePageEntity;

/**
 * 图书馆模板类数据库处理类
 * 
 * <p>2016年4月5日 上午11:21:35
 * @author hjc
 *
 */
public interface LibraryTemplateDao extends CommonDao {

	/**
	 * 新增图书馆模板
	 * 
	 * <p>2016年4月5日 下午2:32:18
	 * <p>create by hjc
	 * @param templateEntity 模板实体对象，如果新增成功，lib_service_tpl_id为新增的ID 
	 * @return 返回数据库操作结果
	 */
	public abstract int addLibraryTemplate(LibraryServiceTemplateEntity templateEntity);
	
	
	/**
	 * 根据图书馆模板id删除模板信息
	 * 
	 * <p>2016年4月6日 下午2:06:11
	 * <p>create by hjc
	 * @param templateEntity 图书馆模板实体类
	 * @return 返回数据库操作结果
	 */
	public abstract int delLibraryTemplateById(LibraryServiceTemplateEntity templateEntity);
	
	/**
	 * 获取图书馆模板list
	 *
	 * <p>2016年4月21日 下午2:25:29
	 * <p>create by hjc
	 * @param templateEntity
	 * @return
	 */
	public abstract List<LibraryServiceTemplateEntity> selLibraryTempList(LibraryServiceTemplateEntity templateEntity);
	
	/**
	 * 根据图书馆模板lib_service_tpl_id 获取模板信息
	 *
	 * <p>2016年4月22日 下午7:42:55
	 * <p>create by hjc
	 * @param templateEntity
	 * @return
	 */
	public abstract LibraryServiceTemplateEntity selLibraryServiceTemplateEntity(LibraryServiceTemplateEntity templateEntity);
	
	/**
	 * 更新图书馆模板信息
	 *
	 * <p>2016年4月21日 下午3:49:42
	 * <p>create by hjc
	 * @param templateEntity
	 * @return
	 */
	public abstract int updLibraryTemplate(LibraryServiceTemplateEntity templateEntity);
	
	public abstract LibraryServiceTemplatePageEntity selLibTempBypage(LibraryServiceTemplatePageEntity libtemPageEntity);
	
	public abstract List<LibraryServiceTemplateEntity> selAllTemp();
	
	
	/**
	 * 通过图书馆library_idx 查询该图书馆的模板信息
	 *
	 * <p>2016年6月2日 下午4:58:56 
	 * <p>create by hjc
	 * @param libraryIdx
	 * @return
	 */
	public abstract LibraryServiceTemplateEntity selTempByLibraryIdx(String libraryIdx);


	public abstract int addLibraryTemplateFully(
			LibraryServiceTemplateEntity serviceTemplateEntity);
}
