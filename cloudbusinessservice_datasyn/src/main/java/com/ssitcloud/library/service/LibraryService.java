package com.ssitcloud.library.service;

import com.ssitcloud.common.entity.LibraryEntity;
import com.ssitcloud.common.entity.ResultEntity;

public interface LibraryService {
	
	public LibraryEntity selLibraryByIdxOrId(String json);
	/**从redis中获取lib_idx.如果缓存中不存在，则查询mysql数据库*/
	public int getLibraryIdxById(String lib_id);

}
