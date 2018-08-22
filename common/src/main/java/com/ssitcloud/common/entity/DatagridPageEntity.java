package com.ssitcloud.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * easyUI 控件 DTO
 * 分页插件
 * 
 * 2016年3月22日 下午6:11:09 
 * @author hjc 
 * @version 1.0 
 * @since 1.0.2
 *
 */
public class DatagridPageEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private int total;// 总条数

	private List<T> rows; // 返回页面的数据

	private Integer beginIndex = 0;

	private int page = 1;

	private Integer pageSize = 10;
	
	private boolean doAount = true; // 是否查询总数 默认是true，首先查询总数。
	
	private boolean whetherPaging=true;//是否分页 true分页
	

	public boolean isWhetherPaging() {
		return whetherPaging;
	}

	public void setWhetherPaging(boolean whetherPaging) {
		this.whetherPaging = whetherPaging;
	}

	public boolean isDoAount() {
		return doAount;
	}

	public void setDoAount(boolean doAount) {
		this.doAount = doAount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
		this.countEndAndIndex();
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		this.countEndAndIndex();
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> lists) {
		if (lists != null && lists.size() > 0) {
			Object row = lists.get(0);
			if (row != null && row instanceof String) {
				setPageSize((Integer.valueOf((String) row)));
			}
		}
		this.rows = lists;
	}

	public Integer getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(Integer beginIndex) {
		this.beginIndex = beginIndex;
	}

	// 计算 分页所用到的 开始和结束 rownum
	private void countEndAndIndex() {
		this.beginIndex = (this.page - 1) * this.pageSize + 0;
	}

}
