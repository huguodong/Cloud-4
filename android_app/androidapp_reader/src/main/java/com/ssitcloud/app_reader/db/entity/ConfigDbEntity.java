package com.ssitcloud.app_reader.db.entity;

/**
 * Created by LXP on 2017/3/16.
 * app config dao entity
 */

public class ConfigDbEntity {
    private String configKey;
    private String configValue;

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}
