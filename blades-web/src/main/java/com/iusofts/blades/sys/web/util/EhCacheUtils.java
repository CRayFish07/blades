/**
 * 
 */
package com.iusofts.blades.sys.web.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @ClassName: Eh缓存工具类(EhCacheUtils.java)
 * 
 * @Description:
 * 
 * @Date: 2011-7-18 下午02:35:13
 * @Author chymilk
 * @Version 1.0
 */
public final class EhCacheUtils {

	private static CacheManager cacheManager = null;

	static {
		cacheManager = CacheManager.create();
	}

	private EhCacheUtils() {
	}

	/**
	 * 根据缓存名称获得缓存对象
	 * 
	 * @param cacheName
	 * @return
	 */
	private static Cache getCacheByCacheName(String cacheName) {
		return cacheManager.getCache(cacheName);
	}

	/**
	 * 根据缓存名称和唯一标识从缓存对象中获得被缓存的对象
	 * 
	 * @param cacheName
	 *            缓存名称，即ehcache.xml中的cache name
	 * @param key
	 *            被缓存的对象的唯一标识
	 * @return
	 */
	public static Object getValue(String cacheName, Object key) {
		Cache cache = getCacheByCacheName(cacheName);
		if (cache == null) {
			return null;
		}
		Element element = cache.get(key);
		return element != null ? element.getObjectValue() : null;
	}

	/**
	 * 将需要被缓存的对象放入缓存
	 * 
	 * @param cacheName
	 *            缓存名称，即ehcache.xml中的cache name
	 * @param key
	 *            被缓存的对象的唯一标识
	 * @param obj
	 *            被缓存的对象
	 */
	public static void setValue(String cacheName, Object key, Object obj) {
		Cache cache = getCacheByCacheName(cacheName);
		if (cache != null) {
			cache.put(new Element(key, obj));
		}
	}

	/**
	 * 更新被缓存的对象
	 * 
	 * @param cacheName
	 *            缓存名称，即ehcache.xml中的cache name
	 * @param key
	 *            被缓存的对象的唯一标识
	 * @param obj
	 *            被缓存的对象
	 */
	public static void refresh(String cacheName, Object key, Object obj) {
		Cache cache = getCacheByCacheName(cacheName);
		if (cache != null) {
			cache.remove(key);
			cache.put(new Element(key, obj));
		}
	}

	/**
	 * 根据缓存名称和唯一标识从缓存对象中删除被缓存的对象
	 * 
	 * @param cacheName
	 *            缓存名称，即ehcache.xml中的cache name
	 * @param key
	 *            被缓存的对象的唯一标识
	 */
	public static void remove(String cacheName, Object key) {
		Cache cache = getCacheByCacheName(cacheName);
		if (cache != null) {
			cache.remove(key);
		}
	}

	/**
	 * 根据缓存名称清空所有被缓存的对象
	 * 
	 * @param cacheName
	 *            缓存名称，即ehcache.xml中的cache name
	 */
	public static void clear(String cacheName) {
		Cache cache = getCacheByCacheName(cacheName);
		if (cache != null) {
			cache.removeAll();
		}
	}
	
	/**
	 * 销毁缓存管理对象
	 */
	public static void distroy() {
		cacheManager.shutdown();
	}
}
