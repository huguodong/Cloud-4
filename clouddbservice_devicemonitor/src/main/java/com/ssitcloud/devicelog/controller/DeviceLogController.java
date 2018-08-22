package com.ssitcloud.devicelog.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.devicelog.service.DeviceStateService;


@Controller
@RequestMapping(value={"devicelog"})
public class DeviceLogController {

	@Resource
	private DeviceStateService deviceStateService;
	/**
	 *  #String transData(String conditionInfo)    『mongoDB』
		#conditionInfo输入内容如下：
		#字段名称	类型	说明
		#device_id	String	设备ID
		#library_id	String	馆ID
		#object		String	对象（状态数据还是操作书架日志）state_data/oper_log
		#data 		String	数据内容
		#transData 新接口

{
    "servicetype": "ssitcloud",
    "target": "ssitcloud",
    "operation": "transData",
    "data":{
		"device_id":"001_SSL001",
		"library_id":"1",
		"object":"state_data",
		"content":{
		    "bin_state":{
	            "cardbin": {
	                "amount": "99",
	                "state": "0"
	            },
	            "cashbin": [
	                {
	                    "subtype": "50",
	                    "amount": "2",
	                    "state": "0"
	                },
	                {
	                    "subtype": "100",
	                    "amount": "5",
	                    "state": "0"
	                }
	            ],
	            "bookbin": [
	                {
	                    "binno": "1",
	                    "subtype": "1",
	                    "desc": "2222",
	                    "amount": "1",
	                    "state": "0"
	                },
	                {
	                    "binno": "2",
	                    "subtype": "1",
	                    "desc": "2222",
	                    "amount": "1",
	                    "state": "0"
	                },
	                {
	                    "binno": "3",
	                    "subtype": "1",
	                    "desc": "海洋类",
	                    "amount": "1",
	                    "state": "0"
	                },
	                {
	                    "binno": "4",
	                    "subtype": "2",
	                    "desc": "222",
	                    "amount": "1",
	                    "state": "0"
	                },
	                {
	                    "binno": "5",
	                    "subtype": "10",
	                    "desc": "11111",
	                    "amount": "1",
	                    "state": "0"
	                }
	            ]
		       }
		}
	   }
}
	 * @methodName: stateReceive
	 * @param request
	 * @param req
	 * @return
	 * @returnType: ResultEntityF<Object>
	 * @author: liuBh
	 */
	@RequestMapping(value="transData")
	@ResponseBody
	public ResultEntityF<Object> transData(HttpServletRequest request,String req){
		System.out.println("------------transData to MongoDB------------------req:" + req);
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		try {
			result = deviceStateService.transData(req);
		} catch (Exception e) {
			result.setState(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	/**
	 * 保存跟图书馆业务相关的日志到mongodb库
	 * 例如，借还日志，财经日志，办证日志，以及各类流通日志等
	 * * <p>函数 String transOperationLog(String conditionInfo)    
	 * <p>备注 ： 逐条发送，云端不支持JSON数组
	 * <p>conditionInfo输入内容如下:
	 *  <p>字段名称	                类型	说明
	    <br>device_id	String	设备ID
		<br>Library_id	String	馆ID
		<br>content	String	json数据内容（云端根据opercmd来区分库）
		
		<p>返回数据结果result内容如下：
		<br>字段名称	                类型	说明
		<br>device_id	String	设备ID
		<br>Library_id	String	馆ID
	 * 
	 * loan_log
	 * {
		    "servicetype":"ssitcloud",
		    "target":"ssitcloud",
		    "operation":"transOperationLog",
		    "data":{
		        "device_id":"STA001",
		        "library_id":"LIB001",
		        "object":"transOperationLog",
		        "content":{
		            "opertime":"20170226004659",
		            "operator":"admin",
		            "opercmd":"llog",
		            "operresult":"Y",
		            "AuthCardno":"004555",
		            "AuthType":"N",
		            "ItemLocation":"LIB1",
		            "ItemBin":"1",
		            "ItemLoanDate":"20170302",
		            "ItemReturnDate":"20170606",
		            "Barcode":"066666",
		            "Title":"BookTitle",
		            "Author":"AuthorName",
		            "ISBN":"9787510846403",
		            "Callno":"A.31c",
		            "MediaType":"B",
		            "Price":"21.00",
		            "CirculationType":"1",
		            "PermanentLocation":"A",
		            "CurrentLocation":"A",
		            "Publisher":"A",
		            "Subject":"article",
		            "PageNum":"122"
		        }
		    }
		}
		
		cardissue_log
		{
		    "servicetype":"ssitcloud",
		    "target":"ssitcloud",
		    "operation":"transOperationLog",
		    "data":{
		        "device_id":"STA001",
		        "library_id":"LIB001",
		        "content":{
		            "opertime":"20170226004659",
		            "operator":"admin",
		            "opercmd":"clog",
		            "operresult":"Y",
		            "AuthCardno":"004555",
		            "AuthType":"N",
		            "Cardtype":"1",
		            "ExpireDate":"20180303",
		            "PrivilegeFee":"100",
		            "name":"杨三三",
		            "Birth":"20010101",
		            "Gender":"1",
		            "EthnicGroup":"汉",
		            "Address":"China",
		            "E-mail":"qq@qq.com",
		            "Telephone":"77777777",
		            "Mobile":"13000000000"
		        }
		    }
		}
		
		finance_log
		{
		    "servicetype":"ssitcloud",
		    "target":"ssitcloud",
		    "operation":"transOperationLog",
		    "data":{
		        "device_id":"STA001",
		        "library_id":"LIB001",
		        "content":{
		            "opertime":"20170226004659",
		            "operator":"admin",
		            "opercmd":"flog",
		            "operresult":"Y",
		            "AuthCardno":"004555",
		            "AuthType":"N",
		            "UniCardno":"5556",
		            "Name":"杨三三",
		            "Purpose":"y",
		            "Payway":"1",
		            "Money":"0.1",
		            "FiscalTranID":"SSL001",
		            "Barcode":"04455665",
		            "SumDay":"10"
		        }
		    }
		}
	 *
	 * <p>2017年3月2日 上午11:23:41 
	 * <p>create by hjc
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/transOperationLog")
	@ResponseBody
	public ResultEntityF<Object> transOperationLog(HttpServletRequest request,String req) {
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		try {
			System.out.println("----------操作日志接口,transOperationLog,req=" + req);
			result = deviceStateService.transOperationLog(req);
		} catch (Exception e) {
			result.setState(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	
	/**
	 * 在架书、卡数量、还书箱、状态：正常 或者不正常
	 * 
	 * @param req 格式 req ={["device_id1","device_id2",......]}
	 * @param @return   
	 * @return ResultEntityF<Object>  
	 * @throws
	 * @author lbh
	 * @date 2016年5月5日
	 */
	@RequestMapping(value={"selectDeviceState"})
	@ResponseBody
	public ResultEntityF<Object> selectDeviceState(HttpServletRequest request,String req){
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		try {
			result = deviceStateService.queryDeviceState(req);
		} catch (Exception e) {
			result.setState(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
			return result;
		}
		return result;
	}
	/**
	 * 获取书架状态信息 bookrack_state集合
	 * @param @param request
	 * @param @param req ={["device_id1","device_id2",......]}
	 * @param @return   
	 * @return ResultEntityF<Object>  
	 * @throws
	 * @author lbh
	 * @date 2016年5月9日
	 */
	@RequestMapping(value={"selectBookrackState"})
	@ResponseBody
	public ResultEntityF<Object> selectBookrackState(HttpServletRequest request,String req){
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		try {
			result = deviceStateService.selectBookrackState(req);
		} catch (Exception e) {
			result.setState(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
			return result;
		}
		return result;
	}
	/**
	 * 获取箱状态信息 selectBinState集合
	 * @param @param request
	 * @param @param req ={["device_id1","device_id2",......]}
	 * @param @return   
	 * @return ResultEntityF<Object>  
	 * @throws
	 * @author lbh
	 * @date 2016年5月10日
	 */
	@RequestMapping(value={"selectBinState"})
	@ResponseBody
	public ResultEntityF<Object> selectBinState(HttpServletRequest request,String req){
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		try {
			result = deviceStateService.selectBinState(req);
		} catch (Exception e) {
			result.setState(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
			return result;
		}
		return result;
	}
	/**
	 * req ={["device_id1","device_id2",......]}
	 * @param @param request
	 * @param @param req
	 * @param @return   
	 * @return ResultEntityF<Object>  
	 * @throws
	 * @author lbh
	 * @date 2016年5月10日
	 */
	@RequestMapping(value={"selectDeviceExtState"})
	@ResponseBody
	public ResultEntityF<Object> selectDeviceExtState(HttpServletRequest request,String req){
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		try {
			result = deviceStateService.selectDeviceExtState(req);
		} catch (Exception e) {
			result.setState(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
			return result;
		}
		return result;
	}
	/**
	 * req ={["device_id1","device_id2",......]}
	 * @param @param request
	 * @param @param req
	 * @param @return   
	 * @return ResultEntityF<Object>  
	 * @throws
	 * @author lbh
	 * @date 2016年5月10日
	 */
	@RequestMapping(value={"selectSoftState"})
	@ResponseBody
	public ResultEntityF<Object> selectSoftState(HttpServletRequest request,String req){
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		try {
			result = deviceStateService.selectSoftState(req);
		} catch (Exception e) {
			result.setState(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
			return result;
		}
		return result;
	}
	/**
	 * 查询所有设备的设备状态
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryDeviceIdByState"})
	@ResponseBody
	public ResultEntityF<Object> queryDeviceIdByState(HttpServletRequest request,String req){
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		try {
			result = deviceStateService.queryDeviceIdByState(req);
		} catch (Exception e) {
			result.setState(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
			return result;
		}
		return result;
	}
	/**
	 * 从client.json获取数据库名（图书馆ID_设备ID）
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"getMongodbNames"})
	@ResponseBody
	public ResultEntityF<Object> getMongodbNames(HttpServletRequest request,String req){
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		try {
			result = deviceStateService.getMongodbNames(req);
		} catch (Exception e) {
			result.setState(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	
	/**
	 * 获取 数据库名（图书馆ID_设备ID） 对应 数据库实例
	 * 接受参数 {dbName:"dbName"}
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"getDBInstanceByDBName"})
	@ResponseBody
	public ResultEntityF<Object> getDBInstanceByDBName(HttpServletRequest request,String req){
		ResultEntityF<Object> result = new ResultEntityF<Object>();
		try {
			result = deviceStateService.getDBInstanceByDBName(req);
		} catch (Exception e) {
			result.setState(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
}
