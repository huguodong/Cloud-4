package com.ssitcloud.operlog.service;

import com.ssitcloud.common.service.BasicService;

public interface LogisticsService extends BasicService {

	/**
	 * 
	 * 函数说明：物流统计 A、根据条件统计物流信息(bookbox_log,cashbox_log,cardbox_log)
	 * B、whereInfo图书使用查询条件,tableName表名 C、whereInfo的条件大致分为图书流转，现金流转，卡箱补卡
	 * 
	 * @param @param whereInfo
	 * @param @param tableName
	 * @param @return
	 * @return String
	 * @throws
	 * @author lbh
	 * @date 2016年3月29日
	 */
	String countLogistics(String whereInfo, String tableName);

	/**
	 * 
	 * @Description: 查询操作
	 * @param @param whereInfo
	 * @param @param tableName
	 * @param @return
	 * @return String
	 * @throws
	 * @author lbh
	 * @date 2016年3月30日
	 */
	String queryLogistics(String whereInfo, String tableName);
}
