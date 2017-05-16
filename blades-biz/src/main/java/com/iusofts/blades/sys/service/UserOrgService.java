package com.iusofts.blades.sys.service;

import java.util.List;
import java.util.Map;

import com.iusofts.blades.sys.model.UserOrg;

public interface UserOrgService {
	public int save(UserOrg userOrg);

	public int remove(String id);

	public int update(UserOrg userOrg);
	
	public UserOrg get(String id);
	
	/**
     * 根据用户ID获取用户岗位信息
     * @param uid
     * @return
     * @author：Ivan
     * @date：2016年3月7日 下午5:30:19
     */
    List<Map<String, String>> selectByUid(String uid);
}
