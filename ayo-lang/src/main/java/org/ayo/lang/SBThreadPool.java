package org.ayo.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SBThreadPool {
	private ExecutorService service;
	private Map<String, Future<?>> tasks = new HashMap<String, Future<?>>();
	
	private SBThreadPool(){
		int num = Runtime.getRuntime().availableProcessors();
		service = Executors.newFixedThreadPool(num*2);
	}
	
	private static SBThreadPool manager;
	
	
	public static SBThreadPool getInstance(){
		if(manager==null)
		{
			manager= new SBThreadPool();
		}
		return manager;
	}
	
	public void addTask(Runnable runnable, String tag){
		Future<?> f = service.submit(runnable);
		this.tasks.put(tag, f);
	}
	
	public void kill(String tag){
		if(tasks.containsKey(tag)){
			Future<?> f = tasks.get(tag);
			f.cancel(true);
		}
	}
	
}
