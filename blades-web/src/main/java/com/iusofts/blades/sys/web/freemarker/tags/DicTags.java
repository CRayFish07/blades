/*
 * Copyright (C) 2009-2017 Ivan All rights reserved
 * Author: Ivan Shen
 * Date: 2017/5/7
 * Description:DateTags.java
 */
package com.iusofts.blades.sys.web.freemarker.tags;

import com.iusofts.blades.sys.web.freemarker.tags.date.ConvertStrDateDirective;
import com.iusofts.blades.sys.web.freemarker.tags.dictionary.GetNameDirective;
import com.iusofts.blades.sys.web.freemarker.tags.dictionary.GetOptionsDirective;
import freemarker.template.SimpleHash;

/**
 * 字典标签
 *
 * @author Ivan Shen
 */
public class DicTags extends SimpleHash {

    public DicTags() {
        this.put("getName", new GetNameDirective());
        this.put("getOptions", new GetOptionsDirective());
    }
    
}
