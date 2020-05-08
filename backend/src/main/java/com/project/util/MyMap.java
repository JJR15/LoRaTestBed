package com.project.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MyMap<K,V> {

    private Map<K, V> kMap = new HashMap<K, V>();
    private Map<V, ConcurrentHashMap<K, String>> vMap = new HashMap<V, ConcurrentHashMap<K, String>>();
    private final static String VV = "1";

    public boolean put(K k, V v) {
        if (null == k || null == v) return false;
        if (kMap.containsKey(k)) {
            vMap.get(kMap.get(k)).remove(k);
        }
        kMap.put(k, v);
        if (vMap.containsKey(v)) {
            vMap.get(v).put(k, VV);
        } else {
            ConcurrentHashMap<K, String> set = new ConcurrentHashMap<K, String>();
            set.put(k, VV);
            vMap.put(v, set);
        }
        return true;
    }

    public boolean removeByKey(K k) {
        if(k == null) return false;
        V v = kMap.remove(k);
        if(v == null) return false;
        vMap.get(v).remove(k);
        if(vMap.get(v).isEmpty())
            vMap.remove(v);
        return true;
    }

    public int  removeByValue(V v) {
        if(v == null) return 0;
        Set<K> kSet = vMap.remove(v).keySet();
        for(K k : kSet) {
            kMap.remove(k);
        }
        return kSet.size();
    }

    public boolean containsKey(K k) {
        return kMap.containsKey(k);
    }

    public boolean containsValue(V v) {
        return vMap.containsKey(v);
    }

    public Set<K> keySet() {
        return kMap.keySet();
    }

    public Set<V> valueSet() {
        return vMap.keySet();
    }

    public V getByKey(K k) {
        return kMap.get(k);
    }

    public Set<K> getByValue(V v) {
        if(vMap.containsKey(v))
            return vMap.get(v).keySet();
        else return new HashSet<>();
    }

    public String toString() {
        return kMap.toString();
    }
}