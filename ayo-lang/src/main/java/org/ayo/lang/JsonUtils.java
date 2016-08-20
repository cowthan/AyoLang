package org.ayo.lang;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	
	public static String toJson(Object bean){
		
		if(bean == null){
			return "{}";
		}
		return JSON.toJSONString(bean);
	}
	
	/**
	 * json is：
	 * {
            "00000": {
                "bounsVaule": "50",
                "detailImgUrl1": "image/bonus/d9a29d18-4b15-4958-b2cf-f39e42bad394.jpg",
                "detailImgUrl2": "image/bonus/5e364e73-1a76-4a3b-9d1b-e0a44b4f4daf.jpg",
                "detailImgUrl3": "image/bonus/c5eed118-bbc9-45a8-8724-a5fc883fc487.jpg",
                "goodsDesc": "11111111111",
                "goodsSize": "22222222222222",
                "imgUrl": "image/bonus/c15c94a9-e142-4c11-803c-ac1f06d95359.jpg",
                "picId": "c5eed118-bbc9-45a8-8724-a5fc883fc487",
                "picName": "1111111111111111111",
                "stocks": "198"
            },
            "00001": {
                "bounsVaule": "100",
                "detailImgUrl1": "image/bonus/8c55ca4b-2174-496e-9758-47fd0a43aaef.jpg",
                "detailImgUrl2": "image/bonus/2baa0e27-3667-48c5-94c3-036f875feee0.jpg",
                "detailImgUrl3": "image/bonus/46fe9a08-3e86-4cff-89ba-ee2f36a800ef.jpg",
                "goodsDesc": "1111111111111111",
                "goodsSize": "11111111",
                "imgUrl": "image/bonus/ede5019c-2806-43d3-a5da-79afb732dbba.jpg",
                "picId": "46fe9a08-3e86-4cff-89ba-ee2f36a800ef",
                "picName": "1111111111",
                "stocks": "98"
            },
	 * @param jsonArrayString
	 * @param cls
	 * @return
	 */
	public static <T> List<T> getBeanListFromStrangJson_1(String jsonArrayString, Class<T> cls) {
		
		try {
			JSONObject jo = new JSONObject(jsonArrayString);
			List<T> list = new ArrayList<T>();
			Iterator<String> it = jo.keys();
			while(it.hasNext()){
				String k = it.next();
				String json = jo.getString(k);
				T t = getBean(json, cls);
				list.add(t);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
