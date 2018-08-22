package com.ssitcloud.business.statistics.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;


public class Tree<T> {

	public Tree() {
		super();
	}
	
	public Tree(TreeData treeData) {
		this.id = treeData.getId();
		this.text = treeData.getText();
		this.count = treeData.getCount();
		this.children = null;
		this.isParent = true;
		this.isChildren = false;
		this.parentId = treeData.getPid();
		this.name = treeData.getName();
	}
	/**
	 * 节点ID
	 */
	private String id;
	/**
	 * 显示节点文本
	 */
	private String text;
	/**
	 * 显示节点名称
	 */
	private String name;
	/**
	 * 节点的子节点
	 */
	private List<Tree<T>> children = new ArrayList<Tree<T>>();

	/**
	 * 父ID
	 */
	private String parentId;
	/**
	 * 是否有父节点
	 */
	private boolean isParent = false;
	/**
	 * 是否有子节点
	 */
	private boolean isChildren = false;
	/**
	 * 数量
	 */
	private Long count;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Tree<T>> getChildren() {
		return children;
	}

	public void setChildren(List<Tree<T>> children) {
		this.children = children;
		if(CollectionUtils.isEmpty(children)){
			this.isChildren = false;
		}else{
			this.isChildren = true;
		}
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isChildren() {
		return isChildren;
	}

	public void setChildren(boolean isChildren) {
		this.isChildren = isChildren;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public Tree(String id, String text,Long count,String name, List<Tree<T>> children,
			boolean isParent, boolean isChildren, String parentID) {
		super();
		this.id = id;
		this.text = text;
		this.count = count;
		this.children = children;
		this.isParent = isParent;
		this.isChildren = isChildren;
		this.parentId = parentID;
		this.name = name;
	}
}
