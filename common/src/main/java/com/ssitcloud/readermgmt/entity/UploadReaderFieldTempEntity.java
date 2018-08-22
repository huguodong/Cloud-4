package com.ssitcloud.readermgmt.entity;

import java.util.List;

public class UploadReaderFieldTempEntity {
	
	/**字段名称  code 编号 */
	private String data_source_select;
	
	/**单行、还是多行，多行是指：在excel一列中存在多种内容，1233311|兰大|本科    text|multiple-text*/
	private String cOptionType;
	
	/**分割符（| ，*， #）*/
	private String dataFilter;
	
	/**字段在excel的位置（第几列）*/
	private int columnRank;
	

	public String getData_source_select() {
		return data_source_select;
	}

	public void setData_source_select(String data_source_select) {
		this.data_source_select = data_source_select;
	}

	public String getcOptionType() {
		return cOptionType;
	}

	public void setcOptionType(String cOptionType) {
		this.cOptionType = cOptionType;
	}

	public String getDataFilter() {
		return dataFilter;
	}

	public void setDataFilter(String dataFilter) {
		this.dataFilter = dataFilter;
	}

	public int getColumnRank() {
		return columnRank;
	}

	public void setColumnRank(int columnRank) {
		this.columnRank = columnRank;
	}

	

	
	
	

}
