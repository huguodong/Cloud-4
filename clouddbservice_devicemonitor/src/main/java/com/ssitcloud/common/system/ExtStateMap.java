package com.ssitcloud.common.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class ExtStateMap {
    private final static ConcurrentMap<String, String> states = new ConcurrentHashMap<>(64);

    public static void putState(String deviceId, String state) {
        states.put(deviceId, state);
    }

    public static void remove(String deviceId){
        states.remove(deviceId);
    }

    /**
     * 删除不在集合内的键值，若传入空集合则不做任何操作
     * @param deviceIds 集合
     */
    public static void removeNotIn(Set<String> deviceIds){
        if(deviceIds.isEmpty()){
            return;
        }
        Set<String> strings = states.keySet();
        List<String> removes = new ArrayList<>(strings.size());
        for (String string : strings) {
            if(!deviceIds.contains(string)){
                removes.add(string);
            }
        }

        for (String remove : removes) {
            remove(remove);
        }
    }

    public static void clear(){
        states.clear();
    }

    public static Set<Map.Entry<String, String>> entrySet(){
        return states.entrySet();
    }
}
