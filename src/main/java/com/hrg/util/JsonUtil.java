package com.hrg.util;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * 类说明：JSON工具类
 */
public class JsonUtil {

	private static ObjectMapper mapper; 
	
	static {
		mapper = new ObjectMapper();
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	}
	
	/**
	 * 
	 * 方法说明：将一个对象obj转换成json字符串
	 * @return
	 */
	public synchronized static String encode(Object obj) {
		String result = null;
		try {
			result = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			System.out.println("Obj对象创建Json字符串异常 : " + e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * 方法说明：将一个json字符串转换成制定类型
	 * @param jsonString
	 * @param clazz
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public synchronized static Object decode(String jsonString, Class<?> clazz) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(jsonString, clazz);
	}
}