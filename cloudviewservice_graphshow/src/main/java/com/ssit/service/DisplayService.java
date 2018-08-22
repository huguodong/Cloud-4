package com.ssit.service;

import java.util.Map;

import com.ssit.common.entity.ResultEntity;

public interface DisplayService {
	// 办证
	public ResultEntity countCertificateByParam(Map<String, String> param);

	// 流通（借还）
	public ResultEntity countCirculateByParam(Map<String, String> param);
	
	// 流通（借还）分时统计
	public ResultEntity countCirculateForBar(Map<String, String> param);

	// 财经
	public ResultEntity countFinanceByParam(Map<String, String> param);
	
	// 人流量
	public ResultEntity countPatronByParam(Map<String, String> param);
	
	// 人流量（多门）
	public ResultEntity countPatronByDoors(Map<String, String> param);
	
	//人流量分时统计
	public ResultEntity countPatronForRealtime(Map<String, String> param);

	// 图书排行
	public ResultEntity bookRankByParam(Map<String, String> param);

	// 读者排行
	public ResultEntity readerRankByParam(Map<String, String> param);
	
	// 图书馆信息
	public ResultEntity loadLibInfo(Map<String, String> param);

	//根据条件查询第三方服务
	public ResultEntity queryThirdPartyServiceByParams(Map<String, String> param);

	//查询大屏显示信息
	public ResultEntity queryDisplayInfo(Map<String, String> param);
}
