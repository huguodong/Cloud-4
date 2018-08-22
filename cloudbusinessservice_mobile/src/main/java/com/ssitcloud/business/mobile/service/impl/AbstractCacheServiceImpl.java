package com.ssitcloud.business.mobile.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.service.CacheServiceI;

/**
 * 抽象缓存服务类
 *
 * @author LXP
 * @version 创建时间：2017年3月31日 下午2:27:32
 */
public abstract class AbstractCacheServiceImpl<K, V> implements CacheServiceI<K, V> {
    private AtomicBoolean isUpdateing = new AtomicBoolean(false);
    private AtomicLong updateTime = new AtomicLong(0);
    private Map<K, V> cacheMap = new ConcurrentHashMap<>(256);

    private Timer timer = new Timer();

    /**
     * 获取超时
     *
     * @return
     */
    protected abstract long getTimeOut();

    /**
     * 获取新一批数据
     *
     * @return
     * @throws CanNotObtainDataException
     */
    protected abstract List<V> getSourceData() throws CanNotObtainDataException;

    /**
     * 通过唯一标识获取数据
     *
     * @param k
     * @return
     * @throws CanNotObtainDataException
     */
    protected abstract V getSourceData(K k) throws CanNotObtainDataException;

    /**
     * 从数据中获取唯一标识
     *
     * @param v
     * @return
     */
    protected abstract K getSourceDataKey(V v);

    @Override
    public V get(K k) {
        V v = cacheMap.get(k);
        if (v == null) {
            LogUtils.debug(getClass() + " 缓存命中失败，获取单条数据中...");
            try {
                v = getSourceData(k);
                cacheMap.put(getSourceDataKey(v), v);
                LogUtils.debug(getClass() + " 获取单条数据成功");
            } catch (CanNotObtainDataException e) {
                LogUtils.info(getClass() + " 获取数据失败,key==>" + k.toString());
            }
        }
        //检查是否需要更新
        if (!isUpdateing.get()) {
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    update();
                }
            }, 0);
        }

        return v;
    }

    @Override
    public void update() {
        try {
            if (!isUpdateing.compareAndSet(false, true)) {
                return;
            }
            //查看更新时间
            if (System.currentTimeMillis() - updateTime.get() < getTimeOut()) {
                return;
            }
            try {
                LogUtils.debug(getClass() + " 获取批量数据中...");
                List<V> sourceData = getSourceData();

                Set<K> setMap = new HashSet<>(sourceData.size());

                for (V v : sourceData) {
                    K k = getSourceDataKey(v);
                    setMap.add(k);
                    cacheMap.put(k, v);
                }
                //检查是否存在失效数据
                Iterator<Entry<K, V>> iterator = cacheMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<K, V> entry = iterator.next();
                    if (!setMap.contains(entry.getKey())) {
                        LogUtils.debug(getClass() + " 移除缓存，key=>" + entry.getKey());
                        iterator.remove();
                    }
                }

                updateTime.set(System.currentTimeMillis());
                LogUtils.debug(getClass() + " 获取批量数据成功");
            } catch (CanNotObtainDataException e) {
                LogUtils.debug(getClass() + " 获取批量数据失败");
            }
        } finally {
            isUpdateing.set(false);
        }
    }

    /**
     * 不能获取源数据时抛出此异常
     *
     * @author LXP
     * @version 创建时间：2017年3月31日 下午2:50:30
     */
    public static class CanNotObtainDataException extends Exception {

    }
}
