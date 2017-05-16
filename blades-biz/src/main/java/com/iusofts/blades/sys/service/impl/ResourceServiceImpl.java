package com.iusofts.blades.sys.service.impl;

import java.util.List;
import java.util.Map;

import com.iusofts.blades.sys.common.util.Page;
import com.iusofts.blades.sys.common.util.date.DateUtil;
import com.iusofts.blades.sys.model.Resource;
import com.iusofts.blades.sys.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iusofts.blades.sys.common.util.StringUtil;
import com.iusofts.blades.sys.dao.ResourceMapper;
import com.iusofts.blades.sys.model.ResourceExample;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceMapper resourceMapper;
	
	@Override
	public int save(Resource resource) {
		return this.resourceMapper.insertSelective(resource);
	}

	@Override
	public int remove(String id) {
		return this.resourceMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int update(Resource resource) {
		return this.resourceMapper.updateByPrimaryKey(resource);
	}

	@Override
	public List<Resource> getList(Resource resource) {
		ResourceExample ex=new ResourceExample();
		ResourceExample.Criteria ca=ex.createCriteria();
		if(StringUtil.isNotBlank(resource.getName())){
			ca.andNameLike("%"+resource.getName()+"%");
		}
		ca.andEnabledEqualTo(1);
		ex.setOrderByClause("order_no asc");
		return this.resourceMapper.selectByExample(ex);
	}

	@Override
	public int enableAll() {
		Resource resource=new Resource();
		resource.setEnabled(0);
		return this.resourceMapper.updateByExampleSelective(resource, new ResourceExample());
	}

	@Override
	public int updateByList(List<Resource> list) {
		enableAll();
		int count=0;
		for (Resource resource : list) {
			Resource res=new Resource();
			res.setId(resource.getId());
			res.setName(resource.getName());
			res.setAlias(resource.getAlias());
			res.setCode(resource.getCode());
			res.setPermission(resource.getPermission());
			res.setUrl(resource.getUrl());
			res.setUpdateTime(DateUtil.getCurrentTimeAs14String());
			res.setEnabled(1);
			int n=this.resourceMapper.updateByPrimaryKeySelective(res);
			count+=n;
			if(n==0){
				count+=this.resourceMapper.insertSelective(resource);
			}
		}
		return count;
	}

	@Override
	public int sortByIds(String[] ids) {
		Resource resource=new Resource();
		int n=0;
		for (int i = 0; i < ids.length; i++) {
			resource.setId(ids[i]);
			resource.setOrderNo(i+1);
			n+=this.resourceMapper.updateByPrimaryKeySelective(resource);
		}
		return n;
	}

	@Override
	public Page<Resource> getPage(Resource resource, Map<String, String> other, int pageNo, int pageSize) {
		ResourceExample ex=new ResourceExample();
		ResourceExample.Criteria ca=ex.createCriteria();
		if(StringUtil.isNotBlank(resource.getName())){
			ca.andNameLike("%"+resource.getName()+"%");
		}
		if(resource.getEnabled()!=null){
			ca.andEnabledEqualTo(resource.getEnabled());
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
		Page<Resource> page=new Page<>(pageNo, pageSize, this.resourceMapper.countByExample(ex));
		ex.setLimitStart(page.getStartIndex());
		ex.setLimitSize(page.getPageSize());
		page.setList(this.resourceMapper.selectByExample(ex));
		return page;
	}

}
