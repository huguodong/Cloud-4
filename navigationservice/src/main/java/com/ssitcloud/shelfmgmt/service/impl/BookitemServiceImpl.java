package com.ssitcloud.shelfmgmt.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.shelfmgmt.dao.BibliosDao;
import com.ssitcloud.shelfmgmt.dao.BookitemDao;
import com.ssitcloud.shelfmgmt.entity.BibliosBean;
import com.ssitcloud.shelfmgmt.entity.BookitemEntity;
import com.ssitcloud.shelfmgmt.service.BookitemService;

@Service
public class BookitemServiceImpl implements BookitemService {

	@Resource
	private BookitemDao bookitemDao;
	
	@Resource
	private BibliosDao bibliosDao;
	
	@Override
	public ResultEntity importBookitem(String req) {
		ResultEntity result = new ResultEntity();
		int re = 0;
		try {
			JSONObject jsonObject =JSONObject.fromObject(req);
            String encoding="GBK";
            File file=new File(jsonObject.getString("file"));
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String	lineTxt = null;
                //条码号|ISBN|书名|作者|出版社|索书号|当前馆藏|分馆标识
                while((lineTxt = bufferedReader.readLine()) != null){
                	try{
                		String temp[] = lineTxt.split("\\|");
                    	BookitemEntity bookitem = new BookitemEntity();
                    	bookitem.setBook_barcode(temp[0]);
                    	//bookitem.setBook_uid("");
                    	//32
                    	if(temp[1] != null && temp[1].length() > 32){
                    		temp[1] =temp[1].substring(0, 32);
                    	}
                    	bookitem.setISBN(temp[1]);
                    	//200
                    	if(temp[2] != null && temp[2].length() > 200){
                    		temp[2] =temp[2].substring(0, 200);
                    	}
                    	bookitem.setTitle(temp[2]);
                    	//200
                    	if(temp[3] != null && temp[3].length() > 200){
                    		temp[3] =temp[3].substring(0, 200);
                    	}
                    	bookitem.setAuthor(temp[3]);
                    	//200
                    	if(temp[4] != null && temp[4].length() > 200){
                    		temp[4] =temp[4].substring(0, 200);
                    	}
                    	bookitem.setPublish(temp[4]);
                    	//255
                    	if(temp[5] != null && temp[5].length() > 255){
                    		temp[5] =temp[5].substring(0, 255);
                    	}
                    	bookitem.setCallNo(temp[5]);
                    	//bookitem.setShelflayer_barcode("");
                    	bookitem.setUpdate_uid_flag(1);
                    	bookitem.setStatemodcount(0);
                    	//255
                    	if(temp[6] != null && temp[6].length() > 255){
                    		temp[6] =temp[6].substring(0, 255);
                    	}
                    	bookitem.setLocation(temp[6]);
                    	//255
                    	if(temp[7] != null && temp[7].length() > 255){
                    		temp[7] =temp[7].substring(0, 255);
                    	}
                    	bookitem.setSubsidiary(temp[7]);
                    	Date now = new Date(); 
                    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    	//bookitem.setUploadtime(dateFormat.format(now));
                    	bookitem.setUpdatetime(dateFormat.format(now));
                		bookitem.setDevice_id("DHSYS");
                    	BookitemEntity bookitem_bak = bookitemDao.queryBookitemByCode(bookitem);
                    	if(bookitem_bak == null){
                    		bookitem.setState(5);
                    		bookitem.setUploadtime(dateFormat.format(now));
                    		bookitemDao.addBookitem(bookitem);
                    	}else{
                    		bookitem.setUploadtime("2000-01-01 00:00:00");
                    		bookitemDao.updateBookitem(bookitem);
                    	}
                	}catch(Exception e){
                		continue;
                	}
                }
                read.close();
                re = 1;
            }else{
            	//System.out.println("找不到指定的文件");
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
	
	@Override
	public ResultEntity importBookitemLocation(String req) {
		ResultEntity result = new ResultEntity();
		int re = 0;
		try {
			JSONObject jsonObject =JSONObject.fromObject(req);
            String encoding="UTF-8";
            File file=new File(jsonObject.getString("file"));
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String	lineTxt = null;
                //条码号|ISBN|书名|作者|出版社|索书号|当前馆藏|分馆标识
                //barcode|location|fenguan|callno|idx
                while((lineTxt = bufferedReader.readLine()) != null){
                	try{
                		String temp[] = lineTxt.split("\\|");
                		String idx = temp[4];
                		if(StringUtils.isEmpty(idx)){
                			continue;
                		}
                		BibliosBean biblios = new BibliosBean();
                		biblios.setItemCtrlNum(idx);
                		BibliosBean newBiblios = bibliosDao.queryMarcDataByItemCtrlNum(biblios);
                		if(newBiblios == null){
                			continue;
                		}
                    	BookitemEntity bookitem = new BookitemEntity();
                    	bookitem.setBook_barcode(temp[0]);
                    	String isbn = newBiblios.getISBN();
                    	if(isbn != null && isbn.length() > 32){
                    		isbn =isbn.substring(0, 32);
                    	}
                    	bookitem.setISBN(isbn);
                    	//200
                    	String title = newBiblios.getTitle();
                    	if(title != null && title.length() > 200){
                    		title =title.substring(0, 200);
                    	}
                    	bookitem.setTitle(title);
                    	//200
                    	String author = newBiblios.getAuthor();
                    	if(author != null && author.length() > 200){
                    		author =author.substring(0, 200);
                    	}
                    	bookitem.setAuthor(author);
                    	//200
                    	String publish = newBiblios.getPublish();
                    	if(publish != null && publish.length() > 200){
                    		publish =publish.substring(0, 200);
                    	}
                    	bookitem.setPublish(publish);
                    	//255
                    	String callNo = newBiblios.getCallNo();
                    	if(callNo != null && callNo.length() > 255){
                    		callNo =callNo.substring(0, 255);
                    	}
                    	bookitem.setCallNo(callNo);
                    	//bookitem.setShelflayer_barcode("");
                    	bookitem.setUpdate_uid_flag(1);
                    	bookitem.setStatemodcount(0);
                    	//255
                    	if(temp[1] != null && temp[1].length() > 255){
                    		temp[1] =temp[1].substring(0, 255);
                    	}
                    	bookitem.setLocation(temp[1]);
                    	//255
                    	if(temp[2] != null && temp[2].length() > 255){
                    		temp[2] =temp[2].substring(0, 255);
                    	}
                    	bookitem.setSubsidiary(temp[2]);
                    	Date now = new Date(); 
                    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    	//bookitem.setUploadtime(dateFormat.format(now));
                    	bookitem.setUpdatetime(dateFormat.format(now));
                		bookitem.setDevice_id("DHSYS");
                    	BookitemEntity bookitem_bak = bookitemDao.queryBookitemByCode(bookitem);
                    	if(bookitem_bak == null){
                    		bookitem.setState(5);
                    		bookitem.setUploadtime(dateFormat.format(now));
                    		bookitemDao.addBookitem(bookitem);
                    	}else{
                    		bookitem.setUploadtime("2000-01-01 00:00:00");
                    		bookitemDao.updateBookitem(bookitem);
                    	}
                	}catch(Exception e){
                		continue;
                	}
                }
                read.close();
                re = 1;
            }else{
            	//System.out.println("找不到指定的文件");
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

	@Override
	public ResultEntity queryBookitemByCode(String req) {
		ResultEntity result = new ResultEntity();
		try {
			BookitemEntity bookitemEntity = bookitemDao.queryBookitemByCode(JsonUtils.fromJson(req, BookitemEntity.class));
			if(bookitemEntity != null){
				result.setState(true);
				result.setResult(bookitemEntity);
			}else{
				result.setState(false);
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@Override
	public ResultEntity opacQueryBookitem(String req) {
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			JSONObject jsonObject =JSONObject.fromObject(req);
			map.put("json", jsonObject.getString("json"));
			map.put("page", jsonObject.getString("page"));
			PageEntity pageEntity = bookitemDao.opacQueryBookitem(map);
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

}
