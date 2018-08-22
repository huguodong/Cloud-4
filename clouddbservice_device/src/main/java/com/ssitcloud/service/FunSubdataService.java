package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.FunSubdataEntity;

public interface FunSubdataService {
	/**
	 * 查询一条子函数记录
	 * author huanghuang
	 * 2017年3月21日 下午5:39:14
	 * @param funSubdataEntity
	 * @return
	 */
	public abstract FunSubdataEntity queryOneFunSubdata(FunSubdataEntity funSubdataEntity);
	
	/**
	 * 查询多条子函数记录
	 * author huanghuang
	 * 2017年3月21日 下午5:39:18
	 * @param params
	 * @return
	 */
	public abstract List<FunSubdataEntity> queryFunSubdatas(FunSubdataEntity funSubdataEntity);
	
}
