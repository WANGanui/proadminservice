package com.hrg.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 用于读取Properties的工具类
 * Created by 82705 on 2017/5/27.
 */
public class PropertiesUtil {

	private Logger logger = Logger.getLogger(PropertiesUtil.class);
	/**
	 * 适用于jar包的运行环境
	 */
	public static String JARCLASSPATH = "JARCLASSPATH";
	/**
	 * 适用于web的运行环境
	 */
	public static String WEBCLASSPATH = "WEBCLASSPATH";
	/**
	 * 自定义绝对路劲
	 */
	public static String CUSTOM = "CUSTOM";

	// Properties 文件对象
	private Properties prop = new Properties();

	/**
	 * 初始化Properties对象
	 * @param filePath 文件的路劲
	 * @param readType 读取文件路劲的类型
	 * @throws IOException
	 */
	public PropertiesUtil(String filePath, String readType) throws IOException {
		if (JARCLASSPATH.equals(readType)) {
			readTypeForJAR(filePath);
		}else if(WEBCLASSPATH.equals(readType)){
			readTypeForWEB(filePath);
		}else if(CUSTOM.equals(readType)){
			readTypeForCUSTOM(filePath);
		}
	}

	/**
	 * 已jar包方式运行加载文件路劲
	 * @param filePath 读取文件的路劲
	 * @throws IOException
	 */
	private void readTypeForJAR(String filePath) throws IOException {
		logger.info("读取Properties文件，路劲为"+filePath+"类型为JAR");
		try {
			InputStream in = Object.class.getResourceAsStream(filePath);
			prop.load(in);
		} catch (Exception e) {
			System.out.println("文件读取错误");
			e.printStackTrace();
		}
	}
	
	/**
	 * 已web包方式运行加载文件路劲
	 * @param filePath 读取文件的路劲
	 * @throws IOException
	 */
	private void readTypeForWEB(String filePath)throws IOException {
//		String projectRoot=System.getProperty("user.dir");//项目跟路径
//		filePath = projectRoot+filePath;
		logger.info("读取Properties文件，路劲为"+filePath+"类型为WEB");
//		File file = new File(filePath);
//		if(!file.exists()){
//			throw new IOException("路劲："+filePath+"不存在！");
//		}
		InputStream inputStream = getClass().getResourceAsStream(filePath);
//		InputStream in=  new FileInputStream(file);
		prop.load(inputStream);
	}
	/**
	 * 已自定义包方式运行加载文件路劲
	 * @param filePath
	 * @throws IOException
	 */
	private void readTypeForCUSTOM(String filePath)throws IOException {
		logger.info("读取Properties文件，路劲为"+filePath+"类型为CUSTOM");
		File file = new File(filePath);
		if(!file.exists()){
			throw new IOException("路劲："+filePath+"不存在！");
		}
		InputStream in=  new FileInputStream(file);
		prop.load(in);
	}
	/**
	 * 通过key获取值
	 * @param key 键
	 * @return
	 */
	public String getValue(String key) {
		return prop.getProperty(key);
	}
	
	public static String getValue(String filePath, String key, String readType){
		try {
			return new PropertiesUtil(filePath, readType).getValue(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
