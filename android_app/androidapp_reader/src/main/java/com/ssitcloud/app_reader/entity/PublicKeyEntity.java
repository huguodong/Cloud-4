package com.ssitcloud.app_reader.entity;

/**
 * Created by LXP on 2017/4/14.
 * 公钥对象
 */

public class PublicKeyEntity {
    private String publickey;
    private String key_version;
    private Long createtime;

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public String getKey_version() {
        return key_version;
    }

    public void setKey_version(String key_version) {
        this.key_version = key_version;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }
}
