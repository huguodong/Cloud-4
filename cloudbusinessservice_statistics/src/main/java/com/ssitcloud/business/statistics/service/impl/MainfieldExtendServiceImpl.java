package com.ssitcloud.business.statistics.service.impl;

import com.ssitcloud.business.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.statistics.service.MainfieldExtendService;
import com.ssitcloud.common.entity.ResultEntity;
import org.springframework.stereotype.Service;

/**
 * Created by LQW on 2017/9/1.
 */

@Service
public class MainfieldExtendServiceImpl extends BasicServiceImpl implements MainfieldExtendService {
    private static final String URL_SELECTBYMFID = "selectByMfid";
    @Override
    public ResultEntity selectByMfid(String req) {
        return postURL(URL_SELECTBYMFID,req);
    }
}
