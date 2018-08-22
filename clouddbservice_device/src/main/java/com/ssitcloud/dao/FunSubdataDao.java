package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.FunSubdataEntity;

public interface FunSubdataDao {
	
	/**
	 * 查询一条子函数记录
	 * author huanghuang
	 * 2017年3月21日 下午5:31:09
	 * @param funSubdataEntity
	 * @return
	 */
	public abstract FunSubdataEntity queryOneFunSubdata(FunSubdataEntity funSubdataEntity);
	
	/**
	 * 查询多条子函数记录
	 * author huanghuang
	 * 2017年3月21日 下午5:31:13
	 * @param params
	 * @return
	 */
	public abstract List<FunSubdataEntity> queryFunSubdatas(FunSubdataEntity funSubdataEntity);
	

}
