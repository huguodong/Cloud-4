package com.ssitcloud.business.reminder.common.util;

public class SimpleMail
{
	private String subject;
	private String content;
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public String getSubject() 
	{
		return subject;
	}

	public Object getContent()
	{
		return content;
	}

}
