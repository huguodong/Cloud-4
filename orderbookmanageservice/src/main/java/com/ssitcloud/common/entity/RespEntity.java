package com.ssitcloud.common.entity;


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

	
	
}
