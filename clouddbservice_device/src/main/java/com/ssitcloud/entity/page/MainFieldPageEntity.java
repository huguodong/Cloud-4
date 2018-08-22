package com.ssitcloud.entity.page;

import com.ssitcloud.common.entity.DatagridPageEntity;

/**
 * 信息主字段表 分页entity
 * @author shuangjunjie
 * 2017年2月24日上午10:39
 *
 */
public class MainFieldPageEntity extends DatagridPageEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer mainfield_idx;//字段ID
	private String mainfield_table;//表名（功能名）
	private String mainfield_field;//	字段
	private String mainfield_field_desc;	//字段说明
	private String ref_statistics;//统计或分组关联数据
	public Integer getMainfield_idx() {
		return mainfield_idx;
	}
	public void setMainfield_idx(Integer mainfield_idx) {
		this.mainfield_idx = mainfield_idx;
	}
	public String getMainfield_table() {
		return mainfield_table;
	}
	public void setMainfield_table(String mainfield_table) {
		this.mainfield_table = mainfield_table;
	}
	public String getMainfield_field() {
		return mainfield_field;
	}
	public void setMainfield_field(String mainfield_field) {
		this.mainfield_field = mainfield_field;
	}
	public String getMainfield_field_desc() {
		return mainfield_field_desc;
	}
	public void setMainfield_field_desc(String mainfield_field_desc) {
		this.mainfield_field_desc = mainfield_field_desc;
	}
	public String getRef_statistics() {
		return ref_statistics;
	}
	public void setRef_statistics(String ref_statistics) {
		this.ref_statistics = ref_statistics;
	}
	
}
