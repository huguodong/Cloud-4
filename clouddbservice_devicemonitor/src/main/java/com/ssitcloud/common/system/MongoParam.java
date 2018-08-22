package com.ssitcloud.common.system;

/**
 * Created by LXP on 2017/8/24.
 */

public class MongoParam {
    private String host;
    private int port;
    private String opacDb;
    private String user;
    private String pwd;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getOpacDb() {
        return opacDb;
    }

    public void setOpacDb(String opacDb) {
        this.opacDb = opacDb;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


}
