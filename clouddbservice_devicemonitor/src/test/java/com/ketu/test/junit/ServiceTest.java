/*package com.ketu.test.junit;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.mongodb.WriteResult;
import com.ssitcloud.common.dao.BaseDao;
import com.ssitcloud.common.entity.MessageI18NEntity;
import com.ssitcloud.common.system.AppConfig;
import com.ssitcloud.common.util.DateUtil;
import com.ssitcloud.common.util.DeviceStateEnum;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.devicelog.dao.DeviceExtDao;
import com.ssitcloud.devicelog.entity.ExtStateDetail;
import com.ssitcloud.devicelog.entity.ExtStateEntity;
import com.ssitcloud.devicelog.entity.ProptiesTemplate;
import com.ssitcloud.devicelog.service.DeviceStateService;
import com.ssitcloud.operlog.bookenum.EnumClass;
import com.ssitcloud.operlog.entity.CardissueLogEntity;
import com.ssitcloud.operlog.service.BookrackLogService;
import com.ssitcloud.operlog.service.CardissueLogService;
import com.ssitcloud.operlog.service.FinanceLogService;
import com.ssitcloud.operlog.service.LoanLogService;
import com.ssitcloud.operlog.service.LogisticsService;

public class ServiceTest extends BasicTestConfig {
	@Resource(name = "appConfig")
	private AppConfig mtconf;

	@Resource(name = "baseDaoImpl")
	private BaseDao baseDao;
	@Resource(name = "deviceExtDaoImpl")
	private DeviceExtDao deviceExtDao;
	@Resource(name = "deviceStateServiceImpl")
	private DeviceStateService deviceStateService;
	@Resource(name = "messageI18NEntity")
	private MessageI18NEntity i18NEntity;
	@Resource
	private LoanLogService loanLogService;
	@Resource
	private CardissueLogService cardissueLogService;
	@Resource
	private FinanceLogService financeLogService;
	@Resource
	private BookrackLogService BookrackLogService;
	@Resource
	private LogisticsService logisticsService;

	@Test
	public void test() throws Exception {

		// System.out.println(locale.getCountry());
		// System.out.println(locale.getLanguage());
		// String s=messageSource.getMessage("gloable.true", new
		// Object[0],locale);

		// System.out.println(i18NEntity.getMessage("message.queryExtState.false"));
		// System.out.println(DateUtil.format(new Date(1500000000)));
		// deviceStateService.updStateAndLog(test3(1300000000));
		// test1();
		// test2();
		// insertBatch();
		// updateById();
		// updateById2();
		// removeById();
		// createDeviceExtTemp();

		
		 * long time = System.currentTimeMillis(); System.out.println(time);
		 * List<ExtStateEntity> list = deviceExtDao.getCollectionElements(
		 * ExtStateEntity.class, true, 1, "updatetime");
		 * System.out.println(list.get(0).getUpdatetime());
		 * System.out.println(System.currentTimeMillis() - time);
		 

		// insertBatch();
		// queryTest();
		// ExtStateEntity e=test3(10000);
		// String json=JsonUtils.toJson(e);
		// baseDao.insertJSON(Constant.TABLE_EXT_STATE, json);
		// testQueryCardissue();
		// CardissueLogCodec
		// CodecRegistry codecRegistry =
		// CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(new
		// CardissueLogCodec()),MongoClient.getDefaultCodecRegistry());

		// MongoCollection<CardissueLogEntity>
		// col=mongoDB.getCollection(mongoDB.getDBClient("localhost", 27017,
		// "root", "root"), "cardissue_log", CodecUtil.fromRegistries(new
		// CardissueLogCodec()),CardissueLogEntity.class);
		//
		// FindIterable<CardissueLogEntity> ita=col.find();
		// List<CardissueLogEntity> logs=ita.into(new
		// ArrayList<CardissueLogEntity>());
		// for(CardissueLogEntity c:logs){
		// System.out.println(c.getCardTypeName());
		// }

		// MongoInstance instance=new MongoInstance("localhost", 27017,
		// "device_state_template", "root", "root");
		// MongoCollection<Document>
		// col=mongoDB.getCollection(mongoDB.getDBClient(instance),
		// "cardissue_log");
		// Document doc=baseDao.findById(instance, "cardissue_log",
		// "56f630459cdd71180c569985");
		// Bson query=Filters.and(Filters.gte("opertime",
		// "20160326144120"),Filters.lte("opertime", "20160326144120"));

		// List<Document> docs=baseDao.findByQuery(instance, "cardissue_log",
		// query);
		// System.out.println(docs.get(0).getObjectId("_id").toHexString());
		// testQueryCardissue();
		// testQueryFinance();
		// testQueryBookrack();
		// testRuncomand();
		testXML();

	}

	public void testXML() throws FileNotFoundException {
		// XPathParser xp=new XPathParser(new FileInputStream(new
		// File("src/main/resources/XML/LogisticsLog.xml")));
		// XNode xn=xp.evalNode("nosql/command[@id='countLogistics']");

		// String command=xn.getStringBody();
		// System.out.println(baseDao.runCommand(command));
	}

	class RRRun implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < 1000; i++) {
			}
		}
	}

	public void testRuncomand() {
		String command = "{" + "aggregate:\"cashbox_log\"," + "pipeline:["
				+ "{" + "$group:{" + "_id:\"sum\"," + "count:{$sum:\"$Money\"}"
				+ "}" + "}" + "]" + "}";
		System.out.println(baseDao.runCommand(command));

	}

	public void testQueyLogistics() {
		String cashboxDevice = "{\"countType\":\"1\",\"startTime\":\"20160330105750\",\"endTime\":\"20160330105750\"}";
		System.out.println(logisticsService.countLogistics(cashboxDevice,
				EnumClass.COLLECTION.cashbox_log.name()));

	}

	public void testQueryBookrack() {
		String whereInfo = "{\"countType\":\"1\",\"startTime\":\"20150329091940\",\"endTime\":\"20160329091940\",\"onRackStartTime\":\"50\",\"onRackEndTime\":\"300\"}";
		String byDevice = "{\"countType\":\"2\",\"startTime\":\"20150329091940\",\"endTime\":\"20160329091940\",\"onRackStartTime\":\"50\",\"onRackEndTime\":\"300\"}";

		System.out.println(BookrackLogService.countBookUsed(byDevice));
	}

	public void testQueryFinance() {
		String byDevice = "{\"countType\":\"1\",\"startTime\":\"20160328151920\",\"endTime\":\"20160328151950\",\"operator\":\"45\"}";
		String byAuthType = "{\"countType\":\"3\",\"startTime\":\"20160328151920\",\"endTime\":\"20160328151950\",\"operator\":\"45\",\"params\":[\"1001\",\"1002\"]}";
		String byCardType = "{\"countType\":\"2\",\"startTime\":\"20160328151920\",\"endTime\":\"20160328151950\",\"operator\":\"45\",\"params\":[\"10001\",\"10002\"]}";
		String query = "{\"startTime\":\"20160328151920\",\"endTime\":\"20160328151950\",\"operator\":\"45\"}";

		// System.out.println(financeLogService.countFinance(byCardType));
		System.out.println(financeLogService.queryFinance(query));
	}

	public void testQueryCardissue() {
		String byCardType = "{\"countType\":\"1\",\"startTime\":\"20160326144120\",\"endTime\":\"20160326154150\"}";
		String byAuthType = "{\"countType\":\"2\",\"startTime\":\"20160326144120\",\"endTime\":\"20160326144150\",\"params\":[\"1001\",\"1002\"]}";
		String byAge = "{\"countType\":\"3\",\"startTime\":\"20160326144120\",\"endTime\":\"20160326144150\",\"startAge\":\"10\",\"endAge\":\"50\"}";
		String byGender = "{\"countType\":\"4\",\"startTime\":\"20160326144120\",\"endTime\":\"20160326144150\",\"params\":[\"0\",\"1\"]}";
		String byDevice = "{\"countType\":\"5\",\"startTime\":\"20160326144120\",\"endTime\":\"20160326144150\"}";
		String byTime = "{\"countType\":\"6\",\"startTime\":\"20160326144120\",\"endTime\":\"20160326144150\"}";
		String byTimeSegment = "{\"countType\":\"7\",\"startTime\":\"20160326144120\",\"endTime\":\"20160326154150\"}";
		String query = "{\"startTime\":\"20160326144120\",\"endTime\":\"20160326154150\",\"ethnicGroup\":\"汉\"}";
		// System.out.println(cardissueLogService.countCardissue(byCardType));
		System.out.println(cardissueLogService.queryCardissue(query));
	}

	public CardissueLogEntity inserCardiusseLog() {
		CardissueLogEntity cl = new CardissueLogEntity();
		cl.setOperator("12345678999");
		cl.setAddress("民治大道102号xx");
		cl.setAuthCardno("4545423456789ffffff");
		cl.setAuthType("1001");
		cl.setBirth("20160326");
		cl.setAge(22);
		cl.setCardType("10001");
		cl.setEmail("qqqq@qq.com");
		cl.setEthnicGroup("li族");
		cl.setExpireDate("20160926");
		cl.setGender("1");
		cl.setMobile("13888888");
		cl.setTelephone("88888");
		cl.setName("小王x");
		cl.setOpertime("20160326144150");
		cl.setOpercmd("1");
		cl.setPrivilegeFee(100);
		cl.setCardTypeName("100元B级读者卡");
		cl.setOperresult("1");
		baseDao.insertOne("cardissue_log", cl);
		return cl;
	}

	*//**
	 * *{ 流通记录测试 cardNo(读者证号):...... cirType(流通类型):[1借书,2还书,3续借] param:[null]
	 * countType :2(统计类型) startTime:开始时间 endTime:结束时间 }
	 *//*
	public void queryTest() {
		String jsonString1 = "{\"cardNo\":\"123\",\"countType\":\"1\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"params\":[\"I\"],\"opercmd\":[\"1\",\"2\",\"3\"]}";
		String jsonString3byResult = "{\"cardNo\":\"123\",\"countType\":\"3\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"params\":[\"1\"],\"opercmd\":[\"1\",\"2\",\"3\"]}";
		String jsonString2byTime = "{\"cardNo\":\"123\",\"countType\":\"2\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"1\",\"2\",\"3\"]}";
		String jsonString4byreadercirType = "{\"cardNo\":\"123\",\"countType\":\"4\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"1\",\"2\",\"3\"],\"params\":[\"10001\",\"10002\"]}";
		String jsonString7byperment = "{\"cardNo\":\"123\",\"countType\":\"7\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"1\",\"2\",\"3\"],\"params\":[\"北京\"]}";
		String jsonString8bycirType = "{\"cardNo\":\"123\",\"countType\":\"8\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"1\",\"2\",\"3\"],\"params\":[\"1\"]}";
		String jsonString9bymediatype = "{\"cardNo\":\"123\",\"countType\":\"9\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"1\",\"2\",\"3\"],\"params\":[\"1\"]}";
		String jsonString10byall = "{\"cardNo\":\"123\",\"countType\":\"10\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"1\",\"2\",\"3\"]}";
		String jsonString6byreaderGernder = "{\"cardNo\":\"123\",\"countType\":\"6\",\"startTime\":\"20160323114120\",\"endTime\":\"20160323114120\",\"opercmd\":[\"1\",\"2\",\"3\"],\"params\":[\"1\",\"0\"]}";
		String jsonString5byAge = "{\"cardNo\":\"123\",\"countType\":\"5\",\"startTime\":\"20160323114100\",\"endTime\":\"20160323114120\",\"opercmd\":[\"1\",\"2\",\"3\"],\"params\":[\"17\",\"66\"]}";
		String jsonStringQuery = "{\"cardNo\":\"123\",\"startTime\":\"20160323114100\",\"endTime\":\"20160323114120\",\"opercmd\":[\"1\",\"2\",\"3\"]}";

		// System.out.println(loanLogService.countLoanLog(jsonString5byAge));
		System.out.println(loanLogService.queryLoanLog(jsonStringQuery));
	}

	public void jsonUtilsTest() {
		ExtStateEntity extState = ProptiesTemplate.getExtStateEntityTemplate();
		String s = JsonUtils.toJson(extState);
		ExtStateEntity afterParse = JsonUtils.fromJson(s, ExtStateEntity.class);
		System.out.println(afterParse.getUpdatetime());
		List<ExtStateDetail> details = afterParse.getDetail();
		for (ExtStateDetail e : details) {
			System.out.println(e.getLogicObj() + " " + e.getLogicState());
		}
	}

	public void createDeviceExtTemp() {

	}

	public void removeById() throws Exception {
		ExtStateEntity extState = baseDao.removeById("ext_state",
				"56e6afb77de771c10ebb79fc", ExtStateEntity.class);
		System.out.println(extState.getId());
	}

	public void updateById2() throws Exception {// 56e6afb77de771c10ebb79fc
		List<ExtStateDetail> detail = test3(0).getDetail();
		// 某条记录部分数据或全部数据更新
		baseDao.updateById("ext_state", "56e678d443623e3766ba0e35", "detail",
				detail);

	}

	public void updateById() throws Exception {// 56e6afb77de771c10ebb79fc
		WriteResult wr = baseDao.updateById("ext_state",
				"56e6afb77de771c10ebb79fc", "updatetime",
				DateUtil.getDateDefaultFormat());

		// write concern:0（Unacknowledged） 不知道写入结果 性能最高
		System.out.println(wr.wasAcknowledged());

	}

	public void insertBatch() throws Exception {
		List<ExtStateEntity> list = new ArrayList<ExtStateEntity>(1024);
		for (int i = 0; i < 10000 * 10000; i = i + 10000) {
			list.add(test3(i));
		}
		System.out.println(list.size());
		// System.out.println(list.contains(null));
		baseDao.insertBatch("ext_state", list);
	}

	public ExtStateEntity test3(long time) {
		ExtStateEntity extStateEntity = new ExtStateEntity();
		extStateEntity
				.setUpdatetime(DateUtil.format(
						new Date(System.currentTimeMillis() + time),
						DateUtil.format_2));
		List<ExtStateDetail> details = new ArrayList<ExtStateDetail>();
		details.add(new ExtStateDetail(DeviceStateEnum.AUTH_RFIDREADER,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.ALERT,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.ARM, DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.BCL, DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.BELT,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.BINMOVING,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.CARDDISPENSER,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.CHECKIN_FUNCTION,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.CHECKOUT,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.CHECKOUT1,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.CHECKOUT1_FUNCTION,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.CHECKOUT2,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.CHECKOUT2_FUNCTION,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.CHECKOUT3,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.CHECKOUT3_FUNCTION,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.CLUTCH1,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.CLUTCH2,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.CLUTCH3,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.ICCARD,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.IDREADER,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.LOAD_RFIDREADER,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.NCIP_CONNECT,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.PLC, DeviceStateEnum.NO));
		details.add(new ExtStateDetail(DeviceStateEnum.PRESENT,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.PRINTER,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.PUSHER1,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.PUSHER2,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.PUSHER3,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.PUSHER4,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.PUSHHANDLE1,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.PUSHHANDLE2,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.PUSHHANDLE3,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.REGISTER,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.RETURN_RFIDREADER,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.REGISTER_RFIDREADER,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.SCANITEM,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.SHELF,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.SHELF1,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.SHELF2,
				DeviceStateEnum.YES));
		details.add(new ExtStateDetail(DeviceStateEnum.SHELF3,
				DeviceStateEnum.NO));
		extStateEntity.setDetail(details);
		// baseDao.insertOne("device_state_template", "ext_state",
		// extStateEntity);
		return extStateEntity;
	}

	public void test2() throws Exception {
		ExtStateEntity extState = baseDao.getElementById(
				"56e678d443623e3766ba0e35", ExtStateEntity.class);
		System.out.println(JsonUtils.toJson(extState));
		// List<ExtStateDetail> list = extState.getDetail();
		// System.out.println(extState.getId());
		// System.out.println(extState.getUpdatetime());
		// for (ExtStateDetail e : list) {
		// System.out.println(e.getExtType() + " " + e.getExtState());
		// }
	}

	public void test1() throws Exception {
		List<ExtStateEntity> list = baseDao.getCollectionElements(
				ExtStateEntity.class, false, 1, 0);
		for (ExtStateEntity extState : list) {
			System.out.println(extState.getId() + " "
					+ extState.getUpdatetime());
		}
	}

}*/