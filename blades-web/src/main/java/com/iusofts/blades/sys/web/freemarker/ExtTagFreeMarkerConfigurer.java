/*
 * Copyright (C) 2009-2017 Ivan All rights reserved
 * Author: Ivan Shen
 * Date: 2017/5/7
 * Description:ExtTagFreeMarkerConfigurer.java
 */
package com.iusofts.blades.sys.web.freemarker;

import com.iusofts.blades.sys.web.freemarker.tags.DateTags;
import com.iusofts.blades.sys.web.freemarker.tags.DicTags;
import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

/**
 * 拓展标签
 *
 * @author Ivan Shen
 */
public class ExtTagFreeMarkerConfigurer extends FreeMarkerConfigurer {

    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        super.afterPropertiesSet();
        this.getConfiguration().setSharedVariable("shiro", new ShiroTags());
        this.getConfiguration().setSharedVariable("date", new DateTags());
        this.getConfiguration().setSharedVariable("dict", new DicTags());//dic被占用
    }

}
