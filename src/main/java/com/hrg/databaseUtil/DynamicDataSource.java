package com.hrg.databaseUtil;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * @创建人： caoyu
 * @创建时间：2016年4月9日下午9:37:24
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
//
//	public DynamicDataSource() {
//		setTargetDataSources(new HashMap<>());
//	}
//	
//	/**
//	 * 当前执行impl中方法的方法名
//	 */
//	public static ThreadLocal<String> methodName = new ThreadLocal<String>();
//
//	/**
//	 * 是否启用读写分离 true启用 false不启用 注意：不启用时读和写都是用的写的数据源
//	 */
//	private boolean readWriteSplitting = false;
//	
//	/**
//	 * 代码中需要调用读取数据源的方法名开头
//	 */
//	private static List<String> readMethodName;
//	/**
//	 * 代码中需要调用写入数据源 的方法名开头
//	 */
//	private static List<String> writeMethodName;
//	
//	
//	/**
//	 * 写的数据源
//	 */
//	private Map<Object, Object> writeDataSourceMap;
//	/**
//	 * 读的数据源
//	 */
//	private Map<Object, Object> readDataSourceMap;	
//	
//	/**
//	 * 用于写操作数据源的名字
//	 */
//	private static List<Object> writeSourceName;
//	/**
//	 * 用于读操作数据源的名字
//	 */
//	private static List<Object> readSourceName;
//	
//	
//	/**
//	 * 初始化数据
//	 * @创建人： caoyu
//	 * @创建时间：2016年4月9日下午9:37:40
//	 */
//	@SuppressWarnings("unused")
//	private void init() throws Exception{
//		Map<Object, Object> map = new HashMap<>();
//		if(writeDataSourceMap!=null){
//			writeSourceName = new ArrayList<>();
//			writeSourceName.addAll(writeDataSourceMap.keySet());
//			map.putAll(writeDataSourceMap);
//		}else{
//			throw new Exception("writeDataSourceMap 不能为空");
//		}
//		if(readWriteSplitting){
//			if(readDataSourceMap!=null){
//				readSourceName = new ArrayList<>();
//				readSourceName.addAll(readDataSourceMap.keySet());
//				map.putAll(readDataSourceMap);
//			}else{
//				throw new Exception("readDataSourceMap 不能为空");
//			}
//		}else{
//			readSourceName = new ArrayList<>();
//			readSourceName.addAll(writeDataSourceMap.keySet());
//		}
//		
//		setTargetDataSources(map);
//	}
//	
	@Override
	protected Object determineCurrentLookupKey() {
		return null;
//		String name = methodName.get();
//		if(name==null){
//			try {
//				throw new Exception("数据源选择错误--已选用默认数据源");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return null;
//			}
//		}
//		return getSourceName(name);
	}
	
//	
//	/**
//	 * 获取数据源名称
//	 * @创建人： caoyu
//	 * @创建时间：2016年4月9日下午9:37:51
//	 */
//	private Object getSourceName(String name){
//		
//		String method = name;
//		Object dataSourceName = null;
//		if(method==null){
//			return null;
//		}
//		if(!readWriteSplitting || isWrite(method)){
//			dataSourceName =  writeSourceName.get(getwriteSourceNumber());//写的数据源
//		}else if(isRead(method)){
//			dataSourceName = readSourceName.get(getreadSourceNumber());//读的数据源
//		}
//		logger.info("选用数据源名称为："+dataSourceName);
//		
//		return dataSourceName;
//	}
//	/**
//	 * 判断是不是读操作
//	 * @创建人： caoyu
//	 * @创建时间：2016年4月9日下午9:37:58
//	 */
//	private boolean isRead(String methodName){
//		for(String str:readMethodName){
//			if(methodName.startsWith(str)){
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	/**
//	 * 判断是不是写操作
//	 * @创建人： caoyu
//	 * @创建时间：2016年4月9日下午9:38:05
//	 *
//	 */
//	private boolean isWrite(String methodName){
//		for(String str : writeMethodName){
//			if(methodName.startsWith(str)){
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	//获取用于  -写-   操作数据库的数据源
//	private static AtomicInteger writeSourceCount = new AtomicInteger(-1);
//	private int getwriteSourceNumber(){
//		if(writeSourceName.size()==1){
//			return 0;
//		}
//		if(writeSourceCount.get()>=(writeSourceName.size())){
//			emptyWriteCount();
//		}
//		return writeSourceCount.incrementAndGet();
//	}
//	private synchronized static void emptyWriteCount(){
//		if(writeSourceCount.get()>=(writeSourceName.size())){
//			writeSourceCount.set(-1);
//		}
//	}
//	
//	
//	
//	//获取用于  -读-   操作数据库的数据源
//	private static AtomicInteger readSourceCount = new AtomicInteger(-1);
//	private int getreadSourceNumber(){
//		if(readSourceName.size()==1){
//			return 0;
//		}
//		if(readSourceCount.get()>=(readSourceName.size())){
//			emptyReadCount();
//		}
//		return readSourceCount.incrementAndGet();
//	}
//	private synchronized static void emptyReadCount(){
//		if(readSourceCount.get()>=(readSourceName.size())){
//			readSourceCount.set(-1);
//		}
//	}
//	
//	
//	
//	
//	
//	public void setWriteDataSourceMap(Map<Object, Object> writeDataSourceMap) {
//		this.writeDataSourceMap = writeDataSourceMap;
//	}
//	public void setReadDataSourceMap(Map<Object, Object> readDataSourceMap) {
//		this.readDataSourceMap = readDataSourceMap;
//	}
//	public void setWriteSourceName(List<Object> writeSourceName) {
//		DynamicDataSource.writeSourceName = writeSourceName;
//	}
//	public void setReadSourceName(List<Object> readSourceName) {
//		DynamicDataSource.readSourceName = readSourceName;
//	}
//	public static void setReadMethodName(List<String> readMethodName) {
//		DynamicDataSource.readMethodName = readMethodName;
//	}
//	public static void setWriteMethodName(List<String> writeMethodName) {
//		DynamicDataSource.writeMethodName = writeMethodName;
//	}
//	public void setReadWriteSplitting(boolean readWriteSplitting) {
//		this.readWriteSplitting = readWriteSplitting;
//	}
}
