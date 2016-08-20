package org.ayo.lang;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Lists {
	
	public static <T> ArrayList<T> newArrayList(T...t){
		ArrayList<T> list = new ArrayList<T>();
		if(t == null || t.length == 0){
			return list;
		}else{
			for(int i = 0; i < t.length; i++){
				list.add(t[i]);
			}
		}
		return list;
	}
	
	public static <T> T lastElement(List<T> list){
		if(list == null || list.size() == 0) return null;
		return list.get(list.size() - 1);
	}
	
	public static <T> T firstElement(List<T> list){
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	
	/**
	 * delete traitors from ours
	 *
	 * @param <T>
	 * @param ours
	 * @param traitors
	 * @return
	 */
	public static <T> List<T> killTraitors(List<T> ours, List<T> traitors) {
		int len = ours.size();
		for (int i = 0; i < len; i++) {
			if (traitors.contains(ours.get(i))) {
				ours.remove(i);
			}
		}
		return ours;
	}

	public static <T> void each(Collection<T> c, OnWalk<T> callback) {
		if (callback == null)
			return;
		if (Lang.isNotEmpty(c)) {
			int count = 0;
			for (T o : c) {
				callback.process(count, o, c.size());
				count++;
			}
		}
	}

	public static <T> void each(T[] c, OnWalk<T> callback) {
		if (callback == null)
			return;
		if (Lang.isNotEmpty(c)) {
			int count = 0;
			for (T o : c) {
				callback.process(count, o, c.length);
				count++;
			}
		}
	}

	public static <T> void remove(List<T> c, OnWalk<T> callback) {
		if (callback == null)
			return;
		List<T> list = Lists.clone(c);
		if (Lang.isNotEmpty(list)) {
			int count = 0;
			for (T o : list) {
				boolean willBeDelete = callback.process(count, o, c.size());
				count++;
				if(willBeDelete){
					c.remove(o);
				}
			}
		}
	}

	private void t(){
		List<String> list;

	}

	public static <T> List<T> clone(List<T> c){
		List<T> list = new ArrayList<T>();
		if (Lang.isNotEmpty(c)) {
			for (T o : c) {
				list.add(o);
			}
		}
		return list;
	}

	public static <T> List<T> clone(T[] c){
		List<T> list = new ArrayList<T>();
		if (Lang.isNotEmpty(c)) {
			for (T o : c) {
				list.add(o);
			}
		}
		return list;
	}

	public static <T> Collection<T> combine(Collection<T> c1,
			Collection<T> c2) {
		if (c1 == null && c2 == null)
			return null;
		if (c1 == null)
			return c2;
		if (c2 == null)
			return c1;
		c1.addAll(c2);
		return c1;
	}
}
