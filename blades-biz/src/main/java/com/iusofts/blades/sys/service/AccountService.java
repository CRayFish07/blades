package com.iusofts.blades.sys.service;

import com.iusofts.blades.sys.model.Account;

public interface AccountService {
	public int save(Account account);

	public int remove(String id);

	public int update(Account account);
	
	public Account get(String id);
	
	/**
	 * 根据用户名获取账号信息
	 * @param username
	 * @return
	 * @author：Ivan
	 * @date：2016年3月2日 上午10:00:19
	 */
	public Account getByUsername(String username);

	/**
	 * 修改密码
	 * 
	 * @param account
	 * @param newPassword
	 * @return
	 * @author：Ivan
	 * @date：2016年3月1日 下午3:12:45
	 */
	public int updatePassword(String username,String oldPassword, String newPassword);

	/**
	 * 账号密码登陆
	 * 
	 * @param account
	 * @param password
	 * @return
	 * @author：Ivan
	 * @date：2016年3月1日 下午3:19:31
	 */
	public Account login(String username, String password);
	
	/**
	 * 
	 * 描述: 用户名是否存在
	 * @param userName
	 * @return
	 * @author Asker_lve
	 * @date 2016年3月11日 下午6:15:31
	 */
	public int isUserNameExist(String userName);
}
