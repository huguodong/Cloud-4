package com.ssitcloud.dblib.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.dao.BibliosDao;
import com.ssitcloud.dblib.dao.BookItemDao;
import com.ssitcloud.dblib.entity.BibliosAndBook;
import com.ssitcloud.dblib.entity.BibliosEntity;
import com.ssitcloud.dblib.entity.page.BibliosPageEntity;
import com.ssitcloud.dblib.service.BibliosService;

@Service
public class BibliosServiceImpl implements BibliosService{
	@Resource
	private BibliosDao bibliosDao;
	
	@Resource
	private BookItemDao bookItemDao;

	@Override
	public int insertBiblios(BibliosEntity bibliosEntity) {
		return bibliosDao.insertBiblios(bibliosEntity);
	}

	@Override
	public int deleteBiblios(BibliosEntity bibliosEntity) {
		return bibliosDao.deleteBiblios(bibliosEntity);
	}

	@Override
	public int updateBiblios(BibliosEntity bibliosEntity) {
		return bibliosDao.updateBiblios(bibliosEntity);
	}

	@Override
	public BibliosEntity queryBiblios(BibliosEntity bibliosEntity) {
		return bibliosDao.queryBiblios(bibliosEntity);
	}

	@Override
	public List<BibliosEntity> queryBibliosList(BibliosEntity bibliosEntity) {
		return bibliosDao.queryBibliosList(bibliosEntity);
	}

	@Override
	public BibliosPageEntity queryBibliosListByPage(
			BibliosPageEntity bibliosPageEntity) {
		return bibliosDao.queryBibliosListByPage(bibliosPageEntity);
	}

	@Override
	public BibliosEntity queryBibliosForBCAndLib(Map<String, Object> param) {
		if(param.get("book_barcode") == null 
				|| param.get("lib_id") == null){
			return null;
		}
		return bibliosDao.queryBibliosForBCAndLib(param);
	}
	
	@Override
	public List<BibliosEntity> queryAllBiblios() {
		// TODO Auto-generated method stub
		return bibliosDao.queryAllBiblios();
	}

	@Override
	public BibliosEntity queryBibliosByISBN(BibliosEntity biblios) {
		// TODO Auto-generated method stub
		return bibliosDao.queryBibliosByISBN(biblios);
	}

	@Override
	public BibliosEntity queryBibliosByTitleAndAuthor(BibliosEntity biblios) {
		// TODO Auto-generated method stub
		return bibliosDao.queryBibliosByTitleAndAuthor(biblios);
	}

	
	@Override
	public int updateBibliosByISBN(BibliosEntity biblios) {
		// TODO Auto-generated method stub
		return bibliosDao.updateBibliosByISBN(biblios);
	}

	@Override
	public int deleteBibliosById(List<BibliosEntity> list) {
		// TODO Auto-generated method stub
		return bibliosDao.deleteBibliosById(list);
	}

	@Override
	public int addBiblios(BibliosEntity biblios) {
		// TODO Auto-generated method stub
		return bibliosDao.addBiblios(biblios);
	}

	@Override
	public BibliosEntity queryBibliosByIsbnMultiCondition(BibliosEntity biblios) {
		return bibliosDao.queryBibliosByIsbnMultiCondition(biblios);
	}

	@Override
	public BibliosEntity queryBibliosByTitleAuthorPublish(BibliosEntity biblios) {
		return bibliosDao.queryBibliosByTitleAuthorPublish(biblios);
	}
	
	
	@Override
	public ResultEntity uploadBiblios(CommonsMultipartFile commonsMultipartFile,String req){
		ResultEntity entity = new ResultEntity();
		try{
			UploadBibliosUtils bibliosUtils = new UploadBibliosUtils(bookItemDao,bibliosDao);
			bibliosUtils.parseUploadFile(commonsMultipartFile,req);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("successNum", bibliosUtils.getSuccessNum());
			jsonObject.put("failNum", bibliosUtils.getFailNum());
			jsonObject.put("errorLog", bibliosUtils.getErrorLogs());
			entity.setResult(jsonObject);
			entity.setState(true);
		}catch(Exception e){
			e.printStackTrace();
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			//ExceptionHelper.afterException(entity, methodName, e);
			entity.setMessage(Const.FAILED);
			entity.setState(true);
			entity.setRetMessage(methodName+"()异常:"+e.getMessage());
			return entity;
		}
		return entity;
	}
}
