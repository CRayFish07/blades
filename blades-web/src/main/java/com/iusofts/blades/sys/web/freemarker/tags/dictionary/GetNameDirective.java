package com.iusofts.blades.sys.web.freemarker.tags.dictionary;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.iusofts.blades.sys.common.util.StringUtil;
import com.iusofts.blades.sys.web.util.DictionaryUtil;
import org.apache.commons.lang.StringUtils;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 数据字典根据key显示value
 * 
 * @author Ivan
 */
public class GetNameDirective implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		SimpleScalar paramValue = (SimpleScalar) params.get("value");
		SimpleScalar type = (SimpleScalar) params.get("type");
		String value = "";
		if (paramValue != null) {
			value = paramValue.getAsString();
		}
		Writer out = env.getOut();
		String result = null;
		if (StringUtil.isNotBlank(value)) {
			result = getDicItemName(type.getAsString(), value);
		}
		if (StringUtils.isEmpty(result))
			result = "";
		out.write(result);
	}

	/**
	 * 获得字典项名称
	 *
	 * @param dicCode
	 *            字典代码
	 * @param dicItemCode
	 *            字典项代码
	 * @return 字典项名称
	 */
	private String getDicItemName(String dicCode, String dicItemCode) {
		Map<String, String[]> dicItemMap = DictionaryUtil.getInstance().getDicItemsByDicCode(dicCode);
		if (dicItemMap == null) {
			return "";
		}
		String s[] = dicItemMap.get(dicItemCode);
		if (s == null || s.length == 0) { // 如果没有找到，则原值返回
			return dicItemCode;
		}
		return s[0];
	}

	/**
	 * 获得字典项名称,多个以逗号分隔
	 *
	 * @param dicCode
	 *            字典代码
	 * @param dicItemCodes
	 *            以逗号分隔的字典项代码集合串
	 * @return 字典名称集合串
	 */
	private String getDicItemNames(String dicCode, String dicItemCodes) {
		StringBuffer sb = new StringBuffer();
		Map<String, String[]> dicItemMap = DictionaryUtil.getInstance().getDicItemsByDicCode(dicCode);
		if (dicItemMap == null || org.apache.commons.lang3.StringUtils.isBlank(dicItemCodes)) {
			return "";
		}
		String itemCodes[] = dicItemCodes.split(",");
		int len = itemCodes.length;
		for (int i = 0; i < len; i++) {
			sb.append(getDicItemName(dicCode, itemCodes[i]));
			if (i != len - 1) {
				sb.append(";");
			}
		}
		return sb.toString();
	}

}