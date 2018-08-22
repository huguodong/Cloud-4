package com.ssitcloud.business.common.exception;

import com.jcraft.jsch.JSchException;

public class JschExecException extends JSchException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Throwable cause = null;

	public JschExecException() {
	}

	public JschExecException(String s) {
		super(s);
	}

	public JschExecException(String s, Throwable e) {
		super(s);
		this.cause = e;
	}

	public Throwable getCause() {
		return this.cause;
	}
}
