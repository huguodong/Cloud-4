package com.ssitcloud.devicelog.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.ssitcloud.common.util.DateUtil;

public class ProptiesTemplate {
	private final static ResourceBundle extTempProp = ResourceBundle
			.getBundle("StatusMonitor/ExtStatusTemplate");

	/**
	 * 
	 * @Description: 获取ExtStateDetail状态模板
	 * @param @return
	 * @return List<ExtStateEntity>
	 * @throws
	 * @author lbh
	 * @date 2016年3月16日
	 */
	public static List<ExtStateDetail> getExtStateDetailTemplate() {
		Set<String> extTempKeySet = extTempProp.keySet();
		List<ExtStateDetail> list = new ArrayList<ExtStateDetail>();
		for (String key : extTempKeySet) {
			String strs = extTempProp.getString(key);
			// assert arr[0]=device name; arr[1]=device state
			String[] arr = strs.split("\\|");
			list.add(new ExtStateDetail(arr[0], arr[1]));
		}
		return list;
	}

	/**
	 * 
	 * @Description: 获取ExtStateEntity状态模板,无ID
	 * @param @return
	 * @return List<ExtStateEntity>
	 * @throws
	 * @author lbh
	 * @date 2016年3月16日
	 */
	public static ExtStateEntity getExtStateEntityTemplate() {
		ExtStateEntity extState = new ExtStateEntity();
		List<ExtStateDetail> list = getExtStateDetailTemplate();
		if (list != null && list.size() > 0) {
			extState.setUpdatetime(DateUtil.getDateDefaultFormat());
			extState.setDetail(list);
			return extState;
		}
		return null;
	}
}
