package com.ssitcloud.dblib.common.utils;

/**
 * 书目信息以及读者数据，
 * 字段对应的数组位置
 *
 * <p>2017年9月6日 下午1:55:29  
 * @author hjc 
 *
 */
public class FieldsIndex {
	
	
	
	/**
	 * 
	 * 书目带馆藏的数据信息
	 *  0  nowlib_idx：	图书当前所在馆IDx   实际上传的数据此字段是 图书馆id，在view层已经转换成idx
		1  book_barcode：	图书条码
		2  ISBN：	ISBN号
		3  title：	题名
		4  author：	作者
		5  publish：	出版社
		6  classNo：	分类号
		7  callNo：	索书号
		8  shelflayer_barcode：	层架标号/书盒号/格口号
		9  update_uid_flag：	是否更新过标签，1否，2是
		10  state：	图书状态
		11 updatetime：	最近一次状态变更时间
		12 statemodcount：	状态变更次数
		13 pubAddress：	出版地
		14 pubDate：	出版日期
		15 price：	价格
		16 subject：	主题
		17 book_page：	页码
		18 book_size：	规格
		19 contents：	简介
		20 seriesBook：	丛书
		21 lang：	语种
		22 version：	版本
		23 bookimage_url：	图书封面路径
		24 location：	所属馆藏地
		25 nowlocation：	当前馆藏地
	 */
	public static final int BOOKITEM_NOWLIB_IDX = 0;
	public static final int BOOKITEM_BOOK_BARCODE = 1;
	public static final int BOOKITEM_ISBN = 2;
	public static final int BOOKITEM_TITLE = 3;
	public static final int BOOKITEM_AUTHOR = 4;
	public static final int BOOKITEM_PUBLISH = 5;
	public static final int BOOKITEM_CLASSNO = 6;
	public static final int BOOKITEM_CALLNO = 7;
	public static final int BOOKITEM_SHELFLAYER_BARCODE = 8;
	public static final int BOOKITEM_UPDATE_UID_FLAG = 9;
	public static final int BOOKITEM_STATE = 10;
	public static final int BOOKITEM_UPDATETIME = 11;
	public static final int BOOKITEM_STATEMODCOUNT = 12;
	public static final int BOOKITEM_PUBADDRESS = 13;
	public static final int BOOKITEM_PUBDATE = 14;
	public static final int BOOKITEM_PRICE = 15;
	public static final int BOOKITEM_SUBJECT = 16;
	public static final int BOOKITEM_BOOK_PAGE = 17;
	public static final int BOOKITEM_BOOK_SIZE = 18;
	public static final int BOOKITEM_CONTENTS = 19;
	public static final int BOOKITEM_SERIESBOOK = 20;
	public static final int BOOKITEM_LANG = 21;
	public static final int BOOKITEM_VERSION = 22;
	public static final int BOOKITEM_BOOKIMAGE_URL = 23;
	public static final int BOOKITEM_LOCATION = 24;
	public static final int BOOKITEM_NOWLOCATION = 25;
	
	
	/**
	 * 
		0	lib_id	所属馆ID
		1	book_barcode	图书条码
		2	ISBN	ISBN号
		3	title	题名
		4	author	作者
		5	publish	出版社
		6	classNo	分类号
		7	state	图书状态
		8	pubAddress	出版地
		9	pubDate	出版日期
		10	price	价格
		11	subject	主题
		12	book_page	页码
		13	book_size	规格
		14	contents	简介
		15	seriesBook	丛书
		16	lang	语种
		17	version	版本
		18	bookimage_url	图书封面路径
		19	location	所属馆藏地

	 */
	
	public static final int BOOK_LIB_ID = 0;
	public static final int BOOK_BOOK_BARCODE = 1;
	public static final int BOOK_ISBN = 2;
	public static final int BOOK_TITLE = 3;
	public static final int BOOK_AUTHOR = 4;
	public static final int BOOK_PUBLISH = 5;
	public static final int BOOK_CLASSNO = 6;
	public static final int BOOK_STATE = 7;
	public static final int BOOK_PUBADDRESS = 8;
	public static final int BOOK_PUBDATE = 9;
	public static final int BOOK_PRICE = 10;
	public static final int BOOK_SUBJECT = 11;
	public static final int BOOK_BOOK_PAGE = 12;
	public static final int BOOK_BOOK_SIZE = 13;
	public static final int BOOK_CONTENTS = 14;
	public static final int BOOK_SERIESBOOK = 15;
	public static final int BOOK_LANG = 16;
	public static final int BOOK_VERSION = 17;
	public static final int BOOK_BOOKIMAGE_URL = 18;
	public static final int BOOK_LOCATION = 19;

	
	
	/**
	 * 
		//读者数据
		0	lib_idx：	图书馆idx  实际上传的数据此字段是 图书馆id，在view层已经转换成idx
		1	card_no：	读者卡号
		2	card_type：	读者卡类型
		3	valid：	是否有效
		4	expire_date：	过期时间
		5	privilege_fee：	需交押金费用
		6	name：	名称
		7	password：	密码
		8	id_card：	身份证号
		9	birth：	生日
		10	gender：	性别1男0女
		11	address：	住址
		12	age：	年龄
		13	login_name：	登录名
		14	allown_loancount：	最大借阅数
		15	surplus_count：	剩余可借数
		16	advance_charge：	预付款
		17	deposit：	押金
		18	arrearage：	欠款
	 */
		
	public static final int READER_LIB_IDX = 0;
	public static final int READER_CARD_NO = 1;
	public static final int READER_CARD_TYPE = 2;
	public static final int READER_VALID = 3;
	public static final int READER_EXPIRE_DATE =4;
	public static final int READER_PRIVILEGE_FEE = 5;
	public static final int READER_NAME = 6;
	public static final int READER_PASSWORD = 7;
	public static final int READER_ID_CARD = 8;
	public static final int READER_BIRTH = 9;
	public static final int READER_GENDER = 10;
	public static final int READER_ADDRESS = 11;
	public static final int READER_AGE = 12;
	public static final int READER_LOGIN_NAME = 13;
	public static final int READER_ALLOWN_LOANCOUNT = 14;
	public static final int READER_SURPLUS_COUNT = 15;
	public static final int READER_ADVANCE_CHARGE = 16;
	public static final int READER_DEPOSIT = 17;
	public static final int READER_ARREARAGE = 18;
}
