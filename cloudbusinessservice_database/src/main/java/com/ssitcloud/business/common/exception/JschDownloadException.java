package com.ssitcloud.business.common.exception;

import com.jcraft.jsch.JSchException;

public class JschDownloadException extends JSchException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Throwable cause = null;

	public JschDownloadException() {
	}

	public JschDownloadException(String s) {
		super(s);
	}

	public JschDownloadException(String s, Throwable e) {
		super(s);
		this.cause = e;
	}

	public Throwable getCause() {
		return this.cause;
	}
}
