package com.iusofts.blades.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iusofts.blades.sys.common.util.Page;
import com.iusofts.blades.sys.common.util.date.DateUtil;
import com.iusofts.blades.sys.dao.DictionaryMapper;
import com.iusofts.blades.sys.enums.EnabledFlag;
import com.iusofts.blades.sys.model.Dictionary;
import com.iusofts.blades.sys.model.DictionaryExample;
import com.iusofts.blades.sys.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iusofts.blades.sys.common.util.StringUtil;

@Service
public class DictionaryServiceImpl implements DictionaryService {

	@Autowired
	private DictionaryMapper dictionaryMapper;

	@Override
	public int save(Dictionary dictionary) {
		dictionary.setCode(dictionary.getCode().toUpperCase());
		if (StringUtil.isBlank(dictionary.getFcode())){
			dictionary.setFcode(dictionary.getCode());
		}
		if(StringUtil.isBlank(dictionary.getPid())) {
			dictionary.setPid("0");
		}
		dictionary.setUpdateTime(DateUtil.getCurrentTimeAs14String());
		return this.dictionaryMapper.insertSelective(dictionary);
	}

	@Override
	public int remove(String id) {
		return this.dictionaryMapper.deleteByPrimaryKey(id) + this.dictionaryMapper.deleteByPid(id);
	}

	@Override
	public int update(Dictionary dictionary) {
		if(StringUtil.isBlank(dictionary.getPid())) {
			dictionary.setPid("0");
		}
		dictionary.setUpdateTime(DateUtil.getCurrentTimeAs14String());
		return this.dictionaryMapper.updateByPrimaryKeySelective(dictionary);
	}

	@Override
	public Dictionary get(String id) {
		return this.dictionaryMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Dictionary> getList(Dictionary dictionary) {
		DictionaryExample ex = new DictionaryExample();
		DictionaryExample.Criteria ca = ex.createCriteria();
		if (dictionary != null) {
			if (StringUtil.isNotBlank(dictionary.getName())) {
				ca.andNameEqualTo(dictionary.getName());
			}
			if (StringUtil.isNotBlank(dictionary.getCode())) {
				ca.andCodeEqualTo(dictionary.getCode());
			}
			if (StringUtil.isNotBlank(dictionary.getPid())) {
				ca.andPidEqualTo(dictionary.getPid());
			}
		}
		return this.dictionaryMapper.selectByExample(ex);
	}

	@Override
	public Page<Dictionary> getPage(Dictionary dictionary, Map<String, String> other, int pageNo, int pageSize) {
		DictionaryExample ex = new DictionaryExample();
		DictionaryExample.Criteria ca = ex.createCriteria();
		// 主要查询条件
		if (dictionary != null) {
			if (StringUtil.isNotBlank(dictionary.getName())) {
				ca.andNameLike("%" + dictionary.getName() + "%");
			}
			if (StringUtil.isNotBlank(dictionary.getCode())) {
				ca.andCodeLike("%" + dictionary.getCode() + "%");
			}
			if (StringUtil.isNotBlank(dictionary.getFcode())) {
				ca.andFcodeLike("%" + dictionary.getFcode() + "%");
			}
			if (StringUtil.isNotBlank(dictionary.getPid())) {
				ca.andPidEqualTo(dictionary.getPid());
			} else {
				ca.andPidEqualTo("0");
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
		Page<Dictionary> page = new Page<>(pageNo, pageSize, this.dictionaryMapper.countByExample(ex));
		ex.setLimitStart(page.getStartIndex());
		ex.setLimitSize(page.getPageSize());
		ex.setOrderByClause("order_no asc");
		page.setList(this.dictionaryMapper.selectByExample(ex));
		return page;
	}

	@Override
	public boolean codeExist(String code, String pid) {
		DictionaryExample ex = new DictionaryExample();
		DictionaryExample.Criteria ca = ex.createCriteria();
		ca.andCodeEqualTo(code.toUpperCase());
		List<Dictionary> list = null;
		int count = 0;
		if (StringUtil.isNotBlank(pid)) {
			// 获取根结点
			String rootId = this.dictionaryMapper.selectRootId(pid);
			// 根据根结点比对所有层是否存在重复
			count = this.dictionaryMapper.countByCode(code, rootId);
		} else {
			ca.andPidIsNull();
			list = this.dictionaryMapper.selectByExample(ex);
			count = list.size();
		}
		return count > 0 ? true : false;
	}

	@Override
	public boolean nameExist(String name, String pid) {
		DictionaryExample ex = new DictionaryExample();
		DictionaryExample.Criteria ca = ex.createCriteria();
		ca.andNameEqualTo(name);
		if (StringUtil.isNotBlank(pid)) {
			ca.andPidEqualTo(pid);
		} else {
			ca.andPidIsNull();
		}
		List<Dictionary> list = this.dictionaryMapper.selectByExample(ex);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, String>> getTreeDataByMap(Map<String, String> queryMap) {
		DictionaryExample ex = new DictionaryExample();
		DictionaryExample.Criteria ca = ex.createCriteria();
		if (StringUtil.isNotBlank(queryMap.get("id"))) {
			ca.andPidEqualTo(queryMap.get("id"));
		} else {
			ca.andPidEqualTo(queryMap.get("pid"));
		}
		ex.setOrderByClause("order_no asc");
		// 查询子项
		List<Dictionary> list = this.dictionaryMapper.selectByExample(ex);
		// 第一次加载添加根结点
		if (StringUtil.isBlank(queryMap.get("id")) && StringUtil.isNotBlank(queryMap.get("pid"))) {
			Dictionary dic = this.dictionaryMapper.selectByPrimaryKey(queryMap.get("pid"));
			if (dic != null) {
				list.add(0, dic);
			}
		}
		List<Map<String, String>> listMap = new ArrayList<>();
		// 转换成Ztree需要的格式
		for (Dictionary dictionary : list) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", dictionary.getId());
			map.put("pId", dictionary.getPid() == null ? "0" : dictionary.getPid());
			map.put("name", dictionary.getName());
			// 判断是否为父级
			ex.clear();
			ca = ex.createCriteria();
			ca.andPidEqualTo(dictionary.getId());
			ex.setOrderByClause("order_no asc");
			int count = this.dictionaryMapper.countByExample(ex);
			map.put("isParent", count > 0 ? "true" : "false");
			if (dictionary.getPid() == null) {
				map.put("open", "true");
				map.put("checked", "isChecked");
			}
			// 设置图标
			if (StringUtil.isNotBlank(dictionary.getIcon())) {
				map.put("icon", queryMap.get("root") + dictionary.getIcon());
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
				// 若存在子节点
				if (this.dictionaryMapper.countByPid(id) > 0) {
					n = this.dictionaryMapper.deleteByPid(id);
					n = n + this.dictionaryMapper.deleteByPrimaryKey(id);
				} else {
					n = this.dictionaryMapper.deleteByPrimaryKey(id);
				}
			}
		}
		return n;
	}

	@Override
	public HashMap<String, HashMap<String, String[]>> loadAllEnableDictionary() {
		DictionaryExample example = new DictionaryExample();
		example.createCriteria().andEnabledEqualTo(EnabledFlag.ENABLE.getCode()).andPidEqualTo("0");
		List<Dictionary> dictionaryList = this.dictionaryMapper.selectByExample(example);
		HashMap<String, HashMap<String, String[]>> dicMap = new HashMap<>();
		for (Dictionary dictionary : dictionaryList) {
			HashMap<String, String[]> childMap = new HashMap<>();
			List<Dictionary> childDictionaryList = this.dictionaryMapper.selectByPid(dictionary.getId());
			for (Dictionary childDictionary : childDictionaryList) {
				childMap.put(childDictionary.getCode(),new String[]{childDictionary.getName(),childDictionary.getAlias()});
			}
			dicMap.put(dictionary.getCode(),childMap);
		}
		return dicMap;
	}

}
