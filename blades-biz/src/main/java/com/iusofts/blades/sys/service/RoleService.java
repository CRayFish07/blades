package com.iusofts.blades.sys.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iusofts.blades.sys.common.util.Page;
import com.iusofts.blades.sys.model.Role;
/**
 * @描述:角色接口定义
 * @author Asker_lve
 * @date 2016年3月4日 上午10:03:09
 */
public interface RoleService {

	public int save(Role role);

	public int remove(String id);

	public int update(Role role);

	public Role get(String id);
	
	public List<Role> getList(Role role);

	/**
	 * 描述:分页查询
	 * @param role 查询条件
	 * @param other 其它查询条件
	 * @param pageNo 当前页码
	 * @param pageSize 每页显示条数
	 * @return 
	 * @author Asker_lve
	 * @date 2016年3月4日 上午10:05:35
	 */
	public Page<Role> getPage(Role role, Map<String, String> other,
			int pageNo, int pageSize);
	
	/**
	 * 
	 * 描述: 校验角色名称是否存在
	 * @param name
	 * @return
	 * @author Asker_lve
	 * @date 2016年3月4日 下午2:26:38
	 */
	public boolean nameExist(String name);
	
	/**
	 * 
	 * 描述:校验角色代码是否存在
	 * @param code
	 * @return
	 * @author Asker_lve
	 * @date 2016年3月4日 下午2:27:05
	 */
	public boolean codeExist(String code);
	
	/**
	 * 
	 * 描述:批量删除
	 * @param ids
	 * @return
	 * @author Asker_lve
	 * @date 2016年3月6日 下午9:29:05
	 */
	public int delsteByIds(List<String> ids);
	
	/**
	 * 
	 * 描述: 批量修改有效性
	 * @param ids
	 * @param enable
	 * @return
	 * @author Asker_lve
	 * @date 2016年3月9日 上午11:21:30
	 */
	public int updateEnables(@Param("enabled") String enabled,@Param("ids") List<String> ids);
	
	/**
	 * 
	 * 描述:修改单条有效性
	 * @param id
	 * @param enable
	 * @return
	 * @author Asker_lve
	 * @date 2016年3月9日 上午11:22:07
	 */
	public int updateEnable(@Param("enabled")String enabled,@Param("id")String id);
}
