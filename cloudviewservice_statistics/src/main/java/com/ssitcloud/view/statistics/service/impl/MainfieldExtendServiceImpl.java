package com.ssitcloud.view.statistics.service.impl;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.statistics.service.MainfieldExtendService;
import org.springframework.stereotype.Service;

/**
 * Created by LQW on 2017/9/1.
 */

@Service
public class MainfieldExtendServiceImpl extends BasicServiceImpl implements MainfieldExtendService {
    private static final String URL_SELECTBYMFID = "selectByMfid";
    @Override
    public ResultEntity selectByMfid(String req) {
        return postUrl(URL_SELECTBYMFID,req);
    }
}
