package com.ssitcloud.business.common.exception;

import com.jcraft.jsch.JSchException;

public class JschConnectException extends JSchException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Throwable cause = null;

	public JschConnectException() {
	}

	public JschConnectException(String s) {
		super(s);
	}

	public JschConnectException(String s, Throwable e) {
		super(s);
		this.cause = e;
	}

	public Throwable getCause() {
		return this.cause;
	}
}
