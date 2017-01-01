package org.ayo.lang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class JsonUtils {

	private JsonUtils() {
	}

	/**
	 * @param jsonArrayString
	 * @param cls
	 * @return
	 * @throws JSONException
	 */
	public static <T> List<T> getBeanList(String jsonArrayString, Class<T> cls) {

		List<T> beanList = new ArrayList<T>();
		beanList = JSON.parseArray(jsonArrayString, cls);
		return beanList;
	}

	public static <T> T getBean(String jsonString, Class<T> cls) {
		T t = null;
		t = JSON.parseObject(jsonString, cls);
		return t;
	}

	/**
	 * 返回一层Map，即不会嵌套解析json，对于:
	 * {code：1，
	 *  msg: "未登录",
	 *  result:{time: "1123"}
	 * }
	 * 会返回Map：
	 * code==>1
	 * msg==>未登录
	 * result==>{time: "1123"}
	 * 即Object可能是int，String，或org.json.JSONObject, org.json.JSONArray
	 * @param jsonStr
	 * @return
     */
	public static Map<String, Object> getMap(String jsonStr){
		org.json.JSONObject jsonObject ;
		try {
			jsonObject = new org.json.JSONObject(jsonStr);

			Iterator<String> keyIter= jsonObject.keys();
			String key;
			Object value ;
			Map<String, Object> valueMap = new HashMap<String, Object>();
			while (keyIter.hasNext()) {
				key = keyIter.next();
				value = jsonObject.get(key);
				valueMap.put(key, value);
			}
			return valueMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 如果jsonStr是个[]，则会返回一个List，
	 * 其元素类型可能是：
	 * String---["aa", "bb"]
	 * Integer---[1, 2, 3, 4]
	 * Map<String, Object>---[{}, {}]
	 * List<Object>----[[], [], []]
	 * @param jsonStr
	 * @return
	 */
	public static List<Object> getMapList(String jsonStr){

		if(jsonStr == null || jsonStr.equals("")) {
			throw new RuntimeException("json串的值为空--" + jsonStr==null ? "null值": "长度为0的字符串");
		}
		if(!jsonStr.startsWith("[")) {
			throw new RuntimeException("json串不合法，不是有效的[]格式--" + jsonStr);
		}

		List<Object> list = null;
		try {
			org.json.JSONArray jsonArray = new org.json.JSONArray(jsonStr);
			org.json.JSONObject jsonObj ;
			list = new ArrayList<Object>();
			for(int i = 0 ; i < jsonArray.length() ; i ++){
				Object obj = jsonArray.get(i);
				if(obj instanceof org.json.JSONObject){
					list.add(getFullMap(obj.toString()));
				}else if(obj instanceof org.json.JSONArray){
					list.add(getMapList(obj.toString()));
				}else{
					list.add(obj);
				}


			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 对于{}类型的json，返回一个Map
	 *
	 * 其值的类型可能是：
	 * String---key: "aa"
	 * Integer---key: 1
	 * Map<String, Object>---key: {}
	 * List<Object>----key:[]
	 * @param jsonStr
	 * @return
     */
	public static Map<String, Object> getFullMap(String jsonStr){
		if(jsonStr == null || jsonStr.equals("")) {
			throw new RuntimeException("json串的值为空--" + jsonStr==null ? "null值": "长度为0的字符串");
		}
		if(!jsonStr.startsWith("{")) {
			throw new RuntimeException("json串不合法，不是有效的{}格式--" + jsonStr);
		}


		JSONObject jsonObject ;
		try {
			jsonObject = new JSONObject(jsonStr);

			Iterator<String> keyIter= jsonObject.keys();
			String key;
			Object value ;
			Map<String, Object> valueMap = new HashMap<String, Object>();
			while (keyIter.hasNext()) {
				key = keyIter.next();
				value = jsonObject.get(key);
				if(value instanceof JSONObject){
					valueMap.put(key, getFullMap(value.toString()));
				}else if(value instanceof JSONArray){
					valueMap.put(key, getMapList(value.toString()));
				}else{
					valueMap.put(key, value);
				}
			}
			return valueMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String toJson(Object bean){
		
		if(bean == null){
			return "{}";
		}
		return JSON.toJSONString(bean);
	}
	

}
