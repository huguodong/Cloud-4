package com.ssitcloud.app_reader.biz;

import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.entity.AppElectronicEntity;

import java.net.SocketException;
import java.util.Date;
import java.util.List;

/**
 * Created by LXP on 2017/3/30.
 * 电子凭证业务接口
 */

public interface ElecBizI {
    /**
     * 从服务器获取电子凭证
     * @param reader_idx 用户idx
     * @param date 获取改时间前电子凭证
     * @return
     * @throws SocketException
     * @throws AuthException
     */
    List<AppElectronicEntity> obtainElecByService(Integer reader_idx, Date date,Integer pageSize,Integer pageCurrent) throws SocketException,AuthException;

    /**
     * 获取未读电子凭证
     * @return
     * @throws SocketException
     * @throws AuthException
     */
    List<AppElectronicEntity> obtainUnReaderElecByService(Integer reader_idx, Integer pageSize, Integer pageCurrent) throws SocketException, AuthException;

    List<AppElectronicEntity> obtainElecByService(Integer reader_idx, Date date, Integer state, Integer pageSize, Integer pageCurrent) throws SocketException, AuthException;

    /**
     * 从数据库中获取电子凭证
     * @return
     */
    List<AppElectronicEntity> obtainElec();

    /**
     * 设置已读电子凭证
     * @param reader_idx
     * @param ids
     * @throws SocketException
     * @throws AuthException
     */
    void setReadElec(Integer reader_idx,List<Integer> ids) throws SocketException, AuthException;

    List<String> getRecommendList(Integer library_idx,String card_no)throws SocketException, AuthException;
}
