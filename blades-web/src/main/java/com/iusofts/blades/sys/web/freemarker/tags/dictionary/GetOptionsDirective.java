package com.iusofts.blades.sys.web.freemarker.tags.dictionary;

import com.iusofts.blades.sys.common.util.StringUtil;
import com.iusofts.blades.sys.web.util.DictionaryUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

/**
 * 数据字典根据key显示value
 * 
 * @author Ivan
 */
public class GetOptionsDirective implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		SimpleScalar type = (SimpleScalar) params.get("type");
		SimpleScalar paramValue = (SimpleScalar) params.get("value");
		SimpleScalar defaultStr = (SimpleScalar) params.get("default");
		Writer out = env.getOut();
		String result = "";
		if(type!=null) {
			if (paramValue != null && StringUtil.isNotBlank(paramValue.getAsString())) {
				if (defaultStr != null && StringUtil.isNotBlank(defaultStr.getAsString())) {
					result = getSelectedOptions(type.getAsString(), paramValue.getAsString(),defaultStr.getAsString());
				}else{
					result = getSelectedOptions(type.getAsString(), paramValue.getAsString());
				}
			}else{
				result = getOptions(type.getAsString());
			}
			if (StringUtils.isEmpty(result)){
				result = "";
			}
		}
		
		out.write(result);
	}

	/**
	 * 获得下拉列表选项
	 *
	 * @param dicCode
	 *            字典代码
	 * @return 下拉列表字符串
	 */
	private String getOptions(String dicCode) {
		StringBuffer sb = new StringBuffer();
		Map<String, String[]> dicItemMap = DictionaryUtil.getInstance()
				.getDicItemsByDicCode(dicCode);
		if (dicItemMap == null) {
			return "";
		}
		Set<Map.Entry<String, String[]>> entrys = dicItemMap.entrySet();
		for (Map.Entry<String, String[]> entry : entrys) {
			sb.append("<option value=");
			sb.append("'" + entry.getKey() + "'");
			sb.append(">");
			sb.append(entry.getValue()[0]);
			sb.append("</option>");
		}
		return sb.toString();
	}

	/**
	 * 获得下拉列表选项（选中）
	 *
	 * @param dicCode
	 *            字典代码
	 * @param dicItemCode
	 *            字典项代码
	 * @return 下拉列表字符串
	 */
	private String getSelectedOptions(String dicCode, String dicItemCode) {
		StringBuffer sb = new StringBuffer();
		Map<String, String[]> dicItemMap = DictionaryUtil.getInstance()
				.getDicItemsByDicCode(dicCode);
		if (dicItemMap == null) {
			return "";
		}
		Set<Map.Entry<String, String[]>> entrys = dicItemMap.entrySet();
		for (Map.Entry<String, String[]> entry : entrys) {
			sb.append("<option value=");
			sb.append("'" + entry.getKey() + "'");
			if (entry.getKey().equalsIgnoreCase(dicItemCode)) {
				sb.append(" selected");
			}
			sb.append(">");
			sb.append(entry.getValue()[0]);
			sb.append("</option>");
		}
		return sb.toString();
	}

	/**
	 * 获得下拉列表选项（选中）
	 *
	 * @param dicCode 字典代码
	 * @param dicItemCode
	 *            字典项代码
	 * @param defaultDicItemCode
	 *            字典项代码默认值
	 * @return
	 */
	private String getSelectedOptions(String dicCode, String dicItemCode,
											String defaultDicItemCode) {
		if (org.apache.commons.lang3.StringUtils.isBlank(dicItemCode)) {
			dicItemCode = defaultDicItemCode;
		}
		return getSelectedOptions(dicCode, dicItemCode);
	}
}