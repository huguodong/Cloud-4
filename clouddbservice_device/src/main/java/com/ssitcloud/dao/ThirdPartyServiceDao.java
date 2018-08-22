package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.devmgmt.entity.ThirdPartyServiceEntity;
import com.ssitcloud.entity.DisplayInfoEntity;
import com.ssitcloud.entity.page.DisplayInfoPageEntity;
import com.ssitcloud.entity.page.ThirdPartyServicePageEntity;

public interface ThirdPartyServiceDao {
	
	public List<ThirdPartyServiceEntity> queryThirdPartyServiceByParams(ThirdPartyServiceEntity entity);

	public int deleteThirdPartyService(int[] service_idxs);

	public ThirdPartyServicePageEntity queryThirdPartyServiceByPage(ThirdPartyServicePageEntity entity);

	public int editThirdPartyService(ThirdPartyServiceEntity entity);
	
	public int addThirdPartyService(ThirdPartyServiceEntity entity);

	public int deleteDisplayInfo(int[] display_info_idxs);

	public DisplayInfoPageEntity queryDisplayInfoList(DisplayInfoPageEntity entity);

	public DisplayInfoEntity queryDisplayInfo(DisplayInfoEntity entity);

	public int addDisplayInfo(DisplayInfoEntity entity);

	public int editDisplayInfo(DisplayInfoEntity entity);
}
