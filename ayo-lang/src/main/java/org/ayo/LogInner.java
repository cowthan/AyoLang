package org.ayo;

import android.util.Log;

public class LogInner {
	
	
	public static void print(String s){
		if(Ayo.debug){
			System.out.println("Genius: " + s);
		}else{
			
		}
	}
	
	public static void debug(String msg){
		if(!Ayo.debug) return;
		if(msg == null) msg = "null";
		Log.i("sb", msg);
	}
}
