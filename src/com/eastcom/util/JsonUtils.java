package com.eastcom.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class JsonUtils {

	public final static ObjectMapper jsonMapper = new ObjectMapper();

	/**
	 * Json格式转换 - 对象转换Json
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public static String object2Json(Object obj) {
		try {
			return jsonMapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Json格式转换 - Json转换对象
	 * 
	 * 注：如果转换格式是List类型，则list中每个元素为LinkedHashMap
	 * 
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public static <T> T json2Object(String json, Class<T> clazz) {
		try {
			return jsonMapper.readValue(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
