package com.ssitcloud.shelfmgmt.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.shelfmgmt.dao.BookitemDao;
import com.ssitcloud.shelfmgmt.dao.BookshelfDao;
import com.ssitcloud.shelfmgmt.dao.BookshelfinfoDao;
import com.ssitcloud.shelfmgmt.dao.BookshelflayerDao;
import com.ssitcloud.shelfmgmt.entity.BookshelfEntity;
import com.ssitcloud.shelfmgmt.entity.BookshelfinfoEntity;
import com.ssitcloud.shelfmgmt.entity.BookshelflayerEntity;
import com.ssitcloud.shelfmgmt.service.BookshelfService;

@Service
public class BookshelfServiceImpl implements BookshelfService {

	@Resource
	private BookshelfDao bookshelfDao;
	
	@Resource
	private BookshelfinfoDao bookshelfinfoDao;
	
	@Resource
	private BookshelflayerDao bookshelflayerDao;
	
	@Resource
	private BookitemDao bookitemDao;
	
	@Override
	public ResultEntity queryAllBookshelf(String req) {
		ResultEntity result = new ResultEntity();
		Map<String, String> map = new HashMap<>();
		JSONObject jsonObject =JSONObject.fromObject(req);
		map.put("json", jsonObject.getString("json"));
		map.put("page", jsonObject.getString("page"));
		PageEntity pageEntity = bookshelfDao.queryAllBookshelf(map);
		result.setResult(pageEntity);
		result.setMessage(Const.SUCCESS);
		result.setState(true);
		return result;
	}

	@Override
	public ResultEntity queryBookshelfById(String req) {
		BookshelfEntity bookshelfEntity =JsonUtils.fromJson(req, BookshelfEntity.class);
		BookshelfEntity entity = bookshelfDao.queryBookshelfById(bookshelfEntity);
		ResultEntity result = new ResultEntity();
		result.setResult(entity);
		result.setState(true);
		result.setMessage(Const.SUCCESS);
		return result;
	}
	
	@Override
	public ResultEntity updateBookshelf(String req) {
		ResultEntity result = new ResultEntity();
		JSONObject jsonObject =JSONObject.fromObject(req);
		BookshelfEntity bookshelfEntity = JsonUtils.fromJson(jsonObject.getString("json1"), BookshelfEntity.class);
		String shelfinfo = jsonObject.getString("json2");
		
		JSONArray jsonarray = JSONArray.fromObject(shelfinfo);
		List<BookshelfinfoEntity> list = (List<BookshelfinfoEntity>)JSONArray.toCollection(jsonarray, BookshelfinfoEntity.class); 
		int re = bookshelfDao.updateBookshelf(bookshelfEntity);
		if(re == 1){
			for(BookshelfinfoEntity info : list){
				re = bookshelfinfoDao.updateBookshelfinfo(info);
			}
		}
		result.setState(re >= 1 ? true : false);
		result.setRetMessage(re >= 1 ? Const.SUCCESS : "optimistic");
		return result;
	}

	@Override
	public ResultEntity deleteBookshelf(String req) {
		ResultEntity result = new ResultEntity();
		JSONArray jsonarray = JSONArray.fromObject(req);
		List<BookshelfEntity> list = (List<BookshelfEntity>)JSONArray.toCollection(jsonarray, BookshelfEntity.class); 
		int re = 0;
		for(BookshelfEntity entity:list){
			re = bookshelfDao.deleteBookshelf(entity);
		}
		result.setState(re >= 1 ? true : false);
		result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		return result;
	}

	@Override
	public ResultEntity addBookshelf(String req) {
		ResultEntity result = new ResultEntity();
		JSONObject jsonObject =JSONObject.fromObject(req);
		BookshelfEntity bookshelfEntity = JsonUtils.fromJson(jsonObject.getString("json1"), BookshelfEntity.class);
		String shelfinfo = jsonObject.getString("json2");
		JSONArray jsonarray = JSONArray.fromObject(shelfinfo);
		List<BookshelfinfoEntity> list = (List<BookshelfinfoEntity>)JSONArray.toCollection(jsonarray, BookshelfinfoEntity.class); 
		
		int re = bookshelfDao.addBookshelf(bookshelfEntity);
		if(re == 1){
			for(BookshelfinfoEntity info : list){
				re = bookshelfinfoDao.addBookshelfinfo(info);
			}
		}
		
		result.setState(re >= 1 ? true : false);
		result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		return result;
	}
	
	@Override
	public ResultEntity updateShelfimage(String req) {
		ResultEntity result = new ResultEntity();
		BookshelfEntity bookshelf = JsonUtils.fromJson(req, BookshelfEntity.class);
		int re = bookshelfDao.updateShelfimage(bookshelf);
		result.setState(re >= 1 ? true : false);
		result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		return result;
	}

	@Override
	public ResultEntity updateFloorimage(String req) {
		ResultEntity result = new ResultEntity();
		BookshelfEntity bookshelf = JsonUtils.fromJson(req, BookshelfEntity.class);
		int re = bookshelfDao.updateFloorimage(bookshelf);
		result.setState(re >= 1 ? true : false);
		result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		return result;
	}
	
	@Override
	public ResultEntity uploadPoint(String req) {
		ResultEntity result = new ResultEntity();
		try {
			JSONObject jsonObject =JSONObject.fromObject(req);
			String lib_id = "";
			int point_type = Integer.parseInt(jsonObject.getString("point_type"));
			int re = 0;
            /*String encoding="GBK";*/
            File file=new File(jsonObject.getString("file"));
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file)/*,encoding*/);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String	lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	String shelf_id = null;
                	String shelflayer_barcode = null;
                	String dot = null;
                	try{
                		String point[] = lineTxt.split(",",2 );
                		shelf_id = point[0];
                		shelflayer_barcode = point[0];
                    	dot = point[1];
                    	String dots[] = dot.split("-");
                    	dot = "";
                    	for(int i = 0;i<dots.length;i++){
                    		String str = dots[i];
                    		String x = str.substring(1,str.indexOf(","));
                    		long xx = Math.round(Double.parseDouble(x)/2.4);
                    		//double xx = Integer.parseInt(x)/2.4;
                    		String y = str.substring(str.indexOf(",")+1, str.length()-1);
                    		long yy = Math.round(Double.parseDouble(y)/3.1);
                    		//double yy = Integer.parseInt(y)/3.1;
                    		if(i == dots.length - 1){
                    			dot = dot + "("+xx+","+yy+")";
                    		}else{
                    			dot = dot + "("+xx+","+yy+")-";
                    		}
                    	}
                	}catch(Exception e){
                		LogUtils.error("数据有误！"+lineTxt);
                		continue;
                	}
                	if(shelf_id == null || shelflayer_barcode == null || dot == null){
                		continue;
                	}
                	if(point_type == 1){
                		BookshelfEntity bookshelfEntity = new BookshelfEntity();
                		bookshelfEntity.setLib_id(lib_id);
                		bookshelfEntity.setShelf_id(shelf_id);
                		bookshelfEntity.setShelfpoint(dot);
                		bookshelfDao.updatePoint(bookshelfEntity);
                	}
                	else if(point_type == 2){
                		BookshelflayerEntity bookshelflayerEntity = new BookshelflayerEntity();
                		bookshelflayerEntity.setLib_id(lib_id);
                		bookshelflayerEntity.setShelflayer_barcode(shelflayer_barcode);
                		bookshelflayerEntity = bookshelflayerDao.queryBookshelflayerByid(bookshelflayerEntity);
                		if(bookshelflayerEntity != null){
                			BookshelfEntity bookshelfEntity = new BookshelfEntity();
                			bookshelfEntity.setLib_id(lib_id);
                			bookshelfEntity.setShelf_id(bookshelflayerEntity.getShelf_id());
                			bookshelfEntity.setShelfpoint(dot);
                			bookshelfDao.updatePoint(bookshelfEntity);
                		}
                	}
                }
                read.close();
                re = 1;
            }else{
            	System.out.println("找不到指定的文件");
            	re = 0;
            }
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
