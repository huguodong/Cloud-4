package com.ssitcloud.app_operator.biz;

import com.ssitcloud.app_operator.common.entity.ResultEntity;
import com.ssitcloud.app_operator.db.entity.MessageRemindDbEntity;

import java.net.SocketException;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 创建日期：2017/3/22 19:10
 * @author shuangjunjie
 */

public interface MessageRemindBizI {
    /**
     * int[0] == unRead int[1] == all message count
     * @return int[]
     */
    int[] getMessageRemindCount();

    List<MessageRemindDbEntity> getMessageRemind(Integer page, Integer pageSize);
//
//    /**
//     * 消息提醒（查询故障消息）
//     */
//    Observable<List<MessageRemindDbEntity>> selectDeviceTroublesByLibIdxs(Map<String, Object> reqMap);

    Observable<List<MessageRemindDbEntity>> selectUnReadDeviceTroublesByLibIdxs();

    boolean insertMessageRemind(List<MessageRemindDbEntity> messageRemindList);

    Observable<Void> updateDeviceTroubles(List<Integer> troubleIdxs);
}
