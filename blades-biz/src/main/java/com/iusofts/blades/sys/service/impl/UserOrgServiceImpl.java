package com.iusofts.blades.sys.service.impl;

import java.util.List;
import java.util.Map;

import com.iusofts.blades.sys.dao.UserOrgMapper;
import com.iusofts.blades.sys.model.UserOrg;
import com.iusofts.blades.sys.service.UserOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserOrgServiceImpl implements UserOrgService {

	@Autowired
	private UserOrgMapper userOrgMapper;
	
	@Override
	public int save(UserOrg userOrg) {
		return this.userOrgMapper.insertSelective(userOrg);
	}

	@Override
	public int remove(String id) {
		return this.userOrgMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int update(UserOrg userOrg) {
		return this.userOrgMapper.updateByPrimaryKeySelective(userOrg);
	}

	@Override
	public UserOrg get(String id) {
		return this.userOrgMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Map<String, String>> selectByUid(String uid) {
		return this.userOrgMapper.selectByUid(uid);
	}

}
