/*package com.ketu.test.junit;

import javax.annotation.Resource;

import org.junit.Test;

import com.ssitcloud.common.dao.BaseDao;
import com.ssitcloud.operlog.bookenum.EnumClass;
import com.ssitcloud.operlog.entity.BookrackLogEntity;
import com.ssitcloud.operlog.entity.CardboxLogEntity;
import com.ssitcloud.operlog.entity.CashBoxLogEntity;
import com.ssitcloud.operlog.entity.FinanceLogEntity;

public class InsertEntityTest extends BasicTestConfig {

	@Resource(name = "baseDaoImpl")
	private BaseDao baseDao;

	@Test
	public void test() {
		generateBookrackLogEnetity();
		// generateFinlogEntity();
		for (int i = 0; i < 5; i++) {
			getnerateCardBoxLogEntity();
		}
	}

	public CardboxLogEntity getnerateCardBoxLogEntity() {
		CardboxLogEntity c = new CardboxLogEntity();
		c.setLogisticsNo("wuliu1233566");
		c.setOperator("computor");
		c.setOpercmd("1");
		c.setOperresult("1");
		c.setOpertime("201630145320");
		c.setPersellCardno("kahao123456789");// 卡号
		baseDao.insertOne(EnumClass.COLLECTION.cardbox_log.name(), c);
		return c;
	}

	public CashBoxLogEntity generateCashBoxLogEntity() {
		CashBoxLogEntity c = new CashBoxLogEntity();
		c.setCashBinNo("钱箱号123456");
		c.setLogisticsBin("物流45454");
		c.setLogisticsNo("五六号45454");
		c.setMoney(100);
		c.setOperator("读者号12345678");
		c.setOpercmd("1");
		c.setOperresult("1");
		c.setOpertime("20160330105750");
		baseDao.insertOne(EnumClass.COLLECTION.cashbox_log.name(), c);
		return c;

	}

	public FinanceLogEntity generateFinlogEntity() {
		FinanceLogEntity f = new FinanceLogEntity();
		f.setAuthCardno("4545423456789ffffff");
		f.setAuthType("1001");
		f.setBarcode("TP31233");
		f.setFiscalTranID("dddsdsds");
		f.setMoney(50);
		f.setName("玩呢");
		f.setOperator("45454545");
		f.setOpercmd("1");
		f.setOperresult("1");
		f.setOpertime("20160328151930");
		f.setPayway("C");
		f.setPurpose("45");
		f.setSumDay("2");
		f.setUniCardno("1222222222");
		baseDao.insertOne("finance_log", f);
		return f;
	}

	public BookrackLogEntity generateBookrackLogEnetity() {
		BookrackLogEntity bookrackLog = new BookrackLogEntity();
		bookrackLog.setAuthor("莎士比亚");
		bookrackLog.setBarCode("052T001142539");
		bookrackLog.setCallno("R247.1/4713");
		bookrackLog.setCirculationType("流通类型1");
		bookrackLog.setCurrentLocation("当前馆藏地北京");
		bookrackLog.setISBN("isbn123");
		bookrackLog.setItemLocation("505");
		bookrackLog.setItemType("书本类型1");
		bookrackLog.setLogisticsBin("物流书箱号A");
		bookrackLog.setLogisticsNo("4545466688");
		bookrackLog.setMediaType("1");
		// bookrackLog.setOperator("");
		bookrackLog.setOperCmd("kkk");
		bookrackLog.setOperResult("1");
		bookrackLog.setOperTime("20160329091940");
		bookrackLog.setPageNum(500);
		bookrackLog.setPermanentLocation("深圳");
		bookrackLog.setPrice(58);
		bookrackLog.setPublisher("人民出版社");
		bookrackLog.setSubject("主题词");
		bookrackLog.setTitle("怎么吃才健康");
		bookrackLog.setClassNo("I");
		baseDao.insertOne("bookrack_log", bookrackLog);
		return bookrackLog;

	}
}
*/