package com.ssitcloud.business.common.exception;

import com.jcraft.jsch.JSchException;

public class JschShellException extends JSchException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Throwable cause = null;

	public JschShellException() {
	}

	public JschShellException(String s) {
		super(s);
	}

	public JschShellException(String s, Throwable e) {
		super(s);
		this.cause = e;
	}

	public Throwable getCause() {
		return this.cause;
	}
}
