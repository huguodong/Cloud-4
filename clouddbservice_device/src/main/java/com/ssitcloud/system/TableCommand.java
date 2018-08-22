package com.ssitcloud.system;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.DownloadCfgSyncEntity;

public interface TableCommand {
	/**
	 * 根据不同的实现，执行不同的表的下载
	 * @param downcfgSync
	 * @return
	 */
	ResultEntity execute(DownloadCfgSyncEntity downcfgSync);
}
