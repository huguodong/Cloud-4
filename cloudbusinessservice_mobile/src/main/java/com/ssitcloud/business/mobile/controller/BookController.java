package com.ssitcloud.business.mobile.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.service.BorrowServiceI;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.ReaderCardEntity;

@Controller
@RequestMapping("/book")
public class BookController {
	@Autowired
	private BorrowServiceI borrowService;
	
	@RequestMapping("/renew")
	@ResponseBody
	public ResultEntity renew(HttpServletRequest request){
		ResultEntity r = new ResultEntity();
		String cardJson = request.getParameter("json");
		String booksn = request.getParameter("sn");
		if(cardJson == null || booksn == null){
			
			r.setValue(false, "args too short");
			return r;
		}
		try{
			ReaderCardEntity card = JsonUtils.fromJson(cardJson, ReaderCardEntity.class);
			return borrowService.renewBook(card , booksn);
		}catch(Exception e){
			r.setMessage(e.getMessage());
			return r;
		}
	}
	
	@RequestMapping("/reservation")
	@ResponseBody
	public ResultEntity reservation(HttpServletRequest request){
		String json = request.getParameter("json");
		String iddata = request.getParameter("idData");
		try{
			Map<String, Object> param = JsonUtils.fromJson(json, Map.class);
			Map<String, Object> idData = JsonUtils.fromJson(iddata, Map.class);
			return borrowService.reservationBook(param, idData);
		}catch (Exception e) {
			LogUtils.info(getClass()+" reservation",e);
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setState(false);
			return resultEntity;
		}
	}
	
	@RequestMapping("/inReservation")
	@ResponseBody
	public ResultEntity inReservation(HttpServletRequest request){
		String json = request.getParameter("json");
		try{
			Map<String, Object> param = JsonUtils.fromJson(json, Map.class);
			return borrowService.inReservationBook(param);
		}catch (Exception e) {
			LogUtils.info(getClass()+" reservation",e);
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setState(false);
			return resultEntity;
		}
	}
	
	@RequestMapping("/reservationList")
	@ResponseBody
	public ResultEntity reservationList(HttpServletRequest request){
		String json = request.getParameter("json");
		try{
			Map<String, Object> param = JsonUtils.fromJson(json, Map.class);
			return borrowService.reservationList(param);
		}catch (Exception e) {
			LogUtils.info(getClass()+" reservation",e);
			ResultEntity resultEntity = new ResultEntity();
			resultEntity.setState(false);
			return resultEntity;
		}
	}
}
