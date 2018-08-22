package com.ssitcloud.business.mobile.common.filter;

import com.ssitcloud.business.mobile.common.util.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 用户认证安全过滤器，用于保护读者app需要鉴权的资源
 * @author LXP
 * @version 创建时间：2017年3月27日 下午5:56:36
 */
public class ReaderAuthFilter extends AuthFilter {

    protected void forbidden(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Override
    protected void check(HttpServletRequest request) {
    }

    @Override
    protected String getAuthStr(HttpServletRequest request) {
        String authStr = request.getParameter("reader_auth");
        String authStrJson = request.getParameter("reader_auth_json");
        if(authStr == null && authStrJson != null){
            Map<String, Object> map = JsonUtils.fromJson(authStrJson, Map.class);
            authStr = (String) map.get("auth");
        }
        return authStr;
    }
}
