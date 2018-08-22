package com.ssitcloud.dblib.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.BibliosDao;
import com.ssitcloud.dblib.dao.BookItemDao;
import com.ssitcloud.dblib.entity.BibliosEntity;
import com.ssitcloud.dblib.entity.BookItemEntity;
import com.ssitcloud.dblib.entity.page.BookItemPageEntity;
import com.ssitcloud.dblib.entity.page.BookUnionEntity;
import com.ssitcloud.dblib.service.BookItemService;

@Service
public class BookItemServiceImpl implements BookItemService{
	
	@Resource
	private BookItemDao bookItemDao;
	
	@Resource
	private BibliosDao bibliosDao;
	

	@Override
	public int insertBookItem(BookItemEntity bookItemEntity) {
		if(bookItemEntity.getNowlib_idx() == null){
			bookItemEntity.setNowlib_idx(bookItemEntity.getLib_idx());
		}
		return bookItemDao.insertBookItem(bookItemEntity);
	}

	@Override
	public int deleteBookItem(BookItemEntity bookItemEntity) {
		return bookItemDao.deleteBookItem(bookItemEntity);
	}

	@Override
	public int updateBookItem(BookItemEntity bookItemEntity) {
		return bookItemDao.updateBookItem(bookItemEntity);
	}

	@Override
	public BookItemEntity queryBookItem(BookItemEntity bookItemEntity) {
		return bookItemDao.queryBookItem(bookItemEntity);
	}

	@Override
	public List<BookItemEntity> queryBookItemList(BookItemEntity bookItemEntity) {
		return bookItemDao.queryBookItemList(bookItemEntity);
	}

	@Override
	public BookItemPageEntity queryBookItemListByPage(
			BookItemPageEntity bookItemPageEntity) {
		return bookItemDao.queryBookItemListByPage(bookItemPageEntity);
	}
	
	@Override
	public List<BookItemEntity> queryAllBookitem(BookItemEntity bookitem) {
		return bookItemDao.queryAllBookitem(bookitem);
	}

	@Override
	public BookItemEntity queryBookitemByCode(BookItemEntity bookitem) {
		return bookItemDao.queryBookitemByCode(bookitem);
	}
	
	@Override
	public BookUnionEntity queryAllBookitemAndBookInfoByCode(BookItemEntity bookitem) {
		return bookItemDao.queryAllBookitemAndBookInfoByCode(bookitem);
	}

	@Override
	public int updateBookitem(BookItemEntity bookitem) {
		return bookItemDao.updateBookitem(bookitem);
	}

	@Override
	public int deleteBookitemById(List<BookItemEntity> list) {
		return bookItemDao.deleteBookitemById(list);
	}

	@Override
	public int addBookitem(BookItemEntity bookitem) {
		return bookItemDao.addBookitem(bookitem);
	}
	
	@Override
	public int importBookitem(Integer lib_idx,String fileName){
		try {
            String encoding="GBK";
            File file=new File(fileName);
            if(file.isFile() && file.exists()){
            	 //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String	lineTxt = null;
                //条码号|ISBN|书名|作者|出版社|分类号|索书号|当前馆藏|分馆标识
                while((lineTxt = bufferedReader.readLine()) != null){
                	try{
                		String temp[] = lineTxt.split("\\|",9);
                		
                    	String bookbarcode = temp[0];
                    	//bookitem.setBook_uid("");
                    	//32
                    	if(temp[1] != null && temp[1].length() > 32){
                    		temp[1] =temp[1].substring(0, 32);
                    	}
                    	String isbn = temp[1];
                    	//200
                    	if(temp[2] != null && temp[2].length() > 200){
                    		temp[2] =temp[2].substring(0, 200);
                    	}
                    	String title = temp[2];
                    	//200
                    	if(temp[3] != null && temp[3].length() > 200){
                    		temp[3] =temp[3].substring(0, 200);
                    	}
                    	String author = temp[3];
                    	//200
                    	if(temp[4] != null && temp[4].length() > 200){
                    		temp[4] =temp[4].substring(0, 200);
                    	}
                    	String publish = temp[4];
                    	//255
                    	if(temp[5] != null && temp[5].length() > 255){
                    		temp[5] =temp[5].substring(0, 255);
                    	}
                    	String classNo = temp[5];
                    	if(temp[6] != null && temp[6].length() > 255){
                    		temp[6] =temp[6].substring(0, 255);
                    	}
                    	String callNo = temp[6];
                    	//255
                    	if(temp[7] != null && temp[7].length() > 255){
                    		temp[7] =temp[7].substring(0, 255);
                    	}
                    	String location = temp[7];
                    	//255
                    	if(temp[8] != null && temp[8].length() > 255){
                    		temp[8] =temp[8].substring(0, 255);
                    	}
                    	String subsidiary = temp[8];
                    	
                    	
                    	BibliosEntity biblios = new BibliosEntity();
                    	BookItemEntity bookitem = new BookItemEntity();
                    	
                    	biblios.setISBN(isbn);
	                	biblios.setTitle(title);
	                	biblios.setAuthor(author);
	                	biblios.setClassNo(classNo);
	                	biblios.setPublish(publish);
                    	
	                	BibliosEntity bibliosExt = null;
	                	Integer bibliosIdx = 0;
	                	if(!StringUtils.isEmpty(isbn)){
	                		bibliosExt = bibliosDao.queryBibliosByIsbnMultiCondition(biblios);
	                		/*if(bibliosExt == null){
	                			if(!StringUtils.isEmpty(title) && !StringUtils.isEmpty(author))
	                			bibliosExt = bibliosDao.queryBibliosByTitleAndAuthor(biblios);
	                		}*/
	                	}else{
	                		if(!StringUtils.isEmpty(title) && !StringUtils.isEmpty(author))
	                			bibliosExt = bibliosDao.queryBibliosByTitleAndAuthor(biblios);
	                	}
	                	
	                	if(bibliosExt == null){
	                		bibliosDao.insertBiblios(biblios);
	                		bibliosIdx = biblios.getBib_idx();
	                	}else{
	                		bibliosIdx = bibliosExt.getBib_idx();
	                	}
	                	bookitem.setCallNo(callNo);
	                	bookitem.setBook_barcode(bookbarcode);
	                	bookitem.setLocation(location);
	                	bookitem.setLocation(subsidiary);
	                	bookitem.setUpdate_uid_flag(1);
                    	bookitem.setStateModCount(0);
	                	bookitem.setBib_idx(bibliosIdx);
	                	bookitem.setLib_idx(lib_idx);
	                	
	                	//数据库不允许插入空
	                	bookitem.setBook_uid("");
	                	
                    	Date now = new Date(); 
                    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    	//bookitem.setUploadtime(dateFormat.format(now));
                    	bookitem.setUpdatetime(dateFormat.format(now));
                		//bookitem.setDevice_id("DHSYS");
                		BookItemEntity bookitem_bak = bookItemDao.queryBookitemByCode(bookitem);
                    	if(bookitem_bak == null){
                    		bookitem.setState(5);
                    		bookItemDao.addBookitem(bookitem);
                    	}else{
                    		bookItemDao.updateBookitem(bookitem);
                    	}
                	}catch(Exception e){
                		continue;
                	}
                }
                read.close();
                return 1;
            }else{
            	System.out.println("文件不存在!");
            	return 0;
            }
		}catch (Exception e) {
			System.out.println("导入失败"+e);
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateBookitemByCodeAndLibId(BookItemEntity bookitem) {
		return bookItemDao.updateBookitemByCodeAndLibId(bookitem);
	}
	
	@Override
	public List<BookItemEntity> queryAllBookitemByLibId(BookItemEntity bookitem) {
		return bookItemDao.queryAllBookitemByLibId(bookitem);
	}

	@Override
	public List<BookUnionEntity> queryBookitemAndBookInfo(Map<String, Object> map) {
		Integer pageCurrent = (Integer) map.get("pageCurrent");
		Integer pageSize = (Integer) map.get("pageSize");
		map.remove("pageCurrent");
		map.remove("pageSize");
		if(pageCurrent != null && pageSize != null){
			Integer limitS = pageCurrent>0?(pageCurrent-1)*pageSize:0;
			Integer limitE = pageSize>0?pageSize:15;
			map.put("limitS", limitS);
			map.put("limitE", limitE);
		}
		return bookItemDao.queryBookitemAndBookInfo(map);
	}

	@Override
	public BookUnionEntity queryBookitemAndBookInfobyIdx(Integer bookitemIdx) {
		if(bookitemIdx != null){
			return bookItemDao.queryBookitemAndBookInfobyIdx(bookitemIdx);
		}
		return null;
	}
	
	@Override
	public List<BookUnionEntity> queryAllBookitemAndBookInfo(BookItemEntity bookitem) {
		return bookItemDao.queryAllBookitemAndBookInfo(bookitem);
	}
	
	@Override
	public BookItemEntity queryBookItemByLibIdxAndBookBarcode(BookItemEntity bookitem) {
		return bookItemDao.queryBookItemByLibIdxAndBookBarcode(bookitem);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity queryBookItemAndBibliosInfo(String req) {
		ResultEntity resultEntity = new ResultEntity();
		
		Map<String, Object> param = new HashMap<String, Object>();
		//先查询有多少条，
		Map<String, Object> reqMap = JsonUtils.fromJson(req, Map.class);
		
		param.put("bookitem_idx", reqMap.get("bookitem_idx"));
		List<Map<String, Object>> list = bookItemDao.queryBookItemAndBibliosInfo(param);
		
		String idx = "0";
		
		if (list!=null && list.size()==0) {
		}else{
			//获取最大的数据
			Map<String, Object> map = list.get(list.size() - 1);
			idx = map.get("bookitem_idx") + "";
		}
		
		resultEntity.setValue(true, idx, "", list);
		
		return resultEntity;
	}

	@Override
	public ResultEntity queryBookItemByUnion(String req) {
		BookUnionEntity bookUnionEntity = new BookUnionEntity();
		ResultEntity resultEntity = new ResultEntity();
		if(req != null && req.length() > 0){
			bookUnionEntity = JsonUtils.fromJson(req, BookUnionEntity.class);
		}
		List<BookUnionEntity> bookUnionEntities = bookItemDao.queryBookItemByUnion(bookUnionEntity);
		resultEntity.setResult(bookUnionEntities);
		resultEntity.setState(true);
		return resultEntity;
	}

	/**
	 * 图书推荐时判断是否在馆
	 */
	@Override
	public ResultEntity queryBookInfoForRecommend(String req) {
		ResultEntity resultEntity = new ResultEntity();
		if(!JSONUtils.mayBeJSON(req)){
			resultEntity.setState(false);
			resultEntity.setMessage("参数传入错误");
			return resultEntity;
		}
		BookItemEntity bookItemEntity = JsonUtils.fromJson(req, BookItemEntity.class);
		List<BookItemEntity> list=bookItemDao.queryBookInfoForRecommend(bookItemEntity);
		resultEntity.setResult(list);
		resultEntity.setState(true);
		return resultEntity;
	}
	
	
	/**
	 *  更新馆藏图书状态
	 * @param bookItemEntity
	 * @return
	 */
	public	int updateState(BookItemEntity bookItemEntity){
		return bookItemDao.updateState(bookItemEntity);
	}
	
}
