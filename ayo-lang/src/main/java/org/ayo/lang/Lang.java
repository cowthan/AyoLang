package org.ayo.lang;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.WindowManager;

import org.apache.http.conn.util.InetAddressUtils;
import org.ayo.Ayo;
import org.ayo.Configer;
import org.ayo.codec.Codec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.ayo.Ayo.context;

/**
 * common utils, like java.lang
 * @author cowthan
 *
 */
public class Lang {
	
	public static boolean isEmpty(Collection<?> c) {
		return (c == null || c.size() == 0);
	}

	public static boolean isEmpty(Map<?, ?> c) {
		return (c == null || c.size() == 0);
	}

	public static boolean isNotEmpty(Collection<?> c) {
		return !(c == null || c.size() == 0);
	}

	public static boolean isEmpty(Object o) {
		return o == null;
	}

	public static boolean isNotEmpty(Object o) {
		return o != null;
	}

	public static <T> boolean isEmpty(T[] arr) {
		return (arr == null || arr.length <= 0);
	}

	public static <T> boolean isNotEmpty(T[] arr) {
		return !(arr == null || arr.length <= 0);
	}

	public static boolean isEmpty(String str) {
		return (str == null || str.trim().equals(""));
	}

	public static boolean isNotEmpty(String str) {
		return !(str == null || str.trim().equals(""));
	}

	// -----------------
	public static int count(Collection<?> c) {
		return c == null ? 0 : c.size();
	}

	public static <T> int count(T[] arr) {
		return arr == null ? 0 : arr.length;
	}
	
	public static <T> int count(String s) {
		return s == null ? 0 : s.length();
	}

	public static <K, V> int count(Map<K, V> s) {
		return s == null ? 0 : s.size();
	}

	//----------------------
	public static String rstring(int id){
		return context.getResources().getString(id);
	}

	public static int rcolor(int id){
		return context.getResources().getColor(id);
	}

	public static float rdimen(int id){
		return context.getResources().getDimension(id);
	}
	//------------------------------


	/**
	 * check if ele contains by array, equals method will be called
	 * array = null, then false
	 * ele = null, then false
     *
	 * depends on T.equals(T, T)
	 *
     * @param array
     * @param ele
     */
    public static <T> boolean contains(T[] array, T ele) {
        if (null == array || array.length == 0)
            return false;
        if(ele == null)
        	return false;
        for (T e : array) {
            if (equals(e, ele))
                return true;
        }
        return false;
    }

	/**
	 * check if ele contains by list, equals method will be called
	 * array = null, then false
	 * ele = null, then false
	 *
	 * depends on T.equals(T, T)
	 *
	 * @param array
	 * @param ele
	 */
    public static <T> boolean contains(List<T> array, T ele) {
        if (null == array || array.size() == 0)
            return false;
        if(ele == null)
        	return false;
        return array.contains(ele);
    }
    
    public static <T> boolean contains(Set<T> array, T ele) {
        if (null == array || array.size() == 0)
            return false;
        if(ele == null)
        	return false;
        return array.contains(ele);
    }
    
    public static <K, V> boolean containsKey(Map<K, V> map, K key) {
        if (null == map || map.size() == 0)
            return false;
        if(key == null)
        	return false;
        return map.containsKey(key);
    }
    
    public static <K, V> boolean containsValue(Map<K, V> map, V value) {
        if (null == map || map.size() == 0)
            return false;
        if(value == null)
        	return false;
        return map.containsValue(value);
    }
	
	//----------
	public static boolean equals(Object o1, Object o2) {
		
		if (o1 == null || o2 == null) {
			return false;
		} else {
			return o1.equals(o2);
		}
	}
	
	/**
     *
     * @param s1
     * @param s2
     */
    public static boolean equalsIgnoreCase(String s1, String s2) {
		if (s1 == null || s2 == null) {
			return false;
		} else {
			return s1.equalsIgnoreCase(s2);
		}
    }
	
	/** in case of obj is null **/
	public static String toString(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	
	//---------------
	/**
     *
     * <pre>
     * String[] strs = Lang.array("A", "B", "A"); => ["A","B","A"]
     * </pre>
     * 
     * @param eles
     */
    public static <T> T[] array(T... eles) {
        return eles;
    }
    
    /**
     * <pre>
     * List&lt;Pet&gt; pets = Lang.list(pet1, pet2, pet3);
     * </pre>
     * 
     */
    public static <T> ArrayList<T> list(T... eles) {
        ArrayList<T> list = new ArrayList<T>(eles.length);
        for (T ele : eles)
            list.add(ele);
        return list;
    }
    
    /**
     */
    public static <T> Set<T> set(T... eles) {
        Set<T> set = new HashSet<T>();
        for (T ele : eles)
            set.add(ele);
        return set;
    }
    
    //-------
    public static String snull(Object maybeNull, String replaceNull){
		return maybeNull == null ? replaceNull : maybeNull.toString();
	}
    public static String snull(Object maybeNull){
		return maybeNull == null ? "" : maybeNull.toString();
	}
    

    
    //-------
    public static String toDate(String pattern, String seconds){
		return toDate(pattern, toInt(seconds));
	}
    
    public static String toDate(String pattern, long seconds){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
		return sdf.format(new Date(seconds * 1000));
	}

	public static String toDate(String pattern, Date date){
		if(date == null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
		return sdf.format(date);
	}
	
	/**
     * 尝试把str的时间格式转为format的格式，通过new Date(str)来转，只能尽量转，失败则返回原来字符串
     * @param str 类似Tue May 31 17:46:55 +0800 2011的字符串
     * @param format
     * @throws Exception
     */
    public static String tryToDate(String str, String format){
        try {
            Date date = new Date(str);
            DateFormat df = new SimpleDateFormat(format);
            String s = df.format(date);
            return s;
        }catch (Exception e){
            e.printStackTrace();
            return str;
        }
    }

	public static int toInt(String strInt){
		try {
			return Integer.parseInt(strInt);
		} catch (Exception e) {
			return 0;
		}
	}

	public static int toInt(String strInt, int defaultValue){
		try {
			return Integer.parseInt(strInt);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static long toLong(String strInt){
		try {
			return Long.parseLong(strInt);
		} catch (Exception e) {
			return 0;
		}
	}

	public static long toLong(String strInt, int defaultValue){
		try {
			return Long.parseLong(strInt);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static double toDouble(String strInt){
		try {
			return Double.parseDouble(strInt);
		} catch (Exception e) {
			return 0;
		}
	}

	public static double toDouble(String strInt, int defaultValue){
		try {
			return Double.parseDouble(strInt);
		} catch (Exception e) {
			return defaultValue;
		}
	}


	public static float toFloat(String str){
		try {
			return Float.parseFloat(str);
		} catch (Exception e) {
			return 0;
		}
	}
	public static float toFloat(String str, int defaultValue){
		try {
			return Float.parseFloat(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}
    
	//----
	public static String readThrowable(Throwable ex) {
		try {
			Writer info = new StringWriter();
			PrintWriter printWriter = new PrintWriter(info);
			ex.printStackTrace(printWriter);

			Throwable cause = ex.getCause();
			while (cause != null) {
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}

			String result = info.toString();
			printWriter.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "read throwable faild";
		}

	}

	public static boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}



	public static void i_am_cold() {
		Vibrator v = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(100);
	}

	public static void sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void run(final Runnable task, final int maxCount, int intervalMillis){
		//final AtomicInteger atom = new AtomicInteger(0);
		final ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
		es.scheduleAtFixedRate(new Runnable() {

			int count = 0;

			@Override
			public void run() {
				count++;
				if(count > maxCount){
					es.shutdown();
					return;
				}else{
					task.run();
				}
			}
		},0, intervalMillis, TimeUnit.MILLISECONDS);

	}


	public static class date{

		private static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
		/**
		 * 年月日[2015-07-28]
		 *
		 * @param timeInMills
		 * @return
		 */
		public static String getYmd(long timeInMills) {
			return ymd.format(new java.util.Date(timeInMills));
		}

		/**
		 * 是否是今天
		 *
		 * @param timeInMills
		 * @return
		 */
		public static boolean isToday(long timeInMills) {
			String dest = getYmd(timeInMills);
			String now = getYmd(Calendar.getInstance().getTimeInMillis());
			return dest.equals(now);
		}

		/**
		 * 是否是同一天
		 *
		 * @param aMills
		 * @param bMills
		 * @return
		 */
		public static boolean isSameDay(long aMills, long bMills) {
			String aDay = getYmd(aMills);
			String bDay = getYmd(bMills);
			return aDay.equals(bDay);
		}

		/**
		 * 获取年份
		 *
		 * @param mills
		 * @return
		 */
		public static int getYear(long mills) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(mills);
			return calendar.get(Calendar.YEAR);
		}

		/**
		 * 获取月份
		 *
		 * @param mills
		 * @return
		 */
		public static int getMonth(long mills) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(mills);
			return calendar.get(Calendar.MONTH) + 1;
		}


		/**
		 * 获取月份的天数
		 *
		 * @param mills
		 * @return
		 */
		public static int getDaysInMonth(long mills) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(mills);

			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);

			switch (month) {
				case Calendar.JANUARY:
				case Calendar.MARCH:
				case Calendar.MAY:
				case Calendar.JULY:
				case Calendar.AUGUST:
				case Calendar.OCTOBER:
				case Calendar.DECEMBER:
					return 31;
				case Calendar.APRIL:
				case Calendar.JUNE:
				case Calendar.SEPTEMBER:
				case Calendar.NOVEMBER:
					return 30;
				case Calendar.FEBRUARY:
					return (year % 4 == 0) ? 29 : 28;
				default:
					throw new IllegalArgumentException("Invalid Month");
			}
		}


		/**
		 * 获取星期,0-周日,1-周一，2-周二，3-周三，4-周四，5-周五，6-周六
		 *
		 * @param mills
		 * @return
		 */
		public static int getWeek(long mills) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(mills);

			return calendar.get(Calendar.DAY_OF_WEEK) - 1;
		}

		/**
		 * 获取当月第一天的时间（毫秒值）
		 *
		 * @param mills
		 * @return
		 */
		public static long getFirstOfMonth(long mills) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(mills);
			calendar.set(Calendar.DAY_OF_MONTH, 1);

			return calendar.getTimeInMillis();
		}
	}

	public static class random{

		static java.util.Random _random = new java.util.Random(System.currentTimeMillis());

		public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		public static final String NUMBERS = "0123456789";
		public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		public static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

		public static String getRandomNumbersAndLetters(int length) {
			return getRandom(NUMBERS_AND_LETTERS, length);
		}

		public static String getRandomNumbers(int length) {
			return getRandom(NUMBERS, length);
		}

		public static String getRandomLetters(int length) {
			return getRandom(LETTERS, length);
		}

		public static String getRandomCapitalLetters(int length) {
			return getRandom(CAPITAL_LETTERS, length);
		}

		public static String getRandomLowerCaseLetters(int length) {
			return getRandom(LOWER_CASE_LETTERS, length);
		}

		public static String getRandom(String source, int length) {
			return TextUtils.isEmpty(source) ? null : getRandom(source.toCharArray(), length);
		}

		public static String getRandom(char[] sourceChar, int length) {
			if (sourceChar == null || sourceChar.length == 0 || length < 0) {
				return null;
			}

			StringBuilder str = new StringBuilder(length);

			for (int i = 0; i < length; i++) {
				str.append(sourceChar[_random.nextInt(sourceChar.length)]);
			}
			return str.toString();
		}

		public static int randomInt(int max) {
			return randomInt(0, max);
		}

		public static int randomInt(int min, int max) {
			if (min > max) {
				return 0;
			}
			if (min == max) {
				return min;
			}
			return min + new java.util.Random().nextInt(max - min);
		}

		public static int randomColor() {
			Random rand = new Random();
			return Color.argb(100, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
		}
	}

	public static class network{
		public static final String NETWORK_TYPE_WIFI = "wifi";
		public static final String NETWORK_TYPE_3G = "eg";
		public static final String NETWORK_TYPE_2G = "2g";
		public static final String NETWORK_TYPE_WAP = "wap";
		public static final String NETWORK_TYPE_UNKNOWN = "unknown";
		public static final String NETWORK_TYPE_DISCONNECT = "disconnect";

		public static int getNetworkType(Context context) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
			return networkInfo == null ? -1 : networkInfo.getType();
		}

		public static String getNetworkTypeName(Context context) {
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo;
			String type = NETWORK_TYPE_DISCONNECT;
			if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null) {
				return type;
			}

			if (networkInfo.isConnected()) {
				String typeName = networkInfo.getTypeName();
				if ("WIFI".equalsIgnoreCase(typeName)) {
					type = NETWORK_TYPE_WIFI;
				} else if ("MOBILE".equalsIgnoreCase(typeName)) {
					String proxyHost = android.net.Proxy.getDefaultHost();
					type = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORK_TYPE_3G : NETWORK_TYPE_2G)
							: NETWORK_TYPE_WAP;
				} else {
					type = NETWORK_TYPE_UNKNOWN;
				}
			}
			return type;
		}

		private static boolean isFastMobileNetwork(Context context) {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (telephonyManager == null) {
				return false;
			}

			switch (telephonyManager.getNetworkType()) {
				case TelephonyManager.NETWORK_TYPE_1xRTT:
					return false;
				case TelephonyManager.NETWORK_TYPE_CDMA:
					return false;
				case TelephonyManager.NETWORK_TYPE_EDGE:
					return false;
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
					return true;
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
					return true;
				case TelephonyManager.NETWORK_TYPE_GPRS:
					return false;
				case TelephonyManager.NETWORK_TYPE_HSDPA:
					return true;
				case TelephonyManager.NETWORK_TYPE_HSPA:
					return true;
				case TelephonyManager.NETWORK_TYPE_HSUPA:
					return true;
				case TelephonyManager.NETWORK_TYPE_UMTS:
					return true;
				case TelephonyManager.NETWORK_TYPE_EHRPD:
					return true;
				case TelephonyManager.NETWORK_TYPE_EVDO_B:
					return true;
				case TelephonyManager.NETWORK_TYPE_HSPAP:
					return true;
				case TelephonyManager.NETWORK_TYPE_IDEN:
					return false;
				case TelephonyManager.NETWORK_TYPE_LTE:
					return true;
				case TelephonyManager.NETWORK_TYPE_UNKNOWN:
					return false;
				default:
					return false;
			}
		}

	}

	public static class system{
		public static void startBrowser(String url){
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse(url);
			intent.setData(content_url);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}

		/**
		 * 安装App
		 *
		 * @param context
		 * @param filePath
		 * @return
		 */
		public static boolean installNormal(Context context, String filePath) {
			Intent i = new Intent(Intent.ACTION_VIEW);
			java.io.File file = new java.io.File(filePath);
			if (file == null || !file.exists() || !file.isFile() || file.length() <= 0) {
				return false;
			}

			i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
			return true;
		}

		/**
		 * 卸载App
		 *
		 * @param context
		 * @param packageName
		 * @return
		 */
		public static boolean uninstallNormal(Context context, String packageName) {
			if (packageName == null || packageName.length() == 0) {
				return false;
			}

			Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse(new StringBuilder().append("package:")
					.append(packageName).toString()));
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
			return true;
		}

		/**
		 * 判断是否是系统App
		 *
		 * @param context
		 * @param packageName 包名
		 * @return
		 */
		public static boolean isSystemApplication(Context context, String packageName) {
			if (context == null) {
				return false;
			}
			PackageManager packageManager = context.getPackageManager();
			if (packageManager == null || packageName == null || packageName.length() == 0) {
				return false;
			}

			try {
				ApplicationInfo app = packageManager.getApplicationInfo(packageName, 0);
				return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
			} catch (PackageManager.NameNotFoundException e) {
				e.printStackTrace();
			}
			return false;
		}

		/**
		 * 判断某个包名是否运行在顶层
		 *
		 * @param context
		 * @param packageName
		 * @return
		 */
		public static Boolean isTopActivity(Context context, String packageName) {
			if (context == null || TextUtils.isEmpty(packageName)) {
				return null;
			}

			ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
			if (tasksInfo == null || tasksInfo.isEmpty()) {
				return null;
			}
			try {
				return packageName.equals(tasksInfo.get(0).topActivity.getPackageName());
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		public static void openSystemAlbun(Activity a, int requestCode){
			Intent intent = new Intent(Intent.ACTION_PICK, null);

			intent.setDataAndType(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			a.startActivityForResult(intent, requestCode);
		}

		public static void openCamera(Activity a, File outFile, int requestCode){
			Intent intent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
			a.startActivityForResult(intent, requestCode);
		}

		/**
		 * @param uri
		 */
		public static void openPhotoCrop(Activity a, Uri uri, File outFile, int requestCode) {
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, "image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 150);
			intent.putExtra("outputY", 150);
			intent.putExtra("output", Uri.fromFile(outFile)); // save path
			a.startActivityForResult(intent, requestCode);
		}
	}

	public static class app{
		public static String getAppVersionName() {
			String versionName = "";
			try {
				PackageManager pm = context.getPackageManager();
				PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
				versionName = pi.versionName;
				if (versionName == null || versionName.length() <= 0) {
					return "";
				}
			} catch (Exception e) {
				Log.e("VersionInfo", "Exception", e);
			}
			return versionName;
		}

		public static int getAppVersionCode() {
			int versionCode = 0;
			try {
				PackageManager pm = context.getPackageManager();
				PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
				versionCode = pi.versionCode;
			} catch (Exception e) {
				Log.e("VersionInfo", "Exception", e);
			}
			return versionCode;
		}

		/**
		 * 获取当前进程的名字，名字类似于：
		 * com.ayo.daogou
		 * com.ayo.daogou:remote
		 *
		 * 同一个app可以起多个进程，但是每个进程都有一个虚拟机，并都会启动一次application，所以application里的东西，
		 * 最好保证只初始化一次
		 *
		 * 如果你有一个Activity需要运行在单独进程里，则默认进程里的单例，静态变量等都没法共享了，所以你可以单独给这个
		 * 进程初始化一份application里的数据
		 *
		 * 归根结底，这方面的需求还是很少的
		 *
		 * 极光为什么要单独开个进程？
		 * 融云的初始化官方已经建议判断当前进程了，只能在默认进程里初始化
		 * 友盟更新相关的DownloadService单独开了一个进程
		 *
		 *
		 * @param context
		 * @return
		 */
		public static String getCurProcessName(Context context) {
			int pid = android.os.Process.myPid();
			ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
				if (appProcess.pid == pid) {
					return appProcess.processName;
				}
			}
			return null;
		}


		/**
		 *
		 */
		public static String getMetaData(Context context, String key) {
			try {
				ApplicationInfo ai = context.getPackageManager()
						.getApplicationInfo(
								context.getPackageName(), PackageManager.GET_META_DATA);
				Object value = ai.metaData.get(key);
				if (value != null) {
					return value.toString();
				}
			} catch (Exception e) {
			}
			return null;
		}

		public static boolean checkPermission(Context context, String permission){
			PackageManager localPackageManager = context.getPackageManager();
			if (localPackageManager.checkPermission(permission,  //"android.permission.ACCESS_NETWORK_STATE"
					context.getPackageName()) != 0) {
				return true;
			}else{
				return false;
			}
		}


		public static String getPackageName() {
			String packageName = "";
			PackageManager packageManager = Ayo.context.getPackageManager();
			try {
				PackageInfo packageInfo = packageManager.getPackageInfo(Ayo.context.getPackageName(), 0);
				packageName = packageInfo.packageName;
			} catch (PackageManager.NameNotFoundException e) {
				e.printStackTrace();
			}
			return packageName;
		}

		/*
         * require    <uses-permission android:name="android.permission.GET_TASKS" />
         */
		public static boolean isMyPackageRunningOnTop() {
			ActivityManager am = (ActivityManager) Ayo.context.getSystemService(Context.ACTIVITY_SERVICE);
			if(am == null) {
				return false;
			}
			List<ActivityManager.RunningTaskInfo> infos = am.getRunningTasks(1);
			if (infos != null && !infos.isEmpty()) {
				ActivityManager.RunningTaskInfo info = infos.get(0);
				ComponentName componentName = info.topActivity;
				if (componentName != null
						&& componentName.getPackageName().equals(Ayo.context.getPackageName())) {
					return true;
				}
			}
			return false;
		}

		public static boolean isMyServiceRunning(Class<?> serviceClass) {
			ActivityManager manager = (ActivityManager) Ayo.context.getSystemService(Context.ACTIVITY_SERVICE);
			for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
				if (serviceClass.getName().equals(service.service.getClassName())) {
					return true;
				}
			}
			return false;
		}

		public static boolean isMyAppRunning() {
			ActivityManager activityManager = (ActivityManager) Ayo.context.getSystemService(Context.ACTIVITY_SERVICE);
			List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
			boolean run = false;
			for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
				if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					if (appProcess.processName.equals(getPackageName())) {
						run = true;
						break;
					}
				}
			}

			return run;
		}

		/**
		 * 判断当前应用是否运行在后台
		 *
		 * @return
		 */
		public static boolean isMyAppInBackground() {
			ActivityManager am = (ActivityManager) Ayo.context.getSystemService(Context.ACTIVITY_SERVICE);
			List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
			if (taskList != null && !taskList.isEmpty()) {
				ComponentName topActivity = taskList.get(0).topActivity;
				if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName())) {
					return true;
				}
			}
			return false;
		}


		public static String getSign(){
			return getSign(Lang.app.getPackageName());
		}

		public static String getSign(String pkg)
		{
			Signature[] ss = getRawSignature(context, pkg);
			if ((ss == null) || (ss.length == 0)){
				//JLog.i("signs is null");
				return "";
			}
			int j = ss.length;
			int i = 0;
			String s = "";

			while (i < j)
			{
				s += Codec.MD5.md5(ss[i].toCharsString()) ;//hex(ss[i].toByteArray());
				i += 1;
			}
			return s;
		}

		private static Signature[] getRawSignature(Context paramContext, String paramString)
		{
			if ((paramString == null) || (paramString.length() == 0))
			{
				return null;
			}
			PackageManager pm = paramContext.getPackageManager();
			try
			{
				PackageInfo pif = pm.getPackageInfo(paramString, PackageManager.GET_SIGNATURES);
				if (paramContext == null)
				{
					//JLog.i("sign---info is null, packageName = " + paramString);
					return null;
				}
				return pif.signatures;
			}
			catch (android.content.pm.PackageManager.NameNotFoundException e)
			{
				//JLog.i("sign---NameNotFoundException");
				return null;
			}

		}
	}

	public static class phone{
		public static boolean isEmulator(Context context){
			String androidId= Settings.System.getString(context.getContentResolver(), "android_id");
			if(TextUtils.isEmpty(androidId)
					|| Build.MODEL.equals("sdk")
					|| Build.MODEL.equals("google_sdk")){
				return true;
			}
			return false;
		}

		/**
		 * get the phone number, always fail
		 */
		public static String getPhoneNumber() {
			TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String number = tManager.getLine1Number();
			//Log.e(TAG, "the phone number is: " + number);
			return number;
		}

		/**
		 */
		protected static String getCpuInfo() {
			String str = null;
			FileReader localFileReader = null;
			BufferedReader localBufferedReader = null;
			try {
				localFileReader = new FileReader("/proc/cpuinfo");
				if (localFileReader != null)
					try {
						localBufferedReader = new BufferedReader(localFileReader, 1024);
						str = localBufferedReader.readLine();
						localBufferedReader.close();
						localFileReader.close();
					} catch (IOException localIOException) {
						localIOException.printStackTrace();
						//Log.e(TAG, "Could not read from file /proc/cpuinfo", localIOException);
					}
			} catch (FileNotFoundException localFileNotFoundException) {
				localFileNotFoundException.printStackTrace();
				//Log.e(TAG, "Could not open file /proc/cpuinfo", localFileNotFoundException);
			}
			if (str != null) {
				int i = str.indexOf(58) + 1;
				str = str.substring(i);
			}
			return str.trim();
		}


		@SuppressLint("NewApi")
		public static String getDeviceId() {
			String KEY = "deviceId-genius";
			String deviceId = Configer.getInstance().get(KEY, "");
			if (Lang.isNotEmpty(deviceId)) {
				return deviceId;
			}

			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			deviceId = tm.getDeviceId();
			if (Lang.isEmpty(deviceId)) {
				if (Build.VERSION.SDK_INT >= 10) {
					deviceId = Build.SERIAL;
				}
			}
			if (Lang.isEmpty(deviceId) || "0123456789ABCDE".equals(deviceId)) {
				deviceId = Settings.Secure.getString(context.getContentResolver(),
						Settings.Secure.ANDROID_ID);
				if ("9774d56d682e549c".equals(deviceId)
						|| Lang.isEmpty(deviceId)) {
					try {
						deviceId = Configer.getInstance().get(KEY, "");
						if (Lang.isNotEmpty(deviceId)) {

						} else {
							deviceId = UUID.randomUUID().toString();
							Configer.getInstance().put(KEY, deviceId);
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
			if (Lang.isEmpty(deviceId)) {
				deviceId = UUID.randomUUID().toString();
			}
			Configer.getInstance().put(KEY, deviceId);
			return deviceId;
		}

		/**
		 *
		 * @param mContext
		 */
		public static String[] getAccesstype(Context mContext) {
			// TODO Auto-generated method stub
			String[] arrayOfString = {
					"Unknown", "Unknown"
			};
			PackageManager localPackageManager = mContext.getPackageManager();
			if (localPackageManager.checkPermission("android.permission.ACCESS_NETWORK_STATE",
					mContext.getPackageName()) != 0) {
				arrayOfString[0] = "Unknown";
				return arrayOfString;
			}
			ConnectivityManager localConnectivityManager = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE); //"connectivity");
			if (localConnectivityManager == null) {
				arrayOfString[0] = "Unknown";
				return arrayOfString;
			}
			NetworkInfo localNetworkInfo1 = localConnectivityManager.getNetworkInfo(1);
			if (localNetworkInfo1.getState() == NetworkInfo.State.CONNECTED) {
				arrayOfString[0] = "wifi";
				return arrayOfString;
			}
			NetworkInfo localNetworkInfo2 = localConnectivityManager.getNetworkInfo(0);
			if (localNetworkInfo2.getState() == NetworkInfo.State.CONNECTED) {
				arrayOfString[0] = "2G/3G";
				arrayOfString[1] = localNetworkInfo2.getSubtypeName();
				return arrayOfString;
			}
			return arrayOfString;
		}

		/**
		 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
		 */
		public static String getMac() {
			String mac = null;
			try {
				WifiManager localWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE); //"wifi");
				WifiInfo localWifiInfo = localWifiManager.getConnectionInfo();
				mac = localWifiInfo.getMacAddress();
			} catch (Exception localException) {
				Log.i("Mac", "Could not read MAC, forget to include ACCESS_WIFI_STATE permission?",
						localException);
			}
			return mac;
		}

		public static String getLocAddress() {

			String ipaddress = "";

			try {
				Enumeration<NetworkInterface> en = NetworkInterface
						.getNetworkInterfaces();
				while (en.hasMoreElements()) {
					NetworkInterface networks = en.nextElement();
					Enumeration<InetAddress> address = networks
							.getInetAddresses();
					while (address.hasMoreElements()) {
						InetAddress ip = address.nextElement();
						System.out.println("+++: " + ip.getHostAddress());
						if (!ip.isLoopbackAddress()
								&& InetAddressUtils.isIPv4Address(ip
								.getHostAddress())
								&& !ip.getHostAddress().equals("10.0.2.15")
								) {
							ipaddress = ip.getHostAddress();
						}
					}
				}
				// ipaddress = "192.168.0.106";
			} catch (SocketException e) {
				e.printStackTrace();
			}

			System.out.println("IP is:" + ipaddress);

			return ipaddress;

		}
	}

	public static class asyc{
		public static void runOnUiThread(Runnable r, long delayMillis){
			new android.os.Handler(Looper.getMainLooper()).postDelayed(r, delayMillis);
		}
	}

	public static class dimen{
		public static int statusBarHeight = 0;
		public static int screenWidth = 0;
		public static int screenHeight = 0;

		public static void init(Context context) {
			statusBarHeight = getStatusBarHeight(context);
			screenWidth = getScreenWidth((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
			screenHeight = getScreenHeight((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
		}

		private static int getStatusBarHeight(Context context) {
			int result = 0;
			int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
			if (resourceId > 0) {
				result = context.getResources().getDimensionPixelSize(resourceId);
			}
			return result;
		}

		private static int getScreenHeight(WindowManager manager) {
			DisplayMetrics metrics = new DisplayMetrics();
			manager.getDefaultDisplay().getMetrics(metrics);
			return metrics.heightPixels;
		}

		private static int getScreenWidth(WindowManager manager) {
			DisplayMetrics metrics = new DisplayMetrics();
			manager.getDefaultDisplay().getMetrics(metrics);
			return metrics.widthPixels;
		}

		// /--------------------
		/**
		 *
		 * @param dpValue
		 * @return
		 */
		public static int dip2px(float dpValue) {
			final float scale = Ayo.context.getResources().getDisplayMetrics().density;
			return (int) (dpValue * scale + 0.5f);
		}

		/**
		 */
		public static int px2dip(float pxValue) {
			final float scale = Ayo.context.getResources().getDisplayMetrics().density;
			return (int) (pxValue / scale + 0.5f) - 15;
		}

		/**
		 *
		 * @param value
		 * @return
		 */
		public static float sp2px(float value) {
			DisplayMetrics metrics = Ayo.context.getResources().getDisplayMetrics();
			return value * metrics.scaledDensity;
		}

		/**
		 *
		 * @param value
		 * @return
		 */
		public static float px2sp(float value) {
			DisplayMetrics metrics = Ayo.context.getResources().getDisplayMetrics();
			return value / metrics.scaledDensity;
		}

		// ========================OrientationHelper================//
		public static final int LANDSCAPE = Configuration.ORIENTATION_LANDSCAPE;
		public static final int PORTRAIT = Configuration.ORIENTATION_PORTRAIT;
		public static final int NOTHING = -100;

		public static Integer userTending(int orientation, int previous) {

			if (previous == PORTRAIT) {
				if (orientation > 85 && orientation < 115) {
					return LANDSCAPE;
				} else if (orientation > 285 && orientation < 300) {
					return LANDSCAPE;
				} else if (orientation > 160 && orientation < 210) {
					return LANDSCAPE;
				}
			} else if (previous == LANDSCAPE) {
				if (orientation > 0 && orientation < 30) {
					return PORTRAIT;
				} else if (orientation > 330 && orientation < 360) {
					return PORTRAIT;
				}
			}
			return NOTHING;
		}
	}

	public static class collection{
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
		public static <K, V> void each(Map<K, V> c, OnWalk<Map.Entry<K,V>> callback) {
			if (callback == null)
				return;
			if(c == null || c.size() == 0)
				return;

			int count = 0;
			for (Map.Entry<K, V> entry : c.entrySet()) {
				callback.process(count, entry, c.size());
				count++;
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

		public static <T> void remove(Collection<T> c, OnWalk<T> callback) {
			if (callback == null)
				return;
			if(Lang.isEmpty(c))
				return;
			Iterator<T> it = c.iterator();
			int count = 0;
			while(it.hasNext()){
				boolean willBeDelete = callback.process(count, it.next(), c.size());
				count++;
				if(willBeDelete) it.remove();
			}
//			List<T> list = Lang.collection.clone(c);
//			if (Lang.isNotEmpty(list)) {
//				int count = 0;
//				for (T o : list) {
//					boolean willBeDelete = callback.process(count, o, c.size());
//					count++;
//					if(willBeDelete){
//						c.remove(o);
//					}
//				}
//			}
		}

		public static <K, V> void remove(Map<K, V> c, OnWalk<Map.Entry<K,V>> callback) {
			if (callback == null)
				return;
			if(Lang.isEmpty(c))
				return;
			int count = 0;
			for (Map.Entry<K, V> entry : c.entrySet()) {
				boolean willBeDeleted = callback.process(count, entry, c.size());
				count++;
				if(willBeDeleted) c.remove(entry.getKey());
			}
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

		public static <K, V> Map<K, V> newHashMap(Pair<K, V>...p){
			HashMap<K, V> m = new HashMap<K, V>();
			if(p != null && p.length > 0){
				for(int i = 0; i < p.length; i++){
					m.put(p[i].first, p[i].second);
				}
			}
			return m;
		}

		public static <K, V> Map<K, V> newHashMap(Object...args){
			Map<K, V> m = new HashMap<K, V>();

			if(args != null && args.length > 0){
				for(int i = 0; i < args.length; i+=2){
					int ki = i;
					int vi = i+1;
					if(ki < args.length && vi < args.length){
						K k = (K)args[ki];
						V v = (V)args[vi];
						m.put(k, v);
					}
				}
			}
			return m;
		}

		public static <K> Set<K> newHashSet(K...args){
			Set<K> set = new HashSet<>();

			if(args != null && args.length > 0){
				for(int i = 0; i < args.length; i++){
					set.add(args[i]);
				}
			}
			return set;
		}
	}
}
