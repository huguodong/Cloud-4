package com.ssitcloud.dblib.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.dblib.common.utils.ExportExcelUtils;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.BookShelfDao;
import com.ssitcloud.dblib.dao.BookShelfInfoDao;
import com.ssitcloud.dblib.dao.BookShelfLayerDao;
import com.ssitcloud.dblib.entity.BookShelfEntity;
import com.ssitcloud.dblib.entity.BookShelfInfoEntity;
import com.ssitcloud.dblib.entity.BookShelfLayerEntity;
import com.ssitcloud.dblib.entity.ExportBookshelfEntity;
import com.ssitcloud.dblib.service.BookShelfLayerService;

@Service
public class BookShelfLayerServiceImpl implements BookShelfLayerService {
	@Resource
	private BookShelfLayerDao bookShelfLayerDao;

	@Resource
	private  BookShelfDao bookshelfDao;
	
	@Resource
	private  BookShelfInfoDao bookshelfinfoDao;
	
	@Override
	public PageEntity queryAllBookshelflayer(Map<String, String> map) {
		// TODO Auto-generated method stub
		return bookShelfLayerDao.queryAllBookshelflayer(map);
	}

	@Override
	public int updateBookshelflayer(BookShelfLayerEntity bookshelflayerEntity) {
		// TODO Auto-generated method stub
		return bookShelfLayerDao.updateBookshelflayer(bookshelflayerEntity);
	}

	@Override
	public int deleteBookshelflayer(BookShelfLayerEntity bookshelflayerEntity) {
		// TODO Auto-generated method stub
		return bookShelfLayerDao.deleteBookshelflayer(bookshelflayerEntity);
	}

	@Override
	public int addBookshelflayer(BookShelfLayerEntity bookshelflayerEntity) {
		// TODO Auto-generated method stub
		return bookShelfLayerDao.addBookshelflayer(bookshelflayerEntity);
	}
	
	@Override
	public List<ExportBookshelfEntity> exportBookshelflayer(BookShelfLayerEntity bookshelflayerEntity){
		// TODO Auto-generated method stub
		return bookShelfLayerDao.exportBookshelflayer(bookshelflayerEntity);
	}
	
	@Override
	public int uploadBookshelflayer(String req){
		// TODO Auto-generated method stub
		try {
			Map<String,String> map1 = JsonUtils.fromJson(req, Map.class);
			String filePath = map1.get("filePath");
			String libId = map1.get("libId");
			/*JSONObject jsonObject =JSONObject.fromObject(req);
			String filePath = jsonObject.getString("filePath").toString();
			String libId = jsonObject.getString("libId").toString();*/
			InputStream is = new FileInputStream(new File(filePath));
			Map<Integer, String> map = ExportExcelUtils.readExcelContent(is);
			for (Integer key : map.keySet()) {  
				
				String value = map.get(key);
				
				String values[] = value.split("\\|");
				BookShelfEntity bookshelfEntity = new BookShelfEntity();
				bookshelfEntity.setLib_id(libId);
				bookshelfEntity.setShelf_id(values[2]);
				bookshelfEntity.setShelf_name(values[3]);
				bookshelfEntity.setLayerCount(exchange(values[5]));
				bookshelfEntity.setLayerSort(exchange(values[6]));
				bookshelfEntity.setFloorImage_url(values[7]);
				bookshelfEntity.setShelfImage_url(values[8]);
				bookshelfEntity.setShelfPoint(values[9]);
				bookshelfEntity.setIsSmartShelf(exchange(values[10]));
				BookShelfEntity bookshelfEntity_bak = bookshelfDao.queryBookshelfById(bookshelfEntity);
				//System.out.println(bookshelfEntity_bak.toString());
				BookShelfLayerEntity bookshelflayerEntity = new BookShelfLayerEntity();
				bookshelflayerEntity.setLib_id(libId);
				bookshelflayerEntity.setShelf_id(values[2]);
				bookshelflayerEntity.setShelf_layer(exchange(values[4]));
				bookshelflayerEntity.setShelflayer_barcode(values[0]);
				bookshelflayerEntity.setShelflayer_name(values[1]);
				if(bookshelfEntity_bak != null){
					bookshelfDao.updateBookshelf(bookshelfEntity);
				}
				else{
					bookshelfDao.addBookshelf(bookshelfEntity);
				}
				System.out.println(values[11]);
				if(values[11] != null && !values[11].equals("")){
					BookShelfInfoEntity bookshelfinfoEntity = new BookShelfInfoEntity();
					bookshelfinfoEntity.setLib_id(libId);
					bookshelfinfoEntity.setShelf_id(values[2]);
					bookshelfinfoDao.deleteBookshelfinfo(bookshelfinfoEntity);
					String[] info_values = values[11].split(",");
					for(int i=0;i<info_values.length;i++){
						bookshelfinfoEntity.setInfo_type(i+1);
						bookshelfinfoEntity.setInfo_value(info_values[i]);
						bookshelfinfoDao.addBookshelfinfo(bookshelfinfoEntity);
					}
				}
				BookShelfLayerEntity bookshelflayerEntity_bak = bookShelfLayerDao.queryBookshelflayerByid(bookshelflayerEntity);
				if(bookshelflayerEntity_bak != null){
					bookShelfLayerDao.updateBookshelflayer(bookshelflayerEntity);
				}
				else{
					bookShelfLayerDao.addBookshelflayer(bookshelflayerEntity);
				}
	        } 
			return 1;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	
	Integer exchange(String str){
		if(str != null && str.length()>0){
			return Integer.parseInt(str);
		}else{
			return 0;
		}
	}
}
