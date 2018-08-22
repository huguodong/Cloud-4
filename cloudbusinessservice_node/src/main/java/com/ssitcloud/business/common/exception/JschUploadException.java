package com.ssitcloud.business.common.exception;

import com.jcraft.jsch.JSchException;

public class JschUploadException extends JSchException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Throwable cause = null;

	public JschUploadException() {
	}

	public JschUploadException(String s) {
		super(s);
	}

	public JschUploadException(String s, Throwable e) {
		super(s);
		this.cause = e;
	}

	public Throwable getCause() {
		return this.cause;
	}
}
