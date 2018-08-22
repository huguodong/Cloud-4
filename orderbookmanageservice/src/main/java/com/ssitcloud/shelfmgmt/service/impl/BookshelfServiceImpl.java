package com.ssitcloud.shelfmgmt.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import com.ssitcloud.shelfmgmt.dao.BookitemDao;
import com.ssitcloud.shelfmgmt.dao.BookshelfDao;
import com.ssitcloud.shelfmgmt.dao.BookshelfinfoDao;
import com.ssitcloud.shelfmgmt.dao.BookshelflayerDao;
import com.ssitcloud.shelfmgmt.entity.BibliosEntity;
import com.ssitcloud.shelfmgmt.entity.BookitemEntity;
import com.ssitcloud.shelfmgmt.entity.BookshelfEntity;
import com.ssitcloud.shelfmgmt.entity.BookshelfinfoEntity;
import com.ssitcloud.shelfmgmt.entity.BookshelflayerEntity;
import com.ssitcloud.shelfmgmt.entity.RelShelfGroupEntity;
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
		RelShelfGroupEntity relShelfGroupEntity = JsonUtils.fromJson(jsonObject.getString("json2"), RelShelfGroupEntity.class);
		String shelfinfo = jsonObject.getString("json3");
		
		JSONArray jsonarray = JSONArray.fromObject(shelfinfo);
		List<BookshelfinfoEntity> list = (List<BookshelfinfoEntity>)JSONArray.toCollection(jsonarray, BookshelfinfoEntity.class); 
		int re = bookshelfDao.updateBookshelf(bookshelfEntity);
		if(re == 1){
			bookshelfDao.updateBookshelf(relShelfGroupEntity);
			if(re == 1){
				for(BookshelfinfoEntity info : list){
					re = bookshelfinfoDao.updateBookshelfinfo(info);
				}
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
		int re = bookshelfDao.deleteBookshelf(list);
		result.setState(re >= 1 ? true : false);
		result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		return result;
	}

	@Override
	public ResultEntity addBookshelf(String req) {
		ResultEntity result = new ResultEntity();
		JSONObject jsonObject =JSONObject.fromObject(req);
		BookshelfEntity bookshelfEntity = JsonUtils.fromJson(jsonObject.getString("json1"), BookshelfEntity.class);
		RelShelfGroupEntity relShelfGroupEntity = JsonUtils.fromJson(jsonObject.getString("json2"), RelShelfGroupEntity.class);
		String shelfinfo = jsonObject.getString("json3");
		JSONArray jsonarray = JSONArray.fromObject(shelfinfo);
		List<BookshelfinfoEntity> list = (List<BookshelfinfoEntity>)JSONArray.toCollection(jsonarray, BookshelfinfoEntity.class); 
		
		int re = bookshelfDao.addBookshelf(bookshelfEntity);
		if(re == 1){
			System.out.println(bookshelfEntity.getShelf_idx());
			relShelfGroupEntity.setShelf_idx(bookshelfEntity.getShelf_idx());
			re = bookshelfDao.addRelShelfGroup(relShelfGroupEntity);
			if(re == 1){
				for(BookshelfinfoEntity info : list){
					re = bookshelfinfoDao.addBookshelfinfo(info);
				}
			}
		}
		
		result.setState(re >= 1 ? true : false);
		result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		return result;
	}
	
	@Override
	public ResultEntity updateShelfimage(String req) {
		ResultEntity result = new ResultEntity();
		int re = bookshelfDao.updateShelfimage(JsonUtils.fromJson(req, BookshelfEntity.class));
		result.setState(re >= 1 ? true : false);
		result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		return result;
	}

	@Override
	public ResultEntity updateFloorimage(String req) {
		ResultEntity result = new ResultEntity();
		try {
			int re = bookshelfDao.updateFloorimage(JsonUtils.fromJson(
					req, BookshelfEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
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
            String encoding="GBK";
            File file=new File(jsonObject.getString("file"));
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String	lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	String point[] = lineTxt.split(",",2 );
                	if(point_type == 1){
                		BookshelfEntity bookshelfEntity = new BookshelfEntity();
                		bookshelfEntity.setLib_id(lib_id);
                		bookshelfEntity.setShelf_id(point[0]);
                		bookshelfEntity.setShelfpoint(point[1]);
                		bookshelfDao.updatePoint(bookshelfEntity);
                	}
                	else if(point_type == 2){
                		BookshelflayerEntity bookshelflayerEntity = new BookshelflayerEntity();
                		bookshelflayerEntity.setLib_id(lib_id);
                		bookshelflayerEntity.setShelflayer_barcode(point[0]);
                		bookshelflayerEntity = bookshelflayerDao.queryBookshelflayerByid(bookshelflayerEntity);
                		if(bookshelflayerEntity != null){
                			BookshelfEntity bookshelfEntity = new BookshelfEntity();
                			bookshelfEntity.setLib_id(lib_id);
                			bookshelfEntity.setShelf_id(bookshelflayerEntity.getShelf_id());
                			bookshelfEntity.setShelfpoint(point[1]);
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
	
	public static void main(String[] args) throws Exception {
		File file = new File("C://1111.txt");
		InputStreamReader read = new InputStreamReader(new FileInputStream(file));
		BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
		while ((lineTxt = bufferedReader.readLine()) != null) {
			if(lineTxt.length()<16) continue;
			System.out.println(lineTxt.substring(0, 17));
			String barcode = lineTxt.substring(0, 17);
			System.out.println(lineTxt.substring(18, lineTxt.length()));
			String title = lineTxt.substring(18, lineTxt.length());
			break;
		}
	}
	
	//导入层架标数据，将A区对应关系放文件对应shelf_group = 38 B区对应 39
	@Override
	public ResultEntity inportShelfData() {
		try {
        	File file = new File("C://2222.txt");
    		InputStreamReader read = new InputStreamReader(new FileInputStream(file));
    		BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				if(lineTxt.length()<6) continue;
				System.out.println(lineTxt.substring(0, 6));
				String layerCode = lineTxt.substring(0, 6);
				System.out.println(lineTxt.substring(7, lineTxt.length()));
				String location = lineTxt.substring(7, lineTxt.length());
				String[] str = location.split("\\,");
				String shelfName = str[0]+"-"+str[1]+"-"+str[2]+"-"+str[3];
				BookshelfEntity shelf = new BookshelfEntity();
				shelf.setShelf_id(shelfName);
				shelf.setShelf_name(shelfName);
				shelf.setLayercount(6);
				shelf.setLayersort(1);
				shelf.setIssmartshelf(4);
				
				RelShelfGroupEntity relShelfGroupEntity  = new RelShelfGroupEntity();
				relShelfGroupEntity.setShelf_group_idx(39);
				
				List<BookshelfinfoEntity> list = new ArrayList<>();
				BookshelfinfoEntity info1 = new BookshelfinfoEntity();
				info1.setInfo_type(1);
				info1.setInfo_value("1");
				info1.setShelf_id(shelfName);
				list.add(info1);
				
				BookshelfinfoEntity info2 = new BookshelfinfoEntity();
				info2.setInfo_type(2);
				info2.setInfo_value(str[0]);
				info2.setShelf_id(shelfName);
				list.add(info2);
				
				BookshelfinfoEntity info3 = new BookshelfinfoEntity();
				info3.setInfo_type(3);
				info3.setInfo_value(str[1]);
				info3.setShelf_id(shelfName);
				list.add(info3);
				
				BookshelfinfoEntity info4 = new BookshelfinfoEntity();
				info4.setInfo_type(4);
				info4.setInfo_value(str[1]);
				info4.setShelf_id(shelfName);
				list.add(info4);
				
				BookshelfinfoEntity info5 = new BookshelfinfoEntity();
				info5.setInfo_type(5);
				info5.setInfo_value(str[2]);
				info5.setShelf_id(shelfName);
				list.add(info5);
				
				BookshelfinfoEntity info6 = new BookshelfinfoEntity();
				info6.setInfo_type(6);
				info6.setInfo_value(str[3]);
				info6.setShelf_id(shelfName);
				list.add(info6);
				
				BookshelfEntity num = bookshelfDao.queryBookshelfById(shelf);
				if(num == null){
					int re = bookshelfDao.addBookshelf(shelf);
					if(re == 1){
						System.out.println(shelf.getShelf_idx());
						relShelfGroupEntity.setShelf_idx(shelf.getShelf_idx());
						re = bookshelfDao.addRelShelfGroup(relShelfGroupEntity);
						if(re == 1){
							for(BookshelfinfoEntity info : list){
								re = bookshelfinfoDao.addBookshelfinfo(info);
							}
						}
					}
				}
				BookshelflayerEntity layer = new BookshelflayerEntity();
				layer.setShelf_id(shelfName);
				layer.setShelf_layer(Integer.parseInt(str[4]));
				layer.setShelflayer_barcode(layerCode);
				layer.setShelflayer_name(layerCode);
				bookshelflayerDao.addBookshelflayer(layer);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//导入图书馆之前数据，先分别导入书目数据，再导入在架书数据，运行程序关联/删除重复的书目数据
	@Override
	public ResultEntity inportBookData() {
		List<BibliosEntity> bs = bookshelfDao.selectBiblios();
		System.out.println(bs.size());
		Map<String,String> map = new HashMap<>();
		Map<String,Integer> map2 = new HashMap<>();
		
		List<BookitemEntity> bk = bookshelfDao.selectBookitem();
		System.out.println(bk.size());
		
		for(BibliosEntity ety : bs){
			map.put(ety.getSubject().substring(1, 15), ety.getTitle());
			System.out.println(ety.getTitle());
			if(map2.get(ety.getTitle())!=null && map2.get(ety.getTitle())>0){
				BibliosEntity e = new BibliosEntity();
				e.setBib_idx(map2.get(ety.getTitle()));
				bookshelfDao.deleteBiblios(e);
			}
			map2.put(ety.getTitle(), ety.getBib_idx());
		}
		
		for(BookitemEntity ety : bk){
			if(map.get(ety.getBook_barcode())!= null){
				String title = map.get(ety.getBook_barcode());
				if(map2.get(title)!=null){
					int idx = map2.get(title);
					ety.setBib_idx(idx);
					ety.setUpdate_uid_flag(1);
					ety.setState(0);
					ety.setUpdatetime("20170419");
					
					bookitemDao.updateBookitem(ety);
				}
			}
			
		}
		return null;
	}
}
