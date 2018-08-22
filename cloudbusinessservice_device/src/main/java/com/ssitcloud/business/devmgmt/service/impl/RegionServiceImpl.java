package com.ssitcloud.business.devmgmt.service.impl;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.devmgmt.service.RegionService;
import com.ssitcloud.common.entity.ResultEntity;
import org.springframework.stereotype.Service;

/**
 * Created by LXP on 2017/8/24.
 */
@Service
public class RegionServiceImpl extends BasicServiceImpl implements RegionService {
    public static final String URL_SELREGIONSBYLIBIDX = "selRegionsByLibidx";
    public static final String URL_SELECTRELLIBSBYID = "selectRelLibsByid";
    @Override
    public ResultEntity selRegionsByLibidx(String req) {
        return postUrl(URL_SELREGIONSBYLIBIDX,req);
    }

    @Override
    public ResultEntity selectRelLibsByid(String req) {
        return postUrl(URL_SELECTRELLIBSBYID,req);
    }
}
