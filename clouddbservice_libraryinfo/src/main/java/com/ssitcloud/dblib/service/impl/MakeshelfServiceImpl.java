package com.ssitcloud.dblib.service.impl;

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
import com.ssitcloud.dblib.common.utils.ExceptionHelper;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.BookShelfDao;
import com.ssitcloud.dblib.dao.BookShelfInfoDao;
import com.ssitcloud.dblib.dao.BookShelfLayerDao;
import com.ssitcloud.dblib.dao.MakeshelfDao;
import com.ssitcloud.dblib.entity.BookShelfEntity;
import com.ssitcloud.dblib.entity.BookShelfInfoEntity;
import com.ssitcloud.dblib.entity.BookShelfLayerEntity;
import com.ssitcloud.dblib.entity.MakeshelfEntity;
import com.ssitcloud.dblib.service.MakeshelfService;


@Service
public class MakeshelfServiceImpl implements MakeshelfService {

	@Resource
	private BookShelfLayerDao bookshelflayerDao;
	
	@Resource
	private  BookShelfDao bookshelfDao;
	
	@Resource
	private  BookShelfInfoDao bookshelfinfoDao;
	
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
						if(i==1) faceFlag = "A";
						if(i==2) faceFlag = "B";
						if(i==3) faceFlag = "C";
						if(i==4) faceFlag = "D";
						Integer shelfLayerTotal = makeshelf.getMarkshelf_shelfLayerNum();
						//处理书架
						String shelf_id = makeshelf.getMarkshelf_libFlag()+makeshelf.getMarkshelf_floorFlag()+makeshelf.getMarkshelf_zoneFlag()+row+faceFlag+colFlag;
						String shelf_name = makeshelf.getMarkshelf_libName()+makeshelf.getMarkshelf_floorName()+"楼"+makeshelf.getMarkshelf_zoneName()+"区"+row+"排"+faceFlag+"面"+colFlag+"列书架";
						
						BookShelfEntity bookshelfEntity = new BookShelfEntity();
						bookshelfEntity.setLib_id(makeshelf.getMarkshelf_libId());
						bookshelfEntity.setShelf_id(shelf_id);
						bookshelfEntity.setShelf_name(shelf_name);
						bookshelfEntity.setLayerCount(shelfLayerTotal);
						bookshelfEntity.setLayerSort(0);
						bookshelfEntity.setFloorImage_url(null);
						bookshelfEntity.setFloorImage_url(null);
						bookshelfEntity.setShelfPoint(null);
						bookshelfEntity.setIsSmartShelf(0);
						
						BookShelfEntity bookshelfEntity_bak = bookshelfDao.queryBookshelfById(bookshelfEntity);
						if(bookshelfEntity_bak != null){
							bookshelfDao.updateBookshelf(bookshelfEntity);
						}
						else{
							bookshelfDao.addBookshelf(bookshelfEntity);
						}
						
						BookShelfInfoEntity bookshelfinfoEntity = new BookShelfInfoEntity();
						bookshelfinfoEntity.setLib_id(makeshelf.getMarkshelf_libId());
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
							BookShelfLayerEntity bookshelflayerEntity = new BookShelfLayerEntity();
							bookshelflayerEntity.setLib_id(makeshelf.getMarkshelf_libId());
							bookshelflayerEntity.setShelf_id(shelf_id);
							bookshelflayerEntity.setShelf_layer(j);
							bookshelflayerEntity.setShelflayer_barcode(shelflayer_barcode);
							bookshelflayerEntity.setShelflayer_name(shelf_layer);
							BookShelfLayerEntity bookshelflayerEntity_bak = bookshelflayerDao.queryBookshelflayerByid(bookshelflayerEntity);
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
				List<BookShelfEntity> bookshelfs = new ArrayList<>();
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
							if(i==1) faceFlag = "A";
							if(i==2) faceFlag = "B";
							if(i==3) faceFlag = "C";
							if(i==4) faceFlag = "D";
							//处理书架
							String shelf_id = makeshelf.getMarkshelf_libFlag()+makeshelf.getMarkshelf_floorFlag()+makeshelf.getMarkshelf_zoneFlag()+row+faceFlag+colFlag;
							//String shelf_name = makeshelf.getMarkshelf_libName()+makeshelf.getMarkshelf_floorName()+"楼"+makeshelf.getMarkshelf_zoneName()+row+"列"+faceFlag+"面"+colFlag+"列书架";
							BookShelfEntity bookshelfEntity = new BookShelfEntity();
							bookshelfEntity.setLib_id(makeshelf.getMarkshelf_libId());
							bookshelfEntity.setShelf_id(shelf_id);
							BookShelfEntity bookshelfEntity_bak = bookshelfDao.queryBookshelfById(bookshelfEntity);
							if(bookshelfEntity_bak != null){
								bookshelfDao.deleteBookshelf(bookshelfEntity_bak);
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
