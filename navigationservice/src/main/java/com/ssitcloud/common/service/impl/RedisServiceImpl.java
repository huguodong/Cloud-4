package com.ssitcloud.common.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.service.RedisService;
import com.ssitcloud.common.util.JedisUtil;

@Service
public class RedisServiceImpl implements RedisService {

	 /** 
     * 添加SortSet型数据 
     * @param key 
     * @param value 
     */ 
	@Override
    public void addSortSet(String key, String value) {  
        double score = new Date().getTime();  
        JedisUtil jedisUtil = JedisUtil.getInstance();  
        jedisUtil.zadd(key, value, score);  
    }  
	
	@Override
	public void addhmset(String key,Map<String,String> map){
		JedisUtil jedisUtil = JedisUtil.getInstance();  
        jedisUtil.hmset(key, map);
	}
  
    /** 
     * 获取倒序的SortSet型的数据 
     * @param key 
     * @return 
     */  
	@Override
    public Set<String> getDescSortSet(String key) {  
        JedisUtil jedisUtil = JedisUtil.getInstance();  
        return jedisUtil.zrevrange(key, 0, -1);  
    }  
  
    /** 
     * 删除SortSet型数据 
     * @param key 
     * @param value 
     */  
	@Override
    public void deleteSortSet(String key, String value) {  
        JedisUtil jedisUtil = JedisUtil.getInstance();  
        jedisUtil.zrem(key, value);  
    }  
  
    /** 
     * 批量删除SortSet型数据 
     * @param key 
     * @param value 
     */  
	@Override
    public void deleteSortSetBatch(String key, String[] value) {  
        JedisUtil jedisUtil = JedisUtil.getInstance();  
        jedisUtil.zrem(key, value);  
    }  
      
    /** 
     * 范围获取倒序的SortSet型的数据 
     * @param key 
     * @return 
     */  
	@Override
    public Set<String> getDescSortSetPage(String key,int start, int end) {  
        JedisUtil jedisUtil = JedisUtil.getInstance();  
        return jedisUtil.zrevrange(key, start, end);  
    }  
  
    /** 
     * 获取SortSet型的总数量 
     * @param key 
     * @return 
     */  
	@Override
    public long getSortSetAllCount(String key) {  
        JedisUtil jedisUtil = JedisUtil.getInstance();  
        return jedisUtil.zcard(key);  
    }  
  
    /** 
     * 检查KEY是否存在 
     * @param key 
     * @return 
     */  
	@Override
    public boolean checkExistsKey(String key) {  
        JedisUtil jedisUtil = JedisUtil.getInstance();  
        return jedisUtil.exists(key);  
    }  
  
    /** 
     * 重命名KEY 
     * @param oldKey 
     * @param newKey 
     * @return 
     */
	@Override
    public String renameKey(String oldKey, String newKey) {  
        JedisUtil jedisUtil = JedisUtil.getInstance();  
        return jedisUtil.rename(oldKey, newKey);  
    }  
  
    /** 
     * 删除KEY 
     * @param key 
     */ 
	@Override
    public void deleteKey(String key) {  
        JedisUtil jedisUtil = JedisUtil.getInstance();  
        jedisUtil.del(key);  
    }  
  
    /** 
     * 设置失效时间 
     * @param key 
     * @param seconds 失效时间，秒 
     */  
	@Override
    public void setExpireTime(String key, int seconds) {  
        JedisUtil jedisUtil = JedisUtil.getInstance();  
        jedisUtil.expire(key, seconds);  
    }  
  
    /** 
     * 删除失效时间 
     * @param key 
     */  
	@Override
    public void deleteExpireTime(String key) {  
        JedisUtil jedisUtil = JedisUtil.getInstance();  
        jedisUtil.persist(key);  
    }  
}
