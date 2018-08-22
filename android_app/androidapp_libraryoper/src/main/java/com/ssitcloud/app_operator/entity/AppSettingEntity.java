package com.ssitcloud.app_operator.entity;

/**
 * 个人菜单设置表
 * author huanghuang
 * 2017年2月22日09:26:09被LXP修改
 * 2017年2月10日 上午10:49:05
 * 2017年3月18号 增加image_url字段 lqw
 */
public class AppSettingEntity {
    private Integer setting_idx;//int(11) NOT NULL 设置ID
    private String user_type;//varchar(2) NULL 用户类型代码1馆员　2读者
    private String image_url;//主页面上图片地址
    private Integer lib_idx;//int(11) NOT NULL 图书馆IDX
    private String service_id;//char NULL APP页面代码
    private String setting_desc;//varchar(50) NULL 功能面描述
    private Integer setting_sort;//排序字段，数据库中默认0。在查询条件中为null则升序，不为null则降序

    public Integer getSetting_idx() {
        return setting_idx;
    }
    public void setSetting_idx(Integer setting_idx) {
        this.setting_idx = setting_idx;
    }
    public String getUser_type() {
        return user_type;
    }
    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getImage_url() {
        return image_url;
    }
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
    public Integer getLib_idx() {
        return lib_idx;
    }
    public void setLib_idx(Integer lib_idx) {
        this.lib_idx = lib_idx;
    }
    public String getService_id() {
        return service_id;
    }
    public void setService_id(String service_id) {
        this.service_id = service_id;
    }
    public String getSetting_desc() {
        return setting_desc;
    }
    public void setSetting_desc(String setting_desc) {
        this.setting_desc = setting_desc;
    }
    public Integer getSetting_sort() {
        return setting_sort;
    }
    public void setSetting_sort(Integer setting_sort) {
        this.setting_sort = setting_sort;
    }

}
