package com.ssitcloud.business.mobile.acs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.common.util.StringUtils;
import com.ssitcloud.libraryinfo.entity.BibliosPageEntity;
import com.ssitcloud.mobile.entity.ReaderCardEntity;
import com.ssitcloud.mobile.entity.ReservationBookEntity;
import com.ssitcloud.mobile.entity.ReservationMessage;

/**
 * acs转换器，转换acs map信息到实体类
 * @author LXP
 * @version 创建时间：2017年3月4日 上午9:10:21
 */
public class AcsConverter {
	public static enum user_card_state{CARE_INVALID,CARE_PASSWORD_ERROR,CARE_SUCCESS,UNKNOW_ERROR}
	
	/**
	 * map转换ReaderCardEntity
	 * @param readerMap
	 * @return 若失败返回null
	 */
	public static ReaderCardEntity converterReaderCardEntity(Map<String, Object> readerMap){
		if(verifyUserState(readerMap) != user_card_state.CARE_SUCCESS){
			return null;
		}
		ReaderCardEntity readerCard = new ReaderCardEntity();
		try{
			Object obj = readerMap.get("HOLDITEMSLIMIT");
			int count = 0;
			if(obj != null && !(obj.toString().isEmpty())){
				try{
					count = Integer.valueOf(readerMap.get("HOLDITEMSLIMIT").toString());
				}catch(Exception e){
					count = 0;
				}
			}
			
			readerCard.setAllown_loancount(count);
			
			int loan = 0;
			obj = readerMap.get("BOOKSN");
			if(obj != null && !(obj.toString().isEmpty())){
				loan = obj.toString().split(",").length;
			}
			int over = 0;
			obj = readerMap.get("OVERDUE");
			if(obj != null && !(obj.toString().isEmpty())){
				over = obj.toString().split(",").length;
			}
			
			//剩余借阅数
			int alreadyBorrow = count-loan-over;
			readerCard.setSurplus_count(alreadyBorrow>-1?alreadyBorrow:0);
			
			if(readerMap.get("PATRONDESPOSIT") != null){
				double qiankuan = StringUtils.parseDouble((String.valueOf(readerMap.get("PATRONDESPOSIT"))));
				readerCard.setDeposit(qiankuan);
			}else{
				readerCard.setDeposit(0.0);
			}
			//读者姓名
			readerCard.setReader_name((String) readerMap.get("PATRONNAME"));
		}catch(Exception e){
			LogUtils.info(e);
			return null;
		}
		return readerCard;
	}
	
	/**
	 * 验证用户卡是否有效
	 * @param userData 用户的数据
	 * @return
	 */
	public static user_card_state verifyUserState(Map<String, Object> userData){
		Object cardInvalid = userData.get("PATRONVALID");
		Object patronPass = userData.get("PATRONPASS");
		if(cardInvalid == null || patronPass == null){//检查是否操作成功
			return user_card_state.UNKNOW_ERROR;
		}else if(!"y".equalsIgnoreCase(String.valueOf(cardInvalid))){
			return user_card_state.CARE_INVALID;
		}else if(!"y".equalsIgnoreCase(String.valueOf(patronPass))){
			return user_card_state.CARE_PASSWORD_ERROR;
		}else{
			return user_card_state.CARE_SUCCESS;
		}
	}
	
	/**
	 * 转换为图书页面信息
	 * @param bookData
	 * @return
	 */
	public static BibliosPageEntity converterBibliosEntity(Map<String, Object> bookData){
		BibliosPageEntity bibliosEntity = new BibliosPageEntity();
		bibliosEntity.setTitle((String)bookData.get("BOOKTITLE"));
		String s = (String) bookData.get("BOOKSN");
		if(s != null){
			bibliosEntity.setBook_barcode(s);
		}
		String currencyType = (String) bookData.get("CURRENCYTYPE");
		if("c".equalsIgnoreCase(currencyType)){
			bibliosEntity.setState(1);
		}else if("b".equalsIgnoreCase(currencyType)){
			bibliosEntity.setState(2);
		}else if("m".equalsIgnoreCase(currencyType)){
			bibliosEntity.setState(3);
		}
		bibliosEntity.setPublish((String) bookData.get("PRINTLINE"));
		bibliosEntity.setAuthor((String) bookData.get("OWNER"));
		bibliosEntity.setReturnDate((String)bookData.get("SHOULDRETURNDATE"));
		bibliosEntity.setLoanDate(null);//借出日期
		return bibliosEntity;
	}
	
	/**
	 * 转换为预借图书页面信息
	 * @param bookData
	 * @return
	 */
	public static ReservationBookEntity converterBibliosByReconEntity(Map<String, Object> bookData){
		ReservationBookEntity entity = new ReservationBookEntity();
		entity.setTitle((String)bookData.get("BOOKTITLE"));
		entity.setBook_barcode((String) bookData.get("BOOKSN"));
		entity.setPublish((String) bookData.get("PRINTLINE"));
		entity.setAuthor((String) bookData.get("OWNER"));
		return entity;
	}
	
	/**
	 * 转换为预借图书页面信息
	 * @param bookData
	 * @return
	 */
	public static ReservationBookEntity converterBibliosByReconEntity(ReservationBookEntity entity,Map<String, Object> bookData){
		entity.setTitle((String)bookData.get("BOOKTITLE"));
		String s = (String) bookData.get("BOOKSN");
		if(s != null){
			entity.setBook_barcode(s);
		}
		entity.setPublish((String) bookData.get("PRINTLINE"));
		entity.setAuthor((String) bookData.get("OWNER"));
		return entity;
	}
	
	/**
	 * 转换为预借列表数据
	 * @param reservationData
	 * @return
	 */
	public static List<ReservationBookEntity> converterReservationList(Map<String, Object> reservationData){
		String result = String.valueOf(reservationData.get("OPERATIONRESULT"));
		if(!"y".equalsIgnoreCase(result) 
				&& !"1".equalsIgnoreCase(result) 
				&& !"yes".equalsIgnoreCase(result)){//检查查询是否成功
			return new ArrayList<>(0);
		}
		
		String temp = (String) reservationData.get("BOOKSN");
		String[] booksns = null;//图书条码
		if(!StringUtils.isEmpty(temp)){
			booksns = temp.split(",");
		}
		
		String[] bookrecodes = null;//图书记录号
		temp = (String) reservationData.get("BOOKRECNO");
		if(!StringUtils.isEmpty(temp)){
			bookrecodes = temp.split(",");
		}
		
		String[] logisticsNums = null;//投递地点
		temp = (String) reservationData.get("BOOKPUTADDR");
		if(!StringUtils.isEmpty(temp)){
			logisticsNums = temp.split(",");
		}
		
		String[] endDates = null;//预借截止日期
		temp = (String) reservationData.get("BOOKENDDATE");
		if(!StringUtils.isEmpty(temp)){
			endDates = temp.split(",");
		}
		//计算数组长度
		int a,b,c,d;
		a=b=c=d=0;
		if(booksns != null){
			a = booksns.length;
		}
		if(bookrecodes != null){
			b = bookrecodes.length;
		}
		if(logisticsNums != null){
			c = logisticsNums.length;
		}
		if(endDates != null){
			d = endDates.length;
		}
		if(a+b != c || a+b != d){
			return new ArrayList<>(0);
		}
		
		List<ReservationBookEntity> books = new ArrayList<>(c);
		for (int i = 0; i < a; i++) {
			String booksn = booksns[i];
			ReservationBookEntity book = new ReservationBookEntity();
			book.setBook_barcode(booksn);
			book.setLogisticsNum(logisticsNums[i]);
			book.setDeadline(endDates[i]);
			
			books.add(book);
		}
		for (int j = 0; j < b; j++) {
			String bookrecode = bookrecodes[j];
			ReservationBookEntity book = new ReservationBookEntity();
			book.setBookRecode(bookrecode);
			book.setLogisticsNum(logisticsNums[j+a]);
			book.setDeadline(endDates[j+a]);
			books.add(book);
		}
		return books;
	}
	
	/**
	 * 转换为预借消息
	 * @param map
	 * @return
	 */
	public static ReservationMessage converterReservation(Map<String, Object> map){
		ReservationMessage message = new ReservationMessage();
		if(map != null && !map.isEmpty()){
			message.setMessage((String) map.get("SCREENMESSAGE"));
		}
		String result = String.valueOf(map.get("OPERATIONRESULT"));
		if("y".equalsIgnoreCase(result) 
				|| "1".equalsIgnoreCase(result) 
				|| "yes".equalsIgnoreCase(result)){
			message.setState(true);
		}else{
			message.setState(false);
		}
		return message;
	}
	
	/**
	 * 转换为取消预借消息
	 * @param map
	 * @return
	 */
	public static ReservationMessage converterInReservation(Map<String, Object> map){
		return converterReservation(map);
	}
}
