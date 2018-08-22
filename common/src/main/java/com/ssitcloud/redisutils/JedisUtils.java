package com.ssitcloud.redisutils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.ssitcloud.common.util.JsonUtils;

import redis.clients.jedis.Jedis;

public class JedisUtils {
	
	private JedisUtils(){}
	
	private static final JedisUtils jedisUtils = new JedisUtils();
	
	/**
	 * 获取连接
	 * @return
	 */
	private Jedis getjedis(){
		return RedisPool.getJedis();
	}
	
	/**
	 * 返回jedis对象
	 * @return
	 */
	public static JedisUtils getInstance(){
		return jedisUtils;
	}
	
	/**
	 * 释放连接
	 * @param jedis
	 */
	public static void closeJedis(final Jedis jedis){
		if(jedis != null){
			jedis.close();
		}
	}
	
	/**
	 * 设置key的过期时间
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Long expire(String key,int seconds){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			return jedis.expire(key, seconds);
		}finally{
			closeJedis(jedis);
		}
		
	}
	
	/**
	 * 以秒为单位返回 key 的剩余过期时间
	 * @param key
	 * @return
	 */
	public Long ttl(String key){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			return jedis.ttl(key);
		}finally{
			closeJedis(jedis);
		}
	}
	
	/**
	 * 删除键
	 * @param key
	 */
	public Long del(String key){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			Long result = jedis.del(key);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	public Long del(byte[] key){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			Long result = jedis.del(key);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	public Set<String> keys(String pattern){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			return jedis.keys(pattern);
		}finally{
			closeJedis(jedis);
		}
	}
	
	public <T> List<T> keys(String pattern,Class<T> t){
		Jedis jedis = null;
		List<T> list = new ArrayList<>();
		try{
			jedis = getjedis();
			Set<String> cacheStrs = jedis.keys(pattern);
			if(cacheStrs != null && !cacheStrs.isEmpty()){
				for(String cache : cacheStrs){
					list.add(JsonUtils.fromJson(cache, t));
				}
			}
			return list;
		}finally{
			closeJedis(jedis);
		}
	}
	
	/**
	 * 数据结构：字符串键值对 【KEY-VALUE】<p>
	 * 如果key已经存在则更新value值，否则插入键值对。
	 * @param key
	 * @param value
	 */
	public String set(String key,String value){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			String result = jedis.set(key, value);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	/**
	 * 存放序列化后的键值对，可以用来存放对象
	 * 类需要继承:Serializable接口
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(byte[] key,byte[] value){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			String result = jedis.set(key, value);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	
	/**
	 * 数据结构：字符串键值对【 KEY-VALUE】<p>
	 * 根据key获取value值
	 * @param key
	 * @param value
	 * @return
	 */
	public String get(String key){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			String result = jedis.get(key);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	
	/**
	 * 数据结构：字符串键值对【 KEY-VALUE】<p>
	 * 根据key获取value值
	 * @param key
	 * @param value
	 * @return
	 */
	public byte[] get(byte[] key){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			byte[] result = jedis.get(key);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	/**
	 * 数据结构：字符串键值对【 KEY-VALUE】<p>
	 * 根据key获取value值
	 * @param key
	 * @param value
	 * @return
	 */
	public <T> T get(String key,Class<T>t){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			String JOSNValue = jedis.get(key);
			if(!StringUtils.isEmpty(JOSNValue)){
				return JsonUtils.fromJson(JOSNValue, t);
			}
			return null;
		}finally{
			closeJedis(jedis);
		}
	}
	
	
	
	/**
	 * 数据结构：字符串键值对【 KEY-VALUE】<p>
	 * 将key的值设为value,当且仅当key不存在<p>
	 * 若key已经存在，则不做任何操作
	 * @param key
	 * @param value
	 */
	public Long setnx(String key,String value){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			Long result = jedis.setnx(key, value);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	/**
	 * 数据结构：字符串键值对 【KEY-VALUE】<p>
	 * 插入键值对，并将key的过期时间设置为seconds<p>
	 * 如果key已经存在，setex将覆盖旧值
	 * @param key
	 * @param seconds 过期时间（秒）
	 * @param value
	 * @return
	 */
	public String setex(String key,int seconds,String value){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			String result = jedis.setex(key, seconds, value);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	/**
	 * 数据结构：链表 key-【value1 value2...】<p>
	 * 将值插入到链表的头部
	 * @param key 链表名称
	 * @param value 值
	 * @return
	 */
	public Long lpush(String key, String ...value){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			Long result = jedis.lpush(key, value);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	/**
	 * 数据结构：链表 key-【value1 value2...】<p>
	 * 从链表的尾部取值
	 * @param key 链表名称
	 * @return
	 */
	public String rpop(String key){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			return jedis.rpop(key);
		}finally{
			closeJedis(jedis);
		}
	}
	
	/**
	 * 一次获取链表全部数据
	 * @param key
	 * @return
	 */
	public List<String> popAll(String key) {
		return lrange(key, 0, -1);
	}
	
	/**
	 * 数据结构：链表 key-【value1 value2...】<p>
	 * 根据index 获取链表中指定的值
	 * @param key
	 * @param index 下标
	 * @return
	 */
	public String lindex(String key,long index){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			String result = jedis.lindex(key, index);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	
	/**
	 * 数据结构：链表 key-【value1 value2...】<p>
	 * 获取链表key中，从start开始，end结束的值
	 * @param key
	 * @param start 从0开始
	 * @param end -1 表示最后一个
	 * @return
	 */
	public List<String> lrange(String key,long start,long end){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			List<String> result = jedis.lrange(key, start, end);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	/**
	 * 删除元素<p>
	 * 数据结构：链表 key-【value1 value2...】
	 * @param key
	 * @param count 删除的个数，如果有重复时，可以指定删除多少个
	 * @param value
	 * @return
	 */
	public Long lrem(String key,long count,String value){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			Long result = jedis.lrem(key, count, value);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	
	/**
	 * 数据格式：散列（hash） key:{field1:value1，field2:value2...}
	 *<blockquote><pre>
	 *    name tom,
	 *key1 age 20,
	 *    sex male
	 *</pre></blockquote>
	 * @param key 散列名称
	 * @param map 字符串键值对（field-value）
	 * @return
	 */
	public String hmset(String key,Map<String, String> map){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			String result = jedis.hmset(key, map);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	
	/**
	 * 更新或者插入散列表中的某个字段的值<p>
	* 数据格式：散列（hash） key:{field1:value1，field2:value2...}
	 *<blockquote><pre>
	 *    name tom,
	 *key1 age 20,
	 *    sex male
	 *</pre></blockquote>
	 * @param key
	 * @param field 字段名称
	 * @param value 字段值
	 */
	public Long hset(String key,String field,String value){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			Long result = jedis.hset(key, field, value);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	/**
	 * 获取散列表中所有的键值对<p>
	 * 数据格式：散列（hash） key:{field1:value1，field2:value2...}
	 * @param key 表名称
	 * @return
	 */
	public Map<String, String> hgetAll(String key){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			Map<String, String> result = jedis.hgetAll(key);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	/**
	 * 获取散列表中一个或多个键的值，返回顺序与请求参数一致。<p>
	 * 数据格式：散列（hash） key:{field1:value1，field2:value2...}
	 * @param key 
	 * @param fields 
	 * @return
	 */
	public String hmget(String key,String fields){
		Jedis jedis = null;
		try{
			if(fields == null) return "";
			jedis = getjedis();
			List<String> result = jedis.hmget(key, fields);
			if(result != null && !result.isEmpty()){
				String str = result.get(0);
				if(str == null ) return "";
				return str;
			}
			return "";
		}finally{
			closeJedis(jedis);
		}
	}
	
	public <T> T hmget(String key,String field,Class<T> t){
		Jedis jedis = null;
		try{
			if(field == null ) return null;
			jedis = getjedis();
			List<String> result = jedis.hmget(key, field);
			String str = result.get(0);
			if(str != null){
				return JsonUtils.fromJson(str, t);
			}
			return null;
		}finally{
			closeJedis(jedis);
		}
	}
	
	public <T> List<T> hmget(String key,Class<T> t,String ...fields){
		Jedis jedis = null;
		try{
			List<T> list = new ArrayList<>();
			if(fields == null) return list;
			jedis = getjedis();
			List<String> result = jedis.hmget(key, fields);
			if(result != null && !result.isEmpty()){
				for(String str : result){
					if(str == null) continue;
					T object = JsonUtils.fromJson(str, t);
					list.add(object);
				}
				return list;
			}
			return list;
		}finally{
			closeJedis(jedis);
		}
	}
	
	/**
	 * 删除散列表中的一个或多个键的值
	 * @param key
	 * @param fields
	 * @return
	 */
	public Long hdel(String key,String ...fields){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			long result = jedis.hdel(key, fields);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	
	/**
	 * 添加元素到无序集合中<p>
	 * key:【member member ...】
	 * @param key
	 * @param values
	 * @return
	 */
	public Long sadd(String key,String ...members){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			Long result = jedis.sadd(key, members);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	/**
	 * 查询集合中所有的元素<p>
	 * key:【member member ...】
	 * @param key
	 * @return
	 */
	public Set<String> smembers(String key){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			Set<String> result = jedis.smembers(key);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	/**
	 * 删除集合里面的元素<p>
	 * key:【member member ...】
	 * @param key
	 * @param members
	 * @return
	 */
	public Long srem(String key,String ...members){
		Jedis jedis = null;
		try{
			jedis = getjedis();
			Long result = jedis.srem(key, members);
			return result;
		}finally{
			closeJedis(jedis);
		}
	}
	
	
	
	
	
	
	

}
