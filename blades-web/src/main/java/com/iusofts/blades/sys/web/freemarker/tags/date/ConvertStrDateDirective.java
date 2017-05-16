/*
 * Copyright (C) 2009-2017 Ivan All rights reserved
 * Author: Ivan Shen
 * Date: 2017/5/7
 * Description:ConvertStrDateDirective.java
 */
package com.iusofts.blades.sys.web.freemarker.tags.date;

import com.iusofts.blades.sys.web.util.ConvertUtil;
import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 时间格式转换
 *
 * @author Ivan Shen
 */
public class ConvertStrDateDirective implements TemplateDirectiveModel {
    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        Writer out = environment.getOut();
        TemplateScalarModel dateStrModel = (TemplateScalarModel) map.get("dateStr");
        TemplateScalarModel maskModel = (TemplateScalarModel) map.get("mask");
        String result = "";
        String dateStr = dateStrModel==null?"":dateStrModel.getAsString();
        String mask = maskModel==null?"":maskModel.getAsString();

        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(mask)) {
            result = "";
        }else{
            if(dateStr.length()==12){
                dateStr=dateStr+"00";
            }
            Date date = ConvertUtil.getDate(dateStr);
            if (date == null) {
                result = dateStr;
            }else{
                String str = new SimpleDateFormat(mask).format(date);
                result = StringUtils.isNotBlank(str) ? str : dateStr;
            }
        }
        out.write(result);
    }
}
