package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.FunMaindataEntity;
import com.ssitcloud.entity.FunSubdataEntity;

public interface FunMaindataDao {
	/**
	 * 查询一条主函数记录
	 * author huanghuang
	 * 2017年3月21日 下午5:30:18
	 * @param funMaindataEntity
	 * @return
	 */
	public abstract FunMaindataEntity queryOneFunMaindata(FunMaindataEntity funMaindataEntity);
	
	/**
	 * 查询多条主函数记录
	 * author huanghuang
	 * 2017年3月21日 下午5:30:43
	 * @param params
	 * @return
	 */
	public abstract List<FunMaindataEntity> queryFunMaindatas(FunMaindataEntity funMaindataEntity);
	

}
