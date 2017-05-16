package com.iusofts.blades.sys.web.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.iusofts.blades.sys.common.util.ServiceLocator;
import com.iusofts.blades.sys.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: 字典工具类 (DictionaryUtil.java)
 * 
 * @Description:
 * 
 * @Date: 2013-7-18 下午04:05:37
 * @Author Ivan
 * @Version 1.0
 */
public final class DictionaryUtil {

	private static Logger logger = LoggerFactory.getLogger(DictionaryUtil.class);

	private static final DictionaryUtil instance = new DictionaryUtil();

	private HashMap<String, HashMap<String, String[]>> dictMap = new LinkedHashMap<String, HashMap<String, String[]>>();

	private DictionaryUtil() {
		DictionaryService dictionaryService = (DictionaryService) ServiceLocator
				.init().getService(DictionaryService.class);
		logger.info("开始导入字典...");
		this.dictMap = dictionaryService.loadAllEnableDictionary();
		logger.info("字典导入完成！共导入" + this.getSize() + "条字典。");
	}

	public static DictionaryUtil getInstance() {
		return instance;
	}

	private int getSize() {
		return this.dictMap.size();
	}

	/**
	 * 根据字典代码获得字典项
	 * 
	 * @param dictCode
	 * 
	 * @return
	 */
	public HashMap<String, String[]> getDicItemsByDicCode(String dictCode) {
		return this.dictMap.get(dictCode);
	}

	/**
	 * 根据字典代码删除字典
	 * 
	 * @param dictCode
	 */
	public void removeDict(String dictCode) {
		if (null == dictCode) {
			return;
		}
		this.dictMap.remove(dictCode);
	}

	/**
	 * 添加字典（项）
	 * 
	 * @param dictCode
	 * @param dicItem
	 */
	public void addDict(String dictCode, HashMap<String, String[]> dicItem) {
		if (null == dictCode) {
			return;
		}
		this.dictMap.put(dictCode, dicItem);
	}

	/**
	 * 删除字典项
	 * 
	 * @param dictCode
	 * @param dicItem
	 */
	public void removeDicItem(String dictCode, Map<String, String> dicItem) {
		if (null == dictCode || null == dicItem) {
			return;
		}
		Map<String, String[]> dicItemMap = this.dictMap.get(dictCode);
		if (dicItemMap == null) {
			return;
		}
		Set<Entry<String, String>> entrys = dicItem.entrySet();
		for (Entry<String, String> en : entrys) {
			dicItemMap.remove(en.getKey());
		}
	}

	/**
	 * 根据代码翻译字典名称
	 * @param type
	 * @param code
	 * @return
	 */
	public String getNameByCode(String type , String code){
		HashMap<String, String[]> map = getDicItemsByDicCode(type);
		if(map!=null){
			String[] values = map.get(code);
			if(values!=null && values.length>0){
				return values[0];
			}
		}
		return "";
	}
}
