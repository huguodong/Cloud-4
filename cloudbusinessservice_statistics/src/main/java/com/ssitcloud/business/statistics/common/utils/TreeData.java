package com.ssitcloud.business.statistics.common.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class TreeData {

	private static AtomicInteger increamentId = new AtomicInteger(1);
	private String id;
    private String pid;
    private String text;
    private String name;
    private Long count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setIncreamentId(Integer id) {
		increamentId.getAndSet(id);
	}
	public TreeData(String pid, String text,Long count,String name) {
        super();
        this.id = ""+ increamentId.getAndIncrement();
        this.pid = pid;
        this.text = text;
        this.count = count;
        this.name = name;
    }

    public TreeData() {
        super();
    }

	@Override
	public String toString() {
		return "TreeData [id=" + id + ", pid=" + pid + ", text=" + text
				+ ", name=" + name + ", count=" + count + "]";
	}
}
