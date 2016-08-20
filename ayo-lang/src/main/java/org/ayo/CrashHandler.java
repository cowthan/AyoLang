package org.ayo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.Log;

import org.ayo.file.Files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.TreeSet;

public class CrashHandler implements UncaughtExceptionHandler {  
	
	
	
	
    public static final String TAG = "CrashHandler";
    public static final boolean DEBUG = true;
    private static CrashHandler INSTANCE;
    private Context mContext;
    private UncaughtExceptionHandler mDefaultHandler;
      
    private Properties mDeviceCrashInfo = new Properties();
    private static final String VERSION_NAME = "versionName";  
    private static final String VERSION_CODE = "versionCode";  
    private static final String STACK_TRACE = "STACK_TRACE";  
    private static final String CRASH_REPORTER_EXTENSION = ".cr";
      
    private CrashHandler() {
    }  
  
    public static CrashHandler getInstance() {
        if (INSTANCE == null)  
            INSTANCE = new CrashHandler();
        INSTANCE.init(Ayo.context);
        return INSTANCE;  
    }  
      
    /** 
     *
     * @param ctx 
     */  
    public void init(Context ctx) {  
        mContext = ctx;  
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();  
        //Thread.setDefaultUncaughtExceptionHandler(this);  
    }  
      
    /** 
     */
    @Override  
    public void uncaughtException(Thread thread, Throwable ex) {

        handleException(ex);
        //android.os.Process.killProcess(android.os.Process.myPid());
        //System.exit(10);
        mDefaultHandler.uncaughtException(thread, ex);

    }  
  
    /** 
     */
    private boolean handleException(Throwable ex) {  
//        if (ex == null) {
//            return true;
//        }
//        final String msg = ex.getLocalizedMessage();
        ex.printStackTrace();
//        new Thread() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                Toast.makeText(mContext, "Sorry for the crash", Toast.LENGTH_LONG).show();
//                Looper.loop();
//            }
//        }.start();
//        collectCrashDeviceInfo(mContext);
//        String crashFileName = saveCrashInfoToFile(ex);
//        //sendCrashReportsToServer(mContext);
        return true;  
    }  
  
    /** 
     */
    public void collectCrashDeviceInfo(Context ctx) {
    	mDeviceCrashInfo.put("Crash Time: ", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        try {  
            // Class for retrieving various kinds of information related to the  
            // application packages that are currently installed on the device.  
            // You can find this class through getPackageManager().  
            PackageManager pm = ctx.getPackageManager();  
            // getPackageInfo(String packageName, int flags)  
            // Retrieve overall information about an application package that is installed on the system.  
            // public static final int GET_ACTIVITIES  
            // Since: API Level 1 PackageInfo flag: return information about activities in the package in activities.  
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);  
            if (pi != null) {  
                // public String versionName The version name of this package,  
                // as specified by the <manifest> tag's versionName attribute.  
                mDeviceCrashInfo.put(VERSION_NAME, pi.versionName == null ? "not set" : pi.versionName);  
                // public int versionCode The version number of this package,   
                // as specified by the <manifest> tag's versionCode attribute.  
                mDeviceCrashInfo.put(VERSION_CODE, pi.versionCode+"");  
            }  
        } catch (NameNotFoundException e) {  
            Log.e(TAG, "Error while collect package info", e);  
        }  
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {  
            try {  
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), field.get(null)+"");  
                if (DEBUG) {  
                    Log.d(TAG, field.getName() + " : " + field.get(null));  
                }  
            } catch (Exception e) {  
                Log.e(TAG, "Error while collect crash info", e);  
            }  
        }  
    }  
      
    /** 
     *
     * @param ex 
     * @return 
     */  
    private String saveCrashInfoToFile(Throwable ex) {  
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
        mDeviceCrashInfo.put(STACK_TRACE, result);  
  
        try {  
            long timestamp = System.currentTimeMillis();  
            String fileName = "crash-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + CRASH_REPORTER_EXTENSION;
            File f = new File(Files.path.getFileInRoot("errorlog/" + fileName));
            FileOutputStream trace = new FileOutputStream(f);
            mDeviceCrashInfo.store(trace, "");  
            trace.flush();  
            trace.close();
            return fileName;
        } catch (Exception e) {  
            Log.e(TAG, "an error occured while writing report file...", e);  
        }  
        return null;  
    }  
      
    /** 
     */
    private void sendCrashReportsToServer(Context ctx) {  
        String[] crFiles = getCrashReportFiles(ctx);  
        if (crFiles != null && crFiles.length > 0) {  
            TreeSet<String> sortedFiles = new TreeSet<String>();  
            sortedFiles.addAll(Arrays.asList(crFiles));  
  
            for (String fileName : sortedFiles) {  
                File cr = new File(ctx.getFilesDir(), fileName);  
                postReport(cr);
                cr.delete();
            }  
        }   
    }  
  
    /** 
     * @param ctx
     * @return 
     */  
    private String[] getCrashReportFiles(Context ctx) {  
        File filesDir = new File(Ayo.ROOT + "errorlog") ;//.getFilesDir();
        if(!filesDir.exists() || !filesDir.isDirectory()) return new String[]{};
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);  
            }  
        };  
        return filesDir.list(filter);
    }  
  
    /**
     * @param file
     */
    private void postReport(File file){}

    /** 
     */  
    public void sendPreviousReportsToServer() {  
        sendCrashReportsToServer(mContext);  
    }  
}  
