package org.ayo.lang;

import android.os.AsyncTask;
import android.os.Looper;

public class Async extends AsyncTask<Void, Void, Void> {
	
	@Override
	protected void onPreExecute() {
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		if(this.runnableBackground != null) this.runnableBackground.run();
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		if(this.runnableUIThread != null) this.runnableUIThread.run();
	}
	
	public void go(){
		this.execute();
	}


	///----------------

	private Runnable runnableBackground;
	private Runnable runnableUIThread;

	public static Async newTask(Runnable r){
		Async s = new Async();
		s.runnableBackground = r;
		return s;
	}

	public Async post(Runnable r){
		this.runnableUIThread = r;
		return this;
	}



	public void test(){
		Async.newTask(new Runnable() {
			@Override
			public void run() {

			}
		}).post(new Runnable() {
			@Override
			public void run() {

			}
		}).go();
	}

	public static void post(Runnable r, long delayMillis){
		new android.os.Handler(Looper.getMainLooper()).postDelayed(r, delayMillis);
	}
}
