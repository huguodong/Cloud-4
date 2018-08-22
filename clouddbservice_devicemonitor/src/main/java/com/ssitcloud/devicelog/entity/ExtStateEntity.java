package com.ssitcloud.devicelog.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

//外设状态
@JsonInclude(value = Include.NON_NULL)
@Document(collection = "ext_state")
public class ExtStateEntity implements Serializable {

	private static final long serialVersionUID = -1930369209755830338L;

	@Id
	private String id;

	// 状态上报时间
	@Field(value = "updatetime", order = 1)
	@JsonProperty(value = "updatetime")
	@Indexed(direction = IndexDirection.DESCENDING)
	private String updatetime;
	// 外设状态 ext_type：ext_state
	@Field(value = "detail")
	@JsonProperty(value = "detail")
	private List<ExtStateDetail> detail;

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

	public List<ExtStateDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<ExtStateDetail> detail) {
		this.detail = detail;
	}

	/**
	 * 仅仅判断状态值是否相同
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ExtStateEntity) {
			ExtStateEntity extStateEntity = (ExtStateEntity) obj;
			List<ExtStateDetail> list = extStateEntity.getDetail();
			Map<String, String> tm1 = new HashMap<String, String>();
			Map<String, String> tm2 = new HashMap<String, String>();
			if (list != null) {
				for (ExtStateDetail extStateDetail : list) {
					tm1.put(extStateDetail.getLogicObj(),
							extStateDetail.getLogicState());
				}
			}
			if (detail != null) {
				for (ExtStateDetail extStateDetail : detail) {
					tm2.put(extStateDetail.getLogicObj(),
							extStateDetail.getLogicState());
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
