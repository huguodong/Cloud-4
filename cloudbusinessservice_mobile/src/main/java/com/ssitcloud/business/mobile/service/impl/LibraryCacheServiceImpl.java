package com.ssitcloud.business.mobile.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.business.mobile.service.LibraryCacheServiceI;
import com.ssitcloud.business.mobile.service.LibraryServiceI;

@Service("libraryCacheService")
public class LibraryCacheServiceImpl extends AbstractCacheServiceImpl<Integer,LibraryEntity> implements LibraryCacheServiceI{

	private final long TIME_OUT = 1*60*60*1000;//1 hour
	
	@Autowired
	private LibraryServiceI libraryService;
	
	@Async
	@Override
	public void updateAsync() {
		update();
	}

	@Override
	protected long getTimeOut() {
		return TIME_OUT;
	}

	@Override
	protected List<LibraryEntity> getSourceData() throws CanNotObtainDataException {
		try{
			List<Map<String, Object>> libraryList = libraryService.selectLibrarys(new HashMap<String, Object>(8));
			List<LibraryEntity> rd = new ArrayList<>(libraryList.size());
			for (Map<String, Object> map : libraryList) {
				LibraryEntity le = new LibraryEntity();
				le.setLibrary_idx((Integer) map.get("library_idx"));
				le.setLib_name((String) map.get("lib_name"));
				le.setLib_id((String) map.get("lib_id"));
				
				rd.add(le);
			}
			return rd;	
		}catch(Exception e){
			throw new CanNotObtainDataException();
		}
		
	}

	@Override
	protected Integer getSourceDataKey(LibraryEntity v) {
		return v.getLibrary_idx();
	}
	
	/**
	 * 此方法返回的图书馆实体只包含library_idx lib_id lib_name
	 */
	@Override
	public LibraryEntity get(Integer k) {
		return super.get(k);
	}

	@Override
	protected LibraryEntity getSourceData(Integer k) throws CanNotObtainDataException {
		Map<String, Object> map = libraryService.selectLibraryByPk(k);
		if(map != null){
			LibraryEntity le = new LibraryEntity();
			le.setLibrary_idx((Integer) map.get("library_idx"));
			le.setLib_name((String) map.get("lib_name"));
			le.setLib_id((String) map.get("lib_id"));
		}
		
		throw new CanNotObtainDataException();
	}
}
