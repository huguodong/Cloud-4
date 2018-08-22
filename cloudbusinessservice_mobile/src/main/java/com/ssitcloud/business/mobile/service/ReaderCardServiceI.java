package com.ssitcloud.business.mobile.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.ReaderCardEntity;

/**
 * 读者卡服务
 * @author LXP
 * @version 创建时间：2017年3月1日 下午3:38:33
 */
public interface ReaderCardServiceI {
	/**
	 * 查询读者绑定的读者卡信息
	 * @param readerCardEntity查询信息
	 * @return 包含读者卡信息的map的list,查询不到信息或者查询失败均返回null。注意！此数据包含用户密码，请发送时删除用户密码
	 */
	List<Map<String, Object>> selectReaderCards(ReaderCardEntity readerCardEntity);
	
	/**
	 * 解绑读者证
	 * @param data
	 * @return 若成功ResultEntity中state为true
	 */
	ResultEntity unbindCard(Map<String, Object> data);
	
	/**
	 * 批量解绑读者证
	 * @param data
	 * @return 若全部解绑成功ResultEntity中state为true，若失败result会包含一个数组，数组中包含解绑失败的卡信息
	 */
	ResultEntity unbindCard(List<Map<String, Object>> data);
	
	/**
	 * 绑定读者卡
	 * @param readercard 需要提供图书馆id，卡号，密码，读者id
	 * @return
	 */
	ResultEntity bindCard(ReaderCardEntity readercard);
	
	/**
	 * 查询读者卡下借书列表
	 * @param readerCard 需要提供图书馆id，卡号，密码，读者id
	 * @return
	 */
	ResultEntity selectCardBooks(ReaderCardEntity readerCard);
}
