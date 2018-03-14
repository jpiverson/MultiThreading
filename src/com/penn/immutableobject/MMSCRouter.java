package com.penn.immutableobject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 路由规则管理器
 * 
 * @author penn
 *
 */
public class MMSCRouter {

	// 用volatile修饰，保证多线程环境下该变量的可见性
	private static volatile MMSCRouter instance = new MMSCRouter();

	private final Map<String, MMSCInfo> routeMap;

	public MMSCRouter() {
		this.routeMap = MMSCRouter.retrieveRouteMapFromDB();
	}

	private static Map<String, MMSCInfo> retrieveRouteMapFromDB() {
		Map<String, MMSCInfo> map = new HashMap<String, MMSCInfo>();
		// 加载数据过程略
		return map;
	}

	public static MMSCRouter getInstance() {
		return instance;
	}

	public MMSCInfo getMMSC(String msisdnPrefix) {
		return routeMap.get(msisdnPrefix);
	}

	/**
	 * 将当前MMSCRouter的实例更新为指定的新实例
	 * 
	 * @param newInstance
	 */
	public static void setInstance(MMSCRouter newInstance) {
		instance = newInstance;
	}

	private static Map<String, MMSCInfo> deepCopy(Map<String, MMSCInfo> m) {
		Map<String, MMSCInfo> result = new HashMap<String, MMSCInfo>();
		for (String key : m.keySet()) {
			result.put(key, new MMSCInfo(m.get(key)));
		}
		return result;
	}

	public Map<String, MMSCInfo> getRouteMap() {
		// 防御性复制
		return Collections.unmodifiableMap(deepCopy(routeMap));
	}

}
