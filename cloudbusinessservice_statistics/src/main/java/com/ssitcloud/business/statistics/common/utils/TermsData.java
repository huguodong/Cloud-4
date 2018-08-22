package com.ssitcloud.business.statistics.common.utils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.sf.json.JSONArray;

public class TermsData {
	private static AtomicInteger increamentId = new AtomicInteger(1);
	private long id;
    private long parentId;
    private String text;
    private String name;
    private long value;
    private List<TermsData> children;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
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

	public List<TermsData> getChildren() {
		return children;
	}

	public void setChildren(List<TermsData> children) {
		this.children = children;
	}


	public TermsData(long parentId, String text,long value,String name) {
        super();
        this.id = increamentId.getAndIncrement();
        this.parentId = parentId;
        this.text = text;
        this.value = value;
        this.name = name;
    }
	
	public TermsData(long parentId, String text,long value,String name,List<TermsData> children) {
		super();
		this.id = increamentId.getAndIncrement();
		this.parentId = parentId;
		this.text = text;
		this.value = value;
		this.name = name;
		this.children = children;
	}

    public TermsData() {
        super();
    }

	@Override
	public String toString() {
		return "{\"id\":" + id + ",\"parentId\":" + parentId + ",\"text\":\"" + text + "\",\"name\":\"" + name + "\",\"value\":" + value + ",\"children\":" + JSONArray.fromObject(children) + "}";
	}
}
