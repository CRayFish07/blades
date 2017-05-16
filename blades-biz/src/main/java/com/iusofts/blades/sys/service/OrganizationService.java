package com.iusofts.blades.sys.service;

import java.util.List;
import java.util.Map;

import com.iusofts.blades.sys.common.util.Page;
import com.iusofts.blades.sys.model.Organization;

public interface OrganizationService {
	public int save(Organization org);

	public int remove(String id);

	public int update(Organization org);

	public Organization get(String id);
	
	public List<Organization> getList(Organization org);

	/**
	 * 判断机构代码是否存在
	 * @param code
	 * @return
	 * @author：Ivan
	 * @date：2016年2月23日 下午2:57:10
	 */
	public boolean codeExist(String code,String pid);
	
	/**
	 * 判断组织名称是否存在
	 * @param name
	 * @return
	 * @author：Ivan
	 * @date：2016年2月23日 下午2:57:36
	 */
	public boolean nameExist(String name,String pid);

	/**
	 * 获取组织机构分页结果
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
	public Page<Organization> getPage(Organization org,
									  Map<String, String> other, int pageNo, int pageSize);
	
	/**
	 * 获取字典树数据
	 * @param pid
	 * @return
	 * @author：Ivan
	 * @date：2016年2月26日 下午3:29:53
	 */
	public List<Map<String,String>> getTreeDataByMap(Map<String,String> map);
	
	/**
	 * 
	 * 描述: 批量删除，子节点及本身
	 * @param ids
	 * @return
	 * @author Asker_lve
	 * @date 2016年3月9日 下午4:50:18
	 */
	public int delsteByIds(List<String> ids);

	/**
	 * 根据代码获取组织机构
	 * @param code
	 * @return
	 */
	public Organization getByCode(String code);
	
}
