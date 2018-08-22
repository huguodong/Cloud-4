package com.ssitcloud.devicelog.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

//箱
@Document(collection = "bin_state")
public class BinStateEntity implements Serializable {

	private static final long serialVersionUID = -67171951259021090L;
	// id
	@Id
	private String id;
	// 状态上报时间v
	@Indexed(direction = IndexDirection.DESCENDING)
	private String updatetime;
	// 具体细节
	private List<BinStateDetail> detail;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public List<BinStateDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<BinStateDetail> detail) {
		this.detail = detail;
	}

	/**
	 * 重写equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BinStateEntity) {
			BinStateEntity binStateEntity = (BinStateEntity) obj;
			List<BinStateDetail> list = binStateEntity.getDetail();
			Map<String, String> tm1 = new HashMap<String, String>();
			Map<String, String> tm2 = new HashMap<String, String>();
			if (list != null) {
				for (BinStateDetail binStateDetail : list) {
					String key = binStateDetail.toString();
					tm1.put(key, binStateDetail.getCount());
				}
			}
			if (detail != null) {
				for (BinStateDetail binStateDetail : detail) {
					String key = binStateDetail.toString();
					tm2.put(key, binStateDetail.getCount());
				}
			}
			if (tm1.size() != tm2.size()) {
				return false;
			}
			return tm1.equals(tm2);
		}
		return false;
	}
}
