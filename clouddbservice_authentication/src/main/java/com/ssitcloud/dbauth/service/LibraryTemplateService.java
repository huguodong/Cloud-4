package com.ssitcloud.dbauth.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.entity.LibraryServiceTemplateEntity;
import com.ssitcloud.dbauth.entity.page.LibraryServiceTemplatePageEntity;

/**
 * <p>2016年4月5日 上午11:31:19
 * @author hjc
 *
 */
public interface LibraryTemplateService {
	/**
	 * 新增图书馆模板
	 * 
	 * <p>2016年4月5日 下午2:32:18
	 * <p>create by hjc
	 * @param templateEntity 模板实体对象，如果新增成功，lib_service_tpl_id为新增的ID 
	 * @return 返回数据库操作结果
	 */
	public abstract int addLibraryTemplate(String json);
	
	/**
	 * 根据图书馆模板id删除模板信息
	 * 
	 * <p>2016年4月6日 下午2:06:11
	 * <p>create by hjc
	 * @param templateEntity 图书馆模板实体类
	 * @return 返回数据库操作结果
	 */
	public abstract int delLibraryTemplateById(String json);
	
	/**
	 * 删除图书馆模板信息，单条删除 批量删除
	 *
	 * <p>2016年7月18日 上午10:53:23 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract ResultEntity delLibraryTemplate(String json);
	
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
	 * 分页查询模版
	 * @comment
	 * @param libTempInfo
	 * @return
	 * @data 2016年5月26日`
	 * @author hwl
	 */
	public abstract LibraryServiceTemplatePageEntity selLibraryTemp(String libTempInfo);
	
	public abstract List<LibraryServiceTemplateEntity> selAllLibraryTemp();
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
	public abstract ResultEntity updLibraryTemplate(String json);
	
	/**
	 * 通过图书馆library_idx 查询该图书馆的模板信息
	 *
	 * <p>2016年6月2日 下午4:58:56 
	 * <p>create by hjc
	 * @param libraryIdx
	 * @return
	 */
	public abstract LibraryServiceTemplateEntity selTempByLibraryIdx(String libraryIdx);

	public abstract LibraryServiceTemplateEntity selLibraryServiceTemplateByIdx(
			String req);
	
	
}
