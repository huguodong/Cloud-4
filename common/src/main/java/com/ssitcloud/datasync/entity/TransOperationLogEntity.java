package com.ssitcloud.datasync.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * transOperationLog的命令码对照类，
 * 设备传过来的日志数据中，根据opercmd操作码划分为不类型，不同类型传到mongodb数据层进行处理时，保存到不同的表中。
 * “_CODE” 为设备传过来的opercmd码，
 * 其余的对应的是对应  mongodb层中的枚举数值，参见clouddbservice_devicemonitor的 com.ssitcloud.operlog.bookenum.EnumClass
 *
 * <p>2017年3月2日 下午2:50:10  
 * @author hjc 
 *
 */
public class TransOperationLogEntity {
	/**
	 * 借还日志
	 */
	public static final String LOAN_LOG = "loan_log";
	
	/**
	 * 财经日志
	 */
	public static final String FINANCE_LOG = "finance_log";
	
	/**
	 * 办证日志
	 */
	public static final String CARDISSUE_LOG = "cardissue_log";
	
	private static Set<String> loanLogSet = null;
	private static Set<String> finLogSet = null;
	private static Set<String> cardLogSet = null;
	
	/**
	 * 详细的命令码
	 */
	private static final String LOAN_CODE = "00010201";//借书
	private static final String RETURN_CODE = "00010202";//还书
	private static final String RENEW_CODE = "00010203";//续借
	private static final String RESERVE_CODE = "00010204";//预借
	
	/*
	 * 财经
	 */
	private static final String CASH_CODE = "00010401";//现金支付
	private static final String TRANSFER_ACCOUNT_CODE = "00010402";//划账支出
	private static final String ACS_ACCOUNT_CODE = "00010403";//ACS扣款
	private static final String IC_CARD_CODE = "00010404";//一卡通扣款
	private static final String CREDIT_CARD_CODE = "00010405";//信用卡扣款
	private static final String E_PAYMENT_CODE = "00010406";//电子支付
	
	/*
	 * 办证
	 */
	private static final String CARD_TYPE1 = "00010301";//办证，出读者证
	private static final String CARD_TYPE2 = "00010302";//办证，身份证做读者证
	private static final String CARD_TYPE3 = "00010303";//办证，一卡通做读者证
	private static final String CARD_TYPE4 = "00010304";//办证，信用卡做读者证
	
	static{
		if(loanLogSet == null){
			loanLogSet = new HashSet<>();
			loanLogSet.add(LOAN_CODE);//
			loanLogSet.add(RETURN_CODE);//
			loanLogSet.add(RENEW_CODE);//
			loanLogSet.add(RESERVE_CODE);//
		}
		if(finLogSet == null){
			finLogSet = new HashSet<>();
			finLogSet.add(CASH_CODE);//
			finLogSet.add(TRANSFER_ACCOUNT_CODE);//
			finLogSet.add(ACS_ACCOUNT_CODE);//
			finLogSet.add(IC_CARD_CODE);//
			finLogSet.add(CREDIT_CARD_CODE);//
			finLogSet.add(E_PAYMENT_CODE);//
		}
		if(cardLogSet == null){
			cardLogSet = new HashSet<>();
			cardLogSet.add(CARD_TYPE1);//办证，出读者证
			cardLogSet.add(CARD_TYPE2);//办证，身份证做读者证
			cardLogSet.add(CARD_TYPE3);//办证，一卡通做读者证
			cardLogSet.add(CARD_TYPE4);//办证，信用卡做读者证
		}
	}
	
	/**
	 * 判断日志码属于哪一种日志
	 *
	 * <p>2017年3月4日 上午10:48:26 
	 * <p>create by hjc
	 * @param code
	 * @return
	 */
	public static String getTranslation(String code){
		if (loanLogSet.contains(code)) {
			return LOAN_LOG;
		}
		if (finLogSet.contains(code)) {
			return FINANCE_LOG;
		}
		if (cardLogSet.contains(code)) {
			return CARDISSUE_LOG;
		}
		return null;
	}
	
	/**
	 * 获取电子凭证类型
	 * 操作类型 1借书 2还书 3续借 4交押金 5交预付款 6交欠款 7从预付款扣除
	 *
	 * <p>2017年3月4日 上午10:50:07 
	 * <p>create by hjc
	 * @param code
	 * @return
	 */
	public static String getElectronicType(String code){
//		if(LOAN_CODE.equals(code)) return "1";
//		if(RETURN_CODE.equals(code)) return "2";
//		if(RENEW_CODE.equals(code)) return "3";
		return code;//目前除了借还书没有对应的码，所以先返回操作码
//		return null;
	}

}
