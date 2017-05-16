package com.iusofts.blades.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iusofts.blades.sys.common.util.Page;
import com.iusofts.blades.sys.model.Dictionary;

public interface DictionaryService {
	public int save(Dictionary dictionary);

	public int remove(String id);

	public int update(Dictionary dictionary);

	public Dictionary get(String id);
	
	public List<Dictionary> getList(Dictionary dictionary);

	/**
	 * 判断字段代码是否存在
	 * @param code
	 * @return
	 * @author：Ivan
	 * @date：2016年2月23日 下午2:57:10
	 */
	public boolean codeExist(String code,String pid);
	
	/**
	 * 判断字典名称是否存在
	 * @param name
	 * @return
	 * @author：Ivan
	 * @date：2016年2月23日 下午2:57:36
	 */
	public boolean nameExist(String name,String pid);

	/**
	 * 获取字典分页结果
	 * 
	 * @param dictionary
	 *            主查询条件
	 * @param other
	 *            其它查询条件
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页大小
	 * @return
	 * @author：Ivan
	 * @date：2016年2月22日 下午2:38:21
	 */
	public Page<Dictionary> getPage(Dictionary dictionary,
									Map<String, String> other, int pageNo, int pageSize);
	
	/**
	 * 获取字典树数据
	 * @param map
	 * @return
	 * @author：Ivan
	 * @date：2016年2月26日 下午3:29:53
	 */
	public List<Map<String,String>> getTreeDataByMap(Map<String,String> map);
	
	/**
	 * 
	 * 描述:批量删除字典
	 * @param ids
	 * @return
	 * @author Asker_lve
	 * @date 2016年3月9日 下午5:59:31
	 */
	public int delsteByIds(List<String> ids);

	/**
	 * 加载所以可用字段
	 * @return
	 */
	public HashMap<String, HashMap<String, String[]>> loadAllEnableDictionary();
}
