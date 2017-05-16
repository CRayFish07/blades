/*
 * Copyright (C) 2009-2017 Ivan All rights reserved
 * Author: Ivan Shen
 * Date: 2017/5/7
 * Description:DateTags.java
 */
package com.iusofts.blades.sys.web.freemarker.tags;

import com.iusofts.blades.sys.web.freemarker.tags.date.ConvertStrDateDirective;
import freemarker.template.SimpleHash;

/**
 * 日期标签
 *
 * @author Ivan Shen
 */
public class DateTags extends SimpleHash {

    public DateTags() {
        this.put("convertStrDate", new ConvertStrDateDirective());
    }
    
}
