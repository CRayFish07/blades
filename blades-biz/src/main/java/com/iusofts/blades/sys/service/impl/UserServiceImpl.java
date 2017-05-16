package com.iusofts.blades.sys.service.impl;

import java.util.List;
import java.util.Map;

import com.iusofts.blades.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iusofts.blades.sys.common.util.Page;
import com.iusofts.blades.sys.common.util.PasswordUtil;
import com.iusofts.blades.sys.common.util.StringUtil;
import com.iusofts.blades.sys.common.util.UUID;
import com.iusofts.blades.sys.common.util.ValidateUtil;
import com.iusofts.blades.sys.common.util.date.DateUtil;
import com.iusofts.blades.sys.dao.AccountMapper;
import com.iusofts.blades.sys.dao.UserMapper;
import com.iusofts.blades.sys.dao.UserOrgMapper;
import com.iusofts.blades.sys.model.Account;
import com.iusofts.blades.sys.model.User;
import com.iusofts.blades.sys.model.UserOrg;
import com.iusofts.blades.sys.service.AccountService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private UserOrgMapper userOrgMapper;
	@Autowired
	private AccountService accountManager;

	@Override
	public int save(User user) {
		return this.userMapper.insertSelective(user);
	}

	@Override
	public int remove(String id) {
		int n = 0;
		if (StringUtil.isNotBlank(id)) {
			n = this.userOrgMapper.deleteByUserId(id);
			n = n + this.accountMapper.deleteByPrimaryKey(id);
			n = n + this.userMapper.deleteByPrimaryKey(id);
		}
		return n;
	}

	@Override
	public int update(User user) {
		return this.userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public int update(User user, Account account) {
		if (StringUtil.isNotBlank(account.getEmail())
				|| StringUtil.isNotBlank(account.getMobile())) {
			account.setId(user.getId());
			this.accountMapper.updateByPrimaryKeySelective(account);
		}
		return this.userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public User get(String id) {
		return this.userMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page<Map<String, String>> getPage(Map<String, String> queryMap,
			int pageNo, int pageSize) {

		// 默认加载有效的用户
		String enabled = queryMap.get("enabled");
		if (StringUtil.isBlank(enabled)) {
			queryMap.put("enabled", "1");
		}

		Page<Map<String, String>> page = new Page<>(pageNo, pageSize,
				this.userMapper.countByMap(queryMap));
		queryMap.put("startIndex", page.getStartIndex() + "");
		queryMap.put("pageSize", page.getPageSize() + "");
		List<Map<String, String>> list = this.userMapper.selectByMap(queryMap);

		page.setList(list);
		return page;
	}

	@Override
	public int save(User user, Account account, List<UserOrg> orgList) {
		// 1.添加账号
		account.setId(user.getId());
		account.setPassword(PasswordUtil.entryptPassword(account.getPassword()));
		account.setRegTime(DateUtil.getCurrentTimeAs14String());
		this.accountMapper.insertSelective(account);

		// 2.添加用户
		user.setUpdateTime(DateUtil.getCurrentTimeAs14String());
		int n = this.userMapper.insertSelective(user);

		// 3.添加用户所属单位
		for (UserOrg userOrg : orgList) {
			userOrg.setId(UUID.getUuid());
			userOrg.setUserId(user.getId());
			userOrg.setCreateTime(DateUtil.getCurrentTimeAs14String());
			this.userOrgMapper.insertSelective(userOrg);
		}
		return n;
	}

	@Override
	public int updateEnables(String enabled, List<String> ids) {
		return this.accountMapper.updateEnabledByIds(enabled, ids);
	}

	@Override
	public int delsteByIds(List<String> ids) {
		int n = 0;
		if (ValidateUtil.isValid(ids)) {
			n = this.userOrgMapper.deleteByUserIds(ids);
			n = n + this.accountMapper.deleteByIds(ids);
			n = n + this.userMapper.delsteByIds(ids);
		}
		return n;
	}

	@Override
	public int updateEnable(String enabled, String id) {
		return this.accountMapper.updateEnabledById(enabled, id);
	}

	@Override
	public User getByUsername(String username) {
		Account account=this.accountManager.getByUsername(username);
		User user=this.userMapper.selectByPrimaryKey(account.getId());
		user.setUsername(account.getUsername());
		user.setEmail(account.getEmail());
		user.setMobile(account.getMobile());
		user.setEnabled(account.getEnabled());
		user.setPassword(account.getPassword());
		return user;
	}

	@Override
	public int update(User user, Account account, List<UserOrg> orgList) {
		// 1、修改帐号信息
		int n = this.accountMapper.updateByPrimaryKeySelective(account);
		// 2、修改用户信息
		user.setUpdateTime(DateUtil.getCurrentTimeAs14String());
		n = n + this.userMapper.updateByPrimaryKeySelective(user);
		// 3、修改用户所属单位
		this.userOrgMapper.deleteByUserId(user.getId());
		for (UserOrg userOrg : orgList) {
			userOrg.setId(UUID.getUuid());
			userOrg.setUserId(user.getId());
			userOrg.setCreateTime(DateUtil.getCurrentTimeAs14String());
			n = n + this.userOrgMapper.insertSelective(userOrg);
		}
		return n;
	}

	@Override
	public int isIdNoExist(String idNo) {
		return this.userMapper.isIdNoExist(idNo);
	}

}
