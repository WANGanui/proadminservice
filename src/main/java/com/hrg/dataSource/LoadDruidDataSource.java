package com.hrg.dataSource;

import com.hrg.util.PropertiesUtil;
import org.apache.log4j.Logger;

/**
 * 用来自动加载数据源环境的类
 * Created by 82705 on 2017/5/27.
 */
public class LoadDruidDataSource extends com.alibaba.druid.pool.DruidDataSource{

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(LoadDruidDataSource.class);

	/**
	 * 当前运行环境默认开发环境
	 */
	public static String THISENVIRONMENT = "dev";

	/**
	 * 数据库的编码dabasource中配置的code需要和Properties中的对应上
	 */
	private String code;
	/**
	 * 当前环境类型 开发环境
	 */
	private static final String DEV = "dev";
	/**
	 * 当前环境类型 正式环境
	 */
	private static final String RELEASE = "release";
	/**
	 * 当前环境类型 测试环境
	 */
	private static final String TEST = "test";
	/**
	 * Properties 中定义数据库的 URL key
	 */
	private static final String URL = "url";
	/**
	 * Properties 中定义数据库的 用户名 key
	 */
	private static final String USERNAME = "username";
	/**
	 * Properties 中定义数据库的 密码 Key
	 */
	private static final String PASSWORD = "password";
	/**
	 * Properties 中定义数据库的 驱动 key
	 */
	private static final String CLASSNAME = "jdbc.driverClassName";
	/**
	 * 运行环境为  -开发环境- 读取的数据源文件
	 */
	private static final String DEVFILE = "/jdbc/dev.properties";
	/**
	 * 运行环境为  -正式环境- 读取的数据源文件
	 */
	private static final String RELEASEFILE = "/jdbc/release.properties";
	/**
	 * 运行环境为  -测试环境- 读取的数据源文件
	 */
	private static final String TESTFILE = "/jdbc/test.properties";
	
	/**
	 * 初始化数据库账号密码
	 * @throws Exception
	 */
	private void initDataSource() throws Exception {
		if(code==null){
			throw new Exception("有数据源的code为空");
		}
		String environmentName = THISENVIRONMENT;
		//默认为开发环境
		String filePath = DEVFILE.replace("dev", environmentName);
		logger.info("加载数据源-当前环境为-- 读取数据源文件为："+filePath);

		try {
			PropertiesUtil prop = new PropertiesUtil(filePath.toString(), PropertiesUtil.JARCLASSPATH);
			String url = prop.getValue(code+"."+URL);
			String userName= prop.getValue( code+"."+USERNAME);
			String passWord = prop.getValue( code+"."+PASSWORD);
			String className = prop.getValue(CLASSNAME);
			super.setUsername(userName);
			super.setPassword(passWord);
			super.setUrl(url);
			super.setDriverClassName(className);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setCode(String code) throws Exception {
		this.code = code;
		initDataSource();
	}
}
