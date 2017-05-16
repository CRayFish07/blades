package com.iusofts.blades.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iusofts.blades.sys.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iusofts.blades.sys.common.util.Page;
import com.iusofts.blades.sys.common.util.StringUtil;
import com.iusofts.blades.sys.common.util.date.DateUtil;
import com.iusofts.blades.sys.dao.OrganizationMapper;
import com.iusofts.blades.sys.model.Organization;
import com.iusofts.blades.sys.model.OrganizationExample;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationMapper organizationMapper;

	@Override
	public int save(Organization org) {
		org.setCode(org.getCode().toUpperCase());
		if (StringUtil.isBlank(org.getFcode()))
			org.setFcode(org.getCode());
		org.setUpdateTime(DateUtil.getCurrentTimeAs14String());
		return this.organizationMapper.insertSelective(org);
	}

	@Override
	public int remove(String id) {
		return this.organizationMapper.deleteByPrimaryKey(id)
				+ this.organizationMapper.deleteByPid(id);
	}

	@Override
	public int update(Organization org) {
		return this.organizationMapper.updateByPrimaryKeySelective(org);
	}

	@Override
	public Organization get(String id) {
		return this.organizationMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Organization> getList(Organization org) {
		OrganizationExample ex = new OrganizationExample();
		OrganizationExample.Criteria ca = ex.createCriteria();
		if (org != null) {
			if (StringUtil.isBlank(org.getName())) {
				ca.andNameEqualTo(org.getName());
			}
			if (StringUtil.isBlank(org.getCode())) {
				ca.andCodeEqualTo(org.getCode());
			}
			if (StringUtil.isBlank(org.getPid())) {
				ca.andPidEqualTo(org.getPid());
			}
		}
		return this.organizationMapper.selectByExample(ex);
	}

	@Override
	public Page<Organization> getPage(Organization org,
			Map<String, String> other, int pageNo, int pageSize) {
		OrganizationExample ex = new OrganizationExample();
		OrganizationExample.Criteria ca = ex.createCriteria();
		// 主要查询条件
		if (org != null) {
			if (StringUtil.isNotBlank(org.getName())) {
				ca.andNameLike("%" + org.getName() + "%");
			}
			if (StringUtil.isNotBlank(org.getCode())) {
				ca.andCodeLike("%" + org.getCode() + "%");
			}
			if (StringUtil.isNotBlank(org.getFcode())) {
				ca.andFcodeLike("%" + org.getFcode() + "%");
			}
			if (StringUtil.isNotBlank(org.getPid())) {
				ca.andPidEqualTo(org.getPid());
			} else {
				ca.andPidIsNull();
			}
		}
		// 其它查询条件
		if (other != null) {
			if (StringUtil.isNotBlank(other.get("startTime"))) {
				ca.andUpdateTimeGreaterThanOrEqualTo(other.get("startTime"));
			}
			if (StringUtil.isNotBlank(other.get("endTime"))) {
				ca.andUpdateTimeLessThanOrEqualTo(other.get("endTime"));
			}
		}
		// 分页
		Page<Organization> page = new Page<>(pageNo, pageSize,
				this.organizationMapper.countByExample(ex));
		ex.setLimitStart(page.getStartIndex());
		ex.setLimitSize(page.getPageSize());
		ex.setOrderByClause("order_no asc");
		page.setList(this.organizationMapper.selectByExample(ex));
		return page;
	}

	@Override
	public boolean codeExist(String code, String pid) {
		OrganizationExample ex = new OrganizationExample();
		OrganizationExample.Criteria ca = ex.createCriteria();
		ca.andCodeEqualTo(code.toUpperCase());
		List<Organization> list = null;
		int count = 0;
		if (StringUtil.isNotBlank(pid)) {
			// 获取根结点
			String rootId = this.organizationMapper.selectRootId(pid);
			// 根据根结点比对所有层是否存在重复
			count = this.organizationMapper.countByCode(code, rootId);
		} else {
			ca.andPidIsNull();
			list = this.organizationMapper.selectByExample(ex);
			count = list.size();
		}
		return count > 0 ? true : false;
	}

	@Override
	public boolean nameExist(String name, String pid) {
		OrganizationExample ex = new OrganizationExample();
		OrganizationExample.Criteria ca = ex.createCriteria();
		ca.andNameEqualTo(name);
		if (StringUtil.isNotBlank(pid)) {
			ca.andPidEqualTo(pid);
		} else {
			ca.andPidIsNull();
		}
		List<Organization> list = this.organizationMapper.selectByExample(ex);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, String>> getTreeDataByMap(
			Map<String, String> queryMap) {
		OrganizationExample ex = new OrganizationExample();
		OrganizationExample.Criteria ca = ex.createCriteria();
		ex.setOrderByClause("order_no asc");
		if (StringUtil.isNotBlank(queryMap.get("id"))) {
			ca.andPidEqualTo(queryMap.get("id"));
		} else {
			ca.andPidEqualTo(queryMap.get("pid"));
		}
		// 查询子项
		List<Organization> list = this.organizationMapper.selectByExample(ex);
		// 第一次加载添加根结点
		if (StringUtil.isBlank(queryMap.get("id"))
				&& StringUtil.isNotBlank(queryMap.get("pid"))) {
			Organization org = this.organizationMapper
					.selectByPrimaryKey(queryMap.get("pid"));
			if (org != null) {
				list.add(0, org);
			}
		}
		List<Map<String, String>> listMap = new ArrayList<>();
		// 允许的类型
		List<String> allowTypes = new ArrayList<>();
		if (StringUtil.isNotBlank(queryMap.get("allowType"))) {
			String allowType[] = queryMap.get("allowType").split(",");
			for (String string : allowType) {
				allowTypes.add(string);
			}
		}
		// 默认选中的节点
		List<String> defaultCheck = new ArrayList<>();
		if (StringUtil.isNotBlank(queryMap.get("defaultCheck"))) {
			String check[] = queryMap.get("defaultCheck").split(",");
			for (String string : check) {
				defaultCheck.add(string.toUpperCase());
			}
		}
		// 转换成Ztree需要的格式
		for (Organization org : list) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", org.getId());
			map.put("pId", org.getPid() == null ? "0" : org.getPid());
			map.put("name", org.getName());
			map.put("code", org.getCode());
			map.put("type", org.getOrgType());
			// 判断是否为父级
			ex.clear();
			ca = ex.createCriteria();
			ca.andPidEqualTo(org.getId());
			int count = this.organizationMapper.countByExample(ex);
			map.put("isParent", count > 0 ? "true" : "false");
			if (org.getPid() == null) {
				map.put("open", "true");
				map.put("checked", "isChecked");
			}
			// 设置图标
			if (StringUtil.isNotBlank(org.getIcon())) {
				map.put("icon", queryMap.get("root") + "/" + org.getIcon());
			} else {
				String icon = null;
				switch (org.getOrgType()) {
				case "0":
					icon = "/resource/sys/images/icon/org/root.gif";
					break;
				case "1":
					icon = "/resource/sys/images/icon/org/1_open.png";
					break;
				case "2":
					icon = "/resource/sys/images/icon/org/dept.gif";
					break;
				case "3":
					icon = "/resource/sys/images/icon/org/pos.gif";
					break;
				case "4":
					icon = "/resource/sys/images/icon/user.png";
					break;
				case "9":
					icon = "/resource/sys/images/icon/group.png";
					break;

				default:
					break;
				}
				if (icon != null)
					map.put("icon", queryMap.get("root") + icon);
			}
			// 设置选择框
			if (allowTypes.size() > 0 && !allowTypes.contains(map.get("type"))) {
				map.put("nocheck", "true");
			}
			// 设置是否默认选中
			if (defaultCheck.size() > 0
					&& defaultCheck.contains(map.get("code"))) {
				map.put("checked", "true");
			}
			listMap.add(map);
		}
		return listMap;
	}

	@Override
	public int delsteByIds(List<String> ids) {
		int n = 0;
		if (ids != null && ids.size() > 0) {
			for (String id : ids) {
				//若存在子节点
				if (this.organizationMapper.countByPid(id) > 0) {
					n = this.organizationMapper.deleteByPid(id);
					n = n + this.organizationMapper.deleteByPrimaryKey(id);
				}else{
					n = this.organizationMapper.deleteByPrimaryKey(id);
				}
			}
		}
		return n;
	}

	@Override
	public Organization getByCode(String code) {
		OrganizationExample example = new OrganizationExample();
		example.createCriteria().andEnabledEqualTo("1").andCodeEqualTo(code);
		List<Organization> organizationList = this.organizationMapper.selectByExample(example);
		if(organizationList!=null && organizationList.size()>0){
			return organizationList.get(0);
		}
		return null;
	}

}
