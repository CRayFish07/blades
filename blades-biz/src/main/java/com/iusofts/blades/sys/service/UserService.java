package com.iusofts.blades.sys.service;

import java.util.List;
import java.util.Map;

import com.iusofts.blades.sys.common.util.Page;
import com.iusofts.blades.sys.model.Account;
import com.iusofts.blades.sys.model.User;
import com.iusofts.blades.sys.model.UserOrg;

public interface UserService {
	public int save(User user);
	
	public int update(User user, Account account);
	
	public int save(User user,Account account,List<UserOrg> orgList);

	public int remove(String id);

	public int update(User user);
	
	public User get(String id);
	
	public User getByUsername(String username);

	public Page<Map<String, String>> getPage(Map<String, String> queryMap,
			int pageNo, int pageSize);
	/**
	 * 
	 * 描述:批量删除
	 * @param ids
	 * @return
	 * @author Asker_lve
	 * @date 2016年3月10日 下午1:38:53
	 */
	public int delsteByIds(List<String> ids);
	
	/**
	 * 
	 * 描述: 修改系统用户有效性
	 * @param enabled
	 * @param id
	 * @return
	 * @author Asker_lve
	 * @date 2016年3月10日 下午3:04:41
	 */
	public int updateEnable(String enabled, String id);
	
	/**
	 * 
	 * 描述:批量修改系统用户有效性
	 * @param enabled
	 * @param ids
	 * @return
	 * @author Asker_lve
	 * @date 2016年3月10日 下午3:05:33
	 */
	public int updateEnables(String enabled, List<String> ids);
	
	/**
	 * 
	 * 描述: 用户修改
	 * @param user 用户
	 * @param account 系统用户公共资料
	 * @param orgList 所属单位 
	 * @return
	 * @author Asker_lve
	 * @date 2016年3月10日 下午6:09:29
	 */
	public int update(User user, Account account, List<UserOrg> orgList);
	
	/**
	 * 
	 * 描述: 身份证是否存在
	 * @param idNo
	 * @return
	 * @author Asker_lve
	 * @date 2016年3月11日 下午6:11:57
	 */
	int isIdNoExist(String idNo);
}
