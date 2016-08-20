package org.ayo.lang;

import android.util.Pair;

import java.util.HashMap;

public class Maps {
	public static <K, V> HashMap<K, V> newHashMap(Pair<K, V>...p){
		HashMap<K, V> m = new HashMap<K, V>();
		if(p != null && p.length > 0){
			for(int i = 0; i < p.length; i++){
				m.put(p[i].first, p[i].second);
			}
		}
		return m;
	}
}
