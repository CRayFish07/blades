package com.iusofts.blades.sys.web.util.tags;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.iusofts.blades.sys.common.util.ServiceLocator;
import com.iusofts.blades.sys.model.Organization;
import com.iusofts.blades.sys.service.OrganizationService;
import com.iusofts.blades.sys.web.util.DictionaryUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: 系统字典标签(DictionaryTag.java)
 * 
 * @Description:
 * 
 * @Date: 2013-7-4 上午09:45:56
 * @Author Ivan
 * @Version 1.0
 */
public final class DictionaryTag {

	/**
	 * 复选框列表
	 * 
	 * @param dicCode
	 *            字典代码
	 * @param propertyName
	 *            属性名称
	 * @return 复选框列表字符串
	 */
	public static String getCheckBoxs(String dicCode, String propertyName) {
		StringBuffer sb = new StringBuffer();
		Map<String, String[]> dicItemMap = DictionaryUtil.getInstance()
				.getDicItemsByDicCode(dicCode);
		if (dicItemMap == null) {
			return "";
		}
		Set<Entry<String, String[]>> entrys = dicItemMap.entrySet();
		for (Entry<String, String[]> entry : entrys) {
			sb.append("<input type='checkbox' ");
			sb.append("name='" + propertyName + "' ");
			sb.append("id='" + propertyName + "' ");
			sb.append("value='" + entry.getKey() + "'");
			sb.append("/>");
			sb.append(entry.getValue()[0]);
		}
		return sb.toString();
	}

	/**
	 * 复选框列表（选中）
	 * 
	 * @param dicCode
	 *            字典代码
	 * @param dicItemCodes
	 *            以逗号分隔的字典项代码字符串
	 * @param propertyName
	 *            属性名称
	 * @return
	 */
	public static String getSelectedCheckBoxs(String dicCode,
			String dicItemCodes, String propertyName) {
		String itemCodes[] = dicItemCodes.split(",");
		StringBuffer sb = new StringBuffer();
		Map<String, String[]> dicItemMap = DictionaryUtil.getInstance()
				.getDicItemsByDicCode(dicCode);
		if (dicItemMap == null) {
			return "";
		}
		Set<Entry<String, String[]>> entrys = dicItemMap.entrySet();
		for (Entry<String, String[]> entry : entrys) {
			sb.append("<input type='checkbox' ");
			sb.append("name='" + propertyName + "' ");
			sb.append("id='" + propertyName + "' ");
			sb.append("value='" + entry.getKey() + "'");
			for (String itemCode : itemCodes) {
				if (entry.getKey().equals(itemCode)) {
					sb.append(" checked");
				}
			}
			sb.append("/>");
			sb.append(entry.getValue()[0]);
		}
		return sb.toString();
	}


	public static String getDsDictItemTree(String dictCode, String txtName,
			String name, String hidId) {
		StringBuffer sb = new StringBuffer();
		sb.append("<input type=\"text\" readonly name=" + txtName + " id="
				+ txtName + " onclick=\"javascript:toTableTreePage('"
				+ dictCode + "','" + txtName + "','" + hidId + "')\" />");
		sb.append("&nbsp;<a href=\"javascript:clear('" + txtName + "','"
				+ hidId + "');\">清空</a>");
		sb.append("<input type=\"hidden\" name=" + name + " id=" + hidId
				+ " />");
		return sb.toString();
	}

	/**
	 * @param style
	 * @param bname 在form中的别名 
	 * @param name 字典树中大项的代码 
	 * @param code 大类下面的的代码
	 * @param sessionCode 所属部门的单位代码
	 * @return
	 */
	public static String getOrgName(String style,String name, String sessionCode,String code) {
		StringBuffer sb=new StringBuffer("");
		if(null==style||StringUtils.isBlank(style)){
			style="style=\"width:60%;height:100%;border:0px;padding: 0px;\"";
		}
		if(name==null||StringUtils.isBlank(name)){
			name="dwdm";
		}
		if(sessionCode==null||StringUtils.isBlank(sessionCode)){
			sessionCode="320400000000";
		}
		OrganizationService orgManager=(OrganizationService) ServiceLocator.init().getService(OrganizationService.class);
		sb.append("<div class=\"content_wrap\">");
		sb.append("<li  style=\"list-style: none;\"><input id=\"orgSelNameID\" type=\"text\" readonly value=\""
				+orgManager.getByCode(code).getName()+"\" "+style+" />\n");
		sb.append("<input type=\"hidden\" id=\"orgSelCodeID\" name=\""+name+"\" value=\""+code+"\">\n");
		sb.append("<a id=\"chooseOrgTreeBtn\" href=\"#\" style=\"align:left;\" " +
				"onclick=\"showOrgTreeMenu('"+sessionCode+"','orgSelCodeID','orgSelNameID'); return false;\">机构选择</a></li>");
		sb.append("</div>");
		return sb.toString();
	}
	
	/****
	 *@param code 单位代码
	 */
	
	public static String getOrgName(String code) {
		if (code == null || StringUtils.isBlank(code)) {
			return "";
		}
		OrganizationService orgManager = (OrganizationService) ServiceLocator.init()
				.getService(OrganizationService.class);
		Organization org = orgManager.getByCode(code);
		if (org != null) {
			return org.getName();
		} else {
			return "";
		}
	}
	
	/**
	 * 获单位名称,多个以逗号分隔
	 * 
	 * @param dicItemCodes
	 *            以逗号分隔的单位代码集合串
	 * @return 单位名称集合串
	 */
	public static String getOrgNames(String dicItemCodes) {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isBlank(dicItemCodes)) {
			return "无对应字典项";
		}
		String itemCodes[] = dicItemCodes.split(",");
		int len = itemCodes.length;
		for (int i = 0; i < len; i++) {
			if (StringUtils.isNotBlank(getOrgName(itemCodes[i]))) {
				sb.append(getOrgName(itemCodes[i]));
			}else{
				sb.append("无对应字典项");
			}
			if (i != len - 1) {
				sb.append(";");
			}
		}
		return sb.toString();
	}
}
