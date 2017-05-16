package com.iusofts.blades.sys.web.permission;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.iusofts.blades.sys.common.util.PasswordUtil;
import com.iusofts.blades.sys.model.Resource;
import com.iusofts.blades.sys.web.permission.conf.PermissionConfig;


/**
 * 权限整合汇总
 * @author：Ivan
 * @date： 2015年1月11日 下午15:12:42
 */
public class PermissionCluster {
	
	private static String path="";
	
	/**
	 * 从包package中获取所有的Class
	 * 
	 * @param pack
	 * @return
	 */
	public static Set<Class<?>> getClasses(String pack) {

		// 第一个class类的集合
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		// 是否循环迭代
		boolean recursive = true;
		// 获取包的名字 并进行替换
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader()
					.getResources(packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					System.err.println("file类型的扫描");
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					PermissionCluster.setPath(filePath);
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					// 定义一个JarFile
					System.err.println("jar类型的扫描");
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection())
								.getJarFile();
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx)
											.replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(".class")
											&& !entry.isDirectory()) {
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(
												packageName.length() + 1,
												name.length() - 6);
										try {
											// 添加到classes
											classes.add(Class
													.forName(packageName + '.'
															+ className));
										} catch (ClassNotFoundException e) {
											// log
											// .error("添加用户自定义视图类错误 找不到此类的.class文件");
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						// log.error("在扫描用户定义视图时从jar包获取文件出错");
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, Set<Class<?>> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(
						packageName + "." + file.getName(),
						file.getAbsolutePath(), recursive, classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					// 添加到集合中去
					// classes.add(Class.forName(packageName + '.' +
					// className));
					// 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
					classes.add(Thread.currentThread().getContextClassLoader()
							.loadClass(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					// log.error("添加用户自定义视图类错误 找不到此类的.class文件");
					e.printStackTrace();
				}
			}
		}
	}

	public static List<Resource> getResourceList() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			InstantiationException, IllegalArgumentException, SecurityException, InterruptedException, ExecutionException {
		
		long start=System.currentTimeMillis();
		
		//1.收集控制器Class文件
		Set<Class<?>> allClass = PermissionCluster.getClasses(PermissionConfig.PROJECT_PACKAGE_NAME);
		Set<Class<?>> controllerClass = new HashSet<Class<?>>();
		for (Class<?> class1 : allClass) {
			if (class1.getName().contains("."+PermissionConfig.CONTROLLER_PACKAGE_NAME)) {
				System.out.println(class1.getName());
				controllerClass.add(class1);
			}
		}

		System.out.println(controllerClass.size());

		//2.解析控制器注解
		ParseAnnotation parse = new ParseAnnotation();
		List<Resource> list = new ArrayList<Resource>();
		for (Class<?> class1 : controllerClass) {
			list.addAll(parse.parseMethod(class1));
		}
		
		//3.创建根结点（系统）
		// System.out.println("path:"+path);
		String parentPackage = PermissionConfig.PROJECT_PACKAGE_NAME;
		Resource fristResource = new Resource();
		fristResource.setName(PermissionConfig.PROJECT_NAME);
		fristResource.setId(PasswordUtil.md5Hex(PermissionConfig.PROJECT_PACKAGE_NAME));
		fristResource.setIsParent(1);
		fristResource.setAlias(parentPackage);
		fristResource.setEnabled(1);
		fristResource.setType(0);
		fristResource.setIsCheck(1);
		list.add(fristResource);

		//4.收集第二级节点（模块）
		List<Resource> mlist = new ArrayList<Resource>();
		for (int i = 0; i < list.size(); i++) {
			Resource module = list.get(i);
			if (module.getType() == 2) {
				// System.out.println(module.getName()+"("+module.getAlias()+"):"+module.getUrl());
				mlist.add(module);
			}
		}
		File file = new File(path);
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isDirectory()) {
				String pname = (parentPackage + "." + tempList[i].getName());
				boolean contains = false;
				for (int j = 0; j < mlist.size(); j++) {
					if (mlist.get(j).getAlias().indexOf(pname + ".") == 0) {
						contains = true;
						break;
					}
				}
				if (contains) {
					//5.创建一级节点（子系统）
					// System.out.println(pname);
					Resource secondResource = new Resource();
					secondResource.setId(PasswordUtil.md5Hex(pname));
					secondResource.setIsParent(1);
					secondResource.setAlias(pname);
					secondResource.setEnabled(1);
					secondResource.setType(1);
					secondResource.setPid(fristResource.getId());
					secondResource.setIsCheck(1);
					secondResource.setName(PermissionConfig.DEFAULT_PACKAGE_NAME.get(pname));
					list.add(secondResource);
					
					//6.将二级节点绑定到一级节点（模块绑定到子系统）
					for (int j = 0; j < mlist.size(); j++) {
						if (mlist.get(j).getAlias().indexOf(pname + ".") == 0) {
							mlist.get(j).setPid(secondResource.getId());
						}
					}
				}
			}
		}
		
		//排序
		Collections.sort(list, new Comparator<Resource>() {
            public int compare(Resource arg0, Resource arg1) {
                return arg0.getAlias().compareTo(arg1.getAlias());
            }
        });
		return list;
	}

	public static String getUrl() {
		return path;
	}

	public static void setPath(String path) {
		PermissionCluster.path = path;
	}

	
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalArgumentException, SecurityException, InterruptedException, ExecutionException {
		long start=System.currentTimeMillis();
		List<Resource> list=PermissionCluster.getResourceList();
		for (Resource module : list) {
			System.out.println(module.getName()+":"+module.getUrl());
		}
		long end=System.currentTimeMillis();
		System.out.println("总耗时："+(end-start)+"ms");
	}
}
