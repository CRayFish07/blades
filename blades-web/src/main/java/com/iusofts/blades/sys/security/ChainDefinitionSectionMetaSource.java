package com.iusofts.blades.sys.security;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import com.iusofts.blades.sys.model.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.iusofts.blades.sys.common.util.StringUtil;
import com.iusofts.blades.sys.service.ResourceService;

public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section>{

	@Autowired
    private ResourceService resourceDao;

    private String filterChainDefinitions;

    /**
     * 默认premission字符串
     */
    public static final String PREMISSION_STRING="perms[\"{0}\"]";

    public Section getObject() throws BeansException {
    	
    	   //                        //
		  //    在此动态映射URL和权限         //
		 //                        //

        //获取所有Resource
    	Resource res=new Resource();
    	res.setEnabled(1);
        List<Resource> list = resourceDao.getList(new Resource());

        Ini ini = new Ini();
        //加载默认的url
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        //循环Resource的url,逐个添加到section中。section就是filterChainDefinitionMap,
        //里面的键就是链接URL,值就是存在什么条件才能访问该链接
        for (Iterator<Resource> it = list.iterator(); it.hasNext();) {

            Resource resource = it.next();
            //如果不为空值添加到section中
            if(StringUtils.isNotEmpty(resource.getUrl())) {
                section.put(cleanUrl(resource.getUrl()),  MessageFormat.format(PREMISSION_STRING,resource.getUrl()));
                section.put(cleanUrl(resource.getUrl())+".*",  MessageFormat.format(PREMISSION_STRING,resource.getUrl()));
            }

        }

        return section;
    }
    
    /**
     * 替换PathVariable
     * 如：/{code}/login 替换为 /'*'/login
     * @param template
     * @return
     * @author：Ivan
     * @date：2016年3月12日 下午2:32:09
     */
    public String cleanUrl(String template){
    		if(StringUtil.isBlank(template)||template.indexOf("{")==-1){
    			return template;
    		}
    	    String template2=template;
    	    StringBuffer sb=new StringBuffer();
    	    boolean isReplace=false;
    	    for (char c : template.toCharArray()) {
    	    	if(c=='}'){
    	    		template2=template2.replace("{"+sb.toString()+"}", "*");
    	    		sb.setLength(0);
    	    		isReplace=false;
    	    	}
    	    	if(isReplace){
    	    		sb.append(c);
    	    	}
    	    	if(c=='{'){
    	    		isReplace=true;
    	    	}
    		}
    	    return template2;
    }

    /**
     * 通过filterChainDefinitions对默认的url过滤定义
     * 
     * @param filterChainDefinitions 默认的url过滤定义
     */
    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    public Class<?> getObjectType() {
        return this.getClass();
    }

    public boolean isSingleton() {
        return false;
    }

}

