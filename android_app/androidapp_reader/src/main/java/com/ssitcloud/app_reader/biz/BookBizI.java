package com.ssitcloud.app_reader.biz;

import com.ssitcloud.app_reader.common.exception.AuthException;
import com.ssitcloud.app_reader.common.exception.ReaderCardInvalidException;
import com.ssitcloud.app_reader.entity.BibliosEntity;
import com.ssitcloud.app_reader.entity.BibliosPageEntity;
import com.ssitcloud.app_reader.entity.BookUnionEntity;
import com.ssitcloud.app_reader.entity.InReservationEntity;
import com.ssitcloud.app_reader.entity.RenewEntity;
import com.ssitcloud.app_reader.entity.ReservationBookEntity;
import com.ssitcloud.app_reader.entity.ReservationEntity;
import com.ssitcloud.app_reader.entity.ReservationMessage;
import com.ssitcloud.app_reader.entity.StaticsTypeEntity;

import java.net.SocketException;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/3/21.
 * 续借业务接口
 */

public interface BookBizI {
    /**
     * 获取读者卡在借书数据
     * @param reader_idx 读者id
     * @param lib_idx 图书馆id
     * @param card_no 卡号
     * @throws SocketException 连接失败
     * @throws AuthException 访问被拒绝
     * @throws ReaderCardInvalidException 读者卡失效
     */
    List<BibliosPageEntity> obtainRenewBook(Integer reader_idx,Integer lib_idx,String card_no) throws SocketException,ReaderCardInvalidException,AuthException;

    RenewEntity renew(Integer reader_idx,Integer lib_idx,String card_no,String booksn)throws SocketException,ReaderCardInvalidException,AuthException;

    /**
     * 获取设备上的书目
     * @param selectData 搜索参数，可选search_str
     * @param idData 设备id数据，应该填入AppOPACEntity.idData
     * @param pageCurrent 分页参数
     * @param pageSize 分页参数
     * @throws SocketException 网络连接失败
     */
    List<BookUnionEntity> findDeviceBook(Map<String,Object> selectData, Map<String,Object> idData, Integer pageCurrent, Integer pageSize) throws SocketException;

    /**
     * 获取数目详情信息
     * @param bib_idx 书目索引号
     * @throws SocketException 网络连接失败
     */
    BibliosEntity findBibliosEntity(Integer bib_idx) throws SocketException;

    /**
     * 预借图书
     * @throws SocketException 网络连接失败
     * @throws AuthException 拒绝访问
     */
    ReservationMessage reservationBook(ReservationEntity entity, Map<String,Object> idData) throws SocketException,AuthException;

    /**
     * 取消预借
     * @throws SocketException 网络连接失败
     * @throws AuthException 拒绝访问
     */
    ReservationMessage inReservationBook(InReservationEntity entity ) throws SocketException, AuthException;

    List<ReservationBookEntity> reservationBookList(ReservationEntity entity) throws SocketException,AuthException;

    /**
     * 查询图书状态
     * @param bookItem 图书在架id
     * @return 1借出，2入藏，3预借
     * @throws SocketException 网络连接失败
     */
    String queryReservationAble(Integer bookItem) throws SocketException;

    /**
     * 获取图书分类
     * @return 图书分类
     * @throws SocketException 网络连接失败
     */
    List<StaticsTypeEntity> bookClassify() throws SocketException;
}
