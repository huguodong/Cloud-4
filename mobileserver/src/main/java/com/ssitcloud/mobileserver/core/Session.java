package com.ssitcloud.mobileserver.core;

import com.ssitcloud.mobileserver.entity.*;

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;

/**
 * Created by LXP on 2017/7/20.
 *
 */

public interface Session extends Closeable{
    /**
     * 检查读者证状态
     * @param cardNo 读者证号
     * @param pwd 读者证密码
     * @return 读者证状态
     */
    ReaderCardState readerCardState(String cardNo, String pwd) throws SocketException;

    /**
     * 当且仅当用户存在，且读者证密码正确，返回用户信息。否则返回null
     * @param cardNo 读者证号
     * @param pwd 读者证密码
     * @return 当且仅当用户存在，且读者证密码正确，返回用户信息。否则返回null
     */
    ReaderInfo readerInfo(String cardNo, String pwd) throws SocketException;

    /**
     * 查询图书信息
     * @param recno 图书条码号
     * @return 返回图书信息，若不存在图书返回null
     */
    BookInfo bookinfo(String recno) throws SocketException;

    /**
     * 返回当前读者借阅信息
     * @param cardNo 读者证号
     * @param pwd 读者证密码
     * @return 读者当前的借阅信息
     */
    List<LoanBookInfo> currentLoan(String cardNo, String pwd) throws SocketException;

    /**
     * 续借图书
     * @param cardno 读者证号
     * @param pwd 读者证密码
     * @param recno 图书条码号
     * @return 续借信息
     */
    Renew renew(String cardno,String pwd,String recno) throws SocketException;

    /**
     * 预借图书
     * @param cardno 读者证号
     * @param pwd 读者证密码
     * @param barcode 图书条码号
     * @param localCode 预借地区代码
     * @return 预借信息
     */
    Reserve reserve(String cardno,String pwd,String barcode,String localCode) throws SocketException;

    /**
     * 取消预借
     * @param cardno 读者证号
     * @param pwd 读者证密码
     * @param barcode 图书条码号
     * @return 取消预借信息
     */
    Reserve inReserve(String cardno,String pwd,String barcode) throws SocketException;

    /**
     * 预借列表
     * @param cardno 读者证号
     * @param pwd 读者证密码
     * @return 读者当前未过期的预借列表
     */
    List<ReserveBookInfo> reserveList(String cardno,String pwd) throws SocketException;

    /**
     * 检查此session是否已经关闭，只有在调用了close()方法之后才能保证返回true
     * @return 如果此对象是关闭的，则返回 true；如果它仍然处于打开状态，则返回 false
     * @throws IOException 如果发生访问异常
     */
    boolean isClosed() throws IOException;
}
