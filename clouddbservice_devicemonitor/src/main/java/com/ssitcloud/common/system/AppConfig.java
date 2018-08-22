package com.ssitcloud.common.system;

import com.mongodb.MongoClient;
import com.ssitcloud.common.entity.MessageI18NEntity;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.util.ResourcesUtil;
import com.ssitcloud.common.util.XMLUtils;
import com.ssitcloud.datasync.entity.HeartBeatTransDataState;
import com.ssitcloud.datasync.entity.HeartBeatTransOperLogState;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.annotation.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 随spring容器初始化类
 * 
 * @author lbh
 * 
 *         2016年3月23日
 */
@Configuration
public class AppConfig {

	@Value("${message.i18N}")
	private String messagei18N;

	@Resource(name = "messageSource")
	private ResourceBundleMessageSource messageSource;
	
	@Resource(name="mongoDBImpl")
	private MongoDB mongoDB;
	
//	/**
//	 * adminClient 不可关闭，释放
//	 * @return
//	 */
//	@Bean(name="adminClient")
//    @Autowired
//    public MongoClient adminClient(MongoParam mp){
//	   MongoInstance mongoIns=new MongoInstance();
//	   mongoIns.setHost(mp.getHost());
//	   mongoIns.setOperDatabase(mp.getOpacDb());//oper admindb
//	   mongoIns.setPort(mp.getPort());
//	   mongoIns.setUser(mp.getUser());
//	   mongoIns.setPwd(mp.getPwd());
//	   return mongoDB.getDBClient(mongoIns);
//	}
	
	public Locale locale() {
		String[] i18NStr = messagei18N.split("\\_");
		if (i18NStr.length != 2) {
			throw new RuntimeException(
					"config.properties 中 message.i18N的 参数设置有误请检查,正确格式如 zh_CN");
		}
		return new Locale(i18NStr[0], i18NStr[1]);
	}

	@Bean(name = "messageI18NEntity")
	public MessageI18NEntity messageI18NEntity() {
		MessageI18NEntity i18NMessage = new MessageI18NEntity();
		i18NMessage.setLocale(locale());
		messageSource.setUseCodeAsDefaultMessage(true);
		i18NMessage.setMsgsrc(messageSource);
		return i18NMessage;
	}

    @Bean(name = "mongoParam")
    public MongoParam mongoParam(@Value("${mongoDB.host}")String mongoHost, @Value("${mongoDB.adminDB}")String adminDB,
                                 @Value("${mongoDB.port}")String mongoDBPort,@Value("${mongoDB.superuser}")String superuser,
                                 @Value("${mongoDB.pwd}")String pwd) {
	    MongoParam mp = new MongoParam();
	    mp.setHost(mongoHost);
	    mp.setOpacDb(adminDB);
        mp.setPort(Integer.parseInt(mongoDBPort));
        mp.setUser(superuser);
        mp.setPwd(pwd);
        return mp;
    }

	/**
	 * 用于保存设备状态的容器 
	 * @deprecated
	 * @return
	 */
	@Bean(name="deviceExtStateMap")
	public ConcurrentMap<String,String> deviceExtStateMap(){
		ConcurrentHashMap<String, String> deviceExtStateMap = new ConcurrentHashMap<String, String>(32);
		return deviceExtStateMap;
	}
	
	@Bean(name = "xmlconfig")
	public org.apache.ibatis.session.Configuration xmlconfig() {
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream("mapperConfig.xml");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		org.apache.ibatis.session.Configuration conf = new XMLConfigBuilder(inputStream).parse();
		return conf;
	}
	
	/**
	 *<p> 随着spring启动，将RequestURL.xml中的Url信息装入RequestURLListEntity中</p>
	 * 
	 * 使用方法
	 * <br/>@Resource(name="requestURL")<br/>
	 * RequestURLListEntity requestURLListEntity;<br/><br/>
	 * 获取requestURLListEntity对象，再通过ID属性获取对应的requestURL<br/>
	 * 
	 * @methodName: requestURL
	 * @return
	 * @throws Exception
	 * @returnType: RequestURLListEntity
	 * @author: liuBh
	 */
	@Bean(name = "requestURL")
	public RequestURLListEntity requestURL() throws Exception {
		Map<String, String> map = XMLUtils.parseAll(ResourcesUtil.getInputStream("RequestURL.xml"));
		return new RequestURLListEntity(map);
	}
	@Bean(name="heartBeatTransDataState")
	public HeartBeatTransDataState HeartBeatTransDataState(){
		return new HeartBeatTransDataState(100);
	}
	@Bean(name="heartBeatTransOperLogState")
	public HeartBeatTransOperLogState HeartBeatTransOperLogState(){
		return new HeartBeatTransOperLogState(100);
	}

}