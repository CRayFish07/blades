package com.iusofts.blades.sys.web.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Xml操作工具类
 * @author：Ivan
 * @date： 2015年9月2日 上午10:44:50
 */
public class Dom4jUtil {
	
	public static void main(String[] args) throws Exception {
		//System.out.println(getStringByName(getChildById("bladesDefaultPackageName"),"com.iusoft.sys"));
		System.out.println(getMapById("/config/blades-config.xml","bladesDefaultConfig").get("showNotCheckUri"));
    }
	
	/**
	 * 根据ID获取子节点
	 * @author：Ivan
	 * @date：2015年9月2日 上午10:55:21
	 * @param path
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Iterator getChildById(String path,String id){
		SAXReader reader = new SAXReader();
		Document document=null;
		try {
			document = reader.read(Dom4jUtil.class.getResourceAsStream(path));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// 获取根元素
		Element root = document.getRootElement();
        System.out.println("Root: " + root.getName());
		for (Iterator iter = root.elementIterator(); iter.hasNext();)
        {
            Element e = (Element) iter.next();
            if(id.equals(e.attributeValue("id"))){
            	return e.elementIterator()!=null?e.elementIterator():null;
            }
        }
		return null;
	}
	/**
	 * 根据名称获取值
	 * @author：Ivan
	 * @date：2015年9月2日 上午10:56:15
	 * @param iter
	 * @param name
	 * @return
	 */
	public static String getStringByName(@SuppressWarnings("rawtypes") Iterator iter,String name){
		while (iter.hasNext())
        {
            Element e = (Element) iter.next();
            if(name.equals(e.attributeValue("name"))||name.equals(e.element("name").getText())){
            	return e.attributeValue("name")!=null?e.attributeValue("value"):e.element("value").getText();
            }
        }
		return null;
	}
	
	/**
	 * 根据ID获取属性Map
	 * @author：Ivan
	 * @date：2015年9月2日 上午10:56:54
	 * @param path
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String,String> getMapById(String path,String id){
		Map<String,String> map=new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		Document document=null;
		try {
			document = reader.read(Dom4jUtil.class.getResourceAsStream(path));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// 获取根元素
		Element root = document.getRootElement();
		for (Iterator iter = root.elementIterator(); iter.hasNext();)
        {
            Element e = (Element) iter.next();
            if(id.equals(e.attributeValue("id"))){
            	for (Iterator iter2 = e.elementIterator(); iter2.hasNext();){
                	Element e2 = (Element) iter2.next();
                	if(e2.attributeValue("name")==null){
                		map.put(e2.element("name").getText(), e2.element("value").getText());
                	}else{
                		map.put(e2.attributeValue("name"), e2.attributeValue("value"));
                	}
                }
            }
        }
		return map;
	}

}
