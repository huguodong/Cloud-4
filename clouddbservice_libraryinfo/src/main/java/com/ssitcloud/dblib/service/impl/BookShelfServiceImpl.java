package com.ssitcloud.dblib.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.utils.LogUtils;
import com.ssitcloud.dblib.dao.BookShelfDao;
import com.ssitcloud.dblib.dao.BookShelfInfoDao;
import com.ssitcloud.dblib.dao.BookShelfLayerDao;
import com.ssitcloud.dblib.entity.BookShelfEntity;
import com.ssitcloud.dblib.entity.BookShelfInfoEntity;
import com.ssitcloud.dblib.entity.BookShelfLayerEntity;
import com.ssitcloud.dblib.service.BookShelfService;

@Service
public class BookShelfServiceImpl implements BookShelfService{
	
	@Resource
	private BookShelfDao bookShelfDao;
	
	@Resource
	private  BookShelfLayerDao bookshelflayerDao;
	
	@Resource
	private  BookShelfInfoDao bookshelfinfoDao;
	
	@Override
	public PageEntity queryAllBookshelf(Map<String, String> map) {
		// TODO Auto-generated method stub
		return bookShelfDao.queryAllBookshelf(map);
	}
	
	public BookShelfEntity queryBookshelfById(BookShelfEntity bookshelfEntity) {
		// TODO Auto-generated method stub
		return bookShelfDao.queryBookshelfById(bookshelfEntity);
	}

	@Override
	public int updateBookshelf(BookShelfEntity bookshelfEntity,List<BookShelfInfoEntity> list) {
		// TODO Auto-generated method stub
		int re = bookShelfDao.updateBookshelf(bookshelfEntity);
		if(re == 1){
			for(BookShelfInfoEntity shelfinfo : list){
				re = bookshelfinfoDao.updateBookshelfinfo(shelfinfo);
			}
		}
		return re;
	}

	@Override
	public int deleteBookshelf(BookShelfEntity bookshelfEntity) {
		return bookShelfDao.deleteBookshelf(bookshelfEntity);
	}

	@Override
	public int addBookshelf(BookShelfEntity bookshelfEntity,List<BookShelfInfoEntity> list) {
		// TODO Auto-generated method stub
		int re = bookShelfDao.addBookshelf(bookshelfEntity);
		if(re == 1){
			for(BookShelfInfoEntity shelfinfo : list){
				re = bookshelfinfoDao.addBookshelfinfo(shelfinfo);
			}
		}
		return re;
	}
	
	public int updateShelfimage(BookShelfEntity bookshelfEntity){
		return bookShelfDao.updateShelfimage(bookshelfEntity);
	}
	
	public int updateFloorimage(BookShelfEntity bookshelfEntity){
		return  bookShelfDao.updateFloorimage(bookshelfEntity);
	}
	
	public int uploadPoint(String lib_id,String fileName,int point_type){
		try {
            //String encoding="uft8";
            File file=new File(fileName);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file)/*,encoding*/);//考虑到编码格式
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
                		BookShelfEntity bookshelfEntity = new BookShelfEntity();
                		bookshelfEntity.setLib_id(lib_id);
                		bookshelfEntity.setShelf_id(shelf_id);
                		bookshelfEntity.setShelfPoint(dot);
                		bookShelfDao.updatePoint(bookshelfEntity);
                	}
                	else if(point_type == 2){
                		BookShelfLayerEntity bookshelflayerEntity = new BookShelfLayerEntity();
                		bookshelflayerEntity.setLib_id(lib_id);
                		bookshelflayerEntity.setShelflayer_barcode(shelflayer_barcode);
                		bookshelflayerEntity = bookshelflayerDao.queryBookshelflayerByid(bookshelflayerEntity);
                		if(bookshelflayerEntity != null){
                			BookShelfEntity bookshelfEntity = new BookShelfEntity();
                			bookshelfEntity.setLib_id(lib_id);
                			bookshelfEntity.setShelf_id(bookshelflayerEntity.getShelf_id());
                			bookshelfEntity.setShelfPoint(dot);
                			bookShelfDao.updatePoint(bookshelfEntity);
                		}
                	}
                }
                read.close();
                return 1;
            }else{
            	System.out.println("找不到指定的文件");
            	return 0;
            }
		}catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			return 0;
		}
	}
}
