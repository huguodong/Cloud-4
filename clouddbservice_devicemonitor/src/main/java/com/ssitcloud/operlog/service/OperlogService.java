package com.ssitcloud.operlog.service;

import com.ssitcloud.common.service.BasicService;

public interface OperlogService extends BasicService {

	boolean addLoanLog(String loanlogInfo);

	/**
	 * 
	 * @param <T>
	 * @Description: 流通统计 根据条件统计流通信息(loan_log) whereInfo流通查询条件 whereInfo的条件大致分为
	 *               1.图书按22大类分； 2.借还日期(按月统计)； 3.操作类型(结果)； 4.读者办证类型； 5.读者年龄段；
	 *               6.读者性别； 7.图书馆藏地点； 8.图书流通类型； 9.媒体类型。
	 * @param whereInfo
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws
	 * @author lbh
	 * @date 2016年3月21日 {
	 * 
	 *       cardNo(读者证号):...... cirType(流通类型):[1借书,2还书,3续借]
	 *       param:["A","B"......] countType :1(统计类型) startTime:开始时间
	 *       endTime:结束时间 } { cardNo(读者证号):...... cirType(流通类型):[1借书,2还书,3续借]
	 *       param:[null] countType :2(统计类型) startTime:开始时间 endTime:结束时间 }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	String countLoanLog(String whereInfo);

}
