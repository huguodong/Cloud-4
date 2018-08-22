package com.ssitcloud.app_reader.biz;

import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.db.entity.ReaderCardDbEntity;
import com.ssitcloud.app_reader.entity.BindCardEntity;

import java.net.SocketException;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by LXP on 2017/3/9.
 * 读者卡服务类
 */

public interface ReaderCardBizI {
    /**
     * 从服务器获取读者卡信息，使用默认排序（图书馆&&卡号，降序排序<br/>
     * <strong>此操作会更新本地数据库缓存信息</strong>
     * @throws SocketException 网络异常
     * @return
     */
    List<ReaderCardDbEntity> obtainReaderCardByService(Integer reader_idx) throws SocketException,AuthException;

    Observable<ReaderCardDbEntity> obtainReaderCardByService(Integer reader_idx, Integer lib_idx, String cardno);

    /**
     * 从数据库获取读者卡信息，使用默认排序（图书馆&&卡号，降序排序）
     * @return
     */
    List<ReaderCardDbEntity> obtainReaderCardByBb();

    boolean unbindCard(ReaderCardDbEntity cardInfo, Integer reader_idx) throws SocketException,AuthException;

    /**
     * 返回码
     * @param cardInfo
     * @param reader_idx
     * @return 0 成功，-1 卡无效，-2卡密码错误 -3卡已经被绑定 -4图书馆暂时无法提供服务
     * @throws SocketException
     */
    int bindCard(BindCardEntity cardInfo, Integer reader_idx) throws SocketException,AuthException;

    /**
     * 获取偏好卡，若没有偏好卡则返回本地数据库首张卡，若没有绑定卡则返回null
     * @return 偏好卡，若没有偏好卡则返回本地数据库首张卡，仅仅当没有绑定卡返回null
     */
    ReaderCardDbEntity obtainPreferCard();

    /**
     * 设置偏好卡
     * @param readerCard 至少应该设置card_no和lib_idx
     */
    void setPreferCard(ReaderCardDbEntity readerCard);
}
