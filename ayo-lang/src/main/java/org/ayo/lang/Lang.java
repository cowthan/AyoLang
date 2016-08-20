package org.ayo.lang;

import android.content.Context;
import android.os.Vibrator;

import org.ayo.Ayo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * common utils, like java.lang
 * @author cowthan
 *
 */
public class Lang {
	
	public static boolean isEmpty(Collection<?> c) {
		return (c == null || c.size() == 0);
	}

	public static boolean isNotEmpty(Collection<?> c) {
		return !(c == null || c.size() == 0);
	}

	public static boolean isNull(Object o) {
		return o == null;
	}

	public static boolean isNotNull(Object o) {
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
	
	//----------------------
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
        return map.containsKey(value);
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
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
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

	public static int toInt(String strInt){
		try {
			return Integer.parseInt(strInt);
		} catch (Exception e) {
			return 0;
		}
	}

	public static long toLong(String strInt){
		try {
			return Long.parseLong(strInt);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static double toDouble(String strInt){
		try {
			return Double.parseDouble(strInt);
		} catch (Exception e) {
			return 0;
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

	
	/**
	 * just System.currentTimeMillis()
	 *
	 * @return
	 */
	public static long getTimeStamp() {
		long mili = System.currentTimeMillis();
		return mili;
	}

	

	public static void i_am_cold() {
		Vibrator v = (Vibrator) Ayo.context
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
}
