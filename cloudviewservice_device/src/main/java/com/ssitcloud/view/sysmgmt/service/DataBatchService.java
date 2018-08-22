package com.ssitcloud.view.sysmgmt.service;

import java.io.File;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.BasicService;

public interface DataBatchService extends BasicService{
	
	
	/**
	 * 处理文本文件
	 *
	 * <p>2017年9月4日 下午4:34:27 
	 * <p>create by hjc
	 * @param file 要上传的文件
	 * @param path 文件缓存的根目录
	 * @param charset 文件编码
	 * @param libidx 所属馆idx
	 * @param operator 操作员
	 * @param datatype 数据类型 1：书目带馆藏 2：书目 3：读者
	 * @param update 是否更新原来的数据 1：更新， 2：不更新
	 */
	public abstract ResultEntity handleTxt(File file, String path, String charset, String libidx, String operator, String datatype, String update);
	
	/**
	 * 获取当前用户上传文件的进度
	 *
	 * <p>2017年9月5日 上午9:59:56 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract ResultEntity getCurrentProcessed(Map<String, Object> param);	
	
	/**
	 * 中断上传操作
	 *
	 * <p>2017年9月5日 下午3:55:57 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract ResultEntity interruptUploading(Map<String, Object> param);
	
	/**
	 * 删除上传操作
	 *
	 * <p>2017年9月5日 下午3:55:57 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract ResultEntity deleteUploading(Map<String, Object> param);

	/**
	 * 
	 *
	 * <p>2017年9月25日 下午6:08:33 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract ResponseEntity<byte []> downloadLogFile(Map<String, Object> param);
}
