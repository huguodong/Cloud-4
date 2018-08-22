package com.ssitcloud.mobileserver.param;

import java.util.Map;

/**
 * Created by LXP on 2017/7/24.
 */

public class WebServiceParam {
    private Map<String, String> headers;
    private byte[] body;

    public WebServiceParam(Map<String, String> headers, byte[] body) {
        this.headers = headers;
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
