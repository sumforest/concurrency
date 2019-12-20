package com.sen.concurrency3.juc.collections.custom;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: Sen
 * @Date: 2019/12/20 14:34
 * @Description:
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

    @Override
    public V put(K key, V value) {
        try {
            lock.lock();
            Map<K,V> newMap = new HashMap<>(map);
            newMap.put(key, value);
            map = newMap;
        }finally {
            lock.unlock();
        }
        return value;
    }

    @Override
    public V remove(Object key) {
        try {
            lock.lock();
            Map<K,V> newMap = new HashMap<>(map);
            V oldValue = newMap.remove(key);
            map = newMap;
            return oldValue;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        try{
            lock.lock();
            Map<K,V> newMap = new HashMap<>(map);
            newMap.putAll(m);
            map = newMap;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {
        try {
            lock.lock();
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
