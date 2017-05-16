package com.iusofts.blades.sys.web.permission;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.iusofts.blades.sys.model.Resource;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iusofts.blades.sys.common.util.PasswordUtil;
import com.iusofts.blades.sys.common.util.StringUtil;

/**
 * 注解提取转换
 * @author：Ivan
 * @date： 2015年1月10日 下午17:13:32
 */
public class ParseAnnotation {

	/**
	 * 根据类反射提取模块及权限信息
	 * @param clazz
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public List<Resource> parseMethod(@SuppressWarnings("rawtypes") Class clazz) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			SecurityException, NoSuchMethodException, InstantiationException, InterruptedException, ExecutionException {
		List<Resource> list=new ArrayList<Resource>();
		
		@SuppressWarnings({ "unchecked", "unused" })
		Object obj = clazz.getConstructor(new Class[] {}).newInstance(
				new Object[] {});

		//1.创建模块
		Resource resource=new Resource();
		resource.setAlias(clazz.getName());
		resource.setIsParent(1);
		resource.setEnabled(1);
		resource.setType(2);
		resource.setIsCheck(0);
		
		//2.根据注解获取模块名称
		@SuppressWarnings("unchecked")
		Permission p = (Permission) clazz.getAnnotation(Permission.class);
		if (p != null) {
			resource.setName(p.value());
			resource.setIsCheck(p.check()?1:0);
			//System.out.println("模块名称：" + p.value());
		}else{
			return list;
		}
		
		@SuppressWarnings("unchecked")
		RequestMapping rm = (RequestMapping) clazz
				.getAnnotation(RequestMapping.class);

		if (rm != null && rm.value() != null && rm.value().length > 0) {
			//3.根据注解获取URI
			resource.setUrl(rm.value()[0]);
			resource.setId(PasswordUtil.md5Hex(resource.getUrl()));
			//System.out.println("模块路径：" + rm.value()[0]);
			list.add(resource);
			
			//4.多线程获取模块的子权限
			ExecutorService threadPool = Executors.newFixedThreadPool(7);
			CompletionService<Resource> pool = new ExecutorCompletionService<Resource>(threadPool);
			 
			int count=0;//方法总数
			for (Method method : clazz.getDeclaredMethods()) {
				pool.submit(new ResourceTask(method,resource));
				count++;
			}
			threadPool.shutdown();
			
			for(int i = 0; i < count; i++){
				Resource m = pool.take().get();
				if(m!=null)list.add(m);
			}
		}
		return list;
	}
	
	/**
	 * 获取模块下权限
	 * 
	 * @author：Ivan
	 * @date： 2016年3月8日 下午5:38:12
	 */
	private final class ResourceTask implements Callable<Resource>{
		private Method method;
		private Resource parent;
		public ResourceTask(Method method,Resource parent){
			this.method=method;
			this.parent=parent;
		}
	   public Resource call(){
		   Resource mp=new Resource();
			mp.setAlias(method.getName());
			mp.setIsParent(0);
			mp.setEnabled(1);
			mp.setPid(parent.getId());
			mp.setType(3);
			mp.setIsCheck(0);
			
			Permission say = method.getAnnotation(Permission.class);
			RequestMapping rMapping = method
					.getAnnotation(RequestMapping.class);
			
			String value = "";
			if (say != null) {
				value = say.value();
				// method.invoke(obj, value);
				mp.setName(value);
				mp.setIsCheck(say.check()?1:0);
				//System.out.println("权限名称:" + value);
			}
			if (rMapping != null) {
				String uri;
				if (rMapping.value()[0].indexOf("/") != 0) {
					uri = parent.getUrl() + "/" + rMapping.value()[0];
				} else {
					uri = parent.getUrl() + rMapping.value()[0];
				}
				
				mp.setUrl(StringUtil.allInOne(uri, '/'));
				
				if(rMapping.method()!=null&&rMapping.method().length>0){
					mp.setMethod(rMapping.method()[0].toString());
					mp.setId(PasswordUtil.md5Hex(mp.getUrl()+mp.getMethod()));
				}else{
					mp.setId(PasswordUtil.md5Hex(mp.getUrl()));
				}
				//System.out.println("访问路径：" + StringUtil.allInOne(uri, '/'));
				return mp;
			}
	      return null;
	   }
	}
}