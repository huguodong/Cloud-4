package com.ssitcloud.shelfmgmt.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.shelfmgmt.dao.BookitemDao;
import com.ssitcloud.shelfmgmt.entity.BookitemEntity;
import com.ssitcloud.shelfmgmt.service.BookitemService;

@Service
public class BookitemServiceImpl implements BookitemService {

	@Resource
	private BookitemDao bookitemDao;
	
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
                while((lineTxt = bufferedReader.readLine()) != null){
                	String temp[] = lineTxt.split("\\|");
                	BookitemEntity bookitem = new BookitemEntity();
                	bookitem.setBook_barcode(temp[0]);
                	bookitem.setBook_uid(temp[1].toUpperCase());
                	bookitem.setShelflayer_barcode(temp[2]);
                	bookitem.setState(1);
                	bookitem.setUpdate_uid_flag(1);
                	bookitem.setStatemodcount(0);
                	Date now = new Date(); 
                	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                	bookitem.setUpdatetime(dateFormat.format( now ));
                	BookitemEntity bookitem_bak = bookitemDao.queryBookitemByCode(bookitem);
                	if(bookitem_bak == null){
                		bookitemDao.addBookitem(bookitem);
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

}
