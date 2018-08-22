package com.ssitcloud.mobileserver.handles;

import com.ssitcloud.mobileserver.entity.*;
import com.ssitcloud.mobileserver.exception.AnalysisDataException;

import java.util.List;

/**
 * Created by LXP on 2017/7/21.
 */

public interface IlasWebServiceHandles {

    /**
     * 查询读者状态信息body
     * @param cardNo 读者证号
     * @param pwd 读者证密码
     * @return 读者状态信息body
     */
    String readerCardStateBody(String cardNo, String pwd);

    /**
     * 解析服务器传回数据为读者状态信息
     * @param message 服务器传回信息
     * @return 读者状态信息
     * @throws AnalysisDataException 转换失败时抛出
     */
    ReaderCardState analysisReaderCardState(String message) throws AnalysisDataException;

    /**
     * 查询读者信息body
     * @param cardNo 读者证号
     * @param pwd 读者证密码
     * @return 读者信息body
     */
    String readerInfoBody(String cardNo, String pwd);

    /**
     * 解析服务器传回信息为读者信息
     * @param message 服务器传回信息
     * @return 读者信息
     * @throws AnalysisDataException 转换失败时抛出
     */
    ReaderInfo analysisReaderInfo(String message) throws AnalysisDataException;

    /**
     * 查询图书信息body
     * @param recno 图书条码号
     * @return 图书信息body
     */
    String bookInfoBody(String recno);

    /**
     * 解析服务器数据为图书信息
     * @param message 服务器传回信息
     * @return 图书信息
     * @throws AnalysisDataException 转换失败时抛出
     */
    BookInfo analysisBookInfo(String message) throws AnalysisDataException;

    /**
     * 读者当前借阅信息body
     * @param cardNo 读者证号
     * @param pwd 读者证密码
     * @return 读者当前借阅信息body
     */
    String currentLoanBody(String cardNo,String pwd);

    /**
     * 解析服务器数据为当前借阅信息
     * @param message 服务器传回信息
     * @return 当前借阅信息
     * @throws AnalysisDataException 转换失败时抛出
     */
    List<LoanBookInfo> analysisCurrentLoan(String message) throws AnalysisDataException;

    /**
     * 续借body
     * @param cardno 读者证号
     * @param pwd 读者证密码
     * @param recno 图书条码号
     * @return 续借body
     */
    String renewBody(String cardno,String pwd,String recno);

    /**
     * 解析服务器数据为续借信息
     * @param message 服务器传回信息
     * @return 续借信息
     * @throws AnalysisDataException 转换失败时抛出
     */
    Renew analysisRenew(String message) throws AnalysisDataException;

    /**
     * 预借body
     * @param cardno 读者证号
     * @param pwd 读者证密码
     * @param barcode 图书条码号
     * @param noticeModel 通知方式
     * @param localCode 投递地点代码
     * @return 预借body
     */
    String reserveBody(String cardno,String pwd,String barcode,String noticeModel,String localCode);

    /**
     * 解析服务器数据为预借信息
     * @param message 服务器传回信息
     * @return 预借信息
     * @throws AnalysisDataException 转换失败时抛出
     */
    Reserve analysisReserve(String message) throws AnalysisDataException;

    /**
     * 预借列表body
     * @param cardno 读者证号
     * @param pwd 读者证密码
     * @return 预借列表body
     */
    String reserveListBody(String cardno,String pwd);

    /**
     * 解析服务器数据为当前预借列表
     * @param message 服务器传回信息
     * @return 当前预借列表
     * @throws AnalysisDataException 转换失败时抛出
     */
    List<ReserveBookInfo> analysisReserveList(String message) throws AnalysisDataException;

    /**
     * 取消预借body
     * @param cardno 读者证号
     * @param pwd 读者证密码
     * @param barcode 图书条码号
     * @return 取消预借body
     */
    String inReserveBody(String cardno,String pwd,String barcode);

    /**
     * 解析服务器数据为取消预借信息
     * @param message 服务器传回信息
     * @return 取消预借信息
     * @throws AnalysisDataException 转换失败时抛出
     */
    Reserve analysisInReserve(String message) throws AnalysisDataException;
}
