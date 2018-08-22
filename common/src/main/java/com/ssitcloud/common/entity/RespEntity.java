package com.ssitcloud.common.entity;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.ResultEntity;


/**
 * 返回给设备用户的封装实体<br/>
 * 
 *  Servicetype		String	数据服务接口<br/>
	Target			String	数据来源业务处理<br/>
	Operation		String	数据服务接口中的相应函数名<br/>
	Data			String	调用接口的参数,具体格式有具体函数而定<br/>
*/

public class RespEntity {
	
	private String Servicetype;
	
	private String Target;
	
	private String Operation;
	/**
	 * 请求id：uuid+时间戳
	 */
	private String requestId;
	
	private ResultEntity data=new ResultEntity();

	public static final String defaultServiceType="ssitcloud";
	public static final String defaultTarget="api";
	public static final String defaultOperation="api";
	
	public RespEntity() {
		super();
	}

	public RespEntity(RequestEntity requestEntity){
		if(requestEntity==null){
			return;
		}
		this.Servicetype=requestEntity.getServicetype();
		this.Target=requestEntity.getTarget();
		this.Operation=requestEntity.getOperation();
	}
	
	public RespEntity(String servicetype, String target, String operation,
			ResultEntity data) {
		super();
		this.Servicetype = servicetype;
		this.Target = target;
		this.Operation = operation;
		this.data = data;
	}
	
	/**
	 * 设置响应实体类的前三个参数
	 *
	 * <p>2016年4月14日 下午2:49:23
	 * <p>create by hjc
	 * @param reqEntity
	 */
	public void setHeader(RequestEntity reqEntity) {
		this.Servicetype = reqEntity.getServicetype();
		this.Target = reqEntity.getTarget();
		this.Operation = reqEntity.getOperation();
	}
	
	/**
	 * 设置结果集的值
	 * 
	 * <p>2016年4月11日 上午11:33:51
	 * <p>create by hjc
	 * @param state  为接口返回的状态包含true、false; 
	 * @param message  为接口返回的个人编的提示消息;
	 * @param retMessage 为组合提示消息包含函数名+系统返回的错误提醒。例："CheckFin()函数异常" + ex.getMessage();
	 * @param result 为实际返回的信息字符串（经过json序列化后的字符串），不同接口的序列化对象不同
	 */
	public void setValue(boolean state,String message, String retMessage, Object result) {
		this.data.setValue(state, message, retMessage, result);
	}
	
	/**
	 * 设置结果集的值
	 * 
	 * <p>2016年4月11日 上午11:33:55
	 * <p>create by hjc
	 * @param state 为接口返回的状态包含true、false; 
	 * @param message 为接口返回的个人编的提示消息;
	 */
	public void setValue(boolean state,String message) {
		this.data.setValue(state, message);
	}

	public String getServicetype() {
		return Servicetype;
	}

	public void setServicetype(String servicetype) {
		Servicetype = servicetype;
	}

	public String getTarget() {
		return Target;
	}

	public void setTarget(String target) {
		Target = target;
	}

	public String getOperation() {
		return Operation;
	}

	public void setOperation(String operation) {
		Operation = operation;
	}



	public ResultEntity getData() {
		return data;
	}

	public void setData(ResultEntity data) {
		this.data = data;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
