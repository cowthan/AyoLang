package org.ayo;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.ayo.jlog.JLog;
import org.ayo.jlog.constant.JLogLevel;
import org.ayo.jlog.constant.JLogSegment;
import org.ayo.lang.Lang;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Ayo {
	
	public static Context context;
	public static String spName = "default.conf";
	public static boolean debug = true;
	public static String ROOT;// default work directory
	public static String ROOT_DIR_NAME;
	//public static String SERVER_ENCODE = "utf-8";

	/**
	 * memory related
	 */
	public static final int MEM_LEVEL_NONE = 1000;
	public static final int MEM_LEVEL_EXERLLENT = 1;
	public static final int MEM_LEVEL_JUST_FINE = 2;
	public static final int MEM_LEVEL_BAD = 3;
	public static final int MEM_LEVEL_NOT_A_PHONE = 4;

	public static int MEM_LEVEL = MEM_LEVEL_JUST_FINE;

//	public static boolean init(Context context, boolean openLog){
//		Ayo.context = context;
//		Ayo.debug = openLog;
//		Lang.dimen.init(context);
//
//		return true;
//	}

	/**
	 * init the genius library
	 * @param context global context
	 * @param path  setSDRoot("/work-dir/"), used be SD_ROOT
	 * @param openLog BuildConfig.DEBUG
	 */
	public static void init(Application context, String path, boolean openLog, boolean logToFile) {
		Ayo.context = context;
		Ayo.debug = openLog;
		Lang.dimen.init(context);
		LogInner.print("genius-init: screen（{w}, {h}）".replace("{w}", Lang.dimen.screenWidth + "").replace("{h}", Lang.dimen.screenHeight + ""));
		
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		LogInner.print("genius-init: memory----Max memory is " + maxMemory + "KB");
		//MI 3--131072K, 130M
		//htc-M8--196608K，200M
		//MI 2A--65536KB，64M
		//HUAWEI C8816：98304KB，96M
		//
		int memMB = maxMemory / 1024;
		if(memMB < 70){
			MEM_LEVEL = MEM_LEVEL_NOT_A_PHONE;
			LogInner.print("genius-init: this is not a phone, image will be scaled by normal+3");
		}
		else if(memMB < 100){
			MEM_LEVEL = MEM_LEVEL_BAD;
			LogInner.print("genius-init: this is a bad phone, image will be scaled by normal+2");
		}else if(memMB < 190){
			MEM_LEVEL = MEM_LEVEL_JUST_FINE;
			LogInner.print("genius-init: this phone is just fine, image will be scaled by normal+1");
		}else{
			MEM_LEVEL = MEM_LEVEL_EXERLLENT;
			LogInner.print("genius-init: this phone is good, image will be scaled by normal");
		}
		
		setSDRoot(path);

		//初始化XUtils

		//初始化JLog
		List<JLogLevel> logLevels = new ArrayList<>(); //哪些级别的日志可以输出到文件中
		logLevels.add(JLogLevel.ERROR);
		logLevels.add(JLogLevel.JSON);

		File logDir = new File(Ayo.ROOT + "log");
		if(!logDir.exists()){
			logDir.mkdirs();
		}

		JLog.init(context)
				.setDebug(openLog)
				.writeToFile(logToFile)
				.setLogDir(Ayo.ROOT + "log")
				.setLogPrefix("log_")
				.setLogSegment(JLogSegment.ONE_HOUR)  //日志按照时间切片写入到不同的文件中
				.setLogLevelsForFile(logLevels)
				.setCharset("UTF-8");
	}

	/**
	 *
	 * @param dirName  "dirName"
	 * @return  Ayo.ROOT变成了/sd/dirName/
     */
	private static boolean setSDRoot(String dirName) {
		if (Lang.isEmpty(dirName)){
			LogInner.print("sd root: " + "设置失败，路径为空");
			return false;
		}

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			Ayo.ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + (dirName.startsWith("/") ? "" : "/") + dirName;
		} else{
			LogInner.print("sd root: " + "找不到sd卡");
			return false;
		}

		if (!Ayo.ROOT.endsWith("/")) {
			Ayo.ROOT += "/";
		}

		Ayo.ROOT_DIR_NAME = dirName.replace("/", "");
		
		File dir = new File(Ayo.ROOT);
		if(dir.exists() && dir.isDirectory()){
			LogInner.print("sd root: " + "已经有啦");
			return true;
		}else{
			if(dir.mkdirs()){
				LogInner.print("sd root: " + "设置成功-" + Ayo.ROOT);
				return true;
			}else{
				LogInner.print("sd root: " + "目录创建失败-" + Ayo.ROOT);
				return false;
			}
		}

	}

	private static class LogInner {


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
}
