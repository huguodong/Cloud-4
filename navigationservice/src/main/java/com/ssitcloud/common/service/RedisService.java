package com.ssitcloud.common.service;

import java.util.Map;
import java.util.Set;
  
public interface RedisService {  
  
    /** 
     * 添加SortSet型数据 
     * @param key 
     * @param value 
     */  
    public void addSortSet(String key, String value);
    
    public void addhmset(String key,Map<String,String> map);
  
    /** 
     * 获取倒序的SortSet型的数据 
     * @param key 
     * @return 
     */  
    public Set<String> getDescSortSet(String key);
  
    /** 
     * 删除SortSet型数据 
     * @param key 
     * @param value 
     */  
    public void deleteSortSet(String key, String value);
  
    /** 
     * 批量删除SortSet型数据 
     * @param key 
     * @param value 
     */  
    public void deleteSortSetBatch(String key, String[] value);
      
    /** 
     * 范围获取倒序的SortSet型的数据 
     * @param key 
     * @return 
     */  
    public Set<String> getDescSortSetPage(String key,int start, int end);
  
    /** 
     * 获取SortSet型的总数量 
     * @param key 
     * @return 
     */  
    public long getSortSetAllCount(String key);
  
    /** 
     * 检查KEY是否存在 
     * @param key 
     * @return 
     */  
    public boolean checkExistsKey(String key);
  
    /** 
     * 重命名KEY 
     * @param oldKey 
     * @param newKey 
     * @return 
     */  
    public String renameKey(String oldKey, String newKey);
  
    /** 
     * 删除KEY 
     * @param key 
     */  
    public void deleteKey(String key);
  
    /** 
     * 设置失效时间 
     * @param key 
     * @param seconds 失效时间，秒 
     */  
    public void setExpireTime(String key, int seconds);
  
    /** 
     * 删除失效时间 
     * @param key 
     */  
    public void deleteExpireTime(String key);
}  