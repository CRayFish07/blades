package com.iusofts.blades.sys.common.propcfg;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.iusofts.blades.sys.common.util.StringUtil;

public class SpringPropertyConfigurer extends PropertyPlaceholderConfigurer {

	static Properties properties = new Properties();

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		super.processProperties(beanFactory, props);
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			properties.put(keyStr, value);
		}
	}

	public static String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}

	public static String[] getProperties(String propertyName) {
		return StringUtil.split(getProperty( propertyName), ",");
				
	}
}