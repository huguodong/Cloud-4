package com.ssitcloud.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.common.util.StringUtil;
import com.ssitcloud.common.util.YhSocket;
import com.ssitcloud.navigation.entity.NavigationInfoEntity;
import com.ssitcloud.navigation.service.NavigationService;
import com.ssitcloud.order.dao.OrderDao;
import com.ssitcloud.order.entity.OrderEntity;
import com.ssitcloud.order.entity.page.OrderPageEntity;
import com.ssitcloud.order.service.OrderViewService;
import com.ssitcloud.shelfmgmt.dao.BookitemDao;
import com.ssitcloud.shelfmgmt.entity.BookitemEntity;

@Service
public class OrderViewServiceImpl implements OrderViewService {
	@Resource
	private OrderDao orderDao;
	
	@Resource
	BookitemDao bookitemDao;
	
	@Resource
	private NavigationService navigationService;

	@Override
	public List<OrderEntity> queryAllOrder(String req) {
		OrderEntity order = null;
		if (StringUtil.isEmpty(req)) {
			order = new OrderEntity();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			order = (OrderEntity) JSONObject.toBean(json, OrderEntity.class);
		}
		return orderDao.queryAllOrder(order);
	}

	@Override
	public OrderPageEntity queryOrderByParam(String req) {
		OrderPageEntity order = null;
		if (StringUtil.isEmpty(req)) {
			order = new OrderPageEntity();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			order = (OrderPageEntity) JSONObject.toBean(json, OrderPageEntity.class);
		}
		return orderDao.queryOrderByParam(order);
	}
	
	@Override
	public OrderEntity queryOrderById(String req) {
		OrderEntity order = null;
		if (StringUtil.isEmpty(req)) {
			order = new OrderEntity();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			order = (OrderEntity) JSONObject.toBean(json, OrderEntity.class);
		}
		return orderDao.queryOrderById(order.getIdx());
	}

	@Override
	public ResultEntity updateOrder(String req) {
		ResultEntity resultEntity = new ResultEntity();
		OrderEntity order = null;
		if (StringUtil.isEmpty(req)) {
			order = new OrderEntity();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			order = (OrderEntity) JSONObject.toBean(json, OrderEntity.class);
		}
		int num = orderDao.updateOrder(order);
		if(num==1){
			resultEntity.setState(true);
		}
		return resultEntity;
	}

	@Override
	public ResultEntity addOrder(String req) {
		ResultEntity resultEntity = new ResultEntity();
		OrderEntity order = null;
		if (StringUtil.isEmpty(req)) {
			order = new OrderEntity();
		} else {
			JSONObject json = JSONObject.fromObject(req);
			order = (OrderEntity) JSONObject.toBean(json, OrderEntity.class);
		}
		int num = orderDao.addOrder(order);
		if(num==1){
			resultEntity.setState(true);
		}
		return resultEntity;
	}

	@Override
	public ResultEntity controlShelf(String req) {
		ResultEntity resultEntity = new ResultEntity();
		//OrderEntity order = null;
		List<OrderEntity> list = null;
		try{
			//order = JsonUtils.fromJson(req, OrderEntity.class);
			list =JsonUtils.fromJson(req, new TypeReference<List<OrderEntity>>() {});
		}catch(Exception e){
			resultEntity.setState(false);
			resultEntity.setMessage("请求参数格式有误！");
			return resultEntity;
		}
		
		for(OrderEntity order : list){
			if(order == null || order.getBook_barcode() == null || order.getReaderid() == null){
				resultEntity.setState(false);
				resultEntity.setMessage("请求参数格式有误！");
				return resultEntity;
			}
			
			String bookBarCode = order.getBook_barcode();
			BookitemEntity et = new BookitemEntity();
			et.setBook_barcode(bookBarCode);
			BookitemEntity bk = bookitemDao.queryBookitemByCode(et);
			if(bk == null){
				resultEntity.setState(false);
				resultEntity.setMessage("图书条码号("+bookBarCode+")有误！");
				return resultEntity;
			}
			
		}
		
		for(OrderEntity order : list){
			orderDao.addOrder(order);
		}
		
		String bookBarCode = list.get(0).getBook_barcode();
		BookitemEntity et = new BookitemEntity();
		et.setBook_barcode(bookBarCode);
		BookitemEntity bookitem = bookitemDao.queryBookitemByCode(et);
		
		Map<String,Object> ctm = new HashMap<>();
		ctm.put("shelflayerBarcode", bookitem.getShelflayer_barcode());
		String title = bookitem.getTitle()==null?"信息缺失":bookitem.getTitle();
		
		BookitemEntity bookitemEntity = navigationService.queryBookInfo(bookBarCode);
		if(bookitemEntity == null){
			return resultEntity;
		}
		
		NavigationInfoEntity navigationInfoEty = navigationService.queryInfoByParam(bookitemEntity);
		int flag = 0;
		int qNumber = 0;
		int lNumber = 0;
		int lName = 0;
		int aCtLNumber = 0;
		String ip ="";
		Integer port = 0;
		if(navigationInfoEty != null){
			String[] locations = null;
			String info = navigationInfoEty.getInfo_value();
			if(info != null){
				locations = info.split(",");
			}
			//控制密集书代码
			ip = navigationInfoEty.getShelf_ip();
			port = navigationInfoEty.getShelf_port();
			if(ip != null && port != null){
				if(locations.length == 6 && StringUtil.hasLength(locations[0]) && StringUtil.hasLength(locations[1]) && StringUtil.hasLength(locations[2]) && StringUtil.hasLength(locations[3]) && StringUtil.hasLength(locations[5])){
					qNumber = Integer.parseInt(locations[1]);
					lNumber = Integer.parseInt(locations[2]);
					aCtLNumber = lNumber;
					lName = Integer.parseInt(locations[3]);
					flag = 1;
					int JNumber = Integer.parseInt(locations[5]);
					int CNumber = navigationInfoEty.getShelf_layer();
					int Ce = Integer.parseInt(locations[4]);
					
					if(qNumber==1||qNumber==3){
						if(Ce==1){//右侧
							lNumber = lNumber+1;
						}
					}
					
					if(qNumber==2||qNumber==4){
						if(Ce==0){//左侧
								lNumber = lNumber+1;
							}
					}
					
					/*
					String JStr = "";
					if(JNumber<10) JStr = "00"+ JNumber;
					else if(JNumber<100) JStr = "0"+ JNumber;
					else JStr = String.valueOf(JNumber);
					String cBarCode = locations[0]+locations[1]+JStr+navigationInfoEty.getShelf_layer();
					navigationInfoEty.setInfo_value(bookitem.getShelflayer_barcode());*/
					byte[] outbytes = new byte[50];
					try{
						System.out.println("打开操作......");
						YhSocket socket = new YhSocket();
						if(socket.NetSet(ip, port)){
							LogUtils.info("###### 设置成功  #####");
							Thread.sleep(2000);
							if(socket.NetConnectOn(qNumber)){
								LogUtils.info("###### 连接成功  #####");
								/*Thread.sleep(2000);
								if(socket.NetStatusChk(qNumber, outbytes)){
									for(int i=0;i<15;i++){
										String str = "0" + Integer.toHexString(outbytes[i] & 0xFF);  
										String state = str.substring(str.length()-2, str.length());
										if("05".equals(state)||"06".equals(state)){
											//列正在打开
											flag = 2;
											LogUtils.info("###### 列正在打开不必操作  #####");
											break;
										}
									}
								}else{
									LogUtils.info("###### 查看状态失败，执行操作  #####");
								}*/
								Thread.sleep(2000);
								if(flag != 2){
									if(socket.NetPowerOn(qNumber)){
										LogUtils.info("###### 打开电源成功  #####");
										Thread.sleep(2000);
										if(socket.NetClearFobidden(qNumber)){
											LogUtils.info("###### 解除禁止成功  #####");
											Thread.sleep(2000);
											if(socket.NetOpenL(qNumber, lNumber, lName)){
												flag = 2;
												LogUtils.info("###### 打开指定列成功  #####");
											}else{
												LogUtils.info("###### 打开指定列失败  #####");
											}
											LogUtils.info("区"+qNumber+"列"+lNumber+"节"+JNumber+"层"+CNumber+"侧"+Ce+"1"+1+"标题"+title+"列名"+lName);
											/*if(socket.NetOpenRecord(qNumber, lNumber, JNumber,CNumber,Ce,1,title,lName)){
												flag = 2;
												LogUtils.info("###### 打开指定列成功  #####");
											}else{
												LogUtils.info("###### 打开指定列失败  #####");
											}*/
											//LibDLLUtil.NetOpenRecord((long)qNumber, (long)lNumber, (long)JNumber, (long)CNumber, (long)Ce, 1l, title, (long)lName);
										}else{
											LogUtils.info("###### 解除禁止失败  #####");
										}
									}else{
										LogUtils.info("###### 打开电源失败  #####");
									}	
								}
							}else{
								LogUtils.info("###### 连接失败  #####");
							}
						}else{
							LogUtils.info("###### 设置成功  #####");
						}
						/*System.out.println("打开操作......");
						if(LibDLLUtil.NetSet(ip,port)){
							System.out.println("################ 密集书架设置ip成功 ######################");
							if(LibDLLUtil.NetConnectOn(qNumber)){
								System.out.println("################ 密集书架连接成功 ######################");
								if(LibDLLUtil.NetStatusChk(qNumber, outData)){
									System.out.println("################ 查看密集书架运行状态成功 ######################");
									System.out.println(outData);
								}else{
									System.out.println("################ 查看密集书架运行状态失败 ######################");
								}
								if(LibDLLUtil.NetOpenL(qNumber, lNumber, lNname)){
									System.out.println(" 成功              区号"+qNumber+"列"+lNumber+"列名"+lNname);
									System.out.println("################ 查看密集书架运行状态成功 ######################");
									System.out.println(outData);
								}else{
									System.out.println(" 失败              区号"+qNumber+"列"+lNumber+"列名"+lNname);
									System.out.println("################ 查看密集书架运行状态失败 ######################");
								}
							}else{
								System.out.println("################ 密集书架连接失败 ######################");
							}
						}else{
							System.out.println("################ 密集书架设置ip失败 ######################");
						}*/
					}catch(Exception e){
						LogUtils.error(e);
					}
				}
			}
		}
		if(flag == 0){
			ctm.put("qNumber", null);
			ctm.put("ip", null);
			ctm.put("port", null);
			resultEntity.setState(false);
			resultEntity.setMessage("密集书架配置信息缺失，打开失败！");
			resultEntity.setResult(ctm);
		}else if(flag == 1){
			ctm.put("qNumber", qNumber);
			ctm.put("ip", ip);
			ctm.put("port", port);
			resultEntity.setState(false);
			resultEntity.setMessage("密集书架("+qNumber+")区("+aCtLNumber+")列打开失败");
			resultEntity.setResult(ctm);
		}else if(flag==2){
			ctm.put("qNumber", qNumber);
			ctm.put("ip", ip);
			ctm.put("port", port);
			resultEntity.setState(true);
			resultEntity.setMessage("密集书架("+qNumber+")区("+aCtLNumber+")列打开成功");
			resultEntity.setResult(ctm);
		}
		return resultEntity;
	}
	
	@Override
	public ResultEntity shelfReset(String req) {
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setState(false);
		String ip = null;
		int port = 0;
		int qNumber = -1;
		byte[] outbytes = new byte[50];
		try{
			Map<String,String> map = JsonUtils.fromJson(req, Map.class);
			ip = map.get("ip");
			port = Integer.parseInt(map.get("port"));
			qNumber = Integer.parseInt(map.get("qNumber"));
		}catch(Exception e){
			LogUtils.error(e);
			resultEntity.setMessage("请求参数格式有误！");
			return resultEntity;
		}
		
		if(ip == null || port == 0 || qNumber == -1){
			resultEntity.setMessage("请求参数格式有误！");
			return resultEntity;
		}
		
		try{
			System.out.println("复位操作......");
			YhSocket socket = new YhSocket();
			if(socket.NetSet(ip, port)){
				LogUtils.info("###### 设置成功  #####");
				Thread.sleep(2000);
				if(socket.NetConnectOn(qNumber)){
					LogUtils.info("###### 连接成功  #####");
					/*Thread.sleep(2000);
					if(socket.NetStatusChk(qNumber, outbytes)){
						for(int i=0;i<15;i++){
							String str = "0" + Integer.toHexString(outbytes[i] & 0xFF);  
							String state = str.substring(str.length()-2, str.length());
							if("07".equals(state)||"08".equals(state)){
								//列正在打开
								LogUtils.info("###### 列正在关闭不必操作  #####");
								resultEntity.setState(true);
								return resultEntity;
							}
						}
					}else{
						LogUtils.info("###### 查看状态失败，执行操作  #####");
					}*/
					Thread.sleep(2000);
					if(socket.NetPowerOn(qNumber)){
						LogUtils.info("###### 打开电源成功  #####");
						Thread.sleep(2000);
						if(socket.NetClearFobidden(qNumber)){
							LogUtils.info("###### 解除禁止成功  #####");
							Thread.sleep(2000);
							if(socket.NetCloseL(qNumber)){
								LogUtils.info("###### 复原成功   #####");
								resultEntity.setState(true);
							}else{
								LogUtils.info("###### 复原失败  #####");
							}
						}else{
							LogUtils.info("###### 解除禁止失败  #####");
						}
					}else{
						LogUtils.info("###### 打开电源失败  #####");
					}
				}else{
					LogUtils.info("###### 连接失败  #####");
				}
			}else{
				LogUtils.info("###### 设置成功  #####");
			}
			/*if(LibDLLUtil.NetSet(ip,port)){
				System.out.println("################ 密集书架设置ip成功 ######################");
				if(LibDLLUtil.NetConnectOn(qNumber)){
					System.out.println("################ 密集书架连接成功 ######################");
					if(LibDLLUtil.NetCloseL(qNumber)){
						System.out.println("################ 密集书架复位控制成功 ######################");
						resultEntity.setState(true);
					}else{
						System.out.println("################ 密集书架复位控制失败 ######################");
						resultEntity.setState(false);
					}
				}else{
					System.out.println("################ 密集书架连接失败 ######################");
				}
			}else{
				System.out.println("################ 密集书架设置ip失败 ######################");
			}*/
		}catch(Exception e){
			System.out.println("################ 密集书架复位控制异常 ######################");
			resultEntity.setState(false);
			LogUtils.error(e);
		}
		return resultEntity;
	}

	@Override
	public ResultEntity getLocations(String req) {
		ResultEntity resultEntity = new ResultEntity();
		List<String> list = new ArrayList<>();
		try{
			JSONArray jsonarray = JSONArray.fromObject(req);
			list = (List<String>)JSONArray.toCollection(jsonarray, String.class); 
		}catch(Exception e){
			resultEntity.setState(false);
			resultEntity.setMessage("请求参数格式有误！");
			return resultEntity;
		}
		
		if(list == null || list.size() == 0){
			resultEntity.setState(false);
			resultEntity.setMessage("请求参数格式有误！");
			return resultEntity;
		}
		//Collections.sort(list);
		Map<String,Object> map = new HashMap<>();
		for(String bookBarCode : list){
			int flag = 0;
			String cBarCode = null;
			String state = null; 
			Map<String,Object> ctm = new HashMap<>();
			BookitemEntity et = new BookitemEntity();
			et.setBook_barcode(bookBarCode);
			BookitemEntity bookitem = bookitemDao.queryBookitemByCode(et);
			if(bookitem != null){
				flag =1;
				cBarCode = bookitem.getShelflayer_barcode();
				state = String.valueOf(bookitem.getState());
				/*NavigationInfoEntity navigationInfoEty = navigationService.queryInfoByParam(bookBarCode);
				if(navigationInfoEty != null){
					flag =1;
					String[] locations = null;
					String info = navigationInfoEty.getInfo_value();
					if(info != null){
						locations = info.split(",");
					}
					if(locations.length == 6 && StringUtil.hasLength(locations[0]) && StringUtil.hasLength(locations[1]) && StringUtil.hasLength(locations[5])){
						int JNumber = Integer.parseInt(locations[5]);
						String JStr = "";
						if(JNumber<10) JStr = "00"+ JNumber;
						else if(JNumber<100) JStr = "0"+ JNumber;
						else JStr = String.valueOf(JNumber);
						cBarCode = locations[0]+locations[1]+JStr+navigationInfoEty.getShelf_layer();
					}
				}*/
			}
			ctm.put("flag", flag);
			ctm.put("shelflayerBarcode", cBarCode);
			ctm.put("state", state);
			map.put(bookBarCode, ctm);
		}
		
		resultEntity.setState(true);
		resultEntity.setResult(map);
		
		return resultEntity;
	}

}
