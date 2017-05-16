package com.iusofts.blades.sys.service.impl;

import java.util.List;

import com.iusofts.blades.sys.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iusofts.blades.sys.common.util.PasswordUtil;
import com.iusofts.blades.sys.common.util.StringUtil;
import com.iusofts.blades.sys.common.util.date.DateUtil;
import com.iusofts.blades.sys.dao.AccountMapper;
import com.iusofts.blades.sys.model.Account;
import com.iusofts.blades.sys.model.AccountExample;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountMapper accountMapper;
	
	@Override
	public int save(Account account) {
		account.setPassword(PasswordUtil.entryptPassword(account.getPassword()));
		account.setRegTime(DateUtil.getCurrentTimeAs14String());
		return this.accountMapper.insertSelective(account);
	}

	@Override
	public int remove(String id) {
		return this.accountMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int update(Account account) {
		return this.accountMapper.updateByPrimaryKeySelective(account);
	}

	@Override
	public Account get(String id) {
		return this.accountMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updatePassword(String username,String oldPassword, String newPassword) {
		Account account=getByUsername(username);
		if(account!=null&&StringUtil.isNotBlank(newPassword)){
			//校验原密码
			if(PasswordUtil.verify(oldPassword, account.getPassword())){
				account.setPassword(PasswordUtil.generate(newPassword));
				return this.accountMapper.updateByPrimaryKeySelective(account);
			}
		}
		return 0;
	}

	@Override
	public Account login(String username, String password) {
		Account account=getByUsername(username);
		if(account!=null&&StringUtil.isNotBlank(password)){
			if(PasswordUtil.verify(password, account.getPassword())){
				account.setPassword(null);
				return account;
			}
		}
		return null;
	}

	@Override
	public Account getByUsername(String username) {
		AccountExample ex=new AccountExample();
		ex.createCriteria().andUsernameEqualTo(username);
		List<Account> list=this.accountMapper.selectByExample(ex);
		return list.size()>0?list.get(0):null;
	}

	@Override
	public int isUserNameExist(String userName) {
		return this.accountMapper.isUserNameExist(userName);
	}

}
