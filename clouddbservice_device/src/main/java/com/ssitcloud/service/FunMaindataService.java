package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.FunMaindataEntity;

public interface FunMaindataService {
	
	/**
	 * 查询一条主函数记录
	 * author huanghuang
	 * 2017年3月21日 下午5:35:10
	 * @param funMaindataEntity
	 * @return
	 */
	public abstract FunMaindataEntity queryOneFunMaindata(FunMaindataEntity funMaindataEntity);
	
	/**
	 * 查询多条主函数记录
	 * author huanghuang
	 * 2017年3月21日 下午5:35:14
	 * @param params
	 * @return
	 */
	public abstract List<FunMaindataEntity> queryFunMaindatas(FunMaindataEntity funMaindataEntity);
	

}
