package com.ssitcloud.shelfmgmt.service.impl;

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
import com.ssitcloud.shelfmgmt.dao.BookshelfDao;
import com.ssitcloud.shelfmgmt.dao.BookshelfinfoDao;
import com.ssitcloud.shelfmgmt.dao.BookshelflayerDao;
import com.ssitcloud.shelfmgmt.dao.MakeshelfDao;
import com.ssitcloud.shelfmgmt.entity.BookshelfEntity;
import com.ssitcloud.shelfmgmt.entity.BookshelfinfoEntity;
import com.ssitcloud.shelfmgmt.entity.BookshelflayerEntity;
import com.ssitcloud.shelfmgmt.entity.MakeshelfEntity;
import com.ssitcloud.shelfmgmt.service.MakeshelfService;


@Service
public class MakeshelfServiceImpl implements MakeshelfService {

	@Resource
	private BookshelflayerDao bookshelflayerDao;
	
	@Resource
	private  BookshelfDao bookshelfDao;
	
	@Resource
	private  BookshelfinfoDao bookshelfinfoDao;
	
	@Resource
	private  MakeshelfDao makeshelfDao;
	
	
	@Override
	public ResultEntity queryAllMakeShelfRecord(String req) {
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			JSONObject jsonObject =JSONObject.fromObject(req);
			map.put("json", jsonObject.getString("json"));
			map.put("page", jsonObject.getString("page"));
			PageEntity pageEntity = makeshelfDao.queryAllMakeShelfRecord(map);
			result.setResult(pageEntity);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}


	@Override
	public ResultEntity deleteBookshelflayer(String req) {
		ResultEntity result = new ResultEntity();
		try {
			JSONArray jsonarray = JSONArray.fromObject(req);
			List<BookshelflayerEntity> list = (List<BookshelflayerEntity>)JSONArray.toCollection(jsonarray, BookshelflayerEntity.class); 
			int re = 0;
			for(BookshelflayerEntity entity:list){
				re = bookshelflayerDao.deleteBookshelflayer(entity);
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

	@Override
	public  ResultEntity makeshelf(String req){
		int re = 0;
		ResultEntity result = new ResultEntity();
		try {
			MakeshelfEntity makeshelf = JsonUtils.fromJson(req, MakeshelfEntity.class);
			String rows[] = makeshelf.getMarkshelf_rowCode().split(",");
			for (String row : rows) {  
				Integer colNum = makeshelf.getMarkshelf_colNum();
				Integer colSize = makeshelf.getMarkshelf_colSize();
				String colFlag = "";
				for(int a=1;a<=colNum;a++){
					if(colSize == 1 && a < 10){
						colFlag = String.valueOf(a);
					}else if(colSize == 2 && a < 10){
						colFlag = "0"+a;
					}else if(colSize == 2 && a < 100){
						colFlag = String.valueOf(a);
					}else{
						continue;
					}
					
					Integer faceNum = makeshelf.getMarkshelf_faceNum();
					String faceFlag = "";
					for(int i= 1;i<=faceNum;i++){
						/*if(i==1) faceFlag = "1";
						if(i==2) faceFlag = "2";
						if(i==3) faceFlag = "3";
						if(i==4) faceFlag = "4";*/
						
						if(i==1) faceFlag = "A";
						if(i==2) faceFlag = "B";
						if(i==3) faceFlag = "C";
						if(i==4) faceFlag = "D";
						Integer shelfLayerTotal = makeshelf.getMarkshelf_shelfLayerNum();
						//处理书架
						String shelf_id = makeshelf.getMarkshelf_libFlag()+makeshelf.getMarkshelf_floorFlag()+makeshelf.getMarkshelf_zoneFlag()+row+faceFlag+colFlag;
						//String shelf_id = makeshelf.getMarkshelf_floorFlag()+makeshelf.getMarkshelf_zoneFlag()+row+faceFlag+colFlag;
						String shelf_name = makeshelf.getMarkshelf_libName()+makeshelf.getMarkshelf_floorName()+"楼"+makeshelf.getMarkshelf_zoneName()+"区"+row+"排"+faceFlag+"面"+colFlag+"列书架";
						
						BookshelfEntity bookshelfEntity = new BookshelfEntity();
						bookshelfEntity.setLib_id("");
						bookshelfEntity.setShelf_id(shelf_id);
						bookshelfEntity.setShelf_name(shelf_name);
						bookshelfEntity.setLayercount(shelfLayerTotal);
						bookshelfEntity.setLayersort(0);
						bookshelfEntity.setFloorimage_url(null);
						bookshelfEntity.setShelfimage_url(null);
						bookshelfEntity.setShelfpoint(null);
						bookshelfEntity.setIssmartshelf(0);
						
						BookshelfEntity bookshelfEntity_bak = bookshelfDao.queryBookshelfById(bookshelfEntity);
						if(bookshelfEntity_bak != null){
							bookshelfDao.updateBookshelf(bookshelfEntity);
						}
						else{
							bookshelfDao.addBookshelf(bookshelfEntity);
						}
						
						BookshelfinfoEntity bookshelfinfoEntity = new BookshelfinfoEntity();
						bookshelfinfoEntity.setLib_id("");
						bookshelfinfoEntity.setShelf_id(shelf_id);
						bookshelfinfoDao.deleteBookshelfinfo(bookshelfinfoEntity);
						
						String info_value="";
						for(int k=1;k<=6;k++){
							if(k==1) info_value=makeshelf.getMarkshelf_libName();
							if(k==2) info_value=makeshelf.getMarkshelf_floorName();
							if(k==3) info_value=makeshelf.getMarkshelf_zoneName();
							if(k==4) info_value=row;
							if(k==5) info_value=faceFlag;
							if(k==6) info_value=colFlag;
							bookshelfinfoEntity.setInfo_type(k);
							bookshelfinfoEntity.setInfo_value(info_value);
							bookshelfinfoDao.addBookshelfinfo(bookshelfinfoEntity);
						}
						
						//处理书架层架
						for(int j=1;j<=shelfLayerTotal;j++){
							String shelflayer_barcode = shelf_id+j;
							String shelf_layer = shelf_name + j +"层";
							BookshelflayerEntity bookshelflayerEntity = new BookshelflayerEntity();
							bookshelflayerEntity.setLib_id("");
							bookshelflayerEntity.setShelf_id(shelf_id);
							bookshelflayerEntity.setShelf_layer(j);
							bookshelflayerEntity.setShelflayer_barcode(shelflayer_barcode);
							bookshelflayerEntity.setShelflayer_name(shelf_layer);
							BookshelflayerEntity bookshelflayerEntity_bak = bookshelflayerDao.queryBookshelflayerByid(bookshelflayerEntity);
							if(bookshelflayerEntity_bak != null){
								bookshelflayerDao.updateBookshelflayer(bookshelflayerEntity);
							}
							else{
								bookshelflayerDao.addBookshelflayer(bookshelflayerEntity);
							}
						}
						
					}
				}
	        } 
			re = makeshelfDao.addMakeShelfRecord(makeshelf);
		} catch (Exception e) {
			re = 0;
		}
		result.setState(re >= 1 ? true : false);
		result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		return result;
	}
	
	@Override
	public  ResultEntity deleteMakeShelfRecord(String req){
		int re = 0;
		ResultEntity result = new ResultEntity();
		try {
			JSONArray jsonarray = JSONArray.fromObject(req);
			List<MakeshelfEntity> list = (List<MakeshelfEntity>)JSONArray.toCollection(jsonarray, MakeshelfEntity.class); 
			for(MakeshelfEntity makeshelf:list){
				makeshelf = makeshelfDao.queryMakeShelfByid(makeshelf);
				List<BookshelfEntity> bookshelfs = new ArrayList<>();
				String rows[] = makeshelf.getMarkshelf_rowCode().split(",");
				for (String row : rows) {  
					Integer colNum = makeshelf.getMarkshelf_colNum();
					Integer colSize = makeshelf.getMarkshelf_colSize();
					String colFlag = "";
					for(int a=1;a<=colNum;a++){
						if(colSize == 1 && a < 10){
							colFlag = String.valueOf(a);
						}else if(colSize == 2 && a < 10){
							colFlag = "0"+a;
						}else if(colSize == 2 && a < 100){
							colFlag = String.valueOf(a);
						}else{
							continue;
						}
						
						Integer faceNum = makeshelf.getMarkshelf_faceNum();
						String faceFlag = "";
						for(int i= 1;i<=faceNum;i++){
							/*if(i==1) faceFlag = "1";
							if(i==2) faceFlag = "2";
							if(i==3) faceFlag = "3";
							if(i==4) faceFlag = "4";*/
							
							if(i==1) faceFlag = "A";
							if(i==2) faceFlag = "B";
							if(i==3) faceFlag = "C";
							if(i==4) faceFlag = "D";
							//处理书架
							String shelf_id = makeshelf.getMarkshelf_libFlag()+makeshelf.getMarkshelf_floorFlag()+makeshelf.getMarkshelf_zoneFlag()+row+faceFlag+colFlag;
							//String shelf_id = makeshelf.getMarkshelf_floorFlag()+makeshelf.getMarkshelf_zoneFlag()+row+faceFlag+colFlag;
							//String shelf_name = makeshelf.getMarkshelf_libName()+makeshelf.getMarkshelf_floorName()+"楼"+makeshelf.getMarkshelf_zoneName()+row+"列"+faceFlag+"面"+colFlag+"列书架";
							BookshelfEntity bookshelfEntity = new BookshelfEntity();
							bookshelfEntity.setLib_id("");
							bookshelfEntity.setShelf_id(shelf_id);
							BookshelfEntity bookshelfEntity_bak = bookshelfDao.queryBookshelfById(bookshelfEntity);
							if(bookshelfEntity_bak != null){
								re = bookshelfDao.deleteBookshelf(bookshelfEntity_bak);
							}
						}
					}
		        } 
			}
			
			re = makeshelfDao.deleteMakeShelf(list);
		} catch (Exception e) {
			re = 0;
		}
		result.setState(re >= 1 ? true : false);
		result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		return result;
	}
}
