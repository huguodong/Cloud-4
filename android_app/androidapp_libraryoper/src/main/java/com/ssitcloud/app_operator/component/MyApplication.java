package com.ssitcloud.app_operator.component;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * 创建日期：2017/3/22 17:10
 *
 * @author shuangjunjie
 */

public class MyApplication extends Application {
    private final String SHAREPREFER_NAME = "MyApplication_LOGIN_INFO";

    /**
     * 自增长ID
     */
    private Integer operator_idx;
    /**
     * 用户ID
     */
    private String operator_id;
    /**
     * 用户名
     */
    private String operator_name;
    /**
     * 图书馆id
     */
    private String lib_id;
    /**
     * 图书馆名
     */
    private String lib_name;
    /**
     * 图书馆idx
     */
    private Integer library_idx;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 身份证号
     */
    private String id_card;
    /**
     * 用户类型
     */
    private String operator_type;

    public Integer getOperator_idx() {
        if (operator_idx == null) {
            SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
            operator_idx = ((Integer) sp.getAll().get("operator_idx"));
        }
        return operator_idx;
    }

    public void setOperator_idx(Integer operator_idx) {
        this.operator_idx = operator_idx;
        if(operator_idx != null) {
            SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME, MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putInt("operator_idx", operator_idx);
            edit.apply();
        }
    }

    public String getOperator_id() {
        if (operator_id == null) {
            SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
            operator_id = sp.getString("operator_id", null);
        }
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
        SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("operator_id", operator_id);
        edit.apply();
    }

    public String getOperator_name() {
        if (operator_name == null) {
            SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
            operator_name = sp.getString("operator_name", null);
        }
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
        SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("operator_name", operator_name);
        edit.apply();
    }

    public String getLib_id() {
        if (lib_id == null) {
            SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
            lib_id = sp.getString("lib_id", null);
        }
        return lib_id;
    }

    public void setLib_id(String lib_id) {
        this.lib_id = lib_id;
        SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("lib_id", lib_id);
        edit.apply();
    }

    public String getLib_name() {
        if (lib_name == null) {
            SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
            lib_name = sp.getString("lib_name", null);
        }
        return lib_name;
    }

    public void setLib_name(String lib_name) {
        this.lib_name = lib_name;
        SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("lib_name", lib_name);
        edit.apply();
    }

    public Integer getLibrary_idx() {
        if (library_idx == null) {
            SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
            library_idx = ((Integer) sp.getAll().get("library_idx"));
        }
        return library_idx;
    }

    public void setLibrary_idx(Integer library_idx) {
        this.library_idx = library_idx;
        if(library_idx != null) {
            SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME, MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putInt("library_idx", library_idx);
            edit.apply();
        }
    }

    public String getMobile() {
        if (mobile == null) {
            SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
            mobile = sp.getString("mobile", null);
        }
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("mobile", mobile);
        edit.apply();
    }

    public String getEmail() {
        if (email == null) {
            SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
            email = sp.getString("email", null);
        }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("email", email);
        edit.apply();
    }

    public String getId_card() {
        if (id_card == null) {
            SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
            id_card = sp.getString("id_card", null);
        }
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
        SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("id_card", id_card);
        edit.apply();
    }

    public String getOperator_type() {
        if (operator_type == null) {
            SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
            operator_type = sp.getString("operator_type", null);
        }
        return operator_type;
    }

    public void setOperator_type(String operator_type) {
        this.operator_type = operator_type;
        SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("operator_type", operator_type);
        edit.apply();
    }

    public void deleteAll() {
        SharedPreferences sp = getSharedPreferences(SHAREPREFER_NAME,MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.apply();
    }
}
