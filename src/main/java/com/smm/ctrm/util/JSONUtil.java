package com.smm.ctrm.util;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smm.ctrm.hibernate.objectMapper.HibernateAwareObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;

/**
 * 工具转换工具类
 * 
 * @author zengshihua
 *
 */
public class JSONUtil {

	/**
	 * 转换对象为JSON字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String doConvertObject2Json(Object object) {
		ConvertUtils.register(new DateConverter(), java.util.Date.class);
		return JSONSerializer.toJSON(object).toString();
	}

	/**
	 * 转换JSON字符串为MAP对象
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map<String, Object> doConvertJson2Map(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator<String> keyIter = jsonObject.keys();
		String key = "";
		Object value = null;
		Map<String, Object> valueMap = new HashMap<String, Object>();
		while (keyIter.hasNext()) {
			key = keyIter.next();
			value = jsonObject.get(key);
			if (JSONUtils.isNull(value)) {
				value = null;
			}
			valueMap.put(key, value);
		}
		return valueMap;
	}

	public static Map<String, String> doConvertJson2Map2(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator<String> keyIter = jsonObject.keys();
		String key = "";
		String value = null;
		Map<String, String> valueMap = new HashMap<String, String>();
		while (keyIter.hasNext()) {
			key = keyIter.next();
			value = (String) jsonObject.get(key);
			if (JSONUtils.isNull(value)) {
				value = null;
			}
			valueMap.put(key, value);
		}
		return valueMap;
	}

	/**
	 * 转换JSON字符串为MAP对象
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map<String, String> doConvertJson2StrMap(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator<String> keyIter = jsonObject.keys();
		String key = "";
		Object value = null;
		Map<String, String> valueMap = new HashMap<String, String>();
		while (keyIter.hasNext()) {
			key = keyIter.next();
			value = jsonObject.get(key);
			if (JSONUtils.isNull(value)) {
				value = null;
			}
			valueMap.put(key, value.toString());
		}
		return valueMap;
	}

	/**
	 * 转换JSON字符串为MAP对象
	 * 
	 * @param jsonString
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> doConvertJson2List(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JsonConfig jsonConfig = new JsonConfig();
		List<Map<String, Object>> list = JSONArray.toList(jsonArray, new HashMap<String, Object>(), jsonConfig);
		return list;
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> doConvertJson2List2(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Collection<Map<String, Object>> collection = JSONArray.toCollection(jsonArray, Map.class);
		List<Map<String, Object>> list = (List<Map<String, Object>>) collection;
		return list;
	}

	/**
	 * 转换JSON字符串为数组对象
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Object[] doConvertJson2Array(String jsonString, Class<?> clazz) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Collection collection = JSONArray.toCollection(jsonArray, clazz);
		Object[] objects = collection.toArray();
		return objects;
	}

	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws IOException {
		/*
		 * HashMap<String, String> map = new HashMap<String, String>();
		 * map.put("id", "ne"); map.put("name", "lvjuntao"); map.put("sex",
		 * "man"); ArrayList<Object> list = new ArrayList<Object>();
		 * list.add(map); list.add(map);
		 * System.out.println(JSONUtil.doConvertObject2Json(list));
		 * 
		 * String _json=
		 * "[{\"id\":\"ne\",\"sex\":\"man\",\"\"name\":\"lvjuntao\"},{\"id\":\"ne\",\"sex\":\"man\",\"name\":\"lvjuntao\"}]";
		 * Object[] objects=JSONUtil.doConvertJson2Array(_json,Map.class);
		 * 
		 * HashMap<String, String> map2 = (HashMap<String, String>)objects[0];
		 * System.out.println(map2.get("id"));
		 */
		String _json = "[{\"id\" : null,\"sex\":\"man\",\"name\":\"lvjuntao\"},{\"id\":\"ne\",\"sex\":\"man\",\"name\":\"lvjuntao\"}]";
		List<Map<String, Object>> list = JSONUtil.doConvertJson2List(_json);

		List<String> list2 = new ArrayList<>();
		list2.add("123");
		list2.add("124");
		list2.add("125");
		list2.add("126");
		ObjectMapper mapper = new ObjectMapper();
		try {

			String json = mapper.writeValueAsString(list2);

			List<String> s = mapper.readValue(FileUtil.read(), List.class);
			System.out.println(s.toString());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 从一个JSON 对象字符格式中得到一个java对象
	 * 
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static Object getObject4JsonString(String json, Class<?> clazz) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		return JSONObject.toBean(jsonObject, clazz);
	}

	/**
	 * 从json HASH表达式中获取一个map，改map支持嵌套功能
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map<String, Object> getMap4Json(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator<String> keyIter = jsonObject.keys();
		String key;
		Object value;
		Map<String, Object> valueMap = new HashMap<String, Object>();

		while (keyIter.hasNext()) {
			key = keyIter.next();
			value = jsonObject.get(key);
			if (JSONUtils.isNull(value)) {
				value = null;
			}
			valueMap.put(key, value);
		}

		return valueMap;
	}

	/**
	 * 从json数组中得到相应java数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Object[] getObjectArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();

	}

	/**
	 * 从json对象集合表达式中得到一个java对象列表
	 * 
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static List<Object> getList4Json(String jsonString, Class pojoClass) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;

		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < jsonArray.size(); i++) {

			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);

		}
		return list;

	}

	/**
	 * 从json数组中解析出java字符串数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static String[] getStringArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);

		}

		return stringArray;
	}

	/**
	 * 从json数组中解析出javaLong型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Long[] getLongArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Long[] longArray = new Long[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			longArray[i] = jsonArray.getLong(i);

		}
		return longArray;
	}

	/**
	 * 从json数组中解析出java Integer型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Integer[] getIntegerArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Integer[] integerArray = new Integer[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			integerArray[i] = jsonArray.getInt(i);

		}
		return integerArray;
	}

	/**
	 * 从json数组中解析出java Date 型对象数组，使用本方法必须保证
	 * 
	 * @param jsonString
	 * @return
	 * @throws ParseException
	 */
	public static Date[] getDateArray4Json(String jsonString, String DataFormat) throws ParseException {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Date[] dateArray = new Date[jsonArray.size()];
		String dateString;
		Date date;

		for (int i = 0; i < jsonArray.size(); i++) {
			dateString = jsonArray.getString(i);
			// date = DateUtil.parseDate(dateString, DataFormat);
			// dateArray[i] = date;

		}
		return dateArray;
	}

	/**
	 * 从json数组中解析出java Integer型对象数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Double[] getDoubleArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Double[] doubleArray = new Double[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			doubleArray[i] = jsonArray.getDouble(i);

		}
		return doubleArray;
	}

	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param javaObj
	 * @return
	 */
	public static String getJsonString4JavaPOJO(Object javaObj) {

		JSONObject json;
		json = JSONObject.fromObject(javaObj);
		return json.toString();

	}

	/**
	 * 将java对象转换成json字符串,并设定日期格式
	 * 
	 * @param javaObj
	 * @param dataFormat
	 * @return
	 */
	public static String getJsonString4JavaPOJO(Object javaObj, String dataFormat) {

		JSONObject json;
		JsonConfig jsonConfig = configJson(dataFormat);
		json = JSONObject.fromObject(javaObj, jsonConfig);
		return json.toString();

	}

	/**
	 * JSON 时间解析器具
	 * 
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "" });
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		// jsonConfig.registerJsonValueProcessor(Date.class,
		// new JsonDateValueProcessor(datePattern));

		return jsonConfig;
	}

	/**
	 * 除去不想生成的字段（特别适合去掉级联的对象）+时间转换
	 * 
	 * @param excludes
	 *            除去不想生成的字段
	 * @param datePattern
	 * @return
	 */
	public static JsonConfig configJson(String[] excludes, String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		jsonConfig.setIgnoreDefaultExcludes(true);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		// jsonConfig.registerJsonValueProcessor(Date.class,
		// new JsonDateValueProcessor(datePattern));

		return jsonConfig;
	}

	/**
	 * 将List<String>转换为List<Map<String,String>>
	 * 
	 * @param list<String>
	 * @return list<Map<String,String>>
	 */
	public static List<Map<String, String>> doConvertJson2Map4List(List<String> list) {
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < list.size(); i++) {
			resultList.add(doConvertJson2StrMap(list.get(i)));
		}
		return resultList;
	}

	/**
	 * Java实体转json字符串 去除级联对象
	 */
	public static String doConvertBeanToJson(Object javaObj, String[] excludes) {
		JsonConfig jsonConfig = new JsonConfig(); // 建立配置文件
		jsonConfig.setExcludes(excludes);
		jsonConfig.setIgnoreDefaultExcludes(true); // 设置默认忽略
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONArray json;
		json = JSONArray.fromObject(javaObj, jsonConfig);
		return json.toString();
	}

	// *****************************
	// 以下是JackJson转换方法
	// *****************************

	/**
	 * bean to String
	 * 
	 * @param javaObj
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String doConvertBeanToString(Object javaObj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(javaObj);
		return json;
	}

	/**
	 * String to bean
	 * 
	 * @param javaObj
	 * @return
	 * @throws IOException
	 */
	public static Object doConvertStringToBean(String jsonStr, Class<?> clazz) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Object object = mapper.readValue(jsonStr, clazz);
		return object;
		
	}
	
	/**
	 * String to bean
	 * 
	 * @param javaObj
	 * @return
	 * @throws IOException 
	 */
	public static <T> T doConvertStringToT(String jsonStr,Class<T> clazz) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		T object = mapper.readValue(jsonStr, clazz);
		return object;
	}
	
	/**
	 * Obj to String
	 * 
	 * @param Obj
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String doConvertObjToString(Object obj) throws JsonProcessingException {
		HibernateAwareObjectMapper mapper = new HibernateAwareObjectMapper();
		String json = mapper.writeValueAsString(obj);
		return json;
	}

	/**
	 * String to List
	 * 
	 * @param Obj
	 * @return
	 * @throws JsonProcessingException
	 */
	@SuppressWarnings("unchecked")
	public static List<String> doConvertStringToList(String val) {
		List<String> s = new ArrayList<>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			s = mapper.readValue(val, List.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
}