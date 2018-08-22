package com.ssitcloud.devmgmt.controller;

import java.util.concurrent.Callable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.devmgmt.service.DeviceService;

@Controller
@RequestMapping(value={"device"})
public class DeviceController {
	
	@Resource
	private DeviceService deviceService;
	/**
	 * 接server端机器命令
	 * @methodName: recevieOrder
	 * @param request
	 * @param req
	 * @return
	 * @returnType: Callable<RespEntity>
	 * @author: liuBh
	 */
	@RequestMapping(value={"recevieOrder"})
	@ResponseBody
	public Callable<ResultEntity> recevieOrder(HttpServletRequest request,final String req){
		deviceService.sendOrderToDevice(req);
		return new Callable<ResultEntity>() {
			@Override
			public ResultEntity call(){
				LogUtils.info("接受到命令:"+req);
				return deviceService.transforOrderData(req);
			}
		};
	}
	/**
	 * 接收表发生变化的数据<br/>
	 * 接收数据格式:<br/>
	 * req=<br/>
	 * {<br/>
	 * 		
	 * }<br/>
	 * @methodName: recevieSyncData
	 * @param request
	 * @param req
	 * @return
	 * @returnType: Callable<RespEntity>
	 * @author: liuBh
	 */
	@RequestMapping(value={"recevieChangeTableData"})
	@ResponseBody
	public Callable<ResultEntity> recevieChangeTableData(HttpServletRequest request,final String req){
		return new Callable<ResultEntity>() {
			@Override
			public ResultEntity call(){
				return deviceService.transforChangeTableData(req);
			}
		};
	}
	/**
	 * 查询设备端日志记录。
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryDeviceLog"})//queryDeviceLog
	@ResponseBody
	public Callable<ResultEntity> queryDeviceLog(HttpServletRequest request,final String req){
		return new Callable<ResultEntity>() {
			@Override
			public ResultEntity call(){
				return deviceService.queryDeviceLog(req);
			}
		};
	}
	
	/**
	 * 
	 * 查询设备端执行命令结果（周期性）
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryControlResult"})
	@ResponseBody
	public Callable<ResultEntity> queryControlResult(HttpServletRequest request,final String req){
		return new Callable<ResultEntity>() {
			@Override
			public ResultEntity call() throws Exception {
				LogUtils.info("queryControlResult req:"+req);
				return deviceService.queryControlResult(req);
			}
		};
	}
	/**
	 * 执行下载设备端日志请求
	 *  req={
    		"utcStartTime":utcStartTime,
    		"utcEndTime":utcEndTime,
    		"device_id":device_id,
    		"operator_idx":"....." 
    	};
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"downloadRunLogOperation"})
	@ResponseBody
	public Callable<ResultEntity> downloadRunLogOperation(HttpServletRequest request,final String req){
		return new Callable<ResultEntity>() {
			@Override
			public ResultEntity call() throws Exception {
				LogUtils.info("downloadRunLogOperation req:"+req);
				return deviceService.downloadRunLogOperation(req);
			}
		};
	}
	/**
	 * req=[
	 * {
	 *  devId:"",
	 *  libid:""
	 * },
	 * {
	 * 
	 * },```,``,``,``,]
	 * 
	 * 
	 * 
	 * 
	 * @param request
	 * @param req 
	 * @return
	 */
	@RequestMapping(value={"GetLastHeatBeatTime"})
	@ResponseBody
	public Callable<ResultEntity> GetLastHeatBeatTime(HttpServletRequest request,final String req){
		return new Callable<ResultEntity>() {
			@Override
			public ResultEntity call() throws Exception {
				return deviceService.getLastHeatBeatTime(req);
			}
		};
	}
	
	
	/**
	 * 接受手机端的指令
	 * 
	 * 请求参数
	 * {
		    "device_id":"SCGS",
		    "library_id":"LIB001",
		    "app_data":"1|V1.0|SZTSG001|014229|0251365,0125487|BTVOWv23piwm2DDAyWw1T9FfPzRV+XwW7pw7cidNfhFMYCWD+RlnL1g+oIVOt4mqFKLA+SXkH9DXLMv/HS/GH0wIA6JMNlznlgTqFEYxftRNyWj4kR/cQ1eYcHhxpKSRvwhMUr9OpWyuVEuCEDzMSQDxYKBg/f0j7pWL2/ldHO4="
		}
		系统组成(1 IOS  2 Android)|密钥版本号|图书馆ID|读者证号|图书列表(多个条码号之间用逗号分隔)|密文
	 *
	 * <p>2017年4月8日 下午4:01:38 
	 * <p>create by hjc
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"recevieAppData"})
	@ResponseBody
	public Callable<ResultEntity> recevieAppData(HttpServletRequest request,final String req){
		return new Callable<ResultEntity>() {
			@Override
			public ResultEntity call(){
				LogUtils.info("接受到命令:" + req);
				return deviceService.transforAppData(req);
			}
		};
	}
	
	@RequestMapping("downloadConfig")
	@ResponseBody
	public ResultEntity downloadConfig(String req){
		deviceService.downloadConfig(req);
		System.out.println(req);
		return new ResultEntity();
	}
	
	
	
	
}
