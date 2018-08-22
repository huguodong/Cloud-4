package com.ssitcloud.common.entity;

import java.util.Map;

/**
 * 用于储存数据层URL信息
 * 
 * @package: com.ssitcloud.common.entity
 * @classFile: RequestURLListEntity
 * @author: liuBh
 * @description: TODO
 */
public class RequestURLListEntity {

	private Map<String, String> map;

	public RequestURLListEntity() {
	}

	public RequestURLListEntity(Map<String, String> map) {
		this.map = map;
	}

	public String getRequestURL(String id) {
		if (map != null) {
			return map.get(id);
		}
		return null;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

}
