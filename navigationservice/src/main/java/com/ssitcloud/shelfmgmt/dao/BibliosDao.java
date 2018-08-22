package com.ssitcloud.shelfmgmt.dao;

import com.ssitcloud.shelfmgmt.entity.BibliosBean;


public interface BibliosDao {
	
	public abstract BibliosBean queryMarcDataByItemCtrlNum(BibliosBean biblios);
	
	public abstract int addBiblios(BibliosBean biblios);
}
