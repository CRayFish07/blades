package com.iusofts.blades.sys.service.impl;

import java.util.List;
import java.util.Map;

import com.iusofts.blades.sys.common.util.Page;
import com.iusofts.blades.sys.model.Role;
import com.iusofts.blades.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iusofts.blades.sys.common.util.StringUtil;
import com.iusofts.blades.sys.dao.RoleMapper;
import com.iusofts.blades.sys.model.RoleExample;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public int save(Role role) {
		return this.roleMapper.insertSelective(role);
	}

	@Override
	public int remove(String id) {
		return this.roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int update(Role role) {
		return this.roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public Role get(String id) {
		return this.roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Role> getList(Role role) {
		RoleExample roCriteria = new RoleExample();
		RoleExample.Criteria ro = roCriteria.createCriteria();
		if (role != null) {
			if (StringUtil.isNotBlank(role.getName())) {
				ro.andNameEqualTo(role.getName());
			}
			if (StringUtil.isNotBlank(role.getCode())) {
				ro.andCodeEqualTo(role.getCode());
			}
		}

		return this.roleMapper.selectByExample(roCriteria);
	}

	@Override
	public Page<Role> getPage(Role role, Map<String, String> other, int pageNo,
							  int pageSize) {
		RoleExample roCriteria = new RoleExample();
		RoleExample.Criteria ro = roCriteria.createCriteria();
		// 查询条件
		if (role != null) {
			if (StringUtil.isNotBlank(role.getCode())) {
				ro.andCodeLike("%" + role.getCode() + "%");
			}
			if (StringUtil.isNotBlank(role.getName())) {
				ro.andNameLike("%" + role.getName() + "%");
			}
			if (StringUtil.isNotBlank(role.getAlias())) {
				ro.andAliasLike("%" + role.getAlias() + "%");
			}
			if (StringUtil.isNotBlank(other.get("enable"))) {
				ro.andEnbaledEqualTo(other.get("enable"));
			} else {
				// 默认显示有效的
				ro.andEnbaledEqualTo("1");
			}
		}

		// 分页
		Page<Role> page = new Page<Role>(pageNo, pageSize,
				this.roleMapper.countByExample(roCriteria));
		roCriteria.setLimitStart(page.getStartIndex());
		roCriteria.setLimitSize(page.getPageSize());
		// 切换排序
		if ("0".equals(other.get("sort"))) {
			roCriteria.setOrderByClause("order_no desc");
		} else {
			// 默认排序
			roCriteria.setOrderByClause("order_no asc");
		}

		page.setList(this.roleMapper.selectByExample(roCriteria));
		return page;
	}

	@Override
	public boolean nameExist(String name) {
		return this.roleMapper.nameExist(name) != 0 ? true : false;
	}

	@Override
	public boolean codeExist(String code) {
		return this.roleMapper.codeExist(code) != 0 ? true : false;
	}

	@Override
	public int delsteByIds(List<String> ids) {
		return this.roleMapper.delsteByIds(ids);
	}

	@Override
	public int updateEnables(String enabled, List<String> ids) {
		return this.roleMapper.updateEnables(enabled, ids);
	}

	@Override
	public int updateEnable(String enabled, String id) {
		return this.roleMapper.updateEnable(enabled, id);
	}
}
