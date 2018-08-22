package com.ssit.common.entity;

import java.util.List;
import java.util.Map;

/**
 * 分页插件
 */

public class DatagridPageEntity {

	private long total;// 总条数

	private List<?> rows; // 返回页面的数据

	private int beginIndex = 0;//当前页开始序号

	private int page = 1;//当前页

	private int pageSize = 10;//每页显示条数
	
	private long pageCount;//总页数
	
	private Map<String,String> param;//查询参数
	

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
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
		this.pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> lists) {
		this.rows = lists;
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		this.countEndAndIndex();
	}

	// 计算 分页所用到的 开始和结束 rownum
	private void countEndAndIndex() {
		this.beginIndex = (this.page - 1) * this.pageSize;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	public Map<String, String> getParam() {
		return param;
	}

	public void setParam(Map<String, String> param) {
		this.param = param;
	}
	
}
