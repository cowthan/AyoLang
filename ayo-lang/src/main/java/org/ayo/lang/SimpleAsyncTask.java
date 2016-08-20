package org.ayo.lang;

import android.os.AsyncTask;

public abstract  class SimpleAsyncTask extends AsyncTask<Void, Void, Void> {
	
	protected void onStart(){}
	protected abstract  void onRunning();
	protected void onFinish(){}
	
	@Override
	protected void onPreExecute() {
		onStart();
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		onRunning();
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		onFinish();
	}
	
	public void go(){
		this.execute();
	}


	///----------------

//	private Runnable runnableBackground;
//	private Runnable runnableUIThread;
//
//	public static SimpleAsyncTask newTask(Runnable r){
//		SimpleAsyncTask s = new SimpleAsyncTask();
//		s.runnableBackground = r;
//		return s;
//	}
//
//	public SimpleAsyncTask post(Runnable r){
//		this.runnableUIThread = r;
//		return this;
//	}



//	private void test(){
//		SimpleAsyncTask.newTask(runnable).post(runnable).go();
//	}
}
