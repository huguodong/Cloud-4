package com.ssitcloud.common.exception;

/**
 * 删除设备异常时候抛出该异常
 * @author lbh
 */
public class DeleteDeviceErrorExeception extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeleteDeviceErrorExeception() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeleteDeviceErrorExeception(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DeleteDeviceErrorExeception(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DeleteDeviceErrorExeception(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
