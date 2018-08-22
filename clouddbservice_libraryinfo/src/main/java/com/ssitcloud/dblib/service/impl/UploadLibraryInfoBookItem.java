package com.ssitcloud.dblib.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.dao.BibliosDao;
import com.ssitcloud.dblib.dao.BookItemDao;
import com.ssitcloud.dblib.entity.BibliosEntity;
import com.ssitcloud.dblib.entity.BookItemEntity;
import com.ssitcloud.dblib.entity.UploadLibraryInfoEntity;
import com.ssitcloud.dblib.service.LibraryInfoService;


@Component("uploadLibraryInfo_bookitem")
public class UploadLibraryInfoBookItem implements LibraryInfoService<UploadLibraryInfoEntity>{
	
	@Resource
	private BibliosDao bibliosDao;
	
	@Resource
	private BookItemDao bookItemDao;

	@Override
	public ResultEntity execute(
			UploadLibraryInfoEntity uploadLibraryInfoEntity) {
		
		ResultEntity result=new ResultEntity();
		try {
			Map<String, Object> fields = uploadLibraryInfoEntity.getFields();
			List<Map<String, Object>> records = uploadLibraryInfoEntity.getRecords();
			String device_idx = uploadLibraryInfoEntity.getDevice_idx();
			String library_idx = uploadLibraryInfoEntity.getLibrary_idx();//
			String library_id = uploadLibraryInfoEntity.getLibrary_id();
			String device_id = uploadLibraryInfoEntity.getDevice_id();
			
			if(fields==null||records==null){
				result.setState(false);
				result.setMessage(Const.FAILED);
				result.setRetMessage("fields is null or records is null");
				return result;
			}
			records = comboFielsAndValue(uploadLibraryInfoEntity.getFields(), uploadLibraryInfoEntity.getRecords());
			long pid = System.currentTimeMillis();
			System.out.println(pid+"长度："+records.size());
			int i = 0;
			for (Map<String, Object> map : records) {
				BibliosEntity bibliosEntity = new BibliosEntity();
				//如果有ISBN，则通过ISBN获取biblios的数据，如果没有则新增，如果有获取这条数据的idx
				Integer bib_idx = null;
				if (map.containsKey("ISBN") && StringUtils.hasText(map.get("ISBN")+"")) {
					bibliosEntity.setISBN(map.get("ISBN")+"");
					bibliosEntity = bibliosDao.queryBibliosByISBN(bibliosEntity);
					if (bibliosEntity == null || bibliosEntity.getBib_idx() == null) {
						//新增
						bibliosEntity = new BibliosEntity(map);
						bibliosDao.insertBiblios(bibliosEntity);
					}
					bib_idx = bibliosEntity.getBib_idx();
				}else{
					//没有isbn 通过 标题，以及著者去获取图书信息，如果没有则新增
			        if (map.containsKey("title") && map.containsKey("author") 
			        		&& StringUtils.hasText(map.get("title")+"")
			        		&& StringUtils.hasText(map.get("author")+"")) {
			        	String title = map.get("title") + "";
			        	String author = map.get("author") + "";
			        	
			        	bibliosEntity.setTitle(title);
			        	bibliosEntity.setAuthor(author);
			        	
			        	bibliosEntity = bibliosDao.queryBibliosByTitleAndAuthor(bibliosEntity);
					}
			        if (bibliosEntity == null || bibliosEntity.getBib_idx() == null) {
		        		//新增
						bibliosEntity = new BibliosEntity(map);
						bibliosDao.insertBiblios(bibliosEntity);
		        	}
		        	bib_idx = bibliosEntity.getBib_idx();
				}
				//获取到bib_idx 保存到bookitem
				if (bib_idx!=null) {
					++i;
					try {
						BookItemEntity bookItemEntity = new BookItemEntity();//将map数据封装到馆藏实体中
						bookItemEntity.setLib_idx(Integer.valueOf(library_idx));//所属馆
						bookItemEntity.setNowlib_idx(Integer.valueOf(library_idx));//当前馆,目前只有ssl设备，所以
						//bookItemEntity.setDevice_idx(Integer.valueOf(device_idx));//设备idx
						if (map.containsKey("book_barcode")) {
							//根据libidx 以及barcode查询是不是有记录
							bookItemEntity.setBook_barcode(map.get("book_barcode")+"");
							bookItemEntity = bookItemDao.queryBookItemByLibIdxAndBookBarcode(bookItemEntity);
							
							BookItemEntity newBookItem = new BookItemEntity(map);
							newBookItem.setLib_idx(Integer.valueOf(library_idx));//所属馆
							newBookItem.setNowlib_idx(Integer.valueOf(library_idx));//当前馆,目前只有ssl设备，所以
							//newBookItem.setDevice_idx(Integer.valueOf(device_idx));//设备idx
							newBookItem.setBib_idx(bib_idx);//书目记录号
							if (bookItemEntity!=null) {
								newBookItem.setBookitem_idx(bookItemEntity.getBookitem_idx());
								bookItemDao.updateBookItemNoUpdatetime(newBookItem);
							}else{
								bookItemDao.insertBookItemNoUpdatetime(newBookItem);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}
				result.setState(true);
			}
			System.out.println(pid+"处理长度:"+i);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	
	protected static List<Map<String, Object>> comboFielsAndValue(Map<String,Object> fields,List<Map<String,Object>> records){
		List<Map<String,Object>> rets=new ArrayList<>();
		for(Map<String,Object> record:records){
			Map<String,Object> newMap=new HashMap<>();
			for(Entry<String, Object> entry:fields.entrySet()){
				String key=entry.getKey();//字段 
				Object value=entry.getValue();//1 2 3 4 
				if (record.get(value) != null) {
					Object obj = record.get(value);
					newMap.put(key, String.valueOf(obj));
				}
			}
			rets.add(newMap);
		}
		return rets;
	}
	
}
