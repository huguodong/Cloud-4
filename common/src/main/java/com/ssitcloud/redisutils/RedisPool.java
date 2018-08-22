package com.ssitcloud.redisutils;

import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/***
 * 初始化Redis连接池
 * @author yanyong
 *
 */
public class RedisPool {
	
	/**Redis服务器IP*/
	private static String  ADDR = "172.16.0.111";
	/**Redis端口*/
	private static int  PORT = 6379;
	/**访问密码*/
	private static String AUTH = "Ssit123456!";
	/**可用连接实例的最大数目，默认是8；如果赋值-1，表示不限制*/
	private static int MAX_TOTAL = 80;
	/**控制一个连接池最多有多少个状态为idle(空闲)的jedis实例*/
	private static int MAX_IDLE = 20;
	/**等待可用连接的最大时间,单位是毫秒,默认值为-1表示永不超时*/
	private static long  MAX_WAIT_MILLIS = 10000;
	/**连接服务器超时*/
	private static int TIME_OUT = 10000;
	/**是否验证操作,true表示得到的jedis实例都可以使用*/
	private static boolean TEST_ON_BORROW = false;
	
	/**每次释放连接的最大数目*/
	private static int NUM_TEST_PEREVICTION_RUN = 1024;
	/**释放连接的扫描间隔(毫秒)*/
	private static long TIME_BETWEEN_EVICTION_RUNSMILLIS = 30000;
	/**连接最小空闲时间*/
	private static long MIN_EVICTABLE_IDLE_TIMEMILLIS = 1800000;
	/**连接空闲多久后释放，当前空闲时间>该值，空闲连接>最大空闲连接数时，直接释放*/
	private static long SOFT_MIN_EVICTABLE_IDLE_TIMEMILLIS = 10000;
	/**空闲时检查有效性,默认false*/
	private static boolean TEST_WHILE_IDLE = true;
	/**连接耗尽时是否阻塞，false报异常,true阻塞到超时*/
	private static boolean BLOCK_WHEN_EXHAUSTED=false;
	private RedisPool(){};
	
	private static JedisPool jedisPool;
	
	static{
		try{
			System.out.println("Redis 连接池初始化开始");
			//初始化连接池
			JedisPoolConfig poolConfig = new JedisPoolConfig();
			initConfig(poolConfig);
			jedisPool = new JedisPool(poolConfig,ADDR,PORT,TIME_OUT,AUTH);
			System.out.println("Redis 连接池初始化完成");
		}catch(Exception e){
			System.out.println("初始化Redis连接池失败!");
			e.printStackTrace();
		}
	}
	
	/***加载配置文件***/
	private static void initConfig(JedisPoolConfig poolConfig){
		ResourceBundle bundle = ResourceBundle.getBundle("redis");
		if(!StringUtils.isBlank(bundle.getString("MAX_TOTAL"))){
			MAX_TOTAL = Integer.parseInt(bundle.getString("MAX_TOTAL"));
		}
		if(!StringUtils.isBlank(bundle.getString("MAX_IDLE"))){
			MAX_IDLE = Integer.parseInt(bundle.getString("MAX_IDLE"));
		}
		if(!StringUtils.isBlank(bundle.getString("MAX_WAIT_MILLIS"))){
			MAX_WAIT_MILLIS = Long.parseLong(bundle.getString("MAX_WAIT_MILLIS"));
		}
		if(!StringUtils.isBlank(bundle.getString("PORT"))){
			PORT = Integer.parseInt(bundle.getString("PORT"));
		}
		if(!StringUtils.isBlank(bundle.getString("TIME_OUT"))){
			TIME_OUT = Integer.parseInt(bundle.getString("TIME_OUT"));
		}
		if(!StringUtils.isBlank(bundle.getString("NUM_TEST_PEREVICTION_RUN"))){
			NUM_TEST_PEREVICTION_RUN = Integer.parseInt(bundle.getString("NUM_TEST_PEREVICTION_RUN"));
		}
		if(!StringUtils.isBlank(bundle.getString("TIME_BETWEEN_EVICTION_RUNSMILLIS"))){
			TIME_BETWEEN_EVICTION_RUNSMILLIS = Long.parseLong(bundle.getString("TIME_BETWEEN_EVICTION_RUNSMILLIS"));
		}
		if(!StringUtils.isBlank(bundle.getString("MIN_EVICTABLE_IDLE_TIMEMILLIS"))){
			MIN_EVICTABLE_IDLE_TIMEMILLIS = Long.parseLong(bundle.getString("MIN_EVICTABLE_IDLE_TIMEMILLIS"));
		}
		if(!StringUtils.isBlank(bundle.getString("SOFT_MIN_EVICTABLE_IDLE_TIMEMILLIS"))){
			SOFT_MIN_EVICTABLE_IDLE_TIMEMILLIS = Long.parseLong(bundle.getString("SOFT_MIN_EVICTABLE_IDLE_TIMEMILLIS"));
		}
		TEST_WHILE_IDLE = "false".equalsIgnoreCase(bundle.getString("TEST_WHILE_IDLE"))?false:true;
		BLOCK_WHEN_EXHAUSTED = "false".equalsIgnoreCase(bundle.getString("BLOCK_WHEN_EXHAUSTED"))?false:true;
		TEST_ON_BORROW = "false".equalsIgnoreCase(bundle.getString("TEST_ON_BORROW"))?false:true;
		ADDR = bundle.getString("ADDR");
		AUTH = bundle.getString("AUTH");
		
		poolConfig.setMaxTotal(MAX_TOTAL);
		poolConfig.setMaxIdle(MAX_IDLE);
		poolConfig.setMaxWaitMillis(MAX_WAIT_MILLIS);
		poolConfig.setTestOnBorrow(TEST_ON_BORROW);
		poolConfig.setNumTestsPerEvictionRun(NUM_TEST_PEREVICTION_RUN);
		poolConfig.setTimeBetweenEvictionRunsMillis(TIME_BETWEEN_EVICTION_RUNSMILLIS);
		poolConfig.setMinEvictableIdleTimeMillis(MIN_EVICTABLE_IDLE_TIMEMILLIS);
		poolConfig.setSoftMinEvictableIdleTimeMillis(SOFT_MIN_EVICTABLE_IDLE_TIMEMILLIS);
		poolConfig.setTestWhileIdle(TEST_WHILE_IDLE);
		poolConfig.setBlockWhenExhausted(BLOCK_WHEN_EXHAUSTED);
	}
	
	
	/***
	 * 获取jedis实例
	 * @return
	 */
	public static Jedis getJedis(){
		try{	
			if(jedisPool != null){
				Jedis jedis = jedisPool.getResource();
				return jedis;
			}
		}catch(Exception e){
			System.out.println("获取Redis连接失败！");
			e.printStackTrace();
		}
		return null;
	}
	
	//TODO 连接是否需要释放
	public static void main(String[] args) {
		RedisPool.getJedis().set("name2", "yanyong2222");
		System.out.println(RedisPool.getJedis().get("name2"));
	}
	
}
