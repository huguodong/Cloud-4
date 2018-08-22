package com.ssitcloud.app_reader.view.viewInterface;

import com.ssitcloud.app_reader.entity.AppElectronicEntity;

import java.util.List;

/**
 * Created by LXP on 2017/3/30.
 *
 */

public interface ElectronicCertificateViewI {

    void setData(List<AppElectronicEntity> elecListData);

    /**
     *
     * @param code -1未登陆，-2网络连接失败
     */
    void setFail(int code);
}
