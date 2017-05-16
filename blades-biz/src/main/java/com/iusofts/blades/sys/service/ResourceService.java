package com.iusofts.blades.sys.service;

import java.util.List;
import java.util.Map;

import com.iusofts.blades.sys.common.util.Page;
import com.iusofts.blades.sys.model.Resource;

public interface ResourceService {
	public int save(Resource resource);

	public int remove(String id);

	public int update(Resource resource);

	public List<Resource> getList(Resource resource);

	/**
	 * 分页查询
	 * 
	 * @param resource
	 * @param other
	 * @return
	 * @author：Ivan
	 * @date：2016年3月11日 上午9:31:52
	 */
	public Page<Resource> getPage(Resource resource, Map<String, String> other, int pageNo, int pageSize);

	/**
	 * 全部标记为不可用（用于更新时）
	 * 
	 * @return
	 * @author：Ivan
	 * @date：2016年3月10日 下午3:01:33
	 */
	public int enableAll();

	/**
	 * 更新全部资源
	 * 
	 * @param list
	 * @return
	 * @author：Ivan
	 * @date：2016年3月10日 下午3:28:15
	 */
	public int updateByList(List<Resource> list);

	/**
	 * 根据主键顺序排序
	 * 
	 * @param ids
	 * @return
	 * @author：Ivan
	 * @date：2016年3月10日 下午4:25:02
	 */
	public int sortByIds(String ids[]);
}
