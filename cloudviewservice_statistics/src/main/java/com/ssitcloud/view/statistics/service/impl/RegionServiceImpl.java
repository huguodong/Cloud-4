package com.ssitcloud.view.statistics.service.impl;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.statistics.service.RegionService;
import org.apache.http.Consts;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.ssitcloud.view.statistics.common.util.HttpClientUtil;
/**
 * Created by LXP on 2017/8/24.
 */
@Service
public class RegionServiceImpl extends BasicServiceImpl implements RegionService {
    public static final String URL_SELREGIONSBYLIBIDX = "selRegionsByLibidx";
    public static final String URL_SELECTRELLIBSBYID = "selectRelLibsByid";
    public static final String URL_QUERYLIBINFO = "querylibInfoByCurUserEXT1";
    @Override
    public ResultEntity selRegionsByLibidx(String req) {
        return postUrl(URL_SELREGIONSBYLIBIDX,req);
    }
    @Override
    public ResultEntity selectRelLibsByid(String req) {
        return postUrl(URL_SELECTRELLIBSBYID,req);
    }
    @Override
    public String querylibInfoByCurUserEXT1(Map<String, String> map) {
        String requrl = requestURL.getRequestURL(URL_QUERYLIBINFO);
        String result = HttpClientUtil.doPost(requrl, map, Consts.UTF_8.toString());
        return result;
    }
}
