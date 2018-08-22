package com.ssitcloud.dbauth.common;

public class CommonException extends RuntimeException {

	private static final long serialVersionUID = 6858608499058135842L;

	public CommonException() {
		super();
	}

	public CommonException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommonException(String message) {
		super(message);
	}

	public CommonException(Throwable cause) {
		super(cause);
	}

}
