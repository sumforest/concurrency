package com.sen.concurrency3.juc.collections.custom;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Sen
 * @Date: 2019/12/20 14:34
 * @Description: 手动实现CopyOnWriteMap
 */
public class CopyOnWriteMap<K,V> implements Map<K,V> {

    private volatile Map<K,V> map;

    private final ReentrantLock lock = new ReentrantLock();

    public CopyOnWriteMap() {
        this.map = new HashMap<>();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    /**
     * 重写put方法
     * @param key
     * @param value
     * @return
     */
    @Override
    public V put(K key, V value) {
        lock.lock();
        try {
            // 创建一个map副本
            Map<K,V> newMap = new HashMap<>(map);
            newMap.put(key, value);
            // 把map的引用指向新的map
            map = newMap;
        }finally {
            lock.unlock();
        }
        return value;
    }

    /**
     * 重写remove
     * @param key
     * @return
     */
    @Override
    public V remove(Object key) {
        lock.lock();
        try {
            // 创建一个map副本
            Map<K,V> newMap = new HashMap<>(map);
            V oldValue = newMap.remove(key);
            // 把map的引用指向新的map
            map = newMap;
            return oldValue;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        lock.lock();
        try{
            // 创建一个map副本
            Map<K,V> newMap = new HashMap<>(map);
            newMap.putAll(m);
            // 把map的引用指向新的map
            map = newMap;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {
        lock.lock();
        try {
            // 直接把map引用指向一个全新的map
            map = new HashMap<>();
        }finally {
            lock.unlock();
        }
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }
}
