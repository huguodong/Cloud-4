package com.ssitcloud.operlog.service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.BasicService;

/**
 * Cardissue_Log Service
 * 
 * @author lbh
 * 
 *         2016年3月25日
 */

public interface CardissueLogService extends BasicService {
	/**
	 * 办证统计、办证增长统计 A、 根据条件统计流通信息(cardissue_log) B、 whereInfo办证查询条件 C、
	 * whereInfo的条件大致分为读者流通类型; 操作时间; 证件类型; 读者年龄段; 读者性别; 办证日期;
	 * 
	 * @Description: TODO
	 * @param @param whereInfo
	 * @param @return
	 * @return String
	 * @throws
	 * @author lbh
	 * @date 2016年3月25日
	 */
	String countCardissue(String whereInfo);

	/**
	 * 
	 * @Description: 查询Cardissue
	 * @param whereInfo
	 * @return String
	 * @throws
	 * @author lbh
	 * @date 2016年3月28日
	 */
	String queryCardissue(String whereInfo);
	/**
	 * 从mongodb查出办证信息
	 * @param req
	 * @return 
	 * @author lqw
	 * @date 2017年5月12日
	 */
	public abstract ResultEntity queryAllCardiLogFromMongodb(String req);
}
