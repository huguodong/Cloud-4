package com.ssitcloud.shelfmgmt.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.ExportExcelUtils;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.shelfmgmt.dao.BookshelfDao;
import com.ssitcloud.shelfmgmt.dao.BookshelfinfoDao;
import com.ssitcloud.shelfmgmt.dao.BookshelflayerDao;
import com.ssitcloud.shelfmgmt.entity.BookshelfEntity;
import com.ssitcloud.shelfmgmt.entity.BookshelfinfoEntity;
import com.ssitcloud.shelfmgmt.entity.BookshelflayerEntity;
import com.ssitcloud.shelfmgmt.entity.ExportBookshelfEntity;
import com.ssitcloud.shelfmgmt.service.BookshelflayerService;


@Service
public class BookshelflayerServiceImpl implements BookshelflayerService {

	@Resource
	private BookshelflayerDao bookshelflayerDao;
	
	@Resource
	private  BookshelfDao bookshelfDao;
	
	@Resource
	private  BookshelfinfoDao bookshelfinfoDao;
	
	@Override
	public ResultEntity queryAllBookshelflayer(String req) {
		ResultEntity result = new ResultEntity();
		Map<String, String> map = new HashMap<>();
		JSONObject jsonObject =JSONObject.fromObject(req);
		map.put("json", jsonObject.getString("json"));
		map.put("page", jsonObject.getString("page"));
		PageEntity pageEntity = bookshelflayerDao.queryAllBookshelflayer(map);
		result.setResult(pageEntity);
		result.setMessage(Const.SUCCESS);
		result.setState(true);
		return result;
	}

	@Override
	public ResultEntity updateBookshelflayer(String req) {
		ResultEntity result = new ResultEntity();
		BookshelflayerEntity bookshelf = JsonUtils.fromJson(req, BookshelflayerEntity.class);
		int re = bookshelflayerDao.updateBookshelflayer(bookshelf);
		result.setState(re >= 1 ? true : false);
		result.setRetMessage(re >= 1 ? Const.SUCCESS : "optimistic");
		return result;
	}

	@Override
	public ResultEntity deleteBookshelflayer(String req) {
		ResultEntity result = new ResultEntity();
		JSONArray jsonarray = JSONArray.fromObject(req);
		List<BookshelflayerEntity> list = (List<BookshelflayerEntity>)JSONArray.toCollection(jsonarray, BookshelflayerEntity.class); 
		int re = 0;
		for(BookshelflayerEntity entity:list){
			re = bookshelflayerDao.deleteBookshelflayer(entity);
		}
		result.setState(re >= 1 ? true : false);
		result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		return result;
	}

	@Override
	public ResultEntity addBookshelflayer(String req) {
		ResultEntity result = new ResultEntity();
		BookshelflayerEntity bookshelf = JsonUtils.fromJson(req, BookshelflayerEntity.class);
		int re = bookshelflayerDao.addBookshelflayer(bookshelf);
		result.setState(re >= 1 ? true : false);
		result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		return result;
	}
	
	@Override
	public ResultEntity exportBookshelflayer(String req){
		ResultEntity result = new ResultEntity();
		List<ExportBookshelfEntity> list = bookshelflayerDao.exportBookshelflayer(JsonUtils.fromJson(req, BookshelflayerEntity.class));
		result.setResult(list);
		result.setMessage(Const.SUCCESS);
		result.setState(true);
		return result;
	}
	
	@Override
	public  ResultEntity uploadBookshelflayer(String req){
		int re = 0;
		ResultEntity result = new ResultEntity();
		try {
			InputStream is = new FileInputStream(new File(req));
			Map<Integer, String> map = ExportExcelUtils.readExcelContent(is);
			for (Integer key : map.keySet()) {  
				
				String value = map.get(key);
				
				String values[] = value.split("\\|");
				for(int i=0;i<values.length;i++){
					values[i]=removePart(values[i]);
				}
				BookshelfEntity bookshelfEntity = new BookshelfEntity();
				bookshelfEntity.setLib_id("");
				bookshelfEntity.setShelf_id(values[2]);
				bookshelfEntity.setShelf_name(values[3]);
				bookshelfEntity.setLayercount(exchange(values[5]));
				bookshelfEntity.setLayersort(exchange(values[6]));
				bookshelfEntity.setFloorimage_url(values[7]);
				bookshelfEntity.setShelfimage_url(values[8]);
				bookshelfEntity.setShelfpoint(values[9]);
				bookshelfEntity.setIssmartshelf(exchange(values[10]));
				BookshelfEntity bookshelfEntity_bak = bookshelfDao.queryBookshelfById(bookshelfEntity);
				//System.out.println(bookshelfEntity_bak.toString());
				BookshelflayerEntity bookshelflayerEntity = new BookshelflayerEntity();
				bookshelflayerEntity.setLib_id("");
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
				if(values[11] != null && !values[11].equals("")){
					BookshelfinfoEntity bookshelfinfoEntity = new BookshelfinfoEntity();
					bookshelfinfoEntity.setLib_id("");
					bookshelfinfoEntity.setShelf_id(values[2]);
					bookshelfinfoDao.deleteBookshelfinfo(bookshelfinfoEntity);
					String[] info_values = values[11].split(",");
					for(int i=0;i<info_values.length;i++){
						bookshelfinfoEntity.setInfo_type(i+1);
						bookshelfinfoEntity.setInfo_value(info_values[i]);
						bookshelfinfoDao.addBookshelfinfo(bookshelfinfoEntity);
					}
				}
				BookshelflayerEntity bookshelflayerEntity_bak = bookshelflayerDao.queryBookshelflayerByid(bookshelflayerEntity);
				if(bookshelflayerEntity_bak != null){
					bookshelflayerDao.updateBookshelflayer(bookshelflayerEntity);
				}
				else{
					bookshelflayerDao.addBookshelflayer(bookshelflayerEntity);
				}
	        } 
			re = 1;
		} catch (FileNotFoundException e) {
			re = 0;
		}
		
		result.setState(re >= 1 ? true : false);
		result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		return result;
	}
	
	Integer exchange(String str){
		if(str != null && str.length()>0){
			return Integer.parseInt(str);
		}else{
			return 0;
		}
	}
	
	String removePart(String str){
		String newSt = str;
		if(str != null && str.length()>0){
			if(str.contains(".0")&&str.indexOf(".0")==str.length()-2){
				newSt = str.replaceAll(".0", "");
			}
		}
		return newSt;
	}

	@Override
	public ResultEntity getbookshelfbybookbarcode(String req) {
		ResultEntity result = new ResultEntity();
		List<ExportBookshelfEntity> list = bookshelflayerDao.getbookshelfbybookbarcode(JsonUtils.fromJson(req, BookshelflayerEntity.class));
		if(list==null||list.size()==0){
			list=null;
		}
		result.setResult(list);
		result.setMessage(Const.SUCCESS);
		result.setState(true);
		return result;
	}
}
