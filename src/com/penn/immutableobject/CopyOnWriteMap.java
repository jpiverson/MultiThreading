package com.penn.immutableobject;

import java.util.HashMap;
import java.util.Map;

/**
 * Immutable Object相关知识，Copy-On-Write
 * 
 * Java标准实现，java.util.concurrent.CopyOnWriteArrayList
 * 适用场景：遍历操作的频率比添加和删除操作更加频发的多线程场景设计，例如黑/白名单，商品类目的访问和更新，路由表的访问和更新等
 * 
 * 根据Copy-On-Write思想CopyOnWriteMap
 * 
 * @author penn
 *
 * @param <K>
 * @param <V>
 */

public class CopyOnWriteMap<K, V> implements Map<K, V>, Cloneable {

	private volatile Map<K, V> internalMap;

	public CopyOnWriteMap() {
		internalMap = new HashMap<K, V>();
	}

	public V put(K key, V value) {
		synchronized (this) {
			Map<K, V> newMap = new HashMap<K, V>(internalMap);
			V val = newMap.put(key, value);
			internalMap = newMap;
			return val;
		}
	}

	public V get(Object key) {
		return internalMap.get(key);
	}

	public void putAll(Map<? extends K, ? extends V> newData) {
		synchronized (this) {
			Map<K, V> newMap = new HashMap<K, V>(internalMap);
			newMap.putAll(newData);
			internalMap = newMap;
		}
	}

}
