package com.ssitcloud.operlog.service;

import com.ssitcloud.common.service.BasicService;

public interface BookrackLogService extends BasicService {

	/**
	 * 函数说明：文献利用率统计 A、根据条件统计流通信息(bookrack_log) B、whereInfo图书使用查询条件
	 * C、whereInfo的条件大致分为图书22大类，上架时间，上架时长；
	 * 
	 * @Description: TODO
	 * @param @param whereInfo
	 * @param @return
	 * @return String
	 * @throws
	 * @author lbh
	 * @date 2016年3月29日
	 */
	String countBookUsed(String whereInfo);

}
