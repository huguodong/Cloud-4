package com.ssitcloud.dbauth.service;

import java.util.List;
import java.util.Map;

/**
 * Created by lqw on 2017/8/29.
 */

public interface DynamicSqlService {
    List<Map<String,Object>> selectBySql(String sql);
}
