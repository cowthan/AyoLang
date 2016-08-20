package org.ayo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import org.ayo.lang.JsonUtils;

import java.util.List;


@SuppressLint("NewApi")
public class Configer {
	private SharedPreferences sp;
	private static Configer instance;
	private Configer(Context context){
		sp = context.getSharedPreferences(Ayo.spName, Context.MODE_PRIVATE);
	}
	
	public static Configer getInstance(){
		if(instance == null) instance = new Configer(Ayo.context);
		return instance;
	}
	
	public void put(String name, boolean value){
		sp.edit().putBoolean(name, value).apply();
	}
	public boolean get(String name, boolean defaultValue){
		return sp.getBoolean(name, defaultValue);
	}
	
	public void put(String name, String value){
		sp.edit().putString(name, value).apply();
	}
	public String get(String name, String defaultValue){
		return sp.getString(name, defaultValue);
	}
	public void put(String name, int value){
		sp.edit().putInt(name, value).apply();
	}
	public int get(String name, int defaultValue){
		return sp.getInt(name, defaultValue);
	}
	public void put(String name, double value){
		sp.edit().putFloat(name, (float)value).apply();
	}
	public double get(String name, double defaultValue){
		return sp.getFloat(name, (float)defaultValue);
	}
	//-----------------
	public void put(String name, Object value){
		//boolean ,float, int long string, StringSet
		if(value instanceof String)
		{
			sp.edit().putString(name, (String)value).apply();
		}
		else if(value instanceof Boolean)
		{
			sp.edit().putBoolean(name, (Boolean)value).apply();
		}
		else if(value instanceof Integer)
		{
			sp.edit().putInt(name, (Integer)value).apply();
		}
		else if(value instanceof Float || value instanceof Double)
		{
			sp.edit().putFloat(name, (Float)value).apply();
		}
		
		else if(value instanceof Long)
		{
			sp.edit().putLong(name, (Long)value).apply();
		}
		
	}
	
	public Object get(String name, Object defaultValue){
		if(defaultValue instanceof String)
		{
			sp.getString(name, (String)defaultValue);
		}
		else if(defaultValue instanceof Boolean)
		{
			sp.getBoolean(name, (Boolean)defaultValue);
		}
		else if(defaultValue instanceof Integer)
		{
			sp.getInt(name, (Integer)defaultValue);
		}
		else if(defaultValue instanceof Float || defaultValue instanceof Double)
		{
			sp.getFloat(name, (Float)defaultValue);
		}
		else if(defaultValue instanceof Long)
		{
			sp.getLong(name, (Long)defaultValue);
		}
		
		return defaultValue;
	}
	
	//-----------------
	public static <T> void putObject(String key, T t){
		if(t == null) return;
		String json = JsonUtils.toJson(t);
		getInstance().put(key, json);
	}
	
	public static <T> T getObject(String key, Class<T> clazz){
		String json = getInstance().get(key, "{}");
		if(json.equals("{}")){
			return null;
		}
		T t = JsonUtils.getBean(json, clazz);
		return t;
	}
	
	public static <T> List<T> getList(String key, Class<T> clazz){
		String json = getInstance().get(key, "[]");
		List<T> t = JsonUtils.getBeanList(json, clazz);
		return t;
	}
	
}
