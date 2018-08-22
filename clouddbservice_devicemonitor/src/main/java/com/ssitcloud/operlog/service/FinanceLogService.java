package com.ssitcloud.operlog.service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.BasicService;

public interface FinanceLogService extends BasicService {

	/**
	 * 
	 * @Description: 统计财经收入
	 * @param @param whereInfo
	 * @param @return
	 * @return String
	 * @throws
	 * @author lbh
	 * @date 2016年3月28日
	 */
	String countFinance(String whereInfo);

	/**
	 * 
	 * @Description: 查询
	 * @param @param whereInfo
	 * @param @return
	 * @return String
	 * @throws
	 * @author lbh
	 * @date 2016年3月28日
	 */
	String queryFinance(String whereInfo);
	/**
	 * 从mongodb查出办财经息
	 * @param req
	 * @return 
	 * @author lqw
	 * @date 2017年5月15日
	 */
	public abstract ResultEntity queryAllFinanceLogFromMongodb(String req);
}
